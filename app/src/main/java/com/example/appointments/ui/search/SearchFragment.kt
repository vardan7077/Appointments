package com.example.appointments.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appointments.R
import com.example.appointments.data.Doctor
import com.example.appointments.data.database.Database
import com.example.appointments.databinding.FragmentSearchBinding
import com.example.appointments.ui.search.doctorsRecyclerView.DoctorItem
import com.example.appointments.ui.search.doctorsRecyclerView.DoctorRecyclerViewAdapter

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(activity?.application)
        val database = Database.getInstance(application).databaseDAO
        val searchFragmentViewModelFactory = SearchFragmentViewModelFactory(application, database)
        val viewModel =
            ViewModelProvider(this, searchFragmentViewModelFactory)[SearchViewModel::class.java]

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)


        val doctorRecyclerViewAdapter = DoctorRecyclerViewAdapter(mutableListOf(), viewModel)
        val doctors = viewModel.listOfDoctors

        doctors.observe(viewLifecycleOwner) {
            val listOfDoctorItems: MutableList<DoctorItem> = mutableListOf()
            for (doctor in it) {
                if (doctor != null)
                    listOfDoctorItems.add(DoctorItem(doctor = doctor))
            }
            doctorRecyclerViewAdapter.submitList(listOfDoctorItems)
        }

        binding.doctorRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.doctorRecyclerView.adapter = doctorRecyclerViewAdapter

        val resources = requireContext().resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        var size = 0
        if (resourceId > 0) {
            size = (resources.getDimensionPixelSize(resourceId)) * 3
        }

        binding.doctorRecyclerView.setPadding(0, 0, 0, size)

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}