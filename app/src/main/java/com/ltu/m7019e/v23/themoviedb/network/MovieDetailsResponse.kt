package com.ltu.m7019e.v23.themoviedb.network

import com.ltu.m7019e.v23.themoviedb.model.Genre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieDetailsResponse {
    @Json(name = "genres")
    var genres: List<Genre> = listOf()

    @Json(name = "homepage")
    var homePage: String = ""

    @Json(name = "imdb_id")
    var imdb_id: String = ""
}