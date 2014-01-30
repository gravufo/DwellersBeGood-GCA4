package com.dwellersbegood;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

public class GameActivity extends Activity
{
	private GameActivity me = this;
	private GameView m_gameView;
	private GData m_Data;
	private MediaPlayer m_BackgroundMusic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			this.m_Data = (GData) extras.getSerializable("Dolla");
		}
		m_BackgroundMusic = SoundManager.getInstance().getPlayer(SoundManager.BACKGROUND_MUSIC);
		m_BackgroundMusic.setLooping(true);
		m_BackgroundMusic.start();
		m_gameView = new GameView(this, this);
		setContentView(m_gameView);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
	protected Dialog onCreateDialog(int id)
	{
		this.m_gameView.setPause(true);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Go To The Homemenu ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int id)
			{
				Intent i = new Intent(me, MainActivity.class);
				
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(i);
				
				finish();
			}
		}).setNegativeButton("No", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int id)
			{
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
		return alert;
	}
	
	@Override
	// Si la touche back est pressé, on retourne au menu
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			this.onCreateDialog(5);
		}
		return false;
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
	
	public void startMusic(){
		m_BackgroundMusic.start();
	}

	public void pauseMusic(){
		m_BackgroundMusic.pause();
	}
	
	@Override
	// Save data in the device
	protected void onPause()
	{
		//m_BackgroundMusic.pause();
		
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
	
	@Override
	public void finish()
	{
		/*try
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
		m_BackgroundMusic.stop();*/
		Intent data = new Intent();
		this.setResult(RESULT_OK, data);
		System.gc();
		super.finishActivity(2);
	}
	
	public void reset(){
		m_Data = null;
		m_gameView = new GameView(this, this);
		setContentView(m_gameView);
		super.onRestart();
	}
}
