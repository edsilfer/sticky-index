package br.com.stickyindexsample.view.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.stickyindex.layout.IndexLayoutManager;
import br.com.stickyindex.model.Subscriber;
import br.com.stickyindexsample.R;

public class FastScroller extends LinearLayout implements Subscriber {
    private static final int BUBBLE_ANIMATION_DURATION = 250;
    private static final int TRACK_SNAP_RANGE = 5;

    private RecyclerView recyclerView;
    private TextView bubble;
    private View handle;
    private int height;
    private ObjectAnimator bubbleAnimator = null;

    private IndexLayoutManager stickyIndex;


    // CONSTRUCTORS ________________________________________________________________________________
    public FastScroller(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise(context);
    }

    public FastScroller(final Context context) {
        super(context);
        initialise(context);
    }

    public FastScroller(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initialise(context);
    }

    private void initialise(Context context) {
        setOrientation(HORIZONTAL);
        setClipChildren(false);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.fastscroller, this, true);

        bubble = (TextView) findViewById(R.id.fastscroller_bubble);
        handle = findViewById(R.id.fastscroller_handle);

        bubble.setVisibility(INVISIBLE);
    }

    // CALLBACKs ___________________________________________________________________________________
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < handle.getX()) {
                    return false;
                }

                if (bubbleAnimator != null) {
                    bubbleAnimator.cancel();
                }

                if (bubble.getVisibility() == INVISIBLE) {
                    showBubble();
                }

                handle.setSelected(true);

            case MotionEvent.ACTION_MOVE:
                final float y = event.getY();

                setBubbleAndHandlePosition(y);
                setRecyclerViewPosition(y);

                return true;

            case MotionEvent.ACTION_UP:

            case MotionEvent.ACTION_CANCEL:
                handle.setSelected(false);
                hideBubble();

                return true;
        }

        return super.onTouchEvent(event);
    }

    // GETTERS AND SETTERS _________________________________________________________________________
    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        //this.recyclerView.setOnScrollListener(scrollListener);
    }

    private void setRecyclerViewPosition(float y) {
        if (recyclerView != null) {
            int itemCount = recyclerView.getAdapter().getItemCount();
            float proportion;

            if (handle.getY() == 0) {
                proportion = 0f;
            } else if (handle.getY() + handle.getHeight() >= height - TRACK_SNAP_RANGE) {
                proportion = 1f;
            } else {
                proportion = y / (float) height;
            }

            int targetPos = getValueInRange(0, itemCount - 1, (int) (proportion * (float) itemCount));

            //Log.d("AppLog", "targetPos:" + targetPos);

            if (stickyIndex != null) {
                stickyIndex.update(recyclerView, 0, 1);
            }

            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(targetPos, 0);
            String bubbleText = ((TextGetter) recyclerView.getAdapter()).getTextFromAdapter(targetPos);

            bubble.setText(bubbleText);
        }
    }

    private int getValueInRange(int min, int max, int value) {
        int minimum = Math.max(min, value);
        return Math.min(minimum, max);
    }

    public void setBubbleAndHandlePosition(float y) {
        int bubbleHeight = bubble.getHeight();
        int handleHeight = handle.getHeight();

        handle.setY(getValueInRange(0, height - handleHeight, (int) (y - handleHeight / 2)));
        bubble.setY(getValueInRange(0, height - bubbleHeight - handleHeight / 2, (int) (y - bubbleHeight)));
    }

    public int getFastScrollHeight() {
        return height;
    }

    public void setStickyIndex(IndexLayoutManager stickyIndex) {
        this.stickyIndex = stickyIndex;
    }

    // UI __________________________________________________________________________________________
    private void showBubble() {
        bubble.setVisibility(VISIBLE);

        if (bubbleAnimator != null)
            bubbleAnimator.cancel();

        bubbleAnimator = ObjectAnimator.ofFloat(bubble, "alpha", 0f, 1f).setDuration(BUBBLE_ANIMATION_DURATION);
        bubbleAnimator.start();
    }

    private void hideBubble() {
        if (bubbleAnimator != null)
            bubbleAnimator.cancel();

        bubbleAnimator = ObjectAnimator.ofFloat(bubble, "alpha", 1f, 0f).setDuration(BUBBLE_ANIMATION_DURATION);
        bubbleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                bubble.setVisibility(INVISIBLE);
                bubbleAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                bubble.setVisibility(INVISIBLE);
                bubbleAnimator = null;
            }
        });

        bubbleAnimator.start();
    }

    // SUBSCRIBER INTERFACE ________________________________________________________________________
    @Override
    public void update(RecyclerView rv, float dx, float dy) {
        View firstVisibleView=recyclerView.getChildAt(0);

        int firstVisiblePosition=recyclerView.getChildPosition(firstVisibleView);
        int visibleRange=recyclerView.getChildCount();
        int lastVisiblePosition=firstVisiblePosition+visibleRange;
        int itemCount=recyclerView.getAdapter().getItemCount();
        int position;

        if(firstVisiblePosition==0) {
            position = 0;
        } else if(lastVisiblePosition==itemCount) {
            position = itemCount;
        } else {
            position = (int) (((float) firstVisiblePosition / (((float) itemCount - (float) visibleRange))) * (float) itemCount);
        }

        float proportion=(float)position/(float)itemCount;

        setBubbleAndHandlePosition(getFastScrollHeight() * proportion);
    }
}
