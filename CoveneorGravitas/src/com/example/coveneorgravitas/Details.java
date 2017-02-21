package com.example.coveneorgravitas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Details 
{
	int id;
	int php_id;
	String name;
	String regno;
	String date;
	//String time;
	String reason;
	String team;
	private JSONObject data;
	
	public Details()
	{
		
	}
	public Details(int slno, JSONObject jsonData)
	{	this.data= jsonData;
		
		try 
		{
			//id = jsonData.getString("id");
			id= slno+1;
			php_id=jsonData.getInt("id");
			name = jsonData.getString("name");
			regno = jsonData.getString("reg");
			date = jsonData.getString("date");
			reason = jsonData.getString("reason");
			team=jsonData.getString("dept");
			System.out.println(""+name);
			
			
		}
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String getName(){
		return this.name;
	}
	public String getRegno(){
		return this.regno;
	}
	
	public String getDate(){
		return this.date;
	}
	public String getReason(){
		return this.reason;
	}
	
	public String getTeam(){
		return this.team;
	}
	
	
	
	
}
