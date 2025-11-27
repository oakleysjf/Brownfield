package io.github.mazegame.effects;

import io.github.mazegame.entities.PlayerEntity;

public class SpeedEffect extends Effect{

    public SpeedEffect(int duration){
        super(duration);
    }
    @Override
    public void applyEffect() {
        duration--;
        PlayerEntity.instance.multiplyMoveMult(2);
    }
}
