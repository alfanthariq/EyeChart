package com.alfanthariq.eyechart.exam;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.alfanthariq.eyechart.R;
import com.alfanthariq.eyechart.helper.DuochromeView;

public class DuochromeActivity extends AppCompatActivity {
    private static DuochromeView mDuochromeView;
    private SeekBar seekBar;
    private SharedPreferences pref;
    private int currentSize;
    private Toolbar myToolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duochrome);

        mDuochromeView = (DuochromeView) findViewById(R.id.duochrome_view);
        seekBar = (SeekBar) findViewById(R.id.SeekBarSize);

        mDuochromeView.setAfterDraw(new DuochromeView.OnAfterDraw() {
            @Override
            public void onAfterDraw() {
                seekBar.setMax(mDuochromeView.getExternalMaxRadius()-mDuochromeView.getExternalMinRadius());
                seekBar.setProgress(mDuochromeView.getExternalMaxRadius()-mDuochromeView.getExternalMinRadius()-1);
            }
        });

        seekBar.incrementProgressBy(1);

        getPrefs();
        setupToolbar();
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
    public void onBackPressed(){
        //Log.d("Backpressed", "Klik");
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 19:
                if (this.mDuochromeView.increaseSize(5)) {
                    this.mDuochromeView.reDraw();
                    currentSize = mDuochromeView.getRadiusToDraw();
                    seekBar.setProgress(currentSize-mDuochromeView.getExternalMinRadius());
                    return true;
                }
                seekBar.setProgress(mDuochromeView.getExternalMaxRadius());
                //Toast.makeText(this, "Maximum size reached", Toast.LENGTH_SHORT).show();
                return true;
            case 20:
                if (this.mDuochromeView.decreaseSize(5)) {
                    this.mDuochromeView.reDraw();
                    currentSize = mDuochromeView.getRadiusToDraw();
                    seekBar.setProgress(currentSize-mDuochromeView.getExternalMinRadius());
                    return true;
                }
                seekBar.setProgress(0);
                //Toast.makeText(this, "Minimum size reached", Toast.LENGTH_SHORT).show();
                return true;
            case KeyEvent.KEYCODE_BACK :
                finish();
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
                    .setMessage(getResources().getString(R.string.guide_duochrome))
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
