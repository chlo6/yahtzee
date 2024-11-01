//what if same chosen twice
public class YahtzeeScoreCard
{
	int [] scores = new int [13]; 
	
	public YahtzeeScoreCard() {
		for (int i = 0; i < 13; i++) scores[i] = -1;
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
		else if (choice <= THREE_FOUR_OF_A_KIND) threeFourOfAKind(choice, dg);
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
		for (int i = 0; i < 13; i++) {
			if (scores[i] > -1) {
				System.out.printf(" %2d |", scores[i]);
			}
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
	 

	public void changeScore(int choice, DiceGroup dg) {
		
		if (choice <= 6) numberScore(choice, dg);
		else if (choice <= 8) threeFourOfAKind(choice, dg);
		else if (choice == 9) fullHouse(dg);
		else if (choice == 10) smallStraight(dg);
		else if (choice == 11) largeStraight(dg);
		else if (choice == 12) chance(dg);
		else if (choice == 13) yahtzeeScore(dg);
						
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
	public void numberScore(int choice, DiceGroup dg) {
		int sum = 0;
		for (int i = 0; i < 5; i++) {
			if (dg.getDiceValue(i) == choice) sum += choice;
		}
		scores[choice-1] = sum;
	}
	
	/**
	 *	Updates the scorecard for Three or Four Of A Kind choice.
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void threeFourOfAKind(int choice, DiceGroup dg) {
		
		// get frequency
		int[] freq = new int [6];
		
		for (int i = 0; i < 6; i++) freq[i] = 0;
		for (int i = 0; i < 5; i++) {
			freq[dg.getDiceValue(i)-1]++;
		}
		
		boolean works = false;
		
		for (int i = 0; i < 6; i++) {
			if (choice == 8 && freq[i] >= 4) works = true;
			else if (choice == 7 && freq[i] >= 3) works = true;
		}			
		
		if (choice == 8) {
			if (works) scores[7] = dg.getTotal();
			else scores[7] = 0;
		} else if (choice == 7) {
			if (works) scores[6] = dg.getTotal();
			else scores[6] = 0;
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
		
		if (found2 && found3) scores[8] = 25;
		else scores[8] = 0;
	}
	
	public void smallStraight(DiceGroup dg) {
		int[] freq = new int [6];
		
		for (int i = 0; i < 6; i++) freq[i] = 0;
		for (int i = 0; i < 5; i++) {
			freq[dg.getDiceValue(i)-1]++;
		}
		
		boolean works = false;
		
		for (int i = 0; i < 3; i++) {
			if (freq[i] > 0 && freq[i+1] > 0 && freq[i + 2] > 0 && freq[i + 3] > 0) works = true;
		}
			
		
		if (works) scores[9] = 30;
		else scores[9] = 0;
	}
	
	public void largeStraight(DiceGroup dg) {
		int[] freq = new int [6];
		
		for (int i = 0; i < 6; i++) freq[i] = 0;
		for (int i = 0; i < 5; i++) {
			freq[dg.getDiceValue(i)-1]++;
		}
		
		boolean works = true;
		
		for (int i = 1; i < 4; i++) {
			if (freq[i] != 1) works = false;
		}
		
		if (works) scores[10] = 40;
		else scores[10] = 0;
	}
	
	public void chance(DiceGroup dg) {
		scores[11] = dg.getTotal();
	}
	
	public void yahtzeeScore(DiceGroup dg) {
		int[] freq = new int [6];
		
		for (int i = 0; i < 6; i++) freq[i] = 0;
		for (int i = 0; i < 5; i++) {
			freq[dg.getDiceValue(i)-1]++;
		}
		
		for (int i = 0; i < 6; i++) {
			if (freq[i] == 5) scores[12] = 50;
		}
	}
	
	public int scoreTotal() {
		int sum = 0;
		for (int i = 0; i < scores.length(); i++) sum += scores[i];
		return sum;
	}
}
