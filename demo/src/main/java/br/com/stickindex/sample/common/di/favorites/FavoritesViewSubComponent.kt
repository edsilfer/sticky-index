package br.com.stickindex.sample.common.di.favorites

import br.com.stickindex.sample.presentation.view.FavoritesView
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by edgarsf on 18/03/2018.
 */
@Subcomponent(modules = [(FavoritesViewModule::class)])
interface FavoritesViewSubComponent : AndroidInjector<FavoritesView> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FavoritesView>()

}