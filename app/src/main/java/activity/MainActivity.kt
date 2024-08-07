package activity

import adapter.OnInteractionListener
import adapter.PostsAdapter
import android.content.Intent
import android.net.Uri
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
        viewModel.save(result)
    }

    private val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
        if (result != null) {
            viewModel.changeContent(result)
            viewModel.save(result)
        }
        viewModel.cancelEdit()

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


        override fun onPlayVideo(post: Post) {
            viewModel.video()
            val videoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
            startActivity(videoIntent)
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

        viewModel.edited.observe(this) {
            if (it.id == 0L) {
                return@observe
            }
        }
    }
}




