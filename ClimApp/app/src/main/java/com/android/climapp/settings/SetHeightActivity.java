package com.android.climapp.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.climapp.R;
import com.android.climapp.utils.Utils;

import static com.android.climapp.utils.SharedPreferencesConstants.APP_NAME;
import static com.android.climapp.utils.SharedPreferencesConstants.HEIGHT_INDEX;
import static com.android.climapp.utils.SharedPreferencesConstants.HEIGHT_VALUE;
import static com.android.climapp.utils.SharedPreferencesConstants.UNIT;

/**
 * Created by frksteenhoff on 21-01-2018.
 * Setting correct units for height according to chosen unit
 */

public class SetHeightActivity extends AppCompatActivity {

    private static SharedPreferences preferences;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_height);

        preferences = getSharedPreferences(APP_NAME, MODE_PRIVATE);
        utils = new Utils(preferences);
        int preferred_unit = preferences.getInt(UNIT,0);
        final String height_values[] = utils.showCorrectHeightValues(preferred_unit);

        // Additional text stating input unit
        final TextView heightUnit = findViewById(R.id.unit_text_height);
        setCorrectPickerUnit(heightUnit);

        //Number picker for height (feet, inches, meters) -- all should be converted to meters
        // before calculations are performed.
        NumberPicker np = findViewById(R.id.HeightPicker);

        //Populate NumberPicker values from String array values
        //Set the minimum/maximum value of NumberPicker
        np.setMinValue(0); //from array first value
        np.setMaxValue(height_values.length-1); //to array last value
        np.setDisplayedValues(height_values);
        np.setValue(preferences.getInt(HEIGHT_INDEX, 0));

        //Sets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Save index position of height value
                preferences.edit().putInt(HEIGHT_INDEX, newVal).apply();
                preferences.edit().putString(HEIGHT_VALUE, height_values[newVal]).apply();

                //Display the newly selected value from picker
                Toast.makeText(getApplicationContext(),
                        getString(R.string.height_updated) + " " + height_values[newVal] + " " +
                                heightUnit.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Setting up the correct units to be displayed as input unit
     * @param heightUnit the text to be displayed alongside the numberpicker
     */
    private void setCorrectPickerUnit(TextView heightUnit) {
        int unit = preferences.getInt(UNIT, 0);
        switch (unit) {
            case 1:
                heightUnit.setText(R.string.height_unit_uk);
                break;
            case 2:
                heightUnit.setText(R.string.height_unit_us);
                break;
            default:
                heightUnit.setText(R.string.height_unit_si);
                break;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent parentIntent = NavUtils.getParentActivityIntent(this);
                parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(parentIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

