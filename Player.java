class Player extends Gambler {
    public static final int MIN_BET = 25;
    public static final int STARTING_MONEY = 250;
    private int bet;
    private int cash;
    private int id;

    public Player(String name, int id) {
        super(name);
        bet = MIN_BET;
        cash = STARTING_MONEY;
        this.id = id;
    }
    
    public int getBet() {
        return this.bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getCash() {
        return this.cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getId() {
        return this.id;
    }
}