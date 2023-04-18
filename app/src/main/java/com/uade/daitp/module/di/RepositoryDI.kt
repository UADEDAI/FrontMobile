package com.uade.daitp.module.di

import com.uade.daitp.login.infrastructure.repository.DummyLoginRepository
import com.uade.daitp.module.infrastructure.InMemoryOwnerRepository

object RepositoryDI {
    private val loginRepo by lazy { DummyLoginRepository() }
    fun getLoginRepository() = loginRepo

    private val ownerRepo by lazy { InMemoryOwnerRepository() }
    fun getOwnerRepository() = ownerRepo
}