package com.uade.daitp.client.presentation.bottomSheet

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uade.daitp.R
import com.uade.daitp.databinding.BottomSheetCinemasBinding
import com.uade.daitp.databinding.BottomSheetFilterBinding
import com.uade.daitp.owner.home.core.models.Cinema
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class CinemasBottomSheet(private val listener: CinemasListener) : BottomSheetDialogFragment() {

    private var cinemaList: MutableList<Cinema> = mutableListOf()
    private lateinit var binding: BottomSheetCinemasBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bottom_sheet_cinemas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = BottomSheetCinemasBinding.bind(view)

        binding.cinemaSelectButton.setOnClickListenerWithThrottle {
            val cinema =
                cinemaList.filter { cinema -> cinema.name.contains(binding.cinemaSelectText.text.toString()) }
            if (cinema.isNotEmpty()){
                listener.onCinemaSelected(
                    cinema[0]
                )
            }
        }
    }

    fun setCinemas(cinemas: List<Cinema>) {
        cinemaList.clear()
        cinemaList.addAll(cinemas)

        val items = cinemaList.map { cinema -> cinema.name }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_popup_window_item, items)
        binding.cinemaSelectText.setAdapter(adapter)
    }

    interface CinemasListener {
        fun onCinemaSelected(
            cinema: Cinema?
        )
    }

    companion object {
        const val TAG = "CinemasBottomSheet"
    }
}