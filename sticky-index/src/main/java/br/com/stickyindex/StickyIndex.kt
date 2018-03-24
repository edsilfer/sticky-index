package br.com.stickyindex

import android.content.Context
import android.support.v4.content.ContextCompat.getColor
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.TypedValue.COMPLEX_UNIT_PX
import android.view.LayoutInflater
import android.view.MotionEvent.ACTION_MOVE
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import br.com.stickyindex.model.RowStyle
import br.com.stickyindex.model.Subscriber

/**
 * Created by edgarsf on 22/03/2018.
 */
class StickyIndex @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private lateinit var indexList: RecyclerView
    private var scrollListener: IndexScrollListener? = null
    private var adapter: IndexAdapter? = null
    var stickyIndex: IndexLayoutManager? = null
        private set

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.sticky_index, this, true)
        val styles = getRowStyle(context, attrs)
        renderStickyList(context, styles)
        renderStickyWrapper(styles)
        addScrollListener()
        applyStyle(styles)
    }

    private fun getRowStyle(context: Context, attrs: AttributeSet?): RowStyle? {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StickyIndex)
            val result = RowStyle(
                    typedArray.getDimension(R.styleable.StickyIndex_rowHeight, -1f),
                    typedArray.getDimension(R.styleable.StickyIndex_stickyWidth, -1f),
                    typedArray.getColor(R.styleable.StickyIndex_android_textColor, getColor(context, R.color.index_text_color)),
                    typedArray.getDimension(R.styleable.StickyIndex_android_textSize, 26f),
                    typedArray.getInt(R.styleable.StickyIndex_android_textStyle, -1)
            )
            typedArray.recycle()
            return result
        } else {
            return null
        }
    }

    private fun renderStickyList(context: Context, styles: RowStyle?) {
        indexList = this.findViewById<View>(R.id.index_list) as RecyclerView
        indexList.layoutManager = LinearLayoutManager(context)
        indexList.setOnTouchListener { _, event -> event.action == ACTION_MOVE }
        adapter = IndexAdapter(charArrayOf(), styles)
        indexList.adapter = adapter
    }

    private fun renderStickyWrapper(styles: RowStyle?) {
        val stickyWrapper = findViewById<View>(R.id.sticky_index_wrapper) as LinearLayout
        val params = stickyWrapper.layoutParams
        params.width = styles!!.stickyWidth.toInt()
        stickyWrapper.layoutParams = params
        invalidate()
    }

    private fun addScrollListener() {
        scrollListener = IndexScrollListener()
        scrollListener?.setOnScrollListener(indexList)
        stickyIndex = IndexLayoutManager(this)
        stickyIndex!!.setIndexList(indexList)
        scrollListener!!.register(stickyIndex!!)
    }

    private fun applyStyle(styles: RowStyle?) {
        if (styles == null) {
            return
        }

        if (styles.height != (-1).toFloat()) {
            setLayoutParams(styles)
        }

        if (styles.textSize != (-1).toFloat()) {
            stickyIndex!!.stickyIndex.setTextSize(COMPLEX_UNIT_PX, styles.textSize)
        }

        stickyIndex!!.stickyIndex.setTextColor(styles.textColor)

        if (styles.textStyle != -1) {
            stickyIndex?.stickyIndex?.setTypeface(null, styles.textStyle)
        }
    }

    private fun setLayoutParams(styles: RowStyle) {
        val stickyIndexWrapper = this.findViewById<View>(R.id.sticky_index_wrapper) as LinearLayout
        val params = stickyIndexWrapper.layoutParams
        params.height = styles.height.toInt()
        stickyIndexWrapper.layoutParams = params
    }

    fun setDataSet(dataSet: CharArray) {
        adapter?.setDataSet(dataSet)
        adapter?.notifyDataSetChanged()
    }

    fun setOnScrollListener(recyclerView: RecyclerView) {
        scrollListener?.setOnScrollListener(recyclerView)
    }

    fun subscribeForScrollListener(nexSubscriber: Subscriber) {
        scrollListener?.register(nexSubscriber)
    }

    fun removeScrollListenerSubscription(oldSubscriber: Subscriber) {
        scrollListener?.unregister(oldSubscriber)
    }
}