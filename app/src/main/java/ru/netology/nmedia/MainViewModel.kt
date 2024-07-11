package ru.netology.nmedia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post

class MainViewModel(private val repository: PostRepository = PostRepositoryInMemoryImpl()) :
    ViewModel() {
    val data = repository.getPosts()
    fun like(id: Long) = repository.likeById(id)
    fun repost(id: Long) = repository.repost(id)
    fun removeById(id: Long) = repository.removeById(id)
    val edited = MutableLiveData(empty)

    fun save(content: String) {
        edited.value?.let {
            if (it.id == 0L) {
                repository.save(it.copy(content = content))
            } else {
                repository.edit(it.copy(content = content))
            }
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun cancelEdit() {
        edited.value = empty
    }
}


val empty = Post(
    id = 0,
    content = "",
    author = "",
    likes = 0,
    likedByMe = false,
    published = "",
    repostsN = 0,
    repostByMe = false,
)