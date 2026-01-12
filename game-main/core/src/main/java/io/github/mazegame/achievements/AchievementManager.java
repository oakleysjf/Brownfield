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
        add(new HandInAssignmentAchievement());
        add(new InteractWithEverythingAchievement());
        add(new SolveBothPuzzlesAchievement());
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

class HandInAssignmentAchievement extends Achievement {
    public HandInAssignmentAchievement() {
        super(
            AchievementID.HAND_IN_ASSIGNMENT,
            "Hand In Assignment",
            "Collect all 5 codes and complete the assignment."
        );
    }
}

class InteractWithEverythingAchievement extends Achievement {
    public InteractWithEverythingAchievement() {
        super(
            AchievementID.INTERACT_WITH_EVERYTHING,
            "Social Butterfly",
            "Interact with all quest givers in the game."
        );
    }
}

class SolveBothPuzzlesAchievement extends Achievement {
    public SolveBothPuzzlesAchievement() {
        super(
            AchievementID.SOLVE_BOTH_PUZZLES,
            "Puzzle Master",
            "Solve both puzzles (computer minigame and Turing machine quiz)."
        );
    }
}