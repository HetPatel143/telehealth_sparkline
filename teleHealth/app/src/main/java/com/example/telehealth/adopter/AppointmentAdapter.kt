package com.example.telehealth.adopter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.telehealth.R
import com.example.telehealth.data.Appointment

class AppointmentAdapter(private val appointments: List<Appointment>) : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    class AppointmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val doctorImage: ImageView = view.findViewById(R.id.doctor_image)
        val doctorName: TextView = view.findViewById(R.id.doctor_name)
        val doctorSpecialty: TextView = view.findViewById(R.id.doctor_specialty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rvappoinmnets, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.doctorName.text = appointment.name
        holder.doctorSpecialty.text = appointment.specialty
        // Set image or other properties as needed
    }

    override fun getItemCount(): Int = appointments.size
}
