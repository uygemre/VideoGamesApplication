package com.base.videogames.ui.pages.detail

import android.content.Intent
import android.os.Bundle
import com.base.videogames.R
import com.base.videogames.ui.base.activity.BaseActivity
import com.base.videogames.ui.base.fragment.BaseFragment
import com.base.videogames.ui.common.enums.IntentBundleKeyEnum
import com.base.videogames.ui.pages.detail.viewmodel.DetailActivityViewModel
import com.base.videogames.ui.pages.gamescarddetail.GamesCardDetailFragment

// Created by Emre UYGUN on 4/10/21

class DetailActivity: BaseActivity<DetailActivityViewModel>() {

    override val viewModelClass = DetailActivityViewModel::class.java
    override val layoutViewRes = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openRelatedFragment(intent)
    }

    private fun openRelatedFragment(intent: Intent?) {
        intent?.let {
            when (it.getStringExtra(IntentBundleKeyEnum.DETAIL_KEY.toString())) {
                IntentBundleKeyEnum.DETAIL_GAMES_CARD.toString() -> {
                    navigateToFragment(GamesCardDetailFragment.newInstance(it.extras))
                }
                IntentBundleKeyEnum.DETAIL_SLIDER.toString() -> {
                    navigateToFragment(GamesCardDetailFragment.newInstance(it.extras))
                }
            }
        }
    }

    private fun navigateToFragment(fragment: BaseFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.detail_container, fragment)
        transaction.commit()
    }
}