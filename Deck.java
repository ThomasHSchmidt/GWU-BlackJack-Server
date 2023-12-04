import java.util.*;

public class Deck {
    private HashMap<String, Integer> deck;
    
    public static final int DECK_SIZE = Card.RANK.length * Card.SUIT.length;
    public static final int NUM_DECKS = 4;
    private Random rand;
    private int cardCount;
    

    public Deck() {
        deck = new HashMap<String, Integer>(DECK_SIZE);
        this.rand = new Random();
        cardCount = NUM_DECKS * DECK_SIZE;

        // Iterate through each rank for all SUIT.length suits, and for each suit, RANK.length ranks
        // Create map of DECK_SIZE size, filling each entry with NUM_DECKS cards (NUM_DECKS * DECK_SIZE cards total)
        for (String suit : Card.SUIT) {
            for (String rank : Card.RANK) {
                deck.put(rank + '_' + suit, NUM_DECKS);
            }
        }
    }

    // Return number of cards remaining in deck
    public int getCardCount() {
        return cardCount;
    }

    // Setting number of cards of each rank and suit back to NUM_DECKS
    public void shuffle() {
        for(Map.Entry<String, Integer> entry : deck.entrySet()) {
            entry.setValue(NUM_DECKS);
        }
    }

    // Randomly create a String key to identify cards in the format RANK_SUIT
    private String generateKey() {
        // Generate key using random numbers between 1-RANK.length and 1-SUIT.length inclusive
        String key = (rand.nextInt(Card.RANK.length) + 1) + "_" + (rand.nextInt(Card.SUIT.length) + 1);

        return key;
    }

    // Remove a random card from deck
    public String drawCard() {
        String key = generateKey();

        // Ensure at least one card of the suit and rank specified by key are still
        // in deck before removal
        while(deck.get(key) == 0) {
            key = generateKey();
        }
        // Remove card from deck
        deck.put(key, deck.get(key) - 1);
        cardCount--;

        return key;
    }

}