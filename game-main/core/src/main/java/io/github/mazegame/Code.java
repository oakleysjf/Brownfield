package io.github.mazegame;

import java.util.Random;

public class Code {
    private int digit;
    private boolean collected;

    public Code(){
        Random rand = new Random();
        this.digit = rand.nextInt(10);
        this.collected = false;
    }

    public int getDigit(){
        return digit;
    }

    public boolean getCollected() {
        return collected;
    }

    public void collect(){
        this.collected = true;
    }

}
