/**
 * 	A recreation of the game Yahtzee
 * 
 * 	Objective: Beat your opponent (another human player) by scoring more 
 * 	points than them.
 * 
 * 	For each turn, you can roll the dice, and reroll and hold certain 
 * 	dies up to 3 times. Then, you can choose a category that you would 
 * 	like to score. If it hasn’t been taken yet, the program will check 
 * 	if the category is valid, then give you a respective amount of 
 * 	points. 
 * 	
 * 	Point System:
 * 	Number (Options 1-6): points = number of that number’s occurrence in 
 * 	the 5 dies * that number
 * 	3 of a kind: if there’s 3 faces with the same number, points = sum 
 * 	of all 5 die’s top face
 * 	4 of a kind: if there’s 4 faces with the same number, points = sum 
 * 	of all 5 die’s top face
 * 	Full house: if there’s 3 faces with the same number and 2 faces with 
 * 	another, points = 25
 * 	Small straight: if there’s 4 consecutive number faces, points = 30	
 * 	Large straight: if there’s 5 consecutive number faces, points = 40
 * 	Yahtzee: 5 identical faces, points = 50
 * 	Chance: points = sum of all 5 die’s top face 
 * 
 * 	To run, cd into the directory with Yahtzee.java, YahtzeeScoreCard.java, 
 * 	YahtzeePlayer.java, Prompt.java, Dice.java, and DiceGroup.java, then 
 * 	type in your terminal "javac -source 1.8 -target 1.8 Yahtzee.java; 
 * 	java Yahtzee" and it should run
 * 
 *	@author Chloe He
 * 	@since	October 23, 2024
 */
 
public class Yahtzee
{
	private String name1, name2;
	private int curPlayer;
	private DiceGroup dice1, dice2;
	private boolean finished = false;
	private int round = 0;
	private YahtzeePlayer player1, player2;
	private YahtzeeScoreCard player1ScoreCard, player2ScoreCard;
	private boolean secondPlayerFirst = false;
		
	
	/** 
	 *	Constructor: initializes fields
	 */ 
	public Yahtzee() {
		dice1 = new DiceGroup();
		dice2 = new DiceGroup();
		player1 = new YahtzeePlayer();
		player2 = new YahtzeePlayer();
		player1ScoreCard = new YahtzeeScoreCard();
		player2ScoreCard = new YahtzeeScoreCard();
	}
	
	/**
	 * 	Main: calls method that runs the whole thing
	 */
	public static void main (String args[]) {
		Yahtzee yt = new Yahtzee();
		yt.runner();
	}
	
	/**
	 * 	Runner: runs the bulk of the program
	 * 	Gets the players names, finds who's the first player, plays
	 * 	the game by calling the methods that run each turn, find and 
	 * 	print the winners and final score totals
	 */ 
	public void runner() {
		printHeader();
		
		// get names
		do {
			name1 = Prompt.getString("Player 1, please enter your first name");
		} while (name1.equals(""));
		
		do { 
			name2 = Prompt.getString("Player 2, please enter your first name");
		} while (name2.equals(""));
		System.out.println();
		
		// find the first player
		curPlayer = getPlayerOne();
		while (curPlayer == -1) curPlayer = getPlayerOne();
		
		// play game
		while (!finished) {
			// print header
			printScore(false);
			System.out.println("\nRound " + (round + 1) + " of 13 rounds.\n\n");
			
			playTurn(player1, dice1, 0);
			printScore(true);
			int choice1;
			do {
				choice1 = Prompt.getInt(player1.getName() + ", now you need to make a choice. Pick a valid integer from the list above (1 - 13)");

			} while (player1ScoreCard.scores[choice1-1] != -1); 
			player1ScoreCard.changeScore(choice1, dice1);
			
			printScore(false);
			
			playTurn(player2, dice2, 0);
			printScore(true);
			int choice2;
			do {
				choice2 = Prompt.getInt(player2.getName() + ", now you need to make a choice. Pick a valid integer from the list above (1 - 13)");
			} while (player2ScoreCard.scores[choice2-1] != -1);
			player2ScoreCard.changeScore(choice2, dice2); // check if used already
			
			// update things after turn
			round++;
			curPlayer = (curPlayer + 1) % 2;
			if (round >= 12) finished = true;
		}
		
		int total1 = player1ScoreCard.scoreTotal(), total2 = player2ScoreCard.scoreTotal();
		if (secondPlayerFirst) System.out.println("\n" + name2 + "\t\tscore total = " + total2);
		System.out.println(name1 + "\t\tscore total = " + total1);
		if (!secondPlayerFirst) System.out.println(name2 + "\t\tscore total = " + total2);
		
		if (total1 > total2) System.out.println("\nCongratulations " + name1 + ". YOU WON!!!");
		else System.out.println("Congratulations " + name2 + ". YOU WON!!!\n");
	}
	
	/** 
	 * Prints the game's headers
	 */
	public void printHeader() {
		System.out.println("\n");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("| WELCOME TO MONTA VISTA YAHTZEE!                                                    |");
		System.out.println("|                                                                                    |");
		System.out.println("| There are 13 rounds in a game of Yahtzee. In each turn, a player can roll his/her  |");
		System.out.println("| dice up to 3 times in order to get the desired combination. On the first roll, the |");
		System.out.println("| player rolls all five of the dice at once. On the second and third rolls, the      |");
		System.out.println("| player can roll any number of dice he/she wants to, including none or all of them, |");
		System.out.println("| trying to get a good combination.                                                  |");
		System.out.println("| The player can choose whether he/she wants to roll once, twice or three times in   |");
		System.out.println("| each turn. After the three rolls in a turn, the player must put his/her score down |");
		System.out.println("| on the scorecard, under any one of the thirteen categories. The score that the     |");
		System.out.println("| player finally gets for that turn depends on the category/box that he/she chooses  |");
		System.out.println("| and the combination that he/she got by rolling the dice. But once a box is chosen  |");
		System.out.println("| on the score card, it can't be chosen again.                                       |");
		System.out.println("|                                                                                    |");
		System.out.println("| LET'S PLAY SOME YAHTZEE!                                                           |");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("\n");
	}
	
	/**
	 * 	Finds out who's the first player
	 * 	@return returns the first player, -1 if it was a tie
	 */
	public int getPlayerOne() {
		String trash = Prompt.getString("Let's see who will go first. " 
					+ name1 + ", please hit enter to roll the dice"); 
		dice1.rollDice();
		trash = Prompt.getString(name2 + ", it's your turn. Please hit "
					+ "enter to roll the dice");
		dice2.rollDice();
		
		int sum1 = dice1.getTotal();
		int sum2 = dice2.getTotal();
		
		System.out.println(name1 + ", you rolled a sum of " + sum1 + 
							", and " + name2 + ", you rolled a sum of " + 
							sum2 + ".");
		String winner = name2;
		if (sum1 > sum2) winner = name1;
		if (sum1 == sum2) {
			System.out.println("\nTie! Roll again.");
			return -1;
		}
		
		System.out.println(winner + ", since your sum was higher, "
					+ "you'll roll first.");
		if (winner.equals(name1)) {
			player1.setName(name1);
			player2.setName(name2);
			return 0;
		} else {
			player1.setName(name2);
			player2.setName(name1);
			secondPlayerFirst = true;
			return 1;
		}
	}
	
	/** 
	 * plays each turn
	 * @param	player	each player's YahtzeePlayer
	 * @param 	dice	each player's dies
	 * @param	turnCount	number of rerolls taken this turn
	 */

	public void playTurn(YahtzeePlayer player, DiceGroup dice, int turnCount) {
		// if turn count up -> break
		if (turnCount >= 3) return; // 3 = max number of rerolls
		
		// find players name
		String name = player.getName();
		
		// if first turn -> set all dice values
		if (turnCount == 0) {
			String trash = Prompt.getString(name + ", it's your turn to "
					+ "play. Please hit enter to roll the dice");
			dice.rollDice(); 
		}
		
		// hold + reroll
		String hold = Prompt.getString("Which di(c)e would you like to keep? Enter "
					+ "the values you'd like to 'hold' without \nspaces. "
					+ "For examples, if you'd like to 'hold' die 1, 2, "
					+ "and 5, enter 125 \n(enter -1 if you'd like to end "
					+ "the turn)");
		
		// if no change -> break
		if (hold.equals("-1")) return; // -1 -> if there's no holding
		
		// roll the dice, go to next move
		dice.rollDice(hold);
		playTurn(player, dice, turnCount + 1);
	}

	/**
	 * prints the Yahtzee score card
	 * @param printFooter - whether or not to print the footer / last line
	 */
	public void printScore(boolean printFooter) {
		player1ScoreCard.printCardHeader();
		if (secondPlayerFirst) {
			player2ScoreCard.printPlayerScore(player2);
			player1ScoreCard.printPlayerScore(player1);
		} else {
			player1ScoreCard.printPlayerScore(player1);
			player2ScoreCard.printPlayerScore(player2);
		}
		if (printFooter) player1ScoreCard.printCardFooter();
	}
}
