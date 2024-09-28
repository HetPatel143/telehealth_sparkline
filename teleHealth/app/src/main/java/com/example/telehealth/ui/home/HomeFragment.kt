package com.example.telehealth.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telehealth.R
import com.example.telehealth.adopter.AppointmentAdapter
import com.example.telehealth.adopter.CategoryAdapter
import com.example.telehealth.adopter.DoctorAdapter
import com.example.telehealth.data.Appointment
import com.example.telehealth.data.Doctor
import com.example.telehealth.databinding.FragmentHomeBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class HomeFragment : Fragment() {

    // Using view binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var appointmentAdapter: AppointmentAdapter
    private lateinit var doctorAdapter: DoctorAdapter
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using view binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = FirebaseFirestore.getInstance()

        // Dummy data for categories and appointments
        val categories = listOf("Cardiology", "Dentistry", "Pediatrics")
        val appointments = listOf(
            Appointment("Jason Smith", "Dentist", R.drawable.profile),
            Appointment("Emily Johnson", "Cardiologist", R.drawable.profile)
        )

        // Set up category RecyclerView
        categoryAdapter = CategoryAdapter(categories)
        binding.recyclerViewCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewCategories.adapter = categoryAdapter

        // Set up appointment RecyclerView
        appointmentAdapter = AppointmentAdapter(appointments)
        binding.recyclerViewAppointments.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewAppointments.adapter = appointmentAdapter

        // Set up doctor RecyclerView
        binding.recyclerViewDoctors.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        retrieveAllDoctors()
    }

    // Retrieve all doctors (same logic as in MainActivity)
    private fun retrieveAllDoctors() {
        // Accessing the collection "doctor" to get all doctor type documents
        firestore.collection("doctor")
            .get()
            .addOnSuccessListener { doctorTypeDocuments ->
                val allDoctorsList = mutableListOf<Doctor>()
                val tasks = mutableListOf<Task<QuerySnapshot>>() // To keep track of the fetch tasks

                // Iterating through each doctor type document
                for (doctorTypeDocument in doctorTypeDocuments) {
                    // For each doctor type, access the "Doctors" subcollection
                    val doctorsTask = firestore.collection("doctor")
                        .document(doctorTypeDocument.id)
                        .collection("Doctors")
                        .get()

                    // Handle each individual task
                    doctorsTask.addOnSuccessListener { result ->
                        for (doctorDocument in result.documents) {
                            val doctor = doctorDocument.toObject(Doctor::class.java)
                            if (doctor != null) {
                                allDoctorsList.add(doctor)
                            }
                        }
                        // Update the adapter once we finish fetching all doctors
                        updateDoctorAdapter(allDoctorsList)
                    }.addOnFailureListener { e ->
                        Toast.makeText(requireContext(), "Error loading doctors for ${doctorTypeDocument.id}: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error loading doctor types: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun updateDoctorAdapter(doctorList: List<Doctor>) {
        doctorAdapter = DoctorAdapter(doctorList,requireContext(),) { doctor ->
            // Handle booking click (e.g., Toast message for booked doctor)
            Toast.makeText(requireContext(), "Booked: ${doctor.name}", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerViewDoctors.adapter = doctorAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
