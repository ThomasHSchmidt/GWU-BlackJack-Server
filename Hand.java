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
            int add = card.getRank();
            if(add < Card.FACE_CARD_VALUE)
                handValue += add;
            else
                handValue += Card.FACE_CARD_VALUE;
        }

        return handValue;
    }
    public List<Card> getHand() {
        return this.hand;
    }
}