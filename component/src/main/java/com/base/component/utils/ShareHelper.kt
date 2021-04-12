package com.base.component.utils

import android.content.Context
import android.content.Intent

class ShareHelper {
    companion object {
        fun shareNews(context: Context, text: String) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)

            context.startActivity(shareIntent)
        }
    }
}