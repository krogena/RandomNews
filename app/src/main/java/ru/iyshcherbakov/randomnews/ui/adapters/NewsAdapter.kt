package ru.iyshcherbakov.randomnews.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.iyshcherbakov.randomnews.R
import ru.iyshcherbakov.randomnews.models.Article

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.Holder> (){
    inner class Holder(view: View) : RecyclerView.ViewHolder(view){
        val image: ImageView = itemView.findViewById(R.id.article_image)
        val title: TextView = itemView.findViewById(R.id.article_title)
        val date: TextView = itemView.findViewById(R.id.article_date)
    }

    private val callback = object: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(holder.image)
            holder.image.clipToOutline = true
            holder.title.text = article.title
            holder.date.text = article.publishedAt
        }
    }
    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit){
        onItemClickListener = listener
    }

}