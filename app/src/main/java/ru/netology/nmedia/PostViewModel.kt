package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post

class PostViewModel : ViewModel() {

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> = _post

    init {
        _post.value = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likes = 10,
            likedByMe = false,
            repostsN = 15,
            repostByMe = false
        )
    }

    fun like() {
        val currentPost = _post.value ?: return
        _post.value = currentPost.copy(
            likes = if (currentPost.likedByMe) currentPost.likes - 1 else currentPost.likes + 1,
            likedByMe = !currentPost.likedByMe
        )
    }

    fun repost() {
        val currentPost = _post.value ?: return
        _post.value = currentPost.copy(
            repostsN = if (currentPost.repostByMe) currentPost.repostsN - 1 else currentPost.repostsN + 1,
            repostByMe = !currentPost.repostByMe
        )
    }
}
