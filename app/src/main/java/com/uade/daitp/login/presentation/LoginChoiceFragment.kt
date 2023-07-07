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
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class LoginChoiceFragment : Fragment(R.layout.fragment_login_choice) {

    private val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build(),
    )

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
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                view.findNavController()
                    .navigate(R.id.action_loginChoiceFragment_to_clientRegisterFragment)
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }

        binding.loginGoogle.setOnClickListenerWithThrottle {
            signInLauncher.launch(signInIntent)
        }
    }

}