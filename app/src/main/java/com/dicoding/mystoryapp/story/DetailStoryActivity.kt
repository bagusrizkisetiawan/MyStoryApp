package com.dicoding.mystoryapp.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.mystoryapp.api.ApiConfig
import com.dicoding.mystoryapp.api.DetailResponse
import com.dicoding.mystoryapp.databinding.ActivityDetailStoryBinding
import com.dicoding.mystoryapp.preference.UserPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailStoryBinding
    private lateinit var userPref: UserPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil ID cerita dari intent
        val storyId = intent.getStringExtra("storyId")

        // Inisialisasi UserPref untuk mengelola preferensi pengguna
        userPref = UserPref(this)

        // Mendapatkan token otorisasi dari preferensi pengguna
        val token = userPref.getUser().token

        // Mendapatkan instance ApiService dengan token otorisasi
        val apiService = ApiConfig.getApiService(token)

        // Memastikan storyId tidak null sebelum memanggil API
        storyId?.let {
            val result = apiService.getDetailStory(it)

            result.enqueue(object : Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val story = response.body()?.story

                        // Mendapatkan data cerita
                        val photoUrl = story?.photoUrl
                        val nameStory = story?.name
                        val descStory = story?.description

                        // Menampilkan data cerita ke dalam tampilan
                        setInView(photoUrl, nameStory, descStory)
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    // Handle kegagalan panggilan API
                    // Misalnya, menampilkan pesan kesalahan kepada pengguna
                    // Di sini bisa menampilkan pesan kesalahan menggunakan Toast atau Snackbar
                }
            })
        }
    }

    // Menampilkan data cerita ke dalam tampilan menggunakan Glide untuk gambar
    private fun setInView(photoUrl: String?, nameStory: String?, descStory: String?) {
        Glide.with(this)
            .load(photoUrl)
            .into(binding.ivStory) // Menggunakan Glide untuk memuat gambar ke ImageView
        binding.tvStoryName.text = nameStory // Menetapkan nama cerita ke TextView
        binding.tvStoryDesc.text = descStory // Menetapkan deskripsi cerita ke TextView
    }
}