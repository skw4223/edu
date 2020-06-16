package com.example.edu48

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class helpPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    val PAGE_MAX_CNT = 2

    override fun getCount(): Int {
        return PAGE_MAX_CNT
    }

    override fun getItem(position: Int): Fragment {
        val fragment = when (position) {
            1 -> HelpFragment_1().newInstance()
            else -> HelpFragment_2().newInstance()
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val title = when (position) {
            1 -> "one"
            else -> "main"
        }
        return title
    }
}