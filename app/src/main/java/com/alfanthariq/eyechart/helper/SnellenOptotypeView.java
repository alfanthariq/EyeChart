package com.alfanthariq.eyechart.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import com.alfanthariq.eyechart.helper.ProgressionToolbox.Optotype;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SnellenOptotypeView extends View {
    private int canvasHeigth;
    private int canvasWidth;
    private int currentAlphaToDraw = 255;
    private int currentDrawOptotypeIndex = 0;
    private int currentDrawRotationInDegrees = 0;
    private int gridStep = 100;
    private boolean isLineModeOn;
    private boolean isToDrawNewOptotypes;
    private boolean isToFindBiggestOptotype;
    public OnOptotypeUpdateListener listener;
    private String optotypeFormatToDraw = "Snellen_Ref";
    private Paint paintColor;
    private ProgressionToolbox progressionToolbox;

    public interface OnOptotypeUpdateListener {
        void updateOptotypeRelatedInfo(ArrayList<ProgressionToolbox.Optotype> arrayList);
    }

    public SnellenOptotypeView(Activity fragmentActivity, SharedPreferences sharedPreferences, AcuityToolbox2 act) {
        super(fragmentActivity);
        String string = sharedPreferences.getString("optotype_display_mode", "");
        String string2 = sharedPreferences.getString("optotype_preference", "");
        String string3 = sharedPreferences.getString("optotype_alpha_preference", "255");
        this.optotypeFormatToDraw = string2;
        this.progressionToolbox = new ProgressionToolbox(sharedPreferences, fragmentActivity, act);
        this.isToFindBiggestOptotype = true;
        this.isToDrawNewOptotypes = true;
        setOptotypeAlpha(Integer.parseInt(string3));
        if (string.equalsIgnoreCase("Line")) {
            this.isLineModeOn = true;
        } else {
            this.isLineModeOn = false;
        }
        this.paintColor = new Paint();
        try {
            this.paintColor.setColor(Color.parseColor(sharedPreferences.getString("optotype_color", "#000000")));
        } catch (NumberFormatException unused) {
            this.paintColor.setColor(ViewCompat.MEASURED_STATE_MASK);
        }
    }

    public SnellenOptotypeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("Optotype View", "onDraw");
        this.canvasWidth = canvas.getWidth();
        this.canvasHeigth = canvas.getHeight();
        if (this.isToFindBiggestOptotype && this.progressionToolbox.calculateBiggestOptotypeForThisScreen(Boolean.valueOf(this.isLineModeOn), this.canvasWidth, this.canvasHeigth).booleanValue()) {
            this.isToFindBiggestOptotype = false;
            validateOptotypeTotalPixelSize(((Optotype) this.progressionToolbox.getCurrentOptotypesListToDraw().get(0)).totalSizeInPixels);
        }
        if (this.progressionToolbox != null && this.progressionToolbox.getCurrentOptotypesListToDraw().size() > 0) {
            Iterator it = this.progressionToolbox.getCurrentOptotypesListToDraw().iterator();
            while (it.hasNext()) {
                Optotype optotype = (ProgressionToolbox.Optotype) it.next();
                StringBuilder sb = new StringBuilder();
                sb.append("Draw Regular:");
                sb.append(optotype.totalSizeInPixels);
                Log.i("Optotype View", sb.toString());
                if (this.isToDrawNewOptotypes) {
                    selectNewRandomOptotype();
                }
                Path drawOneOptotypeAtPosition = drawOneOptotypeAtPosition(this.optotypeFormatToDraw, optotype.xPosition, optotype.yPosition);
                this.paintColor.setAlpha(this.currentAlphaToDraw);
                this.paintColor.setStyle(Paint.Style.FILL);
                canvas.drawPath(drawOneOptotypeAtPosition, this.paintColor);
            }
        }
        this.listener.updateOptotypeRelatedInfo(this.progressionToolbox.getCurrentOptotypesListToDraw());
        this.isToDrawNewOptotypes = false;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.Path drawOneOptotypeAtPosition(java.lang.String r2, int r3, int r4) {
        /*
            r1 = this;
            android.graphics.Path r0 = new android.graphics.Path
            r0.<init>()
            int r0 = r2.hashCode()
            switch(r0) {
                case -2134689289: goto L_0x003f;
                case -1871417122: goto L_0x0035;
                case -1821882201: goto L_0x002b;
                case -715292570: goto L_0x0021;
                case -618563388: goto L_0x0017;
                case 769155484: goto L_0x000d;
                default: goto L_0x000c;
            }
        L_0x000c:
            goto L_0x0049
        L_0x000d:
            java.lang.String r0 = "LEA_Ref"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0049
            r2 = 3
            goto L_0x004a
        L_0x0017:
            java.lang.String r0 = "Landolt_C_Ref"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0049
            r2 = 1
            goto L_0x004a
        L_0x0021:
            java.lang.String r0 = "Tumbling_E_Ref"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0049
            r2 = 2
            goto L_0x004a
        L_0x002b:
            java.lang.String r0 = "Snellen_Ref"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0049
            r2 = 0
            goto L_0x004a
        L_0x0035:
            java.lang.String r0 = "Numbers_Ref"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0049
            r2 = 5
            goto L_0x004a
        L_0x003f:
            java.lang.String r0 = "Sloan_Ref"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0049
            r2 = 4
            goto L_0x004a
        L_0x0049:
            r2 = -1
        L_0x004a:
            switch(r2) {
                case 0: goto L_0x00fb;
                case 1: goto L_0x00ea;
                case 2: goto L_0x00d9;
                case 3: goto L_0x00b6;
                case 4: goto L_0x0081;
                case 5: goto L_0x0058;
                default: goto L_0x004d;
            }
        L_0x004d:
            int r2 = r1.currentDrawOptotypeIndex
            switch(r2) {
                case 0: goto L_0x015a;
                case 1: goto L_0x0155;
                case 2: goto L_0x0150;
                case 3: goto L_0x014b;
                case 4: goto L_0x0146;
                case 5: goto L_0x0141;
                case 6: goto L_0x013c;
                case 7: goto L_0x0137;
                case 8: goto L_0x0132;
                default: goto L_0x0052;
            }
        L_0x0052:
            android.graphics.Path r2 = r1.snellenOptotypeE(r3, r4)
            goto L_0x015e
        L_0x0058:
            int r2 = r1.currentDrawOptotypeIndex
            switch(r2) {
                case 0: goto L_0x007b;
                case 1: goto L_0x0075;
                case 2: goto L_0x006f;
                case 3: goto L_0x0069;
                case 4: goto L_0x0063;
                default: goto L_0x005d;
            }
        L_0x005d:
            android.graphics.Path r2 = r1.optotypeNumber5(r3, r4)
            goto L_0x015e
        L_0x0063:
            android.graphics.Path r2 = r1.optotypeNumber9(r3, r4)
            goto L_0x015e
        L_0x0069:
            android.graphics.Path r2 = r1.optotypeNumber6(r3, r4)
            goto L_0x015e
        L_0x006f:
            android.graphics.Path r2 = r1.optotypeNumber3(r3, r4)
            goto L_0x015e
        L_0x0075:
            android.graphics.Path r2 = r1.optotypeNumber2(r3, r4)
            goto L_0x015e
        L_0x007b:
            android.graphics.Path r2 = r1.optotypeNumber5(r3, r4)
            goto L_0x015e
        L_0x0081:
            int r2 = r1.currentDrawOptotypeIndex
            switch(r2) {
                case 0: goto L_0x00b0;
                case 1: goto L_0x00aa;
                case 2: goto L_0x00a4;
                case 3: goto L_0x009e;
                case 4: goto L_0x0098;
                case 5: goto L_0x0092;
                case 6: goto L_0x008c;
                default: goto L_0x0086;
            }
        L_0x0086:
            android.graphics.Path r2 = r1.optotypeSloanH(r3, r4)
            goto L_0x015e
        L_0x008c:
            android.graphics.Path r2 = r1.optotypeSloanD(r3, r4)
            goto L_0x015e
        L_0x0092:
            android.graphics.Path r2 = r1.optotypeSloanZ(r3, r4)
            goto L_0x015e
        L_0x0098:
            android.graphics.Path r2 = r1.optotypeSloanN(r3, r4)
            goto L_0x015e
        L_0x009e:
            android.graphics.Path r2 = r1.optotypeSloanV(r3, r4)
            goto L_0x015e
        L_0x00a4:
            android.graphics.Path r2 = r1.optotypeSloanO(r3, r4)
            goto L_0x015e
        L_0x00aa:
            android.graphics.Path r2 = r1.optotypeSloanC(r3, r4)
            goto L_0x015e
        L_0x00b0:
            android.graphics.Path r2 = r1.optotypeSloanH(r3, r4)
            goto L_0x015e
        L_0x00b6:
            int r2 = r1.currentDrawOptotypeIndex
            switch(r2) {
                case 0: goto L_0x00d3;
                case 1: goto L_0x00cd;
                case 2: goto L_0x00c7;
                case 3: goto L_0x00c1;
                default: goto L_0x00bb;
            }
        L_0x00bb:
            android.graphics.Path r2 = r1.optotypeLeaSymbolApple(r3, r4)
            goto L_0x015e
        L_0x00c1:
            android.graphics.Path r2 = r1.optotypeLeaSymbolHouse(r3, r4)
            goto L_0x015e
        L_0x00c7:
            android.graphics.Path r2 = r1.optotypeLeaSymbolSquare(r3, r4)
            goto L_0x015e
        L_0x00cd:
            android.graphics.Path r2 = r1.optotypeLeaSymbolCircle(r3, r4)
            goto L_0x015e
        L_0x00d3:
            android.graphics.Path r2 = r1.optotypeLeaSymbolApple(r3, r4)
            goto L_0x015e
        L_0x00d9:
            android.graphics.Path r2 = r1.optotypeTumblingE(r3, r4)
            int r3 = r1.currentDrawRotationInDegrees
            float r3 = (float) r3
            java.lang.Float r3 = java.lang.Float.valueOf(r3)
            android.graphics.Path r2 = r1.rotatePath(r2, r3)
            goto L_0x015e
        L_0x00ea:
            android.graphics.Path r2 = r1.optotypeLandoltC(r3, r4)
            int r3 = r1.currentDrawRotationInDegrees
            float r3 = (float) r3
            java.lang.Float r3 = java.lang.Float.valueOf(r3)
            android.graphics.Path r2 = r1.rotatePath(r2, r3)
            goto L_0x015e
        L_0x00fb:
            int r2 = r1.currentDrawOptotypeIndex
            switch(r2) {
                case 0: goto L_0x012d;
                case 1: goto L_0x0128;
                case 2: goto L_0x0123;
                case 3: goto L_0x011e;
                case 4: goto L_0x0119;
                case 5: goto L_0x0114;
                case 6: goto L_0x010f;
                case 7: goto L_0x010a;
                case 8: goto L_0x0105;
                default: goto L_0x0100;
            }
        L_0x0100:
            android.graphics.Path r2 = r1.snellenOptotypeE(r3, r4)
            goto L_0x015e
        L_0x0105:
            android.graphics.Path r2 = r1.snellenOptotypeP(r3, r4)
            goto L_0x015e
        L_0x010a:
            android.graphics.Path r2 = r1.snellenOptotypeD(r3, r4)
            goto L_0x015e
        L_0x010f:
            android.graphics.Path r2 = r1.snellenOptotypeC(r3, r4)
            goto L_0x015e
        L_0x0114:
            android.graphics.Path r2 = r1.snellenOptotypeO(r3, r4)
            goto L_0x015e
        L_0x0119:
            android.graphics.Path r2 = r1.snellenOptotypeL(r3, r4)
            goto L_0x015e
        L_0x011e:
            android.graphics.Path r2 = r1.snellenOptotypeZ(r3, r4)
            goto L_0x015e
        L_0x0123:
            android.graphics.Path r2 = r1.snellenOptotypeF(r3, r4)
            goto L_0x015e
        L_0x0128:
            android.graphics.Path r2 = r1.snellenOptotypeT(r3, r4)
            goto L_0x015e
        L_0x012d:
            android.graphics.Path r2 = r1.snellenOptotypeE(r3, r4)
            goto L_0x015e
        L_0x0132:
            android.graphics.Path r2 = r1.snellenOptotypeP(r3, r4)
            goto L_0x015e
        L_0x0137:
            android.graphics.Path r2 = r1.snellenOptotypeD(r3, r4)
            goto L_0x015e
        L_0x013c:
            android.graphics.Path r2 = r1.snellenOptotypeC(r3, r4)
            goto L_0x015e
        L_0x0141:
            android.graphics.Path r2 = r1.snellenOptotypeO(r3, r4)
            goto L_0x015e
        L_0x0146:
            android.graphics.Path r2 = r1.snellenOptotypeL(r3, r4)
            goto L_0x015e
        L_0x014b:
            android.graphics.Path r2 = r1.snellenOptotypeZ(r3, r4)
            goto L_0x015e
        L_0x0150:
            android.graphics.Path r2 = r1.snellenOptotypeF(r3, r4)
            goto L_0x015e
        L_0x0155:
            android.graphics.Path r2 = r1.snellenOptotypeT(r3, r4)
            goto L_0x015e
        L_0x015a:
            android.graphics.Path r2 = r1.snellenOptotypeE(r3, r4)
        L_0x015e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ideas.joaomeneses.snellen.SnellenOptotypeView.drawOneOptotypeAtPosition(java.lang.String, int, int):android.graphics.Path");
    }

    private void reDrawOptotype() {
        this.isToDrawNewOptotypes = true;
        invalidate();
    }

    public void redrawOptotypeWithIncreasedAcuity() {
        if (!this.progressionToolbox.nextRound(Boolean.valueOf(this.isLineModeOn), this.canvasWidth, this.canvasHeigth).booleanValue()) {
            return;
        }
        if (validateOptotypeTotalPixelSize(((Optotype) this.progressionToolbox.getCurrentOptotypesListToDraw().get(0)).totalSizeInPixels)) {
            reDrawOptotype();
        } else {
            redrawOptotypeWithDecreasedAcuity();
        }
    }

    public void redrawOptotypeWithDecreasedAcuity() {
        if (!this.progressionToolbox.previousRound(Boolean.valueOf(this.isLineModeOn), this.canvasWidth, this.canvasHeigth).booleanValue()) {
            return;
        }
        if (validateOptotypeTotalPixelSize(((Optotype) this.progressionToolbox.getCurrentOptotypesListToDraw().get(0)).totalSizeInPixels)) {
            reDrawOptotype();
        } else {
            redrawOptotypeWithIncreasedAcuity();
        }
    }

    public void redrawOptotypeWithTheSameAcuity() {
        reDrawOptotype();
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void selectNewRandomOptotype() {
        /*
            r4 = this;
            java.lang.String r0 = r4.optotypeFormatToDraw
            int r1 = r0.hashCode()
            r2 = 5
            r3 = 4
            switch(r1) {
                case -2134689289: goto L_0x003e;
                case -1871417122: goto L_0x0034;
                case -1821882201: goto L_0x002a;
                case -715292570: goto L_0x0020;
                case -618563388: goto L_0x0016;
                case 769155484: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x0048
        L_0x000c:
            java.lang.String r1 = "LEA_Ref"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0048
            r0 = 3
            goto L_0x0049
        L_0x0016:
            java.lang.String r1 = "Landolt_C_Ref"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0048
            r0 = 1
            goto L_0x0049
        L_0x0020:
            java.lang.String r1 = "Tumbling_E_Ref"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0048
            r0 = 2
            goto L_0x0049
        L_0x002a:
            java.lang.String r1 = "Snellen_Ref"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0048
            r0 = 0
            goto L_0x0049
        L_0x0034:
            java.lang.String r1 = "Numbers_Ref"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0048
            r0 = 5
            goto L_0x0049
        L_0x003e:
            java.lang.String r1 = "Sloan_Ref"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0048
            r0 = 4
            goto L_0x0049
        L_0x0048:
            r0 = -1
        L_0x0049:
            r1 = 9
            switch(r0) {
                case 0: goto L_0x0079;
                case 1: goto L_0x0072;
                case 2: goto L_0x006b;
                case 3: goto L_0x0064;
                case 4: goto L_0x005c;
                case 5: goto L_0x0055;
                default: goto L_0x004e;
            }
        L_0x004e:
            int r0 = r4.generateRandomOptotypeIndex(r1)
            r4.currentDrawOptotypeIndex = r0
            goto L_0x007f
        L_0x0055:
            int r0 = r4.generateRandomOptotypeIndex(r2)
            r4.currentDrawOptotypeIndex = r0
            goto L_0x007f
        L_0x005c:
            r0 = 7
            int r0 = r4.generateRandomOptotypeIndex(r0)
            r4.currentDrawOptotypeIndex = r0
            goto L_0x007f
        L_0x0064:
            int r0 = r4.generateRandomOptotypeIndex(r3)
            r4.currentDrawOptotypeIndex = r0
            goto L_0x007f
        L_0x006b:
            int r0 = r4.generateRandomRotationAngleInDegrees()
            r4.currentDrawRotationInDegrees = r0
            goto L_0x007f
        L_0x0072:
            int r0 = r4.generateRandomRotationAngleInDegrees()
            r4.currentDrawRotationInDegrees = r0
            goto L_0x007f
        L_0x0079:
            int r0 = r4.generateRandomOptotypeIndex(r1)
            r4.currentDrawOptotypeIndex = r0
        L_0x007f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ideas.joaomeneses.snellen.SnellenOptotypeView.selectNewRandomOptotype():void");
    }

    private int generateRandomOptotypeIndex(int i) {
        int nextInt;
        Random random = new Random();
        do {
            nextInt = random.nextInt(i);
        } while (nextInt == this.currentDrawOptotypeIndex);
        return nextInt;
    }

    private int generateRandomRotationAngleInDegrees() {
        int i;
        Random random = new Random();
        int[] iArr = {0, 90, 180, 270};
        do {
            i = iArr[random.nextInt(iArr.length)];
        } while (i == this.currentDrawRotationInDegrees);
        return i;
    }

    private void setOptotypeAlpha(int i) {
        if (i > 255) {
            i = 255;
        } else if (i < 0) {
            i = 0;
        }
        this.currentAlphaToDraw = i;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int getMinimumGridUnit(java.lang.String r3) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = 5
            switch(r0) {
                case -2134689289: goto L_0x003b;
                case -1871417122: goto L_0x0031;
                case -1821882201: goto L_0x0027;
                case -715292570: goto L_0x001d;
                case -618563388: goto L_0x0013;
                case 769155484: goto L_0x0009;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x0045
        L_0x0009:
            java.lang.String r0 = "LEA_Ref"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0045
            r3 = 3
            goto L_0x0046
        L_0x0013:
            java.lang.String r0 = "Landolt_C_Ref"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0045
            r3 = 1
            goto L_0x0046
        L_0x001d:
            java.lang.String r0 = "Tumbling_E_Ref"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0045
            r3 = 2
            goto L_0x0046
        L_0x0027:
            java.lang.String r0 = "Snellen_Ref"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0045
            r3 = 0
            goto L_0x0046
        L_0x0031:
            java.lang.String r0 = "Numbers_Ref"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0045
            r3 = 5
            goto L_0x0046
        L_0x003b:
            java.lang.String r0 = "Sloan_Ref"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L_0x0045
            r3 = 4
            goto L_0x0046
        L_0x0045:
            r3 = -1
        L_0x0046:
            switch(r3) {
                case 0: goto L_0x004b;
                case 1: goto L_0x004b;
                case 2: goto L_0x004b;
                case 3: goto L_0x004a;
                case 4: goto L_0x004b;
                case 5: goto L_0x004b;
                default: goto L_0x0049;
            }
        L_0x0049:
            goto L_0x004b
        L_0x004a:
            r1 = 7
        L_0x004b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ideas.joaomeneses.snellen.SnellenOptotypeView.getMinimumGridUnit(java.lang.String):int");
    }

    private boolean validateOptotypeTotalPixelSize(int i) {
        int minimumGridUnit = getMinimumGridUnit(this.optotypeFormatToDraw);
        if (i % 5 != 0) {
            double d = (double) i;
            double d2 = (double) minimumGridUnit;
            Double.isNaN(d);
            Double.isNaN(d2);
            this.gridStep = (((int) Math.round(d / d2)) * minimumGridUnit) / minimumGridUnit;
            StringBuilder sb = new StringBuilder();
            sb.append("Displayed Optotype GridStep (px): ");
            sb.append(this.gridStep);
            sb.append(" x5 = Total: ");
            sb.append(getDisplayedOptotypeTotalPixelSize());
            sb.append(" Requested to draw: ");
            sb.append(i);
            Log.i("Optotype View", sb.toString());
        } else {
            this.gridStep = i / minimumGridUnit;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Displayed Optotype GridStep (px): ");
            sb2.append(this.gridStep);
            sb2.append(" x5 = Total: ");
            sb2.append(getDisplayedOptotypeTotalPixelSize());
            sb2.append(" Requested to draw: ");
            sb2.append(i);
            Log.i("Optotype View", sb2.toString());
        }
        if (this.gridStep <= 1) {
            Toast.makeText(getContext(), "Smallest Optotype Reached (display limits)", Toast.LENGTH_SHORT).show();
            return false;
        } else if (i < this.canvasHeigth && i < this.canvasWidth) {
            return true;
        } else {
            Toast.makeText(getContext(), "Biggest Optotype Reached (display limits)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public int getDisplayedOptotypeTotalPixelSize() {
        return getMinimumGridUnit(this.optotypeFormatToDraw) * this.gridStep;
    }

    private Path rotatePath(Path path, Float f) {
        Matrix matrix = new Matrix();
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        matrix.postRotate(f.floatValue(), rectF.centerX(), rectF.centerY());
        path.transform(matrix);
        return path;
    }

    private Path snellenOptotypeE(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        float f = (float) i3;
        float f2 = (float) i4;
        path.moveTo(f, f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) (this.gridStep + i4));
        path.lineTo((float) ((this.gridStep * 2) + i3), (float) (this.gridStep + i4));
        path.lineTo((float) ((this.gridStep * 2) + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 3) + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 3) + i3), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) ((this.gridStep * 2) + i3), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) ((this.gridStep * 2) + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) (this.gridStep + i3), (float) (this.gridStep + i4));
        path.lineTo(f, (float) (this.gridStep + i4));
        path.lineTo(f, f2);
        return path;
    }

    private Path snellenOptotypeT(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        float f = (float) i3;
        float f2 = (float) i4;
        path.moveTo(f, f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) (this.gridStep + i4));
        path.lineTo((float) ((this.gridStep * 3) + i3), (float) (this.gridStep + i4));
        path.lineTo((float) ((this.gridStep * 3) + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 2) + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 2) + i3), (float) (this.gridStep + i4));
        path.lineTo((float) (this.gridStep + i3), (float) (this.gridStep + i4));
        path.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo(f, (float) ((this.gridStep * 2) + i4));
        path.lineTo(f, f2);
        return path;
    }

    private Path snellenOptotypeF(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        float f = (float) i3;
        float f2 = (float) i4;
        path.moveTo(f, f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) (this.gridStep + i4));
        path.lineTo((float) ((this.gridStep * 2) + i3), (float) (this.gridStep + i4));
        path.lineTo((float) ((this.gridStep * 2) + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 3) + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 3) + i3), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) ((this.gridStep * 2) + i3), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) ((this.gridStep * 2) + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 3) + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 3) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) (this.gridStep + i3), (float) (this.gridStep + i4));
        path.lineTo(f, (float) (this.gridStep + i4));
        path.lineTo(f, f2);
        return path;
    }

    private Path snellenOptotypeZ(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        float f = (float) i3;
        float f2 = (float) i4;
        path.moveTo(f, f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) (this.gridStep + i4));
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        double d4 = d3 * 1.5d;
        double d5 = (double) i3;
        Double.isNaN(d5);
        path.lineTo((float) (d4 + d5), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, (float) ((this.gridStep * 4) + i4));
        double d6 = (double) this.gridStep;
        Double.isNaN(d6);
        double d7 = d6 * 3.5d;
        Double.isNaN(d5);
        path.lineTo((float) (d7 + d5), (float) (this.gridStep + i4));
        path.lineTo((float) (this.gridStep + i3), (float) (this.gridStep + i4));
        path.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo(f, (float) ((this.gridStep * 2) + i4));
        path.lineTo(f, f2);
        return path;
    }

    private Path snellenOptotypeL(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        float f = (float) i3;
        float f2 = (float) i4;
        path.moveTo(f, f2);
        path.lineTo((float) ((this.gridStep * 3) + i3), f2);
        path.lineTo((float) ((this.gridStep * 3) + i3), (float) (this.gridStep + i4));
        path.lineTo((float) ((this.gridStep * 2) + i3), (float) (this.gridStep + i4));
        path.lineTo((float) ((this.gridStep * 2) + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) (this.gridStep + i3), (float) (this.gridStep + i4));
        path.lineTo(f, (float) (this.gridStep + i4));
        path.lineTo(f, f2);
        return path;
    }

    private Path snellenOptotypeO(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        Path path2 = new Path();
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        double d4 = d3 * 2.5d;
        double d5 = (double) i3;
        Double.isNaN(d5);
        float f = (float) (d4 + d5);
        double d6 = (double) this.gridStep;
        Double.isNaN(d6);
        double d7 = d6 * 2.5d;
        double d8 = (double) i4;
        Double.isNaN(d8);
        float f2 = (float) (d7 + d8);
        double d9 = (double) this.gridStep;
        Double.isNaN(d9);
        path2.addCircle(f, f2, (float) (d9 * 2.5d), Path.Direction.CW);
        Path path3 = new Path();
        double d10 = (double) this.gridStep;
        Double.isNaN(d10);
        double d11 = d10 * 2.5d;
        Double.isNaN(d5);
        float f3 = (float) (d11 + d5);
        double d12 = (double) this.gridStep;
        Double.isNaN(d12);
        double d13 = d12 * 2.5d;
        Double.isNaN(d8);
        float f4 = (float) (d13 + d8);
        double d14 = (double) this.gridStep;
        Double.isNaN(d14);
        path3.addCircle(f3, f4, (float) (d14 * 1.5d), Path.Direction.CW);
        path.op(path2, path3, Path.Op.DIFFERENCE);
        return path;
    }

    private Path snellenOptotypeC(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        Path path2 = new Path();
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        double d4 = d3 * 2.5d;
        double d5 = (double) i3;
        Double.isNaN(d5);
        float f = (float) (d4 + d5);
        double d6 = (double) this.gridStep;
        Double.isNaN(d6);
        double d7 = d6 * 2.5d;
        double d8 = (double) i4;
        Double.isNaN(d8);
        float f2 = (float) (d7 + d8);
        double d9 = (double) this.gridStep;
        Double.isNaN(d9);
        path2.addCircle(f, f2, (float) (d9 * 2.5d), Path.Direction.CW);
        Path path3 = new Path();
        double d10 = (double) this.gridStep;
        Double.isNaN(d10);
        double d11 = d10 * 2.5d;
        Double.isNaN(d5);
        float f3 = (float) (d11 + d5);
        double d12 = (double) this.gridStep;
        Double.isNaN(d12);
        double d13 = d12 * 2.5d;
        Double.isNaN(d8);
        float f4 = (float) (d13 + d8);
        double d14 = (double) this.gridStep;
        Double.isNaN(d14);
        path3.addCircle(f3, f4, (float) (d14 * 1.5d), Path.Direction.CW);
        Path path4 = new Path();
        double d15 = (double) this.gridStep;
        Double.isNaN(d15);
        double d16 = d15 * 2.5d;
        Double.isNaN(d5);
        float f5 = (float) ((this.gridStep * 2) + i4);
        Path path5 = path4;
        path5.addRect((float) (d16 + d5), f5, (float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 3) + i4), Path.Direction.CW);
        Path path6 = new Path();
        path6.op(path2, path3, Path.Op.DIFFERENCE);
        path.op(path6, path4, Path.Op.DIFFERENCE);
        return path;
    }

    private Path snellenOptotypeD(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        Path path2 = new Path();
        float f = (float) i3;
        float f2 = (float) i4;
        path2.moveTo(f, f2);
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        path2.lineTo(((float) (d3 * 2.5d)) + f, f2);
        double d4 = (double) this.gridStep;
        Double.isNaN(d4);
        path2.lineTo(((float) (d4 * 2.5d)) + f, (float) (this.gridStep + i4));
        path2.lineTo((float) ((this.gridStep * 2) + i3), (float) (this.gridStep + i4));
        path2.lineTo((float) ((this.gridStep * 2) + i3), (float) ((this.gridStep * 4) + i4));
        double d5 = (double) this.gridStep;
        Double.isNaN(d5);
        path2.lineTo(((float) (d5 * 2.5d)) + f, (float) ((this.gridStep * 4) + i4));
        double d6 = (double) this.gridStep;
        Double.isNaN(d6);
        path2.lineTo(((float) (d6 * 2.5d)) + f, (float) ((this.gridStep * 5) + i4));
        path2.lineTo(f, (float) ((this.gridStep * 5) + i4));
        path2.lineTo(f, (float) ((this.gridStep * 4) + i4));
        path2.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 4) + i4));
        path2.lineTo((float) (this.gridStep + i3), (float) (this.gridStep + i4));
        path2.lineTo(f, (float) (this.gridStep + i4));
        path2.lineTo(f, f2);
        Path path3 = new Path();
        double d7 = (double) this.gridStep;
        Double.isNaN(d7);
        double d8 = d7 * 2.5d;
        double d9 = (double) i3;
        Double.isNaN(d9);
        float f3 = (float) (d8 + d9);
        double d10 = (double) this.gridStep;
        Double.isNaN(d10);
        double d11 = d10 * 2.5d;
        double d12 = (double) i4;
        Double.isNaN(d12);
        float f4 = (float) (d11 + d12);
        double d13 = (double) this.gridStep;
        Double.isNaN(d13);
        path3.addCircle(f3, f4, (float) (d13 * 2.5d), Path.Direction.CW);
        Path path4 = new Path();
        double d14 = (double) this.gridStep;
        Double.isNaN(d14);
        double d15 = d14 * 2.5d;
        Double.isNaN(d9);
        path4.addRect(f, f2, (float) (d15 + d9), (float) ((this.gridStep * 5) + i4), Path.Direction.CW);
        path3.op(path4, Path.Op.DIFFERENCE);
        Path path5 = new Path();
        double d16 = (double) this.gridStep;
        Double.isNaN(d16);
        double d17 = d16 * 2.5d;
        Double.isNaN(d9);
        float f5 = (float) (d17 + d9);
        double d18 = (double) this.gridStep;
        Double.isNaN(d18);
        double d19 = d18 * 2.5d;
        Double.isNaN(d12);
        float f6 = (float) (d19 + d12);
        double d20 = (double) this.gridStep;
        Double.isNaN(d20);
        path5.addCircle(f5, f6, (float) (d20 * 1.5d), Path.Direction.CW);
        path5.op(path4, Path.Op.DIFFERENCE);
        path.op(path2, path3, Path.Op.UNION);
        path.op(path5, Path.Op.DIFFERENCE);
        return path;
    }

    private Path snellenOptotypeP(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        Path path2 = new Path();
        float f = (float) i3;
        float f2 = (float) i4;
        path2.moveTo(f, f2);
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        path2.lineTo(((float) (d3 * 3.5d)) + f, f2);
        double d4 = (double) this.gridStep;
        Double.isNaN(d4);
        path2.lineTo(((float) (d4 * 3.5d)) + f, (float) (this.gridStep + i4));
        path2.lineTo((float) ((this.gridStep * 2) + i3), (float) (this.gridStep + i4));
        path2.lineTo((float) ((this.gridStep * 2) + i3), (float) ((this.gridStep * 2) + i4));
        double d5 = (double) this.gridStep;
        Double.isNaN(d5);
        path2.lineTo(((float) (d5 * 3.5d)) + f, (float) ((this.gridStep * 2) + i4));
        double d6 = (double) this.gridStep;
        Double.isNaN(d6);
        path2.lineTo(((float) (d6 * 3.5d)) + f, (float) ((this.gridStep * 3) + i4));
        path2.lineTo((float) ((this.gridStep * 2) + i3), (float) ((this.gridStep * 3) + i4));
        path2.lineTo((float) ((this.gridStep * 2) + i3), (float) ((this.gridStep * 4) + i4));
        path2.lineTo((float) ((this.gridStep * 3) + i3), (float) ((this.gridStep * 4) + i4));
        path2.lineTo((float) ((this.gridStep * 3) + i3), (float) ((this.gridStep * 5) + i4));
        path2.lineTo(f, (float) ((this.gridStep * 5) + i4));
        path2.lineTo(f, (float) ((this.gridStep * 4) + i4));
        path2.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 4) + i4));
        path2.lineTo((float) (this.gridStep + i3), (float) (this.gridStep + i4));
        path2.lineTo(f, (float) (this.gridStep + i4));
        path2.lineTo(f, f2);
        Path path3 = new Path();
        double d7 = (double) this.gridStep;
        Double.isNaN(d7);
        double d8 = d7 * 3.5d;
        double d9 = (double) i3;
        Double.isNaN(d9);
        float f3 = (float) (d8 + d9);
        double d10 = (double) this.gridStep;
        Double.isNaN(d10);
        double d11 = d10 * 1.5d;
        double d12 = (double) i4;
        Double.isNaN(d12);
        float f4 = (float) (d11 + d12);
        double d13 = (double) this.gridStep;
        Double.isNaN(d13);
        path3.addCircle(f3, f4, (float) (d13 * 1.5d), Path.Direction.CW);
        Path path4 = new Path();
        double d14 = (double) this.gridStep;
        Double.isNaN(d14);
        double d15 = d14 * 3.5d;
        Double.isNaN(d9);
        path4.addRect(f, f2, (float) (d15 + d9), (float) ((this.gridStep * 3) + i4), Path.Direction.CW);
        path3.op(path4, Path.Op.DIFFERENCE);
        Path path5 = new Path();
        double d16 = (double) this.gridStep;
        Double.isNaN(d16);
        double d17 = d16 * 3.5d;
        Double.isNaN(d9);
        float f5 = (float) (d17 + d9);
        double d18 = (double) this.gridStep;
        Double.isNaN(d18);
        double d19 = d18 * 1.5d;
        Double.isNaN(d12);
        float f6 = (float) (d19 + d12);
        double d20 = (double) this.gridStep;
        Double.isNaN(d20);
        path5.addCircle(f5, f6, (float) (d20 * 0.5d), Path.Direction.CW);
        path5.op(path4, Path.Op.DIFFERENCE);
        path.op(path2, path3, Path.Op.UNION);
        path.op(path5, Path.Op.DIFFERENCE);
        return path;
    }

    private Path optotypeTumblingE(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        float f = (float) i3;
        float f2 = (float) i4;
        path.moveTo(f, f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) (this.gridStep + i4));
        path.lineTo((float) (this.gridStep + i3), (float) (this.gridStep + i4));
        path.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, f2);
        return path;
    }

    private Path optotypeLandoltC(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        Path path2 = new Path();
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        double d4 = d3 * 2.5d;
        double d5 = (double) i3;
        Double.isNaN(d5);
        float f = (float) (d4 + d5);
        double d6 = (double) this.gridStep;
        Double.isNaN(d6);
        double d7 = d6 * 2.5d;
        double d8 = (double) i4;
        Double.isNaN(d8);
        float f2 = (float) (d7 + d8);
        double d9 = (double) this.gridStep;
        Double.isNaN(d9);
        path2.addCircle(f, f2, (float) (d9 * 2.5d), Path.Direction.CW);
        Path path3 = new Path();
        double d10 = (double) this.gridStep;
        Double.isNaN(d10);
        double d11 = d10 * 2.5d;
        Double.isNaN(d5);
        float f3 = (float) (d11 + d5);
        double d12 = (double) this.gridStep;
        Double.isNaN(d12);
        double d13 = d12 * 2.5d;
        Double.isNaN(d8);
        float f4 = (float) (d13 + d8);
        double d14 = (double) this.gridStep;
        Double.isNaN(d14);
        path3.addCircle(f3, f4, (float) (d14 * 1.5d), Path.Direction.CW);
        Path path4 = new Path();
        double d15 = (double) this.gridStep;
        Double.isNaN(d15);
        double d16 = d15 * 2.5d;
        Double.isNaN(d5);
        float f5 = (float) ((this.gridStep * 2) + i4);
        Path path5 = path4;
        path5.addRect((float) (d16 + d5), f5, (float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 3) + i4), Path.Direction.CW);
        Path path6 = new Path();
        path6.op(path2, path3, Path.Op.DIFFERENCE);
        path.op(path6, path4, Path.Op.DIFFERENCE);
        return path;
    }

    private Path optotypeLeaSymbolHouse(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 3.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 3.5d));
        Path path = new Path();
        path.reset();
        Path path2 = new Path();
        float f = (float) i3;
        float f2 = (float) i4;
        path2.moveTo((((float) this.gridStep) * 3.5f) + f, f2);
        path2.lineTo((((float) this.gridStep) * 8.2f) + f, (float) ((this.gridStep * 2) + i4));
        path2.lineTo((float) ((this.gridStep * 7) + i3), (float) ((this.gridStep * 2) + i4));
        path2.lineTo((float) ((this.gridStep * 7) + i3), (float) ((this.gridStep * 7) + i4));
        path2.lineTo(f, (float) ((this.gridStep * 7) + i4));
        path2.lineTo(f, (float) ((this.gridStep * 2) + i4));
        path2.lineTo(f - (((float) this.gridStep) * 1.2f), (float) ((this.gridStep * 2) + i4));
        path2.lineTo((((float) this.gridStep) * 3.5f) + f, f2);
        Path path3 = new Path();
        path3.moveTo((((float) this.gridStep) * 3.5f) + f, (float) (this.gridStep + i4));
        path3.lineTo((float) ((this.gridStep * 6) + i3), (((float) this.gridStep) * 2.0f) + f2);
        path3.lineTo((float) ((this.gridStep * 6) + i3), (float) ((this.gridStep * 6) + i4));
        path3.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 6) + i4));
        path3.lineTo((float) (this.gridStep + i3), (((float) this.gridStep) * 2.0f) + f2);
        path3.lineTo((((float) this.gridStep) * 3.5f) + f, (float) (this.gridStep + i4));
        path.op(path2, path3, Path.Op.DIFFERENCE);
        return path;
    }

    private Path optotypeLeaSymbolSquare(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 3.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 3.5d));
        Path path = new Path();
        path.reset();
        Path path2 = new Path();
        float f = (float) i3;
        float f2 = (float) i4;
        path2.moveTo(f, f2);
        path2.lineTo((float) ((this.gridStep * 7) + i3), f2);
        path2.lineTo((float) ((this.gridStep * 7) + i3), (float) ((this.gridStep * 7) + i4));
        path2.lineTo(f, (float) ((this.gridStep * 7) + i4));
        path2.lineTo(f, f2);
        Path path3 = new Path();
        path3.moveTo((float) (this.gridStep + i3), (float) (this.gridStep + i4));
        path3.lineTo((float) ((this.gridStep * 6) + i3), (float) (this.gridStep + i4));
        path3.lineTo((float) ((this.gridStep * 6) + i3), (float) ((this.gridStep * 6) + i4));
        path3.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 6) + i4));
        path3.lineTo((float) (this.gridStep + i3), (float) (this.gridStep + i4));
        path.op(path2, path3, Path.Op.DIFFERENCE);
        return path;
    }

    private Path optotypeLeaSymbolCircle(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 3.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 3.5d));
        Path path = new Path();
        path.reset();
        Path path2 = new Path();
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        double d4 = d3 * 3.5d;
        double d5 = (double) i3;
        Double.isNaN(d5);
        float f = (float) (d4 + d5);
        double d6 = (double) this.gridStep;
        Double.isNaN(d6);
        double d7 = d6 * 3.5d;
        double d8 = (double) i4;
        Double.isNaN(d8);
        float f2 = (float) (d7 + d8);
        double d9 = (double) this.gridStep;
        Double.isNaN(d9);
        path2.addCircle(f, f2, (float) (d9 * 3.5d), Path.Direction.CW);
        Path path3 = new Path();
        double d10 = (double) this.gridStep;
        Double.isNaN(d10);
        double d11 = d10 * 3.5d;
        Double.isNaN(d5);
        float f3 = (float) (d11 + d5);
        double d12 = (double) this.gridStep;
        Double.isNaN(d12);
        double d13 = d12 * 3.5d;
        Double.isNaN(d8);
        float f4 = (float) (d13 + d8);
        double d14 = (double) this.gridStep;
        Double.isNaN(d14);
        path3.addCircle(f3, f4, (float) (d14 * 2.5d), Path.Direction.CW);
        path.op(path2, path3, Path.Op.DIFFERENCE);
        return path;
    }

    private Path optotypeLeaSymbolApple(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 3.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 3.5d));
        Path path = new Path();
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        double d4 = d3 * 3.5d;
        double d5 = (double) i3;
        Double.isNaN(d5);
        path.moveTo((float) (d4 + d5), (float) (this.gridStep + i4));
        float f = (float) ((this.gridStep * 4) + i3);
        double d6 = (double) i4;
        double d7 = (double) this.gridStep;
        Double.isNaN(d7);
        double d8 = d7 * 0.5d;
        Double.isNaN(d6);
        float f2 = (float) (d6 - d8);
        float f3 = (float) ((this.gridStep * 7) + i3);
        double d9 = (double) this.gridStep;
        Double.isNaN(d9);
        double d10 = d9 * 0.5d;
        Double.isNaN(d6);
        Path path2 = path;
        double d11 = d6;
        path2.cubicTo(f, f2, f3, (float) (d6 - d10), (float) ((this.gridStep * 7) + i3), (float) ((this.gridStep * 2) + i4));
        float f4 = (float) ((this.gridStep * 7) + i3);
        float f5 = (float) ((this.gridStep * 4) + i4);
        float f6 = (float) ((this.gridStep * 5) + i3);
        double d12 = (double) this.gridStep;
        Double.isNaN(d12);
        double d13 = d12 * 6.5d;
        Double.isNaN(d11);
        float f7 = (float) (d13 + d11);
        float f8 = (float) ((this.gridStep * 5) + i3);
        double d14 = (double) this.gridStep;
        Double.isNaN(d14);
        double d15 = d14 * 6.5d;
        Double.isNaN(d11);
        path2.cubicTo(f4, f5, f6, f7, f8, (float) (d15 + d11));
        double d16 = (double) this.gridStep;
        Double.isNaN(d16);
        double d17 = d16 * 4.7d;
        Double.isNaN(d5);
        float f9 = (float) (d17 + d5);
        float f10 = (float) ((this.gridStep * 7) + i4);
        double d18 = (double) this.gridStep;
        Double.isNaN(d18);
        double d19 = d18 * 4.3d;
        Double.isNaN(d5);
        float f11 = (float) (d19 + d5);
        float f12 = (float) ((this.gridStep * 7) + i4);
        float f13 = (float) ((this.gridStep * 4) + i3);
        double d20 = (double) this.gridStep;
        Double.isNaN(d20);
        double d21 = d20 * 6.5d;
        Double.isNaN(d11);
        path2.cubicTo(f9, f10, f11, f12, f13, (float) (d21 + d11));
        double d22 = (double) this.gridStep;
        Double.isNaN(d22);
        double d23 = d22 * 3.7d;
        Double.isNaN(d5);
        float f14 = (float) (d23 + d5);
        double d24 = (double) this.gridStep;
        Double.isNaN(d24);
        double d25 = d24 * 6.1d;
        Double.isNaN(d11);
        float f15 = (float) (d25 + d11);
        double d26 = (double) this.gridStep;
        Double.isNaN(d26);
        double d27 = d26 * 3.3d;
        Double.isNaN(d5);
        float f16 = (float) (d27 + d5);
        double d28 = (double) this.gridStep;
        Double.isNaN(d28);
        double d29 = d28 * 6.1d;
        Double.isNaN(d11);
        float f17 = (float) (d29 + d11);
        float f18 = (float) ((this.gridStep * 3) + i3);
        double d30 = (double) this.gridStep;
        Double.isNaN(d30);
        double d31 = d30 * 6.5d;
        Double.isNaN(d11);
        path.cubicTo(f14, f15, f16, f17, f18, (float) (d31 + d11));
        double d32 = (double) this.gridStep;
        Double.isNaN(d32);
        double d33 = d32 * 2.7d;
        Double.isNaN(d5);
        float f19 = (float) (d33 + d5);
        float f20 = (float) ((this.gridStep * 7) + i4);
        double d34 = (double) this.gridStep;
        Double.isNaN(d34);
        double d35 = d34 * 2.3d;
        Double.isNaN(d5);
        float f21 = (float) (d35 + d5);
        float f22 = (float) ((this.gridStep * 7) + i4);
        float f23 = (float) ((this.gridStep * 2) + i3);
        double d36 = (double) this.gridStep;
        Double.isNaN(d36);
        double d37 = d36 * 6.5d;
        Double.isNaN(d11);
        Path path3 = path;
        path3.cubicTo(f19, f20, f21, f22, f23, (float) (d37 + d11));
        float f24 = (float) ((this.gridStep * 2) + i3);
        double d38 = (double) this.gridStep;
        Double.isNaN(d38);
        double d39 = d38 * 6.5d;
        Double.isNaN(d11);
        float f25 = (float) i3;
        path3.cubicTo(f24, (float) (d39 + d11), f25, (float) ((this.gridStep * 4) + i4), f25, (float) ((this.gridStep * 2) + i4));
        double d40 = (double) this.gridStep;
        Double.isNaN(d40);
        double d41 = d40 * 0.2d;
        Double.isNaN(d5);
        float f26 = (float) (d41 + d5);
        float f27 = (float) i4;
        double d42 = (double) this.gridStep;
        Double.isNaN(d42);
        double d43 = d42 * 2.7d;
        Double.isNaN(d5);
        float f28 = (float) (d43 + d5);
        double d44 = (double) this.gridStep;
        Double.isNaN(d44);
        double d45 = d44 * 3.5d;
        Double.isNaN(d5);
        path.cubicTo(f26, f27, f28, f27, (float) (d45 + d5), (float) (this.gridStep + i4));
        Path path4 = new Path();
        double d46 = (double) this.gridStep;
        Double.isNaN(d46);
        double d47 = d46 * 3.5d;
        Double.isNaN(d5);
        float f29 = (float) (d47 + d5);
        double d48 = (double) this.gridStep;
        Double.isNaN(d48);
        double d49 = d48 * 2.5d;
        Double.isNaN(d11);
        path4.moveTo(f29, (float) (d49 + d11));
        double d50 = (double) this.gridStep;
        Double.isNaN(d50);
        double d51 = d50 * 4.3d;
        Double.isNaN(d5);
        float f30 = (float) (d51 + d5);
        double d52 = (double) this.gridStep;
        Double.isNaN(d52);
        double d53 = d52 * 0.2d;
        Double.isNaN(d11);
        float f31 = (float) (d53 + d11);
        float f32 = (float) ((this.gridStep * 6) + i3);
        double d54 = (double) this.gridStep;
        Double.isNaN(d54);
        double d55 = d54 * 0.7d;
        Double.isNaN(d11);
        Path path5 = path4;
        path5.cubicTo(f30, f31, f32, (float) (d55 + d11), (float) ((this.gridStep * 6) + i3), (float) ((this.gridStep * 2) + i4));
        float f33 = (float) ((this.gridStep * 6) + i3);
        float f34 = (float) ((this.gridStep * 3) + i4);
        double d56 = (double) this.gridStep;
        Double.isNaN(d56);
        double d57 = d56 * 5.5d;
        Double.isNaN(d5);
        float f35 = (float) (d57 + d5);
        double d58 = (double) this.gridStep;
        Double.isNaN(d58);
        double d59 = d58 * 4.5d;
        Double.isNaN(d11);
        float f36 = (float) (d59 + d11);
        double d60 = (double) this.gridStep;
        Double.isNaN(d60);
        double d61 = d60 * 4.7d;
        Double.isNaN(d5);
        float f37 = (float) (d61 + d5);
        double d62 = (double) this.gridStep;
        Double.isNaN(d62);
        double d63 = d62 * 5.5d;
        Double.isNaN(d11);
        path5.cubicTo(f33, f34, f35, f36, f37, (float) (d63 + d11));
        double d64 = (double) this.gridStep;
        Double.isNaN(d64);
        double d65 = d64 * 4.5d;
        Double.isNaN(d5);
        float f38 = (float) (d65 + d5);
        double d66 = (double) this.gridStep;
        Double.isNaN(d66);
        double d67 = d66 * 5.8d;
        Double.isNaN(d11);
        float f39 = (float) (d67 + d11);
        double d68 = (double) this.gridStep;
        Double.isNaN(d68);
        double d69 = d68 * 4.1d;
        Double.isNaN(d5);
        float f40 = (float) (d69 + d5);
        double d70 = (double) this.gridStep;
        Double.isNaN(d70);
        double d71 = d70 * 5.8d;
        Double.isNaN(d11);
        float f41 = (float) (d71 + d11);
        double d72 = (double) this.gridStep;
        Double.isNaN(d72);
        double d73 = d72 * 3.9d;
        Double.isNaN(d5);
        float f42 = (float) (d73 + d5);
        double d74 = (double) this.gridStep;
        Double.isNaN(d74);
        double d75 = d74 * 5.5d;
        Double.isNaN(d11);
        path5.cubicTo(f38, f39, f40, f41, f42, (float) (d75 + d11));
        double d76 = (double) this.gridStep;
        Double.isNaN(d76);
        double d77 = d76 * 3.7d;
        Double.isNaN(d5);
        float f43 = (float) (d77 + d5);
        double d78 = (double) this.gridStep;
        Double.isNaN(d78);
        double d79 = d78 * 5.2d;
        Double.isNaN(d11);
        float f44 = (float) (d79 + d11);
        double d80 = (double) this.gridStep;
        Double.isNaN(d80);
        double d81 = d80 * 3.3d;
        Double.isNaN(d5);
        float f45 = (float) (d81 + d5);
        double d82 = (double) this.gridStep;
        Double.isNaN(d82);
        double d83 = d82 * 5.2d;
        Double.isNaN(d11);
        float f46 = (float) (d83 + d11);
        double d84 = (double) this.gridStep;
        Double.isNaN(d84);
        double d85 = d84 * 3.1d;
        Double.isNaN(d5);
        float f47 = (float) (d85 + d5);
        double d86 = (double) this.gridStep;
        Double.isNaN(d86);
        double d87 = d86 * 5.5d;
        Double.isNaN(d11);
        path5.cubicTo(f43, f44, f45, f46, f47, (float) (d87 + d11));
        double d88 = (double) this.gridStep;
        Double.isNaN(d88);
        double d89 = d88 * 2.9d;
        Double.isNaN(d5);
        float f48 = (float) (d89 + d5);
        double d90 = (double) this.gridStep;
        Double.isNaN(d90);
        double d91 = d90 * 5.8d;
        Double.isNaN(d11);
        float f49 = (float) (d91 + d11);
        double d92 = (double) this.gridStep;
        Double.isNaN(d92);
        double d93 = d92 * 2.5d;
        Double.isNaN(d5);
        float f50 = (float) (d93 + d5);
        double d94 = (double) this.gridStep;
        Double.isNaN(d94);
        double d95 = d94 * 5.8d;
        Double.isNaN(d11);
        float f51 = (float) (d95 + d11);
        double d96 = (double) this.gridStep;
        Double.isNaN(d96);
        double d97 = d96 * 2.3d;
        Double.isNaN(d5);
        float f52 = (float) (d97 + d5);
        double d98 = (double) this.gridStep;
        Double.isNaN(d98);
        double d99 = d98 * 5.5d;
        Double.isNaN(d11);
        path5.cubicTo(f48, f49, f50, f51, f52, (float) (d99 + d11));
        double d100 = (double) this.gridStep;
        Double.isNaN(d100);
        double d101 = d100 * 2.3d;
        Double.isNaN(d5);
        float f53 = (float) (d101 + d5);
        double d102 = (double) this.gridStep;
        Double.isNaN(d102);
        double d103 = d102 * 5.5d;
        Double.isNaN(d11);
        float f54 = (float) (d103 + d11);
        float f55 = (float) (this.gridStep + i3);
        float f56 = (float) ((this.gridStep * 4) + i4);
        float f57 = (float) (this.gridStep + i3);
        double d104 = (double) this.gridStep;
        Double.isNaN(d104);
        double d105 = d104 * 2.5d;
        Double.isNaN(d11);
        path5.cubicTo(f53, f54, f55, f56, f57, (float) (d105 + d11));
        float f58 = (float) (this.gridStep + i3);
        double d106 = (double) this.gridStep;
        Double.isNaN(d106);
        double d107 = d106 * 0.8d;
        Double.isNaN(d11);
        float f59 = (float) (d107 + d11);
        float f60 = (float) ((this.gridStep * 3) + i3);
        double d108 = (double) this.gridStep;
        Double.isNaN(d108);
        double d109 = d108 * 0.9d;
        Double.isNaN(d11);
        float f61 = (float) (d109 + d11);
        double d110 = (double) this.gridStep;
        Double.isNaN(d110);
        double d111 = d110 * 3.5d;
        Double.isNaN(d5);
        float f62 = (float) (d111 + d5);
        double d112 = (double) this.gridStep;
        Double.isNaN(d112);
        double d113 = d112 * 2.5d;
        Double.isNaN(d11);
        path5.cubicTo(f58, f59, f60, f61, f62, (float) (d113 + d11));
        Path path6 = new Path();
        path6.reset();
        path6.op(path, path4, Path.Op.DIFFERENCE);
        return path6;
    }

    private Path optotypeSloanC(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        Path path2 = new Path();
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        double d4 = d3 * 2.5d;
        double d5 = (double) i3;
        Double.isNaN(d5);
        float f = (float) (d4 + d5);
        double d6 = (double) this.gridStep;
        Double.isNaN(d6);
        double d7 = d6 * 2.5d;
        double d8 = (double) i4;
        Double.isNaN(d8);
        float f2 = (float) (d7 + d8);
        double d9 = (double) this.gridStep;
        Double.isNaN(d9);
        path2.addCircle(f, f2, (float) (d9 * 2.5d), Path.Direction.CW);
        Path path3 = new Path();
        double d10 = (double) this.gridStep;
        Double.isNaN(d10);
        double d11 = d10 * 2.5d;
        Double.isNaN(d5);
        float f3 = (float) (d11 + d5);
        double d12 = (double) this.gridStep;
        Double.isNaN(d12);
        double d13 = d12 * 2.5d;
        Double.isNaN(d8);
        float f4 = (float) (d13 + d8);
        double d14 = (double) this.gridStep;
        Double.isNaN(d14);
        path3.addCircle(f3, f4, (float) (d14 * 1.5d), Path.Direction.CW);
        Path path4 = new Path();
        double d15 = (double) this.gridStep;
        Double.isNaN(d15);
        double d16 = d15 * 2.5d;
        Double.isNaN(d5);
        float f5 = (float) ((this.gridStep * 2) + i4);
        Path path5 = path4;
        path5.addRect((float) (d16 + d5), f5, (float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 3) + i4), Path.Direction.CW);
        Path path6 = new Path();
        path6.op(path2, path3, Path.Op.DIFFERENCE);
        path.op(path6, path4, Path.Op.DIFFERENCE);
        return path;
    }

    private Path optotypeSloanO(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        Path path2 = new Path();
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        double d4 = d3 * 2.5d;
        double d5 = (double) i3;
        Double.isNaN(d5);
        float f = (float) (d4 + d5);
        double d6 = (double) this.gridStep;
        Double.isNaN(d6);
        double d7 = d6 * 2.5d;
        double d8 = (double) i4;
        Double.isNaN(d8);
        float f2 = (float) (d7 + d8);
        double d9 = (double) this.gridStep;
        Double.isNaN(d9);
        path2.addCircle(f, f2, (float) (d9 * 2.5d), Path.Direction.CW);
        Path path3 = new Path();
        double d10 = (double) this.gridStep;
        Double.isNaN(d10);
        double d11 = d10 * 2.5d;
        Double.isNaN(d5);
        float f3 = (float) (d11 + d5);
        double d12 = (double) this.gridStep;
        Double.isNaN(d12);
        double d13 = d12 * 2.5d;
        Double.isNaN(d8);
        float f4 = (float) (d13 + d8);
        double d14 = (double) this.gridStep;
        Double.isNaN(d14);
        path3.addCircle(f3, f4, (float) (d14 * 1.5d), Path.Direction.CW);
        path.op(path2, path3, Path.Op.DIFFERENCE);
        return path;
    }

    private Path optotypeSloanH(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        float f = (float) i3;
        float f2 = (float) i4;
        path.moveTo(f, f2);
        path.lineTo((float) (this.gridStep + i3), f2);
        path.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, f2);
        return path;
    }

    private Path optotypeSloanV(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        float f = (float) i3;
        float f2 = (float) i4;
        path.moveTo(f, f2);
        path.lineTo((float) (this.gridStep + i3), f2);
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        double d4 = d3 * 2.5d;
        double d5 = (double) i3;
        Double.isNaN(d5);
        path.lineTo((float) (d4 + d5), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), f2);
        path.lineTo((float) ((this.gridStep * 3) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo((float) ((this.gridStep * 2) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, f2);
        return path;
    }

    private Path optotypeSloanN(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        float f = (float) i3;
        float f2 = (float) i4;
        path.moveTo(f, f2);
        path.lineTo((float) (this.gridStep + i3), f2);
        float f3 = (float) ((this.gridStep * 4) + i3);
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        double d4 = d3 * 3.5d;
        double d5 = (double) i4;
        Double.isNaN(d5);
        path.lineTo(f3, (float) (d4 + d5));
        path.lineTo((float) ((this.gridStep * 4) + i3), f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 5) + i4));
        float f4 = (float) (this.gridStep + i3);
        double d6 = (double) this.gridStep;
        Double.isNaN(d6);
        double d7 = d6 * 1.5d;
        Double.isNaN(d5);
        path.lineTo(f4, (float) (d7 + d5));
        path.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, f2);
        return path;
    }

    private Path optotypeSloanZ(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        float f = (float) i3;
        float f2 = (float) i4;
        path.moveTo(f, f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), f2);
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) (this.gridStep + i4));
        path.lineTo((((float) this.gridStep) * 1.5f) + f, (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, (float) ((this.gridStep * 4) + i4));
        path.lineTo((((float) this.gridStep) * 3.5f) + f, (float) (this.gridStep + i4));
        path.lineTo(f, (float) (this.gridStep + i4));
        path.lineTo(f, f2);
        return path;
    }

    private Path optotypeSloanD(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        Path path2 = new Path();
        float f = (float) i3;
        float f2 = (float) i4;
        path2.moveTo(f, f2);
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        path2.lineTo(((float) (d3 * 2.5d)) + f, f2);
        double d4 = (double) this.gridStep;
        Double.isNaN(d4);
        path2.lineTo(((float) (d4 * 2.5d)) + f, (float) (this.gridStep + i4));
        path2.lineTo((float) (this.gridStep + i3), (float) (this.gridStep + i4));
        path2.lineTo((float) (this.gridStep + i3), (float) ((this.gridStep * 4) + i4));
        double d5 = (double) this.gridStep;
        Double.isNaN(d5);
        path2.lineTo(((float) (d5 * 2.5d)) + f, (float) ((this.gridStep * 4) + i4));
        double d6 = (double) this.gridStep;
        Double.isNaN(d6);
        path2.lineTo(((float) (d6 * 2.5d)) + f, (float) ((this.gridStep * 5) + i4));
        path2.lineTo(f, (float) ((this.gridStep * 5) + i4));
        path2.lineTo(f, f2);
        Path path3 = new Path();
        double d7 = (double) this.gridStep;
        Double.isNaN(d7);
        double d8 = d7 * 2.5d;
        double d9 = (double) i3;
        Double.isNaN(d9);
        float f3 = (float) (d8 + d9);
        double d10 = (double) this.gridStep;
        Double.isNaN(d10);
        double d11 = d10 * 2.5d;
        double d12 = (double) i4;
        Double.isNaN(d12);
        float f4 = (float) (d11 + d12);
        double d13 = (double) this.gridStep;
        Double.isNaN(d13);
        path3.addCircle(f3, f4, (float) (d13 * 2.5d), Path.Direction.CW);
        Path path4 = new Path();
        double d14 = (double) this.gridStep;
        Double.isNaN(d14);
        double d15 = d14 * 2.5d;
        Double.isNaN(d9);
        path4.addRect(f, f2, (float) (d15 + d9), (float) ((this.gridStep * 5) + i4), Path.Direction.CW);
        path3.op(path4, Path.Op.DIFFERENCE);
        Path path5 = new Path();
        double d16 = (double) this.gridStep;
        Double.isNaN(d16);
        double d17 = d16 * 2.5d;
        Double.isNaN(d9);
        float f5 = (float) (d17 + d9);
        double d18 = (double) this.gridStep;
        Double.isNaN(d18);
        double d19 = d18 * 2.5d;
        Double.isNaN(d12);
        float f6 = (float) (d19 + d12);
        double d20 = (double) this.gridStep;
        Double.isNaN(d20);
        path5.addCircle(f5, f6, (float) (d20 * 1.5d), Path.Direction.CW);
        path5.op(path4, Path.Op.DIFFERENCE);
        path.op(path2, path3, Path.Op.UNION);
        path.op(path5, Path.Op.DIFFERENCE);
        return path;
    }

    private Path optotypeNumber9(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        path.moveTo((float) i3, (float) (this.gridStep + i4));
        float f = (float) ((this.gridStep * 0) + i3);
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        double d4 = d3 * 0.3d;
        double d5 = (double) i4;
        Double.isNaN(d5);
        float f2 = (float) (d4 + d5);
        double d6 = (double) this.gridStep;
        Double.isNaN(d6);
        double d7 = d6 * 0.3d;
        double d8 = (double) i3;
        Double.isNaN(d8);
        double d9 = d8;
        path.cubicTo(f, f2, (float) (d7 + d8), (float) ((this.gridStep * 0) + i4), (float) ((this.gridStep * 1) + i3), (float) i4);
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 0) + i4));
        double d10 = (double) this.gridStep;
        Double.isNaN(d10);
        double d11 = d10 * 4.7d;
        Double.isNaN(d9);
        float f3 = (float) (d11 + d9);
        float f4 = (float) ((this.gridStep * 0) + i4);
        float f5 = (float) ((this.gridStep * 5) + i3);
        double d12 = (double) this.gridStep;
        Double.isNaN(d12);
        double d13 = d12 * 0.3d;
        Double.isNaN(d5);
        path.cubicTo(f3, f4, f5, (float) (d13 + d5), (float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 1) + i4));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 4) + i4));
        float f6 = (float) ((this.gridStep * 5) + i3);
        double d14 = (double) this.gridStep;
        Double.isNaN(d14);
        double d15 = d14 * 4.7d;
        Double.isNaN(d5);
        float f7 = (float) (d15 + d5);
        double d16 = (double) this.gridStep;
        Double.isNaN(d16);
        double d17 = d16 * 4.7d;
        Double.isNaN(d9);
        path.cubicTo(f6, f7, (float) (d17 + d9), (float) ((this.gridStep * 5) + i4), (float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo((float) ((this.gridStep * 1) + i3), (float) ((this.gridStep * 5) + i4));
        double d18 = (double) this.gridStep;
        Double.isNaN(d18);
        double d19 = d18 * 0.3d;
        Double.isNaN(d9);
        float f8 = (float) (d19 + d9);
        float f9 = (float) ((this.gridStep * 5) + i4);
        float f10 = (float) ((this.gridStep * 0) + i3);
        double d20 = (double) this.gridStep;
        Double.isNaN(d20);
        double d21 = d20 * 4.7d;
        Double.isNaN(d5);
        path.cubicTo(f8, f9, f10, (float) (d21 + d5), (float) ((this.gridStep * 0) + i3), (float) ((this.gridStep * 4) + i4));
        double d22 = (double) this.gridStep;
        Double.isNaN(d22);
        double d23 = d22 * 3.5d;
        Double.isNaN(d9);
        path.lineTo((float) (d23 + d9), (float) ((this.gridStep * 4) + i4));
        double d24 = (double) this.gridStep;
        Double.isNaN(d24);
        double d25 = d24 * 3.9d;
        Double.isNaN(d9);
        float f11 = (float) (d25 + d9);
        float f12 = (float) ((this.gridStep * 4) + i4);
        float f13 = (float) ((this.gridStep * 4) + i3);
        double d26 = (double) this.gridStep;
        Double.isNaN(d26);
        double d27 = d26 * 3.9d;
        Double.isNaN(d5);
        float f14 = (float) (d27 + d5);
        float f15 = (float) ((this.gridStep * 4) + i3);
        double d28 = (double) this.gridStep;
        Double.isNaN(d28);
        double d29 = d28 * 3.5d;
        Double.isNaN(d5);
        path.cubicTo(f11, f12, f13, f14, f15, (float) (d29 + d5));
        float f16 = (float) ((this.gridStep * 4) + i3);
        double d30 = (double) this.gridStep;
        Double.isNaN(d30);
        double d31 = d30 * 3.1d;
        Double.isNaN(d5);
        float f17 = (float) (d31 + d5);
        double d32 = (double) this.gridStep;
        Double.isNaN(d32);
        double d33 = d32 * 3.9d;
        Double.isNaN(d9);
        float f18 = (float) (d33 + d9);
        float f19 = (float) ((this.gridStep * 3) + i4);
        double d34 = (double) this.gridStep;
        Double.isNaN(d34);
        double d35 = d34 * 3.5d;
        Double.isNaN(d9);
        path.cubicTo(f16, f17, f18, f19, (float) (d35 + d9), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) ((this.gridStep * 1) + i3), (float) ((this.gridStep * 3) + i4));
        double d36 = (double) this.gridStep;
        Double.isNaN(d36);
        double d37 = d36 * 0.3d;
        Double.isNaN(d9);
        float f20 = (float) (d37 + d9);
        float f21 = (float) ((this.gridStep * 3) + i4);
        float f22 = (float) ((this.gridStep * 0) + i3);
        double d38 = (double) this.gridStep;
        Double.isNaN(d38);
        double d39 = d38 * 2.7d;
        Double.isNaN(d5);
        path.cubicTo(f20, f21, f22, (float) (d39 + d5), (float) ((this.gridStep * 0) + i3), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 0) + i3), (float) ((this.gridStep * 1) + i4));
        Path path2 = new Path();
        path2.moveTo((float) ((this.gridStep * 2) + i3), (float) ((this.gridStep * 1) + i4));
        double d40 = (double) this.gridStep;
        Double.isNaN(d40);
        double d41 = d40 * 3.5d;
        Double.isNaN(d9);
        path2.lineTo((float) (d41 + d9), (float) ((this.gridStep * 1) + i4));
        double d42 = (double) this.gridStep;
        Double.isNaN(d42);
        double d43 = d42 * 3.9d;
        Double.isNaN(d9);
        float f23 = (float) (d43 + d9);
        float f24 = (float) ((this.gridStep * 1) + i4);
        float f25 = (float) ((this.gridStep * 4) + i3);
        double d44 = (double) this.gridStep;
        Double.isNaN(d44);
        double d45 = d44 * 1.1d;
        Double.isNaN(d5);
        float f26 = (float) (d45 + d5);
        float f27 = (float) ((this.gridStep * 4) + i3);
        double d46 = (double) this.gridStep;
        Double.isNaN(d46);
        double d47 = d46 * 1.5d;
        Double.isNaN(d5);
        Path path3 = path2;
        path3.cubicTo(f23, f24, f25, f26, f27, (float) (d47 + d5));
        float f28 = (float) ((this.gridStep * 4) + i3);
        double d48 = (double) this.gridStep;
        Double.isNaN(d48);
        double d49 = d48 * 1.9d;
        Double.isNaN(d5);
        float f29 = (float) (d49 + d5);
        double d50 = (double) this.gridStep;
        Double.isNaN(d50);
        double d51 = d50 * 3.9d;
        Double.isNaN(d9);
        float f30 = (float) (d51 + d9);
        float f31 = (float) ((this.gridStep * 2) + i4);
        double d52 = (double) this.gridStep;
        Double.isNaN(d52);
        double d53 = d52 * 3.5d;
        Double.isNaN(d9);
        path3.cubicTo(f28, f29, f30, f31, (float) (d53 + d9), (float) ((this.gridStep * 2) + i4));
        double d54 = (double) this.gridStep;
        Double.isNaN(d54);
        double d55 = d54 * 1.5d;
        Double.isNaN(d9);
        path2.lineTo((float) (d55 + d9), (float) ((this.gridStep * 2) + i4));
        double d56 = (double) this.gridStep;
        Double.isNaN(d56);
        double d57 = d56 * 1.1d;
        Double.isNaN(d9);
        float f32 = (float) (d57 + d9);
        float f33 = (float) ((this.gridStep * 2) + i4);
        float f34 = (float) ((this.gridStep * 1) + i3);
        double d58 = (double) this.gridStep;
        Double.isNaN(d58);
        double d59 = d58 * 1.9d;
        Double.isNaN(d5);
        float f35 = (float) (d59 + d5);
        float f36 = (float) ((this.gridStep * 1) + i3);
        double d60 = (double) this.gridStep;
        Double.isNaN(d60);
        double d61 = d60 * 1.5d;
        Double.isNaN(d5);
        path3.cubicTo(f32, f33, f34, f35, f36, (float) (d61 + d5));
        float f37 = (float) ((this.gridStep * 1) + i3);
        double d62 = (double) this.gridStep;
        Double.isNaN(d62);
        double d63 = d62 * 1.1d;
        Double.isNaN(d5);
        float f38 = (float) (d63 + d5);
        double d64 = (double) this.gridStep;
        Double.isNaN(d64);
        double d65 = d64 * 1.1d;
        Double.isNaN(d9);
        float f39 = (float) (d65 + d9);
        float f40 = (float) ((this.gridStep * 1) + i4);
        double d66 = (double) this.gridStep;
        Double.isNaN(d66);
        double d67 = d66 * 1.5d;
        Double.isNaN(d9);
        path3.cubicTo(f37, f38, f39, f40, (float) (d67 + d9), (float) ((this.gridStep * 1) + i4));
        double d68 = (double) this.gridStep;
        Double.isNaN(d68);
        double d69 = d68 * 3.5d;
        Double.isNaN(d9);
        path2.lineTo((float) (d69 + d9), (float) ((this.gridStep * 1) + i4));
        Path path4 = new Path();
        path4.reset();
        path4.op(path, path2, Path.Op.DIFFERENCE);
        return path4;
    }

    private Path optotypeNumber6(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        path.moveTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 1) + i4));
        float f = (float) ((this.gridStep * 5) + i3);
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        double d4 = d3 * 0.7d;
        double d5 = (double) i4;
        Double.isNaN(d5);
        float f2 = (float) (d4 + d5);
        double d6 = (double) this.gridStep;
        Double.isNaN(d6);
        double d7 = d6 * 4.7d;
        double d8 = (double) i3;
        Double.isNaN(d8);
        double d9 = d8;
        path.cubicTo(f, f2, (float) (d7 + d8), (float) ((this.gridStep * 0) + i4), (float) ((this.gridStep * 4) + i3), (float) i4);
        path.lineTo((float) ((this.gridStep * 1) + i3), (float) ((this.gridStep * 0) + i4));
        double d10 = (double) this.gridStep;
        Double.isNaN(d10);
        double d11 = d10 * 0.3d;
        Double.isNaN(d9);
        float f3 = (float) (d11 + d9);
        float f4 = (float) ((this.gridStep * 0) + i4);
        float f5 = (float) ((this.gridStep * 0) + i3);
        double d12 = (double) this.gridStep;
        Double.isNaN(d12);
        double d13 = d12 * 0.3d;
        Double.isNaN(d5);
        path.cubicTo(f3, f4, f5, (float) (d13 + d5), (float) ((this.gridStep * 0) + i3), (float) ((this.gridStep * 1) + i4));
        path.lineTo((float) ((this.gridStep * 0) + i3), (float) ((this.gridStep * 4) + i4));
        float f6 = (float) ((this.gridStep * 0) + i3);
        double d14 = (double) this.gridStep;
        Double.isNaN(d14);
        double d15 = d14 * 4.7d;
        Double.isNaN(d5);
        float f7 = (float) (d15 + d5);
        double d16 = (double) this.gridStep;
        Double.isNaN(d16);
        double d17 = d16 * 0.3d;
        Double.isNaN(d9);
        path.cubicTo(f6, f7, (float) (d17 + d9), (float) ((this.gridStep * 5) + i4), (float) ((this.gridStep * 1) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 5) + i4));
        double d18 = (double) this.gridStep;
        Double.isNaN(d18);
        double d19 = d18 * 4.7d;
        Double.isNaN(d9);
        float f8 = (float) (d19 + d9);
        float f9 = (float) ((this.gridStep * 5) + i4);
        float f10 = (float) ((this.gridStep * 5) + i3);
        double d20 = (double) this.gridStep;
        Double.isNaN(d20);
        double d21 = d20 * 4.7d;
        Double.isNaN(d5);
        path.cubicTo(f8, f9, f10, (float) (d21 + d5), (float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 4) + i4));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 3) + i4));
        float f11 = (float) ((this.gridStep * 5) + i3);
        double d22 = (double) this.gridStep;
        Double.isNaN(d22);
        double d23 = d22 * 2.3d;
        Double.isNaN(d5);
        float f12 = (float) (d23 + d5);
        double d24 = (double) this.gridStep;
        Double.isNaN(d24);
        double d25 = d24 * 4.7d;
        Double.isNaN(d9);
        path.cubicTo(f11, f12, (float) (d25 + d9), (float) ((this.gridStep * 2) + i4), (float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 2) + i4));
        double d26 = (double) this.gridStep;
        Double.isNaN(d26);
        double d27 = d26 * 1.5d;
        Double.isNaN(d9);
        path.lineTo((float) (d27 + d9), (float) ((this.gridStep * 2) + i4));
        double d28 = (double) this.gridStep;
        Double.isNaN(d28);
        double d29 = d28 * 1.1d;
        Double.isNaN(d9);
        float f13 = (float) (d29 + d9);
        float f14 = (float) ((this.gridStep * 2) + i4);
        float f15 = (float) ((this.gridStep * 1) + i3);
        double d30 = (double) this.gridStep;
        Double.isNaN(d30);
        double d31 = d30 * 1.9d;
        Double.isNaN(d5);
        float f16 = (float) (d31 + d5);
        float f17 = (float) ((this.gridStep * 1) + i3);
        int i5 = i4;
        double d32 = (double) this.gridStep;
        Double.isNaN(d32);
        double d33 = d32 * 1.5d;
        Double.isNaN(d5);
        path.cubicTo(f13, f14, f15, f16, f17, (float) (d33 + d5));
        float f18 = (float) ((this.gridStep * 1) + i3);
        double d34 = (double) this.gridStep;
        Double.isNaN(d34);
        double d35 = d34 * 1.1d;
        Double.isNaN(d5);
        float f19 = (float) (d35 + d5);
        double d36 = (double) this.gridStep;
        Double.isNaN(d36);
        double d37 = d36 * 1.1d;
        Double.isNaN(d9);
        float f20 = (float) (d37 + d9);
        float f21 = (float) ((this.gridStep * 1) + i5);
        double d38 = (double) this.gridStep;
        Double.isNaN(d38);
        double d39 = d38 * 1.5d;
        Double.isNaN(d9);
        path.cubicTo(f18, f19, f20, f21, (float) (d39 + d9), (float) ((this.gridStep * 1) + i5));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 1) + i5));
        Path path2 = new Path();
        path2.moveTo((float) ((this.gridStep * 2) + i3), (float) ((this.gridStep * 3) + i5));
        double d40 = (double) this.gridStep;
        Double.isNaN(d40);
        double d41 = d40 * 3.5d;
        Double.isNaN(d9);
        path2.lineTo((float) (d41 + d9), (float) ((this.gridStep * 3) + i5));
        double d42 = (double) this.gridStep;
        Double.isNaN(d42);
        double d43 = d42 * 3.9d;
        Double.isNaN(d9);
        float f22 = (float) (d43 + d9);
        float f23 = (float) ((this.gridStep * 3) + i5);
        float f24 = (float) ((this.gridStep * 4) + i3);
        double d44 = (double) this.gridStep;
        Double.isNaN(d44);
        double d45 = d44 * 3.1d;
        Double.isNaN(d5);
        float f25 = (float) (d45 + d5);
        float f26 = (float) ((this.gridStep * 4) + i3);
        double d46 = (double) this.gridStep;
        Double.isNaN(d46);
        double d47 = d46 * 3.5d;
        Double.isNaN(d5);
        Path path3 = path2;
        path3.cubicTo(f22, f23, f24, f25, f26, (float) (d47 + d5));
        float f27 = (float) ((this.gridStep * 4) + i3);
        double d48 = (double) this.gridStep;
        Double.isNaN(d48);
        double d49 = d48 * 3.9d;
        Double.isNaN(d5);
        float f28 = (float) (d49 + d5);
        double d50 = (double) this.gridStep;
        Double.isNaN(d50);
        double d51 = d50 * 3.9d;
        Double.isNaN(d9);
        float f29 = (float) (d51 + d9);
        float f30 = (float) ((this.gridStep * 4) + i5);
        double d52 = (double) this.gridStep;
        Double.isNaN(d52);
        double d53 = d52 * 3.5d;
        Double.isNaN(d9);
        path3.cubicTo(f27, f28, f29, f30, (float) (d53 + d9), (float) ((this.gridStep * 4) + i5));
        double d54 = (double) this.gridStep;
        Double.isNaN(d54);
        double d55 = d54 * 1.5d;
        Double.isNaN(d9);
        path2.lineTo((float) (d55 + d9), (float) ((this.gridStep * 4) + i5));
        double d56 = (double) this.gridStep;
        Double.isNaN(d56);
        double d57 = d56 * 1.1d;
        Double.isNaN(d9);
        float f31 = (float) (d57 + d9);
        float f32 = (float) ((this.gridStep * 4) + i5);
        float f33 = (float) ((this.gridStep * 1) + i3);
        double d58 = (double) this.gridStep;
        Double.isNaN(d58);
        double d59 = d58 * 3.9d;
        Double.isNaN(d5);
        float f34 = (float) (d59 + d5);
        float f35 = (float) ((this.gridStep * 1) + i3);
        double d60 = (double) this.gridStep;
        Double.isNaN(d60);
        double d61 = d60 * 3.5d;
        Double.isNaN(d5);
        path3.cubicTo(f31, f32, f33, f34, f35, (float) (d61 + d5));
        float f36 = (float) ((this.gridStep * 1) + i3);
        double d62 = (double) this.gridStep;
        Double.isNaN(d62);
        double d63 = d62 * 3.1d;
        Double.isNaN(d5);
        float f37 = (float) (d63 + d5);
        double d64 = (double) this.gridStep;
        Double.isNaN(d64);
        double d65 = d64 * 1.1d;
        Double.isNaN(d9);
        float f38 = (float) (d65 + d9);
        float f39 = (float) ((this.gridStep * 3) + i5);
        double d66 = (double) this.gridStep;
        Double.isNaN(d66);
        double d67 = d66 * 1.5d;
        Double.isNaN(d9);
        path3.cubicTo(f36, f37, f38, f39, (float) (d67 + d9), (float) ((this.gridStep * 3) + i5));
        double d68 = (double) this.gridStep;
        Double.isNaN(d68);
        double d69 = d68 * 3.5d;
        Double.isNaN(d9);
        path2.lineTo((float) (d69 + d9), (float) ((this.gridStep * 3) + i5));
        Path path4 = new Path();
        path4.reset();
        path4.op(path, path2, Path.Op.DIFFERENCE);
        return path4;
    }

    private Path optotypeNumber3(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        float f = (float) i3;
        path.moveTo(f, (float) (this.gridStep + i4));
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        double d4 = d3 * 0.4d;
        double d5 = (double) i4;
        Double.isNaN(d5);
        float f2 = (float) (d4 + d5);
        double d6 = (double) this.gridStep;
        Double.isNaN(d6);
        double d7 = d6 * 0.4d;
        double d8 = (double) i3;
        Double.isNaN(d8);
        float f3 = (float) i4;
        double d9 = d8;
        path.cubicTo(f, f2, (float) (d7 + d8), f3, (float) (this.gridStep + i3), f3);
        float f4 = f3;
        path.lineTo(((float) (this.gridStep * 4)) + f, f4);
        double d10 = (double) this.gridStep;
        Double.isNaN(d10);
        double d11 = d10 * 4.7d;
        Double.isNaN(d9);
        float f5 = (float) (d11 + d9);
        float f6 = (float) ((this.gridStep * 5) + i3);
        double d12 = (double) this.gridStep;
        Double.isNaN(d12);
        double d13 = d12 * 0.3d;
        Double.isNaN(d5);
        path.cubicTo(f5, f4, f6, (float) (d13 + d5), (float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 1) + i4));
        path.lineTo(((float) (this.gridStep * 5)) + f, (float) ((this.gridStep * 2) + i4));
        float f7 = (float) ((this.gridStep * 5) + i3);
        double d14 = (double) this.gridStep;
        Double.isNaN(d14);
        double d15 = d14 * 2.2d;
        Double.isNaN(d5);
        float f8 = (float) (d15 + d5);
        float f9 = (float) ((this.gridStep * 5) + i3);
        double d16 = (double) this.gridStep;
        Double.isNaN(d16);
        double d17 = d16 * 2.4d;
        Double.isNaN(d5);
        float f10 = (float) (d17 + d5);
        double d18 = (double) this.gridStep;
        Double.isNaN(d18);
        double d19 = d18 * 4.7d;
        Double.isNaN(d9);
        float f11 = (float) (d19 + d9);
        int i5 = i3;
        int i6 = i4;
        double d20 = (double) this.gridStep;
        Double.isNaN(d20);
        double d21 = d20 * 2.5d;
        Double.isNaN(d5);
        Path path2 = path;
        path2.cubicTo(f7, f8, f9, f10, f11, (float) (d21 + d5));
        float f12 = (float) ((this.gridStep * 5) + i5);
        double d22 = (double) this.gridStep;
        Double.isNaN(d22);
        double d23 = d22 * 2.6d;
        Double.isNaN(d5);
        float f13 = (float) (d23 + d5);
        float f14 = (float) ((this.gridStep * 5) + i5);
        double d24 = (double) this.gridStep;
        Double.isNaN(d24);
        double d25 = d24 * 2.8d;
        Double.isNaN(d5);
        path2.cubicTo(f12, f13, f14, (float) (d25 + d5), (float) ((this.gridStep * 5) + i5), (float) ((this.gridStep * 3) + i6));
        path.lineTo(((float) (this.gridStep * 5)) + f, (float) ((this.gridStep * 4) + i6));
        float f15 = (float) ((this.gridStep * 5) + i5);
        double d26 = (double) this.gridStep;
        Double.isNaN(d26);
        double d27 = d26 * 4.7d;
        Double.isNaN(d5);
        float f16 = (float) (d27 + d5);
        double d28 = (double) this.gridStep;
        Double.isNaN(d28);
        double d29 = d28 * 4.7d;
        Double.isNaN(d9);
        path2.cubicTo(f15, f16, (float) (d29 + d9), (float) ((this.gridStep * 5) + i6), (float) ((this.gridStep * 4) + i5), (float) ((this.gridStep * 5) + i6));
        path.lineTo(((float) (this.gridStep * 1)) + f, (float) ((this.gridStep * 5) + i6));
        double d30 = (double) this.gridStep;
        Double.isNaN(d30);
        double d31 = d30 * 0.3d;
        Double.isNaN(d9);
        float f17 = (float) (d31 + d9);
        float f18 = (float) ((this.gridStep * 5) + i6);
        float f19 = (float) ((this.gridStep * 0) + i5);
        double d32 = (double) this.gridStep;
        Double.isNaN(d32);
        double d33 = d32 * 4.7d;
        Double.isNaN(d5);
        path2.cubicTo(f17, f18, f19, (float) (d33 + d5), (float) ((this.gridStep * 0) + i5), (float) ((this.gridStep * 4) + i6));
        double d34 = (double) this.gridStep;
        Double.isNaN(d34);
        path.lineTo(((float) (d34 * 3.5d)) + f, (float) ((this.gridStep * 4) + i6));
        double d35 = (double) this.gridStep;
        Double.isNaN(d35);
        double d36 = d35 * 3.9d;
        Double.isNaN(d9);
        float f20 = (float) (d36 + d9);
        float f21 = (float) ((this.gridStep * 4) + i6);
        float f22 = (float) ((this.gridStep * 4) + i5);
        double d37 = (double) this.gridStep;
        Double.isNaN(d37);
        double d38 = d37 * 3.9d;
        Double.isNaN(d5);
        float f23 = (float) (d38 + d5);
        float f24 = (float) ((this.gridStep * 4) + i5);
        double d39 = (double) this.gridStep;
        Double.isNaN(d39);
        double d40 = d39 * 3.5d;
        Double.isNaN(d5);
        path2.cubicTo(f20, f21, f22, f23, f24, (float) (d40 + d5));
        float f25 = (float) ((this.gridStep * 4) + i5);
        double d41 = (double) this.gridStep;
        Double.isNaN(d41);
        double d42 = d41 * 3.1d;
        Double.isNaN(d5);
        float f26 = (float) (d42 + d5);
        double d43 = (double) this.gridStep;
        Double.isNaN(d43);
        double d44 = d43 * 3.9d;
        Double.isNaN(d9);
        float f27 = (float) (d44 + d9);
        float f28 = (float) ((this.gridStep * 3) + i6);
        double d45 = (double) this.gridStep;
        Double.isNaN(d45);
        double d46 = d45 * 3.5d;
        Double.isNaN(d9);
        path2.cubicTo(f25, f26, f27, f28, (float) (d46 + d9), (float) ((this.gridStep * 3) + i6));
        path.lineTo(((float) (this.gridStep * 1)) + f, (float) ((this.gridStep * 3) + i6));
        path.lineTo(((float) (this.gridStep * 1)) + f, (float) ((this.gridStep * 2) + i6));
        double d47 = (double) this.gridStep;
        Double.isNaN(d47);
        path.lineTo(((float) (d47 * 3.5d)) + f, (float) ((this.gridStep * 2) + i6));
        double d48 = (double) this.gridStep;
        Double.isNaN(d48);
        double d49 = d48 * 3.9d;
        Double.isNaN(d9);
        float f29 = (float) (d49 + d9);
        float f30 = (float) ((this.gridStep * 2) + i6);
        float f31 = (float) ((this.gridStep * 4) + i5);
        double d50 = (double) this.gridStep;
        Double.isNaN(d50);
        double d51 = d50 * 1.9d;
        Double.isNaN(d5);
        float f32 = (float) (d51 + d5);
        float f33 = (float) ((this.gridStep * 4) + i5);
        double d52 = (double) this.gridStep;
        Double.isNaN(d52);
        double d53 = d52 * 1.5d;
        Double.isNaN(d5);
        path2.cubicTo(f29, f30, f31, f32, f33, (float) (d53 + d5));
        float f34 = (float) ((this.gridStep * 4) + i5);
        double d54 = (double) this.gridStep;
        Double.isNaN(d54);
        double d55 = d54 * 1.1d;
        Double.isNaN(d5);
        float f35 = (float) (d55 + d5);
        double d56 = (double) this.gridStep;
        Double.isNaN(d56);
        double d57 = d56 * 3.9d;
        Double.isNaN(d9);
        float f36 = (float) (d57 + d9);
        float f37 = (float) ((this.gridStep * 1) + i6);
        double d58 = (double) this.gridStep;
        Double.isNaN(d58);
        double d59 = d58 * 3.5d;
        Double.isNaN(d9);
        path2.cubicTo(f34, f35, f36, f37, (float) (d59 + d9), (float) ((this.gridStep * 1) + i6));
        path.lineTo(((float) (this.gridStep * 0)) + f, (float) ((this.gridStep * 1) + i6));
        return path;
    }

    private Path optotypeNumber2(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        float f = (float) i3;
        path.moveTo(f, (float) (this.gridStep + i4));
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        double d4 = d3 * 0.4d;
        double d5 = (double) i4;
        Double.isNaN(d5);
        float f2 = (float) (d4 + d5);
        double d6 = (double) this.gridStep;
        Double.isNaN(d6);
        double d7 = d6 * 0.4d;
        double d8 = (double) i3;
        Double.isNaN(d8);
        float f3 = (float) i4;
        float f4 = f3;
        path.cubicTo(f, f2, (float) (d7 + d8), f3, (float) (this.gridStep + i3), f3);
        float f5 = f4;
        path.lineTo(((float) (this.gridStep * 4)) + f, f5);
        double d9 = (double) this.gridStep;
        Double.isNaN(d9);
        double d10 = d9 * 4.7d;
        Double.isNaN(d8);
        float f6 = (float) (d10 + d8);
        float f7 = (float) ((this.gridStep * 5) + i3);
        double d11 = (double) this.gridStep;
        Double.isNaN(d11);
        double d12 = d11 * 0.3d;
        Double.isNaN(d5);
        path.cubicTo(f6, f5, f7, (float) (d12 + d5), (float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 1) + i4));
        path.lineTo(((float) (this.gridStep * 5)) + f, (float) ((this.gridStep * 2) + i4));
        float f8 = (float) ((this.gridStep * 5) + i3);
        double d13 = (double) this.gridStep;
        Double.isNaN(d13);
        double d14 = d13 * 2.7d;
        Double.isNaN(d5);
        float f9 = (float) (d14 + d5);
        double d15 = (double) this.gridStep;
        Double.isNaN(d15);
        double d16 = d15 * 4.7d;
        Double.isNaN(d8);
        path.cubicTo(f8, f9, (float) (d16 + d8), (float) ((this.gridStep * 3) + i4), (float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 3) + i4));
        double d17 = (double) this.gridStep;
        Double.isNaN(d17);
        path.lineTo(((float) (d17 * 1.5d)) + f, (float) ((this.gridStep * 3) + i4));
        double d18 = (double) this.gridStep;
        Double.isNaN(d18);
        double d19 = d18 * 1.1d;
        Double.isNaN(d8);
        float f10 = (float) (d19 + d8);
        float f11 = (float) ((this.gridStep * 3) + i4);
        float f12 = (float) ((this.gridStep * 1) + i3);
        double d20 = (double) this.gridStep;
        Double.isNaN(d20);
        double d21 = d20 * 2.9d;
        Double.isNaN(d5);
        float f13 = (float) (d21 + d5);
        float f14 = (float) ((this.gridStep * 1) + i3);
        double d22 = d8;
        double d23 = (double) this.gridStep;
        Double.isNaN(d23);
        double d24 = d23 * 3.5d;
        Double.isNaN(d5);
        path.cubicTo(f10, f11, f12, f13, f14, (float) (d24 + d5));
        path.lineTo(((float) (this.gridStep * 1)) + f, (float) ((this.gridStep * 4) + i4));
        path.lineTo(((float) (this.gridStep * 5)) + f, (float) ((this.gridStep * 4) + i4));
        float f15 = (float) ((this.gridStep * 5) + i3);
        double d25 = (double) this.gridStep;
        Double.isNaN(d25);
        double d26 = d25 * 4.7d;
        Double.isNaN(d5);
        float f16 = (float) (d26 + d5);
        double d27 = (double) this.gridStep;
        Double.isNaN(d27);
        double d28 = d27 * 4.7d;
        Double.isNaN(d22);
        path.cubicTo(f15, f16, (float) (d28 + d22), (float) ((this.gridStep * 5) + i4), (float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo(f, (float) ((this.gridStep * 5) + i4));
        double d29 = (double) this.gridStep;
        Double.isNaN(d29);
        double d30 = d29 * 3.5d;
        Double.isNaN(d5);
        path.lineTo(f, (float) (d30 + d5));
        float f17 = (float) ((this.gridStep * 0) + i3);
        double d31 = (double) this.gridStep;
        Double.isNaN(d31);
        double d32 = d31 * 2.3d;
        Double.isNaN(d5);
        float f18 = (float) (d32 + d5);
        double d33 = (double) this.gridStep;
        Double.isNaN(d33);
        double d34 = d33 * 0.3d;
        Double.isNaN(d22);
        float f19 = (float) (d34 + d22);
        float f20 = (float) ((this.gridStep * 2) + i4);
        double d35 = (double) this.gridStep;
        Double.isNaN(d35);
        double d36 = d35 * 1.5d;
        Double.isNaN(d22);
        path.cubicTo(f17, f18, f19, f20, (float) (d36 + d22), (float) ((this.gridStep * 2) + i4));
        double d37 = (double) this.gridStep;
        Double.isNaN(d37);
        double d38 = d37 * 3.5d;
        Double.isNaN(d22);
        path.lineTo((float) (d38 + d22), (float) ((this.gridStep * 2) + i4));
        double d39 = (double) this.gridStep;
        Double.isNaN(d39);
        double d40 = d39 * 3.9d;
        Double.isNaN(d22);
        float f21 = (float) (d40 + d22);
        float f22 = (float) ((this.gridStep * 2) + i4);
        float f23 = (float) ((this.gridStep * 4) + i3);
        double d41 = (double) this.gridStep;
        Double.isNaN(d41);
        double d42 = d41 * 1.9d;
        Double.isNaN(d5);
        float f24 = (float) (d42 + d5);
        float f25 = (float) ((this.gridStep * 4) + i3);
        double d43 = (double) this.gridStep;
        Double.isNaN(d43);
        double d44 = d43 * 1.5d;
        Double.isNaN(d5);
        path.cubicTo(f21, f22, f23, f24, f25, (float) (d44 + d5));
        float f26 = (float) ((this.gridStep * 4) + i3);
        double d45 = (double) this.gridStep;
        Double.isNaN(d45);
        double d46 = d45 * 1.1d;
        Double.isNaN(d5);
        float f27 = (float) (d46 + d5);
        double d47 = (double) this.gridStep;
        Double.isNaN(d47);
        double d48 = d47 * 3.9d;
        Double.isNaN(d22);
        float f28 = (float) (d48 + d22);
        float f29 = (float) ((this.gridStep * 1) + i4);
        double d49 = (double) this.gridStep;
        Double.isNaN(d49);
        double d50 = d49 * 3.5d;
        Double.isNaN(d22);
        path.cubicTo(f26, f27, f28, f29, (float) (d50 + d22), (float) ((this.gridStep * 1) + i4));
        path.lineTo(f, (float) ((this.gridStep * 1) + i4));
        return path;
    }

    private Path optotypeNumber5(int i, int i2) {
        double d = (double) this.gridStep;
        Double.isNaN(d);
        int i3 = i - ((int) (d * 2.5d));
        double d2 = (double) this.gridStep;
        Double.isNaN(d2);
        int i4 = i2 - ((int) (d2 * 2.5d));
        Path path = new Path();
        path.reset();
        float f = (float) i3;
        float f2 = (float) i4;
        path.moveTo(f, f2);
        path.lineTo(((float) (this.gridStep * 5)) + f, f2);
        path.lineTo(((float) (this.gridStep * 5)) + f, (float) (this.gridStep + i4));
        path.lineTo((((float) this.gridStep) * 1.5f) + f, (float) (this.gridStep + i4));
        double d3 = (double) this.gridStep;
        Double.isNaN(d3);
        double d4 = d3 * 1.1d;
        double d5 = (double) i3;
        Double.isNaN(d5);
        float f3 = (float) (d4 + d5);
        float f4 = (float) (this.gridStep + i4);
        float f5 = (float) (this.gridStep + i3);
        double d6 = d5;
        double d7 = (double) this.gridStep;
        Double.isNaN(d7);
        double d8 = d7 * 1.1d;
        double d9 = (double) i4;
        Double.isNaN(d9);
        float f6 = (float) (d8 + d9);
        float f7 = (float) (this.gridStep + i3);
        float f8 = f;
        float f9 = f2;
        double d10 = (double) this.gridStep;
        Double.isNaN(d10);
        double d11 = d10 * 1.5d;
        Double.isNaN(d9);
        float f10 = (float) (d11 + d9);
        double d12 = d6;
        path.cubicTo(f3, f4, f5, f6, f7, f10);
        float f11 = (float) (this.gridStep + i3);
        double d13 = (double) this.gridStep;
        Double.isNaN(d13);
        double d14 = d13 * 1.9d;
        Double.isNaN(d9);
        float f12 = (float) (d14 + d9);
        double d15 = (double) this.gridStep;
        Double.isNaN(d15);
        double d16 = d15 * 1.1d;
        Double.isNaN(d12);
        float f13 = (float) (d16 + d12);
        float f14 = (float) ((this.gridStep * 2) + i4);
        double d17 = (double) this.gridStep;
        Double.isNaN(d17);
        double d18 = d17 * 1.5d;
        Double.isNaN(d12);
        path.cubicTo(f11, f12, f13, f14, (float) (d18 + d12), (float) ((this.gridStep * 2) + i4));
        path.lineTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 2) + i4));
        double d19 = (double) this.gridStep;
        Double.isNaN(d19);
        double d20 = d19 * 4.7d;
        Double.isNaN(d12);
        float f15 = (float) (d20 + d12);
        float f16 = (float) ((this.gridStep * 2) + i4);
        float f17 = (float) ((this.gridStep * 5) + i3);
        double d21 = (double) this.gridStep;
        Double.isNaN(d21);
        double d22 = d21 * 2.3d;
        Double.isNaN(d9);
        path.cubicTo(f15, f16, f17, (float) (d22 + d9), (float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 3) + i4));
        path.lineTo((float) ((this.gridStep * 5) + i3), (float) ((this.gridStep * 4) + i4));
        float f18 = (float) ((this.gridStep * 5) + i3);
        double d23 = (double) this.gridStep;
        Double.isNaN(d23);
        double d24 = d23 * 4.7d;
        Double.isNaN(d9);
        float f19 = (float) (d24 + d9);
        double d25 = (double) this.gridStep;
        Double.isNaN(d25);
        double d26 = d25 * 4.7d;
        Double.isNaN(d12);
        path.cubicTo(f18, f19, (float) (d26 + d12), (float) ((this.gridStep * 5) + i4), (float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 5) + i4));
        path.lineTo((float) ((this.gridStep * 1) + i3), (float) ((this.gridStep * 5) + i4));
        double d27 = (double) this.gridStep;
        Double.isNaN(d27);
        double d28 = d27 * 0.4d;
        Double.isNaN(d12);
        float f20 = (float) (d28 + d12);
        float f21 = (float) ((this.gridStep * 5) + i4);
        float f22 = (float) ((this.gridStep * 0) + i3);
        double d29 = (double) this.gridStep;
        Double.isNaN(d29);
        double d30 = d29 * 4.6d;
        Double.isNaN(d9);
        path.cubicTo(f20, f21, f22, (float) (d30 + d9), f8, (float) ((this.gridStep * 4) + i4));
        path.lineTo((((float) this.gridStep) * 3.5f) + f8, (float) ((this.gridStep * 4) + i4));
        float f23 = (float) ((this.gridStep * 4) + i3);
        float f24 = (float) ((this.gridStep * 4) + i4);
        float f25 = (float) ((this.gridStep * 4) + i3);
        float f26 = (float) ((this.gridStep * 4) + i4);
        float f27 = (((float) this.gridStep) * 4.0f) + f8;
        double d31 = d12;
        double d32 = (double) this.gridStep;
        Double.isNaN(d32);
        double d33 = d32 * 3.5d;
        Double.isNaN(d9);
        path.cubicTo(f23, f24, f25, f26, f27, (float) (d33 + d9));
        path.cubicTo((float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 3) + i4), (float) ((this.gridStep * 4) + i3), (float) ((this.gridStep * 3) + i4), (((float) this.gridStep) * 3.5f) + f8, (float) ((this.gridStep * 3) + i4));
        path.lineTo(((float) this.gridStep) + f8, (float) ((this.gridStep * 3) + i4));
        double d34 = (double) this.gridStep;
        Double.isNaN(d34);
        double d35 = d34 * 0.3d;
        Double.isNaN(d31);
        float f28 = (float) (d35 + d31);
        float f29 = (float) ((this.gridStep * 3) + i4);
        float f30 = (float) ((this.gridStep * 0) + i3);
        double d36 = (double) this.gridStep;
        Double.isNaN(d36);
        double d37 = d36 * 2.7d;
        Double.isNaN(d9);
        path.cubicTo(f28, f29, f30, (float) (d37 + d9), f8, (float) ((this.gridStep * 2) + i4));
        path.lineTo(f8, f9);
        return path;
    }
}
