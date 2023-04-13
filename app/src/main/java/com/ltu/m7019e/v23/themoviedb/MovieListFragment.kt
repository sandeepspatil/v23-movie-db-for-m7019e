package com.ltu.m7019e.v23.themoviedb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.ltu.m7019e.v23.themoviedb.database.Movies
import com.ltu.m7019e.v23.themoviedb.databinding.FragmentMovieListBinding
import com.ltu.m7019e.v23.themoviedb.databinding.MovieListItemBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null;
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        val movies = Movies()

        movies.list.forEach { movie ->
            val movieListItemBinding: MovieListItemBinding = DataBindingUtil.inflate(inflater, R.layout.movie_list_item, container, false);
            movieListItemBinding.movie = movie

            binding.movieListLl.addView(movieListItemBinding.root)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movies = Movies()
//
//        val movieList = view.findViewById<LinearLayout>(R.id.movie_list_ll)
//        val movieItem = movieList.findViewById<View>(R.id.movie1)
//        val movieTitle = movieItem.findViewById<TextView>(R.id.movie_title)
//        val moviePoster = movieItem.findViewById<ImageView>(R.id.movie_poster)
//
//        movieTitle.text = movies.list[0].title
//        Glide
//            .with(this)
//            .load(Contants.POSTER_IMAGE_BASE_URL + Contants.POSTER_IMAGE_WIDTH + movies.list[0].posterPath)
//            .into(moviePoster);


//        view.findViewById<Button>(R.id.button_first).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }
}