package com.example.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotifReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val msg = intent?.getStringExtra("TES_READ")
        if (msg!=null) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }

        // Aksi Logout
        context?.let {
            val prefManager = PrefManager.getInstance(it)
            prefManager.clear() // Hapus data sesi pengguna di SharedPreferences

            // Redirect ke halaman login
            val loginIntent = Intent(it, LoginActivity::class.java)
            loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            it.startActivity(loginIntent)
        }
    }
}