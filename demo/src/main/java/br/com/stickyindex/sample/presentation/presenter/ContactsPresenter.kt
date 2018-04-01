package br.com.stickyindex.sample.presentation.presenter

import android.arch.lifecycle.Lifecycle
import android.view.View
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import br.com.edsilfer.toolkit.core.components.BasePresenter
import br.com.edsilfer.toolkit.core.components.SchedulersCoupler
import br.com.stickyindex.sample.data.ContactsDataSource
import br.com.stickyindex.sample.domain.Router
import br.com.stickyindex.sample.presentation.view.ContactsView
import timber.log.Timber.e

/**
 * Encapsulates the business logic triggered from {@link ContactView}
 */
class ContactsPresenter(
        lifecycle: Lifecycle,
        private val view: ContactsView,
        private val datasource: ContactsDataSource,
        private val router: Router,
        private val schedulers: SchedulersCoupler
) : BasePresenter(lifecycle) {

    /**
     * {@inheritDoc}
     */
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

    /**
     * Handles {@link ContactsView} FAB clicks
     */
    fun onFABClick(view: View) {
        makeText(view.context, "FAB was clicked!", LENGTH_SHORT).show()
    }
}