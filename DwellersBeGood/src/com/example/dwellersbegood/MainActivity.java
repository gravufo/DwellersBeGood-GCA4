package com.example.dwellersbegood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	// For background music management, see :
	//		http://www.rbgrn.net/content/307-light-racer-20-days-61-64-completion
	private MediaPlayer m_Player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Set the right font for title
		TextView myTextView=(TextView)findViewById(R.id.textGameTitle);
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/Typo Oxin free promo.ttf");
		myTextView.setTypeface(typeFace);
		
		// Activate background music
		this.m_Player = MediaPlayer.create(this, R.raw.radiomartini);
		this.m_Player.setLooping(true);
		//this.m_Player.start();
	}
	
	/** Called when the user clicks the Singleplayer button */
	public void SingleplayerMode(View view){
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Multiplayer button */
	public void MultiplayerMode(View view){
		Intent intent = new Intent(this, MultiplayerConnectionActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Settings button */
	public void Settings(View view){
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Quit button */
	public void Quit(View view){
		this.finish();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//this.m_Player.pause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//this.m_Player.start();
	}


}
