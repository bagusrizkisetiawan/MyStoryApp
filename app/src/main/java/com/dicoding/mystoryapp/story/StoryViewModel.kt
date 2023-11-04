package com.dicoding.mystoryapp.story

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.mystoryapp.api.ApiConfig
import com.dicoding.mystoryapp.api.ListStoryItem
import com.dicoding.mystoryapp.api.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryViewModel:ViewModel() {

    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory: LiveData<List<ListStoryItem>> = _listStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getListStory(token :String){

        // Initialize API service with the retrieved token
        val apiService = ApiConfig.getApiService(token)
        // Make an API call to get the list of stories
        val call = apiService.getStory()

        // Enqueue the API call to handle the response asynchronously
        call?.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(
                call: Call<StoryResponse>,
                response: Response<StoryResponse>
            ) {
                _isLoading.value = true

                // Handle successful response from the API
                if (response.isSuccessful) {
                    // Retrieve the list of stories from the response body
                    val stories = response.body()

                    if (stories != null) {
                        _isLoading.value = false
                        _listStory.value = stories.listStory
                    }
                }
            }

            // Handle API call failure
            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {

            }
        })
    }

}


