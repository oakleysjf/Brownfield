package io.github.mazegame;

import io.github.mazegame.effects.Effect;

/** Manages all collectible codes in the game, tracking their collection status and unlock state. */
public class CodeManager {
    private static Code[] allCodes = new Code[5];
    private static int codesCollected = 0;
    
    private CodeManager() {}

    public static void registerCode(int index, Code code) {
        if (index >= 0 && index < 5) {
            allCodes[index] = code;
        }
    }

    public static Effect collectCode(final Code code){
        return new Effect(0){
            @Override
            public void applyEffect() {
                if (!code.getCollected()) {
                    code.collect();
                    codesCollected++;
                }
            }
        };
    }

    public static boolean allCodesCollected() {
        return codesCollected == 5;
    }

    public static void resetCodes() {
        allCodes = new Code[5];
        codesCollected = 0;
    }

    public static int getCodesCollected() {
        return codesCollected;
    }

    public static void incrementCodesCollected() {
        codesCollected++;
    }
}
