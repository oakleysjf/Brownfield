package io.github.mazegame.entities;

import io.github.mazegame.Code;

public class Door {
    private boolean locked = true;

    public boolean isLocked(){
        return locked;
    }

    public void setLocked(boolean locked){
        this.locked = locked;
    }

    public void updateLockState(Code... codes){
        for(Code code : codes){
            if(!code.getCollected()){
                this.locked = true;
                return;
            }
        }
        this.locked = false;
    }
}
