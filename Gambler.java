class Gambler {
    private Hand hand;
    private String name;

    public Gambler(String name) {
        this.name = name;
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
}