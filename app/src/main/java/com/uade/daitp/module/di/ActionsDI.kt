package com.uade.daitp.module.di

import com.uade.daitp.login.core.actions.LoginOwner
import com.uade.daitp.owner.home.core.actions.*
import com.uade.daitp.owner.recovery.core.actions.RecoverEmail
import com.uade.daitp.owner.recovery.core.actions.RecoverPassword
import com.uade.daitp.owner.register.core.actions.RegisterOwner

object ActionsDI {
    fun getLoginOwner() = LoginOwner(RepositoryDI.getLoginRepository())

    fun getRegisterOwner() = RegisterOwner(RepositoryDI.getOwnerRepository())

    fun getRecoverEmailOwner() = RecoverEmail(RepositoryDI.getOwnerRepository())

    fun getRecoverPasswordOwner() = RecoverPassword(RepositoryDI.getOwnerRepository())

    fun getAddCinema() = AddCinema(RepositoryDI.getCinemaRepository())

    fun getDeleteCinema() = DeleteCinema(RepositoryDI.getCinemaRepository())

    fun getGetCinemasOwner() = GetCinemas(RepositoryDI.getCinemaRepository())

    fun getCinema() = GetCinema(RepositoryDI.getCinemaRepository())

    fun getCinemaRooms() = GetCinemaRooms(RepositoryDI.getCinemaRepository())

    fun getCinemaRoom() = GetCinemaRoom(RepositoryDI.getCinemaRepository())

    fun getAddCinemaRoom() = AddCinemaRoom(RepositoryDI.getCinemaRepository())

    fun getDeleteCinemaRoom() = DeleteCinemaRoom(RepositoryDI.getCinemaRepository())

    fun getMoviesByRoom() = GetMoviesByRoom(RepositoryDI.getMovieRepository())

    fun addMoviesToRoom() = AddMovieToRoom(RepositoryDI.getMovieRepository())

    fun deleteMoviesFromRoom() = DeleteMovieFromRoom(RepositoryDI.getMovieRepository())

    fun getMovies() = GetMovies(RepositoryDI.getMovieRepository())
}