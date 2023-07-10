package com.uade.daitp.client.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.uade.daitp.client.core.model.SeatViewData
import com.uade.daitp.databinding.ListItemSeatBinding
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle

class SeatsAdapter(
    seats: List<SeatViewData>,
    private val seatsToSelect: Int
) : RecyclerView.Adapter<SeatsAdapter.ViewHolder>() {

    private val _seats: MutableLiveData<List<SeatViewData>> by lazy { MutableLiveData<List<SeatViewData>>() }

    private val _selectedSeats: MutableLiveData<MutableList<SeatViewData>> by lazy { MutableLiveData<MutableList<SeatViewData>>() }
    val selectedSeats: LiveData<MutableList<SeatViewData>> get() = _selectedSeats

    init {
        _seats.value = seats
    }

    class ViewHolder(
        private val binding: ListItemSeatBinding,
        private val onSeatSelected: (SeatViewData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(seat: SeatViewData) {
            if (seat.availableSeat == null) {
                binding.seatNumber.text = "X"
                binding.root.isEnabled = false
                binding.root.isActivated = false
            } else {
                binding.seatNumber.text = "${seat.number}"
                binding.root.isEnabled = true

                binding.root.isActivated = seat.selected
            }

            binding.root.setOnClickListenerWithThrottle {
                onSeatSelected(seat)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemSeatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, this::onSeatSelected)
    }

    override fun getItemCount() = _seats.value?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(_seats.value!![position])
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onSeatSelected(seat: SeatViewData) {
        if (_selectedSeats.value == null) {
            _selectedSeats.value = mutableListOf()
        } else {
            if (_selectedSeats.value!!.size == seatsToSelect) {
                val toDiscard = _selectedSeats.value!![0]
                _seats.value!!.forEach {
                    if (it.row == toDiscard.row && it.number == toDiscard.number) {
                        it.selected = false
                    }
                }
                _selectedSeats.value!!.remove(toDiscard)

            }
        }
        _seats.value!!.forEach {
            if (it.row == seat.row && it.number == seat.number) {
                it.selected = true
                notifyDataSetChanged()
            }
        }
        _selectedSeats.value!!.add(seat)
        notifyDataSetChanged()
    }
}