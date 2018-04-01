package br.com.stickyindex.sample.common.di.main

import br.com.stickyindex.sample.presentation.view.MainView
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by edgarsf on 18/03/2018.
 */
@Subcomponent(modules = [(MainViewModule::class)])
interface MainViewSubComponent : AndroidInjector<MainView> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainView>()

}