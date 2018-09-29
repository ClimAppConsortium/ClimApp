package com.android.climapp.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.android.climapp.R;

/**
 * Created by frksteenhoff on 21-01-2018.
 * Setting whether the user is acclimatized to work environment or not.
 */

public class SetAcclimatizationActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Switch acclimatizationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_acclimatization);

        preferences = getSharedPreferences("ClimApp", MODE_PRIVATE);
        editor = preferences.edit();

        // Switch, setting initial value
        acclimatizationSwitch = (Switch) findViewById(R.id.acclimatization_switch);

        // Setting default user acclimatization
        acclimatizationSwitch.setChecked(preferences.getBoolean("Acclimatization",false));

        // TECHNICAL DEBT - same code existing here and in SettingsFragment
        acclimatizationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                acclimatizationSwitch.setChecked(isChecked);
                editor.putBoolean("Acclimatization", isChecked).apply();
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), getString(R.string.acclimatization_true), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.acclimatization_false), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}