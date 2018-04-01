package br.com.stickyindex.sample.common.di.favorites

import android.arch.lifecycle.Lifecycle
import br.com.stickyindex.sample.presentation.presenter.FavoritesPresenter
import br.com.stickyindex.sample.presentation.view.FavoritesView
import dagger.Module
import dagger.Provides

/**
 * Created by edgarsf on 18/03/2018.
 */
@Module
class FavoritesViewModule {
    @Provides
    fun providesLifecycle(favoritesView: FavoritesView): Lifecycle = favoritesView.lifecycle

    @Provides
    fun providesPresenter(lifecycle: Lifecycle): FavoritesPresenter = FavoritesPresenter(lifecycle)
}