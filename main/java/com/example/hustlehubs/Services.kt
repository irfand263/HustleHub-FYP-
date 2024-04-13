package com.example.hustlehubs

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Services : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_services)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageButton>(R.id.btnCarRepair).setOnClickListener {
            startActivity(Intent(this, CarRepair::class.java))
        }

        findViewById<ImageButton>(R.id.btnTechnician).setOnClickListener {
            startActivity(Intent(this, Technician::class.java))
        }

        findViewById<ImageButton>(R.id.btnCleaning).setOnClickListener {
            startActivity(Intent(this, Cleaning::class.java))
        }

        findViewById<ImageButton>(R.id.btnHeavyFoods).setOnClickListener {
            startActivity(Intent(this, HeavyFoods::class.java))
        }

        findViewById<ImageButton>(R.id.btnTraditionalMades).setOnClickListener {
            startActivity(Intent(this, TraditionalMades::class.java))
        }

        findViewById<ImageButton>(R.id.imgBckBtn2).setOnClickListener {
            finish()
        }
    }
}
