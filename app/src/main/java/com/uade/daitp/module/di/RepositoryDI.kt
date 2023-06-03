package com.uade.daitp.module.di

import com.uade.daitp.login.infrastructure.repository.DummyLoginRepository
import com.uade.daitp.owner.home.infrastructure.InMemoryCinemaRepository
import com.uade.daitp.owner.home.infrastructure.InMemoryMovieRepository
import com.uade.daitp.owner.register.infrastructure.InMemoryOwnerRepository

object RepositoryDI {
    private val loginRepo by lazy { DummyLoginRepository() }
    fun getLoginRepository() = loginRepo

    private val ownerRepo by lazy { InMemoryOwnerRepository() }
    fun getOwnerRepository() = ownerRepo

    private val cinemaRepo by lazy { InMemoryCinemaRepository() }
    fun getCinemaRepository() = cinemaRepo

    private val movieRepo by lazy { InMemoryMovieRepository() }
    fun getMovieRepository() = movieRepo
}