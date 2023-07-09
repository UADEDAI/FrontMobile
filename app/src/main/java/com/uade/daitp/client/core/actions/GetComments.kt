package com.uade.daitp.client.core.actions

import com.uade.daitp.client.core.model.Comment
import com.uade.daitp.client.core.repository.ClientRepository

class GetComments (private val clientRepository: ClientRepository) {

    suspend operator fun invoke(movieId: Int): List<Comment> {
        return clientRepository.getComments(movieId)
    }

}