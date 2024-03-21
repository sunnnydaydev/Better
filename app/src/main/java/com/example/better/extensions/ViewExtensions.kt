package com.example.better.extensions

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

/**
 * Create by SunnyDay /01/31 21:00:06
 */

fun RecyclerView.disableChangeAnimations() {
    (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
}

inline fun RecyclerView.addBottomVisibleListener(crossinline listener: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!recyclerView.canScrollVertically(1)) {
                listener()
            }
        }
    })
}