package br.com.stickyindex;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import br.com.stickyindex.adapter.IndexAdapter;
import br.com.stickyindex.layout.IndexLayoutManager;
import br.com.stickyindex.listener.IndexScrollListener;


/**
 * Created by edgar on 6/4/15.
 */
public class StickyIndex extends RelativeLayout {
    private RecyclerView referenceList;
    private RecyclerView indexList;

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

        IndexScrollListener scrollListener = new IndexScrollListener(context);
        scrollListener.setRecyRecyclerView(indexList);
        indexList.setOnScrollListener(scrollListener);

        this.stickyIndex = new IndexLayoutManager(this);
        this.stickyIndex.setRecyclerView(indexList);

        // Observer Design Pattern
        scrollListener.register(stickyIndex);
    }

    // UTIL ________________________________________________________________________________________
    private void onIndexListScrolled (RecyclerView passedRecyclerView, int dx, int dy) {
        View firstVisibleView = passedRecyclerView.getChildAt(0);

        int actual = passedRecyclerView.getChildPosition(firstVisibleView);

        ((LinearLayoutManager) indexList.getLayoutManager()).scrollToPositionWithOffset(actual, firstVisibleView.getTop() + 0);

        stickyIndex.update(actual, dy);
    }

    // GETTERs AND SETTERs__________________________________________________________________________
    public void setDataSet(char[] dataSet) {
        this.adapter.setDataSet(dataSet);
        this.adapter.notifyDataSetChanged();
    }

    public void setReferenceList(RecyclerView rl) {
        this.referenceList = rl;

        this.referenceList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private View currentFirstVisibleItem;
            private int currentVisibleItemCount;

            @Override
            public void onScrolled(RecyclerView passedRecyclerView, int dx, int dy) {
                super.onScrolled(passedRecyclerView, dx, dy);

                currentFirstVisibleItem = passedRecyclerView.getChildAt(0);
                currentVisibleItemCount = passedRecyclerView.getChildPosition(currentFirstVisibleItem);

                onIndexListScrolled (passedRecyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                this.isScrollCompleted(newState);
            }

            private void isScrollCompleted(int currentState) {
                if (this.currentVisibleItemCount > 0 && currentState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // TODO: FIX BUG WHEN FLYING RECYCLER VIEW
                }
            }
        });
    }
}
