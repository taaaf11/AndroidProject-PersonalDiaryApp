package com.example.personaldiaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.personaldiaryapp.room.DiaryVM

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    override fun onDestroy() {
//        ViewModelProvider(this).get(DiaryVM::class).setLoggedInStatus(false)
//        super.onDestroy()
//    }
}