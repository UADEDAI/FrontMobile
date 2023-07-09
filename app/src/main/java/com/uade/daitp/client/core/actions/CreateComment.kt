package com.uade.daitp.client.core.actions

import com.uade.daitp.client.core.model.CommentIntent
import com.uade.daitp.client.core.repository.ClientRepository

class CreateComment(private val clientRepository: ClientRepository) {

    suspend operator fun invoke(commentIntent: CommentIntent) {
        clientRepository.createComment(commentIntent)
    }
}
