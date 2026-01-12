package io.github.mazegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;
import io.github.mazegame.achievements.AchievementID;
import io.github.mazegame.achievements.Achievement;
import io.github.mazegame.achievements.AchievementManager;

/** Display screen shown when the player is caught by the dean (time runs out), showing game over message. */
public class LoseScreen implements Screen {
    private final MazeGame game;
    private OrthographicCamera camera;
    private Leaderboard leaderboard;
    private int playerScore;
    private long playerTime;
    private int playerRank;
    private boolean leaderboardUpdated = false;
    private List<Achievement> unlockedAchievements;
    
    /** Creates the lose screen with a reference to the main game.
     * 
     * @param game the MazeGame instance
     */
    public LoseScreen(MazeGame game) {
        this.game = game;
    }
    
    @Override
    public void show() {
        camera = new OrthographicCamera(800, 500);
        camera.position.set(400, 250, 0);
        camera.update();
        
        // Check and unlock achievements
        GameStats.checkAndUnlockAchievements();
        
        // Collect unlocked achievements
        unlockedAchievements = new ArrayList<>();
        AchievementID[] achievementIDs = {
            AchievementID.HAND_IN_ASSIGNMENT,
            AchievementID.INTERACT_WITH_EVERYTHING,
            AchievementID.SOLVE_BOTH_PUZZLES
        };
        
        for (AchievementID id : achievementIDs) {
            Achievement achievement = AchievementManager.get(id);
            if (achievement != null && achievement.isUnlocked()) {
                unlockedAchievements.add(achievement);
            }
        }
        
        // Initialize leaderboard and calculate score
        leaderboard = new Leaderboard();
        playerScore = GameStats.calculateScore();
        playerTime = GameStats.getElapsedTime();
        
        // Add player to leaderboard (even if they lost)
        String playerName = "Player";
        leaderboardUpdated = leaderboard.addEntry(playerName, playerScore, playerTime, false);
        playerRank = leaderboard.getRank(playerScore);
    }
    
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        
        // Draw title
        MazeGame.font.getData().setScale(2f);
        MazeGame.font.draw(game.batch, "You were caught by the dean!", 80, 480);
        
        // Draw personal stats
        MazeGame.font.getData().setScale(1.2f);
        MazeGame.font.draw(game.batch, "GAME OVER", 300, 440);
        
        MazeGame.font.getData().setScale(1f);
        MazeGame.font.draw(game.batch, "Score: " + playerScore, 60, 410);
        MazeGame.font.draw(game.batch, "Time: " + formatTime(playerTime), 60, 385);
        
        if (leaderboardUpdated) {
            MazeGame.font.getData().setScale(1f);
            MazeGame.font.draw(game.batch, "Rank: #" + playerRank, 60, 360);
            MazeGame.font.getData().setScale(0.8f);
            MazeGame.font.draw(game.batch, "(NEW LEADERBOARD ENTRY!)", 60, 340);
        }
        
        // Draw achievements
        MazeGame.font.getData().setScale(1.2f);
        MazeGame.font.draw(game.batch, "ACHIEVEMENTS UNLOCKED", 50, 320);
        
        MazeGame.font.getData().setScale(0.9f);
        if (unlockedAchievements.isEmpty()) {
            MazeGame.font.draw(game.batch, "None unlocked this game", 60, 300);
        } else {
            int achievementY = 300;
            for (Achievement achievement : unlockedAchievements) {
                MazeGame.font.draw(game.batch, "* " + achievement.getTitle(), 60, achievementY);
                achievementY -= 20;
            }
        }
        
        // Draw leaderboard
        MazeGame.font.getData().setScale(1.2f);
        MazeGame.font.draw(game.batch, "TOP 5 LEADERBOARD", 400, 440);
        
        MazeGame.font.getData().setScale(0.9f);
        List<LeaderboardEntry> topEntries = leaderboard.getTopEntries(5);
        
        int startY = 410;
        for (int i = 0; i < topEntries.size(); i++) {
            LeaderboardEntry entry = topEntries.get(i);
            String rank = "#" + (i + 1);
            String name = entry.getPlayerName();
            String score = "Score: " + entry.getScore();
            String time = entry.getFormattedTime();
            String status = entry.isWon() ? "(WON)" : "(LOST)";
            
            MazeGame.font.draw(game.batch, rank + " " + name + " " + status, 410, startY - (i * 25));
            MazeGame.font.draw(game.batch, score + " (" + time + ")", 430, startY - 12 - (i * 25));
        }
        
        // Draw detailed stats
        MazeGame.font.getData().setScale(0.85f);
        MazeGame.font.draw(game.batch, "Codes Collected: " + GameStats.codesCollected + "/5", 50, 260);
        MazeGame.font.draw(game.batch, "Quest Giver Interactions: " + GameStats.questGiverInteractions, 50, 235);
        MazeGame.font.draw(game.batch, "Puzzles Achieved: " + GameStats.puzzlesAchieved, 50, 210);
        MazeGame.font.draw(game.batch, "Glue Used: " + GameStats.glueUsageCount, 50, 185);
        MazeGame.font.draw(game.batch, "Energy Drinks Used: " + GameStats.energyDrinksUsed, 50, 160);
        
        // Draw instructions
        MazeGame.font.getData().setScale(0.8f);
        MazeGame.font.draw(game.batch, "Press SPACE to return to menu or ESC to exit", 100, 100);
        
        game.batch.end();
        
        // Handle input
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            // Return to main menu (or restart)
            GameStats.reset();
            game.setScreen(new GameScreen(game));
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
    
    /**
     * Formats time in milliseconds to MM:SS format.
     *
     * @param timeMillis time in milliseconds
     * @return formatted time string
     */
    private String formatTime(long timeMillis) {
        long seconds = timeMillis / 1000;
        long minutes = seconds / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
    
    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }
    
    @Override
    public void pause() {}
    
    @Override
    public void resume() {}
    
    @Override
    public void hide() {}
    
    @Override
    public void dispose() {}
}
