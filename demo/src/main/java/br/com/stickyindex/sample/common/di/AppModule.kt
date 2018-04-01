package br.com.stickyindex.sample.common.di

import br.com.edsilfer.toolkit.core.components.SchedulersCoupler
import br.com.edsilfer.toolkit.core.components.SchedulersCouplerImpl
import br.com.stickyindex.sample.common.di.contact.ContactsViewSubComponent
import br.com.stickyindex.sample.common.di.favorites.FavoritesViewSubComponent
import br.com.stickyindex.sample.common.di.main.MainViewSubComponent
import dagger.Module
import dagger.Provides

/**
 * Created by edgarsf on 18/03/2018.
 */
@Module(
        subcomponents = [
            (MainViewSubComponent::class),
            (ContactsViewSubComponent::class),
            (FavoritesViewSubComponent::class)
        ]
)
class AppModule {

    @Provides
    fun providesSchedulersCoupler(): SchedulersCoupler = SchedulersCouplerImpl()

}