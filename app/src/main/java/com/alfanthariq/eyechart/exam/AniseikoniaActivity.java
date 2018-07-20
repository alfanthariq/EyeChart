package com.alfanthariq.eyechart.exam;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alfanthariq.eyechart.R;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import java.math.BigDecimal;

public class AniseikoniaActivity extends AppCompatActivity {
    private Toolbar myToolbar = null;
    private SharedPreferences pref;
    private SeekBar seekBar;
    private LinearLayout wrapper;
    private ImageView imgGreen;
    private TextView txtHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniseikonia);

        seekBar = (SeekBar) findViewById(R.id.SeekBarSize);
        wrapper = findViewById(R.id.wrapper);
        imgGreen = findViewById(R.id.imgGreen);
        txtHasil = findViewById(R.id.txtHasil);

        getPrefs();
        setupToolbar();

        //txtHasil.setText(getResources().getString(R.string.ani_smaller));

        seekBar.setMax(120-60);
        seekBar.setProgress(85-60);
        seekBar.incrementProgressBy(1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Float f = (float) (i+60) / 100;
                imgGreen.setScaleX(round(f, 2));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
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
        } else if (id == R.id.menu_util){
            Float f = imgGreen.getScaleX();
            if (round(f, 2)<1f){
                txtHasil.setText(getResources().getString(R.string.ani_smaller));
            } else if (round(f, 2)>1f){
                txtHasil.setText(getResources().getString(R.string.ani_bigger));
            } else if (round(f, 2)==1f){
                txtHasil.setText(getResources().getString(R.string.ani_same));
            }
        } else if (id == R.id.menu_help) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Log.d("TAG", "Current size index : "+Integer.toString(currSizeIndex));
        switch (keyCode) {
            case 85:
                Float f = imgGreen.getScaleX();
                if (round(f, 2)<1f){
                    txtHasil.setText(getResources().getString(R.string.ani_smaller));
                } else if (round(f, 2)>1f){
                    txtHasil.setText(getResources().getString(R.string.ani_bigger));
                } else if (round(f, 2)==1f){
                    txtHasil.setText(getResources().getString(R.string.ani_same));
                }
                return true;
            case 19: // up
                seekBar.setProgress(seekBar.getProgress() + 1);
                return true;
            case 20: // down
                seekBar.setProgress(seekBar.getProgress() - 1);
                return true;
            case 21: // left
                if (wrapper.getRotation()==0f) {
                    Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation_vertical);
                    wrapper.startAnimation(startRotateAnimation);
                }
                return true;
            case 22: // right
                if (wrapper.getRotation()==0f) {
                    Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation_horizontal);
                    wrapper.startAnimation(startRotateAnimation);
                }
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
        boolean showGuide = pref.getBoolean("showGuide", true);

        if(showGuide){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Instruction")
                    .setMessage(getResources().getString(R.string.guide_ani))
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    protected void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        //myToolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
