package com.example.memgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends ActionBarActivity {

	// Class Member Variables
	public int numDigitsProcessing;             // The number of digits in the phone number the user is currently being prompted
	                                            // to guess
	
	public int score;                           // The user's score. 
	
	public String sequenceUserEntered;          // The sequence of digits the user has entered for this round
	//public boolean userEnteringSequence;
	public boolean addDigitToSequence;          // A flag the determines whether we should add another digit to the 
	                                            // sequence.
	
	public boolean updateScore;                 // A flag that determines whether the score needs to be updated 
	//public HashMap<String, Button> keypad; 
	public String phoneNumber = "6955250";
	
	public void highlightNumberSequence(View v)
	{
		HashMap<String, Integer> keypadButtons = new HashMap<String,Integer>();
		keypadButtons.put("1",R.id.button1);
		keypadButtons.put("2",R.id.button2);
		keypadButtons.put("3", R.id.button3);
		keypadButtons.put("4", R.id.button4);
		keypadButtons.put("5",R.id.button5);
		keypadButtons.put("6",R.id.button6);
		keypadButtons.put("7", R.id.button7);
		keypadButtons.put("8", R.id.button8);
		keypadButtons.put("9", R.id.button9);
		keypadButtons.put("0", R.id.button11);
		
		List<Animator> phoneNumberSubsequence = new ArrayList<Animator>();
	
		for(int i = 0; i < numDigitsProcessing; i++)
		{
		    phoneNumberSubsequence.add(i, (ObjectAnimator)AnimatorInflater.loadAnimator(this, R.anim.blink));
		    Animator anim = phoneNumberSubsequence.get(i);
		    String ch = phoneNumber.substring(i,i+1);
		    anim.setTarget((Button) this.findViewById(keypadButtons.get(ch)));
		}
		
		AnimatorSet as = new AnimatorSet();
		
		if(updateScore)
		{
			ObjectAnimator animateScore = (ObjectAnimator)AnimatorInflater.loadAnimator(this, R.anim.blink);
			animateScore.setDuration(700);
			TextView pts = (TextView) this.findViewById(R.id.points);
			animateScore.setTarget(pts);
			animateScore.start();
			pts.setText(String.valueOf(score));
		    as.setStartDelay(1700);	
		}
		
		as.playSequentially(phoneNumberSubsequence);
		as.setDuration(1000);
		as.start();
	}
	
	public void enteredNumber(View v)
	{
		if( sequenceUserEntered.length() < numDigitsProcessing )
		{
			// add the number to the sequence the user entered
			Button b = (Button)v;
			String btnText = b.getText().toString();
			sequenceUserEntered = sequenceUserEntered + btnText;
			//TextView msg = (TextView) this.findViewById(R.id.textView2);
			//msg.setText(sequenceUserEntered + " the # digits " + String.valueOf(numDigitsProcessing));
			
			if(sequenceUserEntered.length() == numDigitsProcessing )
			{
				validateInput(v);
				//userEnteringSequence = false; 
			}
		}
	}
	
	
	public void validateInput(View v)
	{
	     // increment points
		 addDigitToSequence = validSequenceEntered();
	     score = validSequenceEntered()? score + 10 : score - 10; 
		 updateScore = true; 
	     
		 sequenceUserEntered = "";
		 
		 if(addDigitToSequence)
			numDigitsProcessing++;
		 
         highlightNumberSequence(v);
       //  userEnteringSequence = true;
	}
	
	
	public void startGame(View v)
	{
	   if(numDigitsProcessing == 0)
	   {
		  /* if( numDigitsProcessing > 0 )
		   {
			   // increment points
			   TextView pts = (TextView) this.findViewById(R.id.points);
			   addDigitToSequence = validSequenceEntered();
			   score = validSequenceEntered()? score + 10 : score - 10; 
			   updateScore = true; 
			     
			   //pts.setText(String.valueOf(score));
			   sequenceUserEntered = "";
		   }*/
		   
		   if(addDigitToSequence)
			   numDigitsProcessing++;
		   
           highlightNumberSequence(v);
           //userEnteringSequence = true;
	   }
		   
	}
	
	public boolean validSequenceEntered()
	{
		for(int i = 0; i < numDigitsProcessing; i++)
		{
			if(sequenceUserEntered.charAt(i) != phoneNumber.charAt(i) )
				return false; 
		}
		return true; 
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// Initializing class member variables
		numDigitsProcessing = 0; 
		sequenceUserEntered = new String();
		//userEnteringSequence = false;
		addDigitToSequence = true; 
		updateScore = false; 
		score = 0; 

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		// a bunch of pointers to the buttons in the interface
		/*keypad = new HashMap<String, Button>();
		keypad.put("1",(Button) this.findViewById(R.id.button1));
		keypad.put("2",(Button) this.findViewById(R.id.button2));
		keypad.put("3",(Button) this.findViewById(R.id.button3));
		keypad.put("4",(Button) this.findViewById(R.id.button4));
		keypad.put("5",(Button) this.findViewById(R.id.button5));
		keypad.put("6",(Button) this.findViewById(R.id.button6));
		keypad.put("7",(Button) this.findViewById(R.id.button7));
		keypad.put("8",(Button) this.findViewById(R.id.button8));
		keypad.put("9",(Button) this.findViewById(R.id.button9));
		keypad.put("0",(Button) this.findViewById(R.id.button11));
		
		/*
		// register the onclick event listener for each button 
		for (Map.Entry<String,Button > btn : keypad.entrySet()) {
			btn.getValue().setOnClickListener(this);
		}
		
		Button nextBtn = (Button) findViewById(R.id.validateButton);
		nextBtn.setOnClickListener(this);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
}
