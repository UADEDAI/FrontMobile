package com.uade.daitp.client.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.firebase.ui.auth.AuthUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentClientConfigurationBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.errorDialog
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle
import com.uade.daitp.presentation.util.successDialog

class ClientConfigurationFragment : Fragment(R.layout.fragment_client_configuration) {

    private val viewModel = ViewModelDI.getClientConfigurationViewModel()
    private lateinit var binding: FragmentClientConfigurationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientConfigurationBinding.bind(view)

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
                AuthUI.getInstance().signOut(requireContext()).addOnCompleteListener {
                    successDialog(getString(R.string.session_closed)) {
                        view.findNavController()
                            .navigate(R.id.action_clientHomeFragment_to_loginChoiceFragment)
                    }
                }.addOnFailureListener {
                    errorDialog {
                        view.findNavController()
                            .navigate(R.id.action_clientHomeFragment_to_loginChoiceFragment)
                    }
                }
        }

        viewModel.unsubscribeSuccess.observe(viewLifecycleOwner) {
            if (it)
                AuthUI.getInstance().signOut(requireContext()).addOnCompleteListener {
                    successDialog(getString(R.string.unsubscribed)) {
                        view.findNavController()
                            .navigate(R.id.action_clientHomeFragment_to_loginChoiceFragment)
                    }
                }.addOnFailureListener {
                    errorDialog {
                        view.findNavController()
                            .navigate(R.id.action_clientHomeFragment_to_loginChoiceFragment)
                    }
                }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog()
        }
    }
}