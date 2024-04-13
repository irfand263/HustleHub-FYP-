package com.example.hustlehubs

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hustlehubs.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        binding.imgBtnSignOut.setOnClickListener {
            signOutAndNavigateToMainActivity()
        }

        binding.imgBtnAccount.setOnClickListener {
            navigateToAccount()
        }

        binding.imgBtnServices.setOnClickListener {
            navigateToServices()
        }

        binding.imgBtnHelp.setOnClickListener {
            navigateToHelp()
        }

        binding.imgBtnHustlers.setOnClickListener {
            navigateToHustlers()
        }
    }

    private fun signOutAndNavigateToMainActivity() {
        sharedPreferences.edit().clear().apply()

        Toast.makeText(this, "You have signed out", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun navigateToAccount() {
        val intent = Intent(this, Account::class.java)
        startActivity(intent)
    }

    private fun navigateToServices() {
        val intent = Intent(this, Services::class.java)
        startActivity(intent)
    }

    private fun navigateToHelp() {
        val intent = Intent(this, Help::class.java)
        startActivity(intent)
    }

    private fun navigateToHustlers() {
        val intent = Intent(this, Hustlers::class.java)
        startActivity(intent)
    }
}

