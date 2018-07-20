package com.alfanthariq.eyechart.exam;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.alfanthariq.eyechart.R;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

public class PediatricActivity extends AppCompatActivity {
    private SliderLayout mSlider;
    private Toolbar myToolbar = null;
    private SharedPreferences pref;
    private int height, width, disHeight;
    private LinearLayout.LayoutParams params;
    private SeekBar seekBar;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pediatric);

        mSlider = (SliderLayout)findViewById(R.id.slider);
        seekBar = (SeekBar) findViewById(R.id.SeekBarSize);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        disHeight = size.y;

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
                        seekBar.setMax(disHeight-200);
                        seekBar.setProgress(mSlider.getHeight());
                    }
                });

        TypedArray imgs = getResources().obtainTypedArray(R.array.pediatric);

        for(int i=0;i<imgs.length();i++){
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(imgs.getResourceId(i,0))
                    .setScaleType(BaseSliderView.ScaleType.CenterInside);

            mSlider.addSlider(textSliderView);
        }
        imgs.recycle();

        PagerIndicator indicator = (PagerIndicator) findViewById(R.id.custom_indicator);
        mSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        mSlider.setCustomIndicator(indicator);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.stopAutoCycle();
        mSlider.setCurrentPosition(0);

        getPrefs();
        setupToolbar();

        seekBar.incrementProgressBy(10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                height = mSlider.getHeight();
                width = mSlider.getWidth();
                params = new LinearLayout.LayoutParams(i+150, i);
                mSlider.setLayoutParams(params);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        params = new LinearLayout.LayoutParams(disHeight+150, disHeight-200);
        mSlider.setLayoutParams(params);
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
                height = mSlider.getHeight();
                if(height <= seekBar.getMax()) {
                    //params = new LinearLayout.LayoutParams(width + 10, height + 10);
                    //mSlider.setLayoutParams(params);
                    seekBar.setProgress(height + 10);
                }
                return true;
            case 20: // down
                height = mSlider.getHeight();
                //params = new LinearLayout.LayoutParams(width-10, height-10);
                //mSlider.setLayoutParams(params);
                seekBar.setProgress(height-10);
                return true;
            case 21: // left
                mSlider.movePrevPosition(true);
                return true;
            case 22: // right
                mSlider.moveNextPosition(true);
                return true;
            case KeyEvent.KEYCODE_BACK:
                finish();
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation_horizontal, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mSlider.stopAutoCycle();
        super.onStop();
    }

    private void getPrefs() {
        pref = getApplicationContext().getSharedPreferences("AppPref", MODE_PRIVATE);
        boolean showGuide = pref.getBoolean("showGuide", true);

        if(showGuide){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Instruction")
                    .setMessage(getResources().getString(R.string.guide_exam_general))
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    protected void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
