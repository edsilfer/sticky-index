package br.com.stickyindex.sample.presentation.view.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Color.parseColor
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.TextView
import br.com.edsilfer.toolkit.core.components.SchedulersCoupler
import br.com.stickyindex.sample.R
import br.com.stickyindex.sample.domain.model.Contact
import br.com.stickyindex.sample.presentation.model.ContactsViewHolder
import br.com.stickyindex.view.FastScrollerLabelPublisher
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * Encapsulates {@link Contact} display in the a {@link RecylerView}
 */
class ContactsAdapter(
        private val schedulers: SchedulersCoupler,
        private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), FastScrollerLabelPublisher {

    private var contacts = emptyList<Contact>()
    private val listener: PublishSubject<Contact> = PublishSubject.create()

    /**
     * {@inheritDoc}
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.contacts_list_item, parent, false)
        return ContactsViewHolder(view)
    }

    /**
     * {@inheritDoc}
     */
    override fun onBindViewHolder(rawHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = rawHolder as ContactsViewHolder
        val contact = contacts[position]
        holder.name.text = contact.name
        holder.index.text = contact.name[0].toString().toUpperCase()
        if (contact.thumbnail != null) {
            assembleContactThumbnail(holder, contact)
        } else {
            assembleContactPlaceholder(holder)
        }
        holder.container.setOnClickListener { listener.onNext(contact) }
    }

    private fun assembleContactThumbnail(contactHolder: ContactsViewHolder, contact: Contact) {
        contactHolder.index.visibility = INVISIBLE
        // Picasso doesn't handle contact thumbnail decoding properly
        contact.getThumbnailAsBitmap(context)
                .compose(schedulers.convertToAsyncSingle())
                .doOnSuccess { bitmap ->
                    contactHolder.thumbnail.setImageBitmap(bitmap)
                    contactHolder.thumbnail.setImageURI(contact.thumbnail)
                }
                .subscribe()
    }

    private fun assembleContactPlaceholder(contactHolder: ContactsViewHolder) {
        contactHolder.thumbnail.setImageResource(R.drawable.circle_icon)
        contactHolder.index.visibility = TextView.VISIBLE
        contactHolder.index.setTextColor(parseColor("#ffffff"))
        contactHolder.index.textSize = 26f
        (contactHolder.thumbnail.drawable as GradientDrawable).setColor(getRandomColor())
    }

    private fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    /**
     * {@inheritDoc}
     */
    override fun getItemCount() = contacts.size

    /**
     * Provides a label to be placed inside fast scroll buble
     */
    override fun getLabel(index: Int) = contacts[index].name[0].toString().toUpperCase()

    /**
     * Refresh contact list being displayed
     */
    fun refresh(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    /**
     * Return the list index for the given contact name
     */
    fun getIndexByContactName(name: String) = contacts.indexOf(contacts.first { it.name == name })

    /**
     * Enroll for item click notifications
     */
    fun addOnClickListener() = listener
}
