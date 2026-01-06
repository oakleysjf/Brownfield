package io.github.mazegame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.LinkedList;
import java.util.Queue;

/** Manages in-game notifications displayed in the top-right corner of the screen. Messages appear with a semi-transparent background and auto-expire after 3 seconds. */
public class NotificationManager {
    private static final float MESSAGE_DURATION = 3f;
    private static final float PADDING = 10f;
    private static final float LINE_HEIGHT = 25f;
    
    private Queue<Notification> notifications = new LinkedList<>();
    
    private static class Notification {
        String message;
        float timeRemaining;
        
        Notification(String message, float duration) {
            this.message = message;
            this.timeRemaining = duration;
        }
    }
    
    public void addNotification(String message) {
        notifications.add(new Notification(message, MESSAGE_DURATION));
    }
    
    public void update(float delta) {
        notifications.removeIf(notif -> {
            notif.timeRemaining -= delta;
            return notif.timeRemaining <= 0;
        });
    }
    
    public void draw(SpriteBatch batch, float screenWidth, float screenHeight) {
        if (notifications.isEmpty() || MazeGame.font == null) {
            return;
        }
        
        batch.end();
        
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.7f);
        
        float boxHeight = notifications.size() * LINE_HEIGHT + PADDING * 2;
        float boxWidth = 300f;
        shapeRenderer.rect(screenWidth - boxWidth - PADDING, screenHeight - boxHeight - PADDING, boxWidth, boxHeight);
        
        shapeRenderer.end();
        
        batch.begin();
        
        // Draw notification text
        int index = 0;
        for (Notification notif : notifications) {
            batch.setColor(Color.WHITE);
            float y = screenHeight - PADDING - (index + 1) * LINE_HEIGHT;
            MazeGame.font.draw(batch, notif.message, screenWidth - boxWidth, y);
            index++;
        }
        
        batch.setColor(Color.WHITE);
    }
    
    public static NotificationManager instance = new NotificationManager();
}
