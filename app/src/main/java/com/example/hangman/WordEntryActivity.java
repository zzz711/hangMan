/*
  Programmer 1: Daniel Griffin
  Programmer 2: Jordan Ross
  Completed: 1/14/2014
*/
package com.example.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.*;

/**
 * <h2>Overview</h2>
 * 
 * <p>This class is used to retrieve a string that will be used by the Hangman game. The string is currently limited to 
 * 15 characters. However, the HangmanGame class can use a string with any number of characters.</p>
 * 
 * <h3>Testing Summary</h3>
 * <h4>Testing Strategy</h4>
 * <p>The strategy that was used for testing the WordEntry Activity was the test of all boundary conditions
 * and to ensure that each branch in the logic was taken at least once so that every line was covered. No
 * unit tests were written but instead we manually tested the application by entering in a variety of inputs 
 * and observing the responses.</p>
 * <h4>Test Cases</h4>
 * <ul>
 * 	<li>Do not enter a word and press "Set Word". -- PASSED -- Observed that the appropriate
 * 		notification and did not process the empty string.</li>
 * 	<li>Press "Set Word" after entering in a string of more than 15 characters. -- PASSED -- Observed 
 * 		appropriate notification and did not accept the word.</li>
 * 	<li>Entered in an acceptable word such as "Hello". -- PASSED -- The next activity received the correct
 * 		word and was set as the solution.</li>
 * </ul>
 * 
 * @author Daniel Griffin
 * @author Jordan Ross
 * 
 * */
public class WordEntryActivity extends Activity {

    String word;
	
	/**Description: Key used for transmitting and retrieving a selected hangman word from the WordEntryActivity.*/
	public final static String EXTRA_MESSAGE = "com.example.hangman.MESSAGE";
	
	/**Description: Method used to load GUI and instantiate members of the Activity.*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word_entry_layout);
	}

	/**Description: Method used to create an options menu and inflate it for this activity.*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Description: This method is called by the xml layout for this activity. It acts as the "OnClickListener" for the setWordButton.
	 * 
	 * @param view View class that calls this method.
	 * */
    public void setWord(View view)
    {
        //Create an intent for starting the HangmanActivity.
        Intent intent = new Intent(this, HangmanActivity.class);
        EditText wordEditText = (EditText) findViewById(R.id.enteredWordEditText);

        word = wordEditText.getText().toString();
        word.toLowerCase();

        //Limit the word length to less than 15 characters.
		/*if (word.length() > 12)
		{
			Toast.makeText(wordEditText.getContext(), "Enter a word less than 12 characters!", Toast.LENGTH_SHORT).show();
			return;
		}*/
        //Limit the word length to being greater than 0.
        if (word.length() == 0)
        {
            Toast.makeText(wordEditText.getContext(), "Enter a word!", Toast.LENGTH_SHORT).show();
            return;
        }
	/*	else
		{
			//Put the string in an intent.
			intent.putExtra(EXTRA_MESSAGE, word);
			//Start the HangmanActivity.
			startActivity(intent);
		}*/

    }

    /**
     * Description: This method is called by the xml layout for this activity. It acts as the "OnClickListener" for the userNameButton.
     *
     * @param view View class that calls this method.
     * */
    public void setName(View view)
    {
        //Create an intent for starting the HangmanActivity.
        Intent intent = new Intent(this, HangmanActivity.class);
        EditText userNameEditText = (EditText) findViewById(R.id.userNameButton);

        //creates a new bundle to store the username and phrase entered
        Bundle b = new Bundle();

        //gets the username from the editText
        String name = userNameEditText.getText().toString();
        name.toLowerCase(); //converts all letters to lower case


        b.putString("Word", word); //inserts the phrase entered by the user into the bundle with the key Word
        b.putString("userName", name); ////inserts the username entered by the user into the bundle with the key userName

        //checks to see if the username is empty
        if (name.length() == 0)
        {
            Toast.makeText(userNameEditText.getContext(), "Enter a username!", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            //Put the string in an intent.
            intent.putExtra(EXTRA_MESSAGE, b);
            //Start the HangmanActivity.
            startActivity(intent);
        }
    }

}
