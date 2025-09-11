package com.example.personaldiaryapp

//import androidx.navigation.findNavController
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import com.example.personaldiaryapp.room.DiaryEntry

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannels()


//        handleIntent(intent)
    }


    // this will be called when the notification will be clicked
    // the notification will pass the data inside intent
    // and then the new activity launches
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        Log.i("HERE", "here i com")
        Log.i("handleIntent", "Value1: ${intent?.extras}\nValue: ${intent?.getParcelableExtra("entry") as DiaryEntry?}")

        val intentt = Intent(this, MainActivity2::class.java)

        intentt.putExtra("entry", intent?.getParcelableExtra("entry") as DiaryEntry?)
        startActivity(intentt)
    }


    companion object {
        const val CHANNEL_GENERAL = "channel_general"
    }
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "General"
            val descriptionText = "General app notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_GENERAL, name, importance).apply {
                description = descriptionText
            }
            val nm = getSystemService(NotificationManager::class.java)
            nm?.createNotificationChannel(channel)
        }
    }
}