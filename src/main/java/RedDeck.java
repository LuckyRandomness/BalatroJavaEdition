package src.main.java;

public class RedDeck extends GameState {
    private String stake;

    RedDeck() {
        this.stake = "STAKE";
    }

    @Override
    void display() {
        super.display();
        System.out.println(this.stake);
    }
}
