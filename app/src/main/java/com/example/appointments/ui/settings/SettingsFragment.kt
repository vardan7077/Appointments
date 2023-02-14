package com.example.appointments.ui.settings

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.appointments.R
import com.example.appointments.data.User
import com.example.appointments.data.database.Database
import com.example.appointments.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val application = requireNotNull(activity?.application)
        val database = Database.getInstance(application).databaseDAO
        val settingsFragmentViewModelFactory =
            SettingsFragmentViewModelFactory(application, database)
        val settingsViewModel =
            ViewModelProvider(this, settingsFragmentViewModelFactory)[SettingsViewModel::class.java]


        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = settingsViewModel

        val user = settingsViewModel.user

        user.observe(viewLifecycleOwner) { userData ->
            if (userData != null) {
                binding.editFirstName.setText(userData.firstName)
                binding.editLastName.setText(userData.lastName)
                binding.editAge.setText(userData.age.toString())
                binding.editSex.setText(userData.sex)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}