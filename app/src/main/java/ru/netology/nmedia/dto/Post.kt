package ru.netology.nmedia.dto



data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likes: Int = 0,
    val likedByMe: Boolean,
    val repostsN: Int,
    val repostByMe: Boolean,
    val video: String? = null

)