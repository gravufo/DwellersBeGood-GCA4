package com.dwellersbegood;

import java.util.Set;

import com.example.dwellersbegood.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SearchDevicesActivity extends Activity {
	
	public static final String EXTRA_DEVICE_ADDRESS = "device_address";
	
	private BluetoothAdapter m_bluetoothAdapter;
	private ArrayAdapter<String> m_pairedDevicesArray;
	private ArrayAdapter<String> m_availableDevicesArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_search_devices);
		
		// Set the result to cancel in case user press back
		setResult(Activity.RESULT_CANCELED);
		
		Button searchButton = (Button) findViewById(R.id.search_devices_search_button);
		searchButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//We should now start a discovery
				arg0.setVisibility(View.GONE);
				doDiscovery();
			}
			
		});
		
		m_pairedDevicesArray = new ArrayAdapter<String>(this, R.layout.device_view);
		m_availableDevicesArray = new ArrayAdapter<String>(this, R.layout.device_view);
		
		// Set ListView for paired device
		ListView pairedList = (ListView) findViewById(R.id.search_devices_paired_list);
		pairedList.setAdapter(m_pairedDevicesArray);
		pairedList.setOnItemClickListener(m_deviceClickListener);
		
		ListView availableDevices = (ListView) findViewById(R.id.search_devices_available_list);
		availableDevices.setAdapter(m_availableDevicesArray);
		availableDevices.setOnItemClickListener(m_deviceClickListener);
		
		// Register for broadcast when device is discovered
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(m_broadcastReceiver, filter);
		
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(m_broadcastReceiver, filter);
		
		m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		Set<BluetoothDevice> pairedDevices = m_bluetoothAdapter.getBondedDevices();
		
		if(pairedDevices.size() > 0){
			findViewById(R.id.search_devices_title).setVisibility(View.VISIBLE);
			for(BluetoothDevice device : pairedDevices){
				m_pairedDevicesArray.add(device.getName() + "\n" + device.getAddress());
			}
		}
		else{
			String noDevices = "No paired devices found";
			m_pairedDevicesArray.add(noDevices);
		}
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		
		// Stop discovery
		if(m_bluetoothAdapter != null){
			m_bluetoothAdapter.cancelDiscovery();
		}
		
		this.unregisterReceiver(m_broadcastReceiver);
	}
	
	private void doDiscovery(){
		setProgressBarIndeterminateVisibility(true);
		setTitle("Searching for devices...");
		
		findViewById(R.id.search_devices_available_text).setVisibility(View.VISIBLE);
		
		// if already discovering, clear it to restart
		if(m_bluetoothAdapter.isDiscovering()){
			m_bluetoothAdapter.cancelDiscovery();
		}
		
		m_bluetoothAdapter.startDiscovery();
	}
	
	private OnItemClickListener m_deviceClickListener = new OnItemClickListener(){
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3){
			//Cancel discovery because an item has already been click (discovery is costly)
			m_bluetoothAdapter.cancelDiscovery();
			
			//Get the device MAC address which is the last 17 chars in the view
			String info = ((TextView) v).getText().toString();
			String address = info.substring(info.length() - 17);
			
			//Create the result intent and include the MAC address
			Intent intent = new Intent();
			intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
			
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	};
	
	private final BroadcastReceiver m_broadcastReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent){
			String action = intent.getAction();
			
			// if device is found
			if(BluetoothDevice.ACTION_FOUND.equals(action)){
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if(device.getBondState() != BluetoothDevice.BOND_BONDED){
					m_availableDevicesArray.add(device.getName() + "\n" + device.getAddress());
				}
			}
			// if discovery finished
			else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
				setProgressBarIndeterminateVisibility(false);
				setTitle("Select a device");
				if(m_availableDevicesArray.getCount() == 0){
					String noDevices = "No devices found";
					m_availableDevicesArray.add(noDevices);
				}
			}
		}
	};
	
	

}
