package com.faiz.arta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pindah?.setOnClickListener{
            val intent = Intent(this, AR_View_Gedung::class.java)
            startActivity(intent)
        }


    }

    override fun onStart() {
        super.onStart()
    }
}