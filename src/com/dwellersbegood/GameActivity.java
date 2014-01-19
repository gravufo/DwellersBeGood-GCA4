package com.dwellersbegood;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

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
	protected Dialog onCreateDialog(int id) {
		this.m_gameView.setPause(true);
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Go To The Homemenu ?")
    	       .setCancelable(false)
    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	               finish();
    	           }
    	       })
    	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                dialog.cancel();   	 
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
		return alert;
	}
	
	@Override
	// Si la touche back est press�, on retourne au menu
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
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
	
	@Override
	public void finish() {
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
		Intent data = new Intent();
		this.setResult(RESULT_OK, data);
		System.gc();
		super.finishActivity(1);
	}
}
