public class Card {
    public static final String[] RANK = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
    public static final String[] SUIT = {"1", "2", "3", "4"};
    private Token token;

    public static final int FACE_CARD_VALUE = 10;

    public Card(Token token) {
        this.token = token;
    }

    public int getRank() {
        return token.getTokenRank();
    }

    public int getSuit() {
        return token.getTokenSuit();
    }

    public boolean isFaceCard() {
        return token.getTokenRank() > 10 && token.getTokenRank() <= 13;
    }

    public String getImagePath() {
        return "CardFolder/" + this.getRank() + "_" + this.getSuit() + ".png";
    }
}