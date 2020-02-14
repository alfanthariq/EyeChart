package com.alfanthariq.eyechart.exam;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.alfanthariq.eyechart.R;
import com.alfanthariq.eyechart.helper.StripesView;

public class OknStripesActivity extends AppCompatActivity {
    private Toolbar myToolbar = null;
    private SharedPreferences pref;
    private StripesView stripeView;
    private int speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okn_stripes);

        getPrefs();
        setupToolbar();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 19: // up
                speed -= 50;
                if(speed<0) {
                    speed = 50;
                }
                //Log.d("TAG", "Speed : "+Integer.toString(speed));
                stripeView.setSpeed(speed);
                return true;
            case 20: // down
                speed += 50;
                //Log.d("TAG", "Speed : "+Integer.toString(speed));
                stripeView.setSpeed(speed);
                return true;
            case 21: // left
                if (stripeView.isDrawBall()) {
                    stripeView.hideBall();
                } else {
                    stripeView.showBall();
                }
                return true;
            case 22: // right
                if (stripeView.getColor()==Color.RED) {
                    stripeView.setColor(Color.BLACK);
                } else {
                    stripeView.setColor(Color.RED);
                }
                return true;
            case KeyEvent.KEYCODE_BACK:
                finish();
                return true;
            default:
                return true;
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

    private void getPrefs() {
        pref = getApplicationContext().getSharedPreferences("AppPref", MODE_PRIVATE);
        boolean showGuide = pref.getBoolean("showGuide", true);

        if(showGuide){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Instruction")
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.guide_okn))
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(OknStripesActivity.this);
                            builder.setTitle("Choose orientation")
                                    .setCancelable(false)
                                    .setMessage("Please choose orientation for OKN Stripe Test")
                                    .setPositiveButton("Horizontal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            stripeView = findViewById(R.id.stripeView);

                                            speed = 450;
                                            stripeView.setSpeed(speed);

                                            stripeView.setOrientation(1);
                                            stripeView.start();
                                        }
                                    })
                                    .setNegativeButton("Vertical", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            stripeView = findViewById(R.id.stripeView);

                                            speed = 450;
                                            stripeView.setSpeed(speed);

                                            stripeView.setOrientation(0);
                                            stripeView.start();
                                        }
                                    })
                                    .show();
                        }
                    })
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
