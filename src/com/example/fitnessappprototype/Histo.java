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

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Histo extends Activity {
	//DB Class to perform DB related operations
	DBController controller = new DBController(this);
	//Progress Dialog Object
	ProgressDialog prgDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.histo);
		this.setTitle("History");
		//Get User records from SQLite DB 
		ArrayList<HashMap<String, String>> userList =  controller.getAllUsers();
		//
		if(userList.size()!=0){
			//Set the User Array list in ListView
			ListAdapter adapter = new SimpleAdapter( Histo.this,userList, R.layout.view_user_entry, new String[] {"id_w", "date_w","distance_w","time_w"}, new int[] {R.id.roId,R.id.datelist, R.id.timelist, R.id.distancelist});
			ListView myList=(ListView)findViewById(android.R.id.list);
			myList.setAdapter(adapter);
			//Display Sync status of SQLite DB
			Toast.makeText(getApplicationContext(), controller.getSyncStatus(), Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	}


