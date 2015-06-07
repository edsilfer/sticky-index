package br.com.stickyindex;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import br.com.stickyindex.adapter.IndexAdapter;
import br.com.stickyindex.layout.IndexLayoutManager;
import br.com.stickyindex.listener.IndexScrollListener;
import br.com.stickyindex.model.Subscriber;


/**
 * Created by edgar on 6/4/15.
 */
public class StickyIndex extends RelativeLayout {
    private RecyclerView indexList;
    private IndexScrollListener scrollListener;

    private IndexAdapter adapter;
    private IndexLayoutManager stickyIndex;

    // Constructors ________________________________________________________________________________
    public StickyIndex(Context context) {
        super(context);
        initialize(context);
    }

    public StickyIndex(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize (context);
    }

    public StickyIndex(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize (context);
    }

    public StickyIndex(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize (context);
    }

    private void initialize (Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.sticky_index, this, true);

        // Creates RecyclerView and its layout
        this.indexList = (RecyclerView) this.findViewById(R.id.index_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        indexList.setLayoutManager(linearLayoutManager);
        indexList.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        char[] dataSet = {};
        adapter = new IndexAdapter(dataSet);
        this.indexList.setAdapter(adapter);

        scrollListener = new IndexScrollListener();
        scrollListener.setOnScrollListener(indexList);

        this.stickyIndex = new IndexLayoutManager(this);
        this.stickyIndex.setIndexList(indexList);
        scrollListener.register(stickyIndex);
    }

    // UTIL ________________________________________________________________________________________
    // Interface that provides a way for registering/unregister new subscribers to the onScrollEvent
    public void subscribeForScrollListener(Subscriber nexSubscriber) {
        scrollListener.register(nexSubscriber);
    }

    public void removeScrollListenerSubscription(Subscriber oldSubscriber) {
        scrollListener.unregister(oldSubscriber);
    }

    // GETTERs AND SETTERs__________________________________________________________________________
    public void setDataSet(char[] dataSet) {
        this.adapter.setDataSet(dataSet);
        this.adapter.notifyDataSetChanged();
    }

    public IndexLayoutManager getStickyIndex() {
        return stickyIndex;
    }

    public void setOnScrollListener(RecyclerView rl) {
        scrollListener.setOnScrollListener(rl);
    }
}
