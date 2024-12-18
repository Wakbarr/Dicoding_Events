package com.dicoding.dicodingevent.services.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(event: FavoriteEvent)

    @Delete
    suspend fun removeFavorite(event: FavoriteEvent)

    @Query("SELECT * FROM favorite")
    suspend fun getAll(): List<FavoriteEvent>

    @Query("SELECT * FROM favorite WHERE id = :eventId LIMIT 1")
    suspend fun getFavorite(eventId: String): FavoriteEvent?
}