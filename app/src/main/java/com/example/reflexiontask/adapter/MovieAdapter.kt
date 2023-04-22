package com.example.reflexiontask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.example.reflexiontask.R
import com.example.reflexiontask.model.Movie
import com.example.reflexiontask.model.MovieData
import java.util.UUID

class MovieAdapter : ListAdapter<Movie, MovieAdapter.MyViewHolder>(DiffCallback) {
    lateinit var context: Context
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.img_movie)
        val title: TextView = itemView.findViewById(R.id.title)
        val rating: TextView = itemView.findViewById(R.id.rating)
        val relese_date: TextView = itemView.findViewById(R.id.release_date)
        val genre_1: TextView = itemView.findViewById(R.id.genre_1)
        val genre_2: TextView = itemView.findViewById(R.id.genre_2)
        val genre_3: TextView = itemView.findViewById(R.id.genre_3)

        fun bind(data: Movie) {
            title.text = data.Title
            rating.text = String.format("%s/10", data.Rating)
            relese_date.text = data.Year
            val genre = data.Genres.split("|")
            genre.let {
                if(it.isNotEmpty()){
                    genre_1.text = it[0]
                    genre_1.visibility = View.VISIBLE
                }
                if ( it.size >= 2){
                    genre_2.text = it[1]
                    genre_2.visibility = View.VISIBLE
                }

                if(it.size >= 3){
                    genre_3.text = it[2]
                    genre_3.visibility = View.VISIBLE
                }
            }
            Glide.with(context)
                .load(data.MoviePoster)
                .placeholder(R.drawable.ic_movie)
                .signature(ObjectKey(data.IMDBID))
                .into(img)
                .clearOnDetach()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.IMDBID == newItem.IMDBID
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return newItem == oldItem
        }
    }
}