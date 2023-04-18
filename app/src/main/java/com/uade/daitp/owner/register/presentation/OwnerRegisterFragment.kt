package com.uade.daitp.owner.register.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerRegisterBinding
import com.uade.daitp.module.di.ViewModelDI

class OwnerRegisterFragment : Fragment(R.layout.fragment_owner_register) {

    private val viewModel = ViewModelDI.getOwnerRegisterViewModel()
    private lateinit var binding: FragmentOwnerRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerRegisterBinding.bind(view)

        binding.registerButton.setOnClickListener {
            viewModel.register(
                binding.registerEmail.text.toString(),
                binding.registerPassword.text.toString(),
                binding.registerUsername.text.toString(),
                binding.registerCompany.text.toString(),
            )
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Invalid registry data", Toast.LENGTH_LONG).show()
        }

        viewModel.registerSuccess.observe(viewLifecycleOwner) {
            if (it)
                view.findNavController()
                    .navigate(R.id.action_ownerRegisterFragment_to_ownerHomeFragment)
        }
    }


}