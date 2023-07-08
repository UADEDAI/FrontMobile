package com.uade.daitp.client.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentClientMovieDetailBinding
import com.uade.daitp.owner.home.presentation.OwnerMovieManagerFragment.Companion.MOVIE_ID

class ClientMovieDetailFragment : Fragment(R.layout.fragment_client_movie_detail) {

    private lateinit var binding: FragmentClientMovieDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentClientMovieDetailBinding.bind(view)

        val movieId = arguments?.getInt(MOVIE_ID)
        movieId?.let { id ->
//            viewModel.getMovieBy(id)
        }

//        viewModel.movie.observe(viewLifecycleOwner) {
//            binding.movieName.text = it.title
//            Glide.with(requireContext())
//                .load(it.imageUrl)
//                .into(binding.movieImage);
//            binding.movieDescription.text = it.description
//            binding.movieDuration.text = getDuration(it.duration)
//            binding.movieYear.text = getYear(it.releaseDate)
//            binding.movieCategory.text = it.genre
//            binding.movieDirector.text = it.director
//            binding.movieCast.text = it.cast
//        }
    }

}