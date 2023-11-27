class Card {
    private int suit;
    private int rank;

    // Dont need a value int because we can just take the rank and base
    // it off of that.
    public Card(int suit, int rank) {
        this.suit = suit;   // 1-13 (1 = Ace | 13 = King)
        this.rank = rank;   // 1-4 (1 = Club | 2 = Diamonds | 3 = Hearts | 4 = Spades)
    }

    public int getSuit() {
        return this.suit;
    }

    public int getRank() {
        return this.rank;
    }

    public String getImagePath() {
        return "CardFolder/" + rank + "_" + suit + ".png";
    }

}