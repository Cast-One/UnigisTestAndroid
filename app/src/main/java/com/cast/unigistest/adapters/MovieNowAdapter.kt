package com.cast.unigistest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cast.unigistest.R
import com.cast.unigistest.databinding.ItemMovieNowBinding
import com.cast.unigistest.models.MovieModel
import com.squareup.picasso.Picasso

class MovieNowAdapter (private var context: Context, private var list: ArrayList<MovieModel>):
    RecyclerView.Adapter<MovieNowAdapter.ViewHolder>() {

    class ViewHolder(var bind: ItemMovieNowBinding): RecyclerView.ViewHolder(bind.root)
    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater  = LayoutInflater.from(parent.context)
        val bind = ItemMovieNowBinding.inflate(inflater,parent,false)


        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val movie: MovieModel = list[position]
        val imagePath = movie.poster_path

        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500$imagePath")
            .placeholder(R.drawable.img_placeholder)
            .into(holder.bind.imageMovie)

//        holder.bind.root.setOnClickListener {
//            goToDetail(newsObject)
//        }
    }

}