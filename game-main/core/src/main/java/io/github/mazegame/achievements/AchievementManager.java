package io.github.mazegame.achievements;

import java.util.HashMap;
import java.util.Map;

public final class AchievementManager {
    private static final Map<AchievementID, Achievement> achievements = new HashMap<>();
    public void add(Achievement achievement){
        achievements.put(achievement.getId(),achievement);
    }

    public static Achievement get(AchievementID id){
        return achievements.get(id);
    }
    
    public void init(){
        add(new PickUpItemAchievement());
        add(new GiveRedBoxAchievement());
    }
}

class PickUpItemAchievement extends Achievement {
    public PickUpItemAchievement() {
        super(
            AchievementID.PICK_UP_ITEM,
            "Item picked up",
            "You picked up an item."
        );
    }
}

class GiveRedBoxAchievement extends Achievement {
    public GiveRedBoxAchievement() {
        super(
            AchievementID.GIVE_RED_BOX,
            "Red Box given!",
            "You have given the red box."
        );
    }
}




