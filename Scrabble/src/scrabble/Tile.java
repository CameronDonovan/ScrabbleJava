package scrabble;

import java.awt.Color;
import java.awt.Graphics;

public class Tile {
    public float offset = 0;
    public int width = 30; 
    public int height = 30; 
    public int xPos, yPos;
    public Color tileColor;

    public Tile(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    
    }
    
    public Tile(int x, int y, Color color) {
        this.xPos = x;
        this.yPos = y;
        this.tileColor = color; // Set the tile color
    }

    public void draw(Graphics g) {
        g.setColor(tileColor);
        g.fillRect(xPos, yPos, width, height);
    }
}
