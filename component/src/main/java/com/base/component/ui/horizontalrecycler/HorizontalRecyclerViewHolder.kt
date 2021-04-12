package com.base.component.ui.horizontalrecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.component.R
import com.base.component.constant.RecyclerviewAdapterConstant
import com.base.core.extensions.setup
import com.base.core.ui.recyclerview.*
import com.bekawestberg.loopinglayout.library.LoopingLayoutManager
import javax.inject.Inject

class HorizontalRecyclerViewHolder(var view: View) : ViewHolder<HorizontalRecyclerDTO>(view) {

    private var rcView: RecyclerView = view.findViewById(R.id.horizontal_recyclerview)

    private val layoutManager =
        LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)

    private var loopingLayoutManager: RecyclerView.LayoutManager =
        LoopingLayoutManager(view.context, LoopingLayoutManager.HORIZONTAL, false)

    override fun bind(item: HorizontalRecyclerDTO) {

        val adapter = RecyclerViewAdapter(
            itemComparator = DefaultDisplayItemComperator(),
            viewBinderFactoryMap = RecyclerviewAdapterConstant().binderFactoryMap,
            viewHolderFactoryMap = RecyclerviewAdapterConstant().holderFactoryMap
        )
        adapter.itemClickListener = { _item, _position ->
            itemClickListener?.invoke(_item, _position)
        }

        if (item.isCircleLooping) {
            rcView.layoutManager = loopingLayoutManager
            rcView.adapter = adapter
        } else {
            rcView.setup(adapter, layoutManager)
        }

        adapter.updateAllItems(item.list)
    }

    class HolderFactory @Inject constructor() : ViewHolderFactory {
        override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            HorizontalRecyclerViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_horizontal_recycler,
                    parent,
                    false
                )
            )
    }

    class BinderFactory @Inject constructor() : ViewHolderBinder {
        override fun bind(holder: RecyclerView.ViewHolder, item: DisplayItem) {
            (holder as HorizontalRecyclerViewHolder).bind(item as HorizontalRecyclerDTO)
        }
    }
}
