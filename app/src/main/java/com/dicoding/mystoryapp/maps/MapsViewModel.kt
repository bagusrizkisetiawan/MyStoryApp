package com.dicoding.mystoryapp.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mystoryapp.api.ApiConfig
import com.dicoding.mystoryapp.api.ListStoryItem

class MapsViewModel: ViewModel() {
    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory: LiveData<List<ListStoryItem>> = _listStory

    suspend fun getLocationStory(token :String){

        // Initialize API service with the retrieved token
        val apiService = ApiConfig.getApiService(token)
        // Make an API call to get the list of stories
        val call = apiService.getStoriesWithLocation(1)

        _listStory.value = call.listStory
    }
}