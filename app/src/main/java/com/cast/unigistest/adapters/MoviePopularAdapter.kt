package com.cast.unigistest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cast.unigistest.R
import com.cast.unigistest.databinding.ItemMostPopularBinding
import com.cast.unigistest.models.MovieModel
import com.squareup.picasso.Picasso

class MoviePopularAdapter(private var context: Context, private var list: ArrayList<MovieModel>):
    RecyclerView.Adapter<MoviePopularAdapter.ViewHolder>() {

    class ViewHolder(var bind: ItemMostPopularBinding): RecyclerView.ViewHolder(bind.root)
    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater  = LayoutInflater.from(parent.context)
        val bind = ItemMostPopularBinding.inflate(inflater,parent,false)

        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val movie: MovieModel = list[position]
        val imagePath = movie.poster_path

        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500$imagePath")
            .placeholder(R.drawable.img_placeholder)
            .into(holder.bind.imageMovie)

        holder.bind.textTitle.text = movie.title
        holder.bind.textDate.text = movie.release_date
        holder.bind.textLanguage.text = movie.original_language
        holder.bind.circularProgress.setProgress(movie.vote_average*10, 100.0)

        if (movie.vote_average <= 5){
            holder.bind.circularProgress.progressColor = context.getColor(R.color.yellow)
        }else{
            holder.bind.circularProgress.progressColor = context.getColor(R.color.green)
        }
    }
}