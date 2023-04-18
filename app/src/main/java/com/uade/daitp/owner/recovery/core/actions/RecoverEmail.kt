package com.uade.daitp.owner.recovery.core.actions

import com.uade.daitp.owner.register.core.repository.OwnerRepository

class RecoverEmail(private val ownerRepository: OwnerRepository) {

    operator fun invoke(email: String) {
        ownerRepository.recoverEmail(email)
    }
}