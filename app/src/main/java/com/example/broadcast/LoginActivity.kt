package com.example.broadcast

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.broadcast.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    // Deklarasi variabel
    // Untuk menghubungkan layout, menyimpan preferensi pengguna, dan memvalidasi akun.
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager
    private var usernameAccount = "rackcle"
    private var passwordAccount = "admin123"

    // Metode onCreate
    // Inisialisasi binding, prefManager, dan menangani tombol login.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager.getInstance(this)

        with(binding) {
            // Tombol login
            // Memvalidasi username dan password, menyimpan data jika berhasil, atau memberi pesan error.
            btnLogin.setOnClickListener {
                val usernameInput = edtUsername.text.toString()
                val passwordInput = edtPassword.text.toString()

                if (usernameInput == usernameAccount
                    && passwordInput == passwordAccount
                ) {
                    prefManager.saveUsername(usernameInput)
                    checkLoginStatus()
                } else {
                    Toast.makeText(this@LoginActivity, "Username atau password salah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Metode checkLoginStatus
    // Mengecek login pengguna dan berpindah ke NotifActivity jika berhasil.
    private fun checkLoginStatus() {
        val username = prefManager.getUsername()
        if (username.isNotEmpty()) {
            startActivity(Intent(this@LoginActivity, NotifActivity::class.java))
            finish()
        }
    }
}
