package src.main.java;

import java.util.Random;
import src.main.java.Types.Stake;

public class GreenDeck extends GameState {

    GreenDeck(Stake stakeType, Random rand) {
        super(stakeType, rand);
    }

    @Override
    void display() {
        super.display();
        System.out.println("GREEN");
    }

    @Override
    void calculateMoney() {
        System.out.println("Green deck money calculations");
    }
}
