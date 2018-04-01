package br.com.stickyindex.sample.common.di.contact

import android.arch.lifecycle.Lifecycle
import android.support.v7.app.AppCompatActivity
import br.com.edsilfer.toolkit.core.components.SchedulersCoupler
import br.com.stickyindex.sample.data.ContactsDataSource
import br.com.stickyindex.sample.domain.Router
import br.com.stickyindex.sample.presentation.presenter.ContactsPresenter
import br.com.stickyindex.sample.presentation.view.ContactsView
import br.com.stickyindex.sample.presentation.view.adapter.ContactsAdapter
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
    fun providesAdapter(
            contactsView: ContactsView,
            schedulers: SchedulersCoupler
    ): ContactsAdapter = ContactsAdapter(schedulers, contactsView.context!!)

    @Provides
    fun providesPresenter(
            lifecycle: Lifecycle,
            contactsView: ContactsView,
            datasource: ContactsDataSource,
            router: Router,
            schedulers: SchedulersCoupler
    ): ContactsPresenter =
            ContactsPresenter(lifecycle, contactsView, datasource, router, schedulers)

}