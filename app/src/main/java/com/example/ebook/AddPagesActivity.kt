package com.example.ebook

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddPagesActivity : AppCompatActivity() {
    private lateinit var storyPagesLabel: TextView
    private lateinit var pagesListView: ListView
    private lateinit var storyId: String
    private lateinit var pagesAdapter: NewPageAdapter
    private val pages = mutableListOf<NewPage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pages)

        storyId = intent.getStringExtra("STORY_ID") ?: ""

        storyPagesLabel = findViewById(R.id.storyPagesLabel)
        pagesListView = findViewById(R.id.pagesListView)
        pagesAdapter = NewPageAdapter(this, pages)
        pagesListView.adapter = pagesAdapter

        pagesListView.setOnItemClickListener { parent, view, position, id ->
            val selectedPage = pages[position]
            val intent = Intent(this, CreatePageActivity::class.java)
            intent.putExtra("STORY_ID", storyId)
            intent.putExtra("PAGE_ID", selectedPage.id)
            startActivity(intent)
        }

        // Load and display pages for this story
        loadPages()

        // Set up the ComposeView for the back button and app name
        findViewById<ComposeView>(R.id.composeView).setContent {
            MaterialTheme {
                Surface(color = Color.Transparent) {
                    BackButtonWithAppName()
                }
            }
        }
    }

    private fun loadPages() {
        val pagesRef = FirebaseDatabase.getInstance().getReference("stories/$storyId/pages")
        pagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pages.clear()
                for (dataSnapshot in snapshot.children) {
                    val page = dataSnapshot.getValue(NewPage::class.java)
                    if (page != null) {
                        pages.add(page)
                    }
                }
                pagesAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    @Composable
    fun BackButtonWithAppName() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                val intent = Intent(this@AddPagesActivity, AuthorHomeActivity::class.java)
                startActivity(intent)
                finish()
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Back Home",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun BackButtonWithAppNamePreview() {
        MaterialTheme {
            BackButtonWithAppName()
        }
    }
}
