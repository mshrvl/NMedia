package ru.netology.nmedia

import androidx.lifecycle.ViewModel

class MainViewModel(private val repository: PostRepository = PostRepositoryInMemoryImpl()): ViewModel() {
    fun data() = repository.get()
    fun like() = repository.like()
    fun repost() = repository.repost()
}