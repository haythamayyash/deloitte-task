package com.example.deloittetask.util

import android.view.View

fun View?.debounceClick(debounceTime: Long = 1200L, action: (view: View) -> Unit) {
    var lastClickTime = 0L
    this?.setOnClickListener {
        if (System.currentTimeMillis() > lastClickTime + debounceTime) {
            action.invoke(this)
        }
        lastClickTime = System.currentTimeMillis()
    }
}