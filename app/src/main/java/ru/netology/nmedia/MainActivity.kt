package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.likes.setOnClickListener { binding.likes.setImageResource(R.drawable.ic_liked_24) }

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
            likesnumber.text = post.likes.toString()
            repostsnumber.text = post.repostsN.toString()

            likes.setOnClickListener {
            if(post.likedByMe) post.likes-- else post.likes++
                post.likedByMe = !post.likedByMe
                likes.setImageResource(if (post.likedByMe)R.drawable.ic_liked_24 else R.drawable.ic_like_24)
                likesnumber.text = post.likes.toString()
            }
            reposts.setOnClickListener {
                if(post.repostByMe) post.repostsN++
                post.repostByMe = !post.repostByMe
                repostsnumber.text = post.repostsN.toString()



            }
        }
    }
}



