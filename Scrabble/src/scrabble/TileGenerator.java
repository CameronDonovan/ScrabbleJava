package scrabble;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TileGenerator extends JPanel {
    private static final int ROWS = 15;
    private static final int COLUMNS = 15;
    private static final int TILE_PADDING = 5;
    private static final int TILE_SIZE = Tile.WIDTH + TILE_PADDING;

    private Tile[] tiles;
    private int tilesRevealed = 0;
    private Timer revealTimer;
    private boolean TimerRun = false;
    private GameTerminal gameTerminal;

    public TileGenerator() {
        tiles = new Tile[ROWS * COLUMNS];

        // Generate the tiles for the board
        generateTiles();

        // Initialize the game terminal
        gameTerminal = new GameTerminal(this); // Pass a reference to GameTerminal

        // Set layout manager to BorderLayout
        setLayout(new BorderLayout());
        JPanel gameArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Draw only revealed tiles
                for (int i = 0; i < tilesRevealed && i < tiles.length; i++) {
                    tiles[i].draw(g);
                }
            }
        };
        gameArea.setPreferredSize(new Dimension(COLUMNS * TILE_SIZE, ROWS * TILE_SIZE));
        gameArea.setBackground(Color.WHITE);

        add(gameArea, BorderLayout.CENTER);
        add(gameTerminal, BorderLayout.SOUTH);

        // Initialize the reveal timer
        revealTimer = new Timer(22, e -> {
            tilesRevealed++;
            if (tilesRevealed >= tiles.length) {
                revealTimer.stop();
            }
            repaint();
        });

        // Start the animation if TimerRun is true
        if (TimerRun) {
            revealTimer.start();
        } else {
            tilesRevealed = tiles.length; // Reveal all tiles immediately
        }

        // Add mouse listener for interaction
        gameArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
    }

    private void generateTiles() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;

                Color color = Color.LIGHT_GRAY;
                TileType tileType = TileType.BLANK;

                // Assign different colors and types to specific positions
                if ((row == 0 && col == 0) || (row == 0 && col == 14) ||
                        (row == 14 && col == 0) || (row == 14 && col == 14) ||
                        (row == 0 && col == 7) || (row == 7 && col == 0) ||
                        (row == 14 && col == 7) || (row == 7 && col == 14)) {
                    color = Color.ORANGE;
                    tileType = TileType.TRIPLE_WORD;
                } else if ((row == 0 && (col == 3 || col == 11)) ||
                           (row == 14 && (col == 3 || col == 11)) ||
                           (col == 0 && (row == 3 || row == 11)) ||
                           (col == 14 && (row == 3 || row == 11)) ||
                            (row == 2) && (col == 6 || col == 8) ||
                        (row == 3 && col == 7)) {
                    color = Color.CYAN;
                    tileType = TileType.DOUBLE_LETTER;
                } else if(( (row == 5 && (col == 5 || col == 9)) ||
                        (row == 9 && (col == 9 || col == 5))) ||
                        (row == 1 && (col == 9 || col == 5)) ||
                        (row == 13 && (col == 9 || col == 5))) {
                    color = Color.magenta;
                    tileType = TileType.TRIPLE_LETTER;
                } else if ((row == 1 && (col == 1 || col == 13)) ||
                           (row == 13 && (col == 1 || col == 13)) ||
                           (row == 2 && (col == 2 || col == 12)) ||
                           (row == 12 && (col == 2 || col == 12)) ||
                           (row == 3 && (col == 3 || col == 11)) ||
                           (row == 11 && (col == 3 || col == 11)) ||
                           (row == 4 && (col == 4 || col == 10)) ||
                           (row == 6 && (col == 6 || col == 8)) ||
                           (row == 10 && (col == 4 || col == 10)) ||
                           (row == 8 && (col == 6 || col == 8))) {
                    color = Color.PINK;
                    tileType = TileType.DOUBLE_WORD;
                } else if (row == 7 && col == 7) {
                    color = Color.RED;
                    tileType = TileType.CENTRE;
                }

                tiles[row * COLUMNS + col] = new Tile(x, y, color, tileType);
            }
        }
    }

    private void handleMouseClick(int x, int y) {
        int row = y / TILE_SIZE;
        int col = x / TILE_SIZE;

        if (row >= 0 && row < ROWS && col >= 0 && col < COLUMNS) {
            Tile clickedTile = tiles[row * COLUMNS + col];
            String message = "Tile at row " + row + ", col " + col + " is of type: " + clickedTile.getTileType();
            gameTerminal.appendText(message);
        }
    }

    public void reloadBoard(boolean withAnimation) {
        generateTiles();
        tilesRevealed = 0;

        if (withAnimation) {
            TimerRun = true;
            if (revealTimer.isRunning()) {
                revealTimer.stop();
            }
            revealTimer.start();
        } else {
            TimerRun = false;
            tilesRevealed = tiles.length;
            repaint();
        }
    }

    public void revealAllTiles() {
        tilesRevealed = tiles.length;
        repaint();
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Scrabble Board");
        TileGenerator tileGenerator = new TileGenerator();
        frame.add(tileGenerator);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
