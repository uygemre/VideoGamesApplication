package com.base.videogames.ui.pages.detail.viewmodel

import com.base.videogames.ui.base.viewmodel.BaseActivityViewModel
import com.base.videogames.ui.pages.detail.repository.DetailActivityRepository
import javax.inject.Inject

// Created by Emre UYGUN on 4/10/21

class DetailActivityViewModel @Inject constructor(
    private val repository: DetailActivityRepository
) : BaseActivityViewModel() {

}