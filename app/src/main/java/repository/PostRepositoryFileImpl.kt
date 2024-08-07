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
            sync()
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
            sync()
        }
    }

    override fun removeById(id: Long) {
        data.value = data.value?.filter { it.id != id }
        sync()
    }

    override fun save(post: Post) {
        data.value?.let {
            val lastId = it.maxOfOrNull { it.id } ?: 0
            data.value = listOf(
                post.copy(
                    id = lastId + 1,
                    author = "You",
                    published = "few seconds ago"
                )
            ) + it
            sync()
        }

    }

    override fun edit(post: Post) {
        data.value?.let { it ->
            data.value = it.map {
                if (it.id == post.id) post else it
            }
            sync()
        }
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






