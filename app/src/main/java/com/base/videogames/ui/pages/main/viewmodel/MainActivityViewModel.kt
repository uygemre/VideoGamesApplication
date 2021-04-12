package com.base.videogames.ui.pages.main.viewmodel

import com.base.core.ioc.scopes.ActivityScope
import com.base.videogames.ui.base.viewmodel.BaseActivityViewModel
import com.base.videogames.ui.pages.main.repository.MainActivityRepository
import javax.inject.Inject

// Created by Emre UYGUN on 4/10/21

@ActivityScope
class MainActivityViewModel @Inject constructor(
    private val repository: MainActivityRepository
) : BaseActivityViewModel() {

}