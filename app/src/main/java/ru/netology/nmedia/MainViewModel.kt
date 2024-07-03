package ru.netology.nmedia

import androidx.lifecycle.ViewModel

class MainViewModel(private val repository: PostRepository = PostRepositoryInMemoryImpl()): ViewModel() {
    val data = repository.getPosts()
    fun like(id: Long) = repository.likeById(id)
    fun repost(id: Long) = repository.repost(id)
}