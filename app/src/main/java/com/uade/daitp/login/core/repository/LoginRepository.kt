package com.uade.daitp.login.core.repository

import com.uade.daitp.login.core.model.Owner

interface LoginRepository {
    fun getLoggedInOwner(): Owner
    fun loginOwner(userName: String, password: String)
}