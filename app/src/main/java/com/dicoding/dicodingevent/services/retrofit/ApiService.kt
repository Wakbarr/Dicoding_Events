package com.dicoding.dicodingevent.services.retrofit

import com.dicoding.dicodingevent.services.response.ResponTersedia
import com.dicoding.dicodingevent.services.response.ResponsDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getAvailableEvent(
        @Query("active") active: Int
    ): Call<ResponTersedia>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: Int
    ): Call<ResponsDetail>

    //search
    @GET("events")
    fun getSearchEvent(
        @Query("active") active: Int = -1,
        @Query("q") q: String? = null
    ): Call<ResponTersedia>
}
