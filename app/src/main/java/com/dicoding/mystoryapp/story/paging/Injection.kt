package com.dicoding.mystoryapp.story.paging

import android.content.Context
import com.dicoding.mystoryapp.api.ApiConfig

object Injection {
    fun provideRepository(): StoryRepository {

        val apiService = ApiConfig.getApiService("${"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVYybHpYdVVJU3I5Z3RFd0UiLCJpYXQiOjE2OTc5MjUxMTN9.AYSpGtyteso62HE2v1IM1jTsFc0Q9zHz6rNXN8Gau_E"}")
        return StoryRepository(apiService)
    }
}