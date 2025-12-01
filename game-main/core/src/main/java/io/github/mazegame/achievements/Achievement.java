package io.github.mazegame.achievements;

/**
 * Class for all achievements in the game.
 * 
 * Each achievement extends this class and sets its own id, title and description.
 * 
 * This class also controls the locked/unlocked state of the achievement.
 */

public abstract class Achievement {
    private final AchievementID id;
    private final String title;
    private final String description;

    private boolean unlocked = false;

    public Achievement(AchievementID id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public AchievementID getId() { 
        return id; 
    }
    
    public String getTitle() { 
        return title;     
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public boolean isUnlocked() { 
        return unlocked; 
    }

    public void unlock() {
        if (!unlocked) {
                unlocked = true;
                onUnlock();
        }
    }

    protected void onUnlock() {
        /*
        sound and popup
        */
    }
}

