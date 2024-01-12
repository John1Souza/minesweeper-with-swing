package main.java.com.john.minefield.vision;

import main.java.com.john.minefield.model.Board;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    public MainScreen() {
        Board board = new Board(16, 30, 5);
        BoardScreen boardScreen = new BoardScreen(board);
        add(boardScreen);
        setTitle("MineSweeper");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainScreen();
    }
}
