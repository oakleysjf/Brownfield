package io.github.mazegame.effects;

import io.github.mazegame.entities.PlayerEntity;

/** An effect that grants a temporary speed boost and tracks energy drink usage. */
public class EnergyDrinkEffect extends Effect {
    private boolean recorded = false;

    /** Creates an energy drink effect with a 7-frame duration. */
    public EnergyDrinkEffect() {
        super(7);
    }

    @Override
    public void applyEffect() {
        // Record usage on first application only
        if (!recorded) {
            io.github.mazegame.GameStats.recordEnergyDrinkUsed();
            recorded = true;
        }
        
        duration--;
        PlayerEntity.instance.multiplyMoveMult(2);
    }
}
