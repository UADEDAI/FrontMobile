package com.uade.daitp.owner.recovery.presentacion

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerForgotNewPasswordBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.errorDialog
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class OwnerRecoverNewPasswordFragment : Fragment(R.layout.fragment_owner_forgot_new_password) {

    private val viewModel = ViewModelDI.getRecoverPasswordViewModel()
    private lateinit var binding: FragmentOwnerForgotNewPasswordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerForgotNewPasswordBinding.bind(view)

        binding.recoverButton.setOnClickListenerWithThrottle {
            val password = binding.recoverPasswordText.text.toString()
            val passwordConfirm = binding.recoverNewPasswordText.text.toString()
            if (password != passwordConfirm)
                Toast.makeText(requireContext(), "Password does not match", Toast.LENGTH_LONG)
                    .show()
            viewModel.recover(binding.recoverCodeText.text.toString(), password)
        }

        binding.recoverBack.setOnClickListenerWithThrottle {
            view.findNavController()
                .popBackStack(R.id.loginEmailFragment, false)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog()
        }

        viewModel.recoverSuccess.observe(viewLifecycleOwner) {
            if (it)
                view.findNavController()
                    .navigate(R.id.action_ownerRecoverNewPasswordFragment_to_loginEmailFragment)
        }
    }


}