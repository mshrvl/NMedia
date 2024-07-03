package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun repost()
}

class PostRepositoryInMemoryImpl : PostRepository {
    private val data = MutableLiveData(
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likes = 10,
            likedByMe = false,
            repostsN = 15,
            repostByMe = false
        )
    )

    override fun get(): LiveData<Post> = data

    override fun like() {
        data.value?.let { post ->
            val newLikesCount = if (post.likedByMe) post.likes - 1 else post.likes + 1
            data.value = post.copy(likedByMe = !post.likedByMe, likes = newLikesCount)
        }
    }

    override fun repost() {
        data.value?.let { post ->
             data.value = post.copy(repostsN = post.repostsN + 1)
        }
    }
}