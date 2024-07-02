package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getPosts(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun repost()
}

class PostRepositoryInMemoryImpl : PostRepository {
    private val data = MutableLiveData(
        listOf(
            Post(
                id = 1,
                author = "Нетология. Университет интернет-профессий будущего",
                published = "21 мая в 18:36",
                content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                likes = 10,
                likedByMe = false,
                repostsN = 15,
                repostByMe = false
            ),
            Post(
                id = 2,
                author = "Нетология",
                published = "24 мая в 18:36",
                content = "Всем привет, меня зовут Лололошка",
                likes = 132,
                likedByMe = false,
                repostsN = 73,
                repostByMe = false
            ),
            Post(
                id = 3,
                author = "ЦифраЛаба",
                published = "21 мая в 13:42",
                content = "Всем привет, Виталя лучший ментор по андроид разработке",
                likes = 182,
                likedByMe = false,
                repostsN = 51,
                repostByMe = false
            ),
                    Post(
                    id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likes = 10,
            likedByMe = false,
            repostsN = 15,
            repostByMe = false
        ),
        Post(
            id = 2,
            author = "Нетология",
            published = "24 мая в 18:36",
            content = "Всем привет, меня зовут Лололошка",
            likes = 132,
            likedByMe = false,
            repostsN = 73,
            repostByMe = false
        ),
        Post(
            id = 3,
            author = "ЦифраЛаба",
            published = "21 мая в 13:42",
            content = "Всем привет, Виталя, Леха и Даня лучшие менторы по андроид разработке",
            likes = 182,
            likedByMe = false,
            repostsN = 51,
            repostByMe = false
        )
        )
    )

    override fun getPosts(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        data.value?.let { posts ->
            val newList = posts.map { post ->
                if (id == post.id) {
                    val newLikesCount = if (post.likedByMe) post.likes - 1 else post.likes + 1
                    post.copy(likes = newLikesCount, likedByMe = !post.likedByMe)
                } else {
                    post
                }
            }
            data.value = newList
        }
    }

    override fun repost() {
        //data.value?.let { posts ->
        //data.value = post.copy(repostsN = post.repostsN + 1)
    }
}