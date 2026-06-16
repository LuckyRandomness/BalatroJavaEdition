package src.main.java;

import java.util.ArrayList;
import java.util.Collections;
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
    private int default_hand_size = 8;

    private ArrayList<Card> deck = new ArrayList<Card>();
    private ArrayList<Card> hand = new ArrayList<Card>();
    
    public GameState(Stake stake, Random rand) {
        this.stake = stake;
        this.rand = rand;

        //TODO: check stakes
    }

    void begin() {
        Card card = new Card(Suit.HEART, Value.TEN, Modifier.BASE, Edition.POLYCHROME, Seal.RED);
        float[] initial = {5, 2, 2};
        card.scoreCard(initial, rand, Blind.TOOTH);

        //create our initial deck and hand.
        for (Suit s : Suit.values()) {
            for (Value v : Value.values()) {
                Card c = new Card(s, v);
                deck.add(c);
            }
        }

        //shuffle.
        Collections.shuffle(deck, rand);

        //TODO: BLIND SELECTION/SKIPPING

        //deal into hand.
        deal();

        //display.
        display();

    }

    private void display() {
        //display menu...
        //display all cards in hand

        //this odd way of printing will help with printing cards
        //with visible seals, editions, stone cards, etc.
        //however, if I want the cards in order horizontally, I cannot fully
        //delegate to the card class.
        for (int i = 0; i < 6; i++) {
            for (Card c: hand) {
                c.displayLine(i);
            }
            System.out.println();
        }
    }

    protected void deal() {
        hand.addAll(deck.subList(0, default_hand_size));
        deck.subList(0, default_hand_size).clear();
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
