package com.example.edu48

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class HelpFragment_1: Fragment(){
    fun newInstance(): HelpFragment_1{
        val args = Bundle()
        val frag = HelpFragment_1()
        frag.arguments = args

        return frag
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.help_1, container, false)
        return v
    }
}