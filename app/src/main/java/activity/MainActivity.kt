package activity

import adapter.OnInteractionListener
import adapter.PostsAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.netology.nmedia.MainViewModel
import ru.netology.nmedia.R
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

        override fun onShare(post: Post) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }
            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.group.visibility = View.GONE
        binding.fab?.visibility = View.VISIBLE
        val manager = LinearLayoutManager(this)

        binding.postsList.adapter = adapter
        binding.postsList.layoutManager = manager
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
       val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save(result)
        }
        binding.fab?.setOnClickListener {
            newPostLauncher.launch()
        }

        viewModel.edited.observe(this@MainActivity) { post ->
            if (post.id == 0L) {
                binding.group.visibility = View.GONE
                binding.fab?.visibility = View.VISIBLE
                return@observe
            }
            binding.fab?.visibility = View.GONE
            binding.group.visibility = View.VISIBLE
            with(binding.textField) {
                requestFocus()
                setText(post.content)
            }
        }

        binding.save.setOnClickListener {
            with(binding.textField) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Поле не может быть пустым",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.save(text?.toString() ?: "")
                this.let { editText ->
                    editText.setText("")
                    editText.clearFocus()
                    this@MainActivity.hideKeyboard(editText)
                    binding.group.visibility = View.GONE
                    binding.fab?.visibility = View.VISIBLE

                }
            }

        }
        binding.cancelChange.setOnClickListener {
            hideKeyboard(binding.root)
            binding.textField.setText("")
            binding.textField.clearFocus()
            binding.group.visibility = View.GONE
            binding.fab?.visibility = View.VISIBLE
            viewModel.cancelEdit()
        }

    }

}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}







