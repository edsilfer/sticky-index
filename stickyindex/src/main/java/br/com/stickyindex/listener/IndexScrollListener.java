package br.com.stickyindex.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.stickyindex.model.Publisher;
import br.com.stickyindex.model.Subscriber;

/**
 * Created by edgar on 5/31/15.
 */
public class IndexScrollListener extends RecyclerView.OnScrollListener implements Publisher {

    private RecyclerView recyclerView;
    private RecyclerViewDataObject currentRowState;
    private List<Subscriber> subscribers;

    // CONSTRUCTOR _________________________________________________________________________________
    public IndexScrollListener(Context context) {
        this.subscribers = new ArrayList<Subscriber>();
    }

    // CALLBACKS ___________________________________________________________________________________
    @Override
    public void onScrolled(RecyclerView rv, int dx, int dy) {
        View firstVisibleView = recyclerView.getChildAt(0);

        int visibleRange = recyclerView.getChildCount();
        int actual = recyclerView.getChildPosition(firstVisibleView);
        int last = actual + visibleRange;

        int itemCount = recyclerView.getAdapter().getItemCount();
        int position;

        if (actual == 0) {
            position = 0;
        } else if (last == itemCount) {
            position = itemCount;
        } else {
            position = (int) (((float) actual / (((float) itemCount - (float) visibleRange))) * (float) itemCount);
        }

        currentRowState = new RecyclerViewDataObject(
                position,
                dy
        );

        notifySubscribers();
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
    public void notifySubscribers () {
        for (Subscriber subscriber :  subscribers) {
            subscriber.update(currentRowState.position, currentRowState.dy);
        }
    }

    // GETTERs AND SETTERs _________________________________________________________________________
    public void setRecyRecyclerView(RecyclerView rv) {
        this.recyclerView = rv;
        recyclerView.setOnScrollListener(this);
    }

    // NESTED DATA OBJECT___________________________________________________________________________
    public class RecyclerViewDataObject {
        private Integer position;
        private Integer dy;

        public RecyclerViewDataObject (Integer position, Integer dy) {
            this.position = position;
            this.dy = dy;
        }

        public Integer getPosition() {
            return position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        public Integer getDy() {
            return dy;
        }

        public void setDy(Integer dy) {
            this.dy = dy;
        }
    }
}