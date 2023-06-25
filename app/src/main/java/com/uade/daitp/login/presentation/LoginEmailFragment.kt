package com.uade.daitp.login.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentLoginEmailBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.errorDialog
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class LoginEmailFragment : Fragment(R.layout.fragment_login_email) {

    private val viewModel: LoginOwnerViewModel = ViewModelDI.getLoginViewModel()
    private lateinit var binding: FragmentLoginEmailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginEmailBinding.bind(view)

        binding.loginButton.setOnClickListenerWithThrottle {
            viewModel.login(
                binding.loginUsername.text.toString(),
                binding.loginPassword.text.toString()
            )
        }

        binding.loginRegister.setOnClickListenerWithThrottle {
            view.findNavController()
                .navigate(R.id.action_loginEmailFragment_to_ownerRegisterFragment)
        }

        binding.loginRecover.setOnClickListenerWithThrottle {
            view.findNavController()
                .navigate(R.id.action_loginEmailFragment_to_ownerRecoverEmailFragment)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog(getString(R.string.invalid_email_password))
        }

        viewModel.userLoggedIn.observe(viewLifecycleOwner) {
            if (it)
                view.findNavController()
                    .navigate(R.id.action_loginEmailFragment_to_ownerHomeFragment)
        }
    }
}