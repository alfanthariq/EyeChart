package com.alfanthariq.eyechart.information;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.alfanthariq.eyechart.MainActivity;
import com.alfanthariq.eyechart.R;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import net.rdrei.android.dirchooser.DirectoryChooserConfig;
import net.rdrei.android.dirchooser.DirectoryChooserFragment;

import java.io.File;

public class EyeAnatomyActivity extends AppCompatActivity implements
        DirectoryChooserFragment.OnFragmentInteractionListener {
    private SliderLayout mSlider;
    private Toolbar myToolbar = null;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private int height, width;
    private LinearLayout.LayoutParams params;
    private SeekBar seekBar;
    private String imgDir;
    private DirectoryChooserFragment mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye_anatomy);

        final DirectoryChooserConfig config = DirectoryChooserConfig.builder()
                .newDirectoryName("DialogSample")
                .build();
        mDialog = DirectoryChooserFragment.newInstance(config);

        mSlider = (SliderLayout)findViewById(R.id.slider);
        seekBar = (SeekBar) findViewById(R.id.SeekBarSize);

        getPrefs();
        setupToolbar();

        if (imgDir.equals("")) {
            TypedArray imgs = getResources().obtainTypedArray(R.array.anatomy);

            for (int i = 0; i < imgs.length(); i++) {
                DefaultSliderView textSliderView = new DefaultSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .image(imgs.getResourceId(i, 0))
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);

                mSlider.addSlider(textSliderView);
            }
        } else {
            File targetDirector = new File(imgDir);
            File[] files = targetDirector.listFiles();
            for (File file : files){
                DefaultSliderView textSliderView = new DefaultSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .image(file)
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);

                mSlider.addSlider(textSliderView);
            }
        }

        PagerIndicator indicator = (PagerIndicator) findViewById(R.id.custom_indicator);
        mSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        mSlider.setCustomIndicator(indicator);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.stopAutoCycle();
        mSlider.setCurrentPosition(0);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int disHeight = size.y;

        seekBar.setMax(disHeight);
        seekBar.setProgress(disHeight-270);
        seekBar.incrementProgressBy(10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                height = mSlider.getHeight();
                width = mSlider.getWidth();
                params = new LinearLayout.LayoutParams(i+200, i);
                mSlider.setLayoutParams(params);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
            case 85:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Setting")
                        .setMessage("Choose option for image folder")
                        .setPositiveButton("Default", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                editor = pref.edit();
                                editor.putString("anatomy_path", "");
                                editor.apply();

                                //Toast.makeText(EyeAnatomyActivity.this, "Open again for load new image", Toast.LENGTH_SHORT).show();
                                //finish();
                                mSlider.removeAllSliders();
                                TypedArray imgs = getResources().obtainTypedArray(R.array.anatomy);
                                for (int j = 0; j < imgs.length(); j++) {
                                    DefaultSliderView textSliderView = new DefaultSliderView(EyeAnatomyActivity.this);
                                    // initialize a SliderLayout
                                    textSliderView
                                            .image(imgs.getResourceId(j, 0))
                                            .setScaleType(BaseSliderView.ScaleType.CenterCrop);

                                    mSlider.addSlider(textSliderView);
                                }
                                //mSlider.setCurrentPosition(0, true);
                            }
                        })
                        .setNegativeButton("Choose folder", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mDialog.show(getFragmentManager(), null);
                                /*DialogProperties properties = new DialogProperties();
                                properties.selection_mode = DialogConfigs.SINGLE_MODE;
                                properties.selection_type = DialogConfigs.FILE_SELECT;
                                properties.root = new File(DialogConfigs.DEFAULT_DIR);
                                properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
                                properties.offset = new File(DialogConfigs.DEFAULT_DIR);
                                properties.extensions = null;
                                FilePickerDialog dialog = new FilePickerDialog(EyeAnatomyActivity.this,properties);
                                dialog.setTitle("Select a Directory");
                                dialog.setDialogSelectionListener(new DialogSelectionListener() {
                                    @Override
                                    public void onSelectedFilePaths(String[] files) {
                                        //files is the array of the paths of files selected by the Application User.
                                        File file = new File(files[0]);
                                        String dir = file.getParent();
                                        editor = pref.edit();
                                        editor.putString("anatomy_path", dir);
                                        editor.apply();

                                        Toast.makeText(EyeAnatomyActivity.this, "Open again for load new image", Toast.LENGTH_SHORT).show();
                                        finish();

                                    }
                                });
                                dialog.show();*/
                            }
                        })
                        .show();
                return true;
            case 19: // up
                height = mSlider.getHeight();
                if(height <= seekBar.getMax()) {
                    seekBar.setProgress(height + 10);
                }
                return true;
            case 20: // down
                height = mSlider.getHeight();
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
    public void onSelectDirectory(@NonNull String path) {
        editor = pref.edit();
        editor.putString("anatomy_path", path);
        editor.apply();

        mSlider.removeAllSliders();

        File targetDirector = new File(path);
        if (targetDirector.isDirectory()) {
            File[] files = targetDirector.listFiles();
            for (File file : files) {
                DefaultSliderView textSliderView = new DefaultSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .image(file)
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);

                mSlider.addSlider(textSliderView);
            }
        } else {
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
        }
        mDialog.dismiss();
        //mSlider.setCurrentPosition(0, true);
    }

    @Override
    public void onCancelChooser() {
        mDialog.dismiss();
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
                    .setMessage(getResources().getString(R.string.guide_anatomy))
                    .setPositiveButton("OK", null)
                    .show();
        }

        imgDir = pref.getString("anatomy_path", "");
    }

    protected void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
