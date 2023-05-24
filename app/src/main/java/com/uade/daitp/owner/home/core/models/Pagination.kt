package com.uade.daitp.owner.home.core.models

data class Pagination(
    val page: Int,
    val limit: Int,
    val totalPages: Int,
    val totalResults: Int
)