package com.dwellersbegood;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	// For background music management, see :
	//		http://www.rbgrn.net/content/307-light-racer-20-days-61-64-completion
	private MediaPlayer m_Player;
	private GData m_Data;

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
		
		try{
        	FileInputStream fis = this.openFileInput("Data");
        	ObjectInputStream is = new ObjectInputStream(fis);
        	this.m_Data = (GData) is.readObject();
        	is.close();
        }
        catch(ClassNotFoundException cnfe) { 
            Log.e("deserializeObject", "class not found error", cnfe);
            this.m_Data = new GData();
        } 
        catch(IOException ioe) { 
            Log.e("deserializeObject", "io error", ioe); 
            this.m_Data = new GData();
        } 
		
		/*Intent intent = new Intent(this, IntroActivity.class);
        this.startActivityForResult(intent, 1);*/
		BitmapManager.Instance().loadBitmaps(this.getResources());
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1)
		{
			// Previous activity was the Intro
			//this.m_Player.start();
		}
		
	}
	
	/** Called when the user clicks the Singleplayer button */
	public void SingleplayerMode(View view){
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra("Dolla", this.m_Data);
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
