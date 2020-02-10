package com.alfanthariq.eyechart.optotype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alfanthariq.eyechart.R;
import com.alfanthariq.eyechart.SettingsActivity;
import com.alfanthariq.eyechart.helper.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

public class LandoltCActivity extends AppCompatActivity {
    private Toolbar myToolbar = null;
    private SharedPreferences pref;
    private Double distance, customDiagonal;
    private String fontPath;
    private LinearLayout
            container, tumblingContainer1, tumblingContainer2, tumblingContainer3,
            tumblingContainer4;
    private TextView
            txtFraction, txtDistance, txtDecimal, txtlogMAR;
    private ImageView imgBack, imgNext, imgUp, imgDown;
    private int modelUnit, denominator, letterCount, currSizeIndex, actionBarHeight,
            totalHeightText;
    private AcuityToolbox mAcuity;
    private String fractionStr;
    private Double ContrastLevel;
    private RelativeLayout layProgress;
    private ProgressBar progressBar;
    private ImageButton imb_setting;
    private Random rand;
    private int[] rotation = new int[]{0, 90, 180, 270};
    private ArrayList<TextView> tumblingText1, tumblingText2, tumblingText3, tumblingText4;
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

    // 0: single line, 1: double/same, 2:triple same, 3: triple decresing, 4: quadruple same,
    // 5: quadruple decresing, 6: single letter, 7: columm, 8: columm uneven/tidak rata
    private int ModelPresentasi = 0;
    private int marginBottom, marginRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landolt_c);

        mAcuity = new AcuityToolbox(this);

        container = findViewById(R.id.container);
        container.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        container.getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);

                        cekLimit();
                    }
                });

        container.setOnTouchListener(new OnSwipeTouchListener(LandoltCActivity.this){
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
        txtFraction = findViewById(R.id.txtFraction);
        txtDistance = findViewById(R.id.txtDistance);
        txtDecimal = findViewById(R.id.txtDecimal);
        txtlogMAR = findViewById(R.id.txtLogMAR);
        layProgress = findViewById(R.id.layProgress);
        progressBar = findViewById(R.id.progressbar);
        imb_setting = findViewById(R.id.imb_setting);
        rand = new Random();
        letterCount = 0;
        currSizeIndex = 0;

        imb_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSetting();
            }
        });

        tumblingContainer1 = findViewById(R.id.tumblingContainer1);
        tumblingContainer2 = findViewById(R.id.tumblingContainer2);
        tumblingContainer3 = findViewById(R.id.tumblingContainer3);
        tumblingContainer4 = findViewById(R.id.tumblingContainer4);
        tumblingContainer1.measure(0,0);
        tumblingContainer2.measure(0,0);
        tumblingContainer3.measure(0,0);
        tumblingContainer4.measure(0,0);

        imgBack = findViewById(R.id.imgBack);
        imgNext = findViewById(R.id.imgNext);
        imgUp = findViewById(R.id.imgUp);
        imgDown = findViewById(R.id.imgDown);

        imgBack.setVisibility(View.GONE);
        imgUp.setVisibility(View.GONE);

        fontPath = "fonts/RotationFont.ttf";

        getPrefs();
        setupToolbar();

        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
    }

    private void openSetting(){
        Intent intent = new Intent(LandoltCActivity.this, SettingsActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            letterCount = 0;
            currSizeIndex = 0;
            getPrefs();
            decimalFraction = new Double[]{
                    Double.valueOf(20.0d/400.0d), Double.valueOf(20.0d/320.0d), Double.valueOf(20.0d/250.0d),
                    Double.valueOf(20.0d/200.0d), Double.valueOf(20.0d/160.0d), Double.valueOf(20.0d/125.0d),
                    Double.valueOf(20.0d/100.0d), Double.valueOf(20.0d/80.0d), Double.valueOf(20.0d/63.0d),
                    Double.valueOf(20.0d/50.0d), Double.valueOf(20.0d/40.0d), Double.valueOf(20.0d/32.0d),
                    Double.valueOf(20.0d/25.0d), Double.valueOf(20.0d/20.0d), Double.valueOf(20.0d/16.0d),
                    Double.valueOf(20.0d/12.5d), Double.valueOf(20.0d/10.0d)};
            fractionStr20 = new String[]{
                    "20/400", "20/320", "20/250", "20/200", "20/160", "20/125", "20/100",
                    "20/80", "20/63", "20/50", "20/40", "20/32", "20/25", "20/20", "20/16",
                    "20/12.5", "20/10"
            };
            fractionStr6 = new String[]{
                    "6/120", "6/96", "6/75", "6/60", "6/40", "6/37.5", "6/30", "6/24", "6/18.9",
                    "6/15", "6/12", "6/6.4", "6/7.5", "6/6", "6/4.8", "6/3.75", "6/3"
            };
            cekLimit();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onBackPressed(){
        this.finish();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Log.d("TAG", "Current size index : "+Integer.toString(currSizeIndex));
        System.out.println("Key code : "+keyCode);
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
                openSetting();
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

        ModelPresentasi = pref.getInt("model", 0);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, marginBottom);

        switch (ModelPresentasi) {
            case 0:
                tumblingContainer1.setVisibility(View.VISIBLE);
                tumblingContainer2.setVisibility(View.GONE);
                tumblingContainer3.setVisibility(View.GONE);
                tumblingContainer4.setVisibility(View.GONE);
                break;
            case 1:
                tumblingContainer1.setVisibility(View.VISIBLE);
                tumblingContainer1.setLayoutParams(params);
                tumblingContainer2.setVisibility(View.VISIBLE);
                tumblingContainer3.setVisibility(View.GONE);
                tumblingContainer4.setVisibility(View.GONE);
                break;
            case 2:
                tumblingContainer1.setVisibility(View.VISIBLE);
                tumblingContainer1.setLayoutParams(params);
                tumblingContainer2.setVisibility(View.VISIBLE);
                tumblingContainer2.setLayoutParams(params);
                tumblingContainer3.setVisibility(View.VISIBLE);
                tumblingContainer4.setVisibility(View.GONE);
                break;
            case 3:
                tumblingContainer1.setVisibility(View.VISIBLE);
                tumblingContainer1.setLayoutParams(params);
                tumblingContainer2.setVisibility(View.VISIBLE);
                tumblingContainer2.setLayoutParams(params);
                tumblingContainer3.setVisibility(View.VISIBLE);
                tumblingContainer3.setLayoutParams(params);
                tumblingContainer4.setVisibility(View.GONE);
                break;
            case 4:
                tumblingContainer1.setVisibility(View.VISIBLE);
                tumblingContainer1.setLayoutParams(params);
                tumblingContainer2.setVisibility(View.VISIBLE);
                tumblingContainer2.setLayoutParams(params);
                tumblingContainer3.setVisibility(View.VISIBLE);
                tumblingContainer3.setLayoutParams(params);
                tumblingContainer4.setVisibility(View.VISIBLE);
                tumblingContainer4.setLayoutParams(params);
                break;
            case 5:
                tumblingContainer1.setVisibility(View.VISIBLE);
                tumblingContainer1.setLayoutParams(params);
                tumblingContainer2.setVisibility(View.VISIBLE);
                tumblingContainer2.setLayoutParams(params);
                tumblingContainer3.setVisibility(View.VISIBLE);
                tumblingContainer3.setLayoutParams(params);
                tumblingContainer4.setVisibility(View.VISIBLE);
                tumblingContainer4.setLayoutParams(params);
                break;
            case 6:
                tumblingContainer1.setVisibility(View.VISIBLE);
                tumblingContainer2.setVisibility(View.GONE);
                tumblingContainer3.setVisibility(View.GONE);
                tumblingContainer4.setVisibility(View.GONE);
                break;
            case 7:
                break;
            case 8:
                break;
        }

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

    private TextView buatText(LinearLayout.LayoutParams params, ArrayList<TextView> arr){
        TextView tv = new TextView(this);
        tv.setText("C");
        tv.measure(0,0);
        tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        tv.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel/100.0d).toString()));
        tv.setTextColor(Color.BLACK);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setPadding(5,5,5,5);
        CalligraphyUtils.applyFontToTextView(this, tv, fontPath);

        int rot = rotation[rand.nextInt(rotation.length)];

        if (arr.size()>=1) {
            while (rot == arr.get(arr.size() - 1).getRotation()) {
                rot = rotation[rand.nextInt(rotation.length)];
            }
        }
        tv.setRotation(rot);
        tv.setLayoutParams(params);
        return tv;
    }

    private void randomText(){
        TextView tv;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, marginRight, 0);
        switch (ModelPresentasi){
            case 0:
                tumblingText1 = new ArrayList<>();
                if(tumblingContainer1.getChildCount()>0){
                    tumblingContainer1.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText1);
                    tumblingText1.add(tv);
                    tumblingContainer1.addView(tv);
                }
                break;
            case 1:
                tumblingText1 = new ArrayList<>();
                if(tumblingContainer1.getChildCount()>0){
                    tumblingContainer1.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText1);
                    tumblingText1.add(tv);
                    tumblingContainer1.addView(tv);
                }

                tumblingText2 = new ArrayList<>();
                if(tumblingContainer2.getChildCount()>0){
                    tumblingContainer2.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText2);
                    tumblingText2.add(tv);
                    tumblingContainer2.addView(tv);
                }
                break;
            case 2:
                tumblingText1 = new ArrayList<>();
                if(tumblingContainer1.getChildCount()>0){
                    tumblingContainer1.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText1);
                    tumblingText1.add(tv);
                    tumblingContainer1.addView(tv);
                }

                tumblingText2 = new ArrayList<>();
                if(tumblingContainer2.getChildCount()>0){
                    tumblingContainer2.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText2);
                    tumblingText2.add(tv);
                    tumblingContainer2.addView(tv);
                }

                tumblingText3 = new ArrayList<>();
                if(tumblingContainer3.getChildCount()>0){
                    tumblingContainer3.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText3);
                    tumblingText3.add(tv);
                    tumblingContainer3.addView(tv);
                }
                break;
            case 3:
                tumblingText1 = new ArrayList<>();
                if(tumblingContainer1.getChildCount()>0){
                    tumblingContainer1.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText1);
                    tumblingText1.add(tv);
                    tumblingContainer1.addView(tv);
                }

                tumblingText2 = new ArrayList<>();
                if(tumblingContainer2.getChildCount()>0){
                    tumblingContainer2.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText2);
                    tumblingText2.add(tv);
                    tumblingContainer2.addView(tv);
                }

                tumblingText3 = new ArrayList<>();
                if(tumblingContainer3.getChildCount()>0){
                    tumblingContainer3.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText3);
                    tumblingText3.add(tv);
                    tumblingContainer3.addView(tv);
                }
                break;
            case 4:
                tumblingText1 = new ArrayList<>();
                if(tumblingContainer1.getChildCount()>0){
                    tumblingContainer1.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText1);
                    tumblingText1.add(tv);
                    tumblingContainer1.addView(tv);
                }

                tumblingText2 = new ArrayList<>();
                if(tumblingContainer2.getChildCount()>0){
                    tumblingContainer2.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText2);
                    tumblingText2.add(tv);
                    tumblingContainer2.addView(tv);
                }

                tumblingText3 = new ArrayList<>();
                if(tumblingContainer3.getChildCount()>0){
                    tumblingContainer3.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText3);
                    tumblingText3.add(tv);
                    tumblingContainer3.addView(tv);
                }

                tumblingText4 = new ArrayList<>();
                if(tumblingContainer4.getChildCount()>0){
                    tumblingContainer4.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText4);
                    tumblingText4.add(tv);
                    tumblingContainer4.addView(tv);
                }
                break;
            case 5:
                tumblingText1 = new ArrayList<>();
                if(tumblingContainer1.getChildCount()>0){
                    tumblingContainer1.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText1);
                    tumblingText1.add(tv);
                    tumblingContainer1.addView(tv);
                }

                tumblingText2 = new ArrayList<>();
                if(tumblingContainer2.getChildCount()>0){
                    tumblingContainer2.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText2);
                    tumblingText2.add(tv);
                    tumblingContainer2.addView(tv);
                }

                tumblingText3 = new ArrayList<>();
                if(tumblingContainer3.getChildCount()>0){
                    tumblingContainer3.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText3);
                    tumblingText3.add(tv);
                    tumblingContainer3.addView(tv);
                }

                tumblingText4 = new ArrayList<>();
                if(tumblingContainer4.getChildCount()>0){
                    tumblingContainer4.removeAllViews();
                }
                for (int i=0;i<=letterCount;i++){
                    tv = buatText(params, tumblingText4);
                    tumblingText4.add(tv);
                    tumblingContainer4.addView(tv);
                }
                break;
            case 6:
                tumblingText1 = new ArrayList<>();
                if(tumblingContainer1.getChildCount()>0){
                    tumblingContainer1.removeAllViews();
                }
                for (int i=0;i<=0;i++){
                    tv = buatText(params, tumblingText1);
                    tumblingText1.add(tv);
                    tumblingContainer1.addView(tv);
                }
                break;
        }
    }

    private void updateSizeDecrease(int state){
        totalHeightText = 0;
        if (state==3) {
            if (currSizeIndex+2 < decimalFraction.length) {
                Double fontSize1 = mAcuity.calculateFontSize(decimalFraction[currSizeIndex], distance, customDiagonal);
                Double fontSize2 = mAcuity.calculateFontSize(decimalFraction[currSizeIndex + 1], distance, customDiagonal);
                Double fontSize3 = mAcuity.calculateFontSize(decimalFraction[currSizeIndex + 2], distance, customDiagonal);
                randomText();
                for (int i = 0; i < tumblingText1.size(); i++) {
                    tumblingText1.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize1));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText1.get(i), fontPath);
                }
                //randomText();
                for (int i = 0; i < tumblingText2.size(); i++) {
                    tumblingText2.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize2));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText2.get(i), fontPath);
                }

                //randomText();
                for (int i = 0; i < tumblingText3.size(); i++) {
                    tumblingText3.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize3));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText3.get(i), fontPath);
                }
                totalHeightText += tumblingText1.get(0).getPaint().getFontMetrics().bottom - tumblingText1.get(0).getPaint().getFontMetrics().top;
                totalHeightText += tumblingText2.get(0).getPaint().getFontMetrics().bottom - tumblingText2.get(0).getPaint().getFontMetrics().top;
                totalHeightText += tumblingText3.get(0).getPaint().getFontMetrics().bottom - tumblingText3.get(0).getPaint().getFontMetrics().top;
                totalHeightText += marginBottom * 3;

                String logMar = String.format("%.2f", mAcuity.calculateLogMAR(decimalFraction[currSizeIndex]))+" | "+
                        String.format("%.2f", mAcuity.calculateLogMAR(decimalFraction[currSizeIndex+1]))+" | "+
                        String.format("%.2f", mAcuity.calculateLogMAR(decimalFraction[currSizeIndex+2]));

                String decFrac = String.format("%.2f", decimalFraction[currSizeIndex])+" | "+
                        String.format("%.2f", decimalFraction[currSizeIndex+1])+" | "+
                        String.format("%.2f", decimalFraction[currSizeIndex+2]);
                txtlogMAR.setText("LogMAR : "+logMar);
                txtDecimal.setText("Decimal Acuity : "+decFrac);
                if (denominator==0) {
                    fractionStr = fractionStr20[currSizeIndex]+" | "+fractionStr20[currSizeIndex+1]+" | "+fractionStr20[currSizeIndex+2];
                } else {
                    fractionStr = fractionStr6[currSizeIndex]+" | "+fractionStr6[currSizeIndex+1]+" | "+fractionStr6[currSizeIndex+2];
                }
                txtFraction.setText("Fraction : "+fractionStr);
            }
        } else if (state==5) {
            if (currSizeIndex+3 < decimalFraction.length) {
                Double fontSize1 = mAcuity.calculateFontSize(decimalFraction[currSizeIndex], distance, customDiagonal);
                Double fontSize2 = mAcuity.calculateFontSize(decimalFraction[currSizeIndex + 1], distance, customDiagonal);
                Double fontSize3 = mAcuity.calculateFontSize(decimalFraction[currSizeIndex + 2], distance, customDiagonal);
                Double fontSize4 = mAcuity.calculateFontSize(decimalFraction[currSizeIndex + 3], distance, customDiagonal);

                randomText();
                for (int i = 0; i < tumblingText1.size(); i++) {
                    tumblingText1.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize1));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText1.get(i), fontPath);
                }

                //randomText();
                for (int i = 0; i < tumblingText2.size(); i++) {
                    tumblingText2.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize2));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText2.get(i), fontPath);
                }

                //randomText();
                for (int i = 0; i < tumblingText3.size(); i++) {
                    tumblingText3.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize3));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText3.get(i), fontPath);
                }

                //randomText();
                for (int i = 0; i < tumblingText4.size(); i++) {
                    tumblingText4.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize4));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText4.get(i), fontPath);
                }
                totalHeightText += tumblingText1.get(0).getPaint().getFontMetrics().bottom - tumblingText1.get(0).getPaint().getFontMetrics().top;
                totalHeightText += tumblingText2.get(0).getPaint().getFontMetrics().bottom - tumblingText2.get(0).getPaint().getFontMetrics().top;
                totalHeightText += tumblingText3.get(0).getPaint().getFontMetrics().bottom - tumblingText3.get(0).getPaint().getFontMetrics().top;
                totalHeightText += tumblingText4.get(0).getPaint().getFontMetrics().bottom - tumblingText4.get(0).getPaint().getFontMetrics().top;
                totalHeightText += marginBottom * 4;

                String logMar = String.format("%.2f", mAcuity.calculateLogMAR(decimalFraction[currSizeIndex]))+" | "+
                        String.format("%.2f", mAcuity.calculateLogMAR(decimalFraction[currSizeIndex+1]))+" | "+
                        String.format("%.2f", mAcuity.calculateLogMAR(decimalFraction[currSizeIndex+2]))+" | "+
                        String.format("%.2f", mAcuity.calculateLogMAR(decimalFraction[currSizeIndex+3]));

                String decFrac = String.format("%.2f", decimalFraction[currSizeIndex])+"  "+
                        String.format("%.2f", decimalFraction[currSizeIndex+1])+" | "+
                        String.format("%.2f", decimalFraction[currSizeIndex+2])+" | "+
                        String.format("%.2f", decimalFraction[currSizeIndex+3]);
                txtlogMAR.setText("LogMAR : "+logMar);
                txtDecimal.setText("Decimal Acuity : "+decFrac);
                if (denominator==0) {
                    fractionStr = fractionStr20[currSizeIndex]+" | "+fractionStr20[currSizeIndex+1]+" | "+
                            fractionStr20[currSizeIndex+2]+" | "+fractionStr20[currSizeIndex+3];
                } else {
                    fractionStr = fractionStr6[currSizeIndex]+" | "+fractionStr6[currSizeIndex+1]+" | "+
                            fractionStr6[currSizeIndex+2]+" | "+fractionStr6[currSizeIndex+3];
                }
                txtFraction.setText("Fraction : "+fractionStr);
            }
        }
    }

    private void updateSize(Double fraction){
        Double fontSize = mAcuity.calculateFontSize(fraction, distance, customDiagonal);
        totalHeightText = 0;
        switch (ModelPresentasi){
            case 0:
                randomText();
                for(int i=0; i<tumblingText1.size(); i++){
                    tumblingText1.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText1.get(i), fontPath);
                }
                totalHeightText += tumblingText1.get(0).getPaint().getFontMetrics().bottom - tumblingText1.get(0).getPaint().getFontMetrics().top;
                break;
            case 1:
                randomText();
                for(int i=0; i<tumblingText1.size(); i++){
                    tumblingText1.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText1.get(i), fontPath);
                }

                //randomText();
                for(int i=0; i<tumblingText2.size(); i++){
                    tumblingText2.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText2.get(i), fontPath);
                }
                totalHeightText += tumblingText1.get(0).getPaint().getFontMetrics().bottom - tumblingText1.get(0).getPaint().getFontMetrics().top;
                totalHeightText = totalHeightText*2;
                totalHeightText += marginBottom;
                break;
            case 2:
                randomText();
                for(int i=0; i<tumblingText1.size(); i++){
                    tumblingText1.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText1.get(i), fontPath);
                }

                //randomText();
                for(int i=0; i<tumblingText2.size(); i++){
                    tumblingText2.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText2.get(i), fontPath);
                }

                //randomText();
                for(int i=0; i<tumblingText3.size(); i++){
                    tumblingText3.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText3.get(i), fontPath);
                }

                totalHeightText += tumblingText1.get(0).getPaint().getFontMetrics().bottom - tumblingText1.get(0).getPaint().getFontMetrics().top;
                totalHeightText = totalHeightText*3;
                totalHeightText += marginBottom * 2;
                break;
            case 3:
                updateSizeDecrease(3);
                break;
            case 4:
                randomText();
                for(int i=0; i<tumblingText1.size(); i++){
                    tumblingText1.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText1.get(i), fontPath);
                }

                //randomText();
                for(int i=0; i<tumblingText2.size(); i++){
                    tumblingText2.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText2.get(i), fontPath);
                }

                //randomText();
                for(int i=0; i<tumblingText3.size(); i++){
                    tumblingText3.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText3.get(i), fontPath);
                }

                //randomText();
                for(int i=0; i<tumblingText4.size(); i++){
                    tumblingText4.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText4.get(i), fontPath);
                }

                totalHeightText += tumblingText1.get(0).getPaint().getFontMetrics().bottom - tumblingText1.get(0).getPaint().getFontMetrics().top;
                totalHeightText = totalHeightText*4;
                totalHeightText += marginBottom * 3;
                break;
            case 5:
                updateSizeDecrease(5);
                break;
            case 6:
                randomText();
                for(int i=0; i<tumblingText1.size(); i++){
                    tumblingText1.get(i).setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                    CalligraphyUtils.applyFontToTextView(this, tumblingText1.get(i), fontPath);
                }
                totalHeightText += tumblingText1.get(0).getPaint().getFontMetrics().bottom - tumblingText1.get(0).getPaint().getFontMetrics().top;
                break;
        }
        txtlogMAR.setText("LogMAR : "+String.format("%.2f", mAcuity.calculateLogMAR(fraction)));
        txtDecimal.setText("Decimal Acuity : "+String.format("%.2f", fraction));
        if (denominator==0) {
            fractionStr = fractionStr20[currSizeIndex];
        } else {
            fractionStr = fractionStr6[currSizeIndex];
        }
        txtFraction.setText("Fraction : "+fractionStr);
    }

    private void cekLimit(){
        ArrayList<Double> newDecFraction = new ArrayList<>();
        ArrayList<String> newFracitionStr20 = new ArrayList<>();
        ArrayList<String> newFracitionStr6 = new ArrayList<>();
        layProgress.setVisibility(View.VISIBLE);
        for (int i=0; i<decimalFraction.length; i++) {
            if (ModelPresentasi==3 || ModelPresentasi==5){
                updateSizeDecrease(ModelPresentasi);
                currSizeIndex += 1;
            } else {
                updateSize(decimalFraction[i]);
            }
            if (totalHeightText <= container.getHeight()-(actionBarHeight*3)) {
                newDecFraction.add(decimalFraction[i]);
                newFracitionStr20.add(fractionStr20[i]);
                newFracitionStr6.add(fractionStr6[i]);
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
        if (ModelPresentasi==3 || ModelPresentasi==5) {
            updateSizeDecrease(ModelPresentasi);
        } else {
            updateSize(decimalFraction[currSizeIndex]);
        }
        layProgress.setVisibility(View.GONE);
    }

    private void swipeUp(){
        if (ModelPresentasi==3 || ModelPresentasi==5){
            if (currSizeIndex > 0) {
                currSizeIndex -= 1;
                updateSizeDecrease(ModelPresentasi);
            }
        } else {
            if (currSizeIndex > 0) {
                currSizeIndex -= 1;
                updateSize(decimalFraction[currSizeIndex]);
            }
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
        if (ModelPresentasi==3 || ModelPresentasi==5){
            switch (ModelPresentasi){
                case 3:
                    if (currSizeIndex+2 < decimalFraction.length - 1) {
                        currSizeIndex += 1;
                        updateSizeDecrease(3);
                    }
                    break;
                case 5:
                    if (currSizeIndex+3 < decimalFraction.length - 1) {
                        currSizeIndex += 1;
                        updateSizeDecrease(5);
                    }
                    break;
            }
        } else {
            if (currSizeIndex < decimalFraction.length - 1) {
                currSizeIndex += 1;
                updateSize(decimalFraction[currSizeIndex]);
            }
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
        if (ModelPresentasi!=6) {
            if (letterCount > 0) {
                letterCount -= 1;
                updateSize(decimalFraction[currSizeIndex]);
            }
        } else {
            updateSize(decimalFraction[currSizeIndex]);
        }

        if (letterCount==0){
            imgBack.setVisibility(View.GONE);
        } else {
            imgBack.setVisibility(View.VISIBLE);
        }
    }

    private void swipeRight(){
        int totalWidth = tumblingContainer1.getWidth();
        if (totalWidth+tumblingText1.get(0).getMeasuredWidth() <= container.getWidth()-(actionBarHeight*4)) {
            letterCount += 1;
            updateSize(decimalFraction[currSizeIndex]);
            if (letterCount == 0) {
                imgBack.setVisibility(View.GONE);
            } else {
                imgBack.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(this, "Not enough screen", Toast.LENGTH_SHORT).show();
        }
    }
}
