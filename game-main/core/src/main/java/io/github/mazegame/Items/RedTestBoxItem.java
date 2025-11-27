package io.github.mazegame.items;

import com.badlogic.gdx.graphics.Texture;

import io.github.mazegame.effects.SlowEffect;

public class RedTestBoxItem extends Item{
    public RedTestBoxItem(){
        name = ItemID.RED_TEST_BOX;
        isUsable = true;
        effect = new SlowEffect(7);
        image = new Texture("images/tiles/wall.png");
    }
}
