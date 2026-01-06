package io.github.mazegame.items;

import io.github.mazegame.Code;
import io.github.mazegame.CodeManager;

public class Code1Collectable extends Item{
    private Code code;

    public Code1Collectable(Code code){
        name = ItemID.CODE1_COLLECTABLE;
        isUsable = true;
        this.code = code;
        effect = CodeManager.collectCode(code);
        image = null;
        CodeManager.registerCode(0, code);
    }
}
