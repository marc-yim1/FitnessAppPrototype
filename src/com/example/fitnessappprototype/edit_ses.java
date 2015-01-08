package com.example.fitnessappprototype;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class edit_ses extends Activity {
	//DB Class to perform DB related operations
	DBController controller = new DBController(this);
	//Progress Dialog Object
	ProgressDialog prgDialog;
	TextView distc,timers,dates;
	
	public int pkv2, pkv,pkvh,pkvm,pkvs;
	public Date dtsr;
	 NumberPicker np,np2,nph,npm,nps;
	 DatePicker dtp;
	 String idk,months;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.editsess);
		this.setTitle("Edit Session");
		//Get User records from SQLite DB 
		 idk=getIntent().getExtras().getString("idKey");
		
		ArrayList<HashMap<String, String>> userList =  controller.getoneUser(idk);
		
		//
		if(userList.size()!=0){
			//Set the User Array list in ListView
			ListAdapter adapter = new SimpleAdapter( edit_ses.this,userList, R.layout.view_user_entry, new String[] { "id_w","date_w","distance_w","time_w"}, new int[] {R.id.roId,R.id.datelist, R.id.timelist, R.id.distancelist});
			ListView myList=(ListView)findViewById(android.R.id.list);
			myList.setClickable(false);
			myList.setAdapter(adapter);
			//Display Sync status of SQLite DB
			
			//Toast.makeText(getApplicationContext(), controller.getSyncStatus(), Toast.LENGTH_LONG).show();
		}//ifstatement
		timers = (TextView)findViewById(R.id.timelist);
		distc = (TextView) findViewById(R.id.distancelist);
		dates=(TextView) findViewById(R.id.datelist);
		dtp = (DatePicker) findViewById(R.id.datePicker1);
		
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
	    
	}//oncreate
	public void updateworkoutdata(View view) {
		
		switch(dtp.getMonth()){
		case 0:{months = "Jan";} break;
		case 1:{months = "Feb";} break;
		case 2:{months = "Mar";} break;
		case 3:{months = "Apr";} break;
		case 4:{months = "May";} break;
		case 5:{months = "Jun";} break;
		case 6:{months = "Jul";} break;
		case 7:{months = "Aug";} break;
		case 8:{months = "Sep";} break;
		case 9:{months = "Oct";} break;
		case 10:{months = "Nov";} break;
		case 11:{months = "Dec";} break;
		}
		
		HashMap<String, String> queryValues = new HashMap<String, String>();
		queryValues.put("distance", (Integer.toString(pkv) +"."+ Integer.toString(pkv2)).toString());
		queryValues.put("idk", (idk.toString()));
		queryValues.put("time_w", (Integer.toString(pkvh)+":"+ Integer.toString(pkvm) + ":"+ Integer.toString(pkvs)));
		queryValues.put("dates", months+" "+dtp.getDayOfMonth()+", "+dtp.getYear());
		if ((pkv!=0 || pkv2!=0)&&(pkvh!=0||pkvm!=0||pkvs!=0)) {
			controller.updatework(queryValues);
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
			Toast.makeText(getApplicationContext(), "Work-out Data Updated",
					Toast.LENGTH_LONG).show();
			finish();
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

	
	
	}


