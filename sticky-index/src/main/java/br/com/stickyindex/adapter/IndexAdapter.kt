package br.com.stickyindex.adapter

import android.support.v7.widget.RecyclerView
import android.util.TypedValue.COMPLEX_UNIT_PX
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import br.com.stickyindex.R

/**
 * Created by edgar on 6/4/15.
 */
class IndexAdapter(
        private var dataSet: CharArray,
        private val rowStyle: RowStyle?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sticky_row_details, parent, false)
        applyStyle(view)
        return ViewHolder(view)
    }

    private fun IndexAdapter.applyStyle(view: View) {
        if (rowStyle == null) {
            return
        }

        if (exists(rowStyle.height)) {
            setLayoutParams(view, rowStyle)
        }

        val index = view.findViewById<View>(R.id.sticky_row_index) as TextView

        if (exists(rowStyle.textColor)) {
            index.setTextColor(rowStyle.textColor)
        }

        if (exists(rowStyle.textSize.toInt())) {
            index.setTextSize(COMPLEX_UNIT_PX, rowStyle.textSize)
        }

        if (exists(rowStyle.textStyle)) {
            index.setTypeface(null, rowStyle.textStyle)
        }
    }

    private fun setLayoutParams(view: View, rowStyle: RowStyle) {
        val params = view.layoutParams
        params.height = rowStyle.height.toInt()
        params.width = rowStyle.stickyWidth.toInt()
        view.layoutParams = params
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        viewHolder.index.text = Character.toString(dataSet[position])
        if (isHeader(position)!!) {
            viewHolder.index.visibility = VISIBLE
        } else {
            viewHolder.index.visibility = INVISIBLE
        }
    }

    private fun isHeader(position: Int): Boolean? =
            position == 0 || !isSameChar(dataSet[position - 1], dataSet[position])!!

    private fun isSameChar(prev: Char, curr: Char): Boolean? {
        return Character.toLowerCase(prev) == Character.toLowerCase(curr)
    }

    override fun getItemCount(): Int = dataSet.size

    fun setDataSet(dataSet: CharArray) {
        this.dataSet = dataSet
    }

    private fun exists(number: Float): Boolean = number != (-1).toFloat()
    private fun exists(number: Int): Boolean = number != -1

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var index: TextView = v.findViewById<View>(R.id.sticky_row_index) as TextView

    }

    data class RowStyle(
            val height: Float,
            val stickyWidth: Float,
            val textColor: Int,
            val textSize: Float,
            val textStyle: Int
    )
}
