package src.main.java;

import java.util.Random;

import src.main.java.Types.Blind;
import src.main.java.Types.Edition;
import src.main.java.Types.Modifier;
import src.main.java.Types.Seal;
import src.main.java.Types.Suit;
import src.main.java.Types.Value;

public class Card {
    /*HOW DO CARDS WORK

    - what do we need to know
    - value
    - suit
    - Enhancements (+chips, +mult, all suits, x2 & destructible, x1.5 in hand, set 50, +money, lucky)
    - Seals (Gold + money, red (retrigger), blue (create Planet card when held in hand), purple (create Tarot when discarded))
    - Editions (Base, Foil (+ chips), Holographic (+10 mult), Polychrome (x1.5 mult), Negative (N/A))
    - can only have one of each (changing wild to new modifier will restore old suit)

    //keep original suit, add wild modifier*/

    private Suit suit;
    private Value value;
    private Modifier modifier;
    private Edition edition;
    private Seal seal;
    private boolean played;
    private boolean retriggered;

    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
        this.modifier = Modifier.BASE;
        this.edition = Edition.BASE;
        this.seal = Seal.BASE;
        this.played = false;
        this.retriggered = false;
    }

    public Card(Suit suit, Value value, Modifier modifier, Edition edition, Seal seal) {
        this.suit = suit;
        this.value = value;
        this.modifier = modifier;
        this.edition = edition;
        this.seal = seal;
        this.played = false;
        this.retriggered = false;
    }

    public int getScore() {
        switch (this.value) {
            case Value.ACE:
                return 11;
            case Value.TWO:
                return 2;
            case Value.THREE:
                return 3;
            case Value.FOUR:
                return 4;
            case Value.FIVE:
                return 5;
            case Value.SIX:
                return 6;
            case Value.SEVEN:
                return 7;
            case Value.EIGHT:
                return 8;
            case Value.NINE:
                return 9;
            default:
                return 10;
        }
    }

    public String getSymbol() {
        switch (this.value) {
            case Value.ACE:
                return "A";
            case Value.TWO:
                return "2";
            case Value.THREE:
                return "3";
            case Value.FOUR:
                return "4";
            case Value.FIVE:
                return "5";
            case Value.SIX:
                return "6";
            case Value.SEVEN:
                return "7";
            case Value.EIGHT:
                return "8";
            case Value.NINE:
                return "9";
            case Value.TEN:
                return "10";
            case Value.JACK:
                return "J";
            case Value.QUEEN:
                return "Q";
            case Value.KING:
                return "K";
            default:
                return "X";
        }
    }

    public void displayLine(int line) {
        switch (line) {
            case 0:
                System.out.print(" _____ ");
                break;
            case 1:
                System.out.print("|");
                System.out.print(getSymbol());
                if (this.value == Value.TEN) {
                    System.out.print("   |");
                } else {
                    System.out.print("    |");
                }
                break;
            case 2:
                System.out.print("|     |");
                break;
            case 3:
                System.out.print("|  ");
                System.out.print(getSuitSymbol());
                System.out.print("  |");
                break;
            case 4:
                System.out.print("|     |");
                break;
            case 5:
                if (this.value == Value.TEN) {
                    System.out.print("|___");
                } else {
                    System.out.print("|____");
                }
                System.out.print(getSymbol());
                System.out.print("|");
                break;
        }
    }

    private String getSuitSymbol() {
        switch (this.suit) {
            case Suit.HEART:
                //red text color code added, then white added back
                return "\u001B[31m" + "H" + "\u001B[37m";
            case Suit.CLUB:
                return "C";
            case Suit.DIAMOND:
                return "\u001B[31m" + "D" + "\u001B[37m";
            case Suit.SPADE:
                return "S";
            default:
                return "X";
        }
    }

    public float[] scoreCard(float[] initial, Random rand, Blind blind) {
        displayStats(initial);
        if (!isDebuffed(blind)) {
            //add value of card. 50 chips if a stone card.
            initial[0] = this.modifier == Modifier.STONE ? initial[0] + 50 : initial[0] + getScore();

            initial = scoreModifier(initial, rand);

            initial = scoreEdition(initial);

            //check for TOOTH blind
            if (blind == Blind.TOOTH) {
                initial[2] = (float) Math.max(0.0, initial[2] - 1);
            }

            initial = scoreSeal(initial, rand, blind);

            //mark as played this ante.
            this.played = true;

            System.out.println("Final stats: ");
            displayStats(initial);
            return initial;
        } else {
            System.out.println("DEBUFFED");
            return initial;
        }
       
    }

    private boolean isDebuffed(Blind blind) {
        switch (blind) {
            case Blind.CLUB:
                return (this.suit == Suit.CLUB);
            case Blind.GOAD:
                return (this.suit == Suit.SPADE);
            case Blind.WINDOW:
                return (this.suit == Suit.DIAMOND);
            case Blind.HEAD:
                return (this.suit == Suit.HEART);
            case Blind.PILLAR:
                return this.played;
            case Blind.PLANT:
                return (getScore() == 10 && this.value != Value.TEN);
            default:
                return false;
        }
    }

    private float[] scoreModifier(float[] initial, Random rand) {
        //check enhancement of card. disregard stone, wild, steel, and gold.
        switch (this.modifier) {
            case Modifier.BONUS:
                initial[0] += 30;
                break;
            case Modifier.MULT:
                initial[1] += 4;
                break;
            case Modifier.GLASS:
                initial[1] *= 1.5;
                break;
            case Modifier.LUCKY:
                if (rand.nextInt(5) == 0) {
                    initial[1] += 20;
                }
                if (rand.nextInt(15) == 0) {
                    initial[2] += 20;
                }
                
                break;
            default:
                break;
        }
        return initial;
    }

    private float[] scoreEdition(float[] initial) {
        //check editions. Disregard base and negative.
        switch (this.edition) {
            case Edition.FOIL:
                initial[0] += 50;
                break;
            case Edition.HOLOGRAPHIC:
                initial[1] += 10;
                break;
            case Edition.POLYCHROME:
                initial[1] *= 1.5;
                break;
            default:
                break;
        }

        return initial;
    }

    private float[] scoreSeal(float[] initial, Random rand, Blind blind) {
        //check seal. Disregard blue and purple.
        switch (this.seal) {
            case Seal.GOLD:
                initial[2] += 3;
                break;
            case Seal.RED:
                if (!retriggered) {
                    this.retriggered = true;
                    System.out.println("RETRIGGERING");
                    initial = scoreCard(initial, rand, blind);
                    this.retriggered = false;
                }
                break;
            default:
                break;
        }

        return initial;
    }

    public void resetPlayed() {
        this.played = false;
    }

    public void displayStats(float[] stats) {
        System.out.println("Chips: " + stats[0] + " Mult: " + stats[1] + " Money: " + stats[2]);
    }

    //TODO: CARD WHEN LEFT IN HAND: STEEL GOLD BLUE SEAL (if final round)

    //TODO: CARD WHEN DISCARDED: PURPLE SEAL
}
