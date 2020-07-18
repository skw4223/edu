package com.example.edu48

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.ViewPager
import com.example.edu48.game.AlphabetActivity
import com.example.edu48.game.ElementActivity
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
            val dlgBuilder = AlertDialog.Builder(this)
            val dlgView = layoutInflater.inflate(R.layout.activity_help2, null)
            dlgBuilder.setView(dlgView)
            dlgBuilder.setPositiveButton("확인", DialogInterface.OnClickListener{ dialog, whichButton -> })
            val b = dlgBuilder.create()
            b.show()
        }
        btn1.setOnClickListener{
            if(pager.currentItem == 0) {
                val elementIntent = Intent(this, ElementActivity::class.java)
                startActivity(elementIntent)
            } else{
                val alphabetIntent = Intent(this, AlphabetActivity::class.java)
                startActivity(alphabetIntent)
            }
        }
        mainLeft.setOnClickListener{
            if( pager.currentItem == 0 ){
                pager.currentItem = 1
            }else{
                pager.currentItem = 0
            }
        }
        mainRight.setOnClickListener{
            if( pager.currentItem == 0 ){
                pager.currentItem = 1
            }else{
                pager.currentItem = 0
            }
        }

    }
}
