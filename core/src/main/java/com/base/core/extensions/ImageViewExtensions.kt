package com.base.core.extensions

import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String, isCached: Boolean = false) {

    if (url == "") {
        return
    }

    val callback = object : Callback {
        override fun onSuccess() {
        }

        override fun onError() {
            //todo handle
            Log.d("PICASSO IMAGE LOAD", "ERROR")
        }
    }

    if (isCached) {
        Picasso.with(this.context)
            .load(url)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(this, callback)
    } else {
        Picasso.with(this.context).load(url).into(this, callback)
    }
}

fun ImageView.loadImage(resource: Int) {
    Picasso.with(this.context).load(resource).into(this)
}

