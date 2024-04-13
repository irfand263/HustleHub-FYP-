package com.example.hustlehubs

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Booking : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var bookingReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_booking)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        database = FirebaseDatabase.getInstance()
        bookingReference = database.getReference("Bookings")

        val bookButton = findViewById<Button>(R.id.bookButton)
        bookButton.setOnClickListener {
            bookNow()
        }

        val specialistSpinner = findViewById<Spinner>(R.id.specialistSpinner)
        val specialistAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.services_specialist_array,
            android.R.layout.simple_spinner_item
        )
        specialistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        specialistSpinner.adapter = specialistAdapter

        val paymentMethodSpinner = findViewById<Spinner>(R.id.paymentMethodSpinner)
        val paymentAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.payment_array,
            android.R.layout.simple_spinner_item
        )
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        paymentMethodSpinner.adapter = paymentAdapter

        configureDurationPicker()
    }

    private fun configureDurationPicker() {
        val durationPicker = findViewById<NumberPicker>(R.id.durationPicker)
        durationPicker.minValue = 1
        durationPicker.maxValue = 24
    }

    private fun bookNow() {

        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val year = datePicker.year
        val month = datePicker.month
        val dayOfMonth = datePicker.dayOfMonth

        val durationPicker = findViewById<NumberPicker>(R.id.durationPicker)
        val durationInHours = durationPicker.value

        val specialistSpinner = findViewById<Spinner>(R.id.specialistSpinner)
        val selectedSpecialist = specialistSpinner.selectedItem.toString()

        val paymentMethodSpinner = findViewById<Spinner>(R.id.paymentMethodSpinner)
        val selectedPaymentMethod = paymentMethodSpinner.selectedItem.toString()

        val bookingData = BookingData(
            date = "$year-$month-$dayOfMonth",
            duration = durationInHours,
            specialist = selectedSpecialist,
            paymentMethod = selectedPaymentMethod
        )

        val bookingKey = bookingReference.push().key

        bookingKey?.let {
            bookingReference.child(it).setValue(bookingData)
                .addOnSuccessListener {
                    Toast.makeText(
                        this@Booking,
                        "Transaction Successful. Admin will process your session.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this@Booking,
                        "Transaction failed. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}
