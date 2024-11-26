package scrabble;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameTerminal extends JPanel {
    private JTextArea terminalOutput;
    private JTextField terminalInput;
    private TileGenerator tileGenerator; // Reference to the TileGenerator
    private Map<String, Runnable> commands; // Command registry

    public GameTerminal(TileGenerator tileGenerator) {
        this.tileGenerator = tileGenerator; // Inject TileGenerator instance
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        // Terminal Output
        terminalOutput = new JTextArea();
        terminalOutput.setEditable(false);
        terminalOutput.setBackground(Color.WHITE);
        terminalOutput.setForeground(Color.BLACK);
        terminalOutput.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Add scrolling for the output
        JScrollPane scrollPane = new JScrollPane(terminalOutput);
        scrollPane.setPreferredSize(new Dimension(700, 100));

        // Terminal Input
        terminalInput = new JTextField();
        terminalInput.setBackground(Color.WHITE);
        terminalInput.setForeground(Color.BLACK);
        terminalInput.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        terminalInput.addActionListener(e -> {
            String input = terminalInput.getText().trim();
            processCommand(input);
            terminalInput.setText(""); // Clear input field
        });

        add(scrollPane, BorderLayout.CENTER);
        add(terminalInput, BorderLayout.SOUTH);

        // Initialize commands
        initializeCommands();
    }

    private void initializeCommands() {
        commands = new HashMap<>();

        // Regular commands
        commands.put("help", this::showHelp);
        commands.put("clear", this::clearTerminal);
        commands.put("exit", () -> appendText("Exiting the terminal..."));
        commands.put("about", () -> appendText("Scrabble Game Terminal V1.0."));

        // Cheat commands
        commands.put("reveal-all", this::cheatRevealAllTiles);
        commands.put("add-points", () -> cheatAddPoints(50));
        commands.put("unlimited-tiles", this::cheatUnlimitedTiles);
        commands.put("reload-board", this::cheatReloadBoard); // Add reload-board command
    }

    private void processCommand(String input) {
        if (input.isEmpty()) {
            appendText("No command entered.");
            return;
        }

        // Check for matching commands
        if (commands.containsKey(input)) {
            commands.get(input).run(); // Execute the associated action
        } else {
            appendText("Unknown command: " + input);
        }
    }

    // Command: Show help
    private void showHelp() {
        appendText("Available commands:");
        appendText("- help: Show this help message.");
        appendText("- clear: Clear the terminal.");
        appendText("- exit: Exit the terminal.");
        appendText("- about: Show information about the game.");
        appendText("- reveal-all: Reveal all tiles on the board (cheat).");
        appendText("- add-points: Add 50 points to your score (cheat).");
        appendText("- unlimited-tiles: Get unlimited tiles (cheat).");
        appendText("- reload-board: Reload the board with optional animation (cheat).");
        appendText("  Usage: reload-board [animation] (e.g., reload-board true)");
    }

    // Command: Clear the terminal output
    private void clearTerminal() {
        terminalOutput.setText("");
    }

    // Cheat command: Reveal all tiles
    private void cheatRevealAllTiles() {
        appendText("Cheat activated: All tiles revealed!");
        tileGenerator.revealAllTiles(); // Notify TileGenerator to reveal tiles
    }

    // Cheat command: Add points to the player's score
    private void cheatAddPoints(int points) {
        appendText("Cheat activated: " + points + " points added to your score!");
        // Logic to add points (if integrated with a scoring system)
    }

    // Cheat command: Enable unlimited tiles for the player
    private void cheatUnlimitedTiles() {
        appendText("Cheat activated: Unlimited tiles enabled!");
        // Logic for unlimited tiles (e.g., flag in game logic)
    }

    // Cheat command: Reload the board
    private void cheatReloadBoard() {
        appendText("Reloading the board...");
        String input = JOptionPane.showInputDialog(
                this,
                "Enable animation? (true/false):",
                "Reload Board",
                JOptionPane.QUESTION_MESSAGE
        );

        boolean withAnimation = "true".equalsIgnoreCase(input);
        appendText("Animation: " + (withAnimation ? "Enabled" : "Disabled"));
        tileGenerator.reloadBoard(withAnimation); // Reload the board in TileGenerator
    }

    // Append text to the terminal output
    public void appendText(String text) {
        terminalOutput.append(text + "\n");
        terminalOutput.setCaretPosition(terminalOutput.getDocument().getLength());
    }
}
