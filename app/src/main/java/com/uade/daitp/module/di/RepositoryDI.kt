package com.uade.daitp.module.di

import com.uade.daitp.login.infrastructure.repository.RemoteLoginRepository
import com.uade.daitp.login.infrastructure.repository.SharedPrefUserRepository
import com.uade.daitp.module.factory.RetrofitFactory.cinemaService
import com.uade.daitp.module.factory.RetrofitFactory.loginService
import com.uade.daitp.module.factory.RetrofitFactory.movieService
import com.uade.daitp.owner.home.infrastructure.RemoteCinemaRepository
import com.uade.daitp.owner.home.infrastructure.RemoteMovieRepository

object RepositoryDI {
    private val loginRepo by lazy { RemoteLoginRepository(loginService, SharedPrefUserRepository()) }
    fun getLoginRepository() = loginRepo

    private val cinemaRepo by lazy { RemoteCinemaRepository(cinemaService) }
    fun getCinemaRepository() = cinemaRepo

    private val movieRepo by lazy { RemoteMovieRepository(movieService) }
    fun getMovieRepository() = movieRepo
}