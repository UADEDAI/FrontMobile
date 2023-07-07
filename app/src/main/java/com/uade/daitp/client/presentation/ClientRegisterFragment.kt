package com.uade.daitp.client.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentClientRegisterBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.errorDialog
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class ClientRegisterFragment : Fragment(R.layout.fragment_client_register) {

    private val viewModel = ViewModelDI.getClientRegisterViewModel()
    private lateinit var binding: FragmentClientRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientRegisterBinding.bind(view)

        binding.registerButton.setOnClickListenerWithThrottle {
            viewModel.register(
                binding.registerUsername.text.toString(),
            )
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog()
        }

        viewModel.registerSuccess.observe(viewLifecycleOwner) {
            if (it)
                view.findNavController()
                    .navigate(R.id.action_clientRegisterFragment_to_clientHomeFragment)
        }
    }


}