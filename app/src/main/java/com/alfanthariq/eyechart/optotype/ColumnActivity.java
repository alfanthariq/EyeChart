package com.alfanthariq.eyechart.optotype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alfanthariq.eyechart.R;
import com.alfanthariq.eyechart.helper.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

public class ColumnActivity extends AppCompatActivity {
    private Toolbar myToolbar = null;
    private SharedPreferences pref;
    private int tipeChart, col, row, modelUnit, denominator,
                letterCount, currSizeIndex, textHeight, textWidth,
                actionBarHeight;
    private ArrayList<TextView> textChart;
    private GridLayout gridView;
    private Double ContrastLevel;
    private Random rand;
    private String[] allowedLetter;
    private Double distance, customDiagonal;
    private String fontPath, fractionStr;
    private int[] rotation = new int[]{0, 90, 180, 270};
    private TextView
            txtFraction, txtDistance, txtDecimal, txtlogMAR, txtTitle;
    private AcuityToolbox mAcuity;
    private ImageView imgBack, imgNext, imgUp, imgDown;
    private LinearLayout container;
    private Rect bounds;

    private Double[] decimalFraction = new Double[]{
            Double.valueOf(20.0d/400.0d), Double.valueOf(20.0d/320.0d), Double.valueOf(20.0d/250.0d),
            Double.valueOf(20.0d/200.0d), Double.valueOf(20.0d/160.0d), Double.valueOf(20.0d/125.0d),
            Double.valueOf(20.0d/100.0d), Double.valueOf(20.0d/80.0d), Double.valueOf(20.0d/63.0d),
            Double.valueOf(20.0d/50.0d), Double.valueOf(20.0d/40.0d), Double.valueOf(20.0d/32.0d),
            Double.valueOf(20.0d/25.0d), Double.valueOf(20.0d/20.0d), Double.valueOf(20.0d/16.0d),
            Double.valueOf(20.0d/12.5d), Double.valueOf(20.0d/10.0d)
    };
    private String[] fractionStr20 = new String[]{
            "20/400", "20/320", "20/250", "20/200", "20/160", "20/125", "20/100",
            "20/80", "20/63", "20/50", "20/40", "20/32", "20/25", "20/20", "20/16",
            "20/12.5", "20/10"
    };
    private String[] fractionStr6 = new String[]{
            "6/120", "6/96", "6/75", "6/60", "6/40", "6/37.5", "6/30", "6/24", "6/18.9",
            "6/15", "6/12", "6/6.4", "6/7.5", "6/6", "6/4.8", "6/3.75", "6/3"
    };

    private int marginBottom, marginRight;
    private List<String> letters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column);
        mAcuity = new AcuityToolbox(this);

        container = findViewById(R.id.container);
        container.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            container.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                        } else {
                            container.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }

                        cekLimit();
                    }
                });

        container.setOnTouchListener(new OnSwipeTouchListener(ColumnActivity.this){
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

        txtTitle = findViewById(R.id.toolbar_title);

        bounds = new Rect();
        tipeChart = getIntent().getIntExtra("tipeChart", 1);
        switch (tipeChart){
            case 1:
                fontPath = "fonts/Snellen.ttf";
                txtTitle.setText(R.string.title_snallen);
                break;
            case 2:
                fontPath = "fonts/RotationFont.ttf";
                txtTitle.setText(R.string.title_tumbling_e);
                break;
            case 3:
                fontPath = "fonts/RotationFont.ttf";
                txtTitle.setText(R.string.title_landolt);
                break;
            case 4:
                fontPath = "fonts/Children.ttf";
                txtTitle.setText(R.string.title_lea);
                break;
            case 5:
                fontPath = "fonts/Sloan.ttf";
                txtTitle.setText(R.string.title_lea);
                break;
            case 6:
                fontPath = "fonts/Number.ttf";
                txtTitle.setText(R.string.title_number);
                break;
        }

        gridView = findViewById(R.id.gridView);

        txtFraction = findViewById(R.id.txtFraction);
        txtDistance = findViewById(R.id.txtDistance);
        txtDecimal = findViewById(R.id.txtDecimal);
        txtlogMAR = findViewById(R.id.txtLogMAR);

        imgBack = findViewById(R.id.imgBack);
        imgNext = findViewById(R.id.imgNext);
        imgUp = findViewById(R.id.imgUp);
        imgDown = findViewById(R.id.imgDown);

        imgBack.setVisibility(View.GONE);
        imgUp.setVisibility(View.GONE);

        rand = new Random();
        textChart = new ArrayList<>();

        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }

        getPrefs();
        setupToolbar();

        if (tipeChart==2) {
            generateViewEx("E");
        } else if (tipeChart==3){
            generateViewEx("C");
        } else {
            generateView();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
            case KeyEvent.KEYCODE_BACK:
                finish();
                return true;
            default:
                return true;
        }
    }

    private void getPrefs() {
        pref = getApplicationContext().getSharedPreferences("AppPref", MODE_PRIVATE);
        col = pref.getInt("colCount", 1);
        row = pref.getInt("rowCount", 1);
        modelUnit = pref.getInt("unit_system", 0);
        denominator = pref.getInt("denominator", 0);
        ContrastLevel = Double.valueOf(pref.getString("contrast", "100"));
        marginBottom = pref.getInt("bottomMarg", 100);
        marginRight = pref.getInt("rightMarg", 100);
        boolean showGuide = pref.getBoolean("showGuide", true);

        if (showGuide){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Instruction")
                    .setMessage(" "+getResources().getString(R.string.guide_optotype))
                    .setPositiveButton("OK", null)
                    .show();
        }

        if (tipeChart == 6) {
            allowedLetter = pref.getString("allowedNumber", "2,3,5,6,9").split(",");
        } else if (tipeChart == 4) {
            String letter = "!,#,O,a,b,c,d,e,f,g,h,i,j";
            allowedLetter = letter.split(",");
        } else {
            allowedLetter = pref.getString("allowedLetter", "A,B,C,D,E,F,G,H,K,L,N,O,P,T,U,V,Z").split(",");
        }

        letters = new ArrayList<>(Arrays.asList(allowedLetter));

        if (modelUnit==0) {
            distance = Double.valueOf(pref.getString("distance", "600"));
            txtDistance.setText("Distance : "+Double.valueOf(pref.getString("distance", "600"))+" cm");

            Double diagonalMM = mAcuity.convertInches2Millimeters(mAcuity.getScreenInch());
            customDiagonal = mAcuity.convertMillimeters2Inches(Double.valueOf(pref.getString("diagonal", Double.toString(diagonalMM))));
        } else {
            distance = mAcuity.convertFeet2Centimeters(Double.valueOf(pref.getString("distance", "19")));
            txtDistance.setText("Distance : "+Double.valueOf(pref.getString("distance", "19"))+" feet");

            customDiagonal = Double.valueOf(pref.getString("diagonal", Double.toString(mAcuity.getScreenInch())));
        }
    }

    protected void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void generateView(){
        Double fontSize = mAcuity.calculateFontSize(decimalFraction[currSizeIndex], distance, customDiagonal);
        gridView.setColumnCount(col);
        gridView.setRowCount(row);
        int total = col*row;
        int lastRandInt = 0;
        int randIntNew;
        String txt;
        gridView.removeAllViews();
        textChart.clear();
        gridView.setUseDefaultMargins(false);
        gridView.setAlignmentMode(GridLayout.ALIGN_MARGINS);
        for(int i=0; i<total; i++){
            TextView tv = new TextView(this);
            randIntNew = rand.nextInt(allowedLetter.length);
            if (i>0) {
                do {
                    randIntNew = rand.nextInt(allowedLetter.length);
                } while (randIntNew == lastRandInt);
            }
            lastRandInt = randIntNew;
            txt = allowedLetter[randIntNew];
            tv.setText(txt);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
            tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            tv.setTextColor(Color.BLACK);
            tv.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel / 100.0d).toString()));
            tv.measure(0, 0);
            CalligraphyUtils.applyFontToTextView(this, tv, fontPath);
            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams(gridView.getLayoutParams());
            params.setMargins(0,0,marginRight,marginBottom);
            tv.setLayoutParams(params);
            textChart.add(tv);
            gridView.addView(tv);
        }
    }

    private void generateViewEx(String txt){
        Double fontSize = mAcuity.calculateFontSize(decimalFraction[currSizeIndex], distance, customDiagonal);
        gridView.setColumnCount(col);
        gridView.setRowCount(row);
        gridView.removeAllViews();
        textChart.clear();
        gridView.setUseDefaultMargins(false);
        gridView.setAlignmentMode(GridLayout.ALIGN_MARGINS);
        int total = col*row;
        int lastRandInt = 0;
        int randIntNew;
        int rot;
        for(int i=0; i<total; i++){
            TextView tv = new TextView(this);
            tv.setText(txt);
            CalligraphyUtils.applyFontToTextView(this, tv, fontPath);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
            tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            tv.setTextColor(Color.BLACK);
            tv.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel/100.0d).toString()));
            randIntNew = rand.nextInt(rotation.length);
            if (i>0) {
                do {
                    randIntNew = rand.nextInt(rotation.length);
                } while (randIntNew == lastRandInt);
            }
            lastRandInt = randIntNew;
            rot = rotation[randIntNew];
            tv.setRotation(rot);
            tv.measure(0, 0);
            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams(gridView.getLayoutParams());
            params.setMargins(0, 0, marginRight, marginBottom);
            tv.setLayoutParams(params);
            textChart.add(tv);
            gridView.addView(tv);
        }
    }

    private void updateSize(){
        textHeight = 0;
        Double fontSize = mAcuity.calculateFontSize(decimalFraction[currSizeIndex], distance, customDiagonal);
        for(int i=0; i<textChart.size(); i++){
            textChart.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
        }
        textHeight += textChart.get(0).getPaint().getFontMetrics().bottom - textChart.get(0).getPaint().getFontMetrics().top;
        textChart.get(0).getPaint().getTextBounds(textChart.get(0).getText().toString(), 0, textChart.get(0).getText().length(), bounds);
        textWidth = bounds.width();

        txtlogMAR.setText("LogMAR : "+String.format("%.2f", mAcuity.calculateLogMAR(decimalFraction[currSizeIndex])));
        txtDecimal.setText("Decimal Acuity : "+String.format("%.2f", decimalFraction[currSizeIndex]));
        if (denominator==0) {
            fractionStr = fractionStr20[currSizeIndex];
        } else {
            fractionStr = fractionStr6[currSizeIndex];
        }
        txtFraction.setText("Fraction : "+fractionStr);
    }

    private void randomText(){
        int lastRandInt = 0;
        int randIntNew;
        for(int i=0; i<textChart.size(); i++){
            if (tipeChart==2){
                randIntNew = rand.nextInt(rotation.length);
                if (i>0) {
                    do {
                        randIntNew = rand.nextInt(rotation.length);
                    } while (randIntNew == lastRandInt);
                }
                lastRandInt = randIntNew;
                textChart.get(i).setRotation(rotation[randIntNew]);
            } else if (tipeChart==3) {
                randIntNew = rand.nextInt(rotation.length);
                if (i>0) {
                    do {
                        randIntNew = rand.nextInt(rotation.length);
                    } while (randIntNew == lastRandInt);
                }
                lastRandInt = randIntNew;
                textChart.get(i).setRotation(rotation[randIntNew]);
            } else {
                randIntNew = rand.nextInt(allowedLetter.length);
                if (i>0) {
                    do {
                        randIntNew = rand.nextInt(allowedLetter.length);
                    } while (randIntNew == lastRandInt);
                }
                lastRandInt = randIntNew;
                textChart.get(i).setText(allowedLetter[randIntNew]);
            }
        }
    }

    private void cekLimit(){
        ArrayList<Double> newDecFraction = new ArrayList<>();
        ArrayList<String> newFracitionStr20 = new ArrayList<>();
        ArrayList<String> newFracitionStr6 = new ArrayList<>();
        //layProgress.setVisibility(View.VISIBLE);
        int totalHeightText = 0;
        for (int i=0; i<decimalFraction.length; i++) {
            currSizeIndex = i;
            updateSize();
            totalHeightText = textHeight * row;
            if ((totalHeightText <= container.getHeight() - (actionBarHeight * 3))) {
                if (textWidth <= container.getWidth()) {
                    newDecFraction.add(decimalFraction[i]);
                    newFracitionStr20.add(fractionStr20[i]);
                    newFracitionStr6.add(fractionStr6[i]);
                }
            }
        }

        if (newDecFraction.size()>0) {
            decimalFraction = new Double[newDecFraction.size()];
            fractionStr20 = new String[newFracitionStr20.size()];
            fractionStr6 = new String[newFracitionStr6.size()];

            for (int i = 0; i < newDecFraction.size(); i++) {
                decimalFraction[i] = newDecFraction.get(i);
                fractionStr20[i] = newFracitionStr20.get(i);
                fractionStr6[i] = newFracitionStr6.get(i);
            }
        }

        currSizeIndex = 0;
        if (tipeChart==2) {
            generateViewEx("E");
        } else if (tipeChart==3){
            generateViewEx("C");
        } else {
            generateView();
        }

        txtlogMAR.setText("LogMAR : "+String.format("%.2f", mAcuity.calculateLogMAR(decimalFraction[currSizeIndex])));
        txtDecimal.setText("Decimal Acuity : "+String.format("%.2f", decimalFraction[currSizeIndex]));
        if (denominator==0) {
            fractionStr = fractionStr20[currSizeIndex];
        } else {
            fractionStr = fractionStr6[currSizeIndex];
        }
        txtFraction.setText("Fraction : "+fractionStr);
    }

    private void swipeUp(){
        if (currSizeIndex > 0) {
            currSizeIndex -= 1;
            randomText();
            updateSize();
        }
        if (currSizeIndex==0) {
            imgUp.setVisibility(View.GONE);
        } else {
            imgUp.setVisibility(View.VISIBLE);
        }

        if (currSizeIndex==decimalFraction.length-1){
            imgDown.setVisibility(View.GONE);
        } else {
            imgDown.setVisibility(View.VISIBLE);
        }
    }

    private void swipeDown(){
        if (currSizeIndex < decimalFraction.length - 1) {
            currSizeIndex += 1;
            randomText();
            updateSize();
        }
        if (currSizeIndex==0) {
            imgUp.setVisibility(View.GONE);
        } else {
            imgUp.setVisibility(View.VISIBLE);
        }

        if (currSizeIndex==decimalFraction.length-1){
            imgDown.setVisibility(View.GONE);
        } else {
            imgDown.setVisibility(View.VISIBLE);
        }
    }

    private void swipeLeft(){
        if (letterCount > 0) {
            letterCount -= 1;
            randomText();
        }
        if (letterCount==0){
            imgBack.setVisibility(View.GONE);
        } else {
            imgBack.setVisibility(View.VISIBLE);
        }
    }

    private void swipeRight(){
        letterCount += 1;
        randomText();
        if (letterCount == 0) {
            imgBack.setVisibility(View.GONE);
        } else {
            imgBack.setVisibility(View.VISIBLE);
        }
    }
}
