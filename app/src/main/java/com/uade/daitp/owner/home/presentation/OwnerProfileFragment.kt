package com.uade.daitp.owner.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerProfileBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class OwnerProfileFragment : Fragment(R.layout.fragment_owner_profile) {

    private val viewModel = ViewModelDI.getOwnerProfileViewModel()
    private lateinit var binding: FragmentOwnerProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerProfileBinding.bind(view)

        viewModel.user.observe(viewLifecycleOwner) {
            binding.profileName.setText(it.name)
            binding.profileEmail.setText(it.email)
            binding.profileCompany.setText(it.company)
        }

        binding.profileButton.setOnClickListenerWithThrottle {
            viewModel.update(
                binding.profileName.text.toString(),
                binding.profileCompany.text.toString()
            )
        }
    }
}