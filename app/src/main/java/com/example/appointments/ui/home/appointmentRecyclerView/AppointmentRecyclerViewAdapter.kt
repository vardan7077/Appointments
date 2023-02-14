package com.example.appointments.ui.home.appointmentRecyclerView

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.opengl.Visibility
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import android.provider.Settings.System.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.RecyclerView
import com.example.appointments.R
import com.example.appointments.data.Appointment
import com.example.appointments.databinding.AppointmentsItemBinding
import com.example.appointments.ui.home.HomeViewModel
import com.example.appointments.ui.search.SearchViewModel
import com.example.appointments.ui.search.doctorsRecyclerView.DoctorItem

class AppointmentRecyclerViewAdapter(
    var items: List<AppointmentItem>,
    val viewModel: HomeViewModel
) : RecyclerView.Adapter<AppointmentRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(private val binding: AppointmentsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AppointmentItem, viewModel: HomeViewModel) {
            (item.doctor.firstName + " " + item.doctor.lastName).also {
                binding.doctorsName.text = it
            }
            binding.specialisation.text = item.doctor.specialisation
            binding.listView.visibility = View.GONE
            binding.reasonEdit.visibility = View.GONE
            binding.availableLocations.visibility = View.GONE
            binding.location.text = itemView.context.getString(R.string.location_is) + item.appointment.location

            val listViewAdapter = ArrayAdapter(
                this.itemView.context,
                android.R.layout.simple_list_item_activated_1,
                item.doctor.locations
            )
            binding.listView.adapter = listViewAdapter

            if (item.appointment.reason != null && item.appointment.reason!!.isNotEmpty())
                (itemView.context
                    .getString(R.string.reason_is) + item.appointment.reason).also {
                    binding.reasonTextView.text = it
                }
            else
                binding.reasonTextView.visibility = View.GONE


            binding.listView.setOnItemClickListener { parent, selectedItem, _, _ ->
                for (i in 0 until parent.childCount) {
                    parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT)
                    parent.getChildAt(i).isSelected = false
                }
                selectedItem.setBackgroundColor(Color.rgb(187, 134, 252))
                selectedItem.isSelected = true
            }


            binding.deleteButton.setOnClickListener {
                viewModel.cancelAppointment(item.appointment)
                Toast.makeText(
                    itemView.context,
                    itemView.context.getString(R.string.canceled_appointment),
                    Toast.LENGTH_SHORT
                ).show()
            }

            binding.editButton.setOnClickListener {
                item.editMode = !item.editMode
                if (item.editMode) {

                    binding.listView.adapter = ArrayAdapter(
                        this.itemView.context,
                        android.R.layout.simple_list_item_activated_1,
                        item.doctor.locations
                    )
                    val size = item.doctor.locations.size
                    val scale = Resources.getSystem().displayMetrics.density
                    val pixels = (50 * scale + 0.5f).toInt()
                    val params = binding.listView.layoutParams
                    params.height = size * pixels
                    binding.listView.layoutParams = params
                    binding.listView.visibility = View.VISIBLE
                    binding.editButton.text = itemView.context.getString(R.string.save_button)
                    binding.reasonTextView.visibility = View.GONE
                    binding.reasonEdit.visibility = View.VISIBLE
                    binding.reasonEdit.editText?.setText(item.appointment.reason)
                } else {
                    binding.editButton.text = itemView.context.getString(R.string.edit_button)
                    binding.reasonEdit.visibility = View.GONE
                    binding.listView.visibility = View.GONE
                    for (i in 0 until binding.listView.childCount) {
                        if (binding.listView.getChildAt(i).isSelected)
                            item.appointment.location =
                                binding.listView.getItemAtPosition(i).toString()
                    }

                    if (!item.appointment.reason.equals(binding.reasonEdit.editText?.text.toString())
                        && binding.reasonEdit.editText?.text.toString() != itemView.context
                            .getString(R.string.enter_appointment_reason)
                    )
                        item.appointment.reason = binding.reasonEdit.editText?.text.toString()

                    viewModel.updateAppointment(item.appointment)
                    if (item.appointment.reason != null && item.appointment.reason!!.isNotEmpty())
                        (itemView.context
                            .getString(R.string.reason_is) + item.appointment.reason).also {
                            binding.reasonTextView.text = it
                            binding.reasonTextView.visibility = View.VISIBLE
                        }
                    else
                        binding.reasonTextView.visibility = View.GONE
                    Toast.makeText(
                        itemView.context,
                        itemView.context.getString(R.string.successfully_updated_appointment),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AppointmentsItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<AppointmentItem>) {
        items = newItems
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, viewModel)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}