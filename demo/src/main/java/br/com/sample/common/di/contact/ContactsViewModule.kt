package br.com.sample.common.di.contact

import android.arch.lifecycle.Lifecycle
import android.support.v7.app.AppCompatActivity
import br.com.sample.data.ContactsDataSource
import br.com.sample.domain.Router
import br.com.sample.presentation.presenter.ContactsPresenter
import br.com.sample.presentation.view.ContactsView
import dagger.Module
import dagger.Provides

/**
 * Created by edgarsf on 18/03/2018.
 */
@Module
class ContactsViewModule {

    @Provides
    fun providesLifecycle(contactsView: ContactsView): Lifecycle = contactsView.lifecycle

    @Provides
    fun providesContactsDataSource(
            contactsView: ContactsView
    ): ContactsDataSource = ContactsDataSource(contactsView.context!!)

    @Provides
    fun providesRouter(contactsView: ContactsView): Router = Router(contactsView.activity as AppCompatActivity)

    @Provides
    fun providesPresenter(
            lifecycle: Lifecycle,
            contactsView: ContactsView,
            datasource: ContactsDataSource,
            router: Router
    ): ContactsPresenter =
            ContactsPresenter(lifecycle, contactsView, datasource, router)

}