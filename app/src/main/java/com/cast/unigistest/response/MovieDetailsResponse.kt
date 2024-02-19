package com.cast.unigistest.response

import com.cast.unigistest.models.Genre

data class MovieDetailsResponse(
    val id: Int,
    val title: String,
    val overview: String,
    val genres: List<Genre>,
    val release_date: String,
)