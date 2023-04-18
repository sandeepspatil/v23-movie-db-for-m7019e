package com.ltu.m7019e.v23.themoviedb.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
        var id: Int,
        var name: String,
) : Parcelable