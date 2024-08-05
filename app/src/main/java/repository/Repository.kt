package repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {

    fun getPosts(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun repost(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
    fun edit(post: Post)
    fun video()
}

class PostRepositoryInMemoryImpl : PostRepository {
    private val data = MutableLiveData(
        listOf(
            Post(
                id = 1,
                author = "Нетология. Университет интернет-профессий будущего",
                published = "21 мая в 18:36",
                content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                likes = 0,
                likedByMe = false,
                repostsN = 2131,
                repostByMe = false,
                video = null
            ),
            Post(
                id = 2,
                author = "Нетология",
                published = "24 мая в 18:36",
                content = "В посте мы разобрали проблемы современного глобального потепления",
                likes = 179,
                likedByMe = false,
                repostsN = 73,
                repostByMe = false,
                video = "https://www.youtube.com/watch?v=arUctP5yAYc"

            ),
            Post(
                id = 3,
                author = "ЦифраЛаба",
                published = "21 мая в 13:42",
                content = "Всем привет, Виталя лучший ментор по андроид разработке",
                likes = 182,
                likedByMe = false,
                repostsN = 51,
                repostByMe = false,
                video = null
            ),
            Post(
                id = 4,
                author = "Нетология. Университет интернет-профессий будущего",
                published = "21 мая в 18:36",
                content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                likes = 10,
                likedByMe = false,
                repostsN = 15,
                repostByMe = false,
                video = null
            ),
            Post(
                id = 5,
                author = "Нетология",
                published = "24 мая в 18:36",
                content = "Всем привет, меня зовут Лололошка",
                likes = 132,
                likedByMe = false,
                repostsN = 73,
                repostByMe = false,
                video = null
            ),
            Post(
                id = 6,
                author = "ЦифраЛаба",
                published = "21 мая в 13:42",
                content = "Всем привет, Виталя, Леха и Даня лучшие менторы по андроид разработке",
                likes = 182,
                likedByMe = false,
                repostsN = 51,
                repostByMe = false,
                video = null
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

    override fun repost(id: Long) {
        data.value?.let { posts ->
            val newList = posts.map { post ->
                if (id == post.id) {
                    val newRepostsCount = post.repostsN + 1
                    post.copy(repostsN = newRepostsCount)
                } else {
                    post
                }
            }
            data.value = newList
        }
    }

    override fun removeById(id: Long) {
        data.value = data.value?.filter { it.id != id }
    }

    override fun save(post: Post) {
        data.value?.let {
            val lastId = it.maxOfOrNull { it.id } ?: 0
            data.value = listOf(post.copy(
                id = lastId + 1,
                author = "You",
                published = "few seconds ago"
            )) + it
        }

    }

    override fun edit(post: Post) {
        data.value?.let { it ->
            data.value = it.map {
                if (it.id == post.id) post else it
            }
        }
    }

    override fun video() {
        data.value

    }
}





