package com.test.helloeeg;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MathAttentionActivity extends Activity {
	TextView tvTop, tvBottom, tvOp;
	EditText uSolution;
	int solution = 0;
	int qNumber = 0;
	int finCounter = 0;
	ArrayList<Integer> attList = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_math_attention);

		super.onCreate(savedInstanceState);

		tvTop = (TextView) findViewById(R.id.textView3);
		tvOp = (TextView) findViewById(R.id.textView2);
		tvBottom = (TextView) findViewById(R.id.textView1);
		uSolution = (EditText) findViewById(R.id.editText1);
		uSolution.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				Log.v("onTextChanged", s.toString());
				Log.v("onTextChangedSolution", "" + solution);

				try {
					int response = Integer.parseInt(s.toString());

					if (response == solution) {
						Log.v("AFTER TEXT CHANGED", s.toString());
						qNumber++;
						uSolution.setText("");
						finCounter++;
						Log.v("FIN COUNTER in TL=",String.valueOf(finCounter));
						beginQuiz(qNumber);
					}
				} catch (NumberFormatException e) {
					// Invalid number.
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				Log.v("beforeTextChanged", s.toString());
				Log.v("beforeTextChangedSolution", "" + solution);
				/*
				 * if (s.toString() != "") { if (Integer.parseInt(s.toString())
				 * == solution) { Log.v("AFTER TEXT CHANGED", s.toString());
				 * qNumber++; uSolution.setText(""); beginQuiz(qNumber); } }
				 */
			}

			@Override
			public void afterTextChanged(Editable s) {
				Log.v("onTextChanged", s.toString());
				Log.v("onTextChangedSolution", "" + solution);

				try {
					int response = Integer.parseInt(s.toString());

					if (response == solution) {
						Log.v("AFTER TEXT CHANGED", s.toString());
						qNumber++;
						uSolution.setText("");
						finCounter++;
						Log.v("FIN COUNTER in TL=",String.valueOf(finCounter));
						beginQuiz(qNumber);
					}
				} catch (NumberFormatException e) {
					// Invalid number.
				}
			}
		});

		beginQuiz(0);
		
		new Timer().scheduleAtFixedRate( new TimerTask(){
			@Override
			public void run(){
				MathAttentionActivity.this.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						int curAtt = WaveData.att;
						attList.add(curAtt);
					}
				});
				}
		}, 0, 1000);
		
		
	}

	void beginQuiz(int qNumber) {
		this.qNumber = qNumber;
		while (qNumber < 10) {
			genQuestions();

			qNumber++;
		}
		
		Log.v("FIN COUNTER=",String.valueOf(finCounter));
		if (finCounter >= 10){
			RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativeLayout1);
			layout.removeView(tvTop);
			layout.removeView(tvOp);
			layout.removeView(tvBottom);
			layout.removeView(uSolution);
			GraphView graphView = new LineGraphView(this,"Focus Over Time");
			GraphViewData[] attData = new GraphViewData[(attList.size()-6)];
			for (int i = 0; i < (attList.size()-6); i++) {
				Log.d("i", ""+i);
				attData[i] = new GraphViewData(i, attList.get(i+6));
			}
			GraphViewSeries graphViewList = new GraphViewSeries("Attention", null, attData);
			graphView.addSeries(graphViewList);
			/*graphView.setCustomLabelFormatter(new CustomLabelFormatter() {  
				   @Override
				   public String formatLabel(double value, boolean isValueX) {  
				      if (isValueX) {
				    	  return ""+attList.size();
				      }
				      return null;
				   }  
				});*/
			graphView.getGraphViewStyle().setTextSize(15);
			graphView.setVerticalLabels(new String[] {"100", "90", "80", "70", "60", "50", "40", "30", "20", "10", "0"});
			
			
			layout.addView(graphView);
		}
		

	}

	void genQuestions() {
		// 0=addition
		// 1=subtraction
		// 2=multiplication
		int randomNum = 0 + (int) (Math.random() * 3);

		if (randomNum == 0) {// adding question
			int top = 1 + (int) (Math.random() * 100);
			int bottom = 1 + (int) (Math.random() * 100);
			String addition = "+";
			solution = top + bottom;
			tvTop.setText(String.valueOf(top));
			tvOp.setText(addition);
			tvBottom.setText(String.valueOf(bottom));
			Log.v("ADD", "" + solution);

		}
		if (randomNum == 1) {// subtracting question
			int top = 1 + (int) (Math.random() * 100);
			int bottom = 1 + (int) (Math.random() * 100);
			String addition = "-";
			if (top < bottom) {
				int temp = top;
				top = bottom;
				bottom = temp;
			}
			solution = top - bottom;
			tvTop.setText(String.valueOf(top));
			tvOp.setText(addition);
			tvBottom.setText(String.valueOf(bottom));
			Log.v("SUBTRACTION", "" + solution);

		}
		if (randomNum == 2) {// multiplying question
			int top = 1 + (int) (Math.random() * 12);
			int bottom = 1 + (int) (Math.random() * 12);
			String addition = "*";
			solution = top * bottom;
			tvTop.setText(String.valueOf(top));
			tvOp.setText(addition);
			tvBottom.setText(String.valueOf(bottom));
			Log.v("MULTIPLICATION", "" + solution);

		}
		Log.v("RandomNumber", String.valueOf(randomNum));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_math_attention, menu);
		return true;
	}

}
