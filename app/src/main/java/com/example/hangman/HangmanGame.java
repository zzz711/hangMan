/*
  Programmer 1: Daniel Griffin
  Programmer 2: Jordan Ross
  Completed: 1/14/2014
*/


package com.example.hangman;

import java.util.ArrayList;

/**
 * <h2>Overview</h2>
 * 
 * <p>This class is used to implement that actual hangman game. It includes all of the functionality to 
 * play the game, such that the only thing a user of this class must do is create an instance, and 
 * supply a guess String (the function parses it to its first character). Each character guessed 
 * right increases the user score by 1. Each word guessed right increases the user score by 10.</p>
 * 
 * <p>The game progresses through each string passed to this object. For each individual word, the player gets 
 * a total of 7 guesses. For each guess, the user can either try to guess a character or a word. If they guess right, 
 * the character shows up in the word, and the score is increased. If the user guesses all of the characters or 
 * the word, the game progresses to the next word in the list and resets the number of guesses to 7. If the last word has 
 * been reached, the game goes into the "GAME_ENDED" state.</p>
 * 
 * <h3>Testing Summary</h3>
 * 
 * <p>The correctness of this class was tested in conjunction with the HangmanActivity. For more details 
 * on the testing conditions, consult the {@link com.example.hangman.HangmanActivity} notes.</p>
 * 
 * <p>Certain terminology is used for each kind of word in this game.</p>
 * <ul>
 * 	<li>"solutionString": This field represents the word that is currently trying to be guessed.</li>
 * 	<li>"currentString": This field represents the word that contains the letters of the word that have been previously guessed.</li>
 * 	<li>"getNumRemaining": This field represents the number of guesses a client has remaining.</li>
 * </ul>
 * 
 * @author Daniel Griffin
 * @author Jordan Ross
 * 
 * */
public class HangmanGame {
	
	//Public Class Members.
	/**Constant returned by getGameStatus() when the user has won the game.*/
	public static final int GAME_ENDED = 0;
	/**Constant returned by getGameStatus() when the game is still in progress.*/
	public static final int IN_PROGRESS = 2;
	
	//Private Members
	/**Variable used to hold the solution string, or the string that is trying to be guessed.*/
	private String solutionString;
	/**Variable that holds the number of guesses left for the user to try and guess the word. There is a maximum of 7.*/
	private int getNumRemaining;
	/**Variable that holds the current hangman string. This includes previously found letters as well as letters not found yet (represented by "_").*/
	private String currentString = " ";
	/**Variable that holds the index of the current string in the array of words to guess.*/
	private int currentStringIndex = -1;
	/**List of strings holding the words to be guessed. */
	private ArrayList<String> arrayOfWords;
	/**List of correct characters already guessed for the current word being guessed. It is used to limit scoring on correct characters previously 
	 * guessed*/
	private ArrayList<Character> arrayOfCorrectlyGuessedCharacters = new ArrayList<Character>();
	
	/**Field holding the current score for the user.*/
	private int currentScore = 0;
	
	/**
	 * Description: This fill constructor initializes the game to the first string to be guessed.
	 * 
	 * @param word An ArrayList of words that a player will be trying to guess.
	 * 
	 * */
	public HangmanGame(ArrayList<String> words) {
		if(words == null){
			arrayOfWords = new ArrayList<String>();
		}else{
			//Initialize the list of strings, and first string index.
			arrayOfWords = new ArrayList<String>(words);
			currentStringIndex = 0;
		}
		
		//Set each word in the array to its lowercase equivalent.
		for(int i = 0; i<arrayOfWords.size(); i++){
			arrayOfWords.set(i, arrayOfWords.get(i).toLowerCase());
		}
		
		//Set the first solution string.
		solutionString = arrayOfWords.get(0);
		
		//Set the number of guesses remaining for the first word.
		getNumRemaining = 7;
		
		//Initialize the current string.
		initializeCurrentString(currentStringIndex);

	}
	
	/*--------------------------------------------
	 					Private Methods.
	  --------------------------------------------*/
	/**
	 * Description: Initializes the string that displays the portions of the word guessed so far (e.x. "w _ r _"). 
	 * 
	 * @return true if the current word was initialized.
	 * 		   false if the index passed in was out of bounds.
	 * */
	private boolean initializeCurrentString(int index){
		//Clear the current string for the next word.
		currentString = " ";
		
		//If index is out of bounds, return false.
		if(index > arrayOfWords.size() || index < 0){
			return false;
		}
		
		//Initialize "currentString" based on the word at index
		for(int i = 0; i < arrayOfWords.get(index).length(); i++){
			currentString += "_ ";
		}
		
		return true;
	}
	
	/*--------------------------------------------
	  					Public Methods
	  --------------------------------------------*/
	/**
	 * Description: Method moves to the next word to guess, initializes the "solutionString", 
	 * "currentString", and "getNumRemaining" members of this class.
	 * 
	 * @return true if the game has moved to the next word.
	 *         false if the game has reached the end of the list of words.
	 * */
	public boolean moveToNextWord(){
		//Increment the current string index to the next index in the word array.
		currentStringIndex = (currentStringIndex + 1) % Integer.MAX_VALUE;
		
		//Clear the array of correctly guessed characters.
		arrayOfCorrectlyGuessedCharacters.clear();
		
		//If the index is out of the array bounds, end of array reached. Return false.
		if(currentStringIndex >= arrayOfWords.size()){
			return false;
		}else{
			//Get the new solution string.
			solutionString = arrayOfWords.get(currentStringIndex);
			//Initialize the guessing string.
			initializeCurrentString(currentStringIndex);
			//Reset the number of guesses remaining for the new word.
			getNumRemaining = 7;
			
			return true;
		}
		
	}
	
	/**
	 * Description: Method is used to get the current status of the game.
	 * 
	 * @return USER_WON if the user has won the game.
	 * 		   USER_LOST if the user has lost the game.
	 * 		   IN_PROGRESS if the game is still in progress (The user still has chances to guess the word)
	 * 
	 * */
	public int getGameStatus(){
		String tempCurrentString = new String(currentString);

		if(currentStringIndex > arrayOfWords.size()){
			return GAME_ENDED;
		}else if(currentStringIndex < arrayOfWords.size()){
			return IN_PROGRESS;
		}else{
			return -1;
		}

	}
	
	/**
	 * Description: Method accepts a guess, and checks to see if that guess is in the solution string. If it is, 
	 * it adds the letter to the current hangman string (including repeats) and returns the new hangman string.
	 * 
	 * @param guess the string representing the character guess.
	 * 
	 * @return returns the string containing previously guessed correct letters, as well as unguessed letters (represented by a "_").
	 * */
	public String guessCharacter(String guess){
		guess = guess.toLowerCase();
		String tempCurrentString = "";
		int index = 0; 
		boolean guessRight = false;
		
		for(int j = 0; j < solutionString.length(); j++){
		
			if(solutionString.charAt(j) != guess.charAt(0)){
				//Solution string char not equal to guess char, so continue.
				continue;
			}else{
				
				//This statement checks to make sure that a player cannot keep getting points by entering a character they already know to be true.
				if(arrayOfCorrectlyGuessedCharacters.contains(new Character(guess.charAt(0))) == false){
					//Increment the score.
					currentScore++;
					//Add the guessed character to the list of correctly guessed characters.
					arrayOfCorrectlyGuessedCharacters.add(guess.charAt(0));
				}
				
				//Clear tempCurrentString in case there is a repeated letter.
				tempCurrentString = "";
				
				//Set the flag to true.
				guessRight = true;
				
				//Loop through the current string to build a new string with the guess char added.
				for(int i = 0; i<currentString.length(); i++){
					if(i == ((2*j) + 1)){
						tempCurrentString += guess;
					}else if(i % 2 == 0){
						tempCurrentString += " ";
					}else{
						tempCurrentString += currentString.charAt(i);
					}
				}
			
				//Set the current string to the new value.
				currentString = tempCurrentString;
			}
		}
		
		if(guessRight == false){
			//Decrement the number of guesses remaining.
			getNumRemaining--;
			
			//If the number of guesses has been used up, move to the next word in the list.
			if(getNumRemaining <= 0){
				moveToNextWord();
			}
			
		}
		
		//Check to see if the word has been completely guessed. If it has, move to the next string.
		tempCurrentString = new String(currentString);
		if(tempCurrentString.replace(" ", "").replace("_", "").equals(solutionString) == true){
			moveToNextWord();
		}
		
		return currentString;
	}
	
	/**
	 * Description: Method accepts a string to guess and returns whether or not the guess matches the word that 
	 * is currently being guessed. If it does, the game progresses to the next word in the array, and increments the 
	 * score by 10 points. Otherwise, the game decrements the number of guesses left, and returns false.
	 * 
	 * @param guess The word you think is the solution.
	 * 
	 * @return true if the guess was correct.
	 * 		   false if the guess was not correct.
	 * */
	public boolean guessWord(String guess){
		if(guess.equals(solutionString) == true){
			currentScore += 10;
			moveToNextWord();
			return true;
		}else{
			//Decrement the number of guesses remaining and return false.
			getNumRemaining--;
			
			if(getNumRemaining <= 0){
				moveToNextWord();
			}
			
			return false;
		}
	}
	
	/**Description: Method returns the number of guesses the user has remaining.*/
	public int getNumRemaining(){
		return getNumRemaining;
	}
	
	/**Description: Method returns the current hangman string containing previously guessed correct letters, as well as unguessed letters (represented by a "_")*/
	public String getCurrentHangmanString(){
		return currentString;
	}

	/**Description: Method returns the solution string that the user is trying to guess.*/
	public String getCurrentSolutionString(){
		return solutionString;
	}
	
	/**Description: Method returns the current numeric score for the game.*/
	public int getCurrentScore(){
		return currentScore;
	}
	
}
