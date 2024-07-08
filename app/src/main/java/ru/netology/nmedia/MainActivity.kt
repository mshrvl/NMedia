package ru.netology.nmedia

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private val adapter = PostsAdapter(onInteractionListener = object : OnInteractionListener {
        override fun onLike(post: Post) {
            viewModel.like(post.id)
        }

        override fun onEdit(post: Post) {
            viewModel.edit(post)
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
        }

        override fun onRepost(post: Post) {
            viewModel.repost(post.id)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val manager = LinearLayoutManager(this)
        binding.postsList.adapter = adapter
        binding.postsList.layoutManager = manager

        viewModel.data.observe(this) { posts ->
            Log.d("checkposts", posts.filter { it.id == 1L }.toString())
            adapter.submitList(posts)
        }
        viewModel.edited.observe(this@MainActivity) { post ->
            if (post.id == 0L) {
                return@observe
            }
            with(binding.content!!) {
                requestFocus()
                setText(post.content)
            }
        }
        binding.save?.setOnClickListener {
            with(binding.content) {
                if (this?.text.isNullOrBlank() == true) {
                    Toast.makeText(
                        this@MainActivity,
                        "Поле не может быть пустым",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(this?.text.toString())
                viewModel.save(this?.text?.toString() ?: "")
                this?.let { editText ->
                    editText.setText("")
                    editText.clearFocus()
                    this@MainActivity.hideKeyboard(editText)
                }

            }
        }
    }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}





