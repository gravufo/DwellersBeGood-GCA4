package com.dwellersbegood;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class GameActivity extends Activity {
	
	private GameView m_gameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m_gameView = new GameView(this, this);
		setContentView(m_gameView);
	}

}
