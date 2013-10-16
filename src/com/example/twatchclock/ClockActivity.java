package com.example.twatchclock;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.tomoon.sdk.Emulator;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class ClockActivity extends Activity {
	
	
	public TextView textViewHour;
	public TextView textViewMin;
	public TextView textViewSec;
	
	public Calendar mCalendar;
	public int hour;
	public int min;
	public int sec;
	
	//private Thread updateClockThread;
	private Timer timer = new Timer();
	
	public Handler mHandler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case 1:
					mCalendar = Calendar.getInstance();
					mCalendar.setTimeInMillis(System.currentTimeMillis());
					String[] words = TimeToWords.timeToWords(mCalendar);
					//hour = mCalendar.get(Calendar.HOUR);
					//min = mCalendar.get(Calendar.MINUTE);
					//sec = mCalendar.get(Calendar.SECOND);
					textViewHour.setText(words[0]);
					textViewMin.setText(words[1]);
					if(words.length == 2)
						textViewSec.setVisibility(View.INVISIBLE );
					else
						textViewSec.setText(words[2]);
					break;
			}
			super.handleMessage(msg);
		}
				
	};
	
	public TimerTask tTask = new TimerTask() {		
		@Override
		public void run() {
			Message msg = new Message();
			msg.what = 1;
			mHandler.sendMessage(msg);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_clock);
		Emulator.configure(getWindow());
		
		textViewHour = (TextView) findViewById(R.id.hours);
		textViewMin = (TextView) findViewById(R.id.tens);
		textViewSec = (TextView) findViewById(R.id.minutes);
		
		timer.schedule(tTask, 1000, 1000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.clock, menu);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		if(timer!=null) {
			timer.cancel();
			timer = null;
		}
		super.onDestroy();
	}
	

	

}
