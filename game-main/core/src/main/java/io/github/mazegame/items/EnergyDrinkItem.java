package io.github.mazegame.items;

import com.badlogic.gdx.graphics.Texture;

import io.github.mazegame.effects.SpeedEffect;

/** An item that grants a temporary speed boost, opposite of GlueEntity's slow effect. */
public class EnergyDrinkItem extends Item {
    public EnergyDrinkItem() {
        name = ItemID.ENERGY_DRINK;
        isUsable = true;
        effect = new SpeedEffect(7);
        image = new Texture("images/entities/EnergyDrink.png");
    }
}
