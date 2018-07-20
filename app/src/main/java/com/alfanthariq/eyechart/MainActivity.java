package com.alfanthariq.eyechart;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alfanthariq.eyechart.exam.AmslerActivity;
import com.alfanthariq.eyechart.exam.AniseikoniaActivity;
import com.alfanthariq.eyechart.exam.AxisActivity;
import com.alfanthariq.eyechart.exam.DotTestActivity;
import com.alfanthariq.eyechart.exam.DuochromeActivity;
import com.alfanthariq.eyechart.exam.HirschbergActivity;
import com.alfanthariq.eyechart.exam.OknStripesActivity;
import com.alfanthariq.eyechart.exam.RedGreenActivity;
import com.alfanthariq.eyechart.exam.SchoberActivity;
import com.alfanthariq.eyechart.information.EdukasiActivity;
import com.alfanthariq.eyechart.information.EyeAnatomyActivity;
import com.alfanthariq.eyechart.optotype.ColumnActivity;
import com.alfanthariq.eyechart.exam.IshiharaActivity;
import com.alfanthariq.eyechart.optotype.KindergartenActivity;
import com.alfanthariq.eyechart.optotype.LandoltCActivity;
import com.alfanthariq.eyechart.optotype.NumberActivity;
import com.alfanthariq.eyechart.optotype.SnellenActivity;
import com.alfanthariq.eyechart.unused.SnellenChartActivity;
import com.alfanthariq.eyechart.optotype.TumblingEChartActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.os.Environment.getExternalStorageDirectory;


public class MainActivity extends AppCompatActivity {
    private Toolbar myToolbar = null;
    private Drawer mDrawer = null;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private int model;
    private TextView txtToolbarTitle;
    private RelativeLayout layProgress;
    private TextView txtInfo;
    private final Handler handler = new Handler();
    private Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layProgress = findViewById(R.id.layProgress);
        txtInfo = findViewById(R.id.txtInfo);

        //layProgress.setVisibility(View.GONE);

        r = new Runnable() {
            public void run() {
                File dir = new File(getExternalStorageDirectory().getAbsolutePath()+"/edukasi/");
                if (!dir.exists()){
                    //Toast.makeText(this, "Copying ...", Toast.LENGTH_SHORT).show();
                    try {
                        layProgress.setVisibility(View.VISIBLE);
                        copyDirorfileFromAssetManager("edukasi", "edukasi");
                        layProgress.setVisibility(View.GONE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    txtInfo.setVisibility(View.GONE);
                    layProgress.setVisibility(View.GONE);
                }
            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                // You can show your dialog message here but instead I am
                // showing the grant permission dialog box
                ActivityCompat.requestPermissions(this, new String[] {
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        10);
            }
            else{
                //Requesting permission
                ActivityCompat.requestPermissions(this, new String[] {
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        10);
            }
        } else {
            handler.postDelayed(r, 1000);
        }

        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        getPrefs();
        //setupToolbar();
        //setupNavDrawer(savedInstanceState);
        setupMenu();
        txtToolbarTitle = findViewById(R.id.toolbar_title);
        txtToolbarTitle.setText(getResources().getString(R.string.app_name)+" v"+versionName);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 10) {
            // for each permission check if the user granted/denied them
            // you may want to group the rationale in a single dialog,
            // this is just an example
            for (int i = 0, len = permissions.length; i < len; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    handler.postDelayed(r, 1000);
                }
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        model = pref.getInt("model", 0);
    }

    private void setupMenu(){
        //ArrayList<Button>[] menu = new ArrayList[20];
        Button menu;
        for(int i=1; i<21; i++){
            switch (i){
                case 1:
                    menu = findViewById(R.id.menu1);
                    break;
                case 2:
                    menu = findViewById(R.id.menu2);
                    break;
                case 3:
                    menu = findViewById(R.id.menu3);
                    break;
                case 4:
                    menu = findViewById(R.id.menu4);
                    break;
                /*case 5:
                    menu = findViewById(R.id.menu5);
                    break;*/
                case 6:
                    menu = findViewById(R.id.menu6);
                    break;
                case 7:
                    menu = findViewById(R.id.menu7);
                    break;
                case 8:
                    menu = findViewById(R.id.menu8);
                    break;
                case 9:
                    menu = findViewById(R.id.menu9);
                    break;
                case 10:
                    menu = findViewById(R.id.menu10);
                    break;
                /*case 11:
                    menu = findViewById(R.id.menu11);
                    break;*/
                case 12:
                    menu = findViewById(R.id.menu12);
                    break;
                case 13:
                    menu = findViewById(R.id.menu13);
                    break;
                case 14:
                    menu = findViewById(R.id.menu14);
                    break;
                /*case 15:
                    menu = findViewById(R.id.menu15);
                    break;*/
                case 16:
                    menu = findViewById(R.id.menu16);
                    break;
                case 17:
                    menu = findViewById(R.id.menu17);
                    break;
                case 18:
                    menu = findViewById(R.id.menu18);
                    break;
                case 19:
                    menu = findViewById(R.id.menu19);
                    break;
                case 20:
                    menu = findViewById(R.id.menu20);
                    break;
                default: menu = findViewById(R.id.menu1);
            }
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = null;
                    switch (view.getId()){
                        case R.id.menu1:
                            if (model<7) {
                                intent = new Intent(MainActivity.this, SnellenActivity.class);
                            } else {
                                intent = new Intent(MainActivity.this, ColumnActivity.class);
                                intent.putExtra("tipeChart", 1);
                            }
                            break;
                        case R.id.menu2:
                            if (model<7) {
                                intent = new Intent(MainActivity.this, TumblingEChartActivity.class);
                            } else {
                                intent = new Intent(MainActivity.this, ColumnActivity.class);
                                intent.putExtra("tipeChart", 2);
                            }
                            break;
                        case R.id.menu3:
                            if (model<7) {
                                intent = new Intent(MainActivity.this, LandoltCActivity.class);
                            } else {
                                intent = new Intent(MainActivity.this, ColumnActivity.class);
                                intent.putExtra("tipeChart", 3);
                            }
                            break;
                        case R.id.menu4:
                            if (model<7) {
                                intent = new Intent(MainActivity.this, KindergartenActivity.class);
                            } else {
                                intent = new Intent(MainActivity.this, ColumnActivity.class);
                                intent.putExtra("tipeChart", 4);
                            }
                            break;
                        /*case R.id.menu5:
                            if (model<7) {
                                intent = new Intent(MainActivity.this, SloanActivity.class);
                            } else {
                                intent = new Intent(MainActivity.this, ColumnActivity.class);
                                intent.putExtra("tipeChart", 5);
                            }
                            break;*/
                        case R.id.menu6:
                            if (model<7) {
                                intent = new Intent(MainActivity.this, NumberActivity.class);
                            } else {
                                intent = new Intent(MainActivity.this, ColumnActivity.class);
                                intent.putExtra("tipeChart", 6);
                            }
                            break;
                        case R.id.menu7:
                            intent = new Intent(MainActivity.this, IshiharaActivity.class);
                            break;
                        case R.id.menu8:
                            intent = new Intent(MainActivity.this, DotTestActivity.class);
                            break;
                        case R.id.menu9:
                            intent = new Intent(MainActivity.this, RedGreenActivity.class);
                            break;
                        case R.id.menu10:
                            intent = new Intent(MainActivity.this, AmslerActivity.class);
                            break;
                        /*case R.id.menu11:
                            intent = new Intent(MainActivity.this, PediatricActivity.class);
                            break;*/
                        case R.id.menu12:
                            intent = new Intent(MainActivity.this, AxisActivity.class);
                            break;
                        case R.id.menu13:
                            intent = new Intent(MainActivity.this, DuochromeActivity.class);
                            break;
                        case R.id.menu14:
                            intent = new Intent(MainActivity.this, SchoberActivity.class);
                            break;
                        /*case R.id.menu15:
                            intent = new Intent(MainActivity.this, OknStripesActivity.class);
                            break;*/
                        case R.id.menu16:
                            intent = new Intent(MainActivity.this, HirschbergActivity.class);
                            break;
                        case R.id.menu17:
                            intent = new Intent(MainActivity.this, AniseikoniaActivity.class);
                            break;
                        case R.id.menu18:
                            intent = new Intent(MainActivity.this, EyeAnatomyActivity.class);
                            break;
                        case R.id.menu19:
                            intent = new Intent(MainActivity.this, EdukasiActivity.class);
                            break;
                        case R.id.menu20:
                            intent = new Intent(MainActivity.this, SettingsActivity.class);
                            break;
                    }
                    if (intent != null) {
                        MainActivity.this.startActivity(intent);
                    }
                }
            });
        }
    }

    private void getPrefs() {
        pref = getApplicationContext().getSharedPreferences("AppPref", MODE_PRIVATE);
        model = pref.getInt("model", 0);
    }

    protected void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupNavDrawer(Bundle savedInstanceState){
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)))
                .withTextColor(Color.WHITE)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(
                        new ProfileDrawerItem().withName("PT. Gemahripah Anugrah Lestari").withEmail("email@gmail.com")
                )
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(myToolbar)
                .withHasStableIds(true)
                .withTranslucentStatusBar(false)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.menu_dashboard).
                                withIdentifier(0).withSelectable(false),
                        new SectionDrawerItem().withName("Optotype"),
                        new PrimaryDrawerItem().withName(R.string.menu1).
                                withIdentifier(1).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu2).
                                withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu3).
                                withIdentifier(3).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu4).
                                withIdentifier(4).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu5).
                                withIdentifier(5).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu6).
                                withIdentifier(6).withSelectable(false),
                        new SectionDrawerItem().withName("Eye Exam"),
                        new PrimaryDrawerItem().withName(R.string.menu7).
                                withIdentifier(7).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu8).
                                withIdentifier(8).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu9).
                                withIdentifier(9).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu10).
                                withIdentifier(10).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu11).
                                withIdentifier(11).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu12).
                                withIdentifier(12).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu13).
                                withIdentifier(13).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu14).
                                withIdentifier(14).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu15).
                                withIdentifier(15).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu16).
                                withIdentifier(16).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu17).
                                withIdentifier(17).withSelectable(false),
                        new SectionDrawerItem().withName("Information"),
                        new PrimaryDrawerItem().withName(R.string.menu18).
                                withIdentifier(18).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.menu19).
                                withIdentifier(19).withSelectable(false),
                        new SectionDrawerItem().withName("More"),
                        new PrimaryDrawerItem().withName(R.string.menu20).
                                withIdentifier(20).withSelectable(false)
                ) // add the items we want to use with our Drawer
                .addStickyDrawerItems(new PrimaryDrawerItem().withName("version 1.0 © 2018").withIdentifier(7))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) { // Snallen
                                intent = new Intent(MainActivity.this, SnellenActivity.class);
                            } else
                            if (drawerItem.getIdentifier() == 2) { // Tumbling E
                                intent = new Intent(MainActivity.this, TumblingEChartActivity.class);
                            } else
                            if (drawerItem.getIdentifier() == 3) {  //About

                            } else
                            if (drawerItem.getIdentifier() == 4) {  //LEA Symbols
                                intent = new Intent(MainActivity.this, KindergartenActivity.class);
                            } else
                            if (drawerItem.getIdentifier() == 5) {  //Share

                            } else
                            if (drawerItem.getIdentifier() == 6) {  //Rate us

                            } else
                            if (drawerItem.getIdentifier() == 7) {  //Rate us

                            } else
                            if (drawerItem.getIdentifier() == 8) {  //Rate us

                            } else
                            if (drawerItem.getIdentifier() == 9) {  //Rate us

                            } else
                            if (drawerItem.getIdentifier() == 10) {  //Rate us

                            } else
                            if (drawerItem.getIdentifier() == 11) {  //Rate us

                            } else
                            if (drawerItem.getIdentifier() == 12) {  //Rate us

                            } else
                            if (drawerItem.getIdentifier() == 13) {  //Rate us

                            } else
                            if (drawerItem.getIdentifier() == 14) {  //Rate us

                            } else
                            if (drawerItem.getIdentifier() == 15) {  //Rate us

                            } else
                            if (drawerItem.getIdentifier() == 16) {  //Rate us

                            } else
                            if (drawerItem.getIdentifier() == 17) {  //Rate us

                            } else
                            if (drawerItem.getIdentifier() == 18) {  //Rate us

                            } else
                            if (drawerItem.getIdentifier() == 19) {  //Rate us

                            } else
                            if (drawerItem.getIdentifier() == 20) {  //Setting
                                intent = new Intent(MainActivity.this, SettingsActivity.class);
                            }
                            if (intent != null) {
                                MainActivity.this.startActivity(intent);
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerUntilDraggedOpened(true)
                .build();

        mDrawer.setSelection(0);
    }

    public String copyDirorfileFromAssetManager(String arg_assetDir, String arg_destinationDir) throws IOException
    {
        File sd_path = Environment.getExternalStorageDirectory();
        String dest_dir_path = sd_path + addLeadingSlash(arg_destinationDir);
        File dest_dir = new File(dest_dir_path);

        createDir(dest_dir);

        AssetManager asset_manager = getApplicationContext().getAssets();
        String[] files = asset_manager.list(arg_assetDir);

        for (int i = 0; i < files.length; i++)
        {

            String abs_asset_file_path = addTrailingSlash(arg_assetDir) + files[i];
            String sub_files[] = asset_manager.list(abs_asset_file_path);

            if (sub_files.length == 0)
            {
                // It is a file
                String dest_file_path = addTrailingSlash(dest_dir_path) + files[i];
                copyAssetFile(abs_asset_file_path, dest_file_path);
            } else
            {
                // It is a sub directory
                copyDirorfileFromAssetManager(abs_asset_file_path, addTrailingSlash(arg_destinationDir) + files[i]);
            }
        }

        return dest_dir_path;
    }


    public void copyAssetFile(String assetFilePath, String destinationFilePath) throws IOException
    {
        InputStream in = getApplicationContext().getAssets().open(assetFilePath);
        OutputStream out = new FileOutputStream(destinationFilePath);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0)
            out.write(buf, 0, len);
        in.close();
        out.close();
    }

    public String addTrailingSlash(String path)
    {
        if (path.charAt(path.length() - 1) != '/')
        {
            path += "/";
        }
        return path;
    }

    public String addLeadingSlash(String path)
    {
        if (path.charAt(0) != '/')
        {
            path = "/" + path;
        }
        return path;
    }

    public void createDir(File dir) throws IOException
    {
        if (dir.exists())
        {
            if (!dir.isDirectory())
            {
                throw new IOException("Can't create directory, a file is in the way");
            }
        } else
        {
            dir.mkdirs();
            if (!dir.isDirectory())
            {
                throw new IOException("Unable to create directory");
            }
        }
    }
}


/*
<Button
                            android:id="@+id/menu15"
                            android:layout_width="0dp"
                            android:layout_weight="1"
                            android:layout_height="80dp"
                            android:layout_gravity="fill"
                            android:textColor="@drawable/bright_text_dark_focused"
                            android:background="@drawable/button_bg"
                            android:text="@string/menu15"
                            android:layout_marginRight="5dp"
                            android:textAllCaps="false"
                            android:layout_marginBottom="10dp"/>
 */