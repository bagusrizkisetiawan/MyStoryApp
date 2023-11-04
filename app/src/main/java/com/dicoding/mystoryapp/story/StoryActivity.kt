package com.dicoding.mystoryapp.story

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mystoryapp.R
import com.dicoding.mystoryapp.api.ListStoryItem
import com.dicoding.mystoryapp.databinding.ActivityStoryBinding
import com.dicoding.mystoryapp.main.MainActivity
import com.dicoding.mystoryapp.maps.MapsActivity
import com.dicoding.mystoryapp.preference.UserModel
import com.dicoding.mystoryapp.preference.UserPref
import com.dicoding.mystoryapp.story.paging.LoadingStateAdapter
import com.dicoding.mystoryapp.story.paging.StViewModel
import com.dicoding.mystoryapp.story.paging.StoryPagingAdapter
import com.dicoding.mystoryapp.story.paging.ViewModelFactory


class StoryActivity : AppCompatActivity() {

    private lateinit var userPref: UserPref
    private lateinit var binding: ActivityStoryBinding

    private val mainViewModel: StViewModel by viewModels {
        ViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = StoryPagingAdapter()


        // Set up the app bar menu item click listener
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.add_story -> {
                    val intent = Intent(this, AddStoryActivity::class.java)

                    startActivity(intent)
                    true
                }

                R.id.maps_story -> {
                    val intent = Intent(this, MapsActivity::class.java)

                    startActivity(intent)
                    true
                }

                R.id.keluar -> {
                    // mengembalikan nulai null ke preference
                    val dataUser = UserModel(
                        null,
                        null,
                        null,
                    )
                    userPref.setUser(dataUser)

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }

                else -> false
            }
        }

        // Set up RecyclerView
        binding.rvStory.setHasFixedSize(true)
        binding.rvStory.layoutManager = LinearLayoutManager(this)

        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        mainViewModel.listSt.observe(this) {
            adapter.submitData(lifecycle, it)
        }



//        // Inisialisasi UserPref untuk token preferensi pengguna
//        userPref = UserPref(this)
//        val token = userPref.getUser().token
//
//        if (token != null){
//
//            val storyViewModel = ViewModelProvider(
//                this,
//                ViewModelProvider.NewInstanceFactory()
//            ).get(StoryViewModel::class.java)
//
//            token?.let { storyViewModel.getListStory(it) }
//
//            // observe perubahan status loading di ViewModel
//            storyViewModel.isLoading.observe(this) { isLoading ->
//                showLoading(isLoading)
//            }
//
//            storyViewModel.listStory.observe(this){
//                showStories(it)
//            }
//
//        }
    }


    // Display the list of stories in the RecyclerView using the StoryAdapter
    private fun showStories(stories: List<ListStoryItem>) {
        // Set the adapter for the RecyclerView
        binding.rvStory.adapter = StoryAdapter(stories)
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





//    override fun onResume() {
//        super.onResume()
//        // inisialisasi ViewModel
//        val storyViewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.NewInstanceFactory()
//        ).get(StoryViewModel::class.java)
//
//        // ambil token dari UserPref
//        val token = userPref.getUser().token
//
//        // cek apakah token tidak null
//        token?.let {
//            // panggil metode untuk mendapatkan data terbaru
//            storyViewModel.getListStory(it)
//
//            storyViewModel.listStory.observe(this){
//                showStories(it)
//            }
//
//            // observe perubahan status loading di ViewModel
//            storyViewModel.isLoading.observe(this) { isLoading ->
//                // tampilkan atau sembunyikan loading progress bar sesuai dengan status loading
//                showLoading(isLoading)
//            }
//        }
//    }

}




