package com.alfanthariq.eyechart.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by alfanthariq on 13/03/2018.
 */

public class DuochromeView extends View {
    private int externalMaxRadius;
    private int externalMinRadius;
    private boolean firstTimeToDraw;
    private int radiusToDraw;
    OnAfterDraw mAfterDraw;

    public DuochromeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.firstTimeToDraw = true;
    }

    public interface OnAfterDraw{
        void onAfterDraw();
    }

    public void setAfterDraw(OnAfterDraw eventAfterDraw){
        mAfterDraw = eventAfterDraw;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int screenCenterX = canvas.getWidth() / 2;
        int screenCenterY = canvas.getHeight() / 2;
        int distanceToMoveFromCenter = canvas.getHeight() / 4;
        int minimumGap = canvas.getHeight() / 20;
        if (canvas.getWidth() <= canvas.getHeight() / 2) {
            this.externalMaxRadius = (canvas.getWidth() / 2) - minimumGap;
        } else {
            this.externalMaxRadius = (canvas.getHeight() / 4) - minimumGap;
        }
        this.externalMinRadius = 24;
        if (this.firstTimeToDraw) {
            this.radiusToDraw = this.externalMaxRadius;
        }
        Path rectangleOnePath = new Path();
        rectangleOnePath.addRect(0.0f, 0.0f, (float) canvas.getWidth(), (float) (canvas.getHeight() / 2), Path.Direction.CW);
        Path rectangleTwoPath = new Path();
        rectangleTwoPath.addRect(0.0f, (float) (canvas.getHeight() / 2), (float) canvas.getWidth(), (float) canvas.getHeight(), Path.Direction.CW);
        Paint paintColorOne = new Paint();
        paintColorOne.setColor(SupportMenu.CATEGORY_MASK);
        paintColorOne.setStyle(Paint.Style.FILL);
        Paint paintColorTwo = new Paint();
        paintColorTwo.setColor(-16711936);
        paintColorTwo.setStyle(Paint.Style.FILL);
        canvas.drawPath(rectangleOnePath, paintColorOne);
        canvas.drawPath(rectangleTwoPath, paintColorTwo);
        Path optotypeOnePath = circularPattern(screenCenterX, screenCenterY - distanceToMoveFromCenter, this.radiusToDraw);
        Path optotypeTwoPath = circularPattern(screenCenterX, screenCenterY + distanceToMoveFromCenter, this.radiusToDraw);
        Paint paintColor = new Paint();
        paintColor.setColor(ViewCompat.MEASURED_STATE_MASK);
        paintColor.setStyle(Paint.Style.FILL);
        canvas.drawPath(optotypeOnePath, paintColor);
        canvas.drawPath(optotypeTwoPath, paintColor);
        if (this.firstTimeToDraw) {
            if (mAfterDraw != null) {
                mAfterDraw.onAfterDraw();
            }
            this.firstTimeToDraw = false;
        }
    }

    public void reDraw() {
        invalidate();
    }

    public boolean increaseSize(int pxToIncrease) {
        if (!validateSize(this.externalMaxRadius, this.externalMinRadius, this.radiusToDraw + pxToIncrease)) {
            return false;
        }
        this.radiusToDraw += pxToIncrease;
        return true;
    }

    public boolean decreaseSize(int pxToDecrease) {
        if (!validateSize(this.externalMaxRadius, this.externalMinRadius, this.radiusToDraw - pxToDecrease)) {
            return false;
        }
        this.radiusToDraw -= pxToDecrease;
        return true;
    }

    private boolean validateSize(int max, int min, int size) {
        if (size >= min && size <= max) {
            return true;
        }
        return false;
    }

    private Path circularPattern(int centerX, int centerY, int radius) {
        float externalR = (float) radius;
        float internalR = externalR - ((float) (radius / 4));
        float externalSmallR = internalR - ((float) (radius / 4));
        float internalSmallR = externalSmallR - ((float) (radius / 8));
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerCirclePath = new Path();
        outerCirclePath.addCircle((float) centerX, (float) centerY, externalR, Path.Direction.CW);
        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float) centerX, (float) centerY, internalR, Path.Direction.CW);
        Path smallOuterCirclePath = new Path();
        smallOuterCirclePath.addCircle((float) centerX, (float) centerY, externalSmallR, Path.Direction.CW);
        Path smallInnerCirclePath = new Path();
        smallInnerCirclePath.addCircle((float) centerX, (float) centerY, internalSmallR, Path.Direction.CW);
        optotypePath.op(outerCirclePath, innerCirclePath, Path.Op.DIFFERENCE);
        optotypePath.op(smallOuterCirclePath, Path.Op.UNION);
        optotypePath.op(smallInnerCirclePath, Path.Op.DIFFERENCE);
        return optotypePath;
    }

    public int getExternalMaxRadius() {
        return externalMaxRadius;
    }

    public int getExternalMinRadius() {
        return externalMinRadius;
    }

    public int getRadiusToDraw() {
        return radiusToDraw;
    }
}
