package com.uade.daitp.client.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentClientProfileBinding

class ClientProfileFragment : Fragment(R.layout.fragment_client_profile) {

    private lateinit var binding: FragmentClientProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientProfileBinding.bind(view)

//        viewModel.user.observe(viewLifecycleOwner) {
//            binding.profileName.setText(it.username)
//            binding.profileEmail.setText(it.email)
//        }
//
//        binding.profileButton.setOnClickListenerWithThrottle {
//            viewModel.update(
//                binding.profileName.text.toString(),
//            )
//        }
//
//        viewModel.profileUpdated.observe(viewLifecycleOwner) {
//            if (it)
//                successDialog()
//        }
//
//        viewModel.error.observe(viewLifecycleOwner) {
//            errorDialog()
//        }
    }
}