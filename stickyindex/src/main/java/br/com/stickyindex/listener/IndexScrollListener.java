package br.com.stickyindex.listener;


import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.stickyindex.model.Publisher;
import br.com.stickyindex.model.Subscriber;

/**
 * Created by edgar on 5/31/15.
 */
public class IndexScrollListener extends RecyclerView.OnScrollListener implements Publisher {
    private List<Subscriber> subscribers;

    // CONSTRUCTOR _________________________________________________________________________________
    public IndexScrollListener() {
        this.subscribers = new ArrayList<Subscriber>();
    }

    // CALLBACKS ___________________________________________________________________________________
    @Override
    public void onScrolled(RecyclerView rv, int dx, int dy) {
        notifySubscribers(rv, dx, dy);
    }

    // OBSERVER INTERFACE __________________________________________________________________________
    @Override
    public void register(Subscriber newObserver) {
        subscribers.add(newObserver);
    }

    @Override
    public void unregister(Subscriber existentObserver) {
        subscribers.remove(existentObserver);
    }

    @Override
    public void notifySubscribers (RecyclerView rv, int dx, int dy) {
        for (Subscriber subscriber :  subscribers) {
            subscriber.update(rv, dx, dy);
        }
    }

    // GETTERs AND SETTERs _________________________________________________________________________
    public void setOnScrollListener(RecyclerView rv) {
        rv.setOnScrollListener(this);
    }
}