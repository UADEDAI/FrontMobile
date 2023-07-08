package com.uade.daitp.login.presentation

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentLoginChoiceBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.errorDialog
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class LoginChoiceFragment : Fragment(R.layout.fragment_login_choice) {

    private val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build(),
    )
    private val viewModel: LoginGoogleViewModel = ViewModelDI.getGoogleLoginViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginChoiceBinding.bind(view)

        binding.loginEmail.setOnClickListenerWithThrottle {
            it.findNavController().navigate(R.id.action_loginChoiceFragment_to_loginEmailFragment)
        }

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        val signInLauncher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract(),
        ) { result ->
            val response = result.idpResponse
            if (result.resultCode == RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                user?.getIdToken(true)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val idToken = task.result?.token
                        idToken?.let {
                            viewModel.loginGoogle(idToken)
                        } ?: run {
                            errorDialog()
                        }
                    } else {
                        errorDialog()
                    }
                }
            } else {
                if (response != null) errorDialog()
            }
        }

        binding.loginGoogle.setOnClickListenerWithThrottle {
            signInLauncher.launch(signInIntent)
        }

        viewModel.userLoggedIn.observe(viewLifecycleOwner) { shouldDefineName ->
            if (shouldDefineName) view.findNavController()
                .navigate(R.id.action_loginChoiceFragment_to_clientRegisterFragment)
            else view.findNavController()
                .navigate(R.id.action_loginChoiceFragment_to_clientHomeFragment)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog()
        }
    }

}