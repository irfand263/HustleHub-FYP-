package com.example.hustlehubs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TraditionalMades : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_traditional_mades)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        val bookNowButton = findViewById<Button>(R.id.bookNowButtonTraditionalMades1)
        bookNowButton.setOnClickListener {
            val intent = Intent(this, Booking::class.java)
            startActivity(intent)
        }

        val bookNowButton2 = findViewById<Button>(R.id.bookNowButtonTraditionalMades2)
        bookNowButton2.setOnClickListener {
            val intent = Intent(this, Booking::class.java)
            startActivity(intent)
        }

        val bookNowButton3 = findViewById<Button>(R.id.bookNowButtonTraditionalMades3)
        bookNowButton3.setOnClickListener {
            val intent = Intent(this, Booking::class.java)
            startActivity(intent)
        }
    }
}