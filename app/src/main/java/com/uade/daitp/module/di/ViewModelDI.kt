package com.uade.daitp.module.di

import com.uade.daitp.client.presentation.ClientRegisterViewModel
import com.uade.daitp.login.presentation.LoginOwnerViewModel
import com.uade.daitp.owner.home.presentation.*
import com.uade.daitp.owner.recovery.presentacion.OwnerRecoverEmailViewModel
import com.uade.daitp.owner.recovery.presentacion.OwnerRecoverPasswordViewModel
import com.uade.daitp.owner.register.presentation.OwnerRegisterViewModel
import com.uade.daitp.owner.register.presentation.OwnerValidateViewModel

object ViewModelDI {
    fun getLoginViewModel() = LoginOwnerViewModel(ActionsDI.getLoginOwner(), ActionsDI.getUser())

    fun getOwnerValidateViewModel() = OwnerValidateViewModel(ActionsDI.getValidateOwner())

    fun getOwnerRegisterViewModel() = OwnerRegisterViewModel(ActionsDI.getRegisterOwner())

    fun getRecoverViewModel() = OwnerRecoverEmailViewModel(ActionsDI.getRecoverEmailOwner())

    fun getRecoverPasswordViewModel() =
        OwnerRecoverPasswordViewModel(ActionsDI.getRecoverPasswordOwner())

    fun getOwnerCinemasListViewModel() = OwnerCinemasListViewModel(
        ActionsDI.getGetCinemasOwner(),
        ActionsDI.getDeleteCinema()
    )

    fun getOwnerCinemaFormViewModel() = OwnerCinemaFormViewModel(
        ActionsDI.getAddCinema(),
        ActionsDI.getUpdateCinema(),
        ActionsDI.getCinema(),
        ActionsDI.getUser()
    )

    fun getOwnerCinemaRoomFormViewModel() = OwnerCinemaRoomFormViewModel(
        ActionsDI.getCinemaRoom(),
        ActionsDI.getAddCinemaRoom(),
        ActionsDI.getUpdateCinemaRoom()
    )

    fun getOwnerCinemaViewModel() =
        OwnerCinemaViewModel(
            ActionsDI.getCinema(),
            ActionsDI.getCinemaRooms(),
            ActionsDI.getMoviesByRoom(),
            ActionsDI.getScreeningsBy(),
            ActionsDI.getAvailableScreeningsBy(),
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

    fun getOwnerProfileViewModel() = OwnerProfileViewModel(
        ActionsDI.getUser(),
        ActionsDI.getUpdateUser()
    )

    fun getOwnerConfigurationViewModel() = OwnerConfigurationViewModel(
        ActionsDI.getDeleteUser(),
        ActionsDI.getLogoutUser()
    )

    fun getClientRegisterViewModel() = ClientRegisterViewModel()
}