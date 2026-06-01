package src.main.java;

import java.util.Random;

import src.main.java.Types.Stake;

abstract class GameState {

    private Stake stake;
    private Random rand;

    private int default_hands = 4;
    private int default_discards = 3;
    private int money = 4;

    public GameState(Stake stake, Random rand) {
        this.stake = stake;
        this.rand = rand;

        //TODO: check stakes
    }

    void display() {
        System.out.println("Stake: " + this.stake);
        System.out.println("Num of discards: " + default_discards);
        System.out.println("Num of hands: " + default_hands);
        System.out.println("Money: " + money);
        calculateMoney();
    }

    void calculateMoney() {
        System.out.println("Gamestate / default calcs");
    }

    //Setters for deck types to effect our starting values
    protected void increaseDefaultHands(int extra) {
        default_hands += extra;
    }

    protected void increaseDefaultDiscards(int extra) {
        default_discards += extra;
    }

    protected void increaseMoney(int extra) {
        money += extra;
    }
}
