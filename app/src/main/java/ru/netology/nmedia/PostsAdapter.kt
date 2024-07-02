package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.dto.Post

class PostViewHolder(val view: PostBinding) : RecyclerView.ViewHolder(view.root) {
    fun bind(post: Post) {
        view.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likesnumber.text = formatNumber(post.likes)
            repostsnumber.text = formatNumber(post.repostsN)
            likes.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24)
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

class PostsAdapter : RecyclerView.Adapter<PostViewHolder>() {
    var list = emptyList<Post>(
    )
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.bind(post)
    }
}