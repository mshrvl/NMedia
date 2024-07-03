package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private val adapter = PostsAdapter(
        onLikeClick = { viewModel.like(it.id) },
        onRepostClick = { viewModel.repost(it.id) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val manager = LinearLayoutManager(this)
        binding.postsList.adapter = adapter
        binding.postsList.layoutManager = manager
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }
}






