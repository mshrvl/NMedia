package repository


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositoryFileImpl(private val context: Context) : PostRepository {
    private val gson = Gson()
    private val typeToken = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)


    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, typeToken)
                data.value = posts
            }
        } else
            sync()
    }


    override fun getPosts(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (id == post.id) {
                val newLikesCount = if (post.likedByMe) post.likes - 1 else post.likes + 1
                post.copy(likes = newLikesCount, likedByMe = !post.likedByMe)
            } else {
                post
            }
        }
        data.value = posts
        sync()
    }

    override fun repost(id: Long) {
        posts = posts.map { post ->
            if (id == post.id) {
                val newRepostsCount = post.repostsN + 1
                post.copy(repostsN = newRepostsCount)
            } else {
                post
            }
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        val lastId = posts.maxOfOrNull { it.id } ?: 0
        posts = listOf(
            post.copy(
                id = lastId + 1,
                author = "You",
                published = "few seconds ago"
            )
        ) + posts
        data.value = posts
        sync()
    }

    override fun edit(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
        data.value = posts
        sync()
    }


    override fun video() {
        data.value

    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }
}






