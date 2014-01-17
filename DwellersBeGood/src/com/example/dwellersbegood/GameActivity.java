package com.example.dwellersbegood;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class GameActivity extends Activity {
	
	private GameView m_gameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m_gameView = new GameView(this, this);
		setContentView(m_gameView);
	}

}
