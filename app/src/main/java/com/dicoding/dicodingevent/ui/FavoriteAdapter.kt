package com.dicoding.dicodingevent.ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.databinding.FragmentDetailBinding
import com.dicoding.dicodingevent.databinding.ItemListAvailableBinding
import com.dicoding.dicodingevent.services.data.FavoriteEvent

class AdapterFav(private val ListFav: List<FavoriteEvent>) : RecyclerView.Adapter<AdapterFav.ViewHolder>() {
    class ViewHolder(val binding: ItemListAvailableBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListAvailableBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  ListFav.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorit = ListFav[position]
        holder.binding.eventName.text = favorit.name
        holder.binding.eventDate.text = favorit.category
        Glide.with(holder.itemView.context)
            .load(favorit.imageUrl)
            .into(holder.binding.eventImage)
        holder.itemView.setOnClickListener{
            val seeDetails = Intent(it.context,FragmentDetailBinding::class.java)
            Log.d("AdapterFav", "ID : ${favorit.id}")
            it.context.startActivity(seeDetails)
        }

    }
}