package com.uade.daitp.client.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.uade.daitp.R
import com.uade.daitp.client.core.model.ScreeningClient
import com.uade.daitp.databinding.ListItemClientScreeningBinding
import com.uade.daitp.owner.home.core.models.enums.ScreeningFormat
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle
import java.text.SimpleDateFormat
import java.util.*

class ScreeningsClientAdapter : RecyclerView.Adapter<ScreeningsClientAdapter.ViewHolder>() {

    private val _selectedScreening: MutableLiveData<ScreeningClient> by lazy { MutableLiveData<ScreeningClient>() }
    val selectedScreening: LiveData<ScreeningClient> get() = _selectedScreening

    private val screenings: MutableList<ScreeningClient> = mutableListOf()

    class ViewHolder(
        private val binding: ListItemClientScreeningBinding,
        private val selectedScreening: MutableLiveData<ScreeningClient>
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(screening: ScreeningClient) {
            binding.itemTime.text = toFriendlyDateString(screening.startAt)
            binding.itemRoomName.text = screening.room.name
            binding.itemPrice.text = "\$${screening.room.cinema.price}"
            binding.itemFormat.text = when (screening.format) {
                "subtitled" ->
                    itemView.resources.getString(R.string.subtitled)
                "dubbed" ->
                    itemView.resources.getString(R.string.dubbed)
                else ->
                    itemView.resources.getString(R.string.original)
            }
            binding.itemSeats.text = screening.availableSeats.toString()

            binding.itemButton.setOnClickListenerWithThrottle {
                selectedScreening.postValue(screening)
            }
        }

        private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        private fun toFriendlyDateString(date: Date): String {
            return dateFormat.format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemClientScreeningBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding, _selectedScreening)
    }

    override fun getItemCount() = screenings.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(screenings[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(data: List<ScreeningClient>) {
        screenings.clear()
        screenings.addAll(data)
        notifyDataSetChanged()
    }
}