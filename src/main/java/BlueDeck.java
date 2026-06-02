package src.main.java;

import java.util.Random;
import src.main.java.Types.Stake;

public class BlueDeck extends GameState {

    BlueDeck(Stake stakeType, Random rand) {
        super(stakeType, rand);
        increaseDefaultHands(1);
    }
}
