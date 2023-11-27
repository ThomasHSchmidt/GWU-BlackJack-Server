import java.util.*;

class Hand {
    List<Card> hand = new LinkedList<>();

    public Hand() {

    }

    public int getHandValue() {
        int handValue = 0;

        for(Card card : hand) {
            handValue += card.getValue();
        }
        
        return handValue;
    }
    public List<Card> getHand() {
        return this.hand;
    }
}