package scrabble;

import javax.swing.*;
import java.awt.*;

public class Tile {
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;

    public TileType tileType;
    public String tileLetter;
    private int xPos, yPos;
    private Color tileColor;


    public Tile(int x, int y, Color color, TileType tileType) {
        this.xPos = x;
        this.yPos = y;
        this.tileColor = color;
        this.tileType = tileType;
    }

    // Getters and Setters
    public int getX() {
        return xPos;
    }

    public void setX(int x) {
        this.xPos = x;
    }

    public int getY() {
        return yPos;
    }

    public void setY(int y) {
        this.yPos = y;
    }

    public Color getTileColor() {
        return tileColor;
    }

    public void setTileColor(Color tileColor) {
        this.tileColor = tileColor;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public void draw(Graphics g) {
        g.setColor(tileColor);
        g.fillRect(xPos, yPos, WIDTH, HEIGHT);
    }
}
