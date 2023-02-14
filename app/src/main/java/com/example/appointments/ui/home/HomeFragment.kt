package com.example.appointments.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appointments.R
import com.example.appointments.data.Appointment
import com.example.appointments.data.Doctor
import com.example.appointments.data.User
import com.example.appointments.data.database.Database
import com.example.appointments.databinding.FragmentHomeBinding
import com.example.appointments.ui.home.appointmentRecyclerView.AppointmentItem
import com.example.appointments.ui.home.appointmentRecyclerView.AppointmentRecyclerViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var appointmentsRecyclerViewAdapter: AppointmentRecyclerViewAdapter
    private lateinit var appointments: LiveData<List<Appointment>>
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(activity?.application)
        val databaseDAO = Database.getInstance(application).databaseDAO
        val homeViewModelFactory = HomeFragmentViewModelFactory(application, databaseDAO)
        val homeViewModel =
            ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this

        appointmentsRecyclerViewAdapter =
            AppointmentRecyclerViewAdapter(mutableListOf(), homeViewModel)
        appointments = homeViewModel.appointments

        homeViewModel.addUser()

        binding.appointmentRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.appointmentRecyclerView.adapter = appointmentsRecyclerViewAdapter
        if (appointments.value != null && appointments.value!!.isNotEmpty()) {
            val listOfItems = homeViewModel.getAppointmentData(appointments.value!!)
            appointmentsRecyclerViewAdapter.submitList(listOfItems)
        }

        appointments.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val listOfItems = homeViewModel.getAppointmentData(it)
                appointmentsRecyclerViewAdapter.submitList(listOfItems)
            } else {
                appointmentsRecyclerViewAdapter.submitList(listOf())
            }
        }

        val resources = requireContext().resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        var size = 0
        if (resourceId > 0) {
            size = (resources.getDimensionPixelSize(resourceId)) * 3
        }

        binding.appointmentRecyclerView.setPadding(0, 0, 0, size)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}