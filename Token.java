// Token format: RANK_SUIT
// SUITS: 1-4 (1 = Club | 2 = Diamonds | 3 = Hearts | 4 = Spades)
// RANKS: 1-13 (1 = Ace | 13 = King)
public class Token {
    String key;
    int rank;
    int suit;

    // Constructor to accept integer rank and suit arguments
    public Token(int rank, int suit) {
        this.key = rank + "_" + suit;
        this.rank = rank;
        this.suit = suit;        
    }
    // Constructor to accept String key argument
    public Token(String key) {
        this.key = key;
        this.rank = key.charAt(0) - '0';
        this.suit = key.charAt(2) - '0';
    }

    public String getToken() {
        return key;
    }

    public int getTokenRank() {
        return rank;
    }

    public int getTokenSuit() {
        return suit;
    }
}
