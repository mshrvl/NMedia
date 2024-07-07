package ru.netology.nmedia

import androidx.lifecycle.*
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.PostRepository
import ru.netology.nmedia.PostRepositoryInMemoryImpl

class MainViewModel(private val repository: PostRepository = PostRepositoryInMemoryImpl()): ViewModel() {
    val data = repository.getPosts()
    fun like(id: Long) = repository.likeById(id)
    fun repost(id: Long) = repository.repost(id)
    fun removeById(id: Long) = repository.removeById(id)
}

var empty = Post(
    id = 0,
    content = "",
    author = "",
    likes = 0,
    likedByMe = false,
    published = "",
    repostsN = 0,
    repostByMe = false,
)