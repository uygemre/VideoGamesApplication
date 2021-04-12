package com.base.core.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Activity.drawable(@DrawableRes drawableResource: Int): Drawable? =
    ContextCompat.getDrawable(this, drawableResource)

fun Activity.color(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun <T> Context.openActivity(it: Class<T>, bundle: Bundle) {
    val intent = Intent(this, it)
    intent.putExtras(bundle)
    startActivity(intent)
}