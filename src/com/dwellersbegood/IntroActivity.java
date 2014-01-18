package com.dwellersbegood;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;

public class IntroActivity extends Activity{

	private IntroView m_IntroView;
	private Resources m_Res;
		// Highscores
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.m_Res = this.getResources();
	
		this.m_IntroView = new IntroView(this, this, this.m_Res);
	}
	
	
	@Override
	// Si la touche back est pressé, on retourne au menu
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return true;
	}

}
