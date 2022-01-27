package com.crud.roomdb_14_faza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class opening : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var mid: ImageView
        lateinit var top: TextView
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opening)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)

        //di definisikan
        top = findViewById(R.id.text1)
        top.startAnimation(stb)

        mid = findViewById(R.id.icon_splash)
        mid.startAnimation(stb)

        //1 detik = 1000
        val splashTo = 3500
        val homeIntent = Intent(this@opening, MainActivity::class.java)

        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, splashTo.toLong())
    }
}