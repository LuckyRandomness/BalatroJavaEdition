package src.main.java;

import java.util.Random;
import src.main.java.Types.Stake;

public class RedDeck extends GameState {

    RedDeck(Stake stakeType, Random rand) {
        super(stakeType, rand);
        increaseDefaultDiscards(1);
    }
}
