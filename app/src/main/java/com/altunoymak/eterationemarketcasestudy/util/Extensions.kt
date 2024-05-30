package com.altunoymak.eterationemarketcasestudy.util

import android.content.Context
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.altunoymak.eterationemarketcasestudy.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun View.clickWithDebounce(debounceTime: Long = Constants.debounceTime, action: (View) -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action(v)
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun ImageView.downloadFromUrl(url: String?, context: Context) {
    val options = RequestOptions()
        .error(ContextCompat.getDrawable(context, R.color.grey))

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .dontAnimate()
        .fitCenter().into(this)
}