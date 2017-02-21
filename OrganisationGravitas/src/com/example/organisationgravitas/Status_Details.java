package com.example.organisationgravitas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Status_Details 
{
	int id;
	
	String status;
	String sub_date;
	String apply_date;
	
	private JSONObject data;
	
	public Status_Details()
	{
		
	}
	public Status_Details(int slno, JSONObject jsonData)
	{	this.data= jsonData;
		
		try 
		{
			//id = jsonData.getString("id");
			id= slno+1;
			
			sub_date=jsonData.getString("sub_date");
			apply_date=jsonData.getString("date");
			status=jsonData.getString("status");
			
			System.out.println("json details"+sub_date+apply_date);
			
			
		}
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	public String getApplyDate(){
		return this.apply_date;
	}
	public String getSubDate(){
		return this.sub_date;
	}
	public String getStatus(){
		return this.status;
	}
	
	
	
}
