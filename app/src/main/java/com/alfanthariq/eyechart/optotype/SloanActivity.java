package com.alfanthariq.eyechart.optotype;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alfanthariq.eyechart.R;
import com.alfanthariq.eyechart.helper.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SloanActivity extends AppCompatActivity {
    private Toolbar myToolbar = null;
    private SharedPreferences pref;
    private String[] allowedLetter;
    private Double distance, customDiagonal;
    private LinearLayout container;
    private TextView
            txtFraction, txtDistance, txtDecimal, txtlogMAR,
            snellenText, snellenText2, snellenText3, snellenText4;
    private ImageView imgBack, imgNext, imgUp, imgDown;
    private int modelUnit, denominator, letterCount, currSizeIndex, actionBarHeight;
    private AcuityToolbox mAcuity;
    private String fractionStr;
    private Double ContrastLevel;
    private Random rand;
    private RelativeLayout layProgress;
    private ProgressBar progressBar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sloan);

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

        container.setOnTouchListener(new OnSwipeTouchListener(SloanActivity.this){
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
        rand = new Random();
        letterCount = 0;
        currSizeIndex = 0;

        snellenText = findViewById(R.id.snellenText);
        snellenText2 = findViewById(R.id.snellenText2);
        snellenText3 = findViewById(R.id.snellenText3);
        snellenText4 = findViewById(R.id.snellenText4);

        imgBack = findViewById(R.id.imgBack);
        imgNext = findViewById(R.id.imgNext);
        imgUp = findViewById(R.id.imgUp);
        imgDown = findViewById(R.id.imgDown);

        imgBack.setVisibility(View.GONE);
        imgUp.setVisibility(View.GONE);

        getPrefs();
        setupToolbar();

        allowedLetter = pref.getString("allowedLetter", "A,B,C,D,E,F,G,H,K,L,N,O,P,T,U,V,Z").split(",");

        if (ModelPresentasi==3 || ModelPresentasi==5) {
            updateSizeDecrease(ModelPresentasi);
        } else {
            updateSize(decimalFraction[currSizeIndex]);
        }

        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
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
        modelUnit = pref.getInt("unit_system", 0);
        denominator = pref.getInt("denominator", 0);
        ContrastLevel = Double.valueOf(pref.getString("contrast", "100"));
        boolean showGuide = pref.getBoolean("showGuide", true);

        if (showGuide){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Instruction")
                    .setMessage(" "+getResources().getString(R.string.guide_optotype))
                    .setPositiveButton("OK", null)
                    .show();
        }

        ModelPresentasi = pref.getInt("model", 0);

        switch (ModelPresentasi) {
            case 0:
                snellenText.setVisibility(View.VISIBLE);
                snellenText2.setVisibility(View.GONE);
                snellenText3.setVisibility(View.GONE);
                snellenText4.setVisibility(View.GONE);
                break;
            case 1:
                snellenText.setVisibility(View.VISIBLE);
                snellenText2.setVisibility(View.VISIBLE);
                snellenText3.setVisibility(View.GONE);
                snellenText4.setVisibility(View.GONE);
                break;
            case 2:
                snellenText.setVisibility(View.VISIBLE);
                snellenText2.setVisibility(View.VISIBLE);
                snellenText3.setVisibility(View.VISIBLE);
                snellenText4.setVisibility(View.GONE);
                break;
            case 3:
                snellenText.setVisibility(View.VISIBLE);
                snellenText2.setVisibility(View.VISIBLE);
                snellenText3.setVisibility(View.VISIBLE);
                snellenText4.setVisibility(View.GONE);
                break;
            case 4:
                snellenText.setVisibility(View.VISIBLE);
                snellenText2.setVisibility(View.VISIBLE);
                snellenText3.setVisibility(View.VISIBLE);
                snellenText4.setVisibility(View.VISIBLE);
                break;
            case 5:
                snellenText.setVisibility(View.VISIBLE);
                snellenText2.setVisibility(View.VISIBLE);
                snellenText3.setVisibility(View.VISIBLE);
                snellenText4.setVisibility(View.VISIBLE);
                break;
            case 6:
                snellenText.setVisibility(View.VISIBLE);
                snellenText2.setVisibility(View.GONE);
                snellenText3.setVisibility(View.GONE);
                snellenText4.setVisibility(View.GONE);
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

    private void updateSize(Double fraction){
        Double fontSize = mAcuity.calculateFontSize(fraction, distance, customDiagonal);
        switch (ModelPresentasi){
            case 0:
                randomText();
                snellenText.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                snellenText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel/100.0d).toString()));
                snellenText.measure(0, 0);
                break;
            case 1:
                randomText();
                snellenText.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                snellenText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel/100.0d).toString()));
                snellenText.measure(0, 0);

                randomText();
                snellenText2.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                snellenText2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText2.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel/100.0d).toString()));
                snellenText2.measure(0, 0);
                break;
            case 2:
                randomText();
                snellenText.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                snellenText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel/100.0d).toString()));
                snellenText.measure(0, 0);

                randomText();
                snellenText2.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                snellenText2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText2.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel/100.0d).toString()));
                snellenText2.measure(0, 0);

                randomText();
                snellenText3.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                snellenText3.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText3.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel/100.0d).toString()));
                snellenText3.measure(0, 0);
                break;
            case 3:
                // updateSizeDecrease(3);
                break;
            case 4:
                randomText();
                snellenText.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                snellenText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel/100.0d).toString()));
                snellenText.measure(0, 0);

                randomText();
                snellenText2.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                snellenText2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText2.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel/100.0d).toString()));
                snellenText2.measure(0, 0);

                randomText();
                snellenText3.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                snellenText3.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText3.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel/100.0d).toString()));
                snellenText3.measure(0, 0);

                randomText();
                snellenText4.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                snellenText4.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText4.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel/100.0d).toString()));
                snellenText4.measure(0, 0);
                break;
            case 5:
                // updateSizeDecrease(5);
                break;
            case 6:
                randomText();
                snellenText.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize));
                snellenText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel/100.0d).toString()));
                snellenText.measure(0, 0);
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

    private void updateSizeDecrease(int state){
        if (state==3) {
            if (currSizeIndex+2 < decimalFraction.length) {
                Double fontSize1 = mAcuity.calculateFontSize(decimalFraction[currSizeIndex], distance, customDiagonal);
                Double fontSize2 = mAcuity.calculateFontSize(decimalFraction[currSizeIndex + 1], distance, customDiagonal);
                Double fontSize3 = mAcuity.calculateFontSize(decimalFraction[currSizeIndex + 2], distance, customDiagonal);
                randomText();
                snellenText.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize1));
                snellenText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel / 100.0d).toString()));
                snellenText.measure(0, 0);

                randomText();
                snellenText2.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize2));
                snellenText2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText2.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel / 100.0d).toString()));
                snellenText2.measure(0, 0);

                randomText();
                snellenText3.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize3));
                snellenText3.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText3.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel / 100.0d).toString()));
                snellenText3.measure(0, 0);

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
                snellenText.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize1));
                snellenText.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel / 100.0d).toString()));
                snellenText.measure(0, 0);

                randomText();
                snellenText2.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize2));
                snellenText2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText2.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel / 100.0d).toString()));
                snellenText2.measure(0, 0);

                randomText();
                snellenText3.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize3));
                snellenText3.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText3.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel / 100.0d).toString()));
                snellenText3.measure(0, 0);

                randomText();
                snellenText4.setTextSize(TypedValue.COMPLEX_UNIT_MM, (float) Math.floor(fontSize4));
                snellenText4.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                snellenText4.setAlpha(Float.valueOf(Double.valueOf(ContrastLevel / 100.0d).toString()));
                snellenText4.measure(0, 0);

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

    private String generateRandomText(){
        String text = "";
        if (ModelPresentasi!=6) {
            for (int i = 0; i <= letterCount; i++) {
                if (!text.equals("")) {
                    text = text.concat(" ").concat(allowedLetter[rand.nextInt(allowedLetter.length)]);
                } else {
                    text = text.concat(allowedLetter[rand.nextInt(allowedLetter.length)]);
                }
            }
        } else {
            text = allowedLetter[rand.nextInt(allowedLetter.length)];
        }
        return text;
    }

    private void randomText(){
        String text;
        String res = "";
        switch (ModelPresentasi){
            case 0:
                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText.setText(res);
                break;
            case 1:
                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText.setText(res);

                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText2.setText(res);
                break;
            case 2:
                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText.setText(res);

                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText2.setText(res);

                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText3.setText(res);
                break;
            case 3:
                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText.setText(res);

                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText2.setText(res);

                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText3.setText(res);
                break;
            case 4:
                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText.setText(res);

                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText2.setText(res);

                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText3.setText(res);

                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText4.setText(res);
                break;
            case 5:
                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText.setText(res);

                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText2.setText(res);

                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText3.setText(res);

                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText4.setText(res);
                break;
            case 6:
                text = generateRandomText();
                res = text.replaceAll("\\s+$", "");
                snellenText.setText(res);
                break;
        }
    }

    private void cekLimit(){
        ArrayList<Double> newDecFraction = new ArrayList<>();
        ArrayList<String> newFracitionStr20 = new ArrayList<>();
        ArrayList<String> newFracitionStr6 = new ArrayList<>();
        int totalHeight = 0;
        layProgress.setVisibility(View.VISIBLE);
        for (int i=0; i<decimalFraction.length; i++) {
            if (ModelPresentasi==3 || ModelPresentasi==5){
                updateSizeDecrease(ModelPresentasi);
                currSizeIndex += 1;
            } else {
                updateSize(decimalFraction[i]);
            }
            switch (ModelPresentasi) {
                case 0:
                    totalHeight = snellenText.getMeasuredHeight();
                    break;
                case 1:
                    totalHeight = snellenText.getMeasuredHeight()+snellenText2.getMeasuredHeight();
                    break;
                case 2:
                    totalHeight = snellenText.getMeasuredHeight()+snellenText2.getMeasuredHeight()+snellenText3.getMeasuredHeight();
                    break;
                case 3:
                    totalHeight = snellenText.getMeasuredHeight()+snellenText2.getMeasuredHeight()+snellenText3.getMeasuredHeight();
                    break;
                case 4:
                    totalHeight = snellenText.getMeasuredHeight()+snellenText2.getMeasuredHeight()+
                            snellenText3.getMeasuredHeight()+snellenText4.getMeasuredHeight();
                    break;
                case 5:
                    totalHeight = snellenText.getMeasuredHeight()+snellenText2.getMeasuredHeight()+
                            snellenText3.getMeasuredHeight()+snellenText4.getMeasuredHeight();
                    break;
                case 6:
                    totalHeight = snellenText.getMeasuredHeight();
                    break;
            }
            if (totalHeight <= container.getHeight()-(actionBarHeight*3)) {
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
                randomText();
            }
        } else {
            randomText();
        }

        if (letterCount==0){
            imgBack.setVisibility(View.GONE);
        } else {
            imgBack.setVisibility(View.VISIBLE);
        }
    }

    private void swipeRight(){
        float plus = snellenText.getMeasuredWidth() / (letterCount+1);
        if (snellenText.getMeasuredWidth() + plus <= container.getWidth()-(actionBarHeight*4)) {
            letterCount += 1;
            randomText();
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
