package br.com.stickyindex.sample.presentation.view

import android.content.Context
import android.databinding.DataBindingUtil.inflate
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.stickyindex.sample.R
import br.com.stickyindex.sample.databinding.ContactsViewBinding
import br.com.stickyindex.sample.domain.model.Contact
import br.com.stickyindex.sample.presentation.presenter.ContactsPresenter
import br.com.stickyindex.sample.presentation.view.adapter.ContactsAdapter
import dagger.android.support.AndroidSupportInjection.inject
import kotlinx.android.synthetic.main.contacts_view.*
import javax.inject.Inject


/**
 * Displays {@link Contact} information in the shape of an interactive list
 */
class ContactsView : Fragment() {

    @Inject
    lateinit var presenter: ContactsPresenter
    @Inject
    lateinit var adapter: ContactsAdapter

    /**
     * {@inheritDoc}
     */
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = inflate<ContactsViewBinding>(
                inflater,
                R.layout.contacts_view,
                container,
                false
        )
        binding.presenter = presenter
        return binding.root
    }

    /**
     * {@inheritDoc}
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assemblyContactList()
    }

    /**
     * {@inheritDoc}
     */
    override fun onAttach(context: Context?) {
        inject(this)
        super.onAttach(context)
    }

    private fun assemblyContactList() {
        recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        recyclerView.adapter = adapter
        assemblyStickyIndexAndFastScroller()
    }

    private fun assemblyStickyIndexAndFastScroller() {
        stickyIndex.bindRecyclerView(recyclerView)
        fastScroller.bindRecyclerView(recyclerView)
    }

    /**
     * Maps the RecyclerView content to a {@link CharArray} to be used as sticky-indexes
     */
    private fun convertToIndexList(list: List<Contact>) = list.map { contact -> contact.name.toUpperCase()[0] }
            .toCollection(ArrayList())
            .toCharArray()


    /**
     * Load the given contacts in the list
     */
    fun loadContacts(contacts: List<Contact>) {
        adapter.refresh(contacts)
        stickyIndex.refresh(convertToIndexList(contacts))
    }

    /**
     * Scroll the list to the contact with the given name
     */
    fun scrollToContact(name: String) {
        recyclerView.layoutManager.smoothScrollToPosition(
                recyclerView,
                null,
                adapter.getIndexByContactName(name)
        )
    }

    /**
     * Proxys contact click event to presenter
     */
    fun onContactClicked() = adapter.addOnClickListener()
}
