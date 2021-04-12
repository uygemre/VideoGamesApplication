package com.base.core.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(@StringRes resource: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resource, duration).show()
}

fun Context.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text.orEmpty(), duration).show()
}