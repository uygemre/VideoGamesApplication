package com.base.core.ui.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.base.core.ui.recyclerview.DisplayItem

interface ViewHolderBinder {
    fun bind(holder: RecyclerView.ViewHolder, item: DisplayItem)
}