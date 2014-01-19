package com.dwellersbegood;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity
{
	private static Context context;
	
	// For background music management, see :
	// http://www.rbgrn.net/content/307-light-racer-20-days-61-64-completion
	private MediaPlayer m_Player = null;
	private GData m_Data;
	private boolean muteMedia = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context = getApplicationContext();
		
		// Set the right font for title
		TextView myTextView = (TextView) findViewById(R.id.textGameTitle);
		Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Typo Oxin free promo.ttf");
		myTextView.setTypeface(typeFace);
		
		try
		{
			FileInputStream fis = this.openFileInput("Data");
			ObjectInputStream is = new ObjectInputStream(fis);
			this.m_Data = (GData) is.readObject();
			is.close();
		}
		catch (ClassNotFoundException cnfe)
		{
			Log.e("deserializeObject", "class not found error", cnfe);
			this.m_Data = new GData();
		}
		catch (IOException ioe)
		{
			Log.e("deserializeObject", "io error", ioe);
			this.m_Data = new GData();
		}
		
		Intent intent = new Intent(this, IntroActivity.class);
		this.startActivityForResult(intent, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// Previous activity was the Intro
		if (requestCode == 1)
		{
			m_Player = SoundManager.getInstance().getPlayer(SoundManager.MENU_LOOP);
			
			if (!muteMedia && !m_Player.isPlaying())
				this.m_Player.start();
		}
		
		// Previous activity was the GameActivity
		if (requestCode == 2)
		{
			if (!muteMedia && !m_Player.isPlaying())
				this.m_Player.start();
		}
	}
	
	public static Context getContext()
	{
		return MainActivity.context;
	}
	
	/** Called when the user clicks the Singleplayer button */
	public void SingleplayerMode(View view)
	{
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra("Dolla", this.m_Data);
		startActivityForResult(intent, 2);
	}
	
	/** Called when the user clicks the Settings button */
	public void Settings(View view)
	{
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Called when the mute button is pressed
	 */
	public void muteMedia(View view)
	{
		muteMedia = !muteMedia;
		
		if (muteMedia)
			m_Player.pause();
		else
			m_Player.start();
	}
	
	/** Called when the user clicks the Quit button */
	public void Quit(View view)
	{
		m_Player.stop();
		this.finish();
	}
	
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		
		if (m_Player != null && m_Player.isPlaying())
			this.m_Player.pause();
	}
	
	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		
		if (m_Player != null && !muteMedia)
			this.m_Player.start();
	}
}
