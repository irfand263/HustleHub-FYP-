package com.example.hustlehubs

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class Hustlers : AppCompatActivity() {

    private lateinit var eTPhoneNum: EditText
    private lateinit var eTName: EditText
    private lateinit var eTEmail: EditText
    private lateinit var spinnerGender: Spinner
    private lateinit var spinnerServices: Spinner
    private lateinit var spinnerArea: Spinner
    private lateinit var eTAdditionalComments: EditText
    private lateinit var imgbackBtn4: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hustlers)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        eTPhoneNum = findViewById(R.id.eTPhoneNum)
        eTName = findViewById(R.id.eTName)
        eTEmail = findViewById(R.id.eTEmail)
        spinnerGender = findViewById(R.id.spinnerGender)
        spinnerServices = findViewById(R.id.spinnerServices)
        spinnerArea = findViewById(R.id.spinnerArea)
        eTAdditionalComments = findViewById(R.id.eTAdditionalComments)
        imgbackBtn4 = findViewById(R.id.imgBackBtn4)

        imgbackBtn4.setOnClickListener {
            Log.d("HustlersForm", "Back button clicked")
            finish()
        }


        val genderAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        )
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = genderAdapter

        val servicesAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.services_array,
            android.R.layout.simple_spinner_item
        )
        servicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerServices.adapter = servicesAdapter

        val areaAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.area_array,
            android.R.layout.simple_spinner_item
        )
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerArea.adapter = areaAdapter

        val submitButton = findViewById<Button>(R.id.button)
        submitButton.setOnClickListener {
            if (fieldsAreFilled() && isValidEmail(eTEmail.text.toString()) && isValidPhoneNumber(eTPhoneNum.text.toString())) {
                submitFormData()
            } else {
                Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun fieldsAreFilled(): Boolean {
        return eTPhoneNum.text.isNotEmpty() &&
                eTName.text.isNotEmpty() &&
                eTEmail.text.isNotEmpty() &&
                spinnerGender.selectedItemPosition != Spinner.INVALID_POSITION &&
                spinnerServices.selectedItemPosition != Spinner.INVALID_POSITION &&
                spinnerArea.selectedItemPosition != Spinner.INVALID_POSITION &&
                eTAdditionalComments.text.isNotEmpty()
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val pattern = Pattern.compile(emailRegex)
        return pattern.matcher(email).matches()
    }

    private fun isValidPhoneNumber(phone: String): Boolean {
        val phoneRegex = "^[+]?[0-9]{10,13}$"
        val pattern = Pattern.compile(phoneRegex)
        return pattern.matcher(phone).matches()
    }

    private fun submitFormData() {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Hustlers")

        val formData = HashMap<String, Any>()
        formData["phone_number"] = eTPhoneNum.text.toString()
        formData["name"] = eTName.text.toString()
        formData["email"] = eTEmail.text.toString()
        formData["gender"] = spinnerGender.selectedItem.toString()
        formData["service"] = spinnerServices.selectedItem.toString()
        formData["area"] = spinnerArea.selectedItem.toString()
        formData["additional_comments"] = eTAdditionalComments.text.toString()

        reference.push().setValue(formData)
            .addOnSuccessListener {
                Toast.makeText(this, "Now wait for the admin to approve and email you", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to submit data", Toast.LENGTH_SHORT).show()
            }
    }
}
