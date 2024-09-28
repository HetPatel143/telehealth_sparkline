package com.example.telehealth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {

    lateinit var btnPayNow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // Initialize Pay Now button
        btnPayNow = findViewById(R.id.btnPayNow)

        // Pay Now button click listener
        btnPayNow.setOnClickListener {
            Toast.makeText(this, "Payment Confirmed!", Toast.LENGTH_SHORT).show()
        }
    }
}
