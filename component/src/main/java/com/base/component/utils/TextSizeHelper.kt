package com.base.component.utils

import android.content.Context
import com.base.component.constant.PrefConstants
import com.base.core.helpers.LocalPrefManager

class TextSizeHelper {
    companion object {
        fun getSize(context: Context): Float {
            val sharedPreferences =
                context.getSharedPreferences("videogamesapp", Context.MODE_PRIVATE)
            var localPrefManager = LocalPrefManager(sharedPreferences)
            var sizeRate = localPrefManager.pull(PrefConstants.PREF_TEXT_SIZE, 2)
            return (sizeRate * 3 + 9).toFloat()
        }
    }
}

