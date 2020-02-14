package com.alfanthariq.eyechart.optotype;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Random;

public class SnellenOptotypeCanvas extends View {
    Paint paint = new Paint();
    private int gridStep = 100;
    Double fontSizePx;
    Random rand = new Random();

    public SnellenOptotypeCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SnellenOptotypeCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(android.graphics.Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        canvas.drawColor(Color.WHITE);
        //Float fontSizePx = 15 * getResources().getDisplayMetrics().xdpi * (1.0f/25.4f);
//        Double fontSizePx = (double) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, (float) fontsizeMM,
//                getResources().getDisplayMetrics());
        this.gridStep = fontSizePx.intValue() / 5;
        int tipe = rand.nextInt(9);
        switch (tipe) {
            case 0:
                canvas.drawPath(snellenOptotypeE(getWidth()/2, getHeight()/2), paint);
                break;
            case 1:
                canvas.drawPath(snellenOptotypeT(getWidth()/2, getHeight()/2), paint);
                break;
            case 2:
                canvas.drawPath(snellenOptotypeF(getWidth()/2, getHeight()/2), paint);
                break;
            case 3:
                canvas.drawPath(snellenOptotypeZ(getWidth()/2, getHeight()/2), paint);
                break;
            case 4:
                canvas.drawPath(snellenOptotypeL(getWidth()/2, getHeight()/2), paint);
                break;
            case 5:
                canvas.drawPath(snellenOptotypeO(getWidth()/2, getHeight()/2), paint);
                break;
            case 6:
                canvas.drawPath(snellenOptotypeC(getWidth()/2, getHeight()/2), paint);
                break;
            case 7:
                canvas.drawPath(snellenOptotypeD(getWidth()/2, getHeight()/2), paint);
                break;
            case 8:
                canvas.drawPath(snellenOptotypeP(getWidth()/2, getHeight()/2), paint);
                break;
        }
    }

    public void drawSnellen(double fontsizePx) {
        this.fontSizePx = fontsizePx;
        invalidate();
    }

    private Path snellenOptotypeE(int centerX, int centerY) {
        int originX = centerX - ((int) (((double) this.gridStep) * 2.5d));
        int originY = centerY - ((int) (((double) this.gridStep) * 2.5d));
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo((float) originX, (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) ((this.gridStep * 2) + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) ((this.gridStep * 2) + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 3) + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 3) + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 2) + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 2) + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) originX, (float) (this.gridStep + originY));
        optotypePath.lineTo((float) originX, (float) originY);
        return optotypePath;
    }

    private Path snellenOptotypeT(int centerX, int centerY) {
        int originX = centerX - ((int) (((double) this.gridStep) * 2.5d));
        int originY = centerY - ((int) (((double) this.gridStep) * 2.5d));
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo((float) originX, (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) ((this.gridStep * 3) + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) ((this.gridStep * 3) + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 2) + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 2) + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) originX, (float) originY);
        return optotypePath;
    }

    private Path snellenOptotypeF(int centerX, int centerY) {
        int originX = centerX - ((int) (((double) this.gridStep) * 2.5d));
        int originY = centerY - ((int) (((double) this.gridStep) * 2.5d));
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo((float) originX, (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) ((this.gridStep * 2) + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) ((this.gridStep * 2) + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 3) + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 3) + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 2) + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 2) + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 3) + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 3) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) originX, (float) (this.gridStep + originY));
        optotypePath.lineTo((float) originX, (float) originY);
        return optotypePath;
    }

    private Path snellenOptotypeZ(int centerX, int centerY) {
        int originX = centerX - ((int) (((double) this.gridStep) * 2.5d));
        int originY = centerY - ((int) (((double) this.gridStep) * 2.5d));
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo((float) originX, (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) ((1.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) originX, (float) originY);
        return optotypePath;
    }

    private Path snellenOptotypeL(int centerX, int centerY) {
        int originX = centerX - ((int) (((double) this.gridStep) * 2.5d));
        int originY = centerY - ((int) (((double) this.gridStep) * 2.5d));
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo((float) originX, (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 3) + originX), (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 3) + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) ((this.gridStep * 2) + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) ((this.gridStep * 2) + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) originX, (float) (this.gridStep + originY));
        optotypePath.lineTo((float) originX, (float) originY);
        return optotypePath;
    }

    private Path snellenOptotypeO(int centerX, int centerY) {
        int originX = centerX - ((int) (((double) this.gridStep) * 2.5d));
        int originY = centerY - ((int) (((double) this.gridStep) * 2.5d));
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerCirclePath = new Path();
        outerCirclePath.addCircle((float) ((((double) this.gridStep) * 2.5d) + ((double) originX)), (float) ((((double) this.gridStep) * 2.5d) + ((double) originY)), (float) (((double) this.gridStep) * 2.5d), Path.Direction.CW);
        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float) ((((double) this.gridStep) * 2.5d) + ((double) originX)), (float) ((((double) this.gridStep) * 2.5d) + ((double) originY)), (float) (1.5d * ((double) this.gridStep)), Path.Direction.CW);
        optotypePath.op(outerCirclePath, innerCirclePath, Path.Op.DIFFERENCE);
        return optotypePath;
    }

    private Path snellenOptotypeC(int centerX, int centerY) {
        int originX = centerX - ((int) (2.5d * ((double) this.gridStep)));
        int originY = centerY - ((int) (2.5d * ((double) this.gridStep)));
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerCirclePath = new Path();
        outerCirclePath.addCircle((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)), (float) (2.5d * ((double) this.gridStep)), Path.Direction.CW);
        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)), (float) (1.5d * ((double) this.gridStep)), Path.Direction.CW);
        Path rectanglePath = new Path();
        rectanglePath.addRect((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY), (float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 3) + originY), Path.Direction.CW);
        Path circlePath = new Path();
        circlePath.op(outerCirclePath, innerCirclePath, Path.Op.DIFFERENCE);
        optotypePath.op(circlePath, rectanglePath, Path.Op.DIFFERENCE);
        return optotypePath;
    }

    private Path snellenOptotypeD(int centerX, int centerY) {
        int originX = centerX - ((int) (2.5d * ((double) this.gridStep)));
        int originY = centerY - ((int) (2.5d * ((double) this.gridStep)));
        Path optotypePath = new Path();
        optotypePath.reset();
        Path structPath = new Path();
        structPath.moveTo((float) originX, (float) originY);
        structPath.lineTo(((float) (2.5d * ((double) this.gridStep))) + ((float) originX), (float) originY);
        structPath.lineTo(((float) (2.5d * ((double) this.gridStep))) + ((float) originX), (float) (this.gridStep + originY));
        structPath.lineTo((float) ((this.gridStep * 2) + originX), (float) (this.gridStep + originY));
        structPath.lineTo((float) ((this.gridStep * 2) + originX), (float) ((this.gridStep * 4) + originY));
        structPath.lineTo(((float) (2.5d * ((double) this.gridStep))) + ((float) originX), (float) ((this.gridStep * 4) + originY));
        structPath.lineTo(((float) (2.5d * ((double) this.gridStep))) + ((float) originX), (float) ((this.gridStep * 5) + originY));
        structPath.lineTo((float) originX, (float) ((this.gridStep * 5) + originY));
        structPath.lineTo((float) originX, (float) ((this.gridStep * 4) + originY));
        structPath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 4) + originY));
        structPath.lineTo((float) (this.gridStep + originX), (float) (this.gridStep + originY));
        structPath.lineTo((float) originX, (float) (this.gridStep + originY));
        structPath.lineTo((float) originX, (float) originY);
        Path outerCircleHalfPath = new Path();
        outerCircleHalfPath.addCircle((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)), (float) (2.5d * ((double) this.gridStep)), Path.Direction.CW);
        Path rectanglePath = new Path();
        rectanglePath.addRect((float) originX, (float) originY, (float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 5) + originY), Path.Direction.CW);
        outerCircleHalfPath.op(rectanglePath, Path.Op.DIFFERENCE);
        Path innerCircleHalfPath = new Path();
        innerCircleHalfPath.addCircle((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)), (float) (1.5d * ((double) this.gridStep)), Path.Direction.CW);
        innerCircleHalfPath.op(rectanglePath, Path.Op.DIFFERENCE);
        optotypePath.op(structPath, outerCircleHalfPath, Path.Op.UNION);
        optotypePath.op(innerCircleHalfPath, Path.Op.DIFFERENCE);
        return optotypePath;
    }

    private Path snellenOptotypeP(int centerX, int centerY) {
        int originX = centerX - ((int) (2.5d * ((double) this.gridStep)));
        int originY = centerY - ((int) (2.5d * ((double) this.gridStep)));
        Path optotypePath = new Path();
        optotypePath.reset();
        Path structPath = new Path();
        structPath.moveTo((float) originX, (float) originY);
        structPath.lineTo(((float) (3.5d * ((double) this.gridStep))) + ((float) originX), (float) originY);
        structPath.lineTo(((float) (3.5d * ((double) this.gridStep))) + ((float) originX), (float) (this.gridStep + originY));
        structPath.lineTo((float) ((this.gridStep * 2) + originX), (float) (this.gridStep + originY));
        structPath.lineTo((float) ((this.gridStep * 2) + originX), (float) ((this.gridStep * 2) + originY));
        structPath.lineTo(((float) (3.5d * ((double) this.gridStep))) + ((float) originX), (float) ((this.gridStep * 2) + originY));
        structPath.lineTo(((float) (3.5d * ((double) this.gridStep))) + ((float) originX), (float) ((this.gridStep * 3) + originY));
        structPath.lineTo((float) ((this.gridStep * 2) + originX), (float) ((this.gridStep * 3) + originY));
        structPath.lineTo((float) ((this.gridStep * 2) + originX), (float) ((this.gridStep * 4) + originY));
        structPath.lineTo((float) ((this.gridStep * 3) + originX), (float) ((this.gridStep * 4) + originY));
        structPath.lineTo((float) ((this.gridStep * 3) + originX), (float) ((this.gridStep * 5) + originY));
        structPath.lineTo((float) originX, (float) ((this.gridStep * 5) + originY));
        structPath.lineTo((float) originX, (float) ((this.gridStep * 4) + originY));
        structPath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 4) + originY));
        structPath.lineTo((float) (this.gridStep + originX), (float) (this.gridStep + originY));
        structPath.lineTo((float) originX, (float) (this.gridStep + originY));
        structPath.lineTo((float) originX, (float) originY);
        Path outerCircleHalfPath = new Path();
        outerCircleHalfPath.addCircle((float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((1.5d * ((double) this.gridStep)) + ((double) originY)), (float) (1.5d * ((double) this.gridStep)), Path.Direction.CW);
        Path rectanglePath = new Path();
        rectanglePath.addRect((float) originX, (float) originY, (float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 3) + originY), Path.Direction.CW);
        outerCircleHalfPath.op(rectanglePath, Path.Op.DIFFERENCE);
        Path innerCircleHalfPath = new Path();
        innerCircleHalfPath.addCircle((float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((1.5d * ((double) this.gridStep)) + ((double) originY)), (float) (0.5d * ((double) this.gridStep)), Path.Direction.CW);
        innerCircleHalfPath.op(rectanglePath, Path.Op.DIFFERENCE);
        optotypePath.op(structPath, outerCircleHalfPath, Path.Op.UNION);
        optotypePath.op(innerCircleHalfPath, Path.Op.DIFFERENCE);
        return optotypePath;
    }
}
