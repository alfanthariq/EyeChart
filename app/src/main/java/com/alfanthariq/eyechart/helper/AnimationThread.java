package com.alfanthariq.eyechart.helper;

import android.graphics.Canvas;

/**
 * Created by alfanthariq on 14/03/2018.
 */

public class AnimationThread extends Thread {
    StripesView stripe;
    private boolean running = false;
    private int speed;

    public AnimationThread(StripesView stripe){
        this.stripe = stripe;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void run() {
        while(running){

            Canvas canvas = stripe.getHolder().lockCanvas();

            if(canvas != null){
                synchronized (stripe.getHolder()) {
                    stripe.drawSomething(canvas);
                }
                stripe.getHolder().unlockCanvasAndPost(canvas);
            }

            try {
                sleep(speed);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
