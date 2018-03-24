package br.com.stickindex.sample.presentation.model

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import br.com.stickindex.sample.R
import com.pkmmte.view.CircularImageView

/**
 * Created by edgarsf on 24/03/2018.
 */
class ContactsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    internal var container: LinearLayout = view.findViewById<LinearLayout>(R.id.container)
    internal var index: TextView = view.findViewById<TextView>(R.id.textView_firstLetter)
    internal var name: TextView = view.findViewById<TextView>(R.id.textView_contactName)
    internal var thumbnail: CircularImageView = view.findViewById<CircularImageView>(R.id.circularView_thumbnail)
}