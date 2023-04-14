package com.ltu.m7019e.v23.themoviedb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ltu.m7019e.v23.themoviedb.databinding.FragmentMovieDetailBinding
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.utils.GenreParser

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailBinding.inflate(inflater)
        movie = MovieDetailFragmentArgs.fromBundle(requireArguments()).movie
        binding.movie = movie

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backToMovieList.setOnClickListener {
            findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieListFragment())
        }

        binding.thirdFragBtn.setOnClickListener {
            findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToThree())
        }

        //Created a recycler view for the genres
        recyclerView = binding.genreList
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = GenreParser(movie.genres)

        //Create explicit intent with an url
        binding.url.setOnClickListener(){
            val queryUrl: Uri = Uri.parse("${movie.URL}")
            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
            context?.startActivity(intent)
        }

        binding.urlImdb.setOnClickListener(){
            val queryUrl: Uri = Uri.parse("https://www.imdb.com/title/${movie.IMDB_id}")
            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
            context?.startActivity(intent)
        }

    }
}