package com.base.core.ui.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var itemClickListener: ((item: DisplayItem, position: Int) -> Unit)? = null
    var itemViewClickListener: ((view:View, item: DisplayItem, position: Int) -> Unit)? = null
    var itemLongClickListener: ((item: DisplayItem, position: Int) -> Boolean)? = null
	abstract fun bind(item: T)
	open fun onAttachAdapter() {}
	open fun onDetachAdapter() {}
}

