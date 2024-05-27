package com.altunoymak.eterationemarketcasestudy.util.component

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.altunoymak.eterationemarketcasestudy.R

class CustomProgressBar(context : Context, attrs : AttributeSet) : ConstraintLayout(context,attrs) {
    init {
        inflate(context, R.layout.custom_progress_bar,this)
    }
}