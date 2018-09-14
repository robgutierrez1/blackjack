import java.util.ArrayList;

public class Player {
	
	private String playerName;
	private int currentMoney;
	private ArrayList<PlayingCard> cards;
	private int currentCardTotal;
	
	// Constructor
	public Player(String name, int money) {
		playerName = name;
		currentMoney = money;
		cards = new ArrayList<PlayingCard>();
		currentCardTotal = 0;
	}
	
	// Returns the name of this player
	public String getName() {
		return playerName;
	}
	
	// Returns the amount of money this player has
	public int getMoney() {
		return currentMoney;
	}
	
	// Deducts the amount of money this player has by given amount
	public void deductMoney(int amount) {
		currentMoney -= amount;
	}
	
	// Checks if player has enough money for bet
	public boolean enoughMoney(int amount) {
		if((this.currentMoney - amount) < 0) {
			return false;
		} else {
			return true;
		}
	}
	
	// Adds given amount to this player's money
	public void addFunds(int amount) {
		this.currentMoney += amount;
	}
	
	// Adds playing card to this player's "hand"
	public void addCard(PlayingCard card) {
		cards.add(card);
		int rank = card.getRank();
		// Ranks of 10 & higher should all be worth 10
		if(rank > 10) {
			rank = 10;
		}
		this.currentCardTotal += rank;
	}
	
	// Returns the total value of the cards in this player's "hand"
	public int getCardTotal() {
		return this.currentCardTotal;
	}
	
	// Clears player's hand. No longer has any cards.
	public void flushHand() {
		cards = new ArrayList<PlayingCard>();
		this.currentCardTotal = 0;
	}
	
	// Returns the first card this player has attained
	public PlayingCard peek() {
		if(cards.size() > 0) {
			return this.cards.get(0);
		} else {
			return null;
		}
	}
	
	// return card
	public PlayingCard getCard(int index) {
		return this.cards.get(index);
	}
	
	// return most recent card. Error check later
	public PlayingCard recentCard() {
		// No recent card if player doesn't have any cards!
		if(this.cards.size() < 1) {
			return null;
		}
		return this.cards.get(cards.size() - 1);
	}
}
