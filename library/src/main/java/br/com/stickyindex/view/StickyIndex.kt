package br.com.stickyindex.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater.from
import android.view.MotionEvent.ACTION_MOVE
import android.widget.RelativeLayout
import br.com.edsilfer.toolkit.core.util.InvalidData.Companion.isInvalid
import br.com.stickyindex.R
import br.com.stickyindex.model.RowStyle
import br.com.stickyindex.model.RowStyleMapper.map
import kotlinx.android.synthetic.main.stickyindex_view.view.*

/**
 * Created by edgarsf on 22/03/2018.
 */
class StickyIndex @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private lateinit var adapterSticky: StickyIndexAdapter
    lateinit var stickyStickyIndex: StickyIndexLayoutManager

    init {
        from(context).inflate(R.layout.stickyindex_view, this, true)
        applyStyle(map(context, context.obtainStyledAttributes(attrs, R.styleable.StickyIndex)))
    }

    private fun applyStyle(style: RowStyle?) {
        renderStickyList(context, style)
        if (style == null) return
        stickyStickyIndex.applyStyle(style)
        renderStickyWrapper(style)
        if (!isInvalid(style.height)) {
            val params = sticky_index_wrapper.layoutParams
            params.height = style.height.toInt()
            sticky_index_wrapper.layoutParams = params
        }
    }

    private fun renderStickyList(context: Context, styles: RowStyle?) {
        index_list.layoutManager = LinearLayoutManager(context)
        index_list.setOnTouchListener { _, event -> event.action == ACTION_MOVE }
        adapterSticky = StickyIndexAdapter(charArrayOf(), styles)
        index_list.adapter = adapterSticky
        stickyStickyIndex = StickyIndexLayoutManager(this, index_list)
    }

    private fun renderStickyWrapper(styles: RowStyle) {
        val params = sticky_index_wrapper.layoutParams
        params.width = styles.width.toInt()
        sticky_index_wrapper.layoutParams = params
        invalidate()
    }

    fun refresh(dataSet: CharArray) {
        adapterSticky.refresh(dataSet)
        adapterSticky.notifyDataSetChanged()
    }

    fun bindRecyclerView(rv: RecyclerView) {
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                stickyStickyIndex.update(rv, dy.toFloat())
            }
        })
    }
}