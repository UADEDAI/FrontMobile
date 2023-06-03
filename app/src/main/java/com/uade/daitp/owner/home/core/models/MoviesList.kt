package com.uade.daitp.owner.home.core.models

data class MoviesList(
    val showing: MutableList<Movie>,
    val comingSoon: MutableList<Movie>,
    val showingPagination: Pagination,
    val comingSoonPagination: Pagination,
)