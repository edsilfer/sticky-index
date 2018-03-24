package br.com.stickindex.sample.presentation.presenter

import android.arch.lifecycle.Lifecycle
import android.view.View
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import br.com.edsilfer.toolkit.core.components.BasePresenter
import br.com.edsilfer.toolkit.core.components.SchedulersCoupler
import br.com.stickindex.sample.data.ContactsDataSource
import br.com.stickindex.sample.domain.Router
import br.com.stickindex.sample.presentation.view.ContactsView
import timber.log.Timber.e

/**
 * Created by edgarsf on 18/03/2018.
 */
class ContactsPresenter(
        lifecycle: Lifecycle,
        private val view: ContactsView,
        private val datasource: ContactsDataSource,
        private val router: Router,
        private val schedulers: SchedulersCoupler
) : BasePresenter(lifecycle) {
    override fun onStart() {
        super.onStart()
        loadContacts()
        onContactClicked()
    }

    private fun loadContacts() {
        addDisposable(datasource.list()
                .compose(schedulers.convertToAsyncMaybe())
                .doOnSuccess { view.loadContacts(it) }
                .doOnError { e(it.message) }
                .subscribe())
    }

    private fun onContactClicked() {
        addDisposable(view.onContactClicked()
                .doOnNext {
                    router.launchUserDetails(it)
                    makeText(view.context, "You clicked in ${it.name}", LENGTH_SHORT).show()
                }
                .subscribe()
        )
    }

    fun onFABClick(view: View) {
        makeText(view.context, "FAB was clicked!", LENGTH_SHORT).show()
    }
}