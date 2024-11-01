/**
 * 
 * 	Each player's score card
 * 	Has various methods, for example updating the score, checking each
 * 	option choice, printing out the board, and taking the total
 * 
 * 	@author	Chloe He
 * 	@since 	October 23, 2024
 */ 

public class YahtzeeScoreCard
{
	int [] scores = new int [13]; // number of score options
	
	/** 
	 * 	Constructor - initializes scores
	 */ 
	public YahtzeeScoreCard() {
		for (int i = 0; i < 13; i++) scores[i] = -1;
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
	 * 	@param player - the player's yahtzee player 
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
	
	/**
	 * 	Prints the bottom footer
	 */
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
		
		// choice <= [number to select that choice]
		if (choice <= 6) numberScore(choice, dg);
		else if (choice <= 8) threeFourOfAKind(choice, dg);
		else if (choice == 9) fullHouse(dg);
		else if (choice == 10) smallStraight(dg);
		else if (choice == 11) largeStraight(dg);
		else if (choice == 12) chance(dg);
		else if (choice == 13) yahtzeeScore(dg);
						
	}
	
	/**
	 * 	Getter
	 * 	@param 	num	desired index
	 * 	@return	the score at that index
	 */
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
		for (int i = 0; i < 5; i++) { // 5 = number of die (per person)
			if (dg.getDiceValue(i) == choice) sum += choice;
		}
		scores[choice-1] = sum;
	}
	
	/**
	 *	Updates the scorecard for Three or Four Of A Kind choice.
	 *
	 * 	@param choice	if they wanted 3 of a kind (7) or 4 of a kind (8)
	 *	@param dg	The DiceGroup to score
	 */	
	public void threeFourOfAKind(int choice, DiceGroup dg) {
		
		// get frequency
		int[] freq = new int [6];
		
		for (int i = 0; i < 6; i++) freq[i] = 0; // 6 = number of die faces
		for (int i = 0; i < 5; i++) { // 5 = number of die (per person)
			freq[dg.getDiceValue(i)-1]++;
		}
		
		boolean works = false;
		
		for (int i = 0; i < 6; i++) {
			// if they chose choice 8 and 4 occurences exist
			if (choice == 8 && freq[i] >= 4) works = true;
			// if they chose choice 7 and 3 occurences exist
			else if (choice == 7 && freq[i] >= 3) works = true;
		}			
		
		// if they chose choice 8 (4 of a kind)
		if (choice == 8) {
			if (works) scores[7] = dg.getTotal();
			else scores[7] = 0;
		} else if (choice == 7) { // if they chose choice 7 (3 of a kind)
			if (works) scores[6] = dg.getTotal();
			else scores[6] = 0;
		}

	}
	
	/**
	 * 	If choice is 9 
	 * 	Checks for a full house (1 pair, 1 triple)
	 * 	If there is, scores is updated
	 * 
	 * 	@param dg	player's dice group
	 */
	public void fullHouse(DiceGroup dg) {
		// 6 = number of faces, 5 = number of die per person, scores[ind] = choice #ind
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
	
	/**
	 * 	If choice is 10
	 * 	Checks for a small straight (4 consecutive faces)
	 * 	If there is, scores is updated
	 * 
	 * 	@param dg	player's dice group
	 */
	public void smallStraight(DiceGroup dg) {
		// 6 = number of faces, 5 = number of die per person, scores[ind] = choice #ind
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
	
	/**
	 * 	If choice is 11
	 * 	Checks for a large straight (5 consecutive faces)
	 * 	If there is, scores is updated
	 * 
	 * 	@param dg	player's dice group
	 */
	public void largeStraight(DiceGroup dg) {
		// 6 = number of faces, 5 = number of die per person, scores[ind] = choice #ind
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
	
	/**
	 * 	If choice is 12 
	 * 	Updates scores to total value of dies' faces
	 * 
	 * 	@param dg	player's dice group
	 */
	public void chance(DiceGroup dg) {
		// 6 = number of faces, 5 = number of die per person, scores[ind] = choice #ind
		scores[11] = dg.getTotal();
	}
	
	
	/**
	 * 	If choice is 13
	 * 	Checks for a yahtzee (all the top faces are the same value)
	 * 	If there is, scores is updated
	 * 
	 * 	@param dg	player's dice group
	 */
	public void yahtzeeScore(DiceGroup dg) {
		// 6 = number of faces, 5 = number of die per person, scores[ind] = choice #ind
		int[] freq = new int [6];
		
		for (int i = 0; i < 6; i++) freq[i] = 0;
		for (int i = 0; i < 5; i++) {
			freq[dg.getDiceValue(i)-1]++;
		}
		
		for (int i = 0; i < 6; i++) {
			if (freq[i] == 5) scores[12] = 50;
		}
	}
	
	/**
	 * 	Gets the total of all the scores, used in Yahtzee endgame
	 * 	@return sum	the total
	 */
	public int scoreTotal() {
		int sum = 0;
		for (int i = 0; i < scores.length; i++) {
			if (scores[i] != -1) sum += scores[i];
		}
		return sum;
	}
}
