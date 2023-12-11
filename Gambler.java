import java.util.LinkedList;

class Gambler {
    private Hand hand;
    private String name;
    private int bet;
    private int id;

    public Gambler(String name, int id) {
        this.name = name;
        this.hand = new Hand();
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Hand getHand() {
        return this.hand;
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
        hand.newHand();
    }
}