import java.util.*;

class Hand {
    private List<Card> hand = new LinkedList<>();

    public Hand() {

    }
    public void setCards(List<Card> hand) {
        this.hand = hand;
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