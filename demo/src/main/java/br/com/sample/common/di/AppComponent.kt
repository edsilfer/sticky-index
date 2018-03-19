package br.com.sample.common.di

import android.app.Application
import br.com.sample.common.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by edgarsf on 18/03/2018.
 */
@Singleton
@Component(
        modules = [
            (AppModule::class),
            (ActivityBinding::class),
            (AndroidInjectionModule::class)
        ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}