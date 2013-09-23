package com.example.guessthenumber;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class PlayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
	    // Get the message from the intent
	    Intent intent = getIntent();
	    String message = "Hello, " + intent.getStringExtra(MainActivity.EXTRA_MESSAGE)
	    		         + "! Let's start the game!";

	    // Create the text view	    
	    TextView textView = (TextView) findViewById(R.id.greeting);
	    textView.setText(message);
	    
		// Show the Up button in the action bar.
		setupActionBar();
		
		int trialNum = 10, max = 9999, min = 1000;
		String ans;
		char[] ansArray;
		Random r = new Random();
		outerloop:
		while(true){			 
			ans = Integer.toString(r.nextInt(max - min + 1) + min);
			ansArray = new char[ans.length()];
			for(int i=0;i<4;i++){
				ansArray[i] = ans.charAt(i);
			}
			for(int i=0;i<4;i++){
    			for(int j=i+1;j<4;j++){
    				if(ansArray[i]==ansArray[j]){
    					continue outerloop;
    				}
    			}
    		}
			break;
		}    	
    	
        /* String ansStr = "\nThe answer is:" + ans;
    	TextView textView2 = (TextView) findViewById(R.id.hint);
    	textView2.append(ansStr); */
    	
        final Button button = (Button) findViewById(R.id.button_enter);
        button.setOnClickListener(new MyOnClickListener(trialNum,ans));        
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    public class MyOnClickListener implements OnClickListener{
        int trialNum;
        String ans;
        char[] ansArray;
        MyOnClickListener(int trialNum, String ans){
        	this.trialNum = trialNum;
        	this.ans = ans;
        	ansArray = new char[ans.length()];
        	for(int i=0;i<ans.length();i++){
        		ansArray[i] = ans.charAt(i);
            }
        }
        
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(trialNum<=0) return;
			trialNum--;
        	EditText edittext = (EditText) findViewById(R.id.enter_number);        	
        	String message, userAns = edittext.getText().toString();
        	edittext.setText("");
        	char[] userAnsArray = new char[userAns.length()];
        	int userAnsVal = Integer.parseInt(userAns), A = 0, B = 0;        	
        	for(int i=0;i<userAns.length();i++){
        		userAnsArray[i] = userAns.charAt(i);
            }
        	TextView textView = (TextView) findViewById(R.id.hint);
        	if(userAnsVal<1000 || userAnsVal>9999){        		
        		if(trialNum!=0){
        			message = "\nYou have " + trialNum + " trials remaining!" +
      				          " You should enter a number between 1000 and 9999" +
        					  " with four unique digits!";
        		}
        		else{
        			message = "\nYou have no trial remaining! Game over...";
        		}
        		textView.append(message);
        		return;
        	}
        	else{
        		for(int i=0;i<4;i++){
        			for(int j=i+1;j<4;j++){
        				if(userAnsArray[i]==userAnsArray[j]){
        					if(trialNum!=0){
        						message = "\nYou have " + trialNum + " trials remaining!" +
        	      				          " You should enter a number between 1000 and 9999" +
        	        					  " with four unique digits!";
        					}
        					else{
        						message = "\nYou have no trial remaining! Game over...";
        					}        					
        					textView.append(message);
        	        		return;
        				}
        			}
        		}
        		for(int i=0;i<4;i++){
        			for(int j=0;j<4;j++){
        				if(i==j && ansArray[i]==userAnsArray[j]){
        					A++;
        					break;
        				}
        				else if(i!=j && ansArray[i]==userAnsArray[j]){
        					B++;
        					break;
        				}
        			}
        		}
        	}
        	if(trialNum==0 && A!=4){
        		message = "\nYou have no trial remaining! Game over...";
        		textView.append(message);
        	}
        	else if(A==4){
        		message = "\nCongratulations, you win! The ans is: " + ans + ".";
        		textView.append(message);
        		trialNum = 0;
        	}
        	else{
        		message = "\nYou have " + trialNum + " trials remaining! The hint " +
        				  "is: " + A + "A" + B + "B. The number you entered is: " + 
        				  userAns + ".";
        		textView.append(message);
        	}
		}        	
    }
}
