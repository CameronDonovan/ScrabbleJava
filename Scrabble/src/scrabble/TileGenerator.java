package scrabble;

import javax.swing.*;
import java.awt.*;

public class TileGenerator extends JPanel {
    public int rows = 15; // One row of tiles
    public int columns = 15; // Change as needed
    private Tile[] tiles;

    public TileGenerator() {
        tiles = new Tile[rows * columns];
         for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int x = col * (30 + 5); // Calculate x position based on column
                int y = row * (30 + 5); // Calculate y position based on row
                
                Color color = Color.LIGHT_GRAY; // Default color
                 
                // Set colors for premium squares
                if ((row == 0 && col == 0) || (row == 0 && col == 14) ||
                    (row == 14 && col == 0) || (row == 14 && col == 14)) {
                    color = Color.ORANGE; // Corner tiles color
                } else if ((row == 0 && (col == 3 || col == 11)) || 
                           (row == 14 && (col == 3 || col == 11)) || 
                           (col == 0 && (row == 3 || row == 11)) || 
                           (col == 14 && (row == 3 || row == 11))) {
                    color = Color.CYAN; // Double letter score
                } else if ((row == 1 && (col == 1 || col == 13)) || 
                           (row == 13 && (col == 1 || col == 13)) || 
                           (row == 2 && (col == 2 || col == 12)) || 
                           (row == 12 && (col == 2 || col == 12)) || 
                           (row == 3 && (col == 3 || col == 11)) || 
                           (row == 11 && (col == 3 || col == 11))) {
                    color = Color.PINK; // Double word score
                } else if (row == 7 && col == 7) {
                    color = Color.RED; // Center square
                }
                tiles[row * columns + col] = new Tile(x, y, color); // Store tile in a 1D array
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Tile tile : tiles) {
            tile.draw(g);
        }
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Scrabble V0");
        TileGenerator tileGenerator = new TileGenerator();
        frame.add(tileGenerator);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
