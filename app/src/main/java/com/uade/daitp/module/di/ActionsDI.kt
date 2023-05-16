package com.uade.daitp.module.di

import com.uade.daitp.login.core.actions.LoginOwner
import com.uade.daitp.owner.home.core.actions.AddCinemas
import com.uade.daitp.owner.home.core.actions.DeleteCinema
import com.uade.daitp.owner.home.core.actions.GetCinema
import com.uade.daitp.owner.home.core.actions.GetCinemas
import com.uade.daitp.owner.recovery.core.actions.RecoverEmail
import com.uade.daitp.owner.recovery.core.actions.RecoverPassword
import com.uade.daitp.owner.register.core.actions.RegisterOwner

object ActionsDI {
    fun getLoginOwner() = LoginOwner(RepositoryDI.getLoginRepository())

    fun getRegisterOwner() = RegisterOwner(RepositoryDI.getOwnerRepository())

    fun getRecoverEmailOwner() = RecoverEmail(RepositoryDI.getOwnerRepository())

    fun getRecoverPasswordOwner() = RecoverPassword(RepositoryDI.getOwnerRepository())

    fun getAddCinema() = AddCinemas(RepositoryDI.getCinemaRepository())

    fun getDeleteCinema() = DeleteCinema(RepositoryDI.getCinemaRepository())

    fun getGetCinemasOwner() = GetCinemas(RepositoryDI.getCinemaRepository())

    fun getCinema() = GetCinema(RepositoryDI.getCinemaRepository())
}