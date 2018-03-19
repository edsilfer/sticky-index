package br.com.sample.common.di

import br.com.sample.common.di.contact.ContactsViewSubComponent
import br.com.sample.common.di.favorites.FavoritesViewSubComponent
import br.com.sample.common.di.main.MainViewSubComponent
import dagger.Module

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

}