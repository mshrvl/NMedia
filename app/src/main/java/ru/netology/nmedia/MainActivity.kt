package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likes = 10,
            likedByMe = false,
            repostsN = 15,
            repostByMe = false
        )
        with(binding) {

            author.text = post.author
            published.text = post.published
            content.text = post.content

            likesnumber.text = formatNumber(post.likes)
            repostsnumber.text = formatNumber(post.repostsN)

            likes.setOnClickListener {
                if (post.likedByMe) post.likes-- else post.likes++
                post.likedByMe = !post.likedByMe
                likes.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24)
                likesnumber.text = post.likes.toString()
                updateLikes(post, likes, likesnumber)
            }
            reposts.setOnClickListener {
                if (post.repostByMe) post.repostsN++
                post.repostByMe = !post.repostByMe
                repostsnumber.text = post.repostsN.toString()
                updateReposts(post, repostsnumber)


            }

        }
    }

    private fun updateLikes(post: Post, likeButton: ImageButton, likesNumberView: TextView) {
        val formattedLikes = formatNumber(post.likes)
        likesNumberView.text = formattedLikes
        likeButton.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24)
    }

    private fun updateReposts(post: Post, repostsNumberView: TextView) {
        val formattedReposts = formatNumber(post.repostsN)
        repostsNumberView.text = formattedReposts
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





