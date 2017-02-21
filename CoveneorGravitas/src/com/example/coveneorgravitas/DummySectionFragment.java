package com.example.coveneorgravitas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

public class DummySectionFragment extends Fragment implements OnItemClickListener
{
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	PullToRefreshListView lvDetails;
	
	int i;
	TextView tvDate;
	HttpClient client;
	DatabaseHandlerOd db;
	JSONObject json;
	JSONArray jsonArray;
	Details[] detail;
	ImageButton ivSelectAll,ivSend;
	String[]name=new String[1000];
	String[]regno=new String[1000];
	String []date=new String[1000];
	String [] reason=new String[1000];
	List<RowItemDetails>rowItemsDetails;
	CustomBaseAdapterDetails adapter;
	final int []approved_status=new int[1000];
	final int []not_approved_status=new int[1000];
	static Spinner spCom;
	String options[]= {"General Inquiry","Registrations", "Sales","Guest Care","Purchase","Hall arrangments","Events","Sponsorship","Marketing","Publicity","Special Guest Care","Application Development"};
	
	ProgressDialog progress;
	TextView tvPull;
	public DummySectionFragment()
	{

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{	setRetainInstance(true);
		View rootView = inflater.inflate(R.layout.team_head, container, false);
		
		db=new DatabaseHandlerOd(this.getActivity());
		
		lvDetails=(PullToRefreshListView)rootView.findViewById(R.id.lvDetails);
		tvDate=(TextView)rootView.findViewById(R.id.tvDate);
		ivSelectAll=(ImageButton)rootView.findViewById(R.id.btSelectAll);
		ivSend=(ImageButton)rootView.findViewById(R.id.btSend);
		spCom = (Spinner)rootView.findViewById(R.id.spItems);
		tvPull=(TextView)rootView.findViewById(R.id.tvPull);
		
		ArrayAdapter<String> choiceAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,options);
		spCom.setAdapter(choiceAdapter);
		
		spCom.setOnItemSelectedListener(new OnItemSelectedListener() {

			@SuppressWarnings("null")
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				List<RowItemDetails> rowItemsDetails = null;
				rowItemsDetails=new ArrayList<RowItemDetails>();
				//Toast.makeText(getActivity(), "selected"+position, 1000).show();
				List<Details> event_1=db.getAllEventsSelected(options[position]);
				if(event_1.size()==0){
					tvPull.setText("Please pull to refresh...");
				}
				else{
					tvPull.setText("");
				}
				int count=0;
				for (Details cn : event_1)
				{

					name[count]=""+cn.getName();
					regno[count]=""+cn.getRegno();
					date[count]=""+cn.getDate();
					reason[count]=""+cn.getReason();
					System.out.println(""+name[count]);
					count++;

				}
				for ( i = 0; i<event_1.size(); i++) {
					RowItemDetails item = new RowItemDetails(name[i],regno[i]);
					rowItemsDetails.add(item);
				}
			 adapter = new CustomBaseAdapterDetails(getActivity(), rowItemsDetails);
				lvDetails.setAdapter(adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		rowItemsDetails=new ArrayList<RowItemDetails>();
		List<Details> event_1=db.getAllEventsSelected(spCom.getSelectedItem().toString());
		if(event_1.size()==0){
			tvPull.setText("Please pull to refresh...");
		}
		else{
			tvPull.setText("");
		}
		int count=0;
		for (Details cn : event_1)
		{

			name[count]=""+cn.getName();
			regno[count]=""+cn.getRegno();
			date[count]=""+cn.getDate();
			reason[count]=""+cn.getReason();
			System.out.println(""+name[count]);
			count++;

		}
		for ( i = 0; i<event_1.size(); i++) {
			RowItemDetails item = new RowItemDetails(name[i],regno[i]);
			rowItemsDetails.add(item);
		}
	 adapter = new CustomBaseAdapterDetails(getActivity(), rowItemsDetails);
		lvDetails.setAdapter(adapter);
        lvDetails.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				
				
				// Do work to refresh the list here.
				new Read().execute("getDetails");
			}
		});
		
		
		try{
		
		lvDetails.setOnItemClickListener(this);
		ivSelectAll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db.updateAllStatusCheck();
				adapter = new CustomBaseAdapterDetails(getActivity(), rowItemsDetails);
				lvDetails.setAdapter(adapter);
				
				
			}
		});
		
		ivSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getActivity(), "sending", Toast.LENGTH_SHORT).show();
				List<Integer> event_1=db.getAllApprovedStatus();
				progress = ProgressDialog.show(getActivity(), "Please wait...",
					    "Sending...", true);
				int count=0;
				for (Integer cn : event_1)
				{	System.out.println("sending data ");
					int i=count+1,j;
					j=event_1.size();
					approved_status[count]=cn;
					
					new SubmitToPHP().execute(approved_status[count]+"");
					count++;

				}
				List<Integer> event_2=db.getAllNotApprovedStatus();
				
				 count=0;
				for (Integer cn : event_2)
				{	System.out.println("sending data ");
					not_approved_status[count]=cn;
					//new SubmitToPHPnotApproved().execute(not_approved_status[count]+"");
					count++;

				}
				
			}
		});
		tvDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final DatePicker datePicker;
				Button btSubmit;
				Activity a=getActivity();
				final Dialog d = new Dialog(a);
				d.requestWindowFeature(Window.FEATURE_NO_TITLE);
				d.setContentView(R.layout.date_picker);
				datePicker=(DatePicker)d.findViewById(R.id.datePicker);
				btSubmit=(Button)d.findViewById(R.id.btSubmitDate);
				btSubmit.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String date=""+datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+"/"+datePicker.getYear();
						
						tvDate.setText("Date:"+date);
						d.dismiss();
						
					}
				});
				d.show();
				
			}
		});
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
		}
		return rootView;
	}
	

		
	
		
		public class Read extends AsyncTask<String, Integer, String>
		{

			@Override
			protected String doInBackground(String... params)
			{	sendTeamName(param);
			try 
				{	
				json =new JSONObject(response);
				 
					if(json != null)
					{
						System.out.println(""+response);
						db.deleteTable();
						db.createTable();
					}
					JSONArray eventsDay1 = json.getJSONArray("od");
					System.out.println(""+eventsDay1);

					detail = new Details[eventsDay1.length()];
					
					int i =0;
					for(i = 0 ; i < detail.length ; i++)
					{
						detail[i] = new Details(i,eventsDay1.getJSONObject(i));
						
						db.addEvent(detail[i]);

					}

					
				} 
				catch (JSONException e) 
				{

					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) 
			{

				//setContentView(R.layout.loading);
				if(json != null)
				{
					System.out.println(""+response);
					
					Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
					List<Details> event_1=db.getAllEventsSelected(spCom.getSelectedItem().toString());
					if(event_1.size()==0){
						tvPull.setText("Please pull to refresh...");
					}
					else{
						tvPull.setText("");
					}
					int count=0;
					for (Details cn : event_1)
					{

						name[count]=""+cn.getName();
						regno[count]=""+cn.getRegno();
						date[count]=""+cn.getDate();
						reason[count]=""+cn.getReason();
						count++;

					}
					rowItemsDetails=new ArrayList<RowItemDetails>();
					for ( i = 0; i<event_1.size(); i++) {
						RowItemDetails item = new RowItemDetails(name[i],regno[i]);
						rowItemsDetails.add(item);
					}
				 adapter = new CustomBaseAdapterDetails(getActivity(), rowItemsDetails);
					lvDetails.setAdapter(adapter);
					lvDetails.onRefreshComplete();
					
				}
				else
				{
					Toast.makeText(getActivity(), "Refreshing failed. Not connected to internet.", Toast.LENGTH_SHORT).show();
					//finish();
					//Intent intent = new Intent(Culturals.this,HomeActivity.class);
					//startActivity(intent);
					//fail= true;
				}
			}



		}
		public static void sendTeamName(String forumName)
		{           
			HttpURLConnection connection;
			OutputStreamWriter request = null;


			URL url = null;
			response = null;         
			//Jatin to tell me
			String parameters = "convenor=asfasfa";
			//String parameters = "forumName="+forumNameToSend.toLowerCase()+"&comment="+comment+
				//	"&username="+usernameToSend;   

			try
			{
				url = new URL("http://vitgravitas.com/app/convenor.php");
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
		
		public class SubmitToPHPnotApproved extends AsyncTask<String, Integer, String>
		{


			@Override
			protected void onPreExecute()
			{

			}

			@Override
			protected String doInBackground(String... strings) {
				String param = strings[0];

				sendDataNot(param);
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
				
				//Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
				
			}


		}
		

		public static void sendDataNot(String param)
		{           
			HttpURLConnection connection;
			OutputStreamWriter request = null;


			URL url = null;
			response = null;         
			//Jatin to tell me
			String parameters = "id="+param;
			System.out.println("PARAMETER"+parameters);
			
			//String parameters = "forumName="+forumNameToSend.toLowerCase()+"&comment="+comment+
				//	"&username="+usernameToSend;   

			try
			{
				url = new URL("http://vitgravitas.com/app/schoolHeadDisapprove.php");
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
		public class SubmitToPHP extends AsyncTask<String, Integer, String>
		{


			@Override
			protected void onPreExecute()
			{

			}

			@Override
			protected String doInBackground(String... strings) {
				String param = strings[0];

				sendData(param);
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
				/*if(response.equals("failure") )
				{
					System.out.println("Here in");
					response = "Invalid credentials, please try again";
				}
				else
				{
					System.out.println("Here abc "+response+" "+response.length());
					

				}*/
				//Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
				progress.dismiss();
			}


		}
		static String response;
		static String param;

		public static void sendData(String param)
		{           
			HttpURLConnection connection;
			OutputStreamWriter request = null;


			URL url = null;
			response = null;         
			//Jatin to tell me
			String parameters = "id="+param;
			System.out.println("PARAMETER"+parameters);
			
			//String parameters = "forumName="+forumNameToSend.toLowerCase()+"&comment="+comment+
				//	"&username="+usernameToSend;   

			try
			{
				url = new URL("http://vitgravitas.com/app/convenorApprove.php");
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
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			// TODO Auto-generated method stub
			Dialog d = new Dialog(getActivity());
			d.requestWindowFeature(Window.FEATURE_NO_TITLE);
			d.setContentView(R.layout.dialog_details);
			((TextView)d.findViewById(R.id.tvName)).setText("Name: "+name[position-1]);
			((TextView)d.findViewById(R.id.tvRegno)).setText("Registration No"+regno[position-1]);
			((TextView)d.findViewById(R.id.tvDate)).setText("Date: "+date[position-1]);
			((TextView)d.findViewById(R.id.tvReason)).setText("Reason:"+reason[position-1]);
			System.out.println(""+position);
			d.show();

		}


}
