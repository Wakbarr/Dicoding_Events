package com.dicoding.dicodingevent.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.databinding.FragmentDetailBinding
import com.dicoding.dicodingevent.databinding.ItemListAvailableBinding
import com.dicoding.dicodingevent.services.data.FavoriteEvent
import com.dicoding.dicodingevent.ui.detailEvent.DetailFragment

class AdapterFav(private val ListFav: List<FavoriteEvent>) : RecyclerView.Adapter<AdapterFav.ViewHolder>() {

    class ViewHolder(val binding: ItemListAvailableBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListAvailableBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ListFav.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorit = ListFav[position]

        // Bind data ke view
        holder.binding.apply {
            eventName.text = favorit.name
            eventDate.text = favorit.category
        }

        // Load gambar menggunakan Glide
        Glide.with(holder.itemView.context)
            .load(favorit.imageUrl)
            .into(holder.binding.eventImage)

        // Set click listener
        holder.itemView.setOnClickListener { view ->
            try {
                // Persiapkan bundle untuk navigasi
                val bundle = Bundle().apply {
                    putInt("eventId", favorit.id.toInt())
                }

                // Navigasi ke detail fragment
                view.findNavController().navigate(
                    R.id.action_favorite_nav_to_detailFragment,
                    bundle
                )
            } catch (e: Exception) {
                // Handle error
                Log.e("AdapterFav", "Navigation error: ${e.message}")
                Toast.makeText(
                    view.context,
                    "Gagal membuka detail event",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}