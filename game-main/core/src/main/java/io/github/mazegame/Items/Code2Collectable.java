package io.github.mazegame.items;

import io.github.mazegame.Code;
import io.github.mazegame.CodeManager;

public class Code2Collectable extends Item{
    private Code code;

    public Code2Collectable(Code code){
        name = ItemID.CODE2_COLLECTABLE;
        isUsable = true;
        this.code = code;
        effect = CodeManager.collectCode(code);
        image = null;
        CodeManager.registerCode(1, code);
    }
}
