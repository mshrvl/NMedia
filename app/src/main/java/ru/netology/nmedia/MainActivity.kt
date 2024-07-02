package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()

    private val adapter = PostsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val maanager = LinearLayoutManager(this)
        binding.postsList!!.adapter = adapter
        binding.postsList.layoutManager = maanager
        viewModel.data.observe(this) { post ->
            adapter.list = post
        }

    }

    fun setPost(post: List<Post>, binding: ActivityMainBinding) {
        with(binding) {

            likes?.setOnClickListener {
                viewModel.like()
//            if (post.likedByMe) post.likes-- else post.likes++
//            post.likedByMe = !post.likedByMe
//            likesnumber.text = post.likes.toString()
//            updateLikes(post, likes, likesnumber)
            }
            reposts?.setOnClickListener {
                viewModel.repost()
//                if (post.repostByMe) post.repostsN++
//                post.repostByMe = !post.repostByMe
//                repostsnumber.text = post.repostsN.toString()
//                updateReposts(post, repostsnumber)


            }
        }

    }
}







