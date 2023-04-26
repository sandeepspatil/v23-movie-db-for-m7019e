package com.ltu.m7019e.v23.themoviedb.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.MovieDetailsResponse
import com.ltu.m7019e.v23.themoviedb.network.MovieResponse
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieDatabaseDao: MovieDatabaseDao,
    application: Application,
    movie: Movie
) : AndroidViewModel(application){

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() {
            return _movie
        }

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() {
            return _isFavorite
        }

    init {
        _dataFetchStatus.value = DataFetchStatus.LOADING
        setIsFavorite(movie)
        getMovieDetails(movie)
    }

    private fun setIsFavorite(movie: Movie) {
        viewModelScope.launch {
            _isFavorite.value = movieDatabaseDao.isFavorite(movie.id)
        }
    }


    fun onSaveMovieButtonClicked(movie: Movie) {
        viewModelScope.launch {
            movieDatabaseDao.insert(movie)
            setIsFavorite(movie)
        }
    }

    fun onRemoveMovieButtonClicked(movie: Movie) {
        viewModelScope.launch {
            movieDatabaseDao.delete(movie)
            setIsFavorite(movie)
        }
    }

    private fun getMovieDetails(movie: Movie) {

            viewModelScope.launch {
                try {
                    val movieDetailsResponse: MovieDetailsResponse =
                        TMDBApi.movieListRetrofitService.getMovieDetails(movie.id)

                    _movie.value = Movie(
                        movie.id,
                        movie.title,
                        movieDetailsResponse.genres,
                        movie.posterPath,
                        movie.backdropPath,
                        movie.releaseDate,
                        movieDetailsResponse.homePage,
                        movieDetailsResponse.imdb_id,
                        movie.overview
                    )


                    /*
                    _movie.value = movie
                    _movie.value!!.genres = movieDetailsResponse.genres
                    _movie.value!!.homePage = movieDetailsResponse.homePage
                    _movie.value!!.imdb_id = movieDetailsResponse.imdb_id
                     */

                    _dataFetchStatus.value = DataFetchStatus.DONE
                } catch (e: Exception) {
                    _dataFetchStatus.value = DataFetchStatus.ERROR
                    _movie.value = movie
                    _movie.value?.title = e.message.toString()
                }

        }
    }
}