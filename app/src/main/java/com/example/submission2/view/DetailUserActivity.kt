package com.example.submission2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.submission2.R

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
    }
}