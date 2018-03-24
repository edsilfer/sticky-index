package br.com.stickyindex.model

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import br.com.stickyindex.R

/**
 * Created by edgarsf on 24/03/2018.
 */
class StickyIndexViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    internal var index: TextView = v.findViewById<TextView>(R.id.sticky_row_index)
}