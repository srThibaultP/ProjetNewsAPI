package io.github.srthibaultp.projetnewsapi.ihm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import io.github.srthibaultp.projetnewsapi.R
import io.github.srthibaultp.projetnewsapi.data.network.modele.Article
import io.github.srthibaultp.projetnewsapi.databinding.ListItemArticleBinding

class NewsAdapter(private val onArticleClickListener: OnArticleClickListener) :
    PagingDataAdapter<Article, NewsAdapter.NewsViewHolder>(ARTICLE_COMPARATOR)
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder
    {
        val binding = ListItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int)
    {
        val currentArticle: Article? = getItem(position)
        if (currentArticle != null) holder.bindData(currentArticle)
    }

    fun getCurrentItem(position: Int) = getItem(position)

    inner class NewsViewHolder(private val binding: ListItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)
    {
        init
        {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION)
                {
                    val currentArticle = getItem(position)
                    if (currentArticle != null) onArticleClickListener.onArticleClicked(currentArticle)
                }
            }
        }

        fun bindData(article: Article)
        {
            //fonction de mise en forme des données pour l'affichage sur l'ide client
            binding.apply {
                tvArticleTitle.text = article.title
                tvArticleSource.text = article.source?.name
                tvArticleDescription.text = article.description
                //Changement du format de la date affiché dans un format plus agréable à lire
                tvArticlePublishedAt.text = article.publishedAt?.replace("T"," à ")?.replace("Z","")
                loadImage(article)
            }
        }

        private fun ListItemArticleBinding.loadImage(article: Article)
        {
            Glide.with(this.root)
                .load(article.urlToImage)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(ivArticleImage)
        }
    }


    companion object
    {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>()
        {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean { return oldItem.url == oldItem.url }
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean { return oldItem == newItem }
        }
    }

    interface OnArticleClickListener { fun onArticleClicked(article: Article) }
}