package com.uade.daitp.module.di

import com.uade.daitp.login.core.actions.*
import com.uade.daitp.owner.home.core.actions.*
import com.uade.daitp.owner.recovery.core.actions.RecoverEmail
import com.uade.daitp.owner.recovery.core.actions.RecoverPassword
import com.uade.daitp.owner.register.core.actions.RegisterOwner
import com.uade.daitp.owner.register.core.actions.ValidateOwner

object ActionsDI {
    fun getUser() = GetUser(RepositoryDI.getLoginRepository())

    fun getLoginOwner() = LoginOwner(RepositoryDI.getLoginRepository())

    fun getValidateOwner() = ValidateOwner(RepositoryDI.getLoginRepository())

    fun getRegisterOwner() = RegisterOwner(RepositoryDI.getLoginRepository())

    fun getRecoverEmailOwner() = RecoverEmail(RepositoryDI.getLoginRepository())

    fun getRecoverPasswordOwner() = RecoverPassword(RepositoryDI.getLoginRepository())

    fun getAddCinema() = AddCinema(RepositoryDI.getCinemaRepository())

    fun getUpdateCinema() = UpdateCinema(RepositoryDI.getCinemaRepository())

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

    fun getMovie() = GetMovie(RepositoryDI.getMovieRepository())

    fun getScreeningsBy() = GetScreeningsBy(RepositoryDI.getMovieRepository())

    fun getAvailableScreeningsBy() = GetAvailableScreeningsBy(RepositoryDI.getMovieRepository())

    fun addScreening() = AddScreening(RepositoryDI.getMovieRepository())

    fun deleteScreening() = DeleteScreening(RepositoryDI.getMovieRepository())

    fun getUpdateCinemaRoom() = UpdateCinemaRoom(RepositoryDI.getCinemaRepository())

    fun getUpdateUser() = UpdateUser(RepositoryDI.getUserRepository())

    fun getDeleteUser() = DeleteUser(RepositoryDI.getUserRepository())

    fun getLogoutUser() = LogoutUser(RepositoryDI.getUserRepository())
}