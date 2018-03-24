package br.com.stickyindex.layout

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.RelativeLayout
import android.widget.TextView
import br.com.stickyindex.R
import br.com.stickyindex.model.Subscriber
import java.lang.Character.toLowerCase
import java.lang.Math.abs

/**
 * Created by edgar on 5/31/15.
 */
class IndexLayoutManager(
        container: RelativeLayout
) : Subscriber {

    val stickyIndex: TextView = container.findViewById<TextView>(R.id.sticky_index)
    private var indexList: RecyclerView? = null


    private fun updatePosition(referenceRv: RecyclerView) {
        val firstVisibleView = referenceRv.getChildAt(0)
        val actual = referenceRv.getChildPosition(firstVisibleView)
        (indexList!!.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(actual, firstVisibleView.top + 0)
    }

    // SUBSCRIBER INTERFACE ________________________________________________________________________

    /**
     * {@inheritDoc}
     */
    override fun update(referenceList: RecyclerView, dx: Float, dy: Float) {
        if (indexList == null) {
            return
        }

        updatePosition(referenceList)

        val firstVisibleView = indexList!!.getChildAt(0)
        val secondVisibleView = indexList!!.getChildAt(1)

        if (firstVisibleView == null || secondVisibleView == null) {
            return
        }

        val firstRowIndex = firstVisibleView.findViewById<TextView>(R.id.sticky_row_index)
        val secondRowIndex = secondVisibleView.findViewById<TextView>(R.id.sticky_row_index)

        val actual = indexList!!.getChildAdapterPosition(firstVisibleView)
        val next = actual + 1
        val last = actual + indexList!!.childCount

        resetIndex(firstRowIndex)
        if (dy > 0) {
            handleScrollingDown(next, last, firstRowIndex, secondRowIndex, firstVisibleView)
        } else {
            handleScrollUp(next, last, firstRowIndex, secondRowIndex, firstVisibleView)
        }

        if (stickyIndex.visibility == VISIBLE) {
            firstRowIndex.visibility = INVISIBLE
        }
    }

    private fun resetIndex(firstRowIndex: TextView) {
        stickyIndex.text = getIndexContext(firstRowIndex).toString().toUpperCase()
        stickyIndex.visibility = VISIBLE
        firstRowIndex.alpha = 1f
    }

    private fun handleScrollUp(next: Int, last: Int, firstRowIndex: TextView, secondRowIndex: TextView, firstVisibleView: View) {
        if (next <= last) {
            firstRowIndex.visibility = INVISIBLE
            if (isTransitioningToSecondRow(firstRowIndex, secondRowIndex)) {
                transitionToSecondRow(firstRowIndex, firstVisibleView, secondRowIndex)
            } else {
                secondRowIndex.visibility = INVISIBLE
            }
        }
    }

    private fun isTransitioningToSecondRow(firstRowIndex: TextView, secondRowIndex: TextView) =
            isHeader(firstRowIndex, secondRowIndex) &&
                    getIndexContext(firstRowIndex) != getIndexContext(secondRowIndex)

    private fun handleScrollingDown(next: Int, last: Int, firstRowIndex: TextView, secondRowIndex: TextView, firstVisibleView: View) {
        if (next <= last) {
            if (isHeader(firstRowIndex, secondRowIndex)) {
                transitionToSecondRow(firstRowIndex, firstVisibleView, secondRowIndex)
            } else {
                firstRowIndex.visibility = INVISIBLE
                stickyIndex.visibility = VISIBLE
            }
        }
    }

    private fun transitionToSecondRow(firstRowIndex: TextView, firstVisibleView: View, secondRowIndex: TextView) {
        stickyIndex.visibility = INVISIBLE
        firstRowIndex.visibility = VISIBLE
        firstRowIndex.alpha = computeFirstRowAlpha(firstVisibleView, firstRowIndex)
        secondRowIndex.visibility = VISIBLE
    }

    private fun computeFirstRowAlpha(firstVisibleView: View, firstRowIndex: TextView) =
            1 - abs(firstVisibleView.y) / firstRowIndex.height

    private fun getIndexContext(index: TextView): Char = index.text[0]

    private fun isHeader(prev: TextView, act: TextView): Boolean = !isSameChar(prev.text[0], act.text[0])

    private fun isSameChar(prev: Char, curr: Char): Boolean = toLowerCase(prev) == toLowerCase(curr)

    fun setIndexList(indexList: RecyclerView) {
        this.indexList = indexList
    }

}
