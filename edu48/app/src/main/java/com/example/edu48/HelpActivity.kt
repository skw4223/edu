package com.example.edu48

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help2)
        val helppagerAdapter = helpPagerAdapter(supportFragmentManager)
        val helppager = findViewById<ViewPager>(R.id.helpVP)
        helppager.adapter = helppagerAdapter
    }
}
