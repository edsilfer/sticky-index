package br.com.stickyindex.model

import android.support.v7.widget.RecyclerView

/**
 * Created by edgar on 5/31/15.
 */
interface Publisher {
    fun register(newObserver: Subscriber)
    fun unregister(existentObserver: Subscriber)
    fun notifySubscribers(rv: RecyclerView, dx: Int, dy: Int)
}
