package com.alfanthariq.eyechart.information;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.alfanthariq.eyechart.R;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import net.rdrei.android.dirchooser.DirectoryChooserConfig;
import net.rdrei.android.dirchooser.DirectoryChooserFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.getExternalStorageDirectory;

public class EdukasiActivity extends AppCompatActivity implements
        DirectoryChooserFragment.OnFragmentInteractionListener {
    private SliderLayout mSlider;
    private Toolbar myToolbar = null;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private int height, width, currIndex;
    private LinearLayout.LayoutParams params;
    private SeekBar seekBar;
    private String rootPath;
    private ListView list;
    private DirectoryChooserFragment mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edukasi);

        final DirectoryChooserConfig config = DirectoryChooserConfig.builder()
                .newDirectoryName("DialogSample")
                .build();
        mDialog = DirectoryChooserFragment.newInstance(config);

        mSlider = (SliderLayout)findViewById(R.id.slider);
        seekBar = (SeekBar) findViewById(R.id.SeekBarSize);
        list = findViewById(R.id.menu_list);

        getPrefs();
        setupToolbar();

        List<String> menus = new ArrayList<>();
        menus.add("Eye Disorders");
        menus.add("Astigmatic");
        menus.add("Bleparitis");
        menus.add("Calation");
        menus.add("Conjunctivitis");
        menus.add("Conjunctivitis Alergica");
        menus.add("Glaucoma");
        menus.add("Iritis");
        menus.add("Katarak");
        menus.add("Kelainan Refraksi");
        menus.add("Keratitis dan Ulcus Cornea");
        menus.add("Kontak lens");
        menus.add("Pterygium");
        menus.add("Retinal Disease");
        menus.add("Strabismus");
        menus.add("Lain-Lain");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                menus
        );

        loadImage(rootPath+"/0/");
        currIndex = 0;

        PagerIndicator indicator = (PagerIndicator) findViewById(R.id.custom_indicator);
        mSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        mSlider.setCustomIndicator(indicator);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.stopAutoCycle();

        list.setAdapter(adapter);
        list.setItemsCanFocus(true);
        list.requestFocus();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                loadImage(rootPath+"/"+Integer.toString(i)+"/");
                currIndex = i;
            }
        });

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
    public void onSelectDirectory(@NonNull String path) {
        editor = pref.edit();
        editor.putString("edukasi_path", path);
        editor.apply();
        rootPath = path;

        loadImage(path+"/"+Integer.toString(currIndex)+"/");
        mDialog.dismiss();
        //mSlider.setCurrentPosition(0, true);
    }

    @Override
    public void onCancelChooser() {
        mDialog.dismiss();
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
                mDialog.show(getFragmentManager(), null);
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
                if (seekBar.isFocused()) {
                    list.requestFocus();
                } else if (list.isFocused()) {
                    finish();
                }
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
                    .setMessage(getResources().getString(R.string.guide_edu))
                    .setPositiveButton("OK", null)
                    .show();
        }

        rootPath = pref.getString("edukasi_path", getExternalStorageDirectory().getAbsolutePath()+"/edukasi/");
    }

    protected void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadImage(String path){
        mSlider.removeAllSliders();
        mSlider.stopAutoCycle();
        File f = new File(path);
        if(f.isDirectory()) {
            File targetDirector = new File(path);
            File[] files = targetDirector.listFiles();
            for (File file : files) {
                DefaultSliderView textSliderView = new DefaultSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .image(file)
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);

                mSlider.addSlider(textSliderView);
            }
            seekBar.requestFocus();
            mSlider.setCurrentPosition(files.length-1, false);
            mSlider.moveNextPosition(true);
        } else {
            Toast.makeText(this, "Image directory not found", Toast.LENGTH_SHORT).show();
        }
    }
}
