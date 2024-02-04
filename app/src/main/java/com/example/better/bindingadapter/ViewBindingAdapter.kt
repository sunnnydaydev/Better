package com.example.better.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Create by Carry /02/04 18:22:23
 */
@BindingAdapter(value = ["isVisible"])
fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter(value = ["isInvisible"])
fun View.setInvisible(isInvisible: Boolean) {
    visibility = if (isInvisible) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter(value = ["isGone"])
fun View.setGone(isGone: Boolean) {
    visibility = if (isGone) View.GONE else View.VISIBLE
}