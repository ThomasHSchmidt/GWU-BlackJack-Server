import java.util.*;

class Hand {
    private List<Card> hand;

    public Hand() {
        this.hand = new LinkedList<>();
    }
    // Creates and adds a new card to a hand of suit and rank specified 
    // by token
    public void addCard(Token token)  {
        Card newCard = new Card(token);
        hand.add(newCard);
    }

    public void setCards(List<Card> hand) {
        this.hand = hand;
    }

    public List<Card> getHand() {
        return this.hand;
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

    public boolean isPair() {
        if (hand.size() == 2) {
            Card c1 = hand.get(0);
            Card c2 = hand.get(1);

            return c1.getRank() == c2.getRank();
        }

        return false;
    }

}