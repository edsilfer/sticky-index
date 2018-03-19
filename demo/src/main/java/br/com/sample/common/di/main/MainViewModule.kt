package br.com.sample.common.di.main

import android.arch.lifecycle.Lifecycle
import br.com.sample.domain.Router
import br.com.sample.presentation.presenter.MainPresenter
import br.com.sample.presentation.view.MainView
import dagger.Module
import dagger.Provides

/**
 * Created by edgarsf on 18/03/2018.
 */
@Module
class MainViewModule {

    @Provides
    fun providesLifecycle(mainView: MainView): Lifecycle = mainView.lifecycle

    @Provides
    fun providesRouter(mainView: MainView): Router = Router(mainView)

    @Provides
    fun providesPresenter(
            mainView: MainView,
            lifecycle: Lifecycle,
            router: Router
    ): MainPresenter = MainPresenter(lifecycle, mainView, router)

}