package com.test.helloeeg;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MenuActivity extends EEGActivity {
	
	private SharedPreferences mPreferences;
	private String HTTP_AUTHORIZATION;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
		HTTP_AUTHORIZATION = (mPreferences.getString("HTTP_AUTHORIZATION", ""));
		//Log.d("MENU", "Menu");
		findViewById(R.id.emotionsButton).setOnClickListener(
		        new View.OnClickListener() {
		            @Override
		            public void onClick(View v) {
		                Intent intent = new Intent(MenuActivity.this,
		                    EmotionsActivity.class);
		                startActivityForResult(intent, 0);
		            }
		        });

		findViewById(R.id.minigamesButton).setOnClickListener(
			new View.OnClickListener() {
				@Override
		    	public void onClick(View v) {
		        	// Existing Account, load login view
		        	Intent intent = new Intent(MenuActivity.this,
		            	MinigamesActivity.class);
		        	startActivityForResult(intent, 0);
		        }
		    });
	}

}
