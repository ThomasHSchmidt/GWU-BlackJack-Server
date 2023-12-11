import java.util.*;

public class Hand {
    public static final int BLACKJACK = 21;
    private List<Card> hand;
    private boolean hasAce;

    public Hand() {
        this.hand = new LinkedList<>();
        hasAce = false;
    }
    
    // Creates and adds a new card to a hand of suit and rank specified 
    // by token
    public void addCard(Token token)  {
        if(token.getTokenRank() == 1)
            this.hasAce = true;
        Card newCard = new Card(token);
        this.hand.add(newCard);
    }

    public void setCards(List<Card> hand) {
        this.hand = hand;
    }

    public List<Card> getHand() {
        return this.hand;
    }

    // Sum the value of cards in a hand and return that sum
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

    public void printHand() {
        System.out.print("[");

        for(Card card : hand) {
            System.out.print(card.getTokenKey() + ", ");
        }

        System.out.print("]");
    }

    public boolean hasAce() {
        return this.hasAce;
    }

    public boolean isBust() {
        return this.getHandValue() > BLACKJACK;
    }

    public boolean isBlackjack(boolean hasAce) {
        return hand.size() == 2 && this.getHandValue() == Hand.BLACKJACK;
    }

    public void setAce(boolean bool) {
        this.hasAce = bool;
    }
}


/*
If we want to try and implement the ability to split

    public boolean isPair() {
        if (hand.size() == 2) {
            Card c1 = hand.get(0);
            Card c2 = hand.get(1);

            return c1.getRank() == c2.getRank();
        }

        return false;
    }
*/