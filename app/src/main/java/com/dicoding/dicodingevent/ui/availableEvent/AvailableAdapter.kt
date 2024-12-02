package com.dicoding.dicodingevent.ui.availableEvent

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.services.response.ListEventsItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AvailableAdapter (private val onItemClick: (Int) -> Unit) :
    ListAdapter<ListEventsItem, AvailableAdapter.EventViewHolder>(DIFF_CALLBACK) {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventName: TextView = view.findViewById(R.id.event_name)
        val eventImage: ImageView = view.findViewById(R.id.event_image)
        val eventDate: TextView = view.findViewById(R.id.event_date)
        val eventLocation: TextView = view.findViewById(R.id.event_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_available, parent, false)
        return EventViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)

        holder.eventName.text = event.name
        holder.eventDate.text = "Date : ${formatDate(event.beginTime)}"
        holder.eventLocation.text = "Location : ${event.cityName}"

        // Load image with Glide
        Glide.with(holder.itemView.context)
            .load(event.imageLogo)
            .into(holder.eventImage)

        //set on click item
        holder.itemView.setOnClickListener {
            onItemClick(event.id)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

//    format date
    private fun formatDate(dateString: String): String {
        // Format input sesuai dengan format yang diterima
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        // Format output sesuai keinginan
        val outputFormat = SimpleDateFormat("dd MMMM yyyy 'Pukul' HH.mm", Locale("id", "ID"))

        return try {
            val date: Date = inputFormat.parse(dateString) ?: Date()
            outputFormat.format(date)
        } catch (e: Exception) {
            // Jika terjadi kesalahan dalam parsing, kembalikan string asli
            dateString
        }
    }


}
