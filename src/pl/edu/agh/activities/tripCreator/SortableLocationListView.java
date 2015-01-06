package pl.edu.agh.activities.tripCreator;

import android.animation.*;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import pl.edu.agh.domain.locations.Location;

import java.util.ArrayList;

/**
 * Created by Sławek on 2015-01-06.
 */
public class SortableLocationListView extends ListView {

    private final int SMOOTH_SCROLL_AMOUNT_AT_EDGE = 15;
    private final int MOVE_DURATION = 150;
    private final int LINE_THICKNESS = 15;

    private int totalOffset = 0;
    private int mDownY = -1;
    private int mDownX = -1;

    private boolean cellIsMobile = false;
    private boolean isMobileScrolling = false;
    private int smoothScrollAmountAtEdge = 0;

    private final int INVALID_ID = -1;
    private long aboveItemId = INVALID_ID;
    private long mobileItemId = INVALID_ID;
    private long belowItemId = INVALID_ID;

    private BitmapDrawable hoverCell;
    private Rect hoverCellCurrentBounds;
    private Rect hoverCellOriginalBounds;

    private final int INVALID_POINTER_ID = -1;
    private int activePointerId = INVALID_POINTER_ID;

    private int lastEventY = -1;

    private boolean isWaitingForScrollFinish = false;
    private int scrollState = OnScrollListener.SCROLL_STATE_IDLE;

    public ArrayList<Location> locationList;

    public SortableLocationListView(Context context) {
        super(context);
        init(context);
    }

    public SortableLocationListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SortableLocationListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        setOnItemLongClickListener(longClickListener);
        setOnScrollListener(mScrollListener);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        smoothScrollAmountAtEdge = (int)(SMOOTH_SCROLL_AMOUNT_AT_EDGE / metrics.density);
    }

    private OnItemLongClickListener longClickListener  = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
            totalOffset = 0;

            int position = pointToPosition(mDownX, mDownY);
            int itemNumber = position - getFirstVisiblePosition();

            View selectedView = getChildAt(itemNumber);
            mobileItemId = getAdapter().getItemId(position);
            hoverCell = getAndAddHoverView(selectedView);
            selectedView.setVisibility(INVISIBLE);
            cellIsMobile = true;

            updateNeighborViewsForID(mobileItemId);

            return true;

        }
    };

    private void swapElements(ArrayList arrayList, int indexOne, int indexTwo) {
        Object temp = arrayList.get(indexOne);
        arrayList.set(indexOne, arrayList.get(indexTwo));
        arrayList.set(indexTwo, temp);
    }

    private void updateNeighborViewsForID(long itemID) {
        int position = getPositionForID(itemID);
        SortableLocationAdapter adapter = ((SortableLocationAdapter)getAdapter());
        aboveItemId = adapter.getItemId(position - 1);
        belowItemId = adapter.getItemId(position + 1);
    }

    public int getPositionForID (long itemID) {
        View v = getViewForID(itemID);
        if (v == null) {
            return -1;
        } else {
            return getPositionForView(v);
        }
    }
    public View getViewForID (long itemID) {
        int firstVisiblePosition = getFirstVisiblePosition();
        SortableLocationAdapter adapter = ((SortableLocationAdapter)getAdapter());
        for(int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int position = firstVisiblePosition + i;
            long id = adapter.getItemId(position);
            if (id == itemID) {
                return view;
            }
        }
        return null;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (hoverCell != null) {
            hoverCell.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                mDownX = (int)event.getX();
                mDownY = (int)event.getY();
                activePointerId = event.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                if (activePointerId == INVALID_POINTER_ID) {
                    break;
                }
                int pointerIndex = event.findPointerIndex(activePointerId);

                lastEventY = (int) event.getY(pointerIndex);
                int deltaY = lastEventY - mDownY;

                if (cellIsMobile) {
                    hoverCellCurrentBounds.offsetTo(hoverCellOriginalBounds.left,
                            hoverCellOriginalBounds.top + deltaY + totalOffset);
                    hoverCell.setBounds(hoverCellCurrentBounds);
                    invalidate();

                    handleCellSwitch();

                    isMobileScrolling = false;
                    handleMobileCellScroll();

                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                touchEventsEnded();
                break;
            case MotionEvent.ACTION_CANCEL:
                touchEventsCancelled();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                /* If a multitouch event took place and the original touch dictating
                 * the movement of the hover cell has ended, then the dragging event
                 * ends and the hover cell is animated to its corresponding position
                 * in the listview. */
                pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >>
                        MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == activePointerId) {
                    touchEventsEnded();
                }
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }


    private void handleCellSwitch() {
        final int deltaY = lastEventY - mDownY;
        int deltaYTotal = hoverCellOriginalBounds.top + totalOffset + deltaY;

        View belowView = getViewForID(belowItemId);
        View mobileView = getViewForID(mobileItemId);
        View aboveView = getViewForID(aboveItemId);

        boolean isBelow = (belowView != null) && (deltaYTotal > belowView.getTop());
        boolean isAbove = (aboveView != null) && (deltaYTotal < aboveView.getTop());

        if (isBelow || isAbove) {

            final long switchItemID = isBelow ? belowItemId : aboveItemId;
            View switchView = isBelow ? belowView : aboveView;
            final int originalItem = getPositionForView(mobileView);

            if (switchView == null) {
                updateNeighborViewsForID(mobileItemId);
                return;
            }

            swapElements(locationList, originalItem, getPositionForView(switchView));

            ((BaseAdapter) getAdapter()).notifyDataSetChanged();

            mDownY = lastEventY;

            final int switchViewStartTop = switchView.getTop();

            mobileView.setVisibility(View.VISIBLE);
            switchView.setVisibility(View.INVISIBLE);

            updateNeighborViewsForID(mobileItemId);

            final ViewTreeObserver observer = getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                public boolean onPreDraw() {
                    observer.removeOnPreDrawListener(this);

                    View switchView = getViewForID(switchItemID);

                    totalOffset += deltaY;

                    int switchViewNewTop = switchView.getTop();
                    int delta = switchViewStartTop - switchViewNewTop;

                    switchView.setTranslationY(delta);

                    ObjectAnimator animator = ObjectAnimator.ofFloat(switchView, View.TRANSLATION_Y, 0);
                    animator.setDuration(MOVE_DURATION);
                    animator.start();

                    return true;
                }
            });
        }
    }

    private void touchEventsEnded () {
        final View mobileView = getViewForID(mobileItemId);
        if (cellIsMobile|| isWaitingForScrollFinish) {

            cellIsMobile = false;
            isWaitingForScrollFinish = false;
            isMobileScrolling = false;
            activePointerId = INVALID_POINTER_ID;

            if (scrollState != OnScrollListener.SCROLL_STATE_IDLE) {
                isWaitingForScrollFinish = true;
                return;
            }

            hoverCellCurrentBounds.offsetTo(hoverCellOriginalBounds.left, mobileView.getTop());

            ObjectAnimator hoverViewAnimator = ObjectAnimator.ofObject(hoverCell, "bounds",
                    sBoundEvaluator, hoverCellCurrentBounds);
            hoverViewAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    invalidate();
                }
            });
            hoverViewAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    setEnabled(false);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    aboveItemId = INVALID_ID;
                    mobileItemId = INVALID_ID;
                    belowItemId = INVALID_ID;
                    mobileView.setVisibility(VISIBLE);
                    hoverCell = null;
                    setEnabled(true);
                    invalidate();
                }
            });
            hoverViewAnimator.start();
        } else {
            touchEventsCancelled();
        }
    }

    private void touchEventsCancelled () {
        View mobileView = getViewForID(mobileItemId);
        if (cellIsMobile) {
            aboveItemId = INVALID_ID;
            mobileItemId = INVALID_ID;
            belowItemId = INVALID_ID;
            mobileView.setVisibility(VISIBLE);
            hoverCell = null;
            invalidate();
        }
        cellIsMobile = false;
        isMobileScrolling = false;
        activePointerId = INVALID_POINTER_ID;
    }


    private BitmapDrawable getAndAddHoverView(View view) {

        int w = view.getWidth();
        int h = view.getHeight();
        int top = view.getTop();
        int left = view.getLeft();

        Bitmap b = getBitmapWithBorder(view);

        BitmapDrawable drawable = new BitmapDrawable(getResources(), b);

        hoverCellOriginalBounds = new Rect(left, top, left + w, top + h);
        hoverCellCurrentBounds = new Rect(hoverCellOriginalBounds);

        drawable.setBounds(hoverCellCurrentBounds);

        return drawable;
    }

    private Bitmap getBitmapWithBorder(View view) {
        Bitmap bitmap = getBitmapFromView(view);
        Canvas can = new Canvas(bitmap);

        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(LINE_THICKNESS);
        paint.setColor(Color.BLACK);

        can.drawBitmap(bitmap, 0, 0, null);
        can.drawRect(rect, paint);

        return bitmap;
    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas (bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void handleMobileCellScroll() {
        isMobileScrolling = handleMobileCellScroll(hoverCellCurrentBounds);
    }

    public boolean handleMobileCellScroll(Rect r) {
        int offset = computeVerticalScrollOffset();
        int height = getHeight();
        int extent = computeVerticalScrollExtent();
        int range = computeVerticalScrollRange();
        int hoverViewTop = r.top;
        int hoverHeight = r.height();

        if (hoverViewTop <= 0 && offset > 0) {
            smoothScrollBy(-smoothScrollAmountAtEdge, 0);
            return true;
        }

        if (hoverViewTop + hoverHeight >= height && (offset + extent) < range) {
            smoothScrollBy(smoothScrollAmountAtEdge, 0);
            return true;
        }

        return false;
    }

    public void setLocationList(ArrayList<Location> locationList) {
        this.locationList = locationList;
    }

    private AbsListView.OnScrollListener mScrollListener = new AbsListView.OnScrollListener () {

        private int mPreviousFirstVisibleItem = -1;
        private int mPreviousVisibleItemCount = -1;
        private int mCurrentFirstVisibleItem;
        private int mCurrentVisibleItemCount;
        private int mCurrentScrollState;

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                             int totalItemCount) {
            mCurrentFirstVisibleItem = firstVisibleItem;
            mCurrentVisibleItemCount = visibleItemCount;

            mPreviousFirstVisibleItem = (mPreviousFirstVisibleItem == -1) ? mCurrentFirstVisibleItem
                    : mPreviousFirstVisibleItem;
            mPreviousVisibleItemCount = (mPreviousVisibleItemCount == -1) ? mCurrentVisibleItemCount
                    : mPreviousVisibleItemCount;

            checkAndHandleFirstVisibleCellChange();
            checkAndHandleLastVisibleCellChange();

            mPreviousFirstVisibleItem = mCurrentFirstVisibleItem;
            mPreviousVisibleItemCount = mCurrentVisibleItemCount;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            mCurrentScrollState = scrollState;
            scrollState = scrollState;
            isScrollCompleted();
        }

        public void checkAndHandleFirstVisibleCellChange() {
            if (mCurrentFirstVisibleItem != mPreviousFirstVisibleItem) {
                if (cellIsMobile && mobileItemId != INVALID_ID) {
                    updateNeighborViewsForID(mobileItemId);
                    handleCellSwitch();
                }
            }
        }

        public void checkAndHandleLastVisibleCellChange() {
            int currentLastVisibleItem = mCurrentFirstVisibleItem + mCurrentVisibleItemCount;
            int previousLastVisibleItem = mPreviousFirstVisibleItem + mPreviousVisibleItemCount;
            if (currentLastVisibleItem != previousLastVisibleItem) {
                if (cellIsMobile && mobileItemId != INVALID_ID) {
                    updateNeighborViewsForID(mobileItemId);
                    handleCellSwitch();
                }
            }
        }

        private void isScrollCompleted() {
            if (mCurrentVisibleItemCount > 0 && mCurrentScrollState == SCROLL_STATE_IDLE) {
                if (cellIsMobile && isMobileScrolling) {
                    handleMobileCellScroll();
                } else if (isWaitingForScrollFinish) {
                    touchEventsEnded();
                }
            }
        }

    };

    private final static TypeEvaluator<Rect> sBoundEvaluator = new TypeEvaluator<Rect>() {
        public Rect evaluate(float fraction, Rect startValue, Rect endValue) {
            return new Rect(interpolate(startValue.left, endValue.left, fraction),
                    interpolate(startValue.top, endValue.top, fraction),
                    interpolate(startValue.right, endValue.right, fraction),
                    interpolate(startValue.bottom, endValue.bottom, fraction));
        }

        public int interpolate(int start, int end, float fraction) {
            return (int) (start + fraction * (end - start));
        }
    };

}
