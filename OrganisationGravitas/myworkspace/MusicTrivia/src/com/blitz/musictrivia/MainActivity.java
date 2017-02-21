package com.blitz.musictrivia;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	Button button1;
	EditText userName;
	EditText passWord;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        button1=(Button)findViewById(R.id.button1);
        userName = (EditText)findViewById(R.id.editText1);
        passWord = (EditText)findViewById(R.id.editText2);
        button1.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		String username = null;
		String password = null;
		
		try
		{
		
		username = userName.getText().toString();
		password = passWord.getText().toString();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		Toast.makeText(getApplicationContext(),""+ password, Toast.LENGTH_SHORT).show();
		Intent i = new Intent(MainActivity.this,SecondActivtiy.class);
		startActivity(i);
	}
    

    
}
