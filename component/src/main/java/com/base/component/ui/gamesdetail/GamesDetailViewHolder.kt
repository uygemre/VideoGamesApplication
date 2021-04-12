package com.base.component.ui.gamesdetail

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.base.component.R
import com.base.core.extensions.loadImage
import com.base.core.helpers.LocalPrefManager
import com.base.core.ui.recyclerview.DisplayItem
import com.base.core.ui.recyclerview.ViewHolder
import com.base.core.ui.recyclerview.ViewHolderBinder
import com.base.core.ui.recyclerview.ViewHolderFactory
import javax.inject.Inject

// Created by Emre UYGUN on 4/10/21

class GamesDetailViewHolder(var view: View) : ViewHolder<GamesDetailDTO>(view) {

    private lateinit var localPrefManager: LocalPrefManager

    private var imgGames = view.findViewById<ImageView>(R.id.img_games)
    private var tvGamesName = view.findViewById<TextView>(R.id.tv_games_name)
    private var tvGamesReleased = view.findViewById<TextView>(R.id.tv_games_released)
    private var tvMetacriticRate = view.findViewById<TextView>(R.id.tv_metacritic_rate)
    private var imgFavorites = view.findViewById<ImageButton>(R.id.img_favorites)

    @SuppressLint("SetTextI18n")
    override fun bind(item: GamesDetailDTO) {
        if (view.context is AppCompatActivity) {
            val holder = view.context as AppCompatActivity
            val sharedPreferences =
                holder.getSharedPreferences("videogamesapp", Context.MODE_PRIVATE)
            localPrefManager = LocalPrefManager(sharedPreferences)
        }

        item.name.let {
            tvGamesName.text = it
        }
        item.background_image.let {
            imgGames.loadImage(it ?: "")
        }
        item.metacritic.let {
            tvMetacriticRate.text = "Rating: $it"
        }
        item.released.let {
            tvGamesReleased.text = "Released: $it"
        }
        imgFavorites.setOnClickListener {
            itemViewClickListener?.invoke(it, item, adapterPosition)
        }

        if (localPrefManager.pull(item.slug ?: "", "") == "") {
            imgFavorites.setBackgroundResource(R.drawable.ic_favorites_unselected)
        } else {
            imgFavorites.setBackgroundResource(R.drawable.ic_favorites_selected)
        }
    }

    class HolderFactory @Inject constructor() : ViewHolderFactory {
        override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
            GamesDetailViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_games_detail,
                    parent,
                    false
                )
            )
    }

    class BinderFactory @Inject constructor() : ViewHolderBinder {
        override fun bind(holder: RecyclerView.ViewHolder, item: DisplayItem) {
            (holder as GamesDetailViewHolder).bind(item as GamesDetailDTO)
        }
    }
}