package com.suciokastro.simplecounter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.os.Vibrator;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

public class MainActivity extends Activity

	implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener
{

	public int tap_count = 0;
	private GestureDetector gesturedetector = null;
	private Vibrator vibrator = null;
	private TextView display_count = null;
	public static final String PREFS_NAME = "CounterPrefs";
	private static final String TAG = "Counter";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

		gesturedetector = new GestureDetector(this, this);
        gesturedetector.setOnDoubleTapListener(this);
		vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        setContentView(R.layout.main);

		display_count = (TextView)findViewById(R.id.display_count); 

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		tap_count = settings.getInt("latest_count", 0);

		String current_count = String.valueOf(tap_count);

		display_count.setText(current_count);
    }

	@Override
	public boolean onTouchEvent(MotionEvent event) {
			return gesturedetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
			return false;
	}

	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX,float velocityY) 
	{
			return false;
	}

	@Override
	public void onLongPress(MotionEvent e) 
	{
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle(R.string.reset_count);
		alertDialog.setMessage("dfsd");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which)
			{
				tap_count = 0;
				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putInt("latest_count", tap_count);

				editor.commit();

				display_count = (TextView)findViewById(R.id.display_count); 

				String current_count = String.valueOf(tap_count);

				display_count.setText(current_count);
				vibrator.vibrate(200);

				Log.v(TAG, "Reset count");
			} 
		});
		alertDialog.show();
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,float distanceY)
	{
			return false;
	}

	@Override
	public void onShowPress(MotionEvent e) 
	{
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) 
	{
		tap_count++;

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("latest_count", tap_count);

		editor.commit();

		TextView display_count = new TextView(this);
		display_count = (TextView)findViewById(R.id.display_count); 

		String current_count = String.valueOf(tap_count);

		display_count.setText(current_count);
		vibrator.vibrate(50);
		return false;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) 
	{
			return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) 
	{
			return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) 
	{
			return false;
	}
}
