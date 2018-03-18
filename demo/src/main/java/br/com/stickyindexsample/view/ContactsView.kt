package br.com.stickyindexsample.view

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import br.com.stickyindex.StickyIndex
import br.com.stickyindexsample.R
import br.com.stickyindexsample.common.Contact
import br.com.stickyindexsample.common.Router.launchUserDetails
import br.com.stickyindexsample.data.ContactsDAO
import br.com.stickyindexsample.view.adapter.RecyclerViewAdapter
import br.com.stickyindexsample.view.layout.FastScroller
import br.com.stickyindexsample.view.layout.RecyclerViewOnItemClickListener
import kotlinx.android.synthetic.main.fragment_contacts.*
import java.util.*

/**
 * Created by edgar on 6/7/15.
 */
class ContactsView : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_contacts, container, false)
        assemblyRecyclerView(rootView)
        assemblyFabListener(rootView)
        return rootView
    }

    private fun assemblyRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val contacts = ContactsDAO.listMappedContacts()
        recyclerView.adapter = RecyclerViewAdapter(ArrayList(contacts), context!!)
        addStickIndex(recyclerView, contacts, view)
        addContactClickListener(recyclerView)
    }

    private fun addStickIndex(recyclerView: RecyclerView, contacts: MutableList<Contact>, view: View) {
        val stickyIndex = view.findViewById<StickyIndex>(R.id.sticky_index_container)
        stickyIndex.setDataSet(getIndexList(contacts))
        stickyIndex.setOnScrollListener(recyclerView)
        stickyIndex.subscribeForScrollListener(fast_scroller)

        val fastScroller = view.findViewById<FastScroller>(R.id.fast_scroller)
        fastScroller.setRecyclerView(recyclerView)
        fastScroller.setStickyIndex(stickyIndex.stickyIndex)
    }

    private fun addContactClickListener(recyclerView : RecyclerView) {
        recyclerView.addOnItemTouchListener(RecyclerViewOnItemClickListener(
                context!!,
                { _, position ->
                    val adapter = recyclerView.adapter as RecyclerViewAdapter
                    launchUserDetails(context!!, adapter.getContact(position))
                }
        ))
    }

    private fun assemblyFabListener(view: View) {
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { makeText(activity, "FAB was clicked!", LENGTH_SHORT).show() }
    }

    private fun getIndexList(list: List<Contact>): CharArray {
        val result = CharArray(list.size)
        for ((index, character) in list.withIndex()) {
            result[index] = Character.toUpperCase(character.name[0])
        }
        return result
    }

    fun updateRecyclerViewFromSearchSelection(contactName: String) {
        val adapter = recyclerView.adapter as RecyclerViewAdapter
        val contact = adapter.getContactByName(contactName)
        val contactIdx = adapter.dataSet.indexOf(contact)
        recyclerView.layoutManager.smoothScrollToPosition(recyclerView, null, contactIdx)
    }
}
