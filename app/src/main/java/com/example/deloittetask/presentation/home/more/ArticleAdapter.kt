package com.example.deloittetask.presentation.home.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.deloittetask.R
import com.example.deloittetask.data.model.Articles
import com.example.deloittetask.databinding.ArticleRowItemBinding
import com.example.deloittetask.util.loadImage

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var articles: List<Articles.Article?> = emptyList()

    fun submitList(newList: List<Articles.Article?>) {
        val diffCallback = ArticleDiffCallback(articles, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        articles = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            ArticleRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        article?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = articles.size

    class ArticleViewHolder(private val binding: ArticleRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Articles.Article) {
            binding.apply {
                article.media?.firstOrNull()?.mediaMetadata?.firstOrNull()?.url?.let {
                    imageView.loadImage(
                        it
                    )
                }
                textViewTitle.text = article.title
                textViewDate.text = article.publishedDate
            }
        }
    }

    private class ArticleDiffCallback(
        private val oldList: List<Articles.Article?>, private val newList: List<Articles.Article?>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition]?.id == newList[newItemPosition]?.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
