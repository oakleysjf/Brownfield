package io.github.mazegame.items;

import io.github.mazegame.Code;
import io.github.mazegame.CodeManager;

public class Code4Collectable extends Item{
    private Code code;

    public Code4Collectable(Code code){
        name = ItemID.CODE4_COLLECTABLE;
        isUsable = true;
        this.code = code;
        effect = CodeManager.collectCode(code);
        image = null;
        CodeManager.registerCode(3, code);
    }
}
