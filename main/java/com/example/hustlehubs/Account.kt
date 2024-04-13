package com.example.hustlehubs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class Account : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var profileImageView: ImageView

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let { selectedImageUri ->
                val userId = auth.currentUser?.uid
                userId?.let { uid ->
                    database.child("Users").child(uid).child("profileImageUri").setValue(selectedImageUri.toString())
                        .addOnSuccessListener {
                            profileImageView.setImageURI(selectedImageUri)
                            Toast.makeText(this, "Profile image updated", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to update profile image", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance().reference

        val userId = auth.currentUser?.uid

        userId?.let { uid ->
            database.child("Users").child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val username = snapshot.child("username").getValue(String::class.java)
                    val phoneNumber = snapshot.child("phoneNumber").getValue(String::class.java)
                    val email = snapshot.child("email").getValue(String::class.java)

                    findViewById<TextView>(R.id.usernameTextView).text = username
                    findViewById<TextView>(R.id.phoneNumberTextView).text = phoneNumber
                    findViewById<TextView>(R.id.emailTextView).text = email
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Account, "Database error occurred: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        profileImageView = findViewById(R.id.profileImageView)
        profileImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            pickImageLauncher.launch(intent)
        }

        findViewById<ImageButton>(R.id.imgBckBtn).setOnClickListener {
            finish()
        }

        findViewById<ImageButton>(R.id.imgEditAccount).setOnClickListener {
            val intent = Intent(this, EditAccount::class.java)
            startActivity(intent)
        }
    }
}
