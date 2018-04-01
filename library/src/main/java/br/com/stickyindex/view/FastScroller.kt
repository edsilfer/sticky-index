package br.com.stickyindex.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ObjectAnimator.ofFloat
import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.widget.LinearLayout
import br.com.stickyindex.R
import kotlinx.android.synthetic.main.fastscroller_view.view.*
import java.lang.Math.max
import java.lang.Math.min

/**
 * Custom layout that adds fast scroller capabilities to a {@link RecyclerView}
 */
class FastScroller @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val BUBBLE_ANIMATION_DURATION = 250
        private const val TRACK_SNAP_RANGE = 5
    }

    private var scrollHeight: Int = 0
    private lateinit var bubbleAnimator: ObjectAnimator
    private lateinit var recyclerView: RecyclerView

    init {
        orientation = HORIZONTAL
        clipChildren = false
        LayoutInflater.from(context).inflate(R.layout.fastscroller_view, this, true)
        bubble.visibility = INVISIBLE
    }

    /**
     * {@inheritDoc}
     */
    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        this.scrollHeight = height
    }

    /**
     * {@inheritDoc}
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            ACTION_DOWN -> handleActionDown(event)
            ACTION_MOVE -> handleActionMove(event)
            ACTION_UP, ACTION_CANCEL -> handleActionUpCancel()
            else -> super.onTouchEvent(event)
        }
    }

    private fun handleActionDown(event: MotionEvent): Boolean {
        if (event.x < handle.x) return false
        if (bubble.visibility == INVISIBLE) showBubble()
        handle.isSelected = true
        setBubbleAndHandlePosition(event.y)
        setRecyclerViewPosition(event.y)
        return true
    }

    private fun handleActionMove(event: MotionEvent): Boolean {
        setBubbleAndHandlePosition(event.y)
        setRecyclerViewPosition(event.y)
        return true
    }

    private fun handleActionUpCancel(): Boolean {
        handle.isSelected = false
        hideBubble()
        return true
    }

    /**
     * Binds the fast-scroller to the provided {@link RecyclerView}, so they scroll event are synchronized
     */
    fun bindRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                refreshScrollPosition()
            }
        })
    }

    private fun setRecyclerViewPosition(y: Float) {
        val targetPos = computeTargetPosition(y)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        layoutManager.scrollToPositionWithOffset(targetPos, 0)
        bubble.text = (recyclerView.adapter as FastScrollerLabelPublisher).getLabel(targetPos)
    }

    private fun computeTargetPosition(y: Float): Int {
        val itemCount = recyclerView.adapter.itemCount
        return getValueInRange(0, itemCount - 1, (computeProportion(y) * itemCount.toFloat()).toInt())
    }

    private fun computeProportion(y: Float) = when {
        handle.y == 0f -> 0f
        handle.y + handle.height >= scrollHeight - TRACK_SNAP_RANGE -> 1f
        else -> y / scrollHeight.toFloat()
    }

    private fun getValueInRange(min: Int, max: Int, value: Int) = min(max(min, value), max)

    private fun setBubbleAndHandlePosition(y: Float) {
        val bubbleHeight = bubble.height
        val handleHeight = handle.height
        handle.y = getValueInRange(0, scrollHeight - handleHeight, (y - handleHeight / 2).toInt()).toFloat()
        bubble.y = getValueInRange(0, scrollHeight - bubbleHeight - handleHeight / 2, (y - bubbleHeight).toInt()).toFloat()
    }

    private fun showBubble() {
        bubble.visibility = VISIBLE
        bubbleAnimator = createBubbleAnimator(0f, 1f)
        bubbleAnimator.start()
    }

    private fun hideBubble() {
        bubbleAnimator = createBubbleAnimator(1f, 0f)
        bubbleAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                bubble.visibility = INVISIBLE
            }

            override fun onAnimationCancel(animation: Animator) {
                bubble.visibility = INVISIBLE
            }
        })
        bubbleAnimator.start()
    }

    private fun createBubbleAnimator(v1: Float, v2: Float) =
            ofFloat(bubble, "alpha", v1, v2)
                    .setDuration(BUBBLE_ANIMATION_DURATION.toLong())

    private fun refreshScrollPosition() {
        val firstVisibleView = recyclerView.getChildAt(0)
        val firstVisiblePosition = recyclerView.getChildAdapterPosition(firstVisibleView)
        val visibleRange = recyclerView.childCount
        val lastVisiblePosition = firstVisiblePosition + visibleRange
        val itemCount = recyclerView.adapter.itemCount
        val position = computePosition(firstVisiblePosition, lastVisiblePosition, itemCount, visibleRange)
        val proportion = position.toFloat() / itemCount.toFloat()
        setBubbleAndHandlePosition(scrollHeight * proportion)
    }

    private fun computePosition(
            firstVisiblePosition: Int,
            lastVisiblePosition: Int,
            itemCount: Int,
            visibleRange: Int
    ) = when {
        firstVisiblePosition == 0 -> 0
        lastVisiblePosition == itemCount -> itemCount
        else -> (firstVisiblePosition.toFloat() / (itemCount.toFloat() - visibleRange.toFloat()) * itemCount.toFloat()).toInt()
    }
}
