package com.uade.daitp.module.di

import com.uade.daitp.login.infrastructure.repository.DummyLoginRepository
import com.uade.daitp.module.factory.RetrofitFactory
import com.uade.daitp.owner.home.infrastructure.RemoteCinemaRepository
import com.uade.daitp.owner.home.infrastructure.RemoteMovieRepository
import com.uade.daitp.owner.register.infrastructure.InMemoryOwnerRepository

object RepositoryDI {
    private val loginRepo by lazy { DummyLoginRepository() }
    fun getLoginRepository() = loginRepo

    private val ownerRepo by lazy { InMemoryOwnerRepository() }
    fun getOwnerRepository() = ownerRepo

    private val cinemaRepo by lazy {
        RemoteCinemaRepository(
            RetrofitFactory.cinemaService
        )
    }

    fun getCinemaRepository() = cinemaRepo

    private val movieRepo by lazy {
        RemoteMovieRepository(
            RetrofitFactory.movieService
        )
    }

    fun getMovieRepository() = movieRepo
}