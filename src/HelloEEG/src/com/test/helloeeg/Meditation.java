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
import android.widget.Toast;

public class Meditation extends Activity {
	/** Called when the activity is first created. */
	TimerTask task;
	Boolean start = false;

	int lastMeditation = -1;
	long lastMeditationTime = -1;
	int currentMeditation = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meditation);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		this.mGLSurfaceView = (GLSurfaceView) findViewById(R.id.surfaceviewclass);
		score = (TextView) findViewById(R.id.score);
		score.setText("Meditation Level: ");

		// mGLSurfaceView = new GLSurfaceView(this);
		openGLRenderer = new OpenGLRenderer(this);
		mGLSurfaceView.setRenderer(openGLRenderer);
		myChronometer = (Chronometer) findViewById(R.id.chronometer);
		// myChronometer.start();
		startTime();

		// Timer to update the elapsed time.
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Meditation.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (openGLRenderer.reset == true) {
							toastTime();
							myChronometer.stop();
							myChronometer.setBase(SystemClock.elapsedRealtime());
							openGLRenderer.reset = false;
							myChronometer.start();

						} else {

						}

					}
				});
			}
		}, 0, 1000);

		// Timer to update current meditation.
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Meditation.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						long currentTime = System.currentTimeMillis();
						// The meditation value has been updated.
						if (currentMeditation != WaveData.med) {
							lastMeditation = currentMeditation;
							currentMeditation = WaveData.med;
							lastMeditationTime = currentTime;
						}
						// time diff should be a number between 0 and 1.
						double timeDiff = (currentTime - lastMeditationTime) / 1000.0;
						if (timeDiff > 1) {
							timeDiff = 1;
						}
						double displayScore = currentMeditation * timeDiff
								+ lastMeditation * (1 - timeDiff);
						score.setText("Meditation Level: " + (int) displayScore);
					}
				});
			}
		}, 0, 30);
	}

	public void toastTime() {
		String timeText = myChronometer.getText().toString();
		if (!timeText.equals("00:00")) {
			Toast.makeText(this, "Your time: " + myChronometer.getText(),
					Toast.LENGTH_SHORT).show();
		}
	}

	private void showElapsedTime() {
		long elapsedMillis = SystemClock.elapsedRealtime()
				- myChronometer.getBase();
	}

	public void stopTime() {
		myChronometer.stop();
		showElapsedTime();
	}

	public void resetTime() {
		myChronometer.setBase(SystemClock.elapsedRealtime());
		showElapsedTime();
	}

	public void startTime() {
		myChronometer.start();
		showElapsedTime();
	}

	public void startingTime() {
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

		myChronometer.setBase(SystemClock.elapsedRealtime()
				- stoppedMilliseconds);
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