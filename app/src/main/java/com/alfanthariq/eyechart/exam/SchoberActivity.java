package com.alfanthariq.eyechart.exam;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alfanthariq.eyechart.R;

public class SchoberActivity extends AppCompatActivity implements View.OnTouchListener {
    private RelativeLayout content;
    private ImageView plus, circle;
    private RelativeLayout.LayoutParams params;
    private Toolbar myToolbar = null;
    private SharedPreferences pref;
    private int marginLeft, marginTop;
    private TextView txtHasil;
    private int _xDelta;
    private int _yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schober);

        content = findViewById(R.id.content);
        plus = findViewById(R.id.imgPlus);
        circle = findViewById(R.id.imgCircle);
        txtHasil = findViewById(R.id.txtHasil);
        marginLeft = 100;
        marginTop = 50;

        getPrefs();
        setupToolbar();
        params = new RelativeLayout.LayoutParams(200, 200);
        params.setMargins(marginLeft, marginTop, 0, 0);
        plus.setLayoutParams(params);

        plus.setOnTouchListener(this);
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

    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;
                layoutParams.rightMargin = 0;
                layoutParams.bottomMargin = 0;
                marginLeft = layoutParams.leftMargin;
                marginTop = layoutParams.topMargin;
                view.setLayoutParams(layoutParams);
                break;
        }
        content.invalidate();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Log.d("TAG", "Current size index : "+Integer.toString(currSizeIndex));
        switch (keyCode) {
            case 85:
                String text = "";
                float circleCentreX=circle.getX() + circle.getWidth()  / 2;
                float circleCentreY=circle.getY() + circle.getHeight() / 2;

                float plusCentreX=plus.getX() + plus.getWidth()  / 2;
                float plusCentreY=plus.getY() + plus.getHeight() / 2;

                if (plusCentreX<circleCentreX){
                    text = getResources().getString(R.string.schober_lefter);
                    if (plusCentreY<circleCentreY){
                        text = text + " ; " +getResources().getString(R.string.schober_upper);
                    } else if (plusCentreY>circleCentreY){
                        text = text + " ; " +getResources().getString(R.string.schober_lower);
                    }
                } else if (plusCentreX>circleCentreX){
                    text = getResources().getString(R.string.schober_righter);
                    if (plusCentreY<circleCentreY){
                        text = text + " ; " +getResources().getString(R.string.schober_upper);
                    } else if (plusCentreY>circleCentreY){
                        text = text + " ; " +getResources().getString(R.string.schober_lower);
                    }
                } else if ((plusCentreX==circleCentreX) && (plusCentreY==circleCentreY)){
                    text = getResources().getString(R.string.schober_center);
                }

                txtHasil.setText(text);
                return true;
            case 19: // up
                marginTop -= 10;
                params.setMargins(marginLeft, marginTop, 0, 0);
                plus.setLayoutParams(params);
                return true;
            case 20: // down
                marginTop += 10;
                params.setMargins(marginLeft, marginTop, 0, 0);
                plus.setLayoutParams(params);
                return true;
            case 21: // left
                marginLeft -= 10;
                params.setMargins(marginLeft, marginTop, 0, 0);
                plus.setLayoutParams(params);
                return true;
            case 22: // right
                marginLeft += 10;
                params.setMargins(marginLeft, marginTop, 0, 0);
                plus.setLayoutParams(params);
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
                    .setMessage(getResources().getString(R.string.guide_schober))
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
