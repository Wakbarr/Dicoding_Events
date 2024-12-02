package com.dicoding.dicodingevent.ui.finishedEvent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingevent.services.response.ResponTersedia
import com.dicoding.dicodingevent.services.response.ListEventsItem
import com.dicoding.dicodingevent.services.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel : ViewModel() {

    private val _events = MutableLiveData<List<ListEventsItem>>()
    val events: LiveData<List<ListEventsItem>> get() = _events


    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun loadEvents() {
        _isLoading.value = true

        val apiService = ApiConfig.getApiService().getAvailableEvent(active = 0)
        apiService.enqueue(object : Callback<ResponTersedia> {
            override fun onResponse(call: Call<ResponTersedia>, response: Response<ResponTersedia>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents ?: emptyList()
                    _events.value = events // Set the data to LiveData
                } else {
                    _errorMessage.value = "Failed to load events"
                }
            }

            override fun onFailure(call: Call<ResponTersedia>, t: Throwable) {
                _errorMessage.value = "Error: No Internet Connection"
            }
        })
    }

    fun searchEvents(query: String) {
        _isLoading.value = true

        val apiService = ApiConfig.getApiService().getSearchEvent(active = 0, q = query)
        apiService.enqueue(object : Callback<ResponTersedia> {
            override fun onResponse(
                call: Call<ResponTersedia>,
                response: Response<ResponTersedia>
            ) {
                _isLoading.value = true
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents ?: emptyList()
                    _events.value = events // Set the data to LiveData
                } else {
                    _errorMessage.value = "Failed to load events"
                }
            }

            override fun onFailure(call: Call<ResponTersedia>, t: Throwable) {
                _errorMessage.value = "Error: No Internet Connection"
            }

        })
    }
}