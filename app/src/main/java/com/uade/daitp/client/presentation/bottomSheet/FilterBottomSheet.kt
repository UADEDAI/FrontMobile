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
import com.uade.daitp.databinding.BottomSheetFilterBinding
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class FilterBottomSheet(private val listener: FilterListener) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bottom_sheet_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = BottomSheetFilterBinding.bind(view)

        val items = listOf(
            getString(R.string.action),
            getString(R.string.adventure),
            getString(R.string.comedy),
            getString(R.string.crime),
            getString(R.string.drama),
            getString(R.string.fantasy),
            getString(R.string.horror),
            getString(R.string.mystery),
            getString(R.string.romance),
            getString(R.string.scifi),
            getString(R.string.thriller)
        )

        val adapter = ArrayAdapter(requireContext(), R.layout.list_popup_window_item, items)
        binding.filterGenreMenu.setAdapter(adapter)

        binding.filterButton.setOnClickListenerWithThrottle {
            val genre = when (binding.filterGenreMenu.text.toString()) {
                getString(R.string.action) -> "Action"
                getString(R.string.adventure) -> "Adventure"
                getString(R.string.comedy) -> "Comedy"
                getString(R.string.crime) -> "Crime"
                getString(R.string.drama) -> "Drama"
                getString(R.string.fantasy) -> "Fantasy"
                getString(R.string.horror) -> "Horror"
                getString(R.string.mystery) -> "Mystery"
                getString(R.string.romance) -> "Romance"
                getString(R.string.scifi) -> "Sci-Fi"
                getString(R.string.thriller) -> "Thriller"
                else -> ""
            }
            listener.onFilterSelected(
                binding.filterDistanceText.text.toDouble(),
                genre,
                binding.filterScore.values[0].toDouble()
            )
        }

        binding.filterClear.setOnClickListenerWithThrottle {
            listener.clearFilters()
        }
    }

    private fun Editable?.toDouble(): Double {
        if (this == null || this.isEmpty()) return 0.0
        return this.toString().toDouble()
    }

    interface FilterListener {
        fun onFilterSelected(
            distance: Double,
            genre: String?,
            score: Double?
        )

        fun clearFilters()
    }

    companion object {
        const val TAG = "FilterBottomSheet"
    }
}