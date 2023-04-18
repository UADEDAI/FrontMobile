package com.uade.daitp.module.di

import com.uade.daitp.login.presentation.LoginOwnerViewModel
import com.uade.daitp.owner.home.presentation.OwnerCinemaFormViewModel
import com.uade.daitp.owner.home.presentation.OwnerHomeViewModel
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
}