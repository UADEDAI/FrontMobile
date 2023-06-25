package com.uade.daitp.owner.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerConfigurationBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.errorDialog
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle
import com.uade.daitp.presentation.util.successDialog

class OwnerConfigurationFragment : Fragment(R.layout.fragment_owner_configuration) {

    private val viewModel = ViewModelDI.getOwnerConfigurationViewModel()
    private lateinit var binding: FragmentOwnerConfigurationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerConfigurationBinding.bind(view)

        binding.configLogoutButton.setOnClickListenerWithThrottle {
            viewModel.logout()
        }

        binding.configUnsubscribeButton.setOnClickListenerWithThrottle {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(getString(R.string.unsubscribe_confirm))
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.cancel()
                }
                .setPositiveButton(getString(R.string.accept)) { dialog, _ ->
                    viewModel.unsubscribe()
                    dialog.dismiss()
                }
                .show()
        }

        viewModel.logoutSuccess.observe(viewLifecycleOwner) {
            if (it)
                successDialog(getString(R.string.session_closed)) {
                    view.findNavController()
                        .navigate(R.id.action_ownerHomeFragment_to_loginChoiceFragment)
                }
        }

        viewModel.unsubscribeSuccess.observe(viewLifecycleOwner) {
            if (it)
                successDialog(getString(R.string.unsubscribed)) {
                    view.findNavController()
                        .navigate(R.id.action_ownerHomeFragment_to_loginChoiceFragment)
                }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog()
        }
    }
}