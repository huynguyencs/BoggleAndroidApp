package com.example.mymac.boggle;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by Simeng on 2/12/17.
 */

public class FingerSlide extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private PointF[][] mPoints = new PointF[4][4];
    private List<Integer> pathNodes = new ArrayList<Integer>();
    private float centerRadius;
    private float circleRadius;
    private float viewWidth;
    private float viewHeight;
    private float curX = 0;
    private float curY = 0;


    private OnCompleteListener onCompleteListener = null;

    public FingerSlide(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public FingerSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public FingerSlide(Context context) {
        super(context);
    }


    // OnTouch event create
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if(event.getAction() == MotionEvent.ACTION_DOWN
                || event.getAction() == MotionEvent.ACTION_MOVE ) {
            curX = event.getX();
            curY = event.getY();
            detectGetPoint(curX, curY);
        } else if(event.getAction() == MotionEvent.ACTION_UP) {
            if(pathNodes.size() >= 3) {
                if(null != onCompleteListener) {
                    onCompleteListener.onComplete(pathToString(pathNodes));
                }
            }
            pathNodes.clear();
        }
        this.postInvalidate();
        return true;
    }

    // TO-DO: change drawPoint to set button background color
    // highlight button pressed and draw lines between each selected button
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        viewWidth = this.getMeasuredWidth();
        viewHeight = this.getMeasuredHeight();
        centerRadius = viewWidth / 24;
        circleRadius = viewWidth / 6 * 4 / 5;
        drawPoints(canvas);
        drawLines(canvas, curX, curY);
    }
    public void drawPoints(Canvas canvas) {
        mPaint.setColor(Color.argb(40, 51, 75, 209));
        mPaint.setStyle(Paint.Style.FILL);
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                mPoints[i][j] = new PointF((int)(viewWidth / 6 + viewWidth / 4 * j),
                        (int)(viewHeight / 6 + viewHeight / 4 * i));
                canvas.drawCircle(mPoints[i][j].x, mPoints[i][j].y, centerRadius, mPaint);
            }
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(centerRadius / 6);
        for(int i = 0; i < pathNodes.size(); i++) {
            int m = pathNodes.get(i) / 4;
            int n = pathNodes.get(i) % 4;
            canvas.drawCircle(mPoints[m][n].x, mPoints[m][n].y, circleRadius, mPaint);
        }
    }
    public void drawLines(Canvas canvas, float curX, float curY) {
        mPaint.setStrokeWidth(centerRadius / 2);
        PointF lastPointF = null;
        for(int i = 0; i < pathNodes.size(); i++) {
            int m = pathNodes.get(i) / 4;
            int n = pathNodes.get(i) % 4;
            PointF curPointF  = mPoints[m][n];
            if(null != lastPointF) {
                canvas.drawLine(lastPointF.x, lastPointF.y, curPointF.x, curPointF.y, mPaint);
            }
            lastPointF = curPointF;
        }
        if(null != lastPointF) {
            canvas.drawLine(lastPointF.x, lastPointF.y, curX, curY, mPaint);
        }
    }

    //
    public void detectGetPoint(float x, float y) {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if((mPoints[i][j].x - x) * (mPoints[i][j].x - x)
                        + (mPoints[i][j].y - y) * (mPoints[i][j].y - y)
                        < centerRadius * centerRadius * 4) {
                    // enlarge the space around the point that can be detected by multiplying 4
                    int nodeNum =  i * 4 + j;
                    if(!pathNodes.contains(nodeNum)) {
                        pathNodes.add(nodeNum);
                    }
                    return;
                }
            }
        }
    }

    public String pathToString(List<Integer> list) {
        String des = "";
        for(int i = 0; i < list.size(); i++) {
            des += list.get(i).toString();
        }
        return des;
    }


    public void setOnCompleteListener(OnCompleteListener o) {
        this.onCompleteListener = o;
    }

    public interface OnCompleteListener {
        public void onComplete(String pass);
    }
}