package com.uade.daitp.client.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.uade.daitp.R
import com.uade.daitp.databinding.FragmentClientMoviePagerBinding
import com.uade.daitp.module.di.ViewModelDI
import com.uade.daitp.owner.home.presentation.animations.ZoomOutPageTransformer

class ClientMoviePagerFragment : Fragment(R.layout.fragment_client_movie_pager) {

    private val viewModel = ViewModelDI.getClientMovieDetailViewModel()
    private lateinit var binding: FragmentClientMoviePagerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt(ClientHomeMoviesListFragment.MOVIE_ID)
        movieId?.let { id ->
            viewModel.getMovieBy(id)
        }

        viewModel.movie.observe(viewLifecycleOwner) {
            binding.topAppBar.title = it.title
        }

        binding = FragmentClientMoviePagerBinding.bind(view)

        binding.topAppBar.setNavigationOnClickListener {
            view.findNavController().popBackStack()
        }

        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())
        binding.moviePager.adapter = pagerAdapter
        binding.moviePager.setPageTransformer(ZoomOutPageTransformer())

        TabLayoutMediator(binding.movieTabs, binding.moviePager) { tab, position ->
            when (position) {
                0 ->
                    tab.setText(R.string.movie_details)
                1 ->
                    tab.setText(R.string.movie_functions)
            }
        }.attach()
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            val fragment = when (position) {
                0 -> {
                    ClientMovieDetailFragment()
                }
                else ->
                    ClientScreeningListFragment()
            }
            fragment.arguments = arguments
            return fragment
        }
    }

    private companion object {
        const val NUM_PAGES = 2
    }
}