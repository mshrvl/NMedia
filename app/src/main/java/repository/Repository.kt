package repository

import androidx.lifecycle.LiveData
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






