package com.base.component.ioc.module

import com.base.component.VideoGamesRecyclerviewAdapter
import dagger.Module
import dagger.Provides

@Module
class RecyclerAdapterModule {

	@Provides
	fun provideAdapter(): VideoGamesRecyclerviewAdapter {
		return VideoGamesRecyclerviewAdapter()
	}
}
