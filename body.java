package package1;

import java.util.Scanner;
/**
 * This is the main frame of the Wheel of Fortune game. It calls the classes "Spinner" and "Phrases
 * @version 1.0
  */

public class body {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner (System.in);
		String userInput;
		String wheelResult;
		char userGuess;
		int pointsAdded = 0; //points accrued in the current round
		int bonusTotal = 0; //this is delivered from the Phrase class later and lowers each time a player guesses a letter. So the quicker it is solved, the more bonus points they score
		Spinner newSpinner = new Spinner();
		Phrases newPhrases = new Phrases();
		 
		System.out.println("This is Wheel of Fortune!");
		System.out.println("Type number of players to begin");
		
		int numberOfPlayers = sc.nextInt();
		int[] playerScores = new int[numberOfPlayers]; //players score stored here
		int currentPlayer = 0;  //first element of array is always 0. So you add 1 for each new player. starting 0,1,2,3... 
		
		
		System.out.println("Player 1, Type spin to begin");
	//spinning the wheel and showing the results
		
		do {			
			userInput = sc.nextLine();	
		
			if (userInput.equals("spin")){
			
				wheelResult = newSpinner.spinWheel();
			
				switch (wheelResult)
				{
				  case "Bankrupt":
				    playerScores[currentPlayer] = 0;
				    System.out.println("Sorry, Player " +  (currentPlayer + 1) + ", bankrupt!");
				    currentPlayer = NextPlayer(currentPlayer, numberOfPlayers); //this function is detailed at the bottom of the code
				    System.out.println("Start your turn, Player " + (currentPlayer + 1));
				    
				    break;
				    
				  case "Lose a Turn":
					System.out.println("Sorry, Player " +  (currentPlayer + 1) + ", you lose a turn");
					currentPlayer = NextPlayer(currentPlayer, numberOfPlayers);
					System.out.println("Start your turn, Player " + (currentPlayer + 1));
					
					break;
				    
				  case "Free Spin":
				    System.out.println("Spin Again");   
				    break;
				    
				  default: 
				    System.out.println("You landed on"+" £" + wheelResult + ". Guess a letter. Vowels cost £250");
				    int wheelValue = Integer.parseInt(wheelResult);
				    newPhrases.gameBoard();
				          
				    //Guess calculations
				    int letterCount;
				    userGuess = sc.next().charAt(0);
				    
				   //Checks if vowels were used in guess and docks before phrase comparison
				   if ((userGuess == 'a') || (userGuess == 'e') || (userGuess == 'i') || (userGuess == 'o')	|| (userGuess == 'u')){
					   
					   playerScores[currentPlayer] -= 250;
					   System.out.println("£250 docked from score for vowel use");
				    }
				   
				    letterCount = newPhrases.makeGuess(userGuess);
				          
				    	if (letterCount >= 1)
				    		{
				    			pointsAdded = letterCount * wheelValue;
				    				System.out.println("Correct! This letter was found " + letterCount + " times(s)");
				    					System.out.println("£" + pointsAdded + " has been added to your score");
				    						playerScores[currentPlayer] += pointsAdded;
				    							System.out.println("Your new score is £" + playerScores[currentPlayer]);
				    							System.out.println("Type spin to continue or guess to solve the phrase");
				    			}
				    		else 
				    			{
				    			currentPlayer = NextPlayer(currentPlayer, numberOfPlayers);
						    	System.out.println("Sorry that letter isn't present. Start your turn, Player " + (currentPlayer + 1));
				    			}
				}
			}
			//instead of spinning, a player can choose to guess the whole phrase
			
			if (userInput.equals("guess")){
				System.out.println("Enter your full phrase guess");
				String userFullGuess;
				String guessCheck;
				
				userFullGuess = userInput = sc.nextLine();	
				guessCheck = newPhrases.fullGuess(userFullGuess);
				
				if (guessCheck.equals("incorrect")){
					currentPlayer = NextPlayer(currentPlayer, numberOfPlayers);
					System.out.println("Sorry that is incorrect. Start your turn, Player " + (currentPlayer + 1)); //incorrect guess dock 5 from the bonus count.
				}
				else {
				bonusTotal = Integer.parseInt(guessCheck);
				}
			}
			
	//Game checks if whole phrase has been guessed and ends if true
			
		} while ((!newPhrases.gameComplete()) && (!userInput.equals("end")));
		if (userInput.equals("end")) {
			System.out.println("Game terminated");
		}
		
			else {
				System.out.println("Congratulations, Player " + (currentPlayer + 1) + ", you have completed the phrase");
				System.out.println("Score = £" + playerScores[currentPlayer]);
				System.out.println("Bonus Points = £" + bonusTotal);
				playerScores[currentPlayer] += bonusTotal;
				System.out.println("Final score = £" +playerScores[currentPlayer]);
				
				 sc.close();
				}
	}
	

	/**
	 * this function moves the game on so the next player can spin the wheel. 
	 * if it is at the last player then 0 is returned to start from the beginning
	 * 
	 * @param currentPlayer the player whose turn it currently is
	 * @param numberOfPlayers the total number of people playing the game
	 * @return 0 or +1 depending on whose turn it is
	 */
	private static int NextPlayer(int currentPlayer, int numberOfPlayers)
	{
		if ((currentPlayer + 1) == numberOfPlayers) 
			{
			return 0;
			}
			else
			{
			return currentPlayer + 1;
			}
	}

}


