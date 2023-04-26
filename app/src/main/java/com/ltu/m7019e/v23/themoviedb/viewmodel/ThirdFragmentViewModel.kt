package com.ltu.m7019e.v23.themoviedb.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.model.Review
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.network.MovieResponse
import com.ltu.m7019e.v23.themoviedb.network.ReviewResponse
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import kotlinx.coroutines.launch

class ThirdFragmentViewModel(private val movieDatabaseDao: MovieDatabaseDao,
                             application: Application,
                             movie: Movie) : AndroidViewModel(application) {

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>>
        get() {
            return _reviews
        }

    init {
        _dataFetchStatus.value = DataFetchStatus.LOADING
        getReviews(movie)
    }

    fun getReviews(movie: Movie){
        viewModelScope.launch {
            try {
                val reviewResponse: ReviewResponse =
                    TMDBApi.movieListRetrofitService.getMovieReviews(movie.id)
                _reviews.value = reviewResponse.results
                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception) {
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _reviews.value = arrayListOf(Review("","Error",e.message.toString(),"Error","Error","Error"))
            }
        }
    }

}