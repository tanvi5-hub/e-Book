package com.example.ebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.example.ebook.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        val roleSpinner = findViewById<Spinner>(R.id.roleDropdown)
        val roles = arrayOf("Reader", "Author")
        val arrayAdapter = ArrayAdapter(this@SignInActivity, android.R.layout.simple_spinner_dropdown_item, roles)
        roleSpinner.adapter = arrayAdapter
        var selectedRole = ""

        roleSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedRole = roles[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, firebaseAuth.currentUser?.uid.toString(), Toast.LENGTH_LONG).show()
                        if(selectedRole == "Author") {

                            val intent = Intent(this, AuthorHomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(this, ReaderHomeActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onStart() {
        super.onStart()
    }
}