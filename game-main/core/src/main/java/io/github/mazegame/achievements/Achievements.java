/*
add imports and package
 */

import java.util.HashMap;
import java.util.Map;

public final class Achievements {
    public static final Map<AchievementID, Achievement> achievements = new HashMap<>();
    static {
        achievements.put(AcheivementID,PICK_UP_ITEM, new PickUpItemAchievment());
        achievements.put(AchievementID.GIVE_RED_BOX, new GiveRedBoxAchievement());
    }

    public static Achievement get(AchievementID id){
        return achievements.get(id);
    }
}

class PickUpItemAchievment extends Achievement {
    public PickUpItemAchievement() {
        this.id = AchievementID.PICK_UP_ITEM;
        this.title = "Item picked up";
        this.description = "You picked up an item.";
    }
}

class GiveRedBoxAchievement extends Achievement {
    public GiveRedBoxAchievment() {
        this.id = AchievmentID.GIVE_RED_BOX;
        this.title = "Red Box given!";
        this.description = "You have given the red box.";
    }
}




