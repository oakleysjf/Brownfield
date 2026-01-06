package io.github.mazegame.entities;

import io.github.mazegame.Code;

/** Legacy class that tracks a door's locked/unlocked state based on code collectibles (unused in current implementation). */
public class Door {
    private boolean locked = true;

    /** @return whether the door is locked */
    public boolean isLocked(){
        return locked;
    }

    /** Sets the locked state of the door.
     * 
     * @param locked whether the door should be locked
     */
    public void setLocked(boolean locked){
        this.locked = locked;
    }

    /** Updates the door's lock state based on whether all provided codes have been collected.
     * 
     * @param codes the codes to check
     */
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
