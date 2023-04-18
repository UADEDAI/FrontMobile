package com.uade.daitp.login.infrastructure.repository

import com.uade.daitp.login.core.model.Owner
import com.uade.daitp.login.core.model.exceptions.InvalidUserException
import com.uade.daitp.login.core.repository.LoginRepository

class DummyLoginRepository : LoginRepository {
    override fun getLoggedInOwner(): Owner {
        return Owner()
    }

    override fun loginOwner(userName: String, password: String) {
        if (userName != "test" && password != "test") {
            throw InvalidUserException("Invalid credentials")
        }
    }
}