package io.github.mazegame.effects;

import io.github.mazegame.entities.PlayerEntity;

/** An effect that reduces the player's movement speed by 50% for a specified duration. */
public class SlowEffect extends Effect{

    public SlowEffect(int duration){
        super(duration);
    }

    @Override
    public void applyEffect() {
        duration--;
        PlayerEntity.instance.multiplyMoveMult(0.5f);
    }
}
