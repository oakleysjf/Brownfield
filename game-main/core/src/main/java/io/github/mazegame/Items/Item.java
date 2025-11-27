package io.github.mazegame.items;

import com.badlogic.gdx.graphics.Texture;

import io.github.mazegame.effects.Effect;

/** Used to create all the items character entities can hold and the player can use. */
public abstract class Item {
    public ItemID name;
    public boolean isUsable;
    public Effect effect;
    public Texture image;

    /** If the item is usable return the effect.
     * 
     * @return the {@link Effect} of the item.
     */
    public Effect use() {
        if (!isUsable){
            return null;
        }
        return effect;
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof Item)) {
            return false;
        }
        return ((Item)other).name == name;
    }
}
