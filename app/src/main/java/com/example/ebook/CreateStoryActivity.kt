package com.example.ebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class CreateStoryActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_story)

        val storyTitleEt = findViewById<EditText>(R.id.createStoryTitleEt)
        val storyDescriptionEt = findViewById<EditText>(R.id.createStoryDescriptionEt)
        val createFirstPageButton = findViewById<Button>(R.id.createFirstPageBtn)

        database = FirebaseDatabase.getInstance()

        createFirstPageButton.setOnClickListener {

            val storyTitle = storyTitleEt.text.toString()
            val storyDescription = storyDescriptionEt.text.toString()

            if(storyTitle.isEmpty()) {
                Toast.makeText(this, "Title is empty", Toast.LENGTH_SHORT).show()
            }

            if(storyDescription.isEmpty()) {
                Toast.makeText(this, "Description is empty", Toast.LENGTH_SHORT).show()
            }

            val storyId = "1"
            val storyRef = database.reference.child("story").child(storyId!!)

            val storyData = HashMap<String, Any>()
            storyData["story_title"] = storyTitle
            storyData["story_description"] = storyDescription
            storyRef.setValue(storyData).addOnCompleteListener { dbTask ->
                if(dbTask.isSuccessful) {
                    Toast.makeText(this, "Story added successfully.", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, StoryFirstPageActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Failed to create story data", Toast.LENGTH_LONG).show()
                }
            }

        }


    }
}