package io.github.mazegame.items;

import io.github.mazegame.Code;
import io.github.mazegame.CodeManager;

public class Code5Collectable extends Item{
    private Code code;

    public Code5Collectable(Code code){
        name = ItemID.CODE5_COLLECTABLE;
        isUsable = true;
        this.code = code;
        effect = CodeManager.collectCode(code);
        image = null;
        CodeManager.registerCode(4, code);
    }
}
