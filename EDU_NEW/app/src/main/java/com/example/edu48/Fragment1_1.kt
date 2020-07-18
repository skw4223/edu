package com.example.edu48

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class Fragment1_1: Fragment(){
    fun newInstance(): Fragment1_1{
        val args = Bundle()
        val frag = Fragment1_1()
        frag.arguments = args

        return frag
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.photo_1, container, false)
        return v
    }
}