package com.example.organisationgravitas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class DummySectionFragment extends Fragment implements OnClickListener
{
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	static EditText etName;

	static EditText etRegNo;

	static EditText etReason;
	TextView tvManName,tvManRegNo,tvManCom,tvManDate,tvManReason;
	static DatePicker dpDate;
	static Spinner spCom;
	Button btnSubmit;
	String name,regno,reason,date;
	static String response = "";
	String options[]= {"General Inquiry","Registrations", "Sales","Guest Care","Purchase","Hall arrangments","Events","Sponsorship","Marketing","Publicity","Special Guest Care","Application Development"};
	String sharedPrefKey = "RegistrationNo";
	static String spinerChoice=null;
	public DummySectionFragment()
	{

	}
	
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View rootView1 = inflater.inflate(R.layout.activity_main, container, false);
		etName = (EditText)rootView1.findViewById(R.id.etName);
		etRegNo = (EditText)rootView1.findViewById(R.id.etRegNo);
		etReason = (EditText)rootView1.findViewById(R.id.etReason);
		btnSubmit = (Button)rootView1.findViewById(R.id.btnSubmit);
		dpDate = (DatePicker)rootView1.findViewById(R.id.dpDate);
		spCom = (Spinner)rootView1.findViewById(R.id.ChoiceSpinner);

		tvManDate = (TextView)rootView1.findViewById(R.id.tvManDP);
		tvManRegNo= (TextView)rootView1.findViewById(R.id.tvManRegNo);
		tvManName = (TextView)rootView1.findViewById(R.id.tvManName);
		tvManReason = (TextView)rootView1.findViewById(R.id.tvManReason);
		tvManCom = (TextView)rootView1.findViewById(R.id.tvManSpin);


		tvManDate.setVisibility(View.INVISIBLE);
		tvManRegNo.setVisibility(View.INVISIBLE);
		tvManName.setVisibility(View.INVISIBLE);
		tvManReason.setVisibility(View.INVISIBLE);
		tvManCom.setVisibility(View.INVISIBLE);
	

		ArrayAdapter<String> choiceAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,options);
		spCom.setAdapter(choiceAdapter);


		dpDate.setCalendarViewShown(false);

		btnSubmit.setOnClickListener(this);

		
		return rootView1;
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

			}
			Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
			
		}


	}
	
	static String param;

	public static void sendData(String forumName)
	{           
		HttpURLConnection connection;
		OutputStreamWriter request = null;


		URL url = null;   
		response = null;         
		//Jatin to tell me
		Calendar c = Calendar.getInstance();
		//Jatin to tell me
		spinerChoice=spCom.getSelectedItem().toString();
		String parameters = "name="+etName.getText()+"&reg_num="+etRegNo.getText()+"&department="+spinerChoice+"&reason="+etReason.getText()+"&date="+dpDate.getYear()+"-"+dpDate.getMonth()+"-"+dpDate.getDayOfMonth()+"&submission_date="+c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH);
		System.out.println("parameter departemnt"+parameters);
		//String parameters = "name="+etName.getText()+"&date="+datepicker.getYear()+"-"+datepicker.getMonth()+"-"+datepicker.getDayOfMonth()+"&time="+timepicker.getCurrentHour()+":"+timepicker.getCurrentMinute()+":00"+"&about="+etDescription.getText()+"&cname=IEEE";
		//String parameters = "forumName="+forumNameToSend.toLowerCase()+"&comment="+comment+
			//	"&username="+usernameToSend;   

		try
		{
			url = new URL("http://vitgravitas.com/app/odApply.php");
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		name = etName.getText().toString();
		regno = etRegNo.getText().toString();
		reason = etReason.getText().toString();


		if(name.equals(""))
		{
			tvManName.setVisibility(View.VISIBLE);
		}
		else
		{
			tvManName.setVisibility(View.INVISIBLE);
		}
		if(regno.equals(""))
		{
			tvManRegNo.setVisibility(View.VISIBLE);
		}
		else
		{
			tvManRegNo.setVisibility(View.INVISIBLE);
		}

		if(reason.equals(""))
		{
			tvManReason.setVisibility(View.VISIBLE);
		}
		else
		{
			tvManReason.setVisibility(View.INVISIBLE);
		}


		if(name.length()!=0&&regno.length()!=0&&reason.length()!=0)
		{
			SharedPreferences preferences = this.getActivity().getSharedPreferences(sharedPrefKey, 0);
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString("status", ""+etRegNo.getText());
			editor.commit();
			String blah = "xyz";
			new SubmitToPHP().execute(blah);
		}

		
	}

}
