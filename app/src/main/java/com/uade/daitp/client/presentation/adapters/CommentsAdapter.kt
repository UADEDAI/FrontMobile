package com.uade.daitp.client.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uade.daitp.client.core.model.Comment
import com.uade.daitp.databinding.ListItemCommentBinding
import java.util.*

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    private val comments: MutableList<Comment> = mutableListOf()

    class ViewHolder(
        private val binding: ListItemCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: Comment) {
            binding.itemPersonName.text = getInitials(comment.user?.username)
            binding.itemScore.text = comment.rating.toString()
            binding.itemTitle.text = comment.title
            binding.itemDescription.text = comment.body
        }

        private fun getInitials(username: String?): String {
            username?.let {
                val split = it.split(" ")
                var initials = ""
                split.forEach { name-> initials += name[0] }
                return initials
            } ?: kotlin.run {
                return "AN"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = comments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(data: List<Comment>) {
        comments.clear()
        comments.addAll(data)
        notifyDataSetChanged()
    }
}