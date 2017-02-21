package com.example.organisationgravitas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

public class DummySectionFragment2 extends Fragment 
{
	public static final String ARG_SECTION_NUMBER = "section_number";

	PullToRefreshListView lvStatus;
	static String response = "";
	
	Status_Details[] status_details;
	HttpClient client;
	String [] sub_date=new String[1000];
	String [] apply_date=new String[1000];
	String [] status=new String[1000];
	String sharedPrefKey = "RegistrationNo";
	static String state = null;
	List<RowItemStatus>rowItemsDetails;
	DataBaseHandlerOdStatus db;
	CustomBaseAdapterStatus adapter;
	TextView tvPull;
	public DummySectionFragment2()
	{

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.status, container, false);
		SharedPreferences preferences = this.getActivity().getSharedPreferences(sharedPrefKey, 0);
		lvStatus=(PullToRefreshListView)rootView.findViewById(R.id.lvStatus);
		tvPull=(TextView)rootView.findViewById(R.id.tvPull);
		state=	preferences.getString("status", "");
		db= new DataBaseHandlerOdStatus(getActivity());
		List<Status_Details> event_1=db.getAllStatusDetails();
		if(event_1.size()==0){
			tvPull.setText("Please pull to refresh...");
		}
		else{
			tvPull.setText("");
		}
		int count=0;
		for (Status_Details cn : event_1)
		{

			sub_date[count]=""+cn.getSubDate();
			apply_date[count]=""+cn.getApplyDate();
			status[count]=""+cn.getStatus();
			count++;

		}
		rowItemsDetails=new ArrayList<RowItemStatus>();
		for ( int i = 0; i<count; i++) {

			RowItemStatus item= new RowItemStatus(sub_date[i],apply_date[i],status[i]);
			System.out.println("submission date and apply date"+sub_date[i]+apply_date[i]);
			rowItemsDetails.add(item);
		}
		adapter = new CustomBaseAdapterStatus(getActivity(), rowItemsDetails);
		lvStatus.setAdapter(adapter);
		lvStatus=(PullToRefreshListView)rootView.findViewById(R.id.lvStatus);

		lvStatus.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				
				
				// Do work to refresh the list here.
				new SubmitToPHP().execute("vyhgf");
			}
		});

		
		
		return rootView;
	}

	public class SubmitToPHP extends AsyncTask<String, Integer, String>
	{


		@Override
		protected void onPreExecute()
		{

		}

		@Override
		protected String doInBackground(String... strings) {

			sendData(param);
			try 
			{	
				JSONArray json=new JSONArray(response);
			 
				if(json != null)
				{
					
					db.deleteTable();
					db.createTable();
				}
				
				status_details = new Status_Details[json.length()];
				int i =0;
				for(i = 0 ; i < status_details.length ; i++)
				{
					status_details[i] = new Status_Details(i,json.getJSONObject(i));
					db.addEvent(status_details[i]);
					System.out.println("addin");
				}

				
			} 
			catch (JSONException e) 
			{

				e.printStackTrace();
			}
			return null;
		}


		@Override
		protected void onProgressUpdate(Integer... values)
		{

		}

		@Override
		protected void onPostExecute(String s)
		{
			System.out.println("Here");
			if(response.equals("failure") )
			{
				System.out.println("Here in");
				response = "Invalid credentials, please try again";
			}
			else
			{
				System.out.println("Here abc "+response+" "+response.length());
				
					
					

					List<Status_Details> event_1=db.getAllStatusDetails();
					if(event_1.size()==0){
						tvPull.setText("Please pull to refresh...");
					}
					else{
						tvPull.setText("");
					}
					int count=0;
					for (Status_Details cn : event_1)
					{

						sub_date[count]=""+cn.getSubDate();
						apply_date[count]=""+cn.getApplyDate();
						status[count]=""+cn.getStatus();
						count++;

					}
					List<RowItemStatus>rowItemsDetails=new ArrayList<RowItemStatus>();
					
					for ( int i = 0; i<count; i++) {

						RowItemStatus item= new RowItemStatus(sub_date[i],apply_date[i],status[i]);
						System.out.println("submission date and apply date"+sub_date[i]+apply_date[i]);
						rowItemsDetails.add(item);
					}
					 adapter = new CustomBaseAdapterStatus(getActivity(), rowItemsDetails);
					lvStatus.setAdapter(adapter);
				 
				
				lvStatus.onRefreshComplete();
			}

			//Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();


		}


	}

	static String param;

	public static void sendData(String forumName)
	{           
		HttpURLConnection connection;
		OutputStreamWriter request = null;


		URL url = null;   
		response = null;         
		//Jatin to tell me//TO send the reg no of the student and get a response
		String parameters = "reg="+state;
		System.out.println("state---"+state);
		try
		{
			url = new URL("http://vitgravitas.com/app/status.php");
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestMethod("POST");    

			request = new OutputStreamWriter(connection.getOutputStream());
			request.write(parameters);
			request.flush();
			request.close();            
			String line = "";               
			InputStreamReader isr = new InputStreamReader(connection.getInputStream());
			BufferedReader reader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null)
			{
				sb.append(line);
			}
			// Response from server after login process will be stored in response variable.                
			response = sb.toString();
			// You can perform UI operations here          
			isr.close();
			reader.close();

		}
		catch(IOException e)
		{
			// Error
		}
	}



	

}
