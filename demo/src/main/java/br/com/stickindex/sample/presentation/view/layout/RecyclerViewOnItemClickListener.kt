package br.com.stickindex.sample.presentation.view.layout

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class RecyclerViewOnItemClickListener(
        context: Context,
        private val listener: (view: View, position: Int) -> Unit
) : RecyclerView.OnItemTouchListener {

    private val gestureDetector: GestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return true
        }
    })

    /**
     * {@inheritDoc}
     */
    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (childView != null && gestureDetector.onTouchEvent(e)) {
            listener(childView, view.getChildPosition(childView))
        }
        return false
    }

    /**
     * {@inheritDoc}
     */
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        //DO NOTHING
    }

    /**
     * {@inheritDoc}
     */
    override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {
        //DO NOTHING
    }
}