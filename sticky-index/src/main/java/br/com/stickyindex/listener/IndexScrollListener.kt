package br.com.stickyindex.listener


import android.support.v7.widget.RecyclerView
import br.com.stickyindex.model.Publisher
import br.com.stickyindex.model.Subscriber
import java.util.*

/**
 * Created by edgar on 5/31/15.
 */
class IndexScrollListener : RecyclerView.OnScrollListener(), Publisher {
    private val subscribers: MutableList<Subscriber> = ArrayList()

    /**
     * {@inheritDoc}
     */
    override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
        notifySubscribers(rv, dx, dy)
    }

    /**
     * {@inheritDoc}
     */
    override fun register(newObserver: Subscriber) {
        subscribers.add(newObserver)
    }

    /**
     * {@inheritDoc}
     */
    override fun unregister(existentObserver: Subscriber) {
        subscribers.remove(existentObserver)
    }

    /**
     * {@inheritDoc}
     */
    override fun notifySubscribers(rv: RecyclerView, dx: Int, dy: Int) {
        subscribers.forEach { it.update(rv, dx.toFloat(), dy.toFloat()) }
    }

    fun setOnScrollListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(this)
    }
}