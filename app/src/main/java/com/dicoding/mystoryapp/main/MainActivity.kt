package com.dicoding.mystoryapp.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.dicoding.mystoryapp.databinding.ActivityMainBinding
import com.dicoding.mystoryapp.login.LoginActivity
import com.dicoding.mystoryapp.preference.UserModel
import com.dicoding.mystoryapp.preference.UserPref
import com.dicoding.mystoryapp.register.RegisterActivity
import com.dicoding.mystoryapp.story.StoryActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var userPref: UserPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi UserPref untuk mengelola preferensi pengguna
        userPref = UserPref(this)

        // Cek apakah pengguna sudah login
        if (userPref.preference.contains(UserPref.NAME)){
            // Jika sudah login, ambil nama pengguna dari preferensi
            val user = userPref.getUser().name

            // Tampilkan pesan selamat datang
            showToast("Selamat datang $user")

            // Redirect ke layar cerita
            val intent = Intent(this@MainActivity, StoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnLogin = binding.btnMasuk
        val btnRegist = binding.btnDaftar

        // Mengatur aksi saat tombol login ditekan
        btnLogin.setOnClickListener{
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        // Mengatur aksi saat tombol daftar ditekan
        btnRegist.setOnClickListener{
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    // Menampilkan pesan toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}