package ru.netology.nmedia

import androidx.lifecycle.ViewModel

class MainViewModel(private val repository: PostRepository = PostRepositoryInMemoryImpl()): ViewModel() {
    val data = repository.getPosts()
    fun like() = repository.likeById(id = 1)
    fun repost() = repository.repost()
}