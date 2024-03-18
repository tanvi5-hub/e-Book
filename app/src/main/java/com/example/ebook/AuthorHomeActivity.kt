package com.example.ebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AuthorHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_home)

        val createStoryButton = findViewById<FloatingActionButton>(R.id.createStoryBtn)

        createStoryButton.setOnClickListener {
            val intent = Intent(this, CreateStoryActivity::class.java)
            startActivity(intent)
        }

    }
}