import java.util.*;

public class Hand {
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

    public boolean hasAce() {
        for (Card card : hand) {
            if (card.getRank() == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean isBlackjack(boolean hasAce) {
        int total = 0;
        if (hasAce == false) {
            for (Card card : hand) {
                total = card.getRank();
            }
            if (total == 21) {
                return true;
            }
        }
        
        if(hasAce == true) {
            for (Card card : hand) {
                if (total > 21)
                {
                    total -= 10;
                }
                total = card.getRank();
            }
            if (total == 21) {
                return true;
            }
        }
        return false;
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