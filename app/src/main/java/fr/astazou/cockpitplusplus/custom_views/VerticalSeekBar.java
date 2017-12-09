package fr.astazou.cockpitplusplus.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import fr.astazou.cockpitplusplus.R;

/**
 * Created by archi on 09/12/2017.
 *
 */
public class VerticalSeekBar extends android.support.v7.widget.AppCompatSeekBar {

    private Drawable mOriginalThumb;

    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {
        c.rotate(-90);
        c.translate(-getHeight(), 0);

        super.onDraw(c);
    }

    private OnSeekBarChangeListener onChangeListener;
    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onChangeListener){
        this.onChangeListener = onChangeListener;
    }

    public void setOriginalThumb(Drawable pOriginalThumb) {
        mOriginalThumb = pOriginalThumb;
    }

    private int lastProgress = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onChangeListener.onStartTrackingTouch(this);
                if(mOriginalThumb != null) {
                    setThumb(getResources().getDrawable(R.drawable.ic_filter_tilt_shift_yellow_56dp));
                }
                setPressed(false);
                setSelected(false);
                break;
            case MotionEvent.ACTION_MOVE:
                super.onTouchEvent(event);
                int progress = getMax() - (int) (getMax() * event.getY() / getHeight());

                // Ensure progress stays within boundaries
                if(progress < 0) {progress = 0;}
                if(progress > getMax()) {progress = getMax();}
                setProgress(progress);  // Draw progress
                if(progress != lastProgress) {
                    // Only enact listener if the progress has actually changed
                    lastProgress = progress;
                    onChangeListener.onProgressChanged(this, progress, false);
                }

                onSizeChanged(getWidth(), getHeight() , 0, 0);
                setPressed(false);
                setSelected(false);
                break;
            case MotionEvent.ACTION_UP:
                onChangeListener.onStopTrackingTouch(this);
                if(mOriginalThumb!= null) {
                    setThumb(mOriginalThumb);
                    /*
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setThumb(mOriginalThumb);
                        }
                    }, 2000);
                    */
                }
                setPressed(false);
                setSelected(false);
                break;
            case MotionEvent.ACTION_CANCEL:
                super.onTouchEvent(event);
                setPressed(false);
                setSelected(false);
                break;
        }
        return true;
    }

    public synchronized void setProgressAndThumb(int progress) {
        setProgress(progress);
        onSizeChanged(getWidth(), getHeight() , 0, 0);
        if(progress != lastProgress) {
            // Only enact listener if the progress has actually changed
            lastProgress = progress;
            onChangeListener.onProgressChanged(this, progress, false);
        }
    }

    public synchronized void setMaximum(int maximum) {
        setMax(maximum);
    }

    public synchronized int getMaximum() {
        return getMax();
    }
}
