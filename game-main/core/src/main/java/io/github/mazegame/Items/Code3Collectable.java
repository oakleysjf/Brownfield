package io.github.mazegame.items;

import io.github.mazegame.Code;
import io.github.mazegame.CodeManager;

public class Code3Collectable extends Item{
    private Code code;

    public Code3Collectable(Code code){
        name = ItemID.CODE3_COLLECTABLE;
        isUsable = true;
        this.code = code;
        effect = CodeManager.collectCode(code);
        image = null;
        CodeManager.registerCode(2, code);
    }
}
