package src.main.java;

import java.util.Random;
import src.main.java.Types.Stake;

public class YellowDeck extends GameState {

    YellowDeck(Stake stakeType, Random rand) {
        super(stakeType, rand);
        increaseMoney(10);
    }

    @Override
    void display() {
        super.display();
        System.out.println("YELLOW");
    }
}
