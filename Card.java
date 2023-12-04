class Card {
    private int suit;
    private int rank;

    public static final int FACE_CARD_VALUE = 10;

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