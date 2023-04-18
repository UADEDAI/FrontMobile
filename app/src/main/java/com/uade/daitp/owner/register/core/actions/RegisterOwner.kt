package com.uade.daitp.owner.register.core.actions

import com.uade.daitp.owner.register.core.repository.OwnerRepository

class RegisterOwner(private val ownerRepository: OwnerRepository) {

    operator fun invoke(email: String, password: String, username: String, company: String) {
        ownerRepository.registerOwner(email, password, username, company)
    }
}
