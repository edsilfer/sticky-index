package br.com.sample.presentation.presenter

import android.arch.lifecycle.Lifecycle
import android.view.View
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import br.com.edsilfer.toolkit.core.components.BasePresenter
import br.com.sample.data.ContactsDataSource
import br.com.sample.domain.Router
import br.com.sample.domain.model.Contact
import br.com.sample.presentation.view.ContactsView
import timber.log.Timber

/**
 * Created by edgarsf on 18/03/2018.
 */
class ContactsPresenter(
        lifecycle: Lifecycle,
        private val view: ContactsView,
        private val datasource: ContactsDataSource,
        private val router : Router
) : BasePresenter(lifecycle) {

    override fun onStart() {
        super.onStart()
        // TODO: CHANGE TO RX
        view.loadContacts(datasource.listMappedContacts())
    }

    fun onFABClick(view: View) {
        Timber.i("entrei aqui")
        makeText(view.context, "FAB was clicked!", LENGTH_SHORT).show()
    }

    fun onContactClick(contact: Contact) {
        router.launchUserDetails(contact)
    }

}