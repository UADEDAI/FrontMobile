package com.uade.daitp.client.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentClientConfigurationBinding

class ClientConfigurationFragment : Fragment(R.layout.fragment_client_configuration) {

    private lateinit var binding: FragmentClientConfigurationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientConfigurationBinding.bind(view)

//        binding.configLogoutButton.setOnClickListenerWithThrottle {
//            viewModel.logout()
//        }
//
//        binding.configUnsubscribeButton.setOnClickListenerWithThrottle {
//            MaterialAlertDialogBuilder(requireContext())
//                .setMessage(getString(R.string.unsubscribe_confirm))
//                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
//                    dialog.cancel()
//                }
//                .setPositiveButton(getString(R.string.accept)) { dialog, _ ->
//                    viewModel.unsubscribe()
//                    dialog.dismiss()
//                }
//                .show()
//        }
//
//        viewModel.logoutSuccess.observe(viewLifecycleOwner) {
//            if (it)
//                successDialog(getString(R.string.session_closed)) {
//                    view.findNavController()
//                        .navigate(R.id.action_ownerHomeFragment_to_loginChoiceFragment)
//                }
//        }
//
//        viewModel.unsubscribeSuccess.observe(viewLifecycleOwner) {
//            if (it)
//                successDialog(getString(R.string.unsubscribed)) {
//                    view.findNavController()
//                        .navigate(R.id.action_ownerHomeFragment_to_loginChoiceFragment)
//                }
//        }
//
//        viewModel.error.observe(viewLifecycleOwner) {
//            errorDialog()
//        }
    }
}