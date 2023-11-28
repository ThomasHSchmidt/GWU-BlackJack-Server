import java.util.Collections;
import java.util.ArrayList;

public class Deck {
    private ArrayList<String> cards;
    
    private static final String[] RANK = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
    private static final String[] SUIT = {"1", "2", "3", "4"};

    public Deck() {
        cards = new ArrayList<>();
        // Iterate through each rank for all 4 suits
        // Should create 52 cards (13 x 4 = 52)
        for (String suit : SUIT) {
            for (String rank : RANK) {
                String card = rank + '_' + suit;
                cards.add(card);
            }
        }
    }

    // Get deck size
    public int size() {
        return cards.size();
    }

    // For shuffling the deck, creating a random order of cards that we can just
    // take from cards[0] each time
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // If we want to take a card from the deck
    public String drawCard() {
        // Check if deck is empty
        if (cards.isEmpty()) {
            throw new IllegalStateException("The deck is empty.");
        }
        return cards.remove(0);
    }

}