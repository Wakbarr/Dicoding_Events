package com.dicoding.dicodingevent.services.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEvent (
    @PrimaryKey val id:String,
    val name:String,
    val category: String,
    val logo:String,
    val imageUrl:String,
    )
