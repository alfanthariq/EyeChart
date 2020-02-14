package com.alfanthariq.eyechart.helper;

import android.util.Log;

public class AcuityToolbox2 {
    public static final String DENOMINATOR_20_SNELLEN_FRACTION = "20/20";
    public static final String DENOMINATOR_6_SNELLEN_FRACTION = "6/6";
    private static final Double FOOT_2_CENTIMETER = Double.valueOf(30.48d);
    public static final String IMPERIAL_UNITS = "Imperial";
    private static final Double INCH_2_MILLIMETER = Double.valueOf(25.4d);
    public static final String METRIC_UNITS = "Metric";
    private static Double conversionRatioPixelsPerMillimeter;
    private static Double playerDistanceToScreen_mm;

    public static double convertSnellenFractionFormatTo6(double d) {
        return (d * 6.0d) / 20.0d;
    }

    public AcuityToolbox2(String str, Double d, Double d2, Double d3, Double d4) {
        StringBuilder sb = new StringBuilder();
        sb.append("INPUT >> unit: ");
        sb.append(str);
        sb.append(" distance: ");
        sb.append(d);
        sb.append(" xdpi: ");
        sb.append(d2);
        sb.append(" ydpi: ");
        sb.append(d3);
        sb.append(" ppi: ");
        sb.append(d4);
        Log.d("Acuity Toolbox", sb.toString());
        if (str.equals(METRIC_UNITS)) {
            playerDistanceToScreen_mm = Double.valueOf(d.doubleValue() * 10.0d);
        } else if (str.equals(IMPERIAL_UNITS)) {
            playerDistanceToScreen_mm = Double.valueOf(d.doubleValue() * FOOT_2_CENTIMETER.doubleValue() * 10.0d);
        }
        d3.equals(d2);
        conversionRatioPixelsPerMillimeter = Double.valueOf(d4.doubleValue() * (1.0d / INCH_2_MILLIMETER.doubleValue()));
        StringBuilder sb2 = new StringBuilder();
        sb2.append("OUTPUT >> unit: ");
        sb2.append(str);
        sb2.append(" distance: ");
        sb2.append(playerDistanceToScreen_mm);
        sb2.append(" ratioPxMm: ");
        sb2.append(conversionRatioPixelsPerMillimeter);
        Log.d("Acuity Toolbox", sb2.toString());
    }

    public Double calculateOptotypeSizeInPixelsForSnellenFraction(Double d) {
        return Double.valueOf((double) Math.round(Double.valueOf(Math.tan(0.001454441043328608d) * Double.valueOf(playerDistanceToScreen_mm.doubleValue() / d.doubleValue()).doubleValue()).doubleValue() * conversionRatioPixelsPerMillimeter.doubleValue()));
    }

    public Double calculateSnellenFractionForOptotypeSizeInPixels(Double d) {
        return Double.valueOf(20.0d / (playerDistanceToScreen_mm.doubleValue() / ((d.doubleValue() / conversionRatioPixelsPerMillimeter.doubleValue()) / Math.tan(0.001454441043328608d))));
    }

    public Double calculateDecimalForOptotypeSizeInPixels(Double d) {
        return Double.valueOf(20.0d / Double.valueOf(20.0d / (playerDistanceToScreen_mm.doubleValue() / ((d.doubleValue() / conversionRatioPixelsPerMillimeter.doubleValue()) / Math.tan(0.001454441043328608d)))).doubleValue());
    }

    public Double calculateVisualAngleArcForOptotypeSizeInPixels(Double d) {
        return Double.valueOf(Math.atan((d.doubleValue() / conversionRatioPixelsPerMillimeter.doubleValue()) / playerDistanceToScreen_mm.doubleValue()) * 57.29577951308232d);
    }

    public Double calculateLogMARforOptotypeSizeInPixels(Double d) {
        return Double.valueOf(Math.log10(Double.valueOf((Double.valueOf(Math.atan((d.doubleValue() / conversionRatioPixelsPerMillimeter.doubleValue()) / playerDistanceToScreen_mm.doubleValue()) * 57.29577951308232d).doubleValue() * 60.0d) / 5.0d).doubleValue()));
    }

    public Double calculateOptotypeSizeInPixelsForLogMar(Double d) {
        return Double.valueOf(Math.tan(Double.valueOf((Math.pow(10.0d, d.doubleValue()) * 5.0d) / 60.0d).doubleValue() / 57.29577951308232d) * playerDistanceToScreen_mm.doubleValue() * conversionRatioPixelsPerMillimeter.doubleValue());
    }

    public Double calculateOptotypeSizeInPixelsForVisualAngleArc(Double d) {
        return Double.valueOf(Math.tan(d.doubleValue() / 57.29577951308232d) * playerDistanceToScreen_mm.doubleValue() * conversionRatioPixelsPerMillimeter.doubleValue());
    }

    public static Double convertInches2Millimeters(Double d) {
        return Double.valueOf(d.doubleValue() * INCH_2_MILLIMETER.doubleValue());
    }

    public static Double convertMillimeters2Inches(Double d) {
        return Double.valueOf(d.doubleValue() / INCH_2_MILLIMETER.doubleValue());
    }

    public static Double convertFeet2Centimeters(Double d) {
        return Double.valueOf(d.doubleValue() * FOOT_2_CENTIMETER.doubleValue());
    }

    public static Double convertCentimeters2Feet(Double d) {
        return Double.valueOf(d.doubleValue() / FOOT_2_CENTIMETER.doubleValue());
    }
}
