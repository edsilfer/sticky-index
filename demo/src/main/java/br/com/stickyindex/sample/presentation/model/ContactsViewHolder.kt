package br.com.stickyindex.sample.presentation.model

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import br.com.stickyindex.sample.R
import com.pkmmte.view.CircularImageView

/**
 * {@link ViewHolder} for an user's phone Contact
 */
class ContactsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    internal var container: LinearLayout = view.findViewById(R.id.container)
    internal var index: TextView = view.findViewById(R.id.textView_firstLetter)
    internal var name: TextView = view.findViewById(R.id.textView_contactName)
    internal var thumbnail: CircularImageView = view.findViewById(R.id.circularView_thumbnail)
}