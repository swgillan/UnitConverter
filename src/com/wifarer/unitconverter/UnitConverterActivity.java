package com.wifarer.unitconverter;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class UnitConverterActivity extends Activity {
	private static double[] sSpinnerValues = new double[] {1000, 1, 0.001, 1609.344, 0.3048, 0.0254};
	private double fromFactor;
	private double toFactor;
	private TextView toValue;
	private EditText fromValue;
	private Spinner fromUnits;
	private Spinner toUnits;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        final Button swapButton = (Button)findViewById(R.id.swapButton);
        swapButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	int tempPosition = UnitConverterActivity.this.fromUnits.getSelectedItemPosition();
            	UnitConverterActivity.this.fromUnits.setSelection(UnitConverterActivity.this.toUnits.getSelectedItemPosition());
            	UnitConverterActivity.this.toUnits.setSelection(tempPosition);
            }
        });
        
        //Setup the spinner items for distance units.
        ArrayList<String> spinnerItems = new ArrayList<String>();
        spinnerItems.add("Kilometers, (km)");
        spinnerItems.add("Meters, (m)");
        spinnerItems.add("Milimeters, (mm)");
        spinnerItems.add("Miles, (mi)");
        spinnerItems.add("Feet, (ft)");
        spinnerItems.add("Inches, (in)");
        
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItems);
        
        fromUnits = (Spinner)findViewById(R.id.fromUnits);
        fromUnits.setAdapter(spinnerAdapter);
        fromUnits.setOnItemSelectedListener(new MyOnItemSelectedListener());
        toUnits = (Spinner)findViewById(R.id.toUnits);
        toUnits.setAdapter(spinnerAdapter);
        toUnits.setOnItemSelectedListener(new MyOnItemSelectedListener());
        //Setup the edit text
        fromValue = (EditText) findViewById(R.id.fromValue);
        //Set a default value of 1 on startup
        fromValue.setText("1");
        fromValue.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                  // Perform action on key press
                		UnitConverterActivity.this.performConversion(Double.parseDouble(fromValue.getText().toString()));
                  return true;
                }
                return false;
            }
        });
        toValue = (TextView) findViewById(R.id.toValue);
        toValue.setText("0.0");
        
        toUnits.setSelection(2);
    }
    
    private void performConversion(double parseDouble) {
		double convertedValue = parseDouble*fromFactor/toFactor;
		toValue.setText(""+convertedValue);
    	
	}
    
    public class MyOnItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
        	if ( parent.getId() == R.id.fromUnits ) {
        		fromFactor = sSpinnerValues[pos];
        	} else {
        		toFactor = sSpinnerValues[pos];
        	}
        	UnitConverterActivity.this.performConversion(Double.parseDouble(fromValue.getText().toString()));
          
        }

        public void onNothingSelected(AdapterView<?> parent) {
          // Do nothing.
        }
    }
}