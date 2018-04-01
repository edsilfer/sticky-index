package br.com.stickyindex.model

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import br.com.stickyindex.R

/**
 * A {@link ViewHolder} for sticky-index letters
 */
class StickyIndexViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    internal var index: TextView = view.findViewById(R.id.sticky_row_index)
}