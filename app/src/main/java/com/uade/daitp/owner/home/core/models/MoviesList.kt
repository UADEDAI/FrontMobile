package com.uade.daitp.owner.home.core.models

data class MoviesList(
    val showing: List<Movie>,
    val comingSoon: List<Movie>,
    val showingPagination: Pagination,
    val comingSoonPagination: Pagination,
)