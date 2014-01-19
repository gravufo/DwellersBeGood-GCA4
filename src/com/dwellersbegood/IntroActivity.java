package com.dwellersbegood;

import android.app.Activity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

public class IntroActivity extends Activity
{
	
	private IntroView m_IntroView;
	private Resources m_Res;
	
	private int m_ScreenWidth, m_ScreenHeight;
	
	private boolean m_loadingCompleted;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.m_Res = this.getResources();
		
		DisplayMetrics metrics = this.m_Res.getDisplayMetrics();
		m_ScreenWidth = metrics.widthPixels;
		m_ScreenHeight = metrics.heightPixels;
		
		this.m_IntroView = new IntroView(this, this, this.m_Res);
		
		m_loadingCompleted = false;
		
		new PrefetchData().execute();
	}
	
	public void setLoadingCompleted(boolean completed)
	{
		m_loadingCompleted = completed;
	}
	
	public boolean getLoadingCompleted()
	{
		return m_loadingCompleted;
	}
	
	/**
	 * Async Task to make http call
	 */
	private class PrefetchData extends AsyncTask<Void, Void, Void>
	{
		
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			// before making http calls
			
		}
		
		@Override
		protected Void doInBackground(Void... arg0)
		{
			/*
			 * Will make http call here This call will download required data before launching the app example: 1. Downloading and storing in SQLite 2. Downloading images 3. Fetching and parsing the xml / json 4. Sending device information to server 5. etc.,
			 */
			BitmapManager.getInstance().loadBitmaps(m_Res, m_ScreenWidth, m_ScreenHeight);
			SoundManager.getInstance().loadSfx(m_Res);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);
			// close this activity
			Log.d("Intro", "Finished loading SHITSS ********* ");
			setLoadingCompleted(true);
			// finish();
		}
		
	}
}
