import java.util.Scanner;
/*
 * Name: Robert C. Gutierrez
 * Purpose: The purpose of this program is to simulate a game of Black Jack between one
 * player and the dealer. Currently there is no support for multiple players but future
 * versions of this game may have that one day.
 * 
 * Features:
 * Character creation with name and wallet implementations
 * Real card simulation! Cards given at random!
 * Unlimited games so long as the player has money!
 * 
 * Missing:
 * Card counting since currently cards are chosen at random from an infinite pool
 * Lack of multiple players
 * No GUI
 * Lack of splitting
 * 
 */

public class BlackJack {
	
	// Used to determine a card's suit
	private static String[] allSuits = {"Spades", "Diamonds", "Clubs", "Hearts"};
	// Used to determine a card's rank
	private static String[] allRanks = {"Ace", "Two", "Three", "Four", "Five", "Six",
			"Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
	
	public static void main(String[] args) {
		// Ask if the player wants to play BlackJack
		Scanner keyboard = new Scanner(System.in);
		if(wantToPlay(keyboard)) {
			// Ask for player name and amount of money
			String playerName = askName(keyboard);
			int playerMoney = askMoney(keyboard);
			// Generate the player and the dealer
			Player currentPlayer = newPlayer(playerName, playerMoney);
			Player dealer = newPlayer("dealer", Integer.MAX_VALUE);
			// Time to play the game
			playGame(currentPlayer, dealer, keyboard);
		}
		keyboard.close();
	}
	
	// Creates a new player
	public static Player newPlayer(String name, int money) {
		return new Player(name, money);
	}
	
	// Asks if the player wants to play BlackJack
	public static boolean wantToPlay(Scanner keyboard) {
		System.out.println("Would you like to play a game of BlackJack? Enter y for yes and anything else for no.");
		String answer = keyboard.nextLine();
		return answer.length() > 0 && answer.toLowerCase().charAt(0) == 'y';
	}
	
	// Ask for the player's name
	public static String askName(Scanner keyboard) {
		String answer = null;
		// Error Check: Make sure null isn't passed in
		while(answer == null) {
			System.out.println("What is your name?");
			// Check if string and not null. Assume correct for now
			answer = keyboard.nextLine();
			if(answer == null) {
				System.out.println("Hey! That's not an amount!");
			}
		}
		return answer;
	}
	
	// Ask the player how much money they have
	public static int askMoney(Scanner keyboard) {
		String answer = null;
		while(answer == null) {
			System.out.println("How much money are you willing to play with?");
			answer = keyboard.nextLine();
			if(answer == null) {
				System.out.println("Hey! That's not an amount!");
			}
		}
		// make sure to check if int. Assume int for now. *******
		return Integer.parseInt(answer);
	}
	
	// Plays out the game
	public static void playGame(Player currentPlayer, Player dealer, Scanner keyboard) {
		int currentPot = 0;
		// Starting bet is $5
		if(currentPlayer.getMoney() >= 5) {
			// Ask player how much they want to bet
			System.out.println("How much do you want to bet? (Multiple of $5)");
			// Error check later
			boolean notEnoughMoney = true;
			int betAmount = 0;
			// Ask for a reasonable amount to bet
			while(notEnoughMoney) {
				// Error check: make sure that they enter a number that's an integer
				betAmount = Integer.parseInt(keyboard.nextLine());
				// Player has enough money to bet given amount
				if(currentPlayer.enoughMoney(betAmount)) {
					notEnoughMoney = false;
				} else { // Player does not have enough money to bet given amount
					System.out.println("You do not have that amount of money to bet!");
					System.out.println("Pick another amount!");
				}
			}
			// Deduct bet from player's wallet and add to pool
			currentPlayer.deductMoney(betAmount);
			currentPot += betAmount;
			// With bets in place start the game
			// Dealer deals two cards to player and themselves
			for(int i = 0; i < 2; i++) {
				currentPlayer.addCard(new PlayingCard());
				dealer.addCard(new PlayingCard());
			}
			// Print out what cards the player has and the first card the dealer has
			System.out.println("You eye the dealer's hand and notice that they have: " + 
					allRanks[dealer.peek().getRank() - 1] + " of " + allSuits[dealer.peek().getSuit() - 1]);
			System.out.print("You currently have: " + allRanks[currentPlayer.peek().getRank() - 1] + " of " + 
					allSuits[currentPlayer.peek().getSuit() - 1]);
			System.out.println(" and " + allRanks[currentPlayer.getCard(1).getRank() - 1] + " of " + 
					allSuits[currentPlayer.getCard(1).getSuit() - 1]);
			System.out.println("Totaling to : " + currentPlayer.getCardTotal());
			// Ask if the player wants to hit or stay
			System.out.println("Would you like to hit or stay? h for hit and anything else to stay.");
			boolean stay = false;
			// Keep hitting until the player decides to stay or busts
			while(!stay) {
				String playerDecision = keyboard.nextLine();
				if(playerDecision.charAt(0) == 'h') {
					currentPlayer.addCard(new PlayingCard());
					System.out.println("You pulled a: " + allRanks[currentPlayer.recentCard().getRank() - 1] + " of " + 
							allSuits[currentPlayer.recentCard().getSuit() - 1]);
					System.out.println("Totaling to : " + currentPlayer.getCardTotal());
					// Bust. End game.
					if(currentPlayer.getCardTotal() > 21) {
						System.out.println("Bust!");
						stay = true;
						endOfGame();
						break;
					}
					System.out.println("Hit again? h for hit and anything else to stay.");
				} else {
					stay = true;
				}
			}
			// Dealer has to reach 17 in total card value
			while(dealer.getCardTotal() < 17) {
				dealer.addCard(new PlayingCard());
			}
			System.out.println("The dealer's total card value is: " + dealer.getCardTotal());
			// Player won the round
			if(currentPlayer.getCardTotal() > dealer.getCardTotal() || dealer.getCardTotal() > 21) {
				System.out.println("You won!");
				currentPlayer.addFunds(currentPot * 2);
			} else if(currentPlayer.getCardTotal() == dealer.getCardTotal()) { // tie
				System.out.println("Tie!");
				currentPlayer.addFunds(currentPot);
			} else { // Dealer won the round
				System.out.println("You lost!");
			}
			System.out.println("You currently have: $" + currentPlayer.getMoney());
			System.out.println("Want to play again? y for yes anything else for no.");
			// Error check
			String answer = keyboard.nextLine();
			boolean playAgain = answer.length() > 0 && answer.toLowerCase().charAt(0) == 'y';
			// Play another round
			if(playAgain) {
				currentPlayer.flushHand();
				dealer.flushHand();
				playGame(currentPlayer, dealer, keyboard);
			} else { // No longer wants to play
				endOfGame();
			}
			
		} else {
			System.out.println("You are out of money :(");
			endOfGame();
		}
	}
	
	public static void endOfGame() {
		System.out.println("Thanks for playing!");
		System.out.println("Watch out for future updates!");
	}
}
