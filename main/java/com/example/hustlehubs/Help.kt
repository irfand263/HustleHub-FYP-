package com.example.hustlehubs

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Help : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_help)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val howItWorksTextView = findViewById<TextView>(R.id.howItWorksTextView)
        val explanationTextView = findViewById<TextView>(R.id.explanationTextView)
        val howItWorksTextView2 = findViewById<TextView>(R.id.howItWorksTextView2)
        val explanationTextView2 = findViewById<TextView>(R.id.explanationTextView2)
        val howItWorksTextView3 = findViewById<TextView>(R.id.howItWorksTextView3)
        val explanationTextView3 = findViewById<TextView>(R.id.explanationTextView3)
        val howItWorksTextView4 = findViewById<TextView>(R.id.howItWorksTextView4)
        val explanationTextView4 = findViewById<TextView>(R.id.explanationTextView4)
        val backButton = findViewById<ImageButton>(R.id.imgBckBtn3)

        howItWorksTextView.setOnClickListener {

            explanationTextView.visibility = if (explanationTextView.visibility == android.view.View.VISIBLE) {
                android.view.View.GONE
            } else {
                android.view.View.VISIBLE
            }
        }

        howItWorksTextView2.setOnClickListener {

            explanationTextView2.visibility = if (explanationTextView2.visibility == android.view.View.VISIBLE) {
                android.view.View.GONE
            } else {
                android.view.View.VISIBLE
            }
        }

        howItWorksTextView3.setOnClickListener {

            explanationTextView3.visibility = if (explanationTextView3.visibility == android.view.View.VISIBLE) {
                android.view.View.GONE
            } else {
                android.view.View.VISIBLE
            }
        }

        howItWorksTextView4.setOnClickListener {

            explanationTextView4.visibility = if (explanationTextView4.visibility == android.view.View.VISIBLE) {
                android.view.View.GONE
            } else {
                android.view.View.VISIBLE
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}
