package com.dwellersbegood;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

public class GameActivity extends Activity
{
	
	private GameView m_gameView;
	private GData m_Data;
	private MediaPlayer m_BackgroundMusic = MediaPlayer.create(MainActivity.getContext(), R.raw.aztec_dolla_music);
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			this.m_Data = (GData) extras.getSerializable("Dolla");
		}
		m_BackgroundMusic.setLooping(true);
		m_gameView = new GameView(this, this);
		setContentView(m_gameView);
	}
	
	public GData getData()
	{
		return m_Data;
	}
	
	public void setData(GData data)
	{
		m_Data = data;
	}
	
	@Override
	// Save data in the device
	protected void onStop()
	{
		try
		{
			FileOutputStream fos = this.openFileOutput("Data", Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(this.m_Data);
			os.close();
		}
		catch (IOException ioe)
		{
			Log.e("serializeObject", "error", ioe);
		}
		m_BackgroundMusic.stop();
		super.onStop();
	}
	
	@Override
	protected void onRestart()
	{
		m_Data = null;
		m_gameView = null;
		m_BackgroundMusic.seekTo(0);
		super.onRestart();
	}
	
	@Override
	protected void onResume()
	{
		m_BackgroundMusic.start();
		super.onResume();
	}
	
	@Override
	// Save data in the device
	protected void onPause()
	{
		m_BackgroundMusic.pause();
		
		try
		{
			FileOutputStream fos = this.openFileOutput("Data", Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(this.m_Data);
			os.close();
		}
		catch (IOException ioe)
		{
			Log.e("serializeObject", "error", ioe);
		}
		super.onPause();
	}
}
