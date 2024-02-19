package com.cast.unigistest.response

import com.cast.unigistest.models.DateRange
import com.cast.unigistest.models.MovieModel

data class MovieResponse(
    val dates: DateRange,
    val page: Int,
    val results: List<MovieModel>,
    val total_pages: Int,
    val total_results: Int
)
