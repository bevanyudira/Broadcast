package com.example.broadcast

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.broadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Deklarasi variabel
    // Binding untuk layout, dan prefManager untuk mengelola data preferensi.
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager

    // Metode onCreate
    // Inisialisasi binding, prefManager, dan menangani event UI.
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        prefManager = PrefManager.getInstance(this)
        checkLoginStatus()

        with(binding) {
            // Menampilkan username pada layar utama.
            txtUsername.text = prefManager.getUsername()

            // Tombol logout
            // Menghapus username dari preferensi dan mengecek ulang status login.
            btnLogout.setOnClickListener {
                prefManager.saveUsername("")
                checkLoginStatus()
            }

            // Tombol clear
            // Menghapus semua data dari preferensi dan mengecek ulang status login.
            btnClear.setOnClickListener {
                prefManager.clear()
                checkLoginStatus()
            }
        }
    }

    // Metode checkLoginStatus
    // Mengecek apakah pengguna telah login, dan jika belum, pindah ke LoginActivity.
    private fun checkLoginStatus() {
        val username = prefManager.getUsername()
        if (username.isEmpty()) {
            startActivity(
                Intent(this@MainActivity, LoginActivity::class.java)
            )
            finish()
        }
    }
}
