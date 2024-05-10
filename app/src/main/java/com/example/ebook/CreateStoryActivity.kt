package com.example.ebook

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreateStoryActivity : AppCompatActivity() {
    private lateinit var storyTitle: EditText
    private lateinit var storyDescription: EditText
    private lateinit var storyCategory: EditText
    private lateinit var createStoryButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_story)

        storyTitle = findViewById(R.id.storyTitle)
        storyDescription = findViewById(R.id.storyDescription)
        storyCategory = findViewById(R.id.storyCategory)
        createStoryButton = findViewById(R.id.createStoryButton)

        createStoryButton.setOnClickListener {
            val name = storyTitle.text.toString().trim()
            val description = storyDescription.text.toString().trim()
            val category = storyCategory.text.toString().trim()
            createStory(name, description, category)
        }
    }

    private fun createStory(name: String, description: String, category: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val storyId = FirebaseDatabase.getInstance().getReference("stories").push().key

        if (storyId != null && userId != null) {
            val story = NewStory(storyId, userId, name, description, category)
            FirebaseDatabase.getInstance().getReference("stories/$storyId").setValue(story)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Story created successfully", Toast.LENGTH_SHORT).show()
                        // Navigate to Create First Page
                        val intent = Intent(this, CreatePageActivity::class.java)
                        intent.putExtra("STORY_ID", storyId)
                        intent.putExtra("IS_FIRST_PAGE", true)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Failed to create story", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}

data class NewStory(val id: String, val authorId: String, val name: String, val description: String, val category: String)
