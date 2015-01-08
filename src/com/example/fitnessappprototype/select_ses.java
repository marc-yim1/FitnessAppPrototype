package com.example.fitnessappprototype;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class select_ses extends Activity  {
	//DB Class to perform DB related operations
	DBController controller = new DBController(this);
	ListView listview;
	//Progress Dialog Object
	ProgressDialog prgDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.histo);
		//Get User records from SQLite DB 
		ArrayList<HashMap<String, String>> userList =  controller.getAllUsers();
		this.setTitle("Select Session");
		//
		if(userList.size()!=0){
			//Set the User Array list in ListView
			final ListAdapter adapter = new SimpleAdapter( select_ses.this,userList, R.layout.view_user_entry, new String[] { "id_w","date_w","distance_w","time_w"}, new int[] {R.id.roId,R.id.datelist, R.id.timelist, R.id.distancelist});
			final ListView myList=(ListView)findViewById(android.R.id.list);
			myList.setAdapter(adapter);
			//Display Sync status of SQLite DB
			Toast.makeText(getApplicationContext(), controller.getSyncStatus(), Toast.LENGTH_LONG).show();
			myList.setOnItemClickListener(new OnItemClickListener(){


				@Override
				public void onItemClick(AdapterView<?>parent,View v, int position,long id){

					HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(position);
				Object idn = myList.getItemAtPosition(position);
				String idm2 = obj.get("id_w").toString();
				Toast.makeText(getApplicationContext(), "Edit session number: "+idm2.toString(), Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent(select_ses.this,edit_ses.class);
				intent.putExtra("idKey", idm2.toString());
				//based on item add info to intent
				
				startActivity(intent);
				finish();
				}


				});	
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	}


