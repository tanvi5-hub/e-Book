package com.example.ebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AuthorHomeActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var storiesRef: DatabaseReference
    private lateinit var currentUser: FirebaseAuth

    private lateinit var storiesLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_home)




        setContentView(R.layout.activity_author_home)

        database = FirebaseDatabase.getInstance()
        storiesRef = database.getReference("stories")
        currentUser = FirebaseAuth.getInstance()

        storiesLayout = findViewById(R.id.stories_layout)

        // Get the current user's authorId
        val authorId = currentUser.currentUser?.uid

        // Query stories where authorId matches the logged-in user's authorId
        val query = storiesRef.orderByChild("authorId").equalTo(authorId)

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Clear the existing stories
                storiesLayout.removeAllViews()

                // Iterate through each story and display it
                for (storySnapshot in dataSnapshot.children) {
                    val name = storySnapshot.child("name").getValue(String::class.java) ?: ""
                    val category = storySnapshot.child("category").getValue(String::class.java) ?: ""
                    val description = storySnapshot.child("description").getValue(String::class.java) ?: ""

                    // Create a TextView to display the story details
                    val storyTextView = TextView(this@AuthorHomeActivity)
                    storyTextView.text = "Name: $name\nCategory: $category\nDescription: $description\n"
                    storiesLayout.addView(storyTextView)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
                TODO("Handle database error")
            }
        })

        val createStoryButton = findViewById<FloatingActionButton>(R.id.createStoryBtn)

        createStoryButton.setOnClickListener {
            val intent = Intent(this, CreateStoryActivity::class.java)
            startActivity(intent)
        }

    }
}