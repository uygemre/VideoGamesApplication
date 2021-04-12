package com.base.component.ui.videogamescards

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.base.component.R
import com.base.core.extensions.loadImage
import com.base.core.ui.recyclerview.DisplayItem
import com.base.core.ui.recyclerview.ViewHolder
import com.base.core.ui.recyclerview.ViewHolderBinder
import com.base.core.ui.recyclerview.ViewHolderFactory
import javax.inject.Inject

// Created by Emre UYGUN on 4/10/21

class VideoGamesCardsViewHolder(var view: View) : ViewHolder<VideoGamesCardsDTO>(view) {

    private var imgGames = view.findViewById<ImageView>(R.id.img_games)
    private var tvGamesName = view.findViewById<TextView>(R.id.tv_games_name)
    private var tvGamesRating = view.findViewById<TextView>(R.id.tv_games_rating)
    private var tvGamesReleased = view.findViewById<TextView>(R.id.tv_games_released)
    private var rootView = view.findViewById<CardView>(R.id.rootView)

    @SuppressLint("SetTextI18n")
    override fun bind(item: VideoGamesCardsDTO) {
        item.background_image?.let {
            imgGames.loadImage(it)
        }
        item.name?.let {
            tvGamesName.text = it
        }
        item.released?.let {
            tvGamesReleased.text = "Released: $it"
        }
        item.rating?.let {
            tvGamesRating.text = "Rating: $it"
        }
        rootView.setOnClickListener {
            itemClickListener?.invoke(item, adapterPosition)
        }

    }

    class HolderFactory @Inject constructor() : ViewHolderFactory {
        override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            VideoGamesCardsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_games_card,
                    parent,
                    false
                )
            )
    }

    class BinderFactory @Inject constructor() : ViewHolderBinder {
        override fun bind(holder: RecyclerView.ViewHolder, item: DisplayItem) {
            (holder as VideoGamesCardsViewHolder).bind(item as VideoGamesCardsDTO)
        }
    }
}