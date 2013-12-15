package com.test.helloeeg;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MinigamesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_minigames);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.minigames, menu);
		return true;
	}

	public void meditation(View v) {
		// Intent intent = new Intent(MenuActivity.this,
		// MinigamesActivity.class);
		Intent intent = new Intent(v.getContext(), Meditation.class);
		startActivityForResult(intent, 0);
	}

}
