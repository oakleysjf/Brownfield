package io.github.mazegame.effects;

import io.github.mazegame.entities.PlayerEntity;

/** An effect that doubles the player's movement speed for a specified duration. */
public class SpeedEffect extends Effect{
    /** Creates a speed effect with the specified duration.
     * 
     * @param duration the duration in frames
     */
    public SpeedEffect(int duration){
        super(duration);
    }
    @Override
    public void applyEffect() {
        duration--;
        PlayerEntity.instance.multiplyMoveMult(2);
    }
}
