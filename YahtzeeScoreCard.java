//what if same chosen twice
public class YahtzeeScoreCard
{
	int [] scores = new int [13]; 
	
	public YahtzeeScoreCard() {
		for (int i = 0; i < 13; i++) scores[i] = 0;
	}
	
	private final int NUMBER_SCORE = 6;
	private final int THREE_FOUR_OF_A_KIND = 8;
	private final int FULL_HOUSE = 9;
	private final int SMALL_STRAIGHT = 10;
	private final int LARGE_STRAIGHT = 11;
	private final int CHANCE = 12;
	private final int YAHTZEE_SCORE = 13;
	
	public void updateScore(int choice, DiceGroup dg) {
		if (choice <= NUMBER_SCORE) numberScore(choice, dg);
		else if (choice <= THREE_FOUR_OF_A_KIND) threeFourOfAKind(dg);
		else if (choice == FULL_HOUSE) fullHouse(dg);
		else if (choice == SMALL_STRAIGHT) smallStraight(dg);
		else if (choice == LARGE_STRAIGHT) largeStraight(dg);
		else if (choice == CHANCE) chance(dg);
		else if (choice == YAHTZEE_SCORE) yahtzeeScore(dg);
	}
	
	/**
	 *  Print the scorecard header
	 */
	public void printCardHeader() {
		System.out.println("\n");
		System.out.printf("\t\t\t\t\t       3of  4of  Fll Smll Lrg\n");
		System.out.printf("  NAME\t\t  1    2    3    4    5    6   Knd  Knd  Hse " +
						"Strt Strt Chnc Ytz!\n");
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}
	
	/**
	 *  Prints the player's score
	 */
	public void printPlayerScore(YahtzeePlayer player) {
		System.out.printf("| %-12s |", player.getName());
		for (int i = 1; i < 14; i++) {
			if (getScore(i) > -1)
				System.out.printf(" %2d |", getScore(i));
			else System.out.printf("    |");
		}
		System.out.println();
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}
	
	// print bottom score
	public void printCardFooter() {
		System.out.printf("     \t\t  1    2    3    4    5    6    7    8    9   10   11   12   13\n");
	}


	/**
	 *  Change the scorecard based on the category choice 1-13.
	 *
	 *  @param choice The choice of the player 1 to 13
	 *  @param dg  The DiceGroup to score
	 *  @return  true if change succeeded. Returns false if choice already taken.
	 */
	public boolean changeScore(int choice, DiceGroup dg) {
		if (scores[choice-1] != 0) return false;
		
		if (choice == 7 || choice == 8) { // of a kind -> sum
			int sum = 0;
			for (int i = 0; i < 5; i++) sum += dg.getDiceValue(i);
			
			scores[choice-1] = sum;
		} 
		//update
		
		return true;
	}
	
	public int getScore(int num) {
		return scores[num-1];
	}
	
	/**
	 *  Change the scorecard for a number score 1 to 6
	 *
	 *  @param choice The choice of the player 1 to 6
	 *  @param dg  The DiceGroup to score
	 */
	public void numberScore(int choice, DiceGroup dg) {}
	
	/**
	 *	Updates the scorecard for Three or Four Of A Kind choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void threeFourOfAKind(DiceGroup dg) {
		
		// get frequency
		int[] freq = new int [6];
		
		for (int i = 0; i < 6; i++) freq[i] = 0;
		for (int i = 0; i < 5; i++) {
			freq[dg.getDiceValue(i)-1]++;
		}
		
		for (int i = 0; i < 6; i++) {
			if (freq[i] >= 4) { // 4 of a kind
				changeScore(8, dg);
			} else if (freq[i] >= 3) { // 3 of a kind
				changeScore(7, dg);
			}
		}			
	}
		
	public void fullHouse(DiceGroup dg) {
		boolean found2 = false, found3 = false;
		// get frequency
		int[] freq = new int [6];
		
		for (int i = 0; i < 6; i++) freq[i] = 0;
		for (int i = 0; i < 5; i++) {
			freq[dg.getDiceValue(i)-1]++;
		}
		
		for (int i = 0; i < 6; i++) {
			if (freq[i] == 2) found2 = true;
			else if (freq[i] == 3) found3 = true;
		}	
		
		if (found2 && found3) changeScore(9, dg);
	}
	
	public void smallStraight(DiceGroup dg) {
		
	}
	
	public void largeStraight(DiceGroup dg) {}
	
	public void chance(DiceGroup dg) {}
	
	public void yahtzeeScore(DiceGroup dg) {
		// get frequency
		int[] freq = new int [6];
		
		for (int i = 0; i < 6; i++) freq[i] = 0;
		for (int i = 0; i < 5; i++) {
			freq[dg.getDiceValue(i)-1]++;
		}
		
		for (int i = 0; i < 6; i++) {
			if (freq[i] == 5) changeScore(13, dg);
		}		
	}
}
