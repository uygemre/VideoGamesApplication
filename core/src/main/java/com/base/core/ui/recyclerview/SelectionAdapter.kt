package com.base.core.ui.recyclerview

import com.base.core.ui.recyclerview.DisplayItem

interface SelectionAdapter {
    fun select(pos: Int)
    fun clear()
    fun getSelectedItemCount(): Int
    fun getSelectedItems(): List<DisplayItem>
}