package com.uade.daitp.owner.home.presentation

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
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
                addressNumber = binding.cinemaFormAddressNumberText.text.toInt(),
                country = binding.cinemaFormCountryText.text.toString(),
                province = binding.cinemaFormProvinceText.text.toString(),
                locality = binding.cinemaFormLocalityText.text.toString(),
                neighbourhood = binding.cinemaFormNeighbourhoodText.text.toString(),
                latitude = binding.cinemaFormLatitudeText.text.toLong(),
                longitude = binding.cinemaFormLongitudeText.text.toLong(),
                price = binding.cinemaFormPriceText.text.toDouble(),
                enabled = binding.cinemaFormEnabled.isChecked,
            )
            viewModel.processForm(createIntent)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Invalid Data", Toast.LENGTH_LONG).show()
        }

        viewModel.processSuccess.observe(viewLifecycleOwner) {
            view.findNavController().popBackStack()
        }

        viewModel.cinemaToEdit.observe(viewLifecycleOwner) {
            binding.cinemaFormNameText.setText(it.name)
            binding.cinemaFormAddressText.setText(it.address)
            binding.cinemaFormAddressNumberText.setText(it.addressNumber.toString())
            binding.cinemaFormCountryText.setText(it.country)
            binding.cinemaFormProvinceText.setText(it.province)
            binding.cinemaFormLocalityText.setText(it.locality)
            binding.cinemaFormNeighbourhoodText.setText(it.neighbourhood)
            binding.cinemaFormLatitudeText.setText(it.latitude.toString())
            binding.cinemaFormLongitudeText.setText(it.longitude.toString())
            binding.cinemaFormPriceText.setText(it.price.toString())
            binding.cinemaFormEnabled.isChecked = it.enabled
        }

        val cinemaToEdit = arguments?.getInt(CINEMA_ID)
        cinemaToEdit?.let { viewModel.getCinemaToEdit(it) }
    }

    private fun Editable?.toInt(): Int {
        if (this == null || this.isEmpty()) return 0
        return this.toString().toInt()
    }

    private fun Editable?.toLong(): Long {
        if (this == null || this.isEmpty()) return 0L
        return this.toString().toLong()
    }

    private fun Editable?.toDouble(): Double {
        if (this == null || this.isEmpty()) return 0.0
        return this.toString().toDouble()
    }

    companion object {
        const val CINEMA_ID = "cinemaId"
    }
}
