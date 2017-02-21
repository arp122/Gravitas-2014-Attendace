package com.blitz.musictrivia;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Questions extends Activity implements OnClickListener {
	Button btOpt1,btOpt2,btOpt3,btOpt4;
	MediaPlayer mp;
	int count=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question1);
		btOpt1=(Button)findViewById(R.id.btOpt1);
		btOpt2=(Button)findViewById(R.id.btOpt2);
		btOpt3=(Button)findViewById(R.id.btOpt3);
		btOpt4=(Button)findViewById(R.id.btOpt4);

		btOpt1.setOnClickListener(this);
		btOpt2.setOnClickListener(this);
		btOpt3.setOnClickListener(this);
		btOpt4.setOnClickListener(this);
		mp=MediaPlayer.create
				(getApplicationContext(), R.raw.question1);
		try{
			mp.start();
		}catch(Exception e){

		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btOpt1:
			Toast.makeText
			(getApplicationContext(), "Correct Answer", Toast.LENGTH_SHORT).show();
			count++;		
			Intent intentOpt1 =new Intent(this,question2.class);
			intentOpt1.putExtra("score", count);
			startActivity(intentOpt1);
			break;
		case R.id.btOpt2:
			Toast.makeText
			(getApplicationContext(), "Wrong Answer", Toast.LENGTH_SHORT).show();
			Intent intentOpt2 =new Intent(this,question2.class);
			intentOpt2.putExtra("count", count);
			
			startActivity(intentOpt2);
			break;
		case R.id.btOpt3:
			Toast.makeText
			(getApplicationContext(), "Wrong Answer", Toast.LENGTH_SHORT).show();
			Intent intentOpt3 =new Intent(this,question2.class);
			intentOpt3.putExtra("count", count);
			
			startActivity(intentOpt3);
			break;
		case R.id.btOpt4:
			Toast.makeText
			(getApplicationContext(), "Wrong Answer", Toast.LENGTH_SHORT).show();
			Intent intentOpt4 =new Intent(this,question2.class);
			intentOpt4.putExtra("count", count);
			
			startActivity(intentOpt4);
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onPause() {
		
		mp.stop();
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onResume()
	{
		mp.start();
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		mp.release();
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
