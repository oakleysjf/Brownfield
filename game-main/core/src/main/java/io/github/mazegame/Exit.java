package io.github.mazegame;

import java.awt.Graphics;
import java.awt.Color;

/** Legacy class for drawing an exit (unused in current LibGDX implementation). */
public class Exit {
    public void draw(Graphics g, int x, int y, int w, int h){
        Color prev = g.getColor();
        g.setColor(Color.WHITE);
        int rectW = Math.max(8,w-20);
        int rectH = 6;
        int rectX = x + (w - rectW) / 2;
        int rectY = y + (h - rectH) / 2;    
        g.fillRect(rectX, rectY, rectW, rectH);
        g.setColor(prev);
    }
}
