package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        findViewById<ImageButton>(R.id.likes).setOnClickListener {
            (it as ImageButton).setImageResource(R.drawable.ic_liked_24)
            findViewById<ImageButton>(R.id.likes).setOnClickListener {
                (it as ImageButton).setImageResource(R.drawable.ic_like_24)
            }
                }
            }
        }
class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        findViewById<ImageButton>(R.id.likes).setOnClickListener {
            (it as ImageButton).setImageResource(R.drawable.ic_like_24)
        }

    }
}