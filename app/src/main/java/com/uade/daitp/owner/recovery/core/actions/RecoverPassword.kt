package com.uade.daitp.owner.recovery.core.actions

import com.uade.daitp.owner.register.core.repository.OwnerRepository

class RecoverPassword(private val ownerRepository: OwnerRepository) {

    operator fun invoke(code: String, password: String) {
        ownerRepository.recoverPassword(code, password)
    }
}
