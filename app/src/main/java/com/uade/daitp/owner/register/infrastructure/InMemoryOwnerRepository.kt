package com.uade.daitp.owner.register.infrastructure

import com.uade.daitp.owner.recovery.core.models.exceptions.InvalidRecoveryEmailException
import com.uade.daitp.owner.recovery.core.models.exceptions.InvalidRecoveryPasswordException
import com.uade.daitp.owner.register.core.model.exceptions.InvalidRegisterDataError
import com.uade.daitp.owner.register.core.repository.OwnerRepository

class InMemoryOwnerRepository : OwnerRepository {
    override fun registerOwner(email: String, password: String, username: String, company: String) {
        if (email != "test@test.com") throw InvalidRegisterDataError("Email already used")
    }

    override fun recoverEmail(email: String) {
        if (email != "test@test.com") throw InvalidRecoveryEmailException("Invalid email")
    }

    override fun recoverPassword(code: String, password: String) {
        if (code != "1234") throw InvalidRecoveryPasswordException("Invalid code")
    }
}
