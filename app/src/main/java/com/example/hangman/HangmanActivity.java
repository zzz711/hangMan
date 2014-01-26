/*
  Programmer 1: Daniel Griffin
  Programmer 2: Jordan Ross
  Completed: 1/14/2014
*/

package com.example.hangman;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * <h2>Overview</h2>
 * 
 * <p>This class holds the main functionality of the Hangman App. It shows the 
 * user how many chances he has left, the previously found letters, and supplies a method for 
 * inputing guesses. When the user has won, a "Winner!!!" string will appear in the top TextView. 
 * When the user has lost, a "Looser..." string will appear in the top TextView.</p>
 * 
 * <h3>Testing Summary</h3>
 * <h4>Testing Strategy</h4>
 * <p>The strategy that was used for testing the Hangman Activity was the test of all boundary conditions
 * and to ensure that each branch in the logic was taken at least once so that every line was covered. No
 * unit tests were written but instead we manually tested the application by playing the game and observing
 * the responses.</p>
 * <h4>Test Cases</h4>
 * <ul>
 * 	<li>Press "Guess" after not entering in a character. -- PASSED -- Observed that the appropriate
 * 		notification and did not process the empty character.</li>
 * 	<li>Press "Guess" after entering in a string of more than 1 character. -- PASSED -- Observed 
 * 		appropriate notification and did not accept the guess.</li>
 * 	<li>Entered an "H" when the word was "Hello" and pressed guess. -- PASSED -- Observed that the
 * 		guess was accepted and was displayed in the word as well as no letter in the "hangman" string
 * 		was added".</li>
 * 	<li>Entered an "E" when the word was "Hello" and pressed guess. -- PASSED -- Accepted the guess
 * 		correctly even though the case did not match.</li>
 * 	<li>Entered an "l" when the word was "Hello" and pressed guess. -- PASSED -- Accepted the guess
 *		and placed the "l" character in the two locations.</li>
 *	<li>Entered in the last correct guess in word and pressed guess. -- PASSED -- The message "Winner!!!"
 *		was displayed as it should and the guess button was disabled and the replay button was enabled.
 *	<li>Entered in all incorrect guesses. -- PASSED -- The next character in the message "HANGMAN" 
 *		was displayed each time the guess was incorrect. When the seventh guess was incorrect it displayed
 *		"Looser" and disabled the guess button and enabled the replay button.
 * </ul>
 * 
 * @author Daniel Griffin
 * @author Jordan Ross
 * 
 * */
public class HangmanActivity extends Activity {

	//Private Members
	/**Reference to the HangmanGame object used to perform the game.*/
	private HangmanGame game;
	
	//Private UI Members
	/**Reference to the View holding the word that is trying to be guessed.*/
	private TextView outputText;
	/**Reference to the View used to input guesses.*/
	private EditText guessEditText;
	/**Reference to the Button used to submit a guess.*/
	private Button guessButton;
	/**Reference to a button used to indicate the user wants to play again.*/
	private Button replayButton;
	/**Reference to the View showing the "HANGMAN" letters indicating the chances left for a guess.*/
	private TextView hangmanTextView;
	
	/**Description: Method initializes this Activity, gets GUI references, and defines the "OnClickListeners" for the buttons.*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Load the GUI.
		setContentView(R.layout.hangman_activity_layout);
		
		//Get references.
		outputText = (TextView)findViewById(R.id.outputTextView);
		guessEditText = (EditText)findViewById(R.id.guessEditText);
		guessButton = (Button)findViewById(R.id.guessButton);
		replayButton = (Button)findViewById(R.id.replayButton);
		hangmanTextView = (TextView)findViewById(R.id.hangmanTextView);
		
		//Get the hangman game word
		ArrayList<String> words = new ArrayList<String>();
		words.add(getIntent().getStringExtra(WordEntryActivity.EXTRA_MESSAGE));
			
		//Create a HangmanObject and pass it the game word.
		game = new HangmanGame(new ArrayList<String>(words));
		
		//Get the current string and set it to the outputTextView.
		outputText.setText(game.getCurrentHangmanString());
		
		//Set the replayButton OnClickListener.
		replayButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//If this button is clicked, finish this activity and return to the "WordEntryActivity".
				finish();
			}
		});
		
		//Set the guessButotn OnClickListener.
		guessButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Get the edit text string.
				String guess = guessEditText.getText().toString();
				
				//If no text, make toast and return.
				if(guess.length() == 0){
					Toast.makeText(guessEditText.getContext(), "Enter guess", Toast.LENGTH_SHORT).show();
					return;
				}
				
				//If the text is more than one character, make a toat and return.
				if (guess.length() > 1)
				{
					Toast.makeText(guessEditText.getContext(), "You can only guess 1 character at a time!", Toast.LENGTH_SHORT).show();
					return;
				}
				
				//Set the guess string to the value of the first char.
				guess = String.valueOf(guess.charAt(0));
				
				//Pass the guessed char to the game object.
				String outputString = game.guessCharacter(guess);
				//Check whether or not the game has ended.
				int status = game.getGameStatus();
				
				int temp = 0;
				
				if(status == HangmanGame.GAME_ENDED){
					//Set the output text.
					outputText.setText(outputString);
					
					/**
					 * 
					 * ADD CODE TO START THE HIGH SCORE ACTIVITY.
					 * 
					 * */
					
					//Enable replay button.
					replayButton.setEnabled(true);
					//Disable the guess button.
					guessButton.setEnabled(false);
				}else if(status == HangmanGame.IN_PROGRESS){
					int numTriesLeft = game.getNumRemaining();
					
					if(numTriesLeft == 7){
						hangmanTextView.setText("       ");
					}else if(numTriesLeft == 6){
						hangmanTextView.setText("H      ");
					}else if(numTriesLeft == 5){
						hangmanTextView.setText("HA     ");
					}else if(numTriesLeft == 4){
						hangmanTextView.setText("HAN    ");
					}else if(numTriesLeft == 3){
						hangmanTextView.setText("HANG   ");
					}else if(numTriesLeft == 2){
						hangmanTextView.setText("HANGM  ");
					}else if(numTriesLeft == 1){
						hangmanTextView.setText("HANGMA ");
					}
					
					outputText.setText(outputString);
				}
				
			}
		});
		
		
	}

	
	
}
