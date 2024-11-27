package com.example.broadcast

import android.content.Context

class PrefManager private constructor(context: Context) {

    // Deklarasi SharedPreferences
    // Untuk menyimpan dan mengelola data preferensi dalam mode privat.
    private val sharedPreferences = context.getSharedPreferences(
        PREFS_FILENAME, Context.MODE_PRIVATE
    )

    companion object {
        // Konstanta untuk nama file SharedPreferences dan kunci data.
        private const val PREFS_FILENAME = "AuthAppPref"
        private const val KEY_USERNAME = "username"

        // Implementasi Singleton
        // Memastikan hanya ada satu instance PrefManager dalam aplikasi.
        @Volatile
        private var instance: PrefManager? = null

        fun getInstance(context: Context): PrefManager {
            return instance ?: synchronized(this) {
                instance ?: PrefManager(context.applicationContext).also { pref ->
                    instance = pref
                }
            }
        }
    }

    // Metode saveUsername
    // Menyimpan username ke SharedPreferences.
    fun saveUsername(username: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME, username)
        editor.apply()
    }

    // Metode getUsername
    // Mengambil username dari SharedPreferences, defaultnya kosong.
    fun getUsername(): String {
        return sharedPreferences.getString(KEY_USERNAME, "") ?: ""
    }

    // Metode clear
    // Menghapus semua data yang disimpan dalam SharedPreferences.
    fun clear() {
        sharedPreferences.edit().also {
            it.clear()
            it.apply()
        }
    }
}
