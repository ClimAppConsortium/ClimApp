package com.example.android.climapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by frksteenhoff on 21-01-2018.
 * Setting the user's weight
 */

public class SetWeightActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private NumberPicker np;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_weight);

        preferences = getSharedPreferences("ClimApp", MODE_PRIVATE);

        // Set correct text indicating unit on UI
        TextView weightUnit = findViewById(R.id.unit_text_weight);
        setCorrectPickerUnit(weightUnit);

        //Number picker for age, set initial value
        np = findViewById(R.id.WeightPicker);

        // Set range of values to choose from
        int preferred_unit = preferences.getInt("Unit",0);
        showCorrectWeightValues(preferred_unit);

        // Set value based on user input
        np.setValue(preferences.getInt("Weight", 0));
        Log.v("HESTE", preferences.getInt("Weight", 0)+" heer");

        //Sets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                preferences.edit().putInt("Weight", newVal).apply();
                Log.v("HESTE", newVal+"");

                //Display the newly selected value from picker
                Toast.makeText(getApplicationContext(), getString(R.string.weight_updated) + " " + newVal, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Populate NumberPicker values from String array values
     * Set the minimum/maximum value of NumberPicker
     * @param preferred_unit the integer value representing the preferred unit in which to
     *                       measure a person's weight, in kg, punds or stones
     */
    private void showCorrectWeightValues(int preferred_unit) {
        // If unit chosen is "UK" - use stones
        if (preferred_unit == 1) {
            np.setMinValue(85); //from array first value
            np.setMaxValue(775); //to array last value

            // If unit chosen is "US", use pounds
        } else if (preferred_unit == 2) {
            np.setMinValue(20); //from array first value
            np.setMaxValue(175); //to array last value

            // Default or if unit is "SI", use kilograms
        } else {
            np.setMinValue(40); //from array first value
            np.setMaxValue(350); //to array last value
        }
    }

    /**
     * Setting up the correct units to be displayed as input unit
     * @param unitText the text to be displayed alongside the numberpicker
     */
    private void setCorrectPickerUnit(TextView unitText) {
        int unit = preferences.getInt("Unit", 0);
        switch (unit) {
            case 1:
                unitText.setText(R.string.weight_unit_uk);
                break;
            case 2:
                unitText.setText(R.string.weight_unit_us);
                break;
            default:
                unitText.setText(R.string.weight_unit_si);
                break;
        }
    }
}
