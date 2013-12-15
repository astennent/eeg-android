package com.test.helloeeg;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.SystemClock;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class MenuActivity extends EEGActivity {
	
	private SharedPreferences mPreferences;
	private String HTTP_AUTHORIZATION;
	private GraphView graphView;
	private GraphViewSeries delta;
	private GraphViewSeries highAlpha;
	private GraphViewSeries lowAlpha;
	private GraphViewSeries highBeta;
	private GraphViewSeries lowBeta;
	private GraphViewSeries lowGamma;
	private GraphViewSeries midGamma;
	private GraphViewSeries theta;
	private LinearLayout layout;
	int x;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
		HTTP_AUTHORIZATION = (mPreferences.getString("HTTP_AUTHORIZATION", ""));
		
		x = 0;
		delta = new GraphViewSeries("Delta", null /*TODO Color*/, new GraphViewData[] { new GraphViewData(x, WaveData.DELTA) });
		highAlpha = new GraphViewSeries("High Alpha", null, new GraphViewData[] { new GraphViewData(x, WaveData.HIGH_ALPHA) });
		lowAlpha = new GraphViewSeries("Low Alpha", null, new GraphViewData[] { new GraphViewData(x, WaveData.LOW_ALPHA) });
		highBeta = new GraphViewSeries("High Beta", null, new GraphViewData[] { new GraphViewData(x, WaveData.HIGH_BETA) });
		lowBeta = new GraphViewSeries("Low Beta", null, new GraphViewData[] { new GraphViewData(x, WaveData.LOW_BETA) });
		lowGamma = new GraphViewSeries("Low Gamma", null, new GraphViewData[] { new GraphViewData(x, WaveData.LOW_GAMMA) });
		midGamma = new GraphViewSeries("Mid Gamma", null, new GraphViewData[] { new GraphViewData(x, WaveData.MID_GAMMA) });
		theta = new GraphViewSeries("Theta", null, new GraphViewData[] { new GraphViewData(x, WaveData.THETA) });
		
		graphView = new LineGraphView(this, "Graph");
		graphView.addSeries(delta);
		graphView.addSeries(highAlpha);
		graphView.addSeries(lowAlpha);
		graphView.addSeries(highBeta);
		graphView.addSeries(lowBeta);
		graphView.addSeries(lowGamma);
		graphView.addSeries(midGamma);
		graphView.addSeries(theta);
		graphView.setHorizontalLabels(new String[] {"5 ago", "4 ago", "3 ago", "2 ago", "1 ago"});  
		graphView.setCustomLabelFormatter(new CustomLabelFormatter() {  
			   @Override
			   public String formatLabel(double value, boolean isValueX) {  
			      if (!isValueX) {
			    	  return ""+value;
			      }
			      return null;
			   }  
			});
		graphView.setViewPort(0, 500);
		//graphView.setScrollable(true);
		graphView.setScalable(true);
		graphView.setShowLegend(true);
		graphView.setLegendAlign(LegendAlign.TOP);
		layout = (LinearLayout) findViewById(R.id.graphView);
		//ButtonView button1 = (ButtonView) findViewById(R.id.menuLayout);
		layout.addView(graphView);
		
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
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
	        @Override
	        public void run() {
	            MenuActivity.this.runOnUiThread(new Runnable() {
	                @Override
	                public void run() {
	                	x++;
	        			delta.appendData(new GraphView.GraphViewData(x, WaveData.DELTA), true, 300);
	        			highAlpha.appendData(new GraphView.GraphViewData(x, WaveData.HIGH_ALPHA), true, 300);
	        			lowAlpha.appendData(new GraphView.GraphViewData(x, WaveData.LOW_ALPHA), true, 300);
	        			highBeta.appendData(new GraphView.GraphViewData(x, WaveData.HIGH_BETA), true, 300);
	        			lowBeta.appendData(new GraphView.GraphViewData(x, WaveData.LOW_BETA), true, 300);
	        			midGamma.appendData(new GraphView.GraphViewData(x, WaveData.MID_GAMMA), true, 300);
	        			lowGamma.appendData(new GraphView.GraphViewData(x, WaveData.LOW_GAMMA), true, 300);
	        			theta.appendData(new GraphView.GraphViewData(x, WaveData.THETA), true, 300);
	        			Log.d("GRAPH", ""+x);
	                }
	            });
	        }
	    }, 0, 1000);
	}

}
