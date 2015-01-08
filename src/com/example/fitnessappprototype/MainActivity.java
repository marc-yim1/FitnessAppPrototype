package com.example.fitnessappprototype;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;





import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText editTexttime1,editTextdistance1;
	Button histo,edit;
	TextView distc,timers;
	DBController controller = new DBController(this);
	 public int pkv2, pkv,pkvh,pkvm,pkvs;
	 NumberPicker np,np2,nph,npm,nps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		timers = (TextView) findViewById(R.id.timetxt);
		distc = (TextView) findViewById(R.id.txtdist);
		histo = (Button) findViewById(R.id.history1);
		edit = (Button) findViewById(R.id.edit1);
		this.setTitle("Add Work-out Data");
		histo.setOnClickListener(new View.OnClickListener() {
			   public void onClick(View v) {
				 Intent intent = new Intent(MainActivity.this, Histo.class);
					startActivity(intent);
				}});	
		edit.setOnClickListener(new View.OnClickListener() {
			   public void onClick(View v) {
				 Intent intent = new Intent(MainActivity.this, select_ses.class);
					startActivity(intent);
				}});	
		 np = (NumberPicker)findViewById(R.id.numberPicker1);
		 np2 = (NumberPicker)findViewById(R.id.numberPicker2);
		 nph = (NumberPicker)findViewById(R.id.numberPicker3);
		 npm = (NumberPicker)findViewById(R.id.numberPicker4);
		 nps = (NumberPicker)findViewById(R.id.numberPicker5);
		 nph.setMinValue(0);
		 nph.setMaxValue(24);
		 nph.setFormatter(new NumberPicker.Formatter() {
		        @Override
		        public String format(int i) {
		            return String.format("%02d", i);
		        }
		    });
		 npm.setFormatter(new NumberPicker.Formatter() {
		        @Override
		        public String format(int i) {
		            return String.format("%02d", i);
		        }
		    });
		 nps.setFormatter(new NumberPicker.Formatter() {
		        @Override
		        public String format(int i) {
		            return String.format("%02d", i);
		        }
		    });
		 
		 np2.setFormatter(new NumberPicker.Formatter() {
		        @Override
		        public String format(int i) {
		            return String.format("%02d", i);
		        }
		    });
		 nph.setWrapSelectorWheel(true);
		 npm.setMinValue(0);
		 npm.setMaxValue(60);
		 npm.setWrapSelectorWheel(true);
		 nps.setMinValue(0);
		 nps.setMaxValue(60);
		 nps.setWrapSelectorWheel(true);
	    np.setMinValue(0);
	    np.setMaxValue(31);
	    np.setWrapSelectorWheel(true);
	    np2.setMinValue(0);
	    np2.setMaxValue(99);
	    np.setWrapSelectorWheel(true);
	    np2.setWrapSelectorWheel(true);
	    pkv2 = np2.getValue();
	    pkv = np.getValue();
	    pkvh = nph.getValue();
	    pkvm = npm.getValue();
	    pkvs = nps.getValue();
	    timers.setText(Integer.toString(pkvh)+"h:"+ Integer.toString(pkvm) + "m:"+ Integer.toString(pkvs)+ "s" );
	    distc.setText(Integer.toString(pkv)+"."+ Integer.toString(pkv2) + "KM");
	    np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() 
	    {
	      @Override
	      public void onValueChange(NumberPicker picker, int oldVal, int newVal) 
	      		{
	    	  pkv2 = np2.getValue();
	  	    pkv = np.getValue();
	    	 distc.setText(Integer.toString(pkv)+"."+ Integer.toString(pkv2) + "KM");
	      				}
	    });
	    np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() 
	    {
	      @Override
	      public void onValueChange(NumberPicker picker, int oldVal, int newVal) 
	      		{
	    	  pkv2 = np2.getValue();
	  	    pkv = np.getValue();
	    	 distc.setText(Integer.toString(pkv) +"."+ Integer.toString(pkv2)  + "KM");
	      				}
	    });
	    nph.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() 
	    {
	      @Override
	      public void onValueChange(NumberPicker picker, int oldVal, int newVal) 
	      		{
	    	  pkvh = nph.getValue();
	  	    pkvm = npm.getValue();
	  	    pkvs = nps.getValue();
	  	  timers.setText(Integer.toString(pkvh)+"h:"+ Integer.toString(pkvm) + "m:"+ Integer.toString(pkvs)+ "s" );
	      				}
	    });
	    npm.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() 
	    {
	      @Override
	      public void onValueChange(NumberPicker picker, int oldVal, int newVal) 
	      		{
	    	  pkvh = nph.getValue();
	  	    pkvm = npm.getValue();
	  	    pkvs = nps.getValue();
	  	  timers.setText(Integer.toString(pkvh)+"h:"+ Integer.toString(pkvm) + "m:"+ Integer.toString(pkvs)+ "s" );
	      				}
	    });
	    nps.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() 
	    {
	      @Override
	      public void onValueChange(NumberPicker picker, int oldVal, int newVal) 
	      		{
	    	  pkvh = nph.getValue();
	  	    pkvm = npm.getValue();
	  	    pkvs = nps.getValue();
	  	  timers.setText(Integer.toString(pkvh)+"h:"+ Integer.toString(pkvm) + "m:"+ Integer.toString(pkvs)+ "s" );
	      				}
	    });
	}
	
	/**
	 * Called when Save button is clicked 
	 * @param view
	 */
	public void addworkoutdata(View view) {
		HashMap<String, String> queryValues = new HashMap<String, String>();
		queryValues.put("distance", (Integer.toString(pkv) +"."+ Integer.toString(pkv2)).toString());
		queryValues.put("time_w", (Integer.toString(pkvh)+":"+ Integer.toString(pkvm) + ":"+ Integer.toString(pkvs)));
		if ((pkv!=0 || pkv2!=0)&&(pkvh!=0||pkvm!=0||pkvs!=0)) {
			controller.insertUser(queryValues);
			nph.setValue(0);
			npm.setValue(0);
			nps.setValue(0);
			np.setValue(0);
			np2.setValue(0);
			pkv2 = np2.getValue();
		    pkv = np.getValue();
		    pkvh = nph.getValue();
		    pkvm = npm.getValue();
		    pkvs = nps.getValue();
		    timers.setText(Integer.toString(pkvh)+"h:"+ Integer.toString(pkvm) + "m:"+ Integer.toString(pkvs)+ "s" );
			 distc.setText(Integer.toString(pkv) +"."+ Integer.toString(pkv2)  + "KM");
			Toast.makeText(getApplicationContext(), "Work-out Data Saved",
					Toast.LENGTH_LONG).show();
			//.requestFocus();
			
		} else {
			Toast.makeText(getApplicationContext(), "Please enter a valid Distance and Time",
					Toast.LENGTH_LONG).show();}
		
		
	}
	



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
