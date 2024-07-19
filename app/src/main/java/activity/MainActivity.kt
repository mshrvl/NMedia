package activity

import adapter.OnInteractionListener
import adapter.PostsAdapter
import android.content.Intent
import android.os.Bundle
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

    private val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
        result ?: return@registerForActivityResult
        viewModel.changeContent(result)
        viewModel.save(result)
    }

    private val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
        result ?: return@registerForActivityResult
        viewModel.changeContent(result)
        viewModel.save(result)
    }

    private val adapter = PostsAdapter(onInteractionListener = object : OnInteractionListener {
        override fun onLike(post: Post) {
            viewModel.like(post.id)
        }

        override fun onEdit(post: Post) {
            viewModel.edit(post)
            editPostLauncher.launch(post.content)

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

        val manager = LinearLayoutManager(this)

        binding.postsList.adapter = adapter
        binding.postsList.layoutManager = manager
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.fab?.setOnClickListener {
            newPostLauncher.launch()
        }
        val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save(result)
        }

        viewModel.edited.observe(this) {
            if (it.id == 0L) {
                return@observe
            }
            editPostLauncher.launch(it.content)
        }
    }
}




