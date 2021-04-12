package com.base.core.extensions

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun TabLayout.onTabSelected(onTabSelectedListener: (TabLayout.Tab) -> Unit) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            onTabSelectedListener.invoke(tab!!)
        }

    })
}

inline fun <E : Any, T : List<E>> T?.whenNotNullNorEmpty(func: (T) -> Unit): Unit {
    if (this != null && this.isNotEmpty()) {
        func(this)
    }
}

fun ViewPager.onPageScrolled(onPageChangeListener: (Int) -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            onPageChangeListener.invoke(position)
        }

        override fun onPageSelected(position: Int) {
        }
    })
}

fun View.clickWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.showSoftInput(this, SHOW_IMPLICIT)
}


fun View.gone() {
    visibility = View.GONE
}

fun View.visibile() {
    visibility = View.VISIBLE
}

fun View.inVisibile() {
    visibility = View.INVISIBLE
}

fun TextView.extAutoSize(minSize: Int, maxSize: Int) =
    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
        this,
        minSize,
        maxSize,
        1,
        TypedValue.COMPLEX_UNIT_SP
    )

fun EditText.extUpdateValidate(text: String?): Boolean {

    if (this.text.toString() != text) {
        return true
    }
    return false
}

fun LinearLayout.extPercent(
    child1: TextView,
    child2: TextView,
    child3: TextView,
    percent1: Int?,
    percent2: Int?,
    percent3: Int?,
    isRelatedMatch: Boolean
) {
    var percentHome: Int? = null
    var percentAway: Int? = null
    var percentDraw: Int? = null

    percent1?.let {
        percentHome = if (percent1 == 0) 1
        else percent1

        val childParam = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
            percentHome!!.toFloat()
        )
        child1.layoutParams = childParam
        child1.text = percent1.toString()
    }

    percent2?.let {
        percentAway = if (percent2 == 0) 1
        else percent2

        val childParam = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
            percentAway!!.toFloat()
        )
        child2.layoutParams = childParam
        child2.text = percent2.toString()
    }

    if (isRelatedMatch) {
        percent3?.let {
            percentDraw = if (percent3 == 0) 1
            else percent3

            val childParam = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                percentDraw!!.toFloat()
            )
            child3.layoutParams = childParam
            child3.text = percent3.toString()
        }
    } else {
        child3.gone()
    }

    val totalPercent = (percentHome ?: 0) + (percentAway ?: 0) + (percentDraw ?: 0)
    this.weightSum = totalPercent.toFloat()
}

fun ImageView.extTotalGoalsPercent(
    totalGoalsPercent: Int,
    textView: TextView
) {
    textView.text = totalGoalsPercent.toString()

    if (totalGoalsPercent == 0) {
        this.setBackgroundColor(Color.parseColor("#00000000"))
        textView.setTextColor(Color.parseColor("#1fa1f3"))
    } else {
        var height = totalGoalsPercent * 10

        when (height) {
            10 -> height = 15
        }

        this.layoutParams.height = height * 3
    }
}