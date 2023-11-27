class Card {
    private int suit;
    private int rank;
    private int value;

    public Card(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
        if(rank < 10)
            this.value = rank;
        else
            this.value = 10;
    }

    int getSuit() {
        return this.suit;
    }
    int getRank() {
        return this.rank;
    }
    int getValue() {
        return this.value;
    }
}