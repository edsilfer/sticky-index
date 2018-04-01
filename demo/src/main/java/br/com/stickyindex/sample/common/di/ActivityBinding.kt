package br.com.stickyindex.sample.common.di

import android.app.Activity
import android.support.v4.app.Fragment
import br.com.stickyindex.sample.common.di.contact.ContactsViewSubComponent
import br.com.stickyindex.sample.common.di.favorites.FavoritesViewSubComponent
import br.com.stickyindex.sample.common.di.main.MainViewSubComponent
import br.com.stickyindex.sample.presentation.view.ContactsView
import br.com.stickyindex.sample.presentation.view.FavoritesView
import br.com.stickyindex.sample.presentation.view.MainView
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

/**
 * Created by edgarsf on 18/03/2018.
 */
@Module
abstract class ActivityBinding {

    @Binds
    @IntoMap
    @ActivityKey(MainView::class)
    abstract fun bindMainView(builder: MainViewSubComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @IntoMap
    @FragmentKey(ContactsView::class)
    abstract fun bindContactsView(builder: ContactsViewSubComponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(FavoritesView::class)
    abstract fun bindFavoritesView(builder: FavoritesViewSubComponent.Builder): AndroidInjector.Factory<out Fragment>
}