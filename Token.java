// Token format: RANK_SUIT
// SUITS: 1-4 (1 = Club | 2 = Diamonds | 3 = Hearts | 4 = Spades)
// RANKS: 1-13 (1 = Ace | 13 = King)
public class Token {
    String token;
    int rank;
    int suit;

    // Constructor to accept integer rank and suit arguments
    public Token(int rank, int suit) {
        this.token = rank + "_" + suit;
        rank = token.charAt(0) - '0';
        suit = token.charAt(2) - '0';
    }
    // Constructor to accept String key argument
    public Token(String key) {
        this.token = key;
    }

    public String getToken() {
        return token;
    }

    public int getTokenRank() {
        return rank;
    }

    public int getTokenSuit() {
        return suit;
    }
}
