package com.example.ebook

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateStoryActivity : AppCompatActivity(), TextWatcher {

    private var currentPageNumber = 1
    private lateinit var editTextStoryName: EditText
    private lateinit var editTextStoryDescription: EditText
    private lateinit var buttonAddPage: Button
    private lateinit var buttonSaveStory: Button
    private lateinit var database: FirebaseDatabase
    private lateinit var storyCategoryName: String

    private val pages: MutableList<Page> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_story)

        editTextStoryName = findViewById(R.id.editTextStoryName)
        editTextStoryDescription = findViewById(R.id.editTextStoryDescription)
        storyCategoryName = ""
        val storyCategorySpinner = findViewById<Spinner>(R.id.storyCategoryDropdown)
        val storyCategories = arrayOf("Fiction", "Action", "Romantic", "Horror")
        val arrayAdapter = ArrayAdapter(this@CreateStoryActivity, android.R.layout.simple_spinner_dropdown_item, storyCategories)
        storyCategorySpinner.adapter = arrayAdapter
        buttonAddPage = findViewById(R.id.buttonAddPage)
        buttonSaveStory = findViewById(R.id.buttonSaveStory)
        database = FirebaseDatabase.getInstance()

        storyCategorySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                storyCategoryName = storyCategories[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        buttonAddPage.setOnClickListener {
            addPage()
        }

        buttonSaveStory.setOnClickListener {
            saveStory()
        }
    }

    private fun addPage(parentPage: Page? = null) {
        val pageId = "page$currentPageNumber"
        val page = Page(pageId, "", mutableListOf(), parentPage?.id)
        parentPage?.choices?.add(Choice("Page $currentPageNumber", pageId))
        pages.add(page)
        currentPageNumber++

        val layoutPages = findViewById<LinearLayout>(R.id.layoutPages)

        val pageLayout = LinearLayout(this)
        pageLayout.orientation = LinearLayout.VERTICAL
        pageLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val editTextPageText = EditText(this)
        editTextPageText.hint = "Enter Page Text"
        pageLayout.addView(editTextPageText)

        if (pages.size > 1) {
            val addNestedPageButton = Button(this)
            addNestedPageButton.text = "Add Nested Page"
            addNestedPageButton.setOnClickListener {
                addPage(page)
            }
            pageLayout.addView(addNestedPageButton)
        } else {
            buttonAddPage.text = "Add Another Page"
            buttonAddPage.setOnClickListener {
                addPage(page)
            }
        }

        layoutPages.addView(pageLayout)

        editTextPageText.addTextChangedListener(object : TextWatcher {
            val currentPage = page

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                currentPage.text = s.toString()
            }
        })
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        var currentPage = pages.lastOrNull()
        currentPage?.text = s.toString()
    }

    private fun saveStory() {
        val storyName = editTextStoryName.text.toString()
        val storyDescription = editTextStoryDescription.text.toString()
        val storyCategory = storyCategoryName

        val currentUser = FirebaseAuth.getInstance().currentUser
        val authorId = currentUser?.uid

        if (storyName.isNotEmpty() && storyDescription.isNotEmpty() && storyCategory.isNotEmpty() && authorId != null) {
            val storyRef = database.reference.child("stories").push()
            val storyData = HashMap<String, Any>()
            storyData["name"] = storyName
            storyData["description"] = storyDescription
            storyData["category"] = storyCategory
            storyData["authorId"] = authorId // Add authorId to story data
            storyRef.setValue(storyData).addOnCompleteListener { storyTask ->
                if (storyTask.isSuccessful) {
                    // Story created successfully, now add Pages
                    savePages(storyRef, pages.filter { it.parentPageId == null }, pages)
                    Toast.makeText(this, "Story created successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to create Story", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun savePages(
        parentRef: DatabaseReference,
        pagesToSave: List<Page>,
        allPages: List<Page>
    ) {
        for (page in pagesToSave) {
            val pageRef = parentRef.child("pages").push()
            val pageData = HashMap<String, Any>()
            pageData["text"] = page.text
            val choicesData = HashMap<String, Any>()
            for ((index, choice) in page.choices.withIndex()) {
                choicesData["choice$index"] = choice.text
                val destinationPageId = choice.destinationPageId
                val destinationPage = allPages.find { it.id == destinationPageId }
                if (destinationPage != null) {
                    choicesData["destination$index"] = destinationPageId
                } else {
                    // Handle error: destination page not found
                }
            }
            pageData["choices"] = choicesData
            pageRef.setValue(pageData).addOnCompleteListener { pageTask ->
                if (!pageTask.isSuccessful) {
                    // Handle error while saving page
                }
            }
            val nestedPages = allPages.filter { it.parentPageId == page.id }
            savePages(pageRef, nestedPages, allPages)
        }
    }
}