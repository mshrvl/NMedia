package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.dto.Post


interface OnInteractionListener {
    fun onLike(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onShare(post: Post)
}

class PostViewHolder(
    val view: PostBinding,
    val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(view.root) {
    fun bind(post: Post) {
        view.apply {
            author.text = post.author
            published.text = post.published
            textField.text = post.content
            likes.text = formatNumber(post.likes)
            reposts.text = formatNumber(post.repostsN)
            likes.isChecked = post.likedByMe
            reposts.isChecked = post.repostByMe

            likes.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            reposts.setOnClickListener {
                onInteractionListener.onShare(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit_content -> {
                                onInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
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
} // возможно надо немножко отрефачить :(

class PostsAdapter(
    val onInteractionListener: OnInteractionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view = binding, onInteractionListener = onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

