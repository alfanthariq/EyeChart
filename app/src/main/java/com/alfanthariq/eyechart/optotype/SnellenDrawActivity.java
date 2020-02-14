package com.alfanthariq.eyechart.optotype;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alfanthariq.eyechart.R;
import com.alfanthariq.eyechart.helper.OnSwipeTouchListener;

import static com.alfanthariq.eyechart.optotype.AcuityToolbox.convertFeet2Centimeters;
import static com.alfanthariq.eyechart.optotype.AcuityToolbox.convertInches2Millimeters;

public class SnellenDrawActivity extends AppCompatActivity {
    int fractionIdx = 0;
    SnellenOptotypeCanvas snellenView;
    TextView txtFraction;
    private static final Double INCH_2_MILLIMETER = Double.valueOf(25.4d);
    private double distance = 190d;
    private SharedPreferences pref;
    private int modelUnit;

    private Double[] decimalFraction = new Double[]{
            Double.valueOf(20.0d/400.0d), Double.valueOf(20.0d/320.0d), Double.valueOf(20.0d/250.0d),
            Double.valueOf(20.0d/200.0d), Double.valueOf(20.0d/160.0d), Double.valueOf(20.0d/125.0d),
            Double.valueOf(20.0d/100.0d), Double.valueOf(20.0d/80.0d), Double.valueOf(20.0d/70.0d),
            Double.valueOf(20.0d/50.0d), Double.valueOf(20.0d/40.0d), Double.valueOf(20.0d/30.0d),
            Double.valueOf(20.0d/25.0d), Double.valueOf(20.0d/20.0d), Double.valueOf(20.0d/16.0d),
            Double.valueOf(20.0d/12.5d), Double.valueOf(20.0d/10.0d)
    };
    private String[] fractionStr20 = new String[]{
            "20/400", "20/320", "20/250", "20/200", "20/160", "20/125", "20/100",
            "20/80", "20/70", "20/50", "20/40", "20/30", "20/25", "20/20", "20/16",
            "20/12.5", "20/10"
    };
    private String[] fractionStr6 = new String[]{
            "6/120", "6/96", "6/75", "6/60", "6/40", "6/37.5", "6/30", "6/24", "6/18.9",
            "6/15", "6/12", "6/6.4", "6/7.5", "6/6", "6/4.8", "6/3.75", "6/3"
    };

    Double calculateFontSize(Double fractionDecimal, Double distance){
        double distanceM = distance * 0.01d;
        //double diagonalCM = convertInches2Millimeters(diagonalInch) * 0.1d;
        double m =  0.00145d * (Math.round(distanceM) / fractionDecimal);
        double cm = m * 100d;
        double point = cm * 28.346d;
        double mm = cm * 10.0d;
        System.out.println("fraction: "+fractionDecimal);
        System.out.println("distance: "+distance);
        System.out.println("distance M : "+distanceM);
        System.out.println("CM : "+cm);
        System.out.println("Point : "+point);
        System.out.println("mm : "+mm);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeightPx = dm.heightPixels;
        int screenWidthPx  = dm.widthPixels;
        float ppi = (float)Math.sqrt((double)((screenWidthPx*screenWidthPx) + (screenHeightPx*screenHeightPx)))/getScreenSizeInches().floatValue();
        double conversionRatioPixelsPerMillimeter = Double.valueOf(ppi * (1.0d / INCH_2_MILLIMETER.doubleValue()));

        System.out.println("PPI : "+ppi);
        System.out.println("Ratio : "+conversionRatioPixelsPerMillimeter);
        System.out.println("Return : "+cm*conversionRatioPixelsPerMillimeter);

        return mm*conversionRatioPixelsPerMillimeter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snellen_draw);

        txtFraction = findViewById(R.id.txt_fraction);
        snellenView = findViewById(R.id.snellenView);

        snellenView.drawSnellen(calculateFontSize(decimalFraction[fractionIdx], distance));
        txtFraction.setText("Fraction : "+fractionStr20[fractionIdx]);

        snellenView.setOnTouchListener(new OnSwipeTouchListener(this){
            public void onSwipeTop() {
                swipeUp();
            }
            public void onSwipeRight() {
                swipeRight();
            }
            public void onSwipeLeft() {
                swipeLeft();
            }
            public void onSwipeBottom() {
                swipeDown();
            }
        });

        getPrefs();
    }

    private void getPrefs() {
        pref = getApplicationContext().getSharedPreferences("AppPref", MODE_PRIVATE);
        modelUnit = pref.getInt("unit_system", 0);

        if (modelUnit==0) {
            distance = Double.valueOf(pref.getString("distance", "600"));
        } else {
            distance = convertFeet2Centimeters(Double.valueOf(pref.getString("distance", "19")));
        }
    }

    public Double getScreenSizeInches(){
        DisplayMetrics met = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(met);// get display metrics object
        return Math.sqrt(((met.widthPixels / met.xdpi) *
                (met.widthPixels / met.xdpi)) +
                ((met.heightPixels / met.ydpi) * (met.heightPixels / met.ydpi)));
    }

    private Double calculateOptotypeSizeInPixelsForSnellenFraction(Double d, Double distance, Double diagonalInch) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeightPx = dm.heightPixels;
        int screenWidthPx  = dm.widthPixels;
        float ppi = (float)Math.sqrt((double)(screenWidthPx*screenWidthPx + screenHeightPx*screenHeightPx))/diagonalInch.floatValue();
        double conversionRatioPixelsPerMillimeter = Double.valueOf(ppi * (1.0d / INCH_2_MILLIMETER.doubleValue()));
        return Double.valueOf((double) Math.round(Double.valueOf(Math.tan(0.001454441043328608d) * Double.valueOf(distance.doubleValue() / d.doubleValue()).doubleValue()).doubleValue() * conversionRatioPixelsPerMillimeter));
    }

    void swipeUp(){
        if (fractionIdx > 0) {
            fractionIdx -= 1;
        }
        //snellenView.drawSnellen(calculateOptotypeSizeInPixelsForSnellenFraction(decimalFraction[fractionIdx], distance, screenSizeMM));
        snellenView.drawSnellen(calculateFontSize(decimalFraction[fractionIdx], distance));
        txtFraction.setText("Fraction : "+fractionStr20[fractionIdx]);
    }

    void swipeDown(){
        if (fractionIdx < decimalFraction.length) {
            fractionIdx += 1;
        }
        //snellenView.drawSnellen(calculateOptotypeSizeInPixelsForSnellenFraction(decimalFraction[fractionIdx], distance, screenSizeMM));
        snellenView.drawSnellen(calculateFontSize(decimalFraction[fractionIdx], distance));
        txtFraction.setText("Fraction : "+fractionStr20[fractionIdx]);
    }

    void swipeLeft(){
        //snellenView.drawSnellen(calculateOptotypeSizeInPixelsForSnellenFraction(decimalFraction[fractionIdx], distance, screenSizeMM));
        snellenView.drawSnellen(calculateFontSize(decimalFraction[fractionIdx], distance));
    }

    void swipeRight(){
        //snellenView.drawSnellen(calculateOptotypeSizeInPixelsForSnellenFraction(decimalFraction[fractionIdx], distance, screenSizeMM));
        snellenView.drawSnellen(calculateFontSize(decimalFraction[fractionIdx], distance));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Log.d("TAG", "Current size index : "+Integer.toString(currSizeIndex));
        switch (keyCode) {
            case 19: // up
                swipeUp();
                return true;
            case 20: // down
                swipeDown();
                return true;
            case 21: // left
                swipeLeft();
                return true;
            case 22: // right
                swipeRight();
                return true;
            case 24: // volume up

                return true;
            case KeyEvent.KEYCODE_BACK:
                finish();
                return true;
            default:
                return true;
        }
    }
}
