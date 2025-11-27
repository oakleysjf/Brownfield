package io.github.mazegame.items;

import com.badlogic.gdx.graphics.Texture;

import io.github.mazegame.effects.SpeedEffect;

public class TestBoxItem extends Item{
    public TestBoxItem() {
        name = ItemID.TEST_BOX;
        isUsable = true;
        effect = new SpeedEffect(10);
        image = new Texture("TestBox.png");
    }
}
