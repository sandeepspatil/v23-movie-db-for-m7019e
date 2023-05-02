package com.ltu.m7019e.v23.themoviedb.data

import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.model.Review
import com.ltu.m7019e.v23.themoviedb.model.Video
import com.ltu.m7019e.v23.themoviedb.network.*

interface TMDBRepository {
    suspend fun getPopularMovies(): List<Movie>

    suspend fun getTopRatedMovies(): List<Movie>

    suspend fun getMovieDetails(target: Movie): Movie

    suspend fun getMovieReviews(movieId: Long): List<Review>

    suspend fun getMovieVideos(movieId: Long): List<Video>
}

class NetworkTMDBRepository(private val tmdbApiService: TMDBApiService) : TMDBRepository {
    override suspend fun getPopularMovies(): List<Movie> {
        return tmdbApiService.getPopularMovies().results
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        return tmdbApiService.getTopRatedMovies().results
    }

    override suspend fun getMovieDetails(target: Movie): Movie {
        val res = tmdbApiService.getMovieDetails(target.id)
        target.genres = res.genres
        target.imdb_id = res.imdb_id
        target.homePage = res.homePage

        return target
    }

    override suspend fun getMovieReviews(movieId: Long): List<Review> {
        return tmdbApiService.getMovieReviews(movieId).results
    }

    override suspend fun getMovieVideos(movieId: Long): List<Video> {
        return tmdbApiService.getMovieVideos(movieId).results
    }
}