package com.dicoding.mystoryapp.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat

import com.dicoding.mystoryapp.databinding.ActivityMainBinding
import com.dicoding.mystoryapp.login.LoginActivity
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


            // Redirect ke layar cerita
            val intent = Intent(this@MainActivity, StoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnLogin = binding.btnMasuk
        val btnRegist = binding.btnDaftar

        // Mengatur aksi saat tombol login ditekan
        btnLogin.setOnClickListener{

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@MainActivity,
                btnLogin, // View asal yang akan menggunakan animasi transisi
                ViewCompat.getTransitionName(btnLogin).toString() // Nama transisi, dapat berupa string unik
            )


            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent, options.toBundle())
        }

        // Mengatur aksi saat tombol daftar ditekan
        btnRegist.setOnClickListener{

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@MainActivity,
                btnRegist, // View asal yang akan menggunakan animasi transisi
                ViewCompat.getTransitionName(btnRegist).toString() // Nama transisi, dapat berupa string unik
            )

            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent, options.toBundle())
        }
    }

    // Menampilkan pesan toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}