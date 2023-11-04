package com.dicoding.mystoryapp.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.mystoryapp.api.ApiConfig
import com.dicoding.mystoryapp.databinding.ActivityRegisterBinding
import com.dicoding.mystoryapp.login.LoginActivity
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterActivity : AppCompatActivity() {


    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Mengatur aksi saat tombol proses masuk ditekan
        binding.btnProsesRegister.setOnClickListener {

            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            // Validasi input nama, email, dan password
            if (name.isNullOrEmpty()) {
                binding.edRegisterName.error = "Nama tidak boleh kosong"
            } else if (email.isNullOrEmpty()) {
                binding.edRegisterEmail.error = "Email tidak boleh kosong"
            } else if (!isValidEmail(email)) {
                binding.edRegisterEmail.error = "Email anda tidak valid"
            } else if (password.isNullOrEmpty()) {
                binding.edRegisterPassword.error = "Password tidak boleh kosong"
            } else{
                // Melakukan proses registrasi dengan menggunakan coroutine
                lifecycleScope.launch {
                    try {
                        showLoading(true)

                        // Panggilan API untuk melakukan registrasi
                        val apiService = ApiConfig.getApiService("")
                        val successRegister = apiService.register(name, email, password)

                        showToast(successRegister.message)

                        showLoading(false)

                        // Menampilkan dialog registrasi berhasil
                        AlertDialog.Builder(this@RegisterActivity).apply {
                            setTitle("Registrasi Berhasil!")
                            setMessage("Silahkan login terlebih daluhu sebelum menyimpan cerita anda")
                            setPositiveButton("Login") { _, _ ->
                                val intent = Intent(context, LoginActivity::class.java)
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



    // valid email
    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    // Fungsi untuk memeriksa apakah email valid
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