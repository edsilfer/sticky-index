package br.com.stickyindexsample.model;

import br.com.stickyindexsample.layout.RecyclerViewOnScrollListener;

/**
 * Created by edgar on 5/31/15.
 */
public interface Subscriber {
    public void update(RecyclerViewOnScrollListener.RecyclerViewDataObject currentRowState);
}
