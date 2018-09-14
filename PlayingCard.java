import java.util.Random;

public class PlayingCard {
	
	private int suitValue;
	private int rankValue;
	
	public static final int Spade = 1;
	public static final int Diamond = 2;
	public static final int Club = 3;
	public static final int Heart = 4;
	
	// Constructor for random card
	public PlayingCard() {
		// Generate random suit and rank for card
		Random r = new Random();
		suitValue = r.nextInt(4) + 1;
		rankValue = r.nextInt(13) + 1;
	}
	
	// Returns suit of this card
	public int getSuit() {
		return suitValue;
	}
	
	// Returns rank of this card
	public int getRank() {
		return rankValue;
	}
}
