package com.uade.daitp.owner.recovery.presentacion

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerForgotEmailBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.errorDialog
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class OwnerRecoverEmailFragment : Fragment(R.layout.fragment_owner_forgot_email) {

    private val viewModel = ViewModelDI.getRecoverViewModel()
    private lateinit var binding: FragmentOwnerForgotEmailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerForgotEmailBinding.bind(view)

        binding.recoverButton.setOnClickListenerWithThrottle {
            viewModel.recover(binding.recoverEmailText.text.toString())
        }

        binding.recoverBack.setOnClickListenerWithThrottle {
            view.findNavController().popBackStack()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog()
        }

        viewModel.recoverSuccess.observe(viewLifecycleOwner) {
            if (it)
                view.findNavController()
                    .navigate(R.id.action_ownerRecoverEmailFragment_to_ownerRecoverNewPasswordFragment)
        }
    }


}