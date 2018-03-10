package br.com.stickyindex.model;

import android.support.v7.widget.RecyclerView;

/**
 * Created by edgar on 5/31/15.
 */
public interface Publisher {
    public void register(Subscriber newObserver);
    public void unregister(Subscriber existentObserver);
    public void notifySubscribers(RecyclerView rv, int dx, int dy);
}
