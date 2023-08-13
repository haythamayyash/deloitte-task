package com.example.deloittetask.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.deloittetask.R

fun View?.debounceClick(debounceTime: Long = 1200L, action: (view: View) -> Unit) {
    var lastClickTime = 0L
    this?.setOnClickListener {
        if (System.currentTimeMillis() > lastClickTime + debounceTime) {
            action.invoke(this)
        }
        lastClickTime = System.currentTimeMillis()
    }
}

fun ImageView.loadImage(url: String) {
    Glide.with(this.context).load(url).placeholder(R.drawable.ic_place_holder).into(this)
}