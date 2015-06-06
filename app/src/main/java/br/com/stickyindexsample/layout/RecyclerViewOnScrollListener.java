package br.com.stickyindexsample.layout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.stickyindexsample.model.Publisher;
import br.com.stickyindexsample.model.Subscriber;

/**
 * Created by edgar on 5/31/15.
 */
public class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener implements Publisher {

    private RecyclerView recyclerView;
    private RecyclerViewDataObject currentRowState;
    private List<Subscriber> subscribers;


    public RecyclerViewOnScrollListener (Context context) {
        this.subscribers = new ArrayList<Subscriber>();
    }

    public void setRecyRecyclerView(RecyclerView rv) {
        this.recyclerView = rv;
        recyclerView.setOnScrollListener(this);
    }

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

    // Observer Interfacer _________________________________________________________________________
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
            subscriber.update(currentRowState);
        }
    }

    // STRUCT ______________________________________________________________________________________
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