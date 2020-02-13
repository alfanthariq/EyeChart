package com.alfanthariq.eyechart.optotype;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class SnellenOptotypeCanvas extends View {

    Paint paint = new Paint();
    private int gridStep = 100;

    public SnellenOptotypeCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(android.graphics.Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        Float fontSizePx = 15 * getResources().getDisplayMetrics().xdpi * (1.0f/25.4f);
        this.gridStep = fontSizePx.intValue() / 5;
        canvas.drawPath(snellenOptotypeEex(canvas.getWidth()/2, canvas.getHeight()/2), paint);
    }

    private Path snellenOptotypeE(int fontSizeMM, int thicknessMM) {
        float fontSizePx = fontSizeMM * getResources().getDisplayMetrics().xdpi * (1.0f/25.4f);
        float thicknessPx = thicknessMM * getResources().getDisplayMetrics().xdpi * (1.0f/25.4f);
        float thicknessPxExtra = (thicknessMM + 50) * getResources().getDisplayMetrics().xdpi * (1.0f/25.4f);
        float centerX = (this.getWidth() - fontSizePx) / 2;
        float centerY = (this.getHeight() - fontSizePx) / 2;
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.reset();
        path.moveTo(centerX, centerY);
        path.lineTo(centerX + fontSizePx, centerY);
        path.lineTo(centerX + fontSizePx, centerY+thicknessPxExtra);
        path.lineTo((centerX+fontSizePx) - thicknessPx, centerY+thicknessPxExtra);
        path.lineTo((centerX+fontSizePx) - thicknessPx, (centerY+thicknessPx));
        path.lineTo(centerX+thicknessPx+thicknessPxExtra, (centerY+thicknessPx));
        path.close();
        return path;
    }

    private Path snellenOptotypeEex(int centerX, int centerY) {
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
}
