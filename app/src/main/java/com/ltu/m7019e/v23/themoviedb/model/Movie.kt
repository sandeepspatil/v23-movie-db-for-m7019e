package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class Movie(
        @PrimaryKey()
        @Json(name = "id")
        var id: Long = 0L,

        @ColumnInfo(name = "title")
        @Json(name = "title")
        var title: String,

        @ColumnInfo(name = "genres")
        @Json(name = "genres")
        var genres: List<Genre>? = null,

        @ColumnInfo(name = "poster_path")
        @Json(name = "poster_path")
        var posterPath: String,

        @ColumnInfo(name = "backdrop_path")
        @Json(name = "backdrop_path")
        var backdropPath: String,

        @ColumnInfo(name = "release_date")
        @Json(name = "release_date")
        var releaseDate: String,

        @ColumnInfo(name = "homepage")
        @Json(name = "homepage")
        var homePage: String? = null, //TODO

        @ColumnInfo(name = "imdb_id")
        @Json(name = "imdb_id")
        var imdb_id: String? = null, //TODO

        @ColumnInfo(name = "overview")
        @Json(name = "overview")
        var overview: String
) : Parcelable

@Parcelize
data class Genre(
        @Json(name = "id")
        val id: Int,

        @Json(name = "name")
        val name: String,
) : Parcelable


class Converters {

        @TypeConverter
        fun fromGenresToJSON(genres: List<Genre>): String {
                return Gson().toJson(genres)
        }
        @TypeConverter
        fun fromJSONToGenres(json: String): List<Genre> {
                return Gson().fromJson(json,Array<Genre>::class.java).asList()
        }
}