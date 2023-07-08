package com.uade.daitp.module.di

import com.uade.daitp.client.core.repository.ClientRepository
import com.uade.daitp.client.infrastructure.repository.RemoteClientRepository
import com.uade.daitp.login.infrastructure.repository.RemoteLoginRepository
import com.uade.daitp.login.infrastructure.repository.RemoteUserRepository
import com.uade.daitp.login.infrastructure.repository.SharedPrefPersistenceUserRepository
import com.uade.daitp.module.factory.RetrofitFactory.cinemaService
import com.uade.daitp.module.factory.RetrofitFactory.loginService
import com.uade.daitp.module.factory.RetrofitFactory.movieService
import com.uade.daitp.module.factory.RetrofitFactory.userService
import com.uade.daitp.owner.home.infrastructure.RemoteCinemaRepository
import com.uade.daitp.owner.home.infrastructure.RemoteMovieRepository

object RepositoryDI {
    private val loginRepo by lazy {
        RemoteLoginRepository(
            loginService,
            SharedPrefPersistenceUserRepository()
        )
    }

    fun getLoginRepository() = loginRepo

    private val cinemaRepo by lazy {
        RemoteCinemaRepository(
            cinemaService,
            SharedPrefPersistenceUserRepository()
        )
    }

    fun getCinemaRepository() = cinemaRepo

    private val movieRepo by lazy {
        RemoteMovieRepository(
            movieService
        )
    }

    fun getMovieRepository() = movieRepo

    private val userRepo by lazy {
        RemoteUserRepository(
            userService,
            SharedPrefPersistenceUserRepository()
        )
    }

    fun getUserRepository() = userRepo

    private val clientRepo by lazy {
        RemoteClientRepository()
    }

    fun getClientRepository() = clientRepo
}