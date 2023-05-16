package com.uade.daitp.owner.register.core.repository

interface OwnerRepository {
    fun registerOwner(email: String, password: String, username: String, company: String)
    fun recoverEmail(email: String)
    fun recoverPassword(code: String, password: String)
}