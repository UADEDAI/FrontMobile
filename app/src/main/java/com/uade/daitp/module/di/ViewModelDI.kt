package com.uade.daitp.module.di

import com.uade.daitp.login.presentation.LoginOwnerViewModel
import com.uade.daitp.owner.home.presentation.*
import com.uade.daitp.owner.recovery.presentacion.OwnerRecoverEmailViewModel
import com.uade.daitp.owner.recovery.presentacion.OwnerRecoverPasswordViewModel
import com.uade.daitp.owner.register.presentation.OwnerRegisterViewModel

object ViewModelDI {
    fun getLoginViewModel() = LoginOwnerViewModel(ActionsDI.getLoginOwner())

    fun getOwnerRegisterViewModel() = OwnerRegisterViewModel(ActionsDI.getRegisterOwner())

    fun getRecoverViewModel() = OwnerRecoverEmailViewModel(ActionsDI.getRecoverEmailOwner())

    fun getRecoverPasswordViewModel() =
        OwnerRecoverPasswordViewModel(ActionsDI.getRecoverPasswordOwner())

    fun getOwnerHomeViewModel() = OwnerHomeViewModel(
        ActionsDI.getGetCinemasOwner(),
        ActionsDI.getDeleteCinema()
    )

    fun getOwnerCinemaFormViewModel() = OwnerCinemaFormViewModel(
        ActionsDI.getAddCinema(),
        ActionsDI.getDeleteCinema(),
        ActionsDI.getCinema()
    )

    fun getOwnerCinemaRoomFormViewModel() = OwnerCinemaRoomFormViewModel(
        ActionsDI.getCinemaRoom(),
        ActionsDI.getAddCinemaRoom(),
        ActionsDI.getDeleteCinemaRoom()
    )

    fun getOwnerCinemaViewModel() =
        OwnerCinemaViewModel(
            ActionsDI.getCinema(),
            ActionsDI.getCinemaRooms(),
            ActionsDI.getMoviesByRoom(),
            ActionsDI.getScreeningsBy(),
            ActionsDI.addScreening(),
            ActionsDI.deleteScreening()
        )

    fun getOwnerMoviesViewModel() = OwnerMoviesViewModel(
        ActionsDI.getCinemaRoom(),
        ActionsDI.getMoviesByRoom(),
        ActionsDI.addMoviesToRoom(),
        ActionsDI.deleteMoviesFromRoom(),
        ActionsDI.getMovies(),
    )

    fun getOwnerMovieDetailViewModel() = OwnerMovieDetailViewModel(
        ActionsDI.getMovie()
    )
}