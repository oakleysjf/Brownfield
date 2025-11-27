package io.github.mazegame.effects;

import io.github.mazegame.entities.PlayerEntity;

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
