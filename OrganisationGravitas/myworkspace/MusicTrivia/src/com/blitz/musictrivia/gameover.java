package com.blitz.musictrivia;

import android.app.Activity;
import android.os.Bundle;
import android.view.TextureView;
import android.widget.TextView;

public class gameover extends Activity{
	TextView tvScore;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.gameover);
	String i=getIntent().getExtras().getString("score");
	tvScore=(TextView)findViewById(R.id.textView1);
	tvScore.setText(""+i);
}
}
