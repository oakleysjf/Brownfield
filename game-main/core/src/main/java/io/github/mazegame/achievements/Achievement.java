/*
add imports and package
 */


public abstract class Achievement {
    public AchievementID id;
    public String title;
    public String description;

    public boolean unlocked = false;

    public AchievementID getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isUnlocked() { return unlocked; }

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

