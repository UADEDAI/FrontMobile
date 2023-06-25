package com.uade.daitp.owner.home.presentation

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentOwnerCinemaRoomFormBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.owner.home.core.models.CreateCinemaRoomIntent
import com.uade.daitp.presentation.util.errorDialog
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class OwnerCinemaRoomFormFragment : Fragment(R.layout.fragment_owner_cinema_room_form) {

    private val viewModel = ViewModelDI.getOwnerCinemaRoomFormViewModel()
    private lateinit var binding: FragmentOwnerCinemaRoomFormBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOwnerCinemaRoomFormBinding.bind(view)

        val cinemaId: Int? = arguments?.getInt(CINEMA_ID)
        val cinemaRoomId: Int? = arguments?.getInt(CINEMA_ROOM_ID, -1)
        cinemaRoomId?.let { if (it > -1) viewModel.getRoom(it) }

        binding.cinemaFormButton.setOnClickListenerWithThrottle {
            val createIntent = CreateCinemaRoomIntent(
                cinemaId = cinemaId!!,
                name = binding.cinemaFormRoomNameText.text.toString(),
                numRows = binding.cinemaRoomFormRowText.text.toInt(),
                seats = binding.cinemaRoomFormSeatText.text.toInt(),
                enabled = binding.cinemaRoomFormEnabled.isChecked,
            )
            viewModel.processForm(createIntent)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog()
        }

        viewModel.processSuccess.observe(viewLifecycleOwner) {
            view.findNavController().popBackStack()
        }

        viewModel.roomToEdit.observe(viewLifecycleOwner) {
            binding.cinemaFormRoomNameText.setText(it.name)
            binding.cinemaRoomFormRowText.setText(it.numRows.toString())
            binding.cinemaRoomFormSeatText.setText(it.seats.toString())
            binding.cinemaRoomFormEnabled.isChecked = it.enabled

            binding.cinemaRoomFormTitle.text = getText(R.string.edit_cinema_room_title)
        }
    }

    private fun Editable?.toInt(): Int {
        if (this == null || this.isEmpty()) return 0
        return this.toString().toInt()
    }

    companion object {
        const val CINEMA_ID = "cinemaId"
        const val CINEMA_ROOM_ID = "cinemaRoomId"
    }
}
