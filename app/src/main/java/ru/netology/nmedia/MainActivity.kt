package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()

    private val adapter = PostsAdapter(
        onLikeListener = {viewModel.like(it.id)},
        onRepostListener = {viewModel.repost(it.id)}
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val manager = LinearLayoutManager(this)
        binding.postsList.adapter = adapter
        binding.postsList.layoutManager = manager
        viewModel.data.observe(this) { post ->
            adapter.submitList(post)
        }

    }

}






