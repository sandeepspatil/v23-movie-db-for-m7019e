package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
        var id: Int,
        var title: String,
        var genres: List<Genre>, //TODO
        var posterPath: String,
        var backdropPath: String,
        var releaseDate: String,
        var homePage: String, //TODO
        var imdb_id: String,
        var overview: String,
) : Parcelable