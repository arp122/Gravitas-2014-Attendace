package com.example.organisationgravitas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class DataBaseHandlerOdStatus extends SQLiteOpenHelper 
{
	Context context;
	private static String makeTable = "CREATE TABLE IF NOT EXISTS status "+"" +
			" (id NUMBER, sub_date TEXT, apply_date TEXT, status TEXT)";
			
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "events";
	
	public DataBaseHandlerOdStatus(Context context) 
	{

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
	public void createTable()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Log.d("Query", makeTable);
		
		db.execSQL(makeTable);
		
	}
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		
				
	}
	
	public void addEvent(Status_Details dayEvent)
	{
		/*if(dayEvent.id == 79 || dayEvent.id == 90)
			return;*/
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values_main= new ContentValues();
		//Log.d("Here", dayEvent.id + dayEvent.name);
		values_main.put("id", dayEvent.id);
		values_main.put("sub_date", dayEvent.sub_date);
		values_main.put("apply_date", dayEvent.apply_date);
		values_main.put("status", dayEvent.status);
		db.insert("status", null, values_main);
	}
	
	
	
	public List<Status_Details> getAllStatusDetails()
	{
		String query = "SELECT * FROM status";
		SQLiteDatabase db = this.getWritableDatabase();
		List<Status_Details> data =new ArrayList<Status_Details>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Status_Details event = new Status_Details();
				
				event.sub_date=cursor.getString(cursor.getColumnIndex("sub_date"));
				event.apply_date = cursor.getString(cursor.getColumnIndex("apply_date"));
				event.status = cursor.getString(cursor.getColumnIndex("status"));
				data.add(event);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	public List<Integer> getAllStatus()
	{
		String query = "SELECT status FROM status";
		SQLiteDatabase db = this.getWritableDatabase();
		List<Integer> data =new ArrayList<Integer>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				
				int status=Integer.parseInt(cursor.getString(cursor.getColumnIndex("status")));
				data.add(status);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	
	
	public Cursor listTable()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor table = db.rawQuery("SELECT name _id FROM sqlite_master WHERE type = 'table' ", null);
		if(table.moveToFirst())
		{
			Log.d("Table List", "Not Null " + table.getCount());
			do
			{
				Log.d("Table Name", table.getString(0));
			}
			while(table.moveToNext());
			
			return table;

			
		}
		table.close();
		return null;
	}
	public boolean exists(String table) {
	    try {
	    	SQLiteDatabase db = this.getWritableDatabase();
	         db.execSQL("SELECT * FROM main");
	         System.out.println("TRUE");
	         return true;
	    } catch (SQLException e) {
	    	System.out.println("FALSE");
	    	return false;
	    }
	}
	
	public void deleteTable()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String query1 = "DROP TABLE IF EXISTS status";
		
		db.execSQL(query1);
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS  main");
		// Create tables again
		onCreate(db);
	}
	


	
}
