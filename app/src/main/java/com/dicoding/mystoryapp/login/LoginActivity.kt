package com.dicoding.mystoryapp.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.mystoryapp.api.ApiConfig
import com.dicoding.mystoryapp.databinding.ActivityLoginBinding
import com.dicoding.mystoryapp.preference.UserModel
import com.dicoding.mystoryapp.preference.UserPref
import com.dicoding.mystoryapp.story.StoryActivity
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPref: UserPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi UserPref untuk mengelola preferensi pengguna
        userPref = UserPref(this)

        // Mengatur aksi saat tombol masuk ditekan
        binding.btnProsesMasuk.setOnClickListener {

            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            // Validasi input email dan password
            if (email.isNullOrEmpty()) {
                binding.edLoginEmail.error = "Email tidak boleh kosong"
            } else if (!isValidEmail(email)) {
                binding.edLoginEmail.error = "Email anda tidak valid"
            } else if (password.isNullOrEmpty()) {
                binding.edLoginPassword.error = "Password tidak boleh kosong"
            } else{
                // Melakukan proses login dengan menggunakan coroutine
                lifecycleScope.launch {
                    try {
                        showLoading(true)
                        // Membuat panggilan API untuk login
                        val apiService = ApiConfig.getApiService("")
                        val successLogin = apiService.login(email, password)

                        //showToast(successLogin.message)

                        // menyimpan ke preference
                        val dataUser = UserModel(
                            successLogin.loginResult.userId,
                            successLogin.loginResult.name,
                            successLogin.loginResult.token,
                        )

                        userPref.setUser(dataUser)
                        showLoading(false)

                        // Menampilkan dialog login berhasil
                        AlertDialog.Builder(this@LoginActivity).apply {
                            setTitle("Login Berhasil!")
                            setMessage("Silahkan mencatat dan mengabadikan momen anda lewat aplikasi ini")
                            setPositiveButton("Lanjut") { _, _ ->
                                val intent = Intent(context, StoryActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }

                    }catch (e: HttpException){
                        when (e.code()) {
                            // Menangani kesalahan yang mungkin terjadi saat panggilan API
                            400 -> showToast("Bad Request: The server could not understand the request.")
                            401 -> showToast("Unauthorized: User authentication failed.")
                            404 -> showToast("Not Found: The requested resource was not found on the server.")
                            else -> showToast("An error occurred: ${e.message()}")
                        }
                    }
                }
            }
        }

    }

    // Fungsi untuk memeriksa apakah email valid
    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Menampilkan pesan toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Menampilkan atau menyembunyikan indikator progres
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}


