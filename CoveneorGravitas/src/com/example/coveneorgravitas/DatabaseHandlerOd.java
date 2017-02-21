package com.example.coveneorgravitas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class DatabaseHandlerOd extends SQLiteOpenHelper 
{
	Context context;
	private static String makeTable = "CREATE TABLE IF NOT EXISTS main"+"" +
			" (id NUMBER, php_id NUMBER, name TEXT, regno TEXT, date TEXT," +
			" reason TEXT,status NUMBER, team TEXT)";
			
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "events";
	
	public DatabaseHandlerOd(Context context) 
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
	
	public void addEvent(Details dayEvent)
	{
		/*if(dayEvent.id == 79 || dayEvent.id == 90)
			return;*/
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values_main= new ContentValues();
		Log.d("Here", dayEvent.id + dayEvent.name);
		values_main.put("id", dayEvent.id);
		values_main.put("php_id", dayEvent.php_id);
		values_main.put("name", dayEvent.name);
		values_main.put("regno", dayEvent.regno);
		values_main.put("date", dayEvent.date);
		values_main.put("reason", dayEvent.reason);
		values_main.put("status",0);
		values_main.put("team", dayEvent.team);
		db.insert("main", null, values_main);
	}
	
	public void updateFavImp(String reg_no) {
		String query = "UPDATE main set status=1 where regno ='"+reg_no+"'";
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		
		
	}
	public void updateFavNotImp(String reg_no) {
		String query = "UPDATE main set status=0 where regno ='"+reg_no+"'";
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		
		
	}
	public void updateAllStatusCheck() {
		String query = "UPDATE main set status=1 where status=0";
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		
		
	}
	public void updateAllStatusUnCheck() {
		String query = "UPDATE main set status=0 where status=1";
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		
		
	}
	public List<Details> getAllEventsSelected(String teamName)
	{
		String query = "SELECT * FROM main where team='"+teamName+"'";
		SQLiteDatabase db = this.getWritableDatabase();
		List<Details> data =new ArrayList<Details>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Details event = new Details();
				
				event.php_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("php_id")));
				event.regno=cursor.getString(cursor.getColumnIndex("regno"));
				event.name = cursor.getString(cursor.getColumnIndex("name"));
				event.date = cursor.getString(cursor.getColumnIndex("date"));
				event.reason = cursor.getString(cursor.getColumnIndex("reason"));
				data.add(event);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	public List<Details> getAllEvents()
	{
		String query = "SELECT * FROM main order by date desc ";
		SQLiteDatabase db = this.getWritableDatabase();
		List<Details> data =new ArrayList<Details>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Details event = new Details();
				
				event.php_id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("php_id")));
				event.regno=cursor.getString(cursor.getColumnIndex("regno"));
				event.name = cursor.getString(cursor.getColumnIndex("name"));
				event.date = cursor.getString(cursor.getColumnIndex("date"));
				event.reason = cursor.getString(cursor.getColumnIndex("reason"));
				data.add(event);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	public List<Integer> getAllApprovedStatus()
	{
		String query = "SELECT php_id FROM main where status=1";
		SQLiteDatabase db = this.getWritableDatabase();
		List<Integer> data =new ArrayList<Integer>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				int id;
				id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("php_id")));
				System.out.println("DatabaseHandlerOd.getAllApprovedStatus()");
				data.add(id);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	public List<Integer> getAllNotApprovedStatus()
	{
		String query = "SELECT php_id FROM main where status=0";
		SQLiteDatabase db = this.getWritableDatabase();
		List<Integer> data =new ArrayList<Integer>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				int id;
				id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("php_id")));
				System.out.println("DatabaseHandlerOd.getAllApprovedStatus()");
				data.add(id);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	public List<Integer> getStatusEvents()
	{
		String query = "SELECT status FROM main";
		SQLiteDatabase db = this.getWritableDatabase();
		List<Integer> data =new ArrayList<Integer>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				int status;
				status=Integer.parseInt(cursor.getString(cursor.getColumnIndex("status")));
				
				
				data.add(status);				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	
	public List<Details> getEventsCate(int cid)
	{
		String query = "SELECT * FROM main WHERE cid="+cid;
		SQLiteDatabase db = this.getWritableDatabase();
		List<Details> data =new ArrayList<Details>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;
	}
	
	public List<Details> getEvent(int day, int id)
	{
		String query = "SELECT * FROM main WHERE day="+day+" AND id="+id;
		SQLiteDatabase db = this.getWritableDatabase();
		List<Details> data =new ArrayList<Details>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				
				
				
				
			}
			while(cursor.moveToNext());
		}
		cursor.close();
		return data;

	}
	
	public List<Details> getEvent(int id)
	{
		String query = "SELECT * FROM main WHERE id="+id;
		SQLiteDatabase db = this.getWritableDatabase();
		List<Details> data =new ArrayList<Details>();
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				
				
				
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
		String query1 = "DROP TABLE IF EXISTS main";
		
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
