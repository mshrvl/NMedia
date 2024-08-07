package ru.netology.nmedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import repository.PostRepository
import repository.PostRepositoryFileImpl
import ru.netology.nmedia.dto.Post

class MainViewModel(application: Application) :
    AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryFileImpl(application)
    val data = repository.getPosts()
    fun like(id: Long) = repository.likeById(id)
    fun removeById(id: Long) = repository.removeById(id)
    val edited = MutableLiveData(empty)


    fun save(content: String?) {
        edited.value?.let {
            if (it.id == 0L) {
                repository.save(it.copy(content = content!!))
            } else {
                repository.edit(it.copy(content = content!!))
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

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun video() = repository.video()


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
    video = null
)