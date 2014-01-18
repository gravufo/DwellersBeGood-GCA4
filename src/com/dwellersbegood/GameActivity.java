package com.dwellersbegood;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity
{
	
	private GameView m_gameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		m_gameView = new GameView(this, this);
		setContentView(m_gameView);
	}
	
}
