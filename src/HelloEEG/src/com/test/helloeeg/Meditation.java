package com.test.helloeeg;

import java.util.Timer;
import java.util.TimerTask;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.TextView;

public class Meditation extends Activity {
	/** Called when the activity is first created. */
    TimerTask task;
    Boolean start = false;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		this.mGLSurfaceView =  (GLSurfaceView) findViewById(R.id.surfaceviewclass);
		score = (TextView) findViewById(R.id.score);
		score.setText("Score: ");


		//mGLSurfaceView = new GLSurfaceView(this);
		openGLRenderer = new OpenGLRenderer(this);
		mGLSurfaceView.setRenderer(openGLRenderer);
		myChronometer  = (Chronometer)findViewById(R.id.chronometer);
		//myChronometer.start();
		startTime();
		new Timer().scheduleAtFixedRate( new TimerTask(){
			@Override
			public void run(){
				Meditation.this.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(openGLRenderer.reset==true){
							myChronometer.stop();
							myChronometer.setBase(SystemClock.elapsedRealtime());
							openGLRenderer.reset = false;
							myChronometer.start();
							
						}
						else{
							
						}
						
					}
				});
				}
		}, 0, 1000);
		}
		
		
	
	
	private void showElapsedTime() {
	     long elapsedMillis = SystemClock.elapsedRealtime() - myChronometer.getBase();            
	 }
	public void stopTime() {
		myChronometer.stop();
        showElapsedTime();
	}
	public void resetTime(){
		myChronometer.setBase(SystemClock.elapsedRealtime());
        showElapsedTime();
	}
	public void startTime(){
		myChronometer.start();
		showElapsedTime();
	}
	public void startingTime(){
		int stoppedMilliseconds = 0;

        String chronoText = myChronometer.getText().toString();
        String array[] = chronoText.split(":");
        if (array.length == 2) {
          stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
              + Integer.parseInt(array[1]) * 1000;
        } else if (array.length == 3) {
          stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000 
              + Integer.parseInt(array[1]) * 60 * 1000
              + Integer.parseInt(array[2]) * 1000;
        }

        myChronometer.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
        myChronometer.start();
	}
	
	
	
	
	

	@Override
	protected void onPause() {
		super.onPause();
		mGLSurfaceView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mGLSurfaceView.onResume();
	}

	Chronometer myChronometer; 
	OpenGLRenderer openGLRenderer;
	private GLSurfaceView mGLSurfaceView;
	private TextView score;
	double secFromOGLR;

	
}