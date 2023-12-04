import java.util.LinkedList;

class Gambler {
    private Hand hand;
    private String name;
    private int bet;
    private Deck deck;

    public Gambler(String name, Deck deck) {
        this.name = name;
        this.hand = new Hand();
        this.deck = deck;
    }

    public String getName() {
        return this.name;
    }

    public Hand getHand() {
        return this.hand;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int b) {
        bet = b;
    }
    
    public void setHand(Hand hand) {
        this.hand = hand;
    }

    // Takes a key (RANK_SUIT), creates a card, and adds it to a
    // players hand
    //
    // Returns key used to generate token and card
    public String dealCard(String key) {
        Token temp = new Token(key);
        this.hand.addCard(temp);
        return key;
    }

    public int getHandValue() {
        return hand.getHandValue();
    }

    public void resetHand() {
        hand.setCards(new LinkedList<Card>());
    }

    // To be used when a player presses hit button on GUI or 
    // when dealer is playing its turn
    // 
    // Updates player hand and returns player's new hand value
    public int hit() {
        this.dealCard(deck.drawCard());
        return this.getHandValue();
    }
}