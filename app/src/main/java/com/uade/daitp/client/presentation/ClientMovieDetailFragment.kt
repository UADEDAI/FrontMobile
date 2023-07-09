package com.uade.daitp.client.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uade.daitp.R
import com.uade.daitp.client.presentation.adapters.CommentsAdapter
import com.uade.daitp.databinding.DialogCommentBinding
import com.uade.daitp.databinding.FragmentClientMovieDetailBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.presentation.util.errorDialog
import com.uade.daitp.presentation.util.setOnClickListenerWithThrottle
import java.util.*

class ClientMovieDetailFragment : Fragment(R.layout.fragment_client_movie_detail) {

    private val viewModel = ViewModelDI.getClientMovieDetailViewModel()
    private lateinit var binding: FragmentClientMovieDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientMovieDetailBinding.bind(view)

        binding.movieInfoButton.isActivated = true

        binding.movieInfoButton.setOnClickListenerWithThrottle {
            binding.movieInfoButton.isActivated = true
            binding.movieCommentButton.isActivated = false

            binding.movieDetails.visibility = VISIBLE
            binding.movieCommentsLayout.visibility = GONE
        }

        binding.movieShareButton.setOnClickListenerWithThrottle {
            val intent = Intent(Intent.ACTION_SEND)
            val shareBody =
                "${getString(R.string.app_name)}: ${getString(R.string.share_text)} ${viewModel.movie.value?.title ?: "Movie"}"
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_SUBJECT,
                viewModel.movie.value?.title ?: "Movie"
            )
            intent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(intent, getString(R.string.share_using)))
        }

        binding.movieCommentButton.setOnClickListenerWithThrottle {
            binding.movieInfoButton.isActivated = false
            binding.movieCommentButton.isActivated = true

            binding.movieDetails.visibility = GONE
            binding.movieCommentsLayout.visibility = VISIBLE
        }

        binding.movieCommentConfirmButton.setOnClickListenerWithThrottle {
            val dialogBinding = DialogCommentBinding.inflate(
                LayoutInflater.from(requireContext()),
                binding.root,
                false
            )
            MaterialAlertDialogBuilder(requireContext())
                .setView(dialogBinding.root)
                .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                .setPositiveButton(getString(R.string.accept)) { _, _ ->
                    viewModel.createCommentOf(
                        dialogBinding.dialogTitleText.text.toString(),
                        dialogBinding.dialogBodyText.text.toString(),
                        dialogBinding.dialogScore.values[0].toDouble()
                    )
                }
                .show()
        }

        val movieId = arguments?.getInt(ClientHomeMoviesListFragment.MOVIE_ID)
        movieId?.let { id ->
            viewModel.getMovieBy(id)
            viewModel.refreshComments(id)
        }

        viewModel.movie.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.imageUrl)
                .into(binding.movieImage)
            binding.movieDescription.text = it.description
            binding.movieDuration.text = getDuration(it.duration)
            binding.movieYear.text = getYear(it.releaseDate)
            binding.movieCategory.text = it.genre
            binding.movieDirector.text = it.director
            binding.movieCast.text = it.cast
            binding.movieScore.text = it.score?.toString() ?: "-"
        }

        val recyclerView = binding.movieCommentsList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CommentsAdapter()
        viewModel.comments.observe(viewLifecycleOwner) {
            (recyclerView.adapter as CommentsAdapter).refreshData(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            errorDialog()
        }
    }

    private fun getYear(releaseDate: Date): String {
        val date = Calendar.getInstance()
        date.time = releaseDate
        return date.get(Calendar.YEAR).toString()
    }

    private fun getDuration(duration: Int): String {
        return "${(duration / 60).toString().padStart(2, '0')}:${
            (duration % 60).toString().padStart(2, '0')
        }"
    }

}