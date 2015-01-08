package com.example.fitnessappprototype;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DBController  extends SQLiteOpenHelper {
	
	public DBController(Context applicationcontext) {
        super(applicationcontext, "androidsqlite.db", null, 1);
    }
	//Creates Table
	@Override
	public void onCreate(SQLiteDatabase database) {
		String query;
		query = "CREATE TABLE fit ( id_w INTEGER PRIMARY KEY, distance_w INTEGER,time_w INTEGER,date_w TEXT, udpateStatus TEXT)";
        database.execSQL(query);
	}
	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String query;
		query = "DROP TABLE IF EXISTS fit";
		database.execSQL(query);
        onCreate(database);
	}
	/**
	 * Inserts User into SQLite DB
	 * @param queryValues
	 */
	public void insertUser(HashMap<String, String> queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("distance_w", queryValues.get("distance"));
		values.put("time_w", queryValues.get("time_w"));
		
		values.put("date_w", DateFormat.getDateInstance().format(new Date()));
		values.put("udpateStatus", "no");
		database.insert("fit", null, values);
		database.close();
	}
	public void updatework(HashMap<String, String> queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("distance_w", queryValues.get("distance"));
		values.put("time_w", queryValues.get("time_w"));
		
		values.put("date_w",  queryValues.get("dates"));
		values.put("udpateStatus", "no");
		database.update("fit", values,  "id_w =?"  , new String[] { queryValues.get("idk") });
		database.close();
	}
	
	/**
	 * Get list of Users from SQLite DB as Array List
	 * @return
	 */
	public ArrayList<HashMap<String, String>> getAllUsers() {
		ArrayList<HashMap<String, String>> wordList;
		wordList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM fit";
	    SQLiteDatabase database = this.getWritableDatabase();
	    Cursor cursor = database.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	HashMap<String, String> map = new HashMap<String, String>();
	        	map.put("id_w", cursor.getString(0));
	        	map.put("distance_w", cursor.getString(1));
	        	map.put("time_w", cursor.getString(2));
	        	map.put("date_w", cursor.getString(3));
	        	
                wordList.add(map);
	        } while (cursor.moveToNext());
	    }
	    database.close();
	    return wordList;
	}
	
	public ArrayList<HashMap<String, String>> getoneUser(String idkey) {
		SQLiteDatabase database = this.getWritableDatabase();
		
		ArrayList<HashMap<String, String>> wordList;
		
		wordList = new ArrayList<HashMap<String, String>>();
		//values.put("idkey", queryValues.get("idn"));
		String selectQuery = "SELECT  * FROM fit where id_w ='"+idkey+"'";
	    
	    Cursor cursor = database.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	HashMap<String, String> map = new HashMap<String, String>();
	        	map.put("id_w", cursor.getString(0));
	        	map.put("distance_w", cursor.getString(1));
	        	map.put("time_w", cursor.getString(2));
	        	map.put("date_w", cursor.getString(3));
	        	
                wordList.add(map);
	        } while (cursor.moveToNext());
	    }
	    database.close();
	    return wordList;
	}
	
	/**
	 * Compose JSON out of SQLite records
	 * @return
	 */
	public String composeJSONfromSQLite(){
		ArrayList<HashMap<String, String>> wordList;
		wordList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM fit where udpateStatus = '"+"no"+"'";
	    SQLiteDatabase database = this.getWritableDatabase();
	    Cursor cursor = database.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	HashMap<String, String> map = new HashMap<String, String>();
	        	map.put("id_w", cursor.getString(0));
	        	map.put("distance_w", cursor.getString(1));
	        	map.put("time_w", cursor.getString(2));
	        	map.put("date_w", cursor.getString(3));
	        	wordList.add(map);
	        } while (cursor.moveToNext());
	    }
	    database.close();
		Gson gson = new GsonBuilder().create();
		//Use GSON to serialize Array List to JSON
		return gson.toJson(wordList);
	}
	
	/**
	 * Get Sync status of SQLite
	 * @return
	 */
	public String getSyncStatus(){
	    String msg = null;
	    if(this.dbSyncCount() == 0){
	    	msg = "SQLite and Remote MySQL DBs are in Sync!";
	    }else{
	    	msg = "DB Sync needed";
	    }
	    return msg;
	}
	
	/**
	 * Get SQLite records that are yet to be Synced
	 * @return
	 */
	public int dbSyncCount(){
		int count = 0;
		String selectQuery = "SELECT  * FROM fit where udpateStatus = '"+"no"+"'";
	    SQLiteDatabase database = this.getWritableDatabase();
	    Cursor cursor = database.rawQuery(selectQuery, null);
	    count = cursor.getCount();
	    database.close();
		return count;
	}
	
	/**
	 * Update Sync status against each User ID
	 * @param id
	 * @param status
	 */
	public void updateSyncStatus(String id, String status){
		SQLiteDatabase database = this.getWritableDatabase();	 
		String updateQuery = "Update fit set udpateStatus = '"+ status +"' where id_w="+"'"+ id +"'";
		Log.d("query",updateQuery);		
		database.execSQL(updateQuery);
		database.close();
	}
}
