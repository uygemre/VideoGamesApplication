package com.base.component.ui.gamesdetailparagraph

import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.base.component.R
import com.base.core.ui.recyclerview.DisplayItem
import com.base.core.ui.recyclerview.ViewHolder
import com.base.core.ui.recyclerview.ViewHolderBinder
import com.base.core.ui.recyclerview.ViewHolderFactory
import javax.inject.Inject

// Created by Emre UYGUN on 4/10/21

class GamesDetailParagraphViewHolder(var view: View) : ViewHolder<GamesDetailParagraphDTO>(view) {

    private var tvGamesDescription = view.findViewById<TextView>(R.id.tv_games_description)

    override fun bind(item: GamesDetailParagraphDTO) {
        item?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvGamesDescription.text = Html.fromHtml(it.description)
            } else {
                tvGamesDescription.text = Html.fromHtml(it.description)
            }
        }
    }

    class HolderFactory @Inject constructor() : ViewHolderFactory {
        override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            GamesDetailParagraphViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_games_detail_paragraph,
                    parent,
                    false
                )
            )
    }

    class BinderFactory @Inject constructor() : ViewHolderBinder {
        override fun bind(holder: RecyclerView.ViewHolder, item: DisplayItem) {
            (holder as GamesDetailParagraphViewHolder).bind(item as GamesDetailParagraphDTO)
        }
    }
}