package com.example.edu48

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, LottieActivity::class.java)
        startActivity(intent)
        val pagerAdapter = PagerAdapter1(supportFragmentManager)
        val pager = findViewById<ViewPager>(R.id.viewPager1)
        pager.adapter = pagerAdapter

        helpBtn.setOnClickListener{
            val dlgView = layoutInflater.inflate(R.layout.activity_help2, null)
            val dlgBuilder = AlertDialog.Builder(this)
            dlgBuilder.setView(dlgView)
            dlgBuilder.show()
        }
    }
}
