package com.uade.daitp.owner.home.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uade.daitp.databinding.ListItemScreeningBinding
import com.uade.daitp.owner.home.core.models.Screening
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("NotifyDataSetChanged")
class ScreeningAdapter(
    private var screenings: List<Screening>,
//    private val viewModel: OwnerHomeViewModel
) :
    RecyclerView.Adapter<ScreeningAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: ListItemScreeningBinding,
//        private val viewModel: OwnerHomeViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(screening: Screening) {
            binding.itemScreeningTime.text = getScreeningTime(screening)

            binding.itemScreeningClose.setOnClickListenerWithThrottle {
                //TODO delete viewModel.deleteScreening(screening)
            }
        }

        private fun to24HourFormat(date: Date): String {
            val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            return dateFormat.format(date)
        }

        private fun getScreeningTime(screening: Screening): String {
            return "${to24HourFormat(screening.startAt)} ${to24HourFormat(screening.endAt)}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemScreeningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = screenings.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(screenings[position])
    }

    fun updateData(data: List<Screening>) {
        screenings = data
        notifyDataSetChanged()
    }

}