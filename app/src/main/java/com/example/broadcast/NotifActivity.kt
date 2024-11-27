package com.example.broadcast

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.broadcast.databinding.ActivityNotifBinding

class NotifActivity : AppCompatActivity() {

    // Deklarasi variabel
    // Binding untuk layout dan channelId untuk identifikasi saluran notifikasi.
    private lateinit var binding: ActivityNotifBinding
    private val channelId = "TEST_NOTIF"

    // Metode onCreate
    // Menginisialisasi binding, edge-to-edge UI, dan menangani event tombol notifikasi.
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNotifBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        binding.btnNotif.setOnClickListener {
            // Menentukan flag untuk PendingIntent berdasarkan versi Android.
            val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE
            } else {
                0
            }

            // Intent untuk mengirim broadcast saat tombol di notifikasi ditekan.
            val intent = Intent(this@NotifActivity, NotifReceiver::class.java).putExtra("TES_READ", "Read..")

            // PendingIntent untuk menangkap aksi dari notifikasi.
            val pendingIntent = PendingIntent.getBroadcast(
                this@NotifActivity, 0, intent, PendingIntent.FLAG_IMMUTABLE
            )

            // Membuat notifikasi dengan detail seperti ikon, judul, dan tombol aksi.
            val builder = NotificationCompat.Builder(this@NotifActivity, channelId)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("Notifku")
                .setContentText("Logout dengan klik tombol berikut")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(0, "Logout", pendingIntent)

            // Menampilkan notifikasi dengan pengecekan izin untuk versi Android 13+.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    notifManager.notify(0, builder.build())
                } else {
                    requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
                }
            } else {
                notifManager.notify(0, builder.build())
            }
        }
    }
}
