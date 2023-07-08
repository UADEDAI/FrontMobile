package com.uade.daitp.owner.register.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerValidateBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle
import com.uade.daitp.presentation.util.successDialog


class OwnerValidateFragment : Fragment(R.layout.fragment_owner_validate) {

    private val viewModel = ViewModelDI.getOwnerValidateViewModel()
    private lateinit var binding: FragmentOwnerValidateBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerValidateBinding.bind(view)

        binding.validateText.addTextChangedListener {
            it?.let {
                if (it.length == 6) {
                    val inputMethodManager =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }

        binding.validateButton.setOnClickListenerWithThrottle {
            viewModel.validate(
                binding.validateText.text.toString(),
            )
        }

        viewModel.error.observe(viewLifecycleOwner) {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(getString(R.string.dialog_error))
                .setPositiveButton(getString(R.string.accept)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        viewModel.validateSuccess.observe(viewLifecycleOwner) {
            if (it)
                successDialog(getString(R.string.validate_dialog_success)) {
                    view.findNavController()
                        .navigate(R.id.action_ownerValidateFragment_to_ownerHomeFragment)
                }
        }
    }


}