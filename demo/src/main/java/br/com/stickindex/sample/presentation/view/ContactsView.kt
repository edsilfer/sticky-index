package br.com.stickindex.sample.presentation.view

import android.content.Context
import android.databinding.DataBindingUtil.inflate
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.stickindex.sample.R
import br.com.stickindex.sample.databinding.ContactsViewBinding
import br.com.stickindex.sample.domain.model.Contact
import br.com.stickindex.sample.presentation.presenter.ContactsPresenter
import br.com.stickindex.sample.presentation.view.adapter.ContactsAdapter
import dagger.android.support.AndroidSupportInjection.inject
import kotlinx.android.synthetic.main.contacts_view.*
import javax.inject.Inject


/**
 * Created by edgar on 6/7/15.
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
        assemblyContactList(emptyList())
    }

    /**
     * {@inheritDoc}
     */
    override fun onAttach(context: Context?) {
        inject(this)
        super.onAttach(context)
    }

    private fun assemblyContactList(contacts: List<Contact>) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        addStickIndex(contacts)
    }

    private fun addStickIndex(contacts: List<Contact>) {
        sticky_index_container.setDataSet(convertToIndexList(contacts))
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
    private fun convertToIndexList(list: List<Contact>): CharArray {
        return list.map { contact -> contact.name.toUpperCase()[0] }
                .toCollection(ArrayList())
                .toCharArray()
    }

    fun loadContacts(contacts: List<Contact>) {
        adapter.refresh(contacts)
        sticky_index_container.setDataSet(convertToIndexList(contacts))
    }

    fun scrollToContact(name: String) {
        recyclerView.layoutManager.smoothScrollToPosition(
                recyclerView,
                null,
                adapter.getIndexByContactName(name)
        )
    }

    fun onContactClicked() = adapter.addOnClickListener()
}
