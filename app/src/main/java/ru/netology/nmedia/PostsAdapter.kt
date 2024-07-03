package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.dto.Post

class PostViewHolder(val view: PostBinding, val onLikeListener: OnLikeListener, val onRepostListener: OnRepostListener) : RecyclerView.ViewHolder(view.root) {
    fun bind(post: Post) {
        view.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likesnumber.text = formatNumber(post.likes)
            repostsnumber.text = formatNumber(post.repostsN)
            likes.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24)
            likes.setOnClickListener {
                onLikeListener(post)
            }
            reposts.setOnClickListener {
                onRepostListener(post)
            }
        }
    }

    private fun formatNumber(number: Int): String {
        return when {
            number >= 1_000 && number < 10_000 -> "${number / 1_000}.${(number / 100) % 10}K"
            number >= 10_000 && number < 100_000 -> "${number / 1_000}K"
            number >= 100_000 && number < 1_000_000 -> "${number / 10_000}.${(number % 10_000) / 1_000}M"
            else -> number.toString()
        }
    }
}
typealias OnLikeListener = (post: Post) -> Unit
typealias OnRepostListener = (post: Post) -> Unit

class PostsAdapter(private val onLikeClick: OnLikeListener, private val onRepostClick: OnRepostListener): ListAdapter<Post, PostViewHolder>(PostRepositoryInMemoryImpl.PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeClick, onRepostClick)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}
