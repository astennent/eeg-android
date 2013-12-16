package com.test.helloeeg;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.neurosky.thinkgear.*;

public class EEGIntentService extends IntentService {
	int med =0;
	int att =0;
	public Handler mHandler;
	@SuppressLint("HandlerLeak")
	public Handler mBluetoothHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			//Log.v("blah", "y");
			switch (msg.what) {
			case TGDevice.MSG_STATE_CHANGE:

				switch (msg.arg1) {
				case TGDevice.STATE_IDLE:
					break;
				case TGDevice.STATE_CONNECTING:
					// tv.append("Connecting...\n");
					break;
				case TGDevice.STATE_CONNECTED:
					// tv.append("Connected.\n");
					tgDevice.start();
					break;
				case TGDevice.STATE_NOT_FOUND:
					// tv.append("Can't find\n");
					break;
				case TGDevice.STATE_NOT_PAIRED:
					// tv.append("not paired\n");
					break;
				case TGDevice.STATE_DISCONNECTED:
					// tv.append("Disconnected mang\n");
				}

				break;
			case TGDevice.MSG_POOR_SIGNAL:
				// signal = msg.arg1;
				// tv.append("PoorSignal: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_RAW_DATA:
				// raw1 = msg.arg1;
				// tv.append("Got raw: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_HEART_RATE:
				// tv.append("Heart rate: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_ATTENTION:
				att = msg.arg1;
				// tv.append("Attention: " + msg.arg1 + "\n");
				// Log.v("HelloA", "Attention: " + att + "\n");
				break;
			case TGDevice.MSG_MEDITATION:
				med = msg.arg1;
				break;
			case TGDevice.MSG_BLINK:
				// tv.append("Blink: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_RAW_COUNT:
				// tv.append("Raw Count: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_LOW_BATTERY:
				Toast.makeText(getApplicationContext(), "Low battery!",
						Toast.LENGTH_SHORT).show();
				break;
			case TGDevice.MSG_RAW_MULTI:
				// TGRawMulti rawM = (TGRawMulti)msg.obj;
				// tv.append("Raw1: " + rawM.ch1 + "\nRaw2: " + rawM.ch2);
				break;
			case TGDevice.MSG_EEG_POWER:
				TGEegPower ep = (TGEegPower) msg.obj;
				Log.i("B4A", "Delta: " + ep.delta);
				int DELTA = ep.delta;
				int HIGHALPHA = ep.highAlpha;
				int HIGHBETA = ep.highBeta;
				int LOWALPHA = ep.lowAlpha;
				int LOWBETA = ep.lowBeta;
				int LOWGAMMA = ep.lowGamma;
				int MIDGAMMA = ep.midGamma;
				int THETA = ep.theta;
				sendBrainwaves(DELTA, HIGHALPHA, HIGHBETA, LOWALPHA,
						LOWBETA, LOWGAMMA, MIDGAMMA, THETA, med, att);
				Log.v("INTENTSERVICE", "sent");
			default:
				break;
			}
		}
	};
	BluetoothAdapter bluetoothAdapter;
	TGDevice tgDevice;
	int outlier = 16000000;

	@SuppressLint("HandlerLeak")
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "Service Starting", Toast.LENGTH_SHORT).show();
		Log.v("INTENTSERVICE", "Service Starting");
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bluetoothAdapter == null) {
			// Alert user that Bluetooth is not available
			Toast.makeText(this, "Bluetooth not available", Toast.LENGTH_LONG)
					.show();
		} else {
			tgDevice = new TGDevice(bluetoothAdapter, mBluetoothHandler);
			tgDevice.connect(false);   
		}

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				//Log.d("bluh", "bluh");
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
		};
		//Log.v("blah", "x");
		return super.onStartCommand(intent, flags, startId);
	}

	void sendBrainwaves(int DELTA, int HIGHALPHA, int HIGHBETA, int LOWALPHA,
			int LOWBETA, int LOWGAMMA, int MIDGAMMA, int THETA, int med, int att) {

		AsyncJsonParser sendBrainwave = new AsyncJsonParser(this);

		if (DELTA < outlier) {
			sendBrainwave.addParameter("DELTA", String.valueOf(DELTA));
			WaveData.DELTA = DELTA;
		}
		if (HIGHALPHA < outlier) {
			sendBrainwave.addParameter("HIGHALPHA", String.valueOf(HIGHALPHA));
			WaveData.HIGH_ALPHA = HIGHALPHA;
		}
		if (HIGHBETA < outlier) {
			sendBrainwave.addParameter("HIGHBETA", String.valueOf(HIGHBETA));
			WaveData.HIGH_BETA = HIGHBETA;
		}
		if (LOWALPHA < outlier) {
			sendBrainwave.addParameter("LOWALPHA", String.valueOf(LOWALPHA));
			WaveData.LOW_ALPHA = LOWALPHA;
		}
		if (LOWBETA < outlier) {
			sendBrainwave.addParameter("LOWBETA", String.valueOf(LOWBETA));
			WaveData.LOW_BETA = LOWBETA;
		}
		if (LOWGAMMA < outlier) {
			sendBrainwave.addParameter("LOWGAMMA", String.valueOf(LOWGAMMA));
			WaveData.LOW_GAMMA = LOWGAMMA;
		}
		if (MIDGAMMA < outlier) {
			sendBrainwave.addParameter("MIDGAMMA", String.valueOf(MIDGAMMA));
			WaveData.MID_GAMMA = MIDGAMMA;
		}
		if (THETA < outlier) {
			sendBrainwave.addParameter("THETA", String.valueOf(THETA));
			WaveData.THETA = THETA;
		}
		sendBrainwave.addParameter("med", String.valueOf(med));
		sendBrainwave.addParameter("EMOTION", WaveData.EMOTION);
		//Log.d("EMOTION1", WaveData.EMOTION);
		sendBrainwave.execute(EegURLs.POST_DATA);
		WaveData.med = med;
		WaveData.att = att;
		WaveData.EMOTION = "";
		//Log.d("EMOTION2", WaveData.EMOTION);
	}

	public EEGIntentService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public EEGIntentService() {
		super("EEGIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		//Log.v("INTENT SERVICE", "starting");
	}

}
