package com.example.ebook

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class ProfileActivity : ComponentActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference
    private lateinit var currentUser: FirebaseAuth
    private lateinit var storage: FirebaseStorage

    private lateinit var profileImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var bioEditText: EditText
    private lateinit var saveProfileButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        database = FirebaseDatabase.getInstance()
        userRef = database.getReference("user")
        currentUser = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()

        profileImageView = findViewById(R.id.profileImageView)
        nameTextView = findViewById(R.id.nameTextView)
        emailTextView = findViewById(R.id.emailTextView)
        bioEditText = findViewById(R.id.bioEditText)
        saveProfileButton = findViewById(R.id.saveProfileButton)

        val userId = currentUser.currentUser?.uid

        userId?.let { id ->
            userRef.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val name = dataSnapshot.child("name").getValue(String::class.java) ?: ""
                    val email = dataSnapshot.child("email").getValue(String::class.java) ?: ""
                    val bio = dataSnapshot.child("bio").getValue(String::class.java) ?: ""
                    val photoUrl = dataSnapshot.child("photoUrl").getValue(String::class.java) ?: ""

                    nameTextView.text = name
                    emailTextView.text = email
                    bioEditText.setText(bio)

                    Log.d("ProfileActivity", "Photo URL: $photoUrl")

                    Glide.with(this@ProfileActivity)
                        .load(photoUrl.ifEmpty { null })
                        .placeholder(R.drawable.ic_profile_default)
                        .error(R.drawable.ic_profile_default)
                        .into(profileImageView)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle database error
                }
            })
        }

        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val storageRef = storage.reference.child("profile_pictures/${userId}.jpg")
                val uploadTask = storageRef.putFile(uri)
                uploadTask.addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        userRef.child(userId!!).child("photoUrl").setValue(downloadUri.toString())
                        Glide.with(this).load(downloadUri).placeholder(R.drawable.ic_profile_default).error(R.drawable.ic_profile_default).into(profileImageView)
                    }
                }
            }
        }

        profileImageView.setOnClickListener {
            getContent.launch("image/*")
        }

        saveProfileButton.setOnClickListener {
            val newBio = bioEditText.text.toString()
            userRef.child(userId!!).child("bio").setValue(newBio)
            Toast.makeText(this, "Details saved successfully", Toast.LENGTH_SHORT).show()
        }
    }
}
