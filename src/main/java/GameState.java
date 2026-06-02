package src.main.java;

import java.util.Random;

import src.main.java.Types.Blind;
import src.main.java.Types.Edition;
import src.main.java.Types.Modifier;
import src.main.java.Types.Seal;
import src.main.java.Types.Stake;
import src.main.java.Types.Suit;
import src.main.java.Types.Value;

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
        Card card = new Card(Suit.HEART, Value.TEN, Modifier.BASE, Edition.POLYCHROME, Seal.RED);
        float[] initial = {5, 2, 2};
        card.scoreCard(initial, rand, Blind.TOOTH);
        
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
