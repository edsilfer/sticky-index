package br.com.stickyindex.sample.common.di.contact

import br.com.stickyindex.sample.presentation.view.ContactsView
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by edgarsf on 18/03/2018.
 */
@Subcomponent(modules = [(ContactsViewModule::class)])
interface ContactsViewSubComponent : AndroidInjector<ContactsView> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ContactsView>()

}