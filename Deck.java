import java.text.Normalizer.Form;
import java.util.Collections;
import java.util.Random;
import java.util.HashMap;

public class Deck {
    private HashMap<String, Integer> cards;
    protected Random rand;

    private static final String[] RANK = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
    private static final String[] SUIT = {"1", "2", "3", "4"};

    public Deck() {
        cards = new HashMap<String, Integer>();
        for (String suit : SUIT) {
            for (String rank : RANK) {
                String card = rank + '_' + suit;
                int i = 1;
                cards.put(card, i);
                i++;
            }
        }
    }

    public int size() {
        return cards.size();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public String drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("The deck is empty.");
        }
        return cards.get(rand.nextInt(52));
    }
}