package com.example.telehealth

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class DoctorDetails : AppCompatActivity() {

    lateinit var btnDatePicker: Button
    lateinit var btnTimePicker: Button
    lateinit var btnBookAppointment: Button
    lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_details)

        // Initialize views
        btnDatePicker = findViewById(R.id.btnDatePicker)
        btnTimePicker = findViewById(R.id.btnTimePicker)
        btnBookAppointment = findViewById(R.id.btnBookAppointment)
        calendar = Calendar.getInstance()

        // Date Picker click listener
        btnDatePicker.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    Toast.makeText(this, "Selected Date: $dayOfMonth/${month + 1}/$year", Toast.LENGTH_SHORT).show()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        // Time Picker click listener
        btnTimePicker.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                this,
                { _: TimePicker, hourOfDay: Int, minute: Int ->
                    Toast.makeText(this, "Selected Time: $hourOfDay:$minute", Toast.LENGTH_SHORT).show()
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        }

        // Book Appointment button click listener
        btnBookAppointment.setOnClickListener {
            Toast.makeText(this, "Appointment Booked!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }


    }
}
