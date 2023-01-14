package com.example.newsfeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(private val listener:NewsClicked): RecyclerView.Adapter<NewsViewHolder>() {
    private val items:ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder=NewsViewHolder(view)
        view.setOnClickListener {
            listener.clickonNews(items[viewHolder.adapterPosition])
        }
        view.findViewById<ImageButton>(R.id.imageButton).setOnClickListener{
            listener.shareClick(view.findViewById<ImageView>(R.id.image),items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val current=items[position]
        holder.titleview.text=current.title
        holder.author.text=current.source["name"].toString()
        //holder.author.text=current.author
        Glide.with(holder.itemView.context).load(current.imageUrl).into(holder.imageView)
        holder.dateandtime.text=current.publishedAt
        //holder.content.text=current.content
    }


    fun updatenews(updatenews:ArrayList<News>){
        items.clear()
        items.addAll(updatenews)
        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleview:TextView = itemView.findViewById(R.id.tittle)
    val imageView:ImageView = itemView.findViewById(R.id.image)
    val author:TextView = itemView.findViewById(R.id.author)
    val dateandtime:TextView = itemView.findViewById(R.id.date)
}
interface NewsClicked{
    fun clickonNews(item : News){

    }

    fun shareClick(imageView: ImageView,item:News)
}