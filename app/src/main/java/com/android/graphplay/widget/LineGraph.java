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
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.graphplay.R;
import com.android.graphplay.util.Util;

import java.util.ArrayList;

/**
 * Created by akashshrivatava on 9/12/18.
 */

public class LineGraph extends FrameLayout {

    private View scrollContentView;
    private Paint linePaint;
    private Paint graphPaint;
    private Paint xAxisLinePaint;
    private Path graphPath = new Path();
    public ArrayList<GraphValue> graphData;
    public ArrayList<ArrayList<LineGraph.GraphValue>> dataList;
    Bitmap glowMarkerCircle;

    public LineGraph(@NonNull Context context) {
        super(context);
        init();

    }

    public LineGraph(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public LineGraph(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        scrollContentView.setLayoutParams(new LayoutParams(
                w * 10,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setData(ArrayList<ArrayList<LineGraph.GraphValue>> dataList) {
        this.dataList = dataList;
        invalidate();
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

//              Circle at every point
//                canvas.drawCircle(
//                        (int) value.x, (int) value.y, 10, linePaint);

                //Bitmap..
//                canvas.drawBitmap(glowMarkerCircle, (int) value.x, (int) value.y, linePaint);

            }

            //Extra Helper lines to round the Gradient effect...
            graphPath.lineTo(width + 50, (float) graphData.get(graphData.size() - 1).y);
            graphPath.lineTo(width + 50, height + 50);
            graphPath.lineTo(-50, height + 50);
            graphPath.lineTo(-50, (float) graphData.get(0).y);

            //First and last helper circles
            //Starting Circles
//            canvas.drawCircle(
//                    0, (int) graphData.get(0).y, 16, linePaint);
//
//            Ending circles
//            canvas.drawCircle(
//                    width, (int) graphData.get(graphData.size() - 1).y, 16, linePaint);

            graphPath.close();
            canvas.clipRect(0, 0, width, height);
            linePaint.setColor(graphData.get(0).color);
            linePaint.setShadowLayer(6, 0, 4, Color.BLACK);
            int color = graphData.get(0).color;
            int transparent = Color.argb(60, Color.red(color), Color.green(color), Color.blue(color));
            graphPaint.setShader(new LinearGradient(0, getHeight(), 0, 0,
                    transparent, transparent, Shader.TileMode.MIRROR));

            canvas.drawPath(graphPath, graphPaint);
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