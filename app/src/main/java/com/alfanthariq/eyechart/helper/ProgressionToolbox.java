package com.alfanthariq.eyechart.helper;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ProgressionToolbox {
    private static final Double[] CLASSIC_SNELLEN_CHART_SEQUENCE = {Double.valueOf(400.0d), Double.valueOf(300.0d), Double.valueOf(200.0d), Double.valueOf(160.0d), Double.valueOf(125.0d), Double.valueOf(100.0d), Double.valueOf(80.0d), Double.valueOf(63.0d), Double.valueOf(50.0d), Double.valueOf(40.0d), Double.valueOf(32.0d), Double.valueOf(25.0d), Double.valueOf(20.0d), Double.valueOf(16.0d), Double.valueOf(12.5d), Double.valueOf(10.0d), Double.valueOf(5.0d), Double.valueOf(2.0d), Double.valueOf(1.0d)};
    private static final Double[] CLASSIC_SNELLEN_CHART_SEQUENCE_LOGMAR = {Double.valueOf(1.3d), Double.valueOf(1.15d), Double.valueOf(1.0d), Double.valueOf(0.9d), Double.valueOf(0.8d), Double.valueOf(0.7d), Double.valueOf(0.6d), Double.valueOf(0.5d), Double.valueOf(0.4d), Double.valueOf(0.3d), Double.valueOf(0.2d), Double.valueOf(0.1d), Double.valueOf(0.0d), Double.valueOf(-0.1d), Double.valueOf(-0.2d), Double.valueOf(-0.3d), Double.valueOf(-0.6d), Double.valueOf(-1.0d), Double.valueOf(-1.3d)};
    private AcuityToolbox2 acuityToolbox;
    private int currentIndexHigherLimit;
    private int currentIndexLowerLimit;
    private ArrayList<Optotype> currentOptotypesListToDraw = new ArrayList<>();
    private int currentProgressionIndex = 0;
    private Activity fragmentActivity;
    private boolean isCustomAcuityLimitsOn = false;
    private boolean isSnellenDenominator6 = false;
    private String lastSelectedLowerLimitInDecimals;
    private String lastSelectedUpperLimitInDecimals;

    public class Optotype {
        public double decimalRequiredValue;
        public double logmarRequiredValue;
        public String snellenDenominator;
        public double snellenRequiredValue;
        public int totalSizeInPixels;
        public int xPosition;
        public int yPosition;

        public Optotype(int i, int i2, int i3) {
            this.xPosition = i;
            this.yPosition = i2;
            this.totalSizeInPixels = i3;
        }

        public void setOptotypeValues(double d, double d2, double d3) {
            this.snellenRequiredValue = d;
            this.logmarRequiredValue = d2;
            this.decimalRequiredValue = d3;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00fa  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0118  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x016a  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0172  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x017b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ProgressionToolbox(android.content.SharedPreferences r19, Activity r20, AcuityToolbox2 act) {
        this.acuityToolbox = act;
        fragmentActivity = r20;

        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r18.<init>()
            r2 = 0
            r0.currentProgressionIndex = r2
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r0.currentOptotypesListToDraw = r3
            r3 = r20
            r0.fragmentActivity = r3
            java.lang.String r4 = "custom_limits_preference"
            boolean r4 = r1.getBoolean(r4, r2)
            r0.isCustomAcuityLimitsOn = r4
            java.lang.String r4 = "custom_limit_upper_preference"
            java.lang.String r5 = ""
            java.lang.String r4 = r1.getString(r4, r5)
            r0.lastSelectedUpperLimitInDecimals = r4
            java.lang.String r4 = "custom_limit_lower_preference"
            java.lang.String r5 = ""
            java.lang.String r4 = r1.getString(r4, r5)
            r0.lastSelectedLowerLimitInDecimals = r4
            java.lang.String r4 = "snellen_fraction_denominator_preference"
            java.lang.String r5 = "0/0"
            java.lang.String r4 = r1.getString(r4, r5)
            java.lang.String r5 = "6/6"
            boolean r4 = r4.equals(r5)
            r5 = 1
            if (r4 == 0) goto L_0x0045
            r0.isSnellenDenominator6 = r5
            goto L_0x0047
        L_0x0045:
            r0.isSnellenDenominator6 = r2
        L_0x0047:
            java.lang.String r4 = "unit_system_preference"
            java.lang.String r6 = ""
            java.lang.String r8 = r1.getString(r4, r6)
            java.lang.String r4 = "user_distance_preference"
            java.lang.String r6 = ""
            java.lang.String r4 = r1.getString(r4, r6)
            java.lang.String r6 = "display_diagonal_size_preference"
            java.lang.String r7 = ""
            java.lang.String r6 = r1.getString(r6, r7)
            java.lang.String r7 = "display_horizontal_resolution_preference"
            java.lang.String r9 = ""
            java.lang.String r7 = r1.getString(r7, r9)
            java.lang.String r9 = "display_vertical_resolution_preference"
            java.lang.String r10 = ""
            java.lang.String r9 = r1.getString(r9, r10)
            double r14 = java.lang.Double.parseDouble(r6)     // Catch:{ NumberFormatException -> 0x007b }
            java.lang.Double r14 = java.lang.Double.valueOf(r14)     // Catch:{ NumberFormatException -> 0x007b }
            r17 = r6
            goto L_0x0140
        L_0x007b:
            android.util.DisplayMetrics r14 = new android.util.DisplayMetrics
            r14.<init>()
            android.view.WindowManager r15 = r20.getWindowManager()
            android.view.Display r2 = r15.getDefaultDisplay()
            r2.getMetrics(r14)
            int r2 = r14.widthPixels
            r17 = r6
            double r5 = (double) r2
            float r2 = r14.ydpi
            double r10 = (double) r2
            java.lang.Double.isNaN(r5)
            java.lang.Double.isNaN(r10)
            double r5 = r5 / r10
            int r2 = r14.heightPixels
            double r10 = (double) r2
            float r2 = r14.xdpi
            double r12 = (double) r2
            java.lang.Double.isNaN(r10)
            java.lang.Double.isNaN(r12)
            double r10 = r10 / r12
            r12 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r5 = java.lang.Math.pow(r5, r12)
            double r10 = java.lang.Math.pow(r10, r12)
            double r5 = r5 + r10
            double r5 = java.lang.Math.sqrt(r5)
            java.lang.Double r2 = java.lang.Double.valueOf(r5)
            java.lang.Double r2 = com.ideas.joaomeneses.snellen.AcuityToolbox.convertInches2Millimeters(r2)
            double r10 = r2.doubleValue()
            r12 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r10 = r10 * r12
            long r10 = java.lang.Math.round(r10)
            double r10 = (double) r10
            java.lang.Double.isNaN(r10)
            double r10 = r10 / r12
            android.content.SharedPreferences$Editor r2 = r19.edit()
            int r12 = r8.hashCode()
            r13 = -1993678384(0xffffffff892ae1d0, float:-2.0569182E-33)
            if (r12 == r13) goto L_0x00ec
            r13 = -366970277(0xffffffffea207a5b, float:-4.8501485E25)
            if (r12 == r13) goto L_0x00e2
            goto L_0x00f6
        L_0x00e2:
            java.lang.String r12 = "Imperial"
            boolean r12 = r8.equals(r12)
            if (r12 == 0) goto L_0x00f6
            r12 = 1
            goto L_0x00f7
        L_0x00ec:
            java.lang.String r12 = "Metric"
            boolean r12 = r8.equals(r12)
            if (r12 == 0) goto L_0x00f6
            r12 = 0
            goto L_0x00f7
        L_0x00f6:
            r12 = -1
        L_0x00f7:
            switch(r12) {
                case 0: goto L_0x0118;
                case 1: goto L_0x0107;
                default: goto L_0x00fa;
            }
        L_0x00fa:
            java.lang.String r2 = "Progression Toolbox"
            java.lang.String r10 = "ERROR: Invalid Selected Unit."
            android.util.Log.e(r2, r10)
            java.lang.Double r2 = java.lang.Double.valueOf(r5)
        L_0x0105:
            r14 = r2
            goto L_0x0129
        L_0x0107:
            java.lang.String r10 = "display_diagonal_size_preference"
            java.lang.String r11 = java.lang.String.valueOf(r5)
            r2.putString(r10, r11)
            r2.apply()
            java.lang.Double r2 = java.lang.Double.valueOf(r5)
            goto L_0x0105
        L_0x0118:
            java.lang.String r5 = "display_diagonal_size_preference"
            java.lang.String r6 = java.lang.String.valueOf(r10)
            r2.putString(r5, r6)
            r2.apply()
            java.lang.Double r2 = java.lang.Double.valueOf(r10)
            goto L_0x0105
        L_0x0129:
            android.content.Context r2 = r20.getApplicationContext()
            android.content.res.Resources r5 = r20.getResources()
            r6 = 2131624098(0x7f0e00a2, float:1.8875366E38)
            java.lang.String r5 = r5.getString(r6)
            r6 = 1
            android.widget.Toast r2 = android.widget.Toast.makeText(r2, r5, r6)
            r2.show()
        L_0x0140:
            int r2 = r8.hashCode()
            r5 = -1993678384(0xffffffff892ae1d0, float:-2.0569182E-33)
            if (r2 == r5) goto L_0x015a
            r5 = -366970277(0xffffffffea207a5b, float:-4.8501485E25)
            if (r2 == r5) goto L_0x014f
            goto L_0x0165
        L_0x014f:
            java.lang.String r2 = "Imperial"
            boolean r2 = r8.equals(r2)
            if (r2 == 0) goto L_0x0165
            r16 = 1
            goto L_0x0167
        L_0x015a:
            java.lang.String r2 = "Metric"
            boolean r2 = r8.equals(r2)
            if (r2 == 0) goto L_0x0165
            r16 = 0
            goto L_0x0167
        L_0x0165:
            r16 = -1
        L_0x0167:
            switch(r16) {
                case 0: goto L_0x017b;
                case 1: goto L_0x0172;
                default: goto L_0x016a;
            }
        L_0x016a:
            java.lang.String r2 = "Progression Toolbox"
            java.lang.String r5 = "ERROR: Invalid Selected Unit."
            android.util.Log.e(r2, r5)
            goto L_0x0187
        L_0x0172:
            double r5 = java.lang.Double.parseDouble(r17)
            java.lang.Double r14 = java.lang.Double.valueOf(r5)
            goto L_0x0187
        L_0x017b:
            double r5 = java.lang.Double.parseDouble(r17)
            java.lang.Double r2 = java.lang.Double.valueOf(r5)
            java.lang.Double r14 = com.ideas.joaomeneses.snellen.AcuityToolbox.convertMillimeters2Inches(r2)
        L_0x0187:
            double r5 = java.lang.Double.parseDouble(r7)
            r10 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r5 = java.lang.Math.pow(r5, r10)
            double r12 = java.lang.Double.parseDouble(r9)
            double r9 = java.lang.Math.pow(r12, r10)
            double r5 = r5 + r9
            double r5 = java.lang.Math.sqrt(r5)
            java.lang.Double r2 = java.lang.Double.valueOf(r5)
            double r5 = r2.doubleValue()
            double r9 = r14.doubleValue()
            double r5 = r5 / r9
            java.lang.Double r12 = java.lang.Double.valueOf(r5)
            android.util.DisplayMetrics r2 = new android.util.DisplayMetrics
            r2.<init>()
            android.view.WindowManager r5 = r20.getWindowManager()
            android.view.Display r5 = r5.getDefaultDisplay()
            r5.getMetrics(r2)
            double r4 = java.lang.Double.parseDouble(r4)     // Catch:{ NumberFormatException -> 0x01c9 }
            java.lang.Double r4 = java.lang.Double.valueOf(r4)     // Catch:{ NumberFormatException -> 0x01c9 }
            r9 = r4
            goto L_0x01fa
        L_0x01c9:
            android.content.SharedPreferences$Editor r1 = r19.edit()
            java.lang.String r4 = "user_distance_preference"
            r5 = 4648488871632306176(0x4082c00000000000, double:600.0)
            java.lang.String r7 = java.lang.String.valueOf(r5)
            r1.putString(r4, r7)
            r1.apply()
            java.lang.Double r1 = java.lang.Double.valueOf(r5)
            android.content.Context r4 = r20.getApplicationContext()
            android.content.res.Resources r3 = r20.getResources()
            r5 = 2131624099(0x7f0e00a3, float:1.8875368E38)
            java.lang.String r3 = r3.getString(r5)
            r5 = 1
            android.widget.Toast r3 = android.widget.Toast.makeText(r4, r3, r5)
            r3.show()
            r9 = r1
        L_0x01fa:
            com.ideas.joaomeneses.snellen.AcuityToolbox r1 = new com.ideas.joaomeneses.snellen.AcuityToolbox
            float r3 = r2.xdpi
            double r3 = (double) r3
            java.lang.Double r10 = java.lang.Double.valueOf(r3)
            float r2 = r2.ydpi
            double r2 = (double) r2
            java.lang.Double r11 = java.lang.Double.valueOf(r2)
            r7 = r1
            r7.<init>(r8, r9, r10, r11, r12)
            r0.acuityToolbox = r1
            return
        */
        //throw new UnsupportedOperationException("Method not decompiled: com.ideas.joaomeneses.snellen.ProgressionToolbox.<init>(android.content.SharedPreferences, android.support.v4.app.FragmentActivity):void");
    }

    public Boolean calculateBiggestOptotypeForThisScreen(Boolean bool, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("Calculate Biggest Optotype For This Screen (Width:");
        sb.append(i);
        sb.append("; Height:");
        sb.append(i2);
        sb.append(")");
        Log.i("Progression Toolbox", sb.toString());
        Boolean bool2 = Boolean.FALSE;
        Double[] dArr = CLASSIC_SNELLEN_CHART_SEQUENCE;
        int length = dArr.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                break;
            }
            double doubleValue = dArr[i3].doubleValue();
            Double calculateOptotypeSizeInPixelsForSnellenFraction = this.acuityToolbox.calculateOptotypeSizeInPixelsForSnellenFraction(Double.valueOf(20.0d / doubleValue));
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Try: ");
            sb2.append(calculateOptotypeSizeInPixelsForSnellenFraction);
            Log.i("Progression Toolbox", sb2.toString());
            if (calculateOptotypeSizeInPixelsForSnellenFraction.intValue() > i || calculateOptotypeSizeInPixelsForSnellenFraction.intValue() > i2 || !validateAcuityCustomLimits(calculateOptotypeSizeInPixelsForSnellenFraction.doubleValue(), false)) {
                i3++;
            } else {
                this.currentProgressionIndex = Arrays.asList(CLASSIC_SNELLEN_CHART_SEQUENCE).indexOf(Double.valueOf(doubleValue));
                this.currentIndexLowerLimit = this.currentProgressionIndex;
                if (bool.booleanValue()) {
                    findEachOptotypePositionOnDisplay(i, i2, calculateOptotypeSizeInPixelsForSnellenFraction.intValue());
                } else {
                    Optotype optotype = new Optotype(i / 2, i2 / 2, calculateOptotypeSizeInPixelsForSnellenFraction.intValue());
                    addMedicalInfoToOptotype(optotype);
                    this.currentOptotypesListToDraw.add(optotype);
                }
                bool2 = Boolean.TRUE;
            }
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Current Progression Index:");
        sb3.append(this.currentProgressionIndex);
        sb3.append(" Biggest Snellen Possible: 20/");
        sb3.append(CLASSIC_SNELLEN_CHART_SEQUENCE[this.currentProgressionIndex]);
        Log.i("Progression Toolbox", sb3.toString());
        return bool2;
    }

    public Boolean nextRound(Boolean bool, int i, int i2) {
        if (this.currentProgressionIndex < CLASSIC_SNELLEN_CHART_SEQUENCE.length - 1) {
            this.currentProgressionIndex++;
            Double calculateOptotypeSizeInPixelsForSnellenFraction = this.acuityToolbox.calculateOptotypeSizeInPixelsForSnellenFraction(Double.valueOf(20.0d / CLASSIC_SNELLEN_CHART_SEQUENCE[this.currentProgressionIndex].doubleValue()));
            if (validateAcuityCustomLimits(calculateOptotypeSizeInPixelsForSnellenFraction.doubleValue(), true)) {
                this.currentOptotypesListToDraw.clear();
                if (bool.booleanValue()) {
                    findEachOptotypePositionOnDisplay(i, i2, calculateOptotypeSizeInPixelsForSnellenFraction.intValue());
                } else {
                    Optotype optotype = new Optotype(i / 2, i2 / 2, calculateOptotypeSizeInPixelsForSnellenFraction.intValue());
                    addMedicalInfoToOptotype(optotype);
                    this.currentOptotypesListToDraw.add(optotype);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Current Progression Draw List:");
                sb.append(this.currentOptotypesListToDraw);
                Log.i("Progression Toolbox", sb.toString());
                return Boolean.TRUE;
            }
            this.currentProgressionIndex--;
            return Boolean.FALSE;
        }
        this.currentProgressionIndex = CLASSIC_SNELLEN_CHART_SEQUENCE.length - 1;
        Toast.makeText(this.fragmentActivity.getApplicationContext(), "Not enough screen", Toast.LENGTH_SHORT).show();
        return Boolean.FALSE;
    }

    public Boolean previousRound(Boolean bool, int i, int i2) {
        if (this.currentProgressionIndex > 0) {
            this.currentProgressionIndex--;
            Double calculateOptotypeSizeInPixelsForSnellenFraction = this.acuityToolbox.calculateOptotypeSizeInPixelsForSnellenFraction(Double.valueOf(20.0d / CLASSIC_SNELLEN_CHART_SEQUENCE[this.currentProgressionIndex].doubleValue()));
            if (validateAcuityCustomLimits(calculateOptotypeSizeInPixelsForSnellenFraction.doubleValue(), true)) {
                this.currentOptotypesListToDraw.clear();
                if (bool.booleanValue()) {
                    findEachOptotypePositionOnDisplay(i, i2, calculateOptotypeSizeInPixelsForSnellenFraction.intValue());
                } else {
                    Optotype optotype = new Optotype(i / 2, i2 / 2, calculateOptotypeSizeInPixelsForSnellenFraction.intValue());
                    addMedicalInfoToOptotype(optotype);
                    this.currentOptotypesListToDraw.add(optotype);
                }
                return Boolean.TRUE;
            }
            this.currentProgressionIndex++;
            return Boolean.FALSE;
        }
        this.currentProgressionIndex = 0;
        Toast.makeText(this.fragmentActivity.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        return Boolean.FALSE;
    }

    private void findEachOptotypePositionOnDisplay(int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append("findEachOptotypePositionOnDisplay View Width:");
        sb.append(i);
        sb.append("; View Height:");
        sb.append(i2);
        Log.i("Progression Toolbox", sb.toString());
        int i4 = i / i3;
        if (i4 < 1) {
            i4 = 1;
        } else if (i4 > 5) {
            i4 = 5;
        }
        int i5 = (i - (i4 * i3)) / (i4 + 1);
        if (i5 * i4 < i3) {
            i4--;
            i5 = (i - (i4 * i3)) / (i4 + 1);
        }
        switch (i4) {
            case 1:
                Optotype optotype = new Optotype(Math.round(((float) i) * 0.5f), i2 / 2, i3);
                addMedicalInfoToOptotype(optotype);
                this.currentOptotypesListToDraw.add(optotype);
                return;
            case 2:
                float f = (float) i3;
                int round = Math.round(0.5f * f) + i5;
                int round2 = (i5 * 2) + Math.round(f * 1.5f);
                int i6 = i2 / 2;
                Optotype optotype2 = new Optotype(round, i6, i3);
                addMedicalInfoToOptotype(optotype2);
                this.currentOptotypesListToDraw.add(optotype2);
                Optotype optotype3 = new Optotype(round2, i6, i3);
                addMedicalInfoToOptotype(optotype3);
                this.currentOptotypesListToDraw.add(optotype3);
                return;
            case 3:
                float f2 = (float) i3;
                int round3 = Math.round(0.5f * f2) + i5;
                int round4 = (i5 * 2) + Math.round(1.5f * f2);
                int round5 = (i5 * 3) + Math.round(f2 * 2.5f);
                int i7 = i2 / 2;
                Optotype optotype4 = new Optotype(round3, i7, i3);
                addMedicalInfoToOptotype(optotype4);
                this.currentOptotypesListToDraw.add(optotype4);
                Optotype optotype5 = new Optotype(round4, i7, i3);
                addMedicalInfoToOptotype(optotype5);
                this.currentOptotypesListToDraw.add(optotype5);
                Optotype optotype6 = new Optotype(round5, i7, i3);
                addMedicalInfoToOptotype(optotype6);
                this.currentOptotypesListToDraw.add(optotype6);
                return;
            case 4:
                float f3 = (float) i3;
                int round6 = Math.round(0.5f * f3) + i5;
                int round7 = (i5 * 2) + Math.round(1.5f * f3);
                int round8 = (i5 * 3) + Math.round(2.5f * f3);
                int round9 = (i5 * 4) + Math.round(f3 * 3.5f);
                int i8 = i2 / 2;
                Optotype optotype7 = new Optotype(round6, i8, i3);
                addMedicalInfoToOptotype(optotype7);
                this.currentOptotypesListToDraw.add(optotype7);
                Optotype optotype8 = new Optotype(round7, i8, i3);
                addMedicalInfoToOptotype(optotype8);
                this.currentOptotypesListToDraw.add(optotype8);
                Optotype optotype9 = new Optotype(round8, i8, i3);
                addMedicalInfoToOptotype(optotype9);
                this.currentOptotypesListToDraw.add(optotype9);
                Optotype optotype10 = new Optotype(round9, i8, i3);
                addMedicalInfoToOptotype(optotype10);
                this.currentOptotypesListToDraw.add(optotype10);
                return;
            case 5:
                float f4 = (float) i3;
                int round10 = Math.round(0.5f * f4) + i5;
                int round11 = (i5 * 2) + Math.round(1.5f * f4);
                int round12 = (i5 * 3) + Math.round(2.5f * f4);
                int round13 = (i5 * 4) + Math.round(3.5f * f4);
                int round14 = (i5 * 5) + Math.round(f4 * 4.5f);
                int i9 = i2 / 2;
                Optotype optotype11 = new Optotype(round10, i9, i3);
                addMedicalInfoToOptotype(optotype11);
                this.currentOptotypesListToDraw.add(optotype11);
                Optotype optotype12 = new Optotype(round11, i9, i3);
                addMedicalInfoToOptotype(optotype12);
                this.currentOptotypesListToDraw.add(optotype12);
                Optotype optotype13 = new Optotype(round12, i9, i3);
                addMedicalInfoToOptotype(optotype13);
                this.currentOptotypesListToDraw.add(optotype13);
                Optotype optotype14 = new Optotype(round13, i9, i3);
                addMedicalInfoToOptotype(optotype14);
                this.currentOptotypesListToDraw.add(optotype14);
                Optotype optotype15 = new Optotype(round14, i9, i3);
                addMedicalInfoToOptotype(optotype15);
                this.currentOptotypesListToDraw.add(optotype15);
                return;
            default:
                Optotype optotype16 = new Optotype(Math.round(((float) i) * 0.5f), i2 / 2, i3);
                addMedicalInfoToOptotype(optotype16);
                this.currentOptotypesListToDraw.add(optotype16);
                return;
        }
    }

    public ArrayList<Optotype> getCurrentOptotypesListToDraw() {
        return this.currentOptotypesListToDraw;
    }

    private void addMedicalInfoToOptotype(Optotype optotype) {
        String str;
        Double calculateLogMARforOptotypeSizeInPixels = this.acuityToolbox.calculateLogMARforOptotypeSizeInPixels(Double.valueOf((double) optotype.totalSizeInPixels));
        StringBuilder sb = new StringBuilder();
        sb.append("Value:");
        sb.append(calculateLogMARforOptotypeSizeInPixels);
        Log.i("Logmar", sb.toString());
        Double d = CLASSIC_SNELLEN_CHART_SEQUENCE[this.currentProgressionIndex];
        Double d2 = CLASSIC_SNELLEN_CHART_SEQUENCE_LOGMAR[this.currentProgressionIndex];
        Double valueOf = Double.valueOf(20.0d / d.doubleValue());
        if (this.isSnellenDenominator6) {
            str = AcuityToolbox2.DENOMINATOR_6_SNELLEN_FRACTION;
            d = Double.valueOf(AcuityToolbox2.convertSnellenFractionFormatTo6(d.doubleValue()));
        } else {
            str = AcuityToolbox2.DENOMINATOR_20_SNELLEN_FRACTION;
        }
        optotype.snellenDenominator = str.split("/")[0];
        optotype.setOptotypeValues(d.doubleValue(), d2.doubleValue(), valueOf.doubleValue());
    }

    private boolean validateAcuityCustomLimits(double d, boolean z) {
        if (this.isCustomAcuityLimitsOn) {
            try {
                Double calculateDecimalForOptotypeSizeInPixels = this.acuityToolbox.calculateDecimalForOptotypeSizeInPixels(Double.valueOf(d));
                StringBuilder sb = new StringBuilder();
                sb.append("Cimo:");
                sb.append(this.lastSelectedUpperLimitInDecimals);
                sb.append("Valor:");
                sb.append(calculateDecimalForOptotypeSizeInPixels);
                sb.append("Baixo:");
                sb.append(this.lastSelectedLowerLimitInDecimals);
                Log.i("PT - Custom Limits", sb.toString());
                if (calculateDecimalForOptotypeSizeInPixels.doubleValue() >= Double.parseDouble(this.lastSelectedUpperLimitInDecimals)) {
                    if (calculateDecimalForOptotypeSizeInPixels.doubleValue() <= Double.parseDouble(this.lastSelectedLowerLimitInDecimals)) {
                        Log.i("PT - Custom Limits", "True");
                        return true;
                    }
                }
                if (z) {
                    Toast.makeText(this.fragmentActivity.getApplicationContext(), "Limit", Toast.LENGTH_SHORT).show();
                }
                Log.i("PT - Custom Limits", "False");
                return false;
            } catch (NumberFormatException unused) {
                Log.i("PT - Custom Limits", "True");
                return true;
            }
        } else {
            Log.i("PT - Custom Limits", "True");
            return true;
        }
    }
}
