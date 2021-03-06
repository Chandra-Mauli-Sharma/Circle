package com.example.circle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.circle.Models.Memes
import com.example.circle.R

class MemeAdapter(private val dataSet:ArrayList<Memes>): RecyclerView.Adapter<MemeAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView
        var textView: TextView

        init {
            imageView=view.findViewById(R.id.imageMeme)
            textView=view.findViewById(R.id.memeTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meme_display, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meme=dataSet[position]

        holder.textView.text=meme.title
        Glide.with(holder.itemView.context)
            .load(meme.image)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int =dataSet.size
}