package io.github.mazegame.Items;

import com.badlogic.gdx.graphics.Texture;

import io.github.mazegame.Code;
import io.github.mazegame.CodeManager;

public class Code3Collectable extends Item{

    public Code3Collectable(Code code){
        name = ItemID.CODE1_COLLECTABLE;
        isUsable = true;
        effect = CodeManager.collectCode(code);
        image = new Texture(/*texture*/);
    }
}
