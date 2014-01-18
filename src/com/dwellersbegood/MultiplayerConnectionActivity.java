package com.dwellersbegood;

import com.dwellersbegood.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MultiplayerConnectionActivity extends Activity {
	final Activity thisActivity = this;
	
	private static final int REQUEST_HOST = 1;
	private static final int REQUEST_JOIN = 2;
	
	private BluetoothAdapter m_bluetoothAdapter = null;
	private MediaPlayer m_Player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multiplayer_connection);
		
		TextView myTextView=(TextView)findViewById(R.id.textMultiplayerTitle);
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/Typo Oxin free promo.ttf");
		myTextView.setTypeface(typeFace);
		
		m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(m_bluetoothAdapter == null){
			// Bluetooth not supported
			finish();
			return;
		}
		
		Button hostButton = (Button)findViewById(R.id.buttonHost);
		Button joinButton = (Button)findViewById(R.id.buttonJoin);
		
		hostButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(m_bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
					Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
					intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
					startActivityForResult(intent, REQUEST_HOST);
				}
			}
			
		});
		
		joinButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(thisActivity, SearchDevicesActivity.class);
				startActivityForResult(intent, REQUEST_JOIN);
			}
			
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		switch(requestCode){
		case REQUEST_HOST:
			if(resultCode == Activity.RESULT_OK){
				// Device is now hosting a game
			}
			break;
		case REQUEST_JOIN:
			if(resultCode == Activity.RESULT_OK){
				// Device has found a peer
				
			}
		}
	}
}
