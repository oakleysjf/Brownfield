package io.github.mazegame.achievements;

import java.util.HashMap;
import java.util.Map;

public final class AchievementManager {
    public static final Map<AchievementID, Achievement> achievements = new HashMap<>();
    public void add(Achievement achievement){
        achievements.put(AchievementID,PICK_UP_ITEM, new PickUpItemAchievment());
        achievements.put(AchievementID.GIVE_RED_BOX, new GiveRedBoxAchievement());
    }

    public static Achievement get(AchievementID id){
        return achievements.get(id);
    }
}

class PickUpItemAchievement extends Achievement {
    public PickUpItemAchievement() {
        this.id = AchievementID.PICK_UP_ITEM;
        this.title = "Item picked up";
        this.description = "You picked up an item.";
    }
}

class GiveRedBoxAchievement extends Achievement {
    public GiveRedBoxAchievement() {
        this.id = AchievementID.GIVE_RED_BOX;
        this.title = "Red Box given!";
        this.description = "You have given the red box.";
    }
}




