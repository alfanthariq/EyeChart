package com.alfanthariq.eyechart.optotype;

import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

public class AcuityToolbox {
    private static final Double FOOT_2_CENTIMETER = Double.valueOf(30.48d);
    private static final Double INCH_2_MILLIMETER = Double.valueOf(25.4d);
    private Activity activity;

    public AcuityToolbox(Activity activity) {
        this.activity = activity;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public Double calculateLogMAR(Double decimalAcuity) {
        //Log.d("AcuityToolbox", "LogMAR : "+String.format("%.2f", Math.log10(round(decimalAcuity, 2))));
        return Double.valueOf(Math.log10(round(decimalAcuity, 2))) * -1;
    }

    public static double convertSnellenFractionFormatTo6(double current20denominator) {
        return (6.0d * current20denominator) / 20.0d;
    }

    public static Double convertInches2Millimeters(Double inches) {
        return Double.valueOf(inches.doubleValue() * INCH_2_MILLIMETER.doubleValue());
    }

    public static Double convertMillimeters2Inches(Double millimeters) {
        return Double.valueOf(millimeters.doubleValue() / INCH_2_MILLIMETER.doubleValue());
    }

    public static Double convertFeet2Centimeters(Double feet) {
        return Double.valueOf(feet.doubleValue() * FOOT_2_CENTIMETER.doubleValue());
    }

    public static Double convertCentimeters2Feet(Double centimeters) {
        return Double.valueOf(centimeters.doubleValue() / FOOT_2_CENTIMETER.doubleValue());
    }

    public double pitagor (int x , int y){
        double c = Math.sqrt((x*x)+(y*y));
        return c;
    }

    /*public Double calculateFontSize(Double fractionDecimal, Double distance, Double diagonalInch){
        Double x =  0.001454441043328608d * (distance * 10d);
        double xx = (double) Math.round((x / fractionDecimal));
        //Log.d("calculateFont", Double.toString(Math.round(getScreenInch()/diagonalInch)));
        Double xxx = xx * diagonalInch;
        System.out.println("Screen size 0: "+fractionDecimal);
        System.out.println("Screen size 1: "+xx);
        System.out.println("Screen size 2: "+xxx);
        double distanceInch = convertMillimeters2Inches(distance);
        return xxx;
    }*/

    public Double calculateFontSize(Double fractionDecimal, Double distance, Double diagonalInch){
        double distanceM = distance * 0.01d;
        //double diagonalCM = convertInches2Millimeters(diagonalInch) * 0.1d;
        double m =  0.00145d * (Math.round(distanceM) / fractionDecimal);
        double cm = m * 100d;
        double point = cm * 28.346d;
        double mm = cm * 10.0d;
        double dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) mm,
                this.activity.getResources().getDisplayMetrics());
        System.out.println("fraction: "+fractionDecimal);
        System.out.println("distance: "+distance);
        System.out.println("distance M : "+distanceM);
        System.out.println("CM : "+cm);
        System.out.println("Point : "+point);
        System.out.println("mm : "+mm);
        return mm;
    }

    public Double getScreenInch(){
        DisplayMetrics dm = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels/dm.xdpi,2);
        double y = Math.pow(dm.heightPixels/dm.ydpi,2);
        double screenInches = Math.sqrt(x+y);
        return screenInches;
    }

    public double getScreenSizeInches(){
        DisplayMetrics met = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay().getMetrics(met);// get display metrics object
        return Math.sqrt(((met.widthPixels / met.xdpi) *
                (met.widthPixels / met.xdpi)) +
                ((met.heightPixels / met.ydpi) * (met.heightPixels / met.ydpi)));
    }
}
