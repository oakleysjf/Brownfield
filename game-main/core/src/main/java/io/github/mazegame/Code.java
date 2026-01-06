package io.github.mazegame;

import java.util.Random;

/** Represents a single collectible code digit (0-9) that can be picked up by the player. */
public class Code {
    private int digit;
    private boolean collected;

    /** Creates a new code with a randomly generated digit from 0 to 9. */
    public Code(){
        Random rand = new Random();
        this.digit = rand.nextInt(10);
        this.collected = false;
    }

    /** @return the digit value of this code */
    public int getDigit(){
        return digit;
    }

    /** @return whether this code has been collected */
    public boolean getCollected() {
        return collected;
    }

    /** Marks this code as collected by the player. */
    public void collect(){
        this.collected = true;
    }

}
