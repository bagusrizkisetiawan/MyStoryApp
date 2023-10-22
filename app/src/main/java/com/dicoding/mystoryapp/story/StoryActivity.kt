package com.dicoding.mystoryapp.story

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mystoryapp.R
import com.dicoding.mystoryapp.api.ApiConfig
import com.dicoding.mystoryapp.api.StoryResponse
import com.dicoding.mystoryapp.databinding.ActivityStoryBinding
import com.dicoding.mystoryapp.preference.UserPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StoryActivity : AppCompatActivity() {

    private lateinit var userPref: UserPref
    private lateinit var binding: ActivityStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the app bar menu item click listener
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.add_story -> {
                    startActivity(Intent(this, AddStoryActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Set up RecyclerView
        binding.rvStory.setHasFixedSize(true)
        binding.rvStory.layoutManager = LinearLayoutManager(this)

        // Initialize UserPref for handling user preferences
        userPref = UserPref(this)

        // Check if user data (including token) is available in shared preferences
        if (userPref.preference.contains(UserPref.NAME)) {
            val token = userPref.getUser().token

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
                    showLoading(true)

                    // Handle successful response from the API
                    if (response.isSuccessful) {
                        // Retrieve the list of stories from the response body
                        val stories = response.body()

                        if (stories != null) {
                            showLoading(false)
                            showStories(stories)
                        } else {
                            showError("Empty response")
                        }
                    } else {
                        showError("Unsuccessful response: ${response.code()}")
                    }
                }

                // Handle API call failure
                override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    // Display the list of stories in the RecyclerView using the StoryAdapter
    private fun showStories(stories: StoryResponse) {
        // Set the adapter for the RecyclerView
        binding.rvStory.adapter = StoryAdapter(stories.listStory)
    }

    // Display a Toast message with the given message
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Show or hide the loading progress bar based on the given boolean value
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}