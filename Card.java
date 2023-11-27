class Card {
    private int suit;
    private int rank;
    private int value;

    public Card(int suit, int rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
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