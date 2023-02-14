package com.example.appointments.ui.search.doctorsRecyclerView

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.opengl.Visibility
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.appointments.R
import com.example.appointments.data.Doctor
import com.example.appointments.databinding.DoctorItemBinding
import com.example.appointments.ui.search.SearchViewModel

class DoctorRecyclerViewAdapter(
    var items: MutableList<DoctorItem>,
    val viewModel: SearchViewModel
) : RecyclerView.Adapter<DoctorRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(private val binding: DoctorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DoctorItem, viewModel: SearchViewModel) {
            "${item.doctor.firstName} ${item.doctor.lastName}".also { binding.name.text = it }
            binding.button.isEnabled = false
            binding.reason.visibility = View.GONE
            binding.specialisation.text = item.doctor.specialisation

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

            binding.listView.setOnItemClickListener { parent, selectedItem, _, _ ->
                for (i in 0 until parent.childCount) {
                    parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT)
                    parent.getChildAt(i).isSelected = false
                }
                selectedItem.setBackgroundColor(Color.rgb(187, 134, 252))
                selectedItem.isSelected = true
                binding.button.isEnabled = true
                binding.reason.visibility = View.VISIBLE
            }

            binding.button.setOnClickListener {
                for (i in 0 until binding.listView.childCount) {
                    if (binding.listView.getChildAt(i).isSelected)
                        viewModel.createAppointment(
                            DoctorItem(
                                binding.listView.getItemAtPosition(i).toString(),
                                binding.reason.editText?.text.toString(),
                                item.doctor
                            )
                        )
                    binding.listView.getChildAt(i).isSelected = false
                    binding.listView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT)
                }
                binding.button.isEnabled = false
                binding.reason.visibility = View.GONE
                binding.reason.editText?.setText("")
                Toast.makeText(
                    itemView.context,
                    itemView.context.getString(R.string.appointment_confirmed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DoctorItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItem: MutableList<DoctorItem>) {
        items = newItem
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