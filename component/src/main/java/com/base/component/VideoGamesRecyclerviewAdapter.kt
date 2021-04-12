package com.base.component

import com.base.component.constant.RecyclerviewAdapterConstant
import com.base.core.ui.recyclerview.DefaultDisplayItemComperator
import com.base.core.ui.recyclerview.RecyclerViewAdapter

class VideoGamesRecyclerviewAdapter {

    fun getAdapter() = _adapter

    private val _adapter = RecyclerViewAdapter(
        itemComparator = DefaultDisplayItemComperator(),
        viewBinderFactoryMap = RecyclerviewAdapterConstant().binderFactoryMap,
        viewHolderFactoryMap = RecyclerviewAdapterConstant().holderFactoryMap
    )
}
