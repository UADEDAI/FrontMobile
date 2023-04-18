package com.uade.daitp.owner.recovery.presentacion

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerForgotEmailBinding
import com.uade.daitp.databinding.FragmentOwnerForgotNewPasswordBinding
import com.uade.daitp.databinding.FragmentOwnerRegisterBinding
import com.uade.daitp.module.di.ViewModelDI

class OwnerRecoverNewPasswordFragment : Fragment(R.layout.fragment_owner_forgot_new_password) {

    private val viewModel = ViewModelDI.getRecoverPasswordViewModel()
    private lateinit var binding: FragmentOwnerForgotNewPasswordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerForgotNewPasswordBinding.bind(view)

        binding.recoverButton.setOnClickListener {
            val password = binding.recoverPasswordText.text.toString()
            val passwordConfirm = binding.recoverNewPasswordText.text.toString()
            if (password != passwordConfirm)
                Toast.makeText(requireContext(), "Password does not match", Toast.LENGTH_LONG)
                    .show()
            viewModel.recover(binding.recoverCodeText.text.toString(), password)
        }

        binding.recoverBack.setOnClickListener {
            view.findNavController()
                .popBackStack(R.id.loginEmailFragment, false)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Invalid recovery code", Toast.LENGTH_LONG).show()
        }

        viewModel.recoverSuccess.observe(viewLifecycleOwner) {
            if (it)
                view.findNavController()
                    .navigate(R.id.action_ownerRecoverNewPasswordFragment_to_ownerHomeFragment)
        }
    }


}