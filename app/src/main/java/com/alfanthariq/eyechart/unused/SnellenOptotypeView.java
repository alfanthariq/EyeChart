package com.alfanthariq.eyechart.unused;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.Op;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import java.util.Random;

public class SnellenOptotypeView extends View {
    private int currentAlphaToDraw = 255;
    private int currentChildrenOptotype = 0;
    private int currentNumberOptotype = 0;
    private int currentRotationInDegrees = 0;
    private int currentSloanOptotype = 0;
    private String currentSnellenOptotype = "E";
    private int gridStep = 100;
    private String optotypeFormatToDraw = "Snellen_Ref";
    private Paint paintColor = new Paint();

    public SnellenOptotypeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path optotypePath = new Path();
        String str = this.optotypeFormatToDraw;
        Integer obj = -1;
        switch (str.hashCode()) {
            case -2134689289:
                if (str.equals("Sloan_Ref")) {
                    obj = 4;
                    break;
                }
                break;
            case -1871417122:
                if (str.equals("Numbers_Ref")) {
                    obj = 5;
                    break;
                }
                break;
            case -1821882201:
                if (str.equals("Snellen_Ref")) {
                    obj = 0;
                    break;
                }
                break;
            case -715292570:
                if (str.equals("Tumbling_E_Ref")) {
                    obj = 2;
                    break;
                }
                break;
            case -618563388:
                if (str.equals("Landolt_C_Ref")) {
                    obj = 1;
                    break;
                }
                break;
            case 769155484:
                if (str.equals("LEA_Ref")) {
                    obj = 3;
                    break;
                }
                break;
        }
        int originOffsetX;
        int originOffsetY;
        switch (obj) {
            case 0:
                originOffsetX = canvas.getWidth() / 2;
                originOffsetY = canvas.getHeight() / 2;
                if (!this.currentSnellenOptotype.equals("E")) {
                    if (!this.currentSnellenOptotype.equals("T")) {
                        if (!this.currentSnellenOptotype.equals("F")) {
                            if (!this.currentSnellenOptotype.equals("Z")) {
                                if (!this.currentSnellenOptotype.equals("L")) {
                                    if (!this.currentSnellenOptotype.equals("O")) {
                                        if (!this.currentSnellenOptotype.equals("C")) {
                                            if (!this.currentSnellenOptotype.equals("D")) {
                                                if (!this.currentSnellenOptotype.equals("P")) {
                                                    optotypePath = snellenOptotypeE(originOffsetX, originOffsetY);
                                                    break;
                                                } else {
                                                    optotypePath = snellenOptotypeP(originOffsetX, originOffsetY);
                                                    break;
                                                }
                                            }
                                            optotypePath = snellenOptotypeD(originOffsetX, originOffsetY);
                                            break;
                                        }
                                        optotypePath = snellenOptotypeC(originOffsetX, originOffsetY);
                                        break;
                                    }
                                    optotypePath = snellenOptotypeO(originOffsetX, originOffsetY);
                                    break;
                                }
                                optotypePath = snellenOptotypeL(originOffsetX, originOffsetY);
                                break;
                            }
                            optotypePath = snellenOptotypeZ(originOffsetX, originOffsetY);
                            break;
                        }
                        optotypePath = snellenOptotypeF(originOffsetX, originOffsetY);
                        break;
                    }
                    optotypePath = snellenOptotypeT(originOffsetX, originOffsetY);
                    break;
                }
                optotypePath = snellenOptotypeE(originOffsetX, originOffsetY);
                break;
            case 1:
                originOffsetX = canvas.getWidth() / 2;
                originOffsetY = canvas.getHeight() / 2;
                canvas.rotate((float) this.currentRotationInDegrees, (float) originOffsetX, (float) originOffsetY);
                optotypePath = optotypeLandoltC(originOffsetX, originOffsetY);
                break;
            case 2:
                originOffsetX = canvas.getWidth() / 2;
                originOffsetY = canvas.getHeight() / 2;
                canvas.rotate((float) this.currentRotationInDegrees, (float) (canvas.getWidth() / 2), (float) (canvas.getHeight() / 2));
                optotypePath = optotypeTumblingE(originOffsetX, originOffsetY);
                break;
            case 3:
                originOffsetX = canvas.getWidth() / 2;
                originOffsetY = canvas.getHeight() / 2;
                switch (this.currentChildrenOptotype) {
                    case 0:
                        optotypePath = optotypeLeaSymbolApple(originOffsetX, originOffsetY);
                        break;
                    case 1:
                        optotypePath = optotypeLeaSymbolCircle(originOffsetX, originOffsetY);
                        break;
                    case 2:
                        optotypePath = optotypeLeaSymbolSquare(originOffsetX, originOffsetY);
                        break;
                    case 3:
                        optotypePath = optotypeLeaSymbolHouse(originOffsetX, originOffsetY);
                        break;
                    default:
                        optotypePath = optotypeLeaSymbolApple(originOffsetX, originOffsetY);
                        break;
                }
            case 4:
                originOffsetX = canvas.getWidth() / 2;
                originOffsetY = canvas.getHeight() / 2;
                switch (this.currentSloanOptotype) {
                    case 0:
                        optotypePath = optotypeSloanH(originOffsetX, originOffsetY);
                        break;
                    case 1:
                        optotypePath = optotypeSloanC(originOffsetX, originOffsetY);
                        break;
                    case 2:
                        optotypePath = optotypeSloanO(originOffsetX, originOffsetY);
                        break;
                    case 3:
                        optotypePath = optotypeSloanV(originOffsetX, originOffsetY);
                        break;
                    case 4:
                        optotypePath = optotypeSloanN(originOffsetX, originOffsetY);
                        break;
                    case 5:
                        optotypePath = optotypeSloanZ(originOffsetX, originOffsetY);
                        break;
                    case 6:
                        optotypePath = optotypeSloanD(originOffsetX, originOffsetY);
                        break;
                    default:
                        optotypePath = optotypeSloanH(originOffsetX, originOffsetY);
                        break;
                }
            case 5:
                originOffsetX = canvas.getWidth() / 2;
                originOffsetY = canvas.getHeight() / 2;
                switch (this.currentNumberOptotype) {
                    case 0:
                        optotypePath = optotypeNumber5(originOffsetX, originOffsetY);
                        break;
                    case 1:
                        optotypePath = optotypeNumber2(originOffsetX, originOffsetY);
                        break;
                    case 2:
                        optotypePath = optotypeNumber3(originOffsetX, originOffsetY);
                        break;
                    case 3:
                        optotypePath = optotypeNumber6(originOffsetX, originOffsetY);
                        break;
                    case 4:
                        optotypePath = optotypeNumber9(originOffsetX, originOffsetY);
                        break;
                    default:
                        optotypePath = optotypeNumber5(originOffsetX, originOffsetY);
                        break;
                }
            default:
                originOffsetX = canvas.getWidth() / 2;
                originOffsetY = canvas.getHeight() / 2;
                if (!this.currentSnellenOptotype.equals("E")) {
                    if (!this.currentSnellenOptotype.equals("T")) {
                        if (!this.currentSnellenOptotype.equals("F")) {
                            if (!this.currentSnellenOptotype.equals("Z")) {
                                if (!this.currentSnellenOptotype.equals("L")) {
                                    if (!this.currentSnellenOptotype.equals("O")) {
                                        if (!this.currentSnellenOptotype.equals("C")) {
                                            if (!this.currentSnellenOptotype.equals("D")) {
                                                if (!this.currentSnellenOptotype.equals("P")) {
                                                    optotypePath = snellenOptotypeE(originOffsetX, originOffsetY);
                                                    break;
                                                } else {
                                                    optotypePath = snellenOptotypeP(originOffsetX, originOffsetY);
                                                    break;
                                                }
                                            }
                                            optotypePath = snellenOptotypeD(originOffsetX, originOffsetY);
                                            break;
                                        }
                                        optotypePath = snellenOptotypeC(originOffsetX, originOffsetY);
                                        break;
                                    }
                                    optotypePath = snellenOptotypeO(originOffsetX, originOffsetY);
                                    break;
                                }
                                optotypePath = snellenOptotypeL(originOffsetX, originOffsetY);
                                break;
                            }
                            optotypePath = snellenOptotypeZ(originOffsetX, originOffsetY);
                            break;
                        }
                        optotypePath = snellenOptotypeF(originOffsetX, originOffsetY);
                        break;
                    }
                    optotypePath = snellenOptotypeT(originOffsetX, originOffsetY);
                    break;
                }
                optotypePath = snellenOptotypeE(originOffsetX, originOffsetY);
                break;
        }
        this.paintColor.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.paintColor.setAlpha(this.currentAlphaToDraw);
        this.paintColor.setStyle(Style.FILL);
        canvas.drawPath(optotypePath, this.paintColor);
    }

    public void setOptotypeFormat(String format) {
        this.optotypeFormatToDraw = format;
    }

    public void setOptotypeAlpha(int alpha) {
        if (alpha > 255) {
            alpha = 255;
        } else if (alpha < 0) {
            alpha = 0;
        }
        this.currentAlphaToDraw = alpha;
    }

    public void reDrawOptotype() {
        invalidate();
    }

    public boolean setOptotypeTotalPixelSize(int pxSize) {
        if (!validateGridStepSize(getWidth(), getHeight(), pxSize / 5)) {
            return false;
        }
        this.gridStep = pxSize / 5;
        return true;
    }

    public int getOptotypeTotalPixelSize() {
        return this.gridStep * 5;
    }

    public boolean increaseSize(int pxToIncrease) {
        if (!validateGridStepSize(getWidth(), getHeight(), this.gridStep + pxToIncrease)) {
            return false;
        }
        this.gridStep += pxToIncrease;
        return true;
    }

    public boolean decreaseSize(int pxToDecrease) {
        if (!validateGridStepSize(getWidth(), getHeight(), this.gridStep - pxToDecrease)) {
            return false;
        }
        this.gridStep -= pxToDecrease;
        return true;
    }

    private boolean validateGridStepSize(int width, int height, int gridStep) {
        int gridFormatSize;
        String str = this.optotypeFormatToDraw;
        boolean z = true;
        switch (str.hashCode()) {
            case -2134689289:
                if (str.equals("Sloan_Ref")) {
                    z = true;
                    break;
                }
                break;
            case -1871417122:
                if (str.equals("Numbers_Ref")) {
                    z = true;
                    break;
                }
                break;
            case -1821882201:
                if (str.equals("Snellen_Ref")) {
                    z = false;
                    break;
                }
                break;
            case -715292570:
                if (str.equals("Tumbling_E_Ref")) {
                    z = true;
                    break;
                }
                break;
            case -618563388:
                if (str.equals("Landolt_C_Ref")) {
                    z = true;
                    break;
                }
                break;
            case 769155484:
                if (str.equals("LEA_Ref")) {
                    z = true;
                    break;
                }
                break;
        }
        if (z == false) {
            gridFormatSize = 5;

        } else if (z == true) {
            gridFormatSize = 5;

        } else {
            gridFormatSize = 5;

        }
        if (gridStep <= 1) {
            return false;
        }
        if (width <= height) {
            if (gridFormatSize * gridStep >= width) {
                return false;
            }
        } else if (gridFormatSize * gridStep >= height) {
            return false;
        }
        return true;
    }

    public void selectNewRandomOptotype() {
        String str = this.optotypeFormatToDraw;
        Integer obj = -1;
        switch (str.hashCode()) {
            case -2134689289:
                if (str.equals("Sloan_Ref")) {
                    obj = 4;
                    break;
                }
                break;
            case -1871417122:
                if (str.equals("Numbers_Ref")) {
                    obj = 5;
                    break;
                }
                break;
            case -1821882201:
                if (str.equals("Snellen_Ref")) {
                    obj = 0;
                    break;
                }
                break;
            case -715292570:
                if (str.equals("Tumbling_E_Ref")) {
                    obj = 2;
                    break;
                }
                break;
            case -618563388:
                if (str.equals("Landolt_C_Ref")) {
                    obj = 1;
                    break;
                }
                break;
            case 769155484:
                if (str.equals("LEA_Ref")) {
                    obj = 3;
                    break;
                }
                break;
        }
        switch (obj) {
            case 0:
                generateNewRandomSnellenOptotypeAndRotation();
                return;
            case 1:
                generateNewRandomSnellenOptotypeAndRotation();
                return;
            case 2:
                generateNewRandomSnellenOptotypeAndRotation();
                return;
            case 3:
                generateNewRandomChildrenOptotype();
                return;
            case 4:
                generateNewRandomSloanOptotype();
                return;
            case 5:
                generateNewRandomNumbersOptotype();
                return;
            default:
                generateNewRandomSnellenOptotypeAndRotation();
                return;
        }
    }

    private void generateNewRandomSnellenOptotypeAndRotation() {
        String randomOptotype;
        int randomDegrees;
        String[] allOptotypesAvailable = new String[]{"E", "T", "F", "Z", "O", "L", "C", "D", "P"};
        int[] allRotationsAvailable = new int[]{0, 90, 180, 270};
        Random rand = new Random();
        do {
            randomOptotype = allOptotypesAvailable[rand.nextInt(allOptotypesAvailable.length)];
        } while (randomOptotype.equals(this.currentSnellenOptotype));
        this.currentSnellenOptotype = randomOptotype;
        do {
            randomDegrees = allRotationsAvailable[rand.nextInt(allRotationsAvailable.length)];
        } while (randomDegrees == this.currentRotationInDegrees);
        this.currentRotationInDegrees = randomDegrees;
    }

    private void generateNewRandomChildrenOptotype() {
        int randomOptotype;
        Random rand = new Random();
        do {
            randomOptotype = rand.nextInt(4);
        } while (randomOptotype == this.currentSloanOptotype);
        this.currentSloanOptotype = randomOptotype;
        do {
            randomOptotype = rand.nextInt(4);
        } while (randomOptotype == this.currentChildrenOptotype);
        this.currentChildrenOptotype = randomOptotype;
    }

    private void generateNewRandomSloanOptotype() {
        int randomOptotype;
        Random rand = new Random();
        do {
            randomOptotype = rand.nextInt(7);
        } while (randomOptotype == this.currentSloanOptotype);
        this.currentSloanOptotype = randomOptotype;
    }

    private void generateNewRandomNumbersOptotype() {
        int randomOptotype;
        Random rand = new Random();
        do {
            randomOptotype = rand.nextInt(5);
        } while (randomOptotype == this.currentNumberOptotype);
        this.currentNumberOptotype = randomOptotype;
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
        outerCirclePath.addCircle((float) ((((double) this.gridStep) * 2.5d) + ((double) originX)), (float) ((((double) this.gridStep) * 2.5d) + ((double) originY)), (float) (((double) this.gridStep) * 2.5d), Direction.CW);
        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float) ((((double) this.gridStep) * 2.5d) + ((double) originX)), (float) ((((double) this.gridStep) * 2.5d) + ((double) originY)), (float) (1.5d * ((double) this.gridStep)), Direction.CW);
        optotypePath.op(outerCirclePath, innerCirclePath, Op.DIFFERENCE);
        return optotypePath;
    }

    private Path snellenOptotypeC(int centerX, int centerY) {
        int originX = centerX - ((int) (2.5d * ((double) this.gridStep)));
        int originY = centerY - ((int) (2.5d * ((double) this.gridStep)));
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerCirclePath = new Path();
        outerCirclePath.addCircle((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)), (float) (2.5d * ((double) this.gridStep)), Direction.CW);
        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)), (float) (1.5d * ((double) this.gridStep)), Direction.CW);
        Path rectanglePath = new Path();
        rectanglePath.addRect((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY), (float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 3) + originY), Direction.CW);
        Path circlePath = new Path();
        circlePath.op(outerCirclePath, innerCirclePath, Op.DIFFERENCE);
        optotypePath.op(circlePath, rectanglePath, Op.DIFFERENCE);
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
        outerCircleHalfPath.addCircle((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)), (float) (2.5d * ((double) this.gridStep)), Direction.CW);
        Path rectanglePath = new Path();
        rectanglePath.addRect((float) originX, (float) originY, (float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 5) + originY), Direction.CW);
        outerCircleHalfPath.op(rectanglePath, Op.DIFFERENCE);
        Path innerCircleHalfPath = new Path();
        innerCircleHalfPath.addCircle((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)), (float) (1.5d * ((double) this.gridStep)), Direction.CW);
        innerCircleHalfPath.op(rectanglePath, Op.DIFFERENCE);
        optotypePath.op(structPath, outerCircleHalfPath, Op.UNION);
        optotypePath.op(innerCircleHalfPath, Op.DIFFERENCE);
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
        outerCircleHalfPath.addCircle((float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((1.5d * ((double) this.gridStep)) + ((double) originY)), (float) (1.5d * ((double) this.gridStep)), Direction.CW);
        Path rectanglePath = new Path();
        rectanglePath.addRect((float) originX, (float) originY, (float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 3) + originY), Direction.CW);
        outerCircleHalfPath.op(rectanglePath, Op.DIFFERENCE);
        Path innerCircleHalfPath = new Path();
        innerCircleHalfPath.addCircle((float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((1.5d * ((double) this.gridStep)) + ((double) originY)), (float) (0.5d * ((double) this.gridStep)), Direction.CW);
        innerCircleHalfPath.op(rectanglePath, Op.DIFFERENCE);
        optotypePath.op(structPath, outerCircleHalfPath, Op.UNION);
        optotypePath.op(innerCircleHalfPath, Op.DIFFERENCE);
        return optotypePath;
    }

    private Path optotypeTumblingE(int centerX, int centerY) {
        int originX = centerX - ((int) (((double) this.gridStep) * 2.5d));
        int originY = centerY - ((int) (((double) this.gridStep) * 2.5d));
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo((float) originX, (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) originY);
        return optotypePath;
    }

    private Path optotypeLandoltC(int centerX, int centerY) {
        int originX = centerX - ((int) (2.5d * ((double) this.gridStep)));
        int originY = centerY - ((int) (2.5d * ((double) this.gridStep)));
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerCirclePath = new Path();
        outerCirclePath.addCircle((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)), (float) (2.5d * ((double) this.gridStep)), Direction.CW);
        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)), (float) (1.5d * ((double) this.gridStep)), Direction.CW);
        Path rectanglePath = new Path();
        rectanglePath.addRect((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY), (float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 3) + originY), Direction.CW);
        Path circlePath = new Path();
        circlePath.op(outerCirclePath, innerCirclePath, Op.DIFFERENCE);
        optotypePath.op(circlePath, rectanglePath, Op.DIFFERENCE);
        return optotypePath;
    }

    private Path optotypeLeaSymbolHouse(int centerX, int centerY) {
        int originX = centerX - ((int) (((double) this.gridStep) * 3.5d));
        int originY = centerY - ((int) (((double) this.gridStep) * 3.5d));
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerHousePath = new Path();
        outerHousePath.moveTo((((float) this.gridStep) * 3.5f) + ((float) originX), (float) originY);
        outerHousePath.lineTo((8.2f * ((float) this.gridStep)) + ((float) originX), (float) ((this.gridStep * 2) + originY));
        outerHousePath.lineTo((float) ((this.gridStep * 7) + originX), (float) ((this.gridStep * 2) + originY));
        outerHousePath.lineTo((float) ((this.gridStep * 7) + originX), (float) ((this.gridStep * 7) + originY));
        outerHousePath.lineTo((float) originX, (float) ((this.gridStep * 7) + originY));
        outerHousePath.lineTo((float) originX, (float) ((this.gridStep * 2) + originY));
        outerHousePath.lineTo(((float) originX) - (1.2f * ((float) this.gridStep)), (float) ((this.gridStep * 2) + originY));
        outerHousePath.lineTo((((float) this.gridStep) * 3.5f) + ((float) originX), (float) originY);
        Path innerHousePath = new Path();
        innerHousePath.moveTo((((float) this.gridStep) * 3.5f) + ((float) originX), (float) (this.gridStep + originY));
        innerHousePath.lineTo((float) ((this.gridStep * 6) + originX), (((float) this.gridStep) * 2.0f) + ((float) originY));
        innerHousePath.lineTo((float) ((this.gridStep * 6) + originX), (float) ((this.gridStep * 6) + originY));
        innerHousePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 6) + originY));
        innerHousePath.lineTo((float) (this.gridStep + originX), (((float) this.gridStep) * 2.0f) + ((float) originY));
        innerHousePath.lineTo((((float) this.gridStep) * 3.5f) + ((float) originX), (float) (this.gridStep + originY));
        optotypePath.op(outerHousePath, innerHousePath, Op.DIFFERENCE);
        return optotypePath;
    }

    private Path optotypeLeaSymbolSquare(int centerX, int centerY) {
        int originX = centerX - ((int) (((double) this.gridStep) * 3.5d));
        int originY = centerY - ((int) (((double) this.gridStep) * 3.5d));
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerSquarePath = new Path();
        outerSquarePath.moveTo((float) originX, (float) originY);
        outerSquarePath.lineTo((float) ((this.gridStep * 7) + originX), (float) originY);
        outerSquarePath.lineTo((float) ((this.gridStep * 7) + originX), (float) ((this.gridStep * 7) + originY));
        outerSquarePath.lineTo((float) originX, (float) ((this.gridStep * 7) + originY));
        outerSquarePath.lineTo((float) originX, (float) originY);
        Path innerSquarePath = new Path();
        innerSquarePath.moveTo((float) (this.gridStep + originX), (float) (this.gridStep + originY));
        innerSquarePath.lineTo((float) ((this.gridStep * 6) + originX), (float) (this.gridStep + originY));
        innerSquarePath.lineTo((float) ((this.gridStep * 6) + originX), (float) ((this.gridStep * 6) + originY));
        innerSquarePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 6) + originY));
        innerSquarePath.lineTo((float) (this.gridStep + originX), (float) (this.gridStep + originY));
        optotypePath.op(outerSquarePath, innerSquarePath, Op.DIFFERENCE);
        return optotypePath;
    }

    private Path optotypeLeaSymbolCircle(int centerX, int centerY) {
        int originX = centerX - ((int) (((double) this.gridStep) * 3.5d));
        int originY = centerY - ((int) (((double) this.gridStep) * 3.5d));
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerCirclePath = new Path();
        outerCirclePath.addCircle((float) ((((double) this.gridStep) * 3.5d) + ((double) originX)), (float) ((((double) this.gridStep) * 3.5d) + ((double) originY)), (float) (((double) this.gridStep) * 3.5d), Direction.CW);
        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float) ((((double) this.gridStep) * 3.5d) + ((double) originX)), (float) ((((double) this.gridStep) * 3.5d) + ((double) originY)), (float) (2.5d * ((double) this.gridStep)), Direction.CW);
        optotypePath.op(outerCirclePath, innerCirclePath, Op.DIFFERENCE);
        return optotypePath;
    }

    private Path optotypeLeaSymbolApple(int centerX, int centerY) {
        int originX = centerX - ((int) (3.5d * ((double) this.gridStep)));
        int originY = centerY - ((int) (3.5d * ((double) this.gridStep)));
        Path outerApplePath = new Path();
        outerApplePath.moveTo((float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) (this.gridStep + originY));
        outerApplePath.cubicTo((float) ((this.gridStep * 4) + originX), (float) (((double) originY) - (0.5d * ((double) this.gridStep))), (float) ((this.gridStep * 7) + originX), (float) (((double) originY) - (0.5d * ((double) this.gridStep))), (float) ((this.gridStep * 7) + originX), (float) ((this.gridStep * 2) + originY));
        outerApplePath.cubicTo((float) ((this.gridStep * 7) + originX), (float) ((this.gridStep * 4) + originY), (float) ((this.gridStep * 5) + originX), (float) ((6.5d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 5) + originX), (float) ((6.5d * ((double) this.gridStep)) + ((double) originY)));
        outerApplePath.cubicTo((float) ((4.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 7) + originY), (float) ((4.3d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 7) + originY), (float) ((this.gridStep * 4) + originX), (float) ((6.5d * ((double) this.gridStep)) + ((double) originY)));
        outerApplePath.cubicTo((float) ((3.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((6.1d * ((double) this.gridStep)) + ((double) originY)), (float) ((3.3d * ((double) this.gridStep)) + ((double) originX)), (float) ((6.1d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 3) + originX), (float) ((6.5d * ((double) this.gridStep)) + ((double) originY)));
        outerApplePath.cubicTo((float) ((2.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 7) + originY), (float) ((2.3d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 7) + originY), (float) ((this.gridStep * 2) + originX), (float) ((6.5d * ((double) this.gridStep)) + ((double) originY)));
        outerApplePath.cubicTo((float) ((this.gridStep * 2) + originX), (float) ((6.5d * ((double) this.gridStep)) + ((double) originY)), (float) originX, (float) ((this.gridStep * 4) + originY), (float) originX, (float) ((this.gridStep * 2) + originY));
        outerApplePath.cubicTo((float) ((0.2d * ((double) this.gridStep)) + ((double) originX)), (float) originY, (float) ((2.7d * ((double) this.gridStep)) + ((double) originX)), (float) originY, (float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) (this.gridStep + originY));
        Path innerApplePath = new Path();
        innerApplePath.moveTo((float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)));
        innerApplePath.cubicTo((float) ((4.3d * ((double) this.gridStep)) + ((double) originX)), (float) ((0.2d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 6) + originX), (float) ((0.7d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 6) + originX), (float) ((this.gridStep * 2) + originY));
        innerApplePath.cubicTo((float) ((this.gridStep * 6) + originX), (float) ((this.gridStep * 3) + originY), (float) ((5.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((4.5d * ((double) this.gridStep)) + ((double) originY)), (float) ((4.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((5.5d * ((double) this.gridStep)) + ((double) originY)));
        innerApplePath.cubicTo((float) ((4.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((5.8d * ((double) this.gridStep)) + ((double) originY)), (float) ((4.1d * ((double) this.gridStep)) + ((double) originX)), (float) ((5.8d * ((double) this.gridStep)) + ((double) originY)), (float) ((3.9d * ((double) this.gridStep)) + ((double) originX)), (float) ((5.5d * ((double) this.gridStep)) + ((double) originY)));
        innerApplePath.cubicTo((float) ((3.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((5.2d * ((double) this.gridStep)) + ((double) originY)), (float) ((3.3d * ((double) this.gridStep)) + ((double) originX)), (float) ((5.2d * ((double) this.gridStep)) + ((double) originY)), (float) ((3.1d * ((double) this.gridStep)) + ((double) originX)), (float) ((5.5d * ((double) this.gridStep)) + ((double) originY)));
        innerApplePath.cubicTo((float) ((2.9d * ((double) this.gridStep)) + ((double) originX)), (float) ((5.8d * ((double) this.gridStep)) + ((double) originY)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((5.8d * ((double) this.gridStep)) + ((double) originY)), (float) ((2.3d * ((double) this.gridStep)) + ((double) originX)), (float) ((5.5d * ((double) this.gridStep)) + ((double) originY)));
        innerApplePath.cubicTo((float) ((2.3d * ((double) this.gridStep)) + ((double) originX)), (float) ((5.5d * ((double) this.gridStep)) + ((double) originY)), (float) (this.gridStep + originX), (float) ((this.gridStep * 4) + originY), (float) (this.gridStep + originX), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)));
        innerApplePath.cubicTo((float) (this.gridStep + originX), (float) ((0.8d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 3) + originX), (float) ((0.9d * ((double) this.gridStep)) + ((double) originY)), (float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)));
        Path completeOptotypePath = new Path();
        completeOptotypePath.reset();
        completeOptotypePath.op(outerApplePath, innerApplePath, Op.DIFFERENCE);
        return completeOptotypePath;
    }

    private Path optotypeSloanC(int centerX, int centerY) {
        int originX = centerX - ((int) (2.5d * ((double) this.gridStep)));
        int originY = centerY - ((int) (2.5d * ((double) this.gridStep)));
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerCirclePath = new Path();
        outerCirclePath.addCircle((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)), (float) (2.5d * ((double) this.gridStep)), Direction.CW);
        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)), (float) (1.5d * ((double) this.gridStep)), Direction.CW);
        Path rectanglePath = new Path();
        rectanglePath.addRect((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY), (float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 3) + originY), Direction.CW);
        Path circlePath = new Path();
        circlePath.op(outerCirclePath, innerCirclePath, Op.DIFFERENCE);
        optotypePath.op(circlePath, rectanglePath, Op.DIFFERENCE);
        return optotypePath;
    }

    private Path optotypeSloanO(int centerX, int centerY) {
        int originX = centerX - ((int) (((double) this.gridStep) * 2.5d));
        int originY = centerY - ((int) (((double) this.gridStep) * 2.5d));
        Path optotypePath = new Path();
        optotypePath.reset();
        Path outerCirclePath = new Path();
        outerCirclePath.addCircle((float) ((((double) this.gridStep) * 2.5d) + ((double) originX)), (float) ((((double) this.gridStep) * 2.5d) + ((double) originY)), (float) (((double) this.gridStep) * 2.5d), Direction.CW);
        Path innerCirclePath = new Path();
        innerCirclePath.addCircle((float) ((((double) this.gridStep) * 2.5d) + ((double) originX)), (float) ((((double) this.gridStep) * 2.5d) + ((double) originY)), (float) (1.5d * ((double) this.gridStep)), Direction.CW);
        optotypePath.op(outerCirclePath, innerCirclePath, Op.DIFFERENCE);
        return optotypePath;
    }

    private Path optotypeSloanH(int centerX, int centerY) {
        int originX = centerX - ((int) (((double) this.gridStep) * 2.5d));
        int originY = centerY - ((int) (((double) this.gridStep) * 2.5d));
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo((float) originX, (float) originY);
        optotypePath.lineTo((float) (this.gridStep + originX), (float) originY);
        optotypePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) originY);
        return optotypePath;
    }

    private Path optotypeSloanV(int centerX, int centerY) {
        int originX = centerX - ((int) (((double) this.gridStep) * 2.5d));
        int originY = centerY - ((int) (((double) this.gridStep) * 2.5d));
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo((float) originX, (float) originY);
        optotypePath.lineTo((float) (this.gridStep + originX), (float) originY);
        optotypePath.lineTo((float) ((((double) this.gridStep) * 2.5d) + ((double) originX)), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 3) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 2) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) originY);
        return optotypePath;
    }

    private Path optotypeSloanN(int centerX, int centerY) {
        int originX = centerX - ((int) (((double) this.gridStep) * 2.5d));
        int originY = centerY - ((int) (((double) this.gridStep) * 2.5d));
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo((float) originX, (float) originY);
        optotypePath.lineTo((float) (this.gridStep + originX), (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((3.5d * ((double) this.gridStep)) + ((double) originY)));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) ((1.5d * ((double) this.gridStep)) + ((double) originY)));
        optotypePath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) originY);
        return optotypePath;
    }

    private Path optotypeSloanZ(int centerX, int centerY) {
        int originX = centerX - ((int) (((double) this.gridStep) * 2.5d));
        int originY = centerY - ((int) (((double) this.gridStep) * 2.5d));
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo((float) originX, (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) originY);
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((1.5f * ((float) this.gridStep)) + ((float) originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((3.5f * ((float) this.gridStep)) + ((float) originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((float) originX, (float) (this.gridStep + originY));
        optotypePath.lineTo((float) originX, (float) originY);
        return optotypePath;
    }

    private Path optotypeSloanD(int centerX, int centerY) {
        int originX = centerX - ((int) (2.5d * ((double) this.gridStep)));
        int originY = centerY - ((int) (2.5d * ((double) this.gridStep)));
        Path optotypePath = new Path();
        optotypePath.reset();
        Path structPath = new Path();
        structPath.moveTo((float) originX, (float) originY);
        structPath.lineTo(((float) (2.5d * ((double) this.gridStep))) + ((float) originX), (float) originY);
        structPath.lineTo(((float) (2.5d * ((double) this.gridStep))) + ((float) originX), (float) (this.gridStep + originY));
        structPath.lineTo((float) (this.gridStep + originX), (float) (this.gridStep + originY));
        structPath.lineTo((float) (this.gridStep + originX), (float) ((this.gridStep * 4) + originY));
        structPath.lineTo(((float) (2.5d * ((double) this.gridStep))) + ((float) originX), (float) ((this.gridStep * 4) + originY));
        structPath.lineTo(((float) (2.5d * ((double) this.gridStep))) + ((float) originX), (float) ((this.gridStep * 5) + originY));
        structPath.lineTo((float) originX, (float) ((this.gridStep * 5) + originY));
        structPath.lineTo((float) originX, (float) originY);
        Path outerCircleHalfPath = new Path();
        outerCircleHalfPath.addCircle((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)), (float) (2.5d * ((double) this.gridStep)), Direction.CW);
        Path rectanglePath = new Path();
        rectanglePath.addRect((float) originX, (float) originY, (float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 5) + originY), Direction.CW);
        outerCircleHalfPath.op(rectanglePath, Op.DIFFERENCE);
        Path innerCircleHalfPath = new Path();
        innerCircleHalfPath.addCircle((float) ((2.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)), (float) (1.5d * ((double) this.gridStep)), Direction.CW);
        innerCircleHalfPath.op(rectanglePath, Op.DIFFERENCE);
        optotypePath.op(structPath, outerCircleHalfPath, Op.UNION);
        optotypePath.op(innerCircleHalfPath, Op.DIFFERENCE);
        return optotypePath;
    }

    private Path optotypeNumber9(int centerX, int centerY) {
        int originX = centerX - ((int) (2.5d * ((double) this.gridStep)));
        int originY = centerY - ((int) (2.5d * ((double) this.gridStep)));
        Path outerPath = new Path();
        outerPath.reset();
        outerPath.moveTo((float) originX, (float) (this.gridStep + originY));
        outerPath.cubicTo((float) ((this.gridStep * 0) + originX), (float) ((0.3d * ((double) this.gridStep)) + ((double) originY)), (float) ((0.3d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 0) + originY), (float) ((this.gridStep * 1) + originX), (float) originY);
        outerPath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 0) + originY));
        outerPath.cubicTo((float) ((4.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 0) + originY), (float) ((this.gridStep * 5) + originX), (float) ((0.3d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 1) + originY));
        outerPath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 4) + originY));
        outerPath.cubicTo((float) ((this.gridStep * 5) + originX), (float) ((4.7d * ((double) this.gridStep)) + ((double) originY)), (float) ((4.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 5) + originY), (float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 5) + originY));
        outerPath.lineTo((float) ((this.gridStep * 1) + originX), (float) ((this.gridStep * 5) + originY));
        outerPath.cubicTo((float) ((0.3d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 5) + originY), (float) ((this.gridStep * 0) + originX), (float) ((4.7d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 0) + originX), (float) ((this.gridStep * 4) + originY));
        outerPath.lineTo((float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 4) + originY));
        outerPath.cubicTo((float) ((3.9d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 4) + originY), (float) ((this.gridStep * 4) + originX), (float) ((3.9d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 4) + originX), (float) ((3.5d * ((double) this.gridStep)) + ((double) originY)));
        outerPath.cubicTo((float) ((this.gridStep * 4) + originX), (float) ((3.1d * ((double) this.gridStep)) + ((double) originY)), (float) ((3.9d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 3) + originY), (float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 3) + originY));
        outerPath.lineTo((float) ((this.gridStep * 1) + originX), (float) ((this.gridStep * 3) + originY));
        outerPath.cubicTo((float) ((0.3d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 3) + originY), (float) ((this.gridStep * 0) + originX), (float) ((2.7d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 0) + originX), (float) ((this.gridStep * 2) + originY));
        outerPath.lineTo((float) ((this.gridStep * 0) + originX), (float) ((this.gridStep * 1) + originY));
        Path innerPath = new Path();
        innerPath.moveTo((float) ((this.gridStep * 2) + originX), (float) ((this.gridStep * 1) + originY));
        innerPath.lineTo((float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 1) + originY));
        innerPath.cubicTo((float) ((3.9d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 1) + originY), (float) ((this.gridStep * 4) + originX), (float) ((1.1d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 4) + originX), (float) ((1.5d * ((double) this.gridStep)) + ((double) originY)));
        innerPath.cubicTo((float) ((this.gridStep * 4) + originX), (float) ((1.9d * ((double) this.gridStep)) + ((double) originY)), (float) ((3.9d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY), (float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY));
        innerPath.lineTo((float) ((1.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY));
        innerPath.cubicTo((float) ((1.1d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY), (float) ((this.gridStep * 1) + originX), (float) ((1.9d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 1) + originX), (float) ((1.5d * ((double) this.gridStep)) + ((double) originY)));
        innerPath.cubicTo((float) ((this.gridStep * 1) + originX), (float) ((1.1d * ((double) this.gridStep)) + ((double) originY)), (float) ((1.1d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 1) + originY), (float) ((1.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 1) + originY));
        innerPath.lineTo((float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 1) + originY));
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.op(outerPath, innerPath, Op.DIFFERENCE);
        return optotypePath;
    }

    private Path optotypeNumber6(int centerX, int centerY) {
        int originX = centerX - ((int) (2.5d * ((double) this.gridStep)));
        int originY = centerY - ((int) (2.5d * ((double) this.gridStep)));
        Path outerPath = new Path();
        outerPath.reset();
        outerPath.moveTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 1) + originY));
        outerPath.cubicTo((float) ((this.gridStep * 5) + originX), (float) ((0.7d * ((double) this.gridStep)) + ((double) originY)), (float) ((4.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 0) + originY), (float) ((this.gridStep * 4) + originX), (float) originY);
        outerPath.lineTo((float) ((this.gridStep * 1) + originX), (float) ((this.gridStep * 0) + originY));
        outerPath.cubicTo((float) ((0.3d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 0) + originY), (float) ((this.gridStep * 0) + originX), (float) ((0.3d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 0) + originX), (float) ((this.gridStep * 1) + originY));
        outerPath.lineTo((float) ((this.gridStep * 0) + originX), (float) ((this.gridStep * 4) + originY));
        outerPath.cubicTo((float) ((this.gridStep * 0) + originX), (float) ((4.7d * ((double) this.gridStep)) + ((double) originY)), (float) ((0.3d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 5) + originY), (float) ((this.gridStep * 1) + originX), (float) ((this.gridStep * 5) + originY));
        outerPath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 5) + originY));
        outerPath.cubicTo((float) ((4.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 5) + originY), (float) ((this.gridStep * 5) + originX), (float) ((4.7d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 4) + originY));
        outerPath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 3) + originY));
        outerPath.cubicTo((float) ((this.gridStep * 5) + originX), (float) ((2.3d * ((double) this.gridStep)) + ((double) originY)), (float) ((4.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY), (float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 2) + originY));
        outerPath.lineTo((float) ((1.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY));
        outerPath.cubicTo((float) ((1.1d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY), (float) ((this.gridStep * 1) + originX), (float) ((1.9d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 1) + originX), (float) ((1.5d * ((double) this.gridStep)) + ((double) originY)));
        outerPath.cubicTo((float) ((this.gridStep * 1) + originX), (float) ((1.1d * ((double) this.gridStep)) + ((double) originY)), (float) ((1.1d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 1) + originY), (float) ((1.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 1) + originY));
        outerPath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 1) + originY));
        Path innerPath = new Path();
        innerPath.moveTo((float) ((this.gridStep * 2) + originX), (float) ((this.gridStep * 3) + originY));
        innerPath.lineTo((float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 3) + originY));
        innerPath.cubicTo((float) ((3.9d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 3) + originY), (float) ((this.gridStep * 4) + originX), (float) ((3.1d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 4) + originX), (float) ((3.5d * ((double) this.gridStep)) + ((double) originY)));
        innerPath.cubicTo((float) ((this.gridStep * 4) + originX), (float) ((3.9d * ((double) this.gridStep)) + ((double) originY)), (float) ((3.9d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 4) + originY), (float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 4) + originY));
        innerPath.lineTo((float) ((1.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 4) + originY));
        innerPath.cubicTo((float) ((1.1d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 4) + originY), (float) ((this.gridStep * 1) + originX), (float) ((3.9d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 1) + originX), (float) ((3.5d * ((double) this.gridStep)) + ((double) originY)));
        innerPath.cubicTo((float) ((this.gridStep * 1) + originX), (float) ((3.1d * ((double) this.gridStep)) + ((double) originY)), (float) ((1.1d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 3) + originY), (float) ((1.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 3) + originY));
        innerPath.lineTo((float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 3) + originY));
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.op(outerPath, innerPath, Op.DIFFERENCE);
        return optotypePath;
    }

    private Path optotypeNumber3(int centerX, int centerY) {
        int originX = centerX - ((int) (2.5d * ((double) this.gridStep)));
        int originY = centerY - ((int) (2.5d * ((double) this.gridStep)));
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo((float) originX, (float) (this.gridStep + originY));
        optotypePath.cubicTo((float) originX, (float) ((0.4d * ((double) this.gridStep)) + ((double) originY)), (float) ((0.4d * ((double) this.gridStep)) + ((double) originX)), (float) originY, (float) (this.gridStep + originX), (float) originY);
        optotypePath.lineTo(((float) (this.gridStep * 4)) + ((float) originX), (float) originY);
        optotypePath.cubicTo((float) ((4.7d * ((double) this.gridStep)) + ((double) originX)), (float) originY, (float) ((this.gridStep * 5) + originX), (float) ((0.3d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 1) + originY));
        optotypePath.lineTo(((float) (this.gridStep * 5)) + ((float) originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.cubicTo((float) ((this.gridStep * 5) + originX), (float) ((2.2d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 5) + originX), (float) ((2.4d * ((double) this.gridStep)) + ((double) originY)), (float) ((4.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((2.5d * ((double) this.gridStep)) + ((double) originY)));
        optotypePath.cubicTo((float) ((this.gridStep * 5) + originX), (float) ((2.6d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 5) + originX), (float) ((2.8d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo(((float) (this.gridStep * 5)) + ((float) originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.cubicTo((float) ((this.gridStep * 5) + originX), (float) ((4.7d * ((double) this.gridStep)) + ((double) originY)), (float) ((4.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 5) + originY), (float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo(((float) (this.gridStep * 1)) + ((float) originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.cubicTo((float) ((0.3d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 5) + originY), (float) ((this.gridStep * 0) + originX), (float) ((4.7d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 0) + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo(((float) (3.5d * ((double) this.gridStep))) + ((float) originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.cubicTo((float) ((3.9d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 4) + originY), (float) ((this.gridStep * 4) + originX), (float) ((3.9d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 4) + originX), (float) ((3.5d * ((double) this.gridStep)) + ((double) originY)));
        optotypePath.cubicTo((float) ((this.gridStep * 4) + originX), (float) ((3.1d * ((double) this.gridStep)) + ((double) originY)), (float) ((3.9d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 3) + originY), (float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo(((float) (this.gridStep * 1)) + ((float) originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo(((float) (this.gridStep * 1)) + ((float) originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo(((float) (3.5d * ((double) this.gridStep))) + ((float) originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.cubicTo((float) ((3.9d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY), (float) ((this.gridStep * 4) + originX), (float) ((1.9d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 4) + originX), (float) ((1.5d * ((double) this.gridStep)) + ((double) originY)));
        optotypePath.cubicTo((float) ((this.gridStep * 4) + originX), (float) ((1.1d * ((double) this.gridStep)) + ((double) originY)), (float) ((3.9d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 1) + originY), (float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 1) + originY));
        optotypePath.lineTo(((float) (this.gridStep * 0)) + ((float) originX), (float) ((this.gridStep * 1) + originY));
        return optotypePath;
    }

    private Path optotypeNumber2(int centerX, int centerY) {
        int originX = centerX - ((int) (2.5d * ((double) this.gridStep)));
        int originY = centerY - ((int) (2.5d * ((double) this.gridStep)));
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo((float) originX, (float) (this.gridStep + originY));
        optotypePath.cubicTo((float) originX, (float) ((0.4d * ((double) this.gridStep)) + ((double) originY)), (float) ((0.4d * ((double) this.gridStep)) + ((double) originX)), (float) originY, (float) (this.gridStep + originX), (float) originY);
        optotypePath.lineTo(((float) (this.gridStep * 4)) + ((float) originX), (float) originY);
        optotypePath.cubicTo((float) ((4.7d * ((double) this.gridStep)) + ((double) originX)), (float) originY, (float) ((this.gridStep * 5) + originX), (float) ((0.3d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 1) + originY));
        optotypePath.lineTo(((float) (this.gridStep * 5)) + ((float) originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.cubicTo((float) ((this.gridStep * 5) + originX), (float) ((2.7d * ((double) this.gridStep)) + ((double) originY)), (float) ((4.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 3) + originY), (float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo(((float) (1.5d * ((double) this.gridStep))) + ((float) originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.cubicTo((float) ((1.1d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 3) + originY), (float) ((this.gridStep * 1) + originX), (float) ((2.9d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 1) + originX), (float) ((3.5d * ((double) this.gridStep)) + ((double) originY)));
        optotypePath.lineTo(((float) (this.gridStep * 1)) + ((float) originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo(((float) (this.gridStep * 5)) + ((float) originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.cubicTo((float) ((this.gridStep * 5) + originX), (float) ((4.7d * ((double) this.gridStep)) + ((double) originY)), (float) ((4.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 5) + originY), (float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) originX, (float) ((3.5d * ((double) this.gridStep)) + ((double) originY)));
        optotypePath.cubicTo((float) ((this.gridStep * 0) + originX), (float) ((2.3d * ((double) this.gridStep)) + ((double) originY)), (float) ((0.3d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY), (float) ((1.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY));
        optotypePath.cubicTo((float) ((3.9d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY), (float) ((this.gridStep * 4) + originX), (float) ((1.9d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 4) + originX), (float) ((1.5d * ((double) this.gridStep)) + ((double) originY)));
        optotypePath.cubicTo((float) ((this.gridStep * 4) + originX), (float) ((1.1d * ((double) this.gridStep)) + ((double) originY)), (float) ((3.9d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 1) + originY), (float) ((3.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 1) + originY));
        optotypePath.lineTo((float) originX, (float) ((this.gridStep * 1) + originY));
        return optotypePath;
    }

    private Path optotypeNumber5(int centerX, int centerY) {
        int originX = centerX - ((int) (2.5d * ((double) this.gridStep)));
        int originY = centerY - ((int) (2.5d * ((double) this.gridStep)));
        Path optotypePath = new Path();
        optotypePath.reset();
        optotypePath.moveTo((float) originX, (float) originY);
        optotypePath.lineTo(((float) (this.gridStep * 5)) + ((float) originX), (float) originY);
        optotypePath.lineTo(((float) (this.gridStep * 5)) + ((float) originX), (float) (this.gridStep + originY));
        optotypePath.lineTo((1.5f * ((float) this.gridStep)) + ((float) originX), (float) (this.gridStep + originY));
        optotypePath.cubicTo((float) ((1.1d * ((double) this.gridStep)) + ((double) originX)), (float) (this.gridStep + originY), (float) (this.gridStep + originX), (float) ((1.1d * ((double) this.gridStep)) + ((double) originY)), (float) (this.gridStep + originX), (float) ((1.5d * ((double) this.gridStep)) + ((double) originY)));
        optotypePath.cubicTo((float) (this.gridStep + originX), (float) ((1.9d * ((double) this.gridStep)) + ((double) originY)), (float) ((1.1d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY), (float) ((1.5d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 2) + originY));
        optotypePath.cubicTo((float) ((4.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 2) + originY), (float) ((this.gridStep * 5) + originX), (float) ((2.3d * ((double) this.gridStep)) + ((double) originY)), (float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 5) + originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.cubicTo((float) ((this.gridStep * 5) + originX), (float) ((4.7d * ((double) this.gridStep)) + ((double) originY)), (float) ((4.7d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 5) + originY), (float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.lineTo((float) ((this.gridStep * 1) + originX), (float) ((this.gridStep * 5) + originY));
        optotypePath.cubicTo((float) ((0.4d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 5) + originY), (float) ((this.gridStep * 0) + originX), (float) ((4.6d * ((double) this.gridStep)) + ((double) originY)), (float) originX, (float) ((this.gridStep * 4) + originY));
        optotypePath.lineTo((3.5f * ((float) this.gridStep)) + ((float) originX), (float) ((this.gridStep * 4) + originY));
        optotypePath.cubicTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 4) + originY), (float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 4) + originY), (4.0f * ((float) this.gridStep)) + ((float) originX), (float) ((3.5d * ((double) this.gridStep)) + ((double) originY)));
        optotypePath.cubicTo((float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 3) + originY), (float) ((this.gridStep * 4) + originX), (float) ((this.gridStep * 3) + originY), (3.5f * ((float) this.gridStep)) + ((float) originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.lineTo(((float) this.gridStep) + ((float) originX), (float) ((this.gridStep * 3) + originY));
        optotypePath.cubicTo((float) ((0.3d * ((double) this.gridStep)) + ((double) originX)), (float) ((this.gridStep * 3) + originY), (float) ((this.gridStep * 0) + originX), (float) ((2.7d * ((double) this.gridStep)) + ((double) originY)), (float) originX, (float) ((this.gridStep * 2) + originY));
        optotypePath.lineTo((float) originX, (float) originY);
        return optotypePath;
    }
}
