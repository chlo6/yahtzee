/*
 * comment, constants
 * plaeyer 1 always goes first -> fix
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
	
	public Yahtzee() {
		dice1 = new DiceGroup();
		dice2 = new DiceGroup();
		player1 = new YahtzeePlayer();
		player2 = new YahtzeePlayer();
		player1ScoreCard = new YahtzeeScoreCard();
		player2ScoreCard = new YahtzeeScoreCard();
	}
	
	public static void main (String args[]) {
		Yahtzee yt = new Yahtzee();
		yt.runner();
	}
	
	public void runner() {
		printHeader();
		
		// get names
		do {
			name1 = Prompt.getString("Player 1, please enter your first name");
		} while (name1.equals(""));
		player1.setName(name1);
		
		do { 
			name2 = Prompt.getString("Player 2, please enter your first name");
		} while (name2.equals(""));
		player2.setName(name2);
		System.out.println();
		
		// find the first player
		curPlayer = getPlayerOne();
		while (curPlayer == -1) getPlayerOne();
		
		// play game
		while (!finished) {
			// print header
			printScore();
			System.out.println("\nRound " + (round + 1) + " of 13 rounds.\n\n");
			
			playTurn(0, dice1, 0);
			countScore(0, dice1); 
			printScore();
			int choice1 = Prompt.getInt(name1 + ", now you need to make a choice. Pick a valid integer from the list above (1 - 13)");
			player1ScoreCard.changeScore(choice1, dice1);
			
			printScore();
			playTurn(1, dice2, 0);
			countScore(0, dice2);
			printScore();
			int choice2 = Prompt.getInt(name1 + ", now you need to make a choice. Pick a valid integer from the list above (1 - 13)");
			player2ScoreCard.changeScore(choice2, dice2);
			
			// update things after turn
			round++;
			curPlayer = (curPlayer + 1) % 2;
			if (round >= 12) finished = true;
		}
	}
	
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
		if (sum1 == sum2) return -1;
		
		System.out.println(winner + ", since your sum was higher, "
					+ "you'll roll first.");
		if (winner.equals(name1)) return 0;
		return 1;
	}
	
	public void playTurn(int playerNumber, DiceGroup dice, int turnCount) {
		// if turn count up -> break
		if (turnCount >= 3) return;
		
		// find players name
		String name = name1;
		if (playerNumber == 1) name = name2;
		
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
		if (hold.equals("-1")) return;
		
		// roll the dice, go to next move
		dice.rollDice(hold);
		playTurn(playerNumber, dice, turnCount + 1);
	}
	
	public void countScore (int playerNumber, DiceGroup dice) {
	}
	
	public void printScore() {
		YahtzeeScoreCard yzs = new YahtzeeScoreCard();
		yzs.printCardHeader();
		yzs.printPlayerScore(player1);
		yzs.printPlayerScore(player2);
		yzs.printCardFooter();
	}
}
