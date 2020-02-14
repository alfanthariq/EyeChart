package com.alfanthariq.eyechart.helper;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by alfanthariq on 14/03/2018.
 */

public class Stripes {
    private Paint p;
    private int color, top, bottom;
    private int size;

    public Stripes(int color, int top, int bottom) {
        this.color = color;
        this.top = top;
        this.bottom = bottom;

        p = new Paint();
        p.setColor(this.color);
        p.setStyle(Paint.Style.FILL);
    }

    /*public void draw(Canvas canvas, int x, int size){
        this.size = size;
    }*/

    public Paint getP() {
        return p;
    }

    public int getColor() {
        return color;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }
}
