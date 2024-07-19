package activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.MainViewModel
import ru.netology.nmedia.databinding.ActivityEditPostBinding

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        val viewModel: MainViewModel by viewModels()
        setContentView(binding.root)

        val postText = intent.getStringExtra(Intent.EXTRA_TEXT)
        binding.postText.setText(postText)


        binding.save.setOnClickListener {
            val newText = binding.postText.text.toString()
            with(binding.postText) {
                if (text.isNullOrBlank()) {
                    android.widget.Toast.makeText(
                        this@EditPostActivity,
                        "Поле не может быть пустым",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.save(text?.toString() ?: "")
                this.let { editText ->
                    editText.setText("")
                    editText.clearFocus()
                    this@EditPostActivity.hideKeyboard(editText)
                }
                val resultIntent = Intent().apply {
                    putExtra(Intent.EXTRA_TEXT, newText)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

        }
        binding.cancelChange.setOnClickListener {
            hideKeyboard(binding.root)
            binding.postText.setText("")
            binding.postText.clearFocus()
            //binding.group.visibility = View.GONE
            viewModel.cancelEdit()
            finish()
        }

    }
    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

