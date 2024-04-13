package com.example.hustlehubs

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hustlehubs.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.registerRedirect.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }

    private fun loginUser() {
        val username = binding.loginUsername.text.toString()
        val password = binding.loginPassword.text.toString()

        val usersRef = FirebaseDatabase.getInstance().getReference("Users")

        usersRef.orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (userSnapshot in dataSnapshot.children) {
                            val email = userSnapshot.child("email").getValue(String::class.java)

                            if (email != null) {
                                auth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(this@Login) { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(this@Login, "Login Successful", Toast.LENGTH_SHORT).show()
                                            startActivity(Intent(this@Login, Home::class.java))
                                            finish()
                                        } else {
                                            Toast.makeText(this@Login, "Login Failed. Please try again.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            }
                        }
                    } else {
                        Toast.makeText(this@Login, "Username not found. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@Login, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

}
