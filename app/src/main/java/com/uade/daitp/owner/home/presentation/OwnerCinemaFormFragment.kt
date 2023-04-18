package com.uade.daitp.owner.home.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerCinemaFormBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.owner.home.core.models.CreateCinemaIntent
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class OwnerCinemaFormFragment : Fragment(R.layout.fragment_owner_cinema_form) {

    private val viewModel = ViewModelDI.getOwnerCinemaFormViewModel()
    private lateinit var binding: FragmentOwnerCinemaFormBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerCinemaFormBinding.bind(view)

        binding.cinemaFormButton.setOnClickListenerWithThrottle {
            val createIntent = CreateCinemaIntent(
                name = binding.cinemaFormNameText.text.toString(),
                address = binding.cinemaFormAddressText.text.toString(),
                addressNumber = binding.cinemaFormAddressNumberText.text.toString().toInt(),
                country = binding.cinemaFormCountryText.text.toString(),
                province = binding.cinemaFormProvinceText.text.toString(),
                locality = binding.cinemaFormLocalityText.text.toString(),
                neighbourhood = binding.cinemaFormNeighbourhoodText.text.toString(),
                latitude = binding.cinemaFormLatitudeText.text.toString().toLong(),
                longitude = binding.cinemaFormLongitudeText.text.toString().toLong(),
                price = binding.cinemaFormPriceText.text.toString().toDouble(),
                enabled = binding.cinemaFormEnabled.isEnabled,
            )
            viewModel.processForm(null, createIntent)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Invalid Data", Toast.LENGTH_LONG).show()
        }

        viewModel.processSuccess.observe(viewLifecycleOwner) {
            view.findNavController().popBackStack()
        }
    }


}