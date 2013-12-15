package com.test.helloeeg;

import org.json.JSONException;
import org.json.JSONObject;

//import com.neurosky.thinkgear.TGDevice;
//import com.neurosky.thinkgear.TGEegPower;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

//import com.neurosky.thinkgear.*;

public class EmotionsActivity extends EEGActivity {
	/*public Handler mBluetoothHandler;
	public Handler mHandler;
	BluetoothAdapter bluetoothAdapter;
	TGDevice tgDevice;
	int DELTA, HIGHALPHA, LOWALPHA, HIGHBETA, LOWBETA, LOWGAMMA, MIDGAMMA, THETA;*/
	String[] emotions = {"Happy", "Sad", "Angry", "Anxious", "Excited", "Pain"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emotions);
		/*bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bluetoothAdapter == null) {
			// Alert user that Bluetooth is not available
			Toast.makeText(this, "Bluetooth not available", Toast.LENGTH_LONG)
					.show();
		} else {
			tgDevice = new TGDevice(bluetoothAdapter, mBluetoothHandler);
			tgDevice.connect(false);   
		}
		mBluetoothHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == TGDevice.MSG_EEG_POWER) {
					TGEegPower ep = (TGEegPower) msg.obj;
					DELTA = ep.delta;
					HIGHALPHA = ep.highAlpha;
					HIGHBETA = ep.highBeta;
					LOWALPHA = ep.lowAlpha;
					LOWBETA = ep.lowBeta;
					LOWGAMMA = ep.lowGamma;
					MIDGAMMA = ep.midGamma;
					THETA = ep.theta;
				}
			}
		};
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				JSONObject json = (JSONObject) msg.obj;
				try {
					String m = json.getString("message");
					if (m.equals("success")) {
						// TODO: Nthiung?
					} else {
						// TODO: Toast or something.
					}
				} catch (JSONException e) {
					Log.e("EEGIntentService", e.getMessage());
				}

			}
		};*/
		
		findViewById(R.id.happyButton).setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//sendBrainwaves(emotions[0]);
					sendEmotion(emotions[0]);
				}
			});

		findViewById(R.id.sadButton).setOnClickListener(
			new View.OnClickListener() {
				@Override
		    	public void onClick(View v) {
					//sendBrainwaves(emotions[1]);
					sendEmotion(emotions[1]);
		        }
		    });
		
		findViewById(R.id.angryButton).setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//sendBrainwaves(emotions[2]);
					sendEmotion(emotions[2]);
				}
			});

		findViewById(R.id.anxiousButton).setOnClickListener(
			new View.OnClickListener() {
				@Override
			    public void onClick(View v) {
					//sendBrainwaves(emotions[3]);
					sendEmotion(emotions[3]);
				}
			});
		
		findViewById(R.id.excitedButton).setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//sendBrainwaves(emotions[4]);
					sendEmotion(emotions[4]);
				}
			});

		findViewById(R.id.painButton).setOnClickListener(
			new View.OnClickListener() {
				@Override
			    public void onClick(View v) {
					//sendBrainwaves(emotions[5]);
					sendEmotion(emotions[5]);
				}
			});
	}
	
	void sendEmotion(String EMOTION) {
		WaveData.EMOTION = EMOTION;
	}
	
	/*void sendBrainwaves(String EMOTION) {

		AsyncJsonParser sendBrainwave = new AsyncJsonParser(this);
		sendBrainwave.addParameter("DELTA", String.valueOf(DELTA));
		sendBrainwave.addParameter("HIGHALPHA", String.valueOf(HIGHALPHA));
		sendBrainwave.addParameter("HIGHBETA", String.valueOf(HIGHBETA));
		sendBrainwave.addParameter("LOWALPHA", String.valueOf(LOWALPHA));
		sendBrainwave.addParameter("LOWBETA", String.valueOf(LOWBETA));
		sendBrainwave.addParameter("LOWGAMMA", String.valueOf(LOWGAMMA));
		sendBrainwave.addParameter("MIDGAMMA", String.valueOf(MIDGAMMA));
		sendBrainwave.addParameter("THETA", String.valueOf(THETA));
		sendBrainwave.addParameter("EMOTION", EMOTION);
		sendBrainwave.execute(EegURLs.POST_DATA);

		WaveData.DELTA = DELTA;
		WaveData.HIGH_ALPHA = HIGHALPHA;
		WaveData.LOW_ALPHA = LOWALPHA;
		WaveData.HIGH_BETA = HIGHBETA;
		WaveData.LOW_BETA = HIGHBETA;
		WaveData.MID_GAMMA = MIDGAMMA;
		WaveData.LOW_GAMMA = LOWGAMMA;
		WaveData.THETA = THETA;
		
	}*/

}
