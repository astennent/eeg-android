package com.test.helloeeg;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class EmotionsActivity extends EEGActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emotions);
		findViewById(R.id.happyButton).setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//TODO Add code to throw data at the server
				}
			});

		findViewById(R.id.sadButton).setOnClickListener(
			new View.OnClickListener() {
				@Override
		    	public void onClick(View v) {
					
		        }
		    });
		
		findViewById(R.id.angryButton).setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			});

		findViewById(R.id.anxiousButton).setOnClickListener(
			new View.OnClickListener() {
				@Override
			    public void onClick(View v) {
						
				}
			});
		
		findViewById(R.id.excitedButton).setOnClickListener(
			new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});

		findViewById(R.id.painButton).setOnClickListener(
			new View.OnClickListener() {
				@Override
			    public void onClick(View v) {
							
				}
			});
	}
	
	void sendBrainwaves(int DELTA, int HIGHALPHA, int HIGHBETA, int LOWALPHA,
			int LOWBETA, int LOWGAMMA, int MIDGAMMA, int THETA) {

		AsyncJsonParser sendBrainwave = new AsyncJsonParser(this);
		sendBrainwave.addParameter("DELTA", String.valueOf(DELTA));
		sendBrainwave.addParameter("HIGHALPHA", String.valueOf(HIGHALPHA));
		sendBrainwave.addParameter("HIGHBETA", String.valueOf(HIGHBETA));
		sendBrainwave.addParameter("LOWALPHA", String.valueOf(LOWALPHA));
		sendBrainwave.addParameter("LOWBETA", String.valueOf(LOWBETA));
		sendBrainwave.addParameter("LOWGAMMA", String.valueOf(LOWGAMMA));
		sendBrainwave.addParameter("MIDGAMMA", String.valueOf(MIDGAMMA));
		sendBrainwave.addParameter("THETA", String.valueOf(THETA));
		sendBrainwave.execute(EegURLs.POST_DATA);

		WaveData.HIGH_ALPHA = HIGHALPHA;// FINISH FOR LATER
	}

}
