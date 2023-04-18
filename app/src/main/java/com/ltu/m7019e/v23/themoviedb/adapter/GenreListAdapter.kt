package com.ltu.m7019e.v23.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ltu.m7019e.v23.themoviedb.R
import com.ltu.m7019e.v23.themoviedb.model.Genre

//From: Android docs recycler view example
//url: developer.android.com/develop/ui/views/layout/recyclerview#kotlin
class GenreListAdapter(private val dataSet: List<Genre>) : RecyclerView.Adapter<GenreListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView : TextView

        init {
            textView = view.findViewById(R.id.genre_list_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataSet[position].name
    }

    override fun getItemCount() = dataSet.size


}