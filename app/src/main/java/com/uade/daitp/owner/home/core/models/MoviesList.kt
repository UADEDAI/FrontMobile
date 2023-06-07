package com.uade.daitp.owner.home.core.models

data class MoviesList(
    val showing: MutableList<Movie>,
    val comingSoon: MutableList<Movie>,
    val showingPagination: Pagination,
    val comingSoonPagination: Pagination,
)

fun emptyMovieList(): MoviesList = MoviesList(
    emptyList<Movie>().toMutableList(),
    emptyList<Movie>().toMutableList(),
    Pagination(
        1,
        0,
        1,
        0,
    ),
    Pagination(
        1,
        0,
        1,
        0,
    )
)