package br.com.sample.presentation.view

import android.content.Context
import android.databinding.DataBindingUtil.inflate
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.sample.R
import br.com.sample.databinding.ContactsViewBinding
import br.com.sample.domain.model.Contact
import br.com.sample.presentation.presenter.ContactsPresenter
import br.com.sample.presentation.view.adapter.ContactsAdapter
import br.com.sample.presentation.view.layout.RecyclerViewOnItemClickListener
import dagger.android.support.AndroidSupportInjection.inject
import kotlinx.android.synthetic.main.contacts_view.*
import javax.inject.Inject


/**
 * Created by edgar on 6/7/15.
 */
class ContactsView : Fragment() {

    @Inject
    lateinit var presenter: ContactsPresenter

    private lateinit var contactsAdapter: ContactsAdapter

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
    override fun onAttach(context: Context?) {
        inject(this)
        super.onAttach(context)
    }

    fun loadContacts(contacts: List<Contact>) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        contactsAdapter = ContactsAdapter(contacts, context!!)
        recyclerView.adapter = contactsAdapter
        recyclerView.addOnItemTouchListener(
                RecyclerViewOnItemClickListener(context!!, { _, position ->
                    presenter.onContactClick(contactsAdapter.getContact(position))
                })
        )
        addStickIndex(contacts.toMutableList())
    }

    private fun addStickIndex(contacts: MutableList<Contact>) {
        sticky_index_container.setDataSet(getIndexList(contacts))
        sticky_index_container.setOnScrollListener(recyclerView)
        sticky_index_container.subscribeForScrollListener(fast_scroller)
        addFastScroller()
    }

    private fun addFastScroller() {
        fast_scroller.setRecyclerView(recyclerView)
        fast_scroller.setStickyIndex(sticky_index_container.stickyIndex)
    }

    /**
     * Maps the RecyclerView content to a {@link CharArray} to be used as sticky-indexes
     */
    private fun getIndexList(list: List<Contact>): CharArray {
        return list.map { contact -> contact.name.toUpperCase()[0] }
                .toCollection(ArrayList())
                .toCharArray()
    }

    fun updateRecyclerViewFromSearchSelection(contactName: String) {
        val contact = contactsAdapter.getContactByName(contactName)
        val contactIdx = contactsAdapter.dataSet.indexOf(contact)
        recyclerView.layoutManager.smoothScrollToPosition(recyclerView, null, contactIdx)
    }
}
