package com.alfanthariq.eyechart.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;


/**
 * Created by alfanthariq on 14/03/2018.
 */

public class StripesView extends SurfaceView {
    private SurfaceHolder surfaceHolder;
    private int LINE_SPACING = 30;
    private int color = Color.BLACK;
    private int radius = 50;
    private int lastY, count, moveY, delta, speed;
    private boolean drawBall = false;
    private ArrayList<Stripes> stripes = new ArrayList<>();
    private ArrayList<Stripes> stripes_temp = new ArrayList<>();
    private Paint pBall, p, bg;
    private AnimationThread anim;
    private int orientation;

    public StripesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        pBall = new Paint();
        p = new Paint();
        bg = new Paint();
        delta = 1;
        orientation = 0; //0: vertical; 1:horizontal
        init();
    }

    private void init(){
        anim = new AnimationThread(this);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback(){
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                final Canvas canvas = holder.lockCanvas(null);
                prepareObject(canvas);
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder,
                                       int format, int width, int height) {
                // TODO Auto-generated method stub

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                boolean retry = true;
                anim.setRunning(false);
                while (retry) {
                    try {
                        anim.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }});
    }

    protected void drawSomething(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        bg.setColor(Color.WHITE);
        canvas.drawRect(0, 0, getRight(), getBottom(), bg);

        if (orientation==0) {
            for (int x = 0; x < stripes.size(); x++) {
                p = stripes.get(x).getP();
                if (stripes.get(x).getColor() != Color.WHITE) {
                    p.setColor(this.color);
                } else {
                    p.setColor(stripes.get(x).getColor());
                }
                int top = stripes.get(x).getTop() - moveY;
                int bottom = stripes.get(x).getBottom() - moveY;
                canvas.drawRect(0, top, getWidth(), bottom, p);
                moveY += delta;
            }

            if (moveY >= canvas.getHeight()) {
                moveY = 0;
            }
        } else {
            for (int x = 0; x < stripes.size(); x++) {
                p = stripes.get(x).getP();
                if (stripes.get(x).getColor() != Color.WHITE) {
                    p.setColor(this.color);
                } else {
                    p.setColor(stripes.get(x).getColor());
                }
                int left = stripes.get(x).getTop() - moveY;
                int right = stripes.get(x).getBottom() - moveY;
                canvas.drawRect(left, 0, right, getHeight(), p);
                moveY += delta;
            }

            if (moveY >= canvas.getWidth()) {
                moveY = 0;
            }
        }

        if (drawBall) {
            pBall.setStyle(Paint.Style.FILL);
            if (color==Color.BLACK) {
                pBall.setColor(Color.RED);
            } else {
                pBall.setColor(Color.BLACK);
            }
            canvas.translate(getWidth()/2f,getHeight()/2f);
            canvas.drawCircle(0, 0, radius, pBall);
        }
    }

    private void prepareObject(Canvas canvas){
        //count = 16;
        //moveY = getHeight();
        //lastY = 0;
        //LINE_SPACING = canvas.getHeight() / (count - 1);
        LINE_SPACING = 100;
        if (orientation==0) {
            count = canvas.getHeight() / LINE_SPACING;
            count += 3;
        } else {
            count = canvas.getWidth() / LINE_SPACING;
            count += 3;
        }
        int colorX;
        for(int i=0; i<count*5; i++){
            if (i % 2 == 0) {
                colorX = color;
            } else {
                colorX = Color.WHITE;
            }
            stripes.add(new Stripes(colorX, lastY, lastY + LINE_SPACING));
            lastY += LINE_SPACING;
        }
    }

    /*protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }*/

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void showBall(){
        drawBall = true;
    }

    public void hideBall(){
        drawBall = false;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        anim.setSpeed(this.speed);
    }

    public boolean isDrawBall() {
        return drawBall;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void start(){
        anim.setRunning(true);
        anim.setSpeed(speed);
        anim.start();
    }

    public void stop(){
        anim.setRunning(false);
        anim.interrupt();
    }
}
