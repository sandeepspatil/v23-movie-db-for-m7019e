package com.ltu.m7019e.v23.themoviedb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ltu.m7019e.v23.themoviedb.adapter.GenreListAdapter
import androidx.navigation.fragment.findNavController
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabase
import com.ltu.m7019e.v23.themoviedb.database.MovieDatabaseDao
import com.ltu.m7019e.v23.themoviedb.databinding.FragmentMovieDetailBinding
import com.ltu.m7019e.v23.themoviedb.model.Genre
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.DataFetchStatus
import com.ltu.m7019e.v23.themoviedb.utils.Constants
import com.ltu.m7019e.v23.themoviedb.viewmodel.MovieDetailViewModel
import com.ltu.m7019e.v23.themoviedb.viewmodel.MovieDetailViewModelFactory

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieDetailFragment : Fragment() {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var viewModelFactory: MovieDetailViewModelFactory

    private lateinit var movieDatabaseDao: MovieDatabaseDao

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie : Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        movie = MovieDetailFragmentArgs.fromBundle(requireArguments()).movie
        binding.movie = movie

        val genreListAdapter = GenreListAdapter()
        binding.genreListRv.adapter = genreListAdapter

        val application = requireNotNull(this.activity).application
        movieDatabaseDao = MovieDatabase.getInstance(application).movieDatabaseDao

        viewModelFactory = MovieDetailViewModelFactory(movieDatabaseDao, application, movie)
        viewModel = ViewModelProvider(this, viewModelFactory)[MovieDetailViewModel::class.java]

        viewModel.movie.observe(viewLifecycleOwner) { mv ->
            movie = mv
            binding.movie = mv
            mv.genres?.let {
                genreListAdapter.submitList(it)
            }
        }

        viewModel.dataFetchStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (status) {
                    DataFetchStatus.LOADING -> {
                        binding.visitImdb.visibility = View.GONE
                        binding.visitMovieSite.visibility = View.GONE
                    }
                    DataFetchStatus.ERROR -> {
                        binding.movieDetailTitle.text = getString(R.string.movie_detail_api_error)
                    }
                    DataFetchStatus.DONE -> {
                        if(movie.homePage != ""){
                            binding.visitMovieSite.visibility = View.VISIBLE
                        }
                        if(movie.imdb_id != ""){
                            binding.visitImdb.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.visitMovieSite.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(binding.movie?.homePage)))
        }

        binding.visitImdb.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.IMDB_BASE_URL + binding.movie?.imdb_id)))
        }

        binding.backToMovieList.setOnClickListener {
            findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieListFragment())
        }

        binding.toThirdFragment.setOnClickListener {
            findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToThirdFragment(movie))
        }
    }

}