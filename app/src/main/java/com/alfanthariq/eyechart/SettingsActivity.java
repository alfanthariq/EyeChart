package com.alfanthariq.eyechart;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.alfanthariq.eyechart.optotype.AcuityToolbox;

public class SettingsActivity extends AppCompatActivity {
    private Toolbar myToolbar = null;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private RadioGroup rgUnit, rgDenom;
    private TextView txtRes;
    private RadioButton rbMetric, rbImperial, rb20, rb6;
    private EditText inputDistance, inputNumber, inputContrast,
                     inputLetter, inputCol, inputRow, inputDiagonal, inputMarginR, inputMarginB;
    private Spinner spin_model;
    private AcuityToolbox mAcuity;
    private TextInputLayout layoutDistance, layoutCol, layoutRow, layoutDiagonal;
    private CheckBox cbInstruction;
    private Double diagonalMM;
    private int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAcuity = new AcuityToolbox(this);

        rgUnit = findViewById(R.id.rg_unit);
        rgDenom = findViewById(R.id.rg_denominator);

        rbMetric = findViewById(R.id.rb_metric);
        rbImperial = findViewById(R.id.rb_imperial);
        rb20 = findViewById(R.id.rb_20);
        rb6 = findViewById(R.id.rb_6);

        txtRes = findViewById(R.id.txtResolution);
        inputDistance = findViewById(R.id.input_distance);
        inputContrast = findViewById(R.id.input_contrast);
        inputNumber = findViewById(R.id.input_number);
        inputLetter = findViewById(R.id.input_letter);
        inputCol = findViewById(R.id.input_col);
        inputRow = findViewById(R.id.input_row);
        inputDiagonal = findViewById(R.id.input_diagonal);
        inputMarginR = findViewById(R.id.input_marginR);
        inputMarginB = findViewById(R.id.input_marginB);
        spin_model = (Spinner) findViewById(R.id.spin_model);
        layoutDistance = (TextInputLayout) findViewById(R.id.input_layout_distance);
        layoutCol = (TextInputLayout) findViewById(R.id.input_layout_col);
        layoutRow = (TextInputLayout) findViewById(R.id.input_layout_row);
        layoutDiagonal = (TextInputLayout) findViewById(R.id.input_layout_diagonal);
        cbInstruction = findViewById(R.id.cb_instruction);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        diagonalMM = mAcuity.convertInches2Millimeters(mAcuity.getScreenInch());

        getPrefs();
        setupToolbar();

        layoutCol.setVisibility(View.GONE);
        layoutRow.setVisibility(View.GONE);

        rgUnit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    // 0: metric; 1: imperial
                    case R.id.rb_metric:
                        editor = pref.edit();
                        editor.putInt("unit_system", 0);
                        editor.apply();
                        layoutDistance.setHint(getResources().getString(R.string.setting_distance)+" (cm)");
                        inputDistance.setText(String.format("%.2f", mAcuity.convertFeet2Centimeters(Double.valueOf(pref.getString("distance", "600.0")))));
                        layoutDiagonal.setHint(getResources().getString(R.string.setting_diagonal_size)+" (mm)");
                        inputDiagonal.setText(String.format("%.2f", mAcuity.convertInches2Millimeters(Double.valueOf(pref.getString("diagonal", Double.toString(mAcuity.getScreenInch()))))));
                        break;
                    case R.id.rb_imperial:
                        editor = pref.edit();
                        editor.putInt("unit_system", 1);
                        editor.apply();
                        layoutDistance.setHint(getResources().getString(R.string.setting_distance)+" (feet)");
                        inputDistance.setText(String.format("%.2f", mAcuity.convertCentimeters2Feet(Double.valueOf(pref.getString("distance", "600.0")))));
                        layoutDiagonal.setHint(getResources().getString(R.string.setting_diagonal_size)+" (inch)");
                        inputDiagonal.setText(String.format("%.2f", mAcuity.convertMillimeters2Inches(Double.valueOf(pref.getString("diagonal", Double.toString(diagonalMM))))));
                        break;
                }
            }
        });

        rgDenom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    // 0: metric; 1: imperial
                    case R.id.rb_20:
                        editor = pref.edit();
                        editor.putInt("denominator", 0);
                        editor.apply();
                        break;
                    case R.id.rb_6:
                        editor = pref.edit();
                        editor.putInt("denominator", 1);
                        editor.apply();
                        break;
                }
            }
        });

        cbInstruction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor = pref.edit();
                editor.putBoolean("showGuide", b);
                editor.apply();
            }
        });

        inputDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                editor = pref.edit();
                editor.putString("distance", editable.toString());
                editor.apply();
            }
        });

        inputContrast.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                editor = pref.edit();
                editor.putString("contrast", editable.toString());
                editor.apply();
            }
        });

        inputNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                editor = pref.edit();
                editor.putString("allowedNumber", editable.toString());
                editor.apply();
            }
        });

        inputLetter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                editor = pref.edit();
                editor.putString("allowedLetter", editable.toString());
                editor.apply();
            }
        });

        inputCol.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    editor = pref.edit();
                    editor.putInt("colCount", Integer.valueOf(editable.toString()));
                    editor.apply();
                }
            }
        });

        inputRow.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    editor = pref.edit();
                    editor.putInt("rowCount", Integer.valueOf(editable.toString()));
                    editor.apply();
                }
            }
        });

        inputDiagonal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                editor = pref.edit();
                editor.putString("diagonal", editable.toString());
                editor.apply();
            }
        });

        inputMarginR.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    editor = pref.edit();
                    editor.putInt("rightMarg", Integer.valueOf(editable.toString()));
                    editor.apply();
                }
            }
        });

        inputMarginB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    editor = pref.edit();
                    editor.putInt("bottomMarg", Integer.valueOf(editable.toString()));
                    editor.apply();
                }
            }
        });

        String[] arraySpinner = new String[] {
                "Single Line", "Double Same", "Triple Same", "Triple Decreasing",
                "Quadruple Same", "Quadruple Decreasing", "Single Letter",
                "Column"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_model.setAdapter(adapter);
        spin_model.setSelection(pref.getInt("model", 0));
        spin_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor = pref.edit();
                editor.putInt("model", i);
                editor.apply();
                if (i>6) {
                    layoutCol.setVisibility(View.VISIBLE);
                    layoutRow.setVisibility(View.VISIBLE);
                } else {
                    layoutCol.setVisibility(View.GONE);
                    layoutRow.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rbMetric.requestFocus();
    }

    private void getPrefs() {
        pref = getApplicationContext().getSharedPreferences("AppPref", MODE_PRIVATE);

        switch (pref.getInt("unit_system", 0)){
            case 0:
                rgUnit.check(R.id.rb_metric);
                layoutDistance.setHint(getResources().getString(R.string.setting_distance)+" (cm)");
                layoutDiagonal.setHint(getResources().getString(R.string.setting_diagonal_size)+" (mm)");
                break;
            case 1:
                rgUnit.check(R.id.rb_imperial);
                layoutDistance.setHint(getResources().getString(R.string.setting_distance)+" (feet)");
                layoutDiagonal.setHint(getResources().getString(R.string.setting_diagonal_size)+" (inch)");
                break;
        }

        switch (pref.getInt("denominator", 0)){
            case 0:
                rgDenom.check(R.id.rb_20);
                break;
            case 1:
                rgDenom.check(R.id.rb_6);
                break;
        }

        inputDistance.setText(pref.getString("distance", "600.0"));
        inputContrast.setText(pref.getString("contrast", "100"));
        inputNumber.setText(pref.getString("allowedNumber", "2,3,5,6,9"));
        inputLetter.setText(pref.getString("allowedLetter", "A,B,C,D,E,F,G,H,K,L,N,O,P,T,U,V,Z"));
        inputCol.setText(String.valueOf(pref.getInt("colCount", 1)));
        inputRow.setText(String.valueOf(pref.getInt("rowCount", 1)));
        inputDiagonal.setText(String.format("%.2f", Double.valueOf(pref.getString("diagonal", Double.toString(diagonalMM)))));
        inputMarginR.setText(String.valueOf(pref.getInt("rightMarg", 100)));
        inputMarginB.setText(String.valueOf(pref.getInt("bottomMarg", 100)));

        txtRes.setText("Screen resolution : "+Integer.toString(width)+"x"+Integer.toString(height));

        cbInstruction.setChecked(pref.getBoolean("showGuide", true));
    }

    protected void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.menu_main);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
