package br.com.stickyindex.view

import android.support.v7.widget.RecyclerView
import android.util.TypedValue.COMPLEX_UNIT_PX
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import br.com.edsilfer.toolkit.core.util.InvalidData.Companion.isInvalid
import br.com.stickyindex.R
import br.com.stickyindex.model.RowStyle
import br.com.stickyindex.model.StickyIndexViewHolder

/**
 * Encapsulates sticky letter display in a {@link RecyclerView}
 */
class StickyIndexAdapter(
        private var dataSet: CharArray,
        private val rowStyle: RowStyle?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * {@inheritDoc}
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stickyindex_details, parent, false)
        applyStyle(view)
        return StickyIndexViewHolder(view)
    }

    private fun applyStyle(view: View) {
        if (rowStyle == null) return
        if (!isInvalid(rowStyle.height)) setLayoutParams(view, rowStyle)
        val index = view.findViewById<TextView>(R.id.sticky_row_index)
        if (!isInvalid(rowStyle.color)) index.setTextColor(rowStyle.color)
        if (!isInvalid(rowStyle.size.toInt())) index.setTextSize(COMPLEX_UNIT_PX, rowStyle.size)
        if (!isInvalid(rowStyle.style)) index.setTypeface(null, rowStyle.style)
    }

    private fun setLayoutParams(view: View, rowStyle: RowStyle) {
        val params = view.layoutParams
        params.height = rowStyle.height.toInt()
        params.width = rowStyle.width.toInt()
        view.layoutParams = params
    }

    /**
     * {@inheritDoc}
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as StickyIndexViewHolder
        viewHolder.index.text = Character.toString(dataSet[position])
        if (isHeader(position)) viewHolder.index.visibility = VISIBLE
        else viewHolder.index.visibility = INVISIBLE
    }

    private fun isHeader(idx: Int) = idx == 0 || dataSet[idx - 1].toLowerCase() != dataSet[idx].toLowerCase()

    /**
     * {@inheritDoc}
     */
    override fun getItemCount() = dataSet.size

    /**
     * Refreshes sticky letters being displayed with new content
     */
    fun refresh(dataSet: CharArray) {
        this.dataSet = dataSet
    }
}
