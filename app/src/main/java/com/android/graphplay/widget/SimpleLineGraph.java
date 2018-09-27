package com.android.graphplay.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.graphplay.R;
import com.android.graphplay.util.BackInterpolator;
import com.android.graphplay.util.Util;

import java.util.ArrayList;

/**
 * Created by akashshrivatava on 9/12/18.
 */

public class SimpleLineGraph extends FrameLayout {

    private View scrollContentView;
    private Paint linePaint;
    private Paint graphPaint;
    private Paint xAxisLinePaint;
    private Path graphPath = new Path();
    Bitmap glowMarkerCircle;

    private boolean showLable = false;

    public ArrayList<ArrayList<SimpleLineGraph.GraphValue>> dataList;
    public ArrayList<GraphValue> graphData;

    private TextView overlayTextView;
    private GestureDetector singleTapListener;
    private boolean selectedAtTouchStart = false;

    //View circle on the graph line..
    private View overlayGraphPointer;
    private float overlayOffsetY;
    private float overlayOffsetX;

    private PointF overlayPreviousPosition;


    private PointF point;


    public SimpleLineGraph(@NonNull Context context) {
        super(context);
        init();

    }

    public SimpleLineGraph(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public SimpleLineGraph(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        setWillNotDraw(false);
        this.graphData = new ArrayList<>();
        scrollContentView = new View(getContext());
        glowMarkerCircle = BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(Util.dpToPx(getContext(), 2.5f));

        linePaint.setPathEffect(new CornerPathEffect(Util.dpToPx(getContext(), 40)));
        linePaint.setStrokeJoin(Paint.Join.ROUND);
        linePaint.setStrokeCap(Paint.Cap.ROUND);

        xAxisLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xAxisLinePaint.setTextAlign(Paint.Align.CENTER);
        xAxisLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.bgButton));
        xAxisLinePaint.setTextSize(getResources().getDimension(R.dimen.fontSize8));
        xAxisLinePaint.setStrokeWidth(3);
        xAxisLinePaint.setColor(Color.WHITE);

        graphPaint = new Paint(linePaint);
        graphPaint.setStyle(Paint.Style.FILL);

        overlayOffsetX = -Util.dpToPx(getContext(), 28);
        overlayOffsetY = -Util.dpToPx(getContext(), 22);


        overlayGraphPointer = new View(getContext());
        overlayGraphPointer.setBackgroundResource(R.drawable.ic_triangle);

        //Size of triangle
        addView(overlayGraphPointer,
                new LayoutParams(Util.dpToPx(getContext(), 18), Util.dpToPx(getContext(), 12)));
        overlayGraphPointer.setAlpha(0);

        overlayTextView = new TextView(getContext());

        //Size of hover lable...
        LayoutParams layoutParams = new LayoutParams(Util.dpToPx(getContext(), 55), LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 0);
        addView(overlayTextView, layoutParams);

        overlayTextView.setTextSize(12);
        overlayTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.textPrimaryDark));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            overlayTextView.setTypeface(getResources().getFont(R.font.avenir_next_bold));
        }
        overlayTextView.setAlpha(0);
        overlayTextView.setBackgroundResource(R.drawable.bg_graph_lable);
        overlayTextView.setEnabled(false);
        overlayTextView.setPadding(0, Util.dpToPx(getContext(), 4), 0, Util.dpToPx(getContext(), 4));

        overlayTextView.setGravity(Gravity.CENTER_HORIZONTAL);

        singleTapListener = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (selectedAtTouchStart) {
                    clearTouchedSegment();
                }
                return false;
            }
        });
    }

    public void showOverLayLable(boolean showLable){
        this.showLable = showLable;
    }

    public void clearTouchedSegment() {
        invalidate();
        overlayTextView.animate().alpha(0).setDuration(200).start();
        overlayGraphPointer.animate().alpha(0).setDuration(200).start();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        scrollContentView.setLayoutParams(new LayoutParams(
                w * 10,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setData(ArrayList<ArrayList<SimpleLineGraph.GraphValue>> dataList) {
        this.dataList = dataList;
        invalidate();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        requestDisallowInterceptTouchEvent(true);
        getParent().requestDisallowInterceptTouchEvent(true);
        // Let's not allow parents to steal our touch
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(showLable) {
            singleTapListener.onTouchEvent(event);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    selectedAtTouchStart = overlayTextView.getAlpha() != 0;
                    overlayTextView.animate().alpha(1).setDuration(300).setStartDelay(0);
                    overlayGraphPointer.animate().alpha(1).setDuration(300).setStartDelay(0);

                    overlayTextView.setText(String.valueOf(graphData.get(2).x + graphData.get(2).y));

                    PointF point = new PointF((float) graphData.get(2).x, (float) graphData.get(2).y);
                    PointF startAt = new PointF((float) graphData.get(1).x, (float) graphData.get(1).y);

                    overlayTextView.setX(overlayOffsetX + startAt.x);
                    overlayTextView.setY(overlayOffsetY + startAt.y);
                    overlayGraphPointer.setX(startAt.x - overlayGraphPointer.getWidth() / 2);
                    overlayGraphPointer.setY(startAt.y - overlayGraphPointer.getHeight() / 2);


                    break;
                case MotionEvent.ACTION_MOVE:
                    //updateTouchedSegment(event.getX());
//                PointF closestPoint = findClosestPoint(event.getX());
                    PointF target = new PointF((float) graphData.get(5).x, (float) graphData.get(5).y);
                    if (target != null && !target.equals(overlayPreviousPosition)) {
                        overlayTextView.setText(String.valueOf(graphData.get(2).x + graphData.get(2).y));

                        overlayTextView.animate()
                                .x(overlayOffsetX + target.x)
                                .y(overlayOffsetY + target.y)
                                .setInterpolator(new BackInterpolator())
                                .setDuration(400)
                                .start();
                        overlayGraphPointer.setX(target.x - overlayGraphPointer.getWidth() / 2);
                        overlayGraphPointer.setY(target.y - overlayGraphPointer.getHeight() / 2);
                    }
                    overlayPreviousPosition = target;

                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    break;
            }
        }
        return super.onTouchEvent(event);

    }



    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();

        //X Axis.....
//        canvas.drawLine(0, height, width, height, xAxisLinePaint);

        for (int x = 0; x < dataList.size(); x++) {

            graphData = dataList.get(x);
            graphPath.rewind();
            GraphValue value;

            //First Helper line-50
            graphPath.lineTo(-50, (float) graphData.get(0).y);

            // We draw the basic graph
            for (int i = 0; i < graphData.size(); i++) {
                value = graphData.get(i);
                graphPath.lineTo((float) value.x, (float) value.y);

            }

            //Extra Helper lines to round the Gradient effect...
            graphPath.lineTo(width + 50, (float) graphData.get(graphData.size() - 1).y);
            graphPath.lineTo(width + 50, height + 50);
            graphPath.lineTo(-50, height + 50);
            graphPath.lineTo(-50, (float) graphData.get(0).y);

//            graphPath.close();
//            canvas.clipRect(0, 0, width, height);

            linePaint.setColor(graphData.get(0).color);
            linePaint.setShadowLayer(6, 0, 4, Color.BLACK);

//            int color = graphData.get(0).color;
//            int transparent = Color.argb(40, Color.red(color), Color.green(color), Color.blue(color));
//            graphPaint.setShader(new LinearGradient(0, getHeight(), 0, 0,
//                    transparent, transparent, Shader.TileMode.MIRROR));

//            canvas.drawPath(graphPath, graphPaint);
            canvas.drawPath(graphPath, linePaint);
            canvas.save();



        }
    }

    public static class GraphValue {
        public final double x; // in our specific case, is the millis time slot
        public final double y; // kwh
        public final int color;

        public GraphValue(double x, double y, int color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }


    }


}