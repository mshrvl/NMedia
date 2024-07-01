package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.data().observe(this) { post ->
            setPost(post, binding)
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

    fun setPost(post: Post, binding: ActivityMainBinding) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24)

            likesnumber.text = formatNumber(post.likes)
            repostsnumber.text = formatNumber(post.repostsN)

            likes.setOnClickListener {
                viewModel.like()
//            if (post.likedByMe) post.likes-- else post.likes++
//            post.likedByMe = !post.likedByMe
//            likesnumber.text = post.likes.toString()
//            updateLikes(post, likes, likesnumber)
            }
            reposts.setOnClickListener {
                viewModel.repost()
//                if (post.repostByMe) post.repostsN++
//                post.repostByMe = !post.repostByMe
//                repostsnumber.text = post.repostsN.toString()
//                updateReposts(post, repostsnumber)


            }
        }

    }
}







