/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package scrabble;

import javax.swing.SwingUtilities;

/**
 *
 * @author 30506789
 */
public class Scrabble {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TileGenerator::createAndShowGUI);
    }
    
}
