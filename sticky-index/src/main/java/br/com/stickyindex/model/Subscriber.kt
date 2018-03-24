package br.com.stickyindex.model

import android.support.v7.widget.RecyclerView

/**
 * Created by edgar on 5/31/15.
 */
interface Subscriber {
    fun update(rv: RecyclerView, dx: Float, dy: Float)
}
