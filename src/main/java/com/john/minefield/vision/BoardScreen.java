package main.java.com.john.minefield.vision;

import main.java.com.john.minefield.model.Board;

import javax.swing.*;
import java.awt.*;

public class BoardScreen extends JPanel {
    public BoardScreen(Board board) {
        setLayout(new GridLayout(board.getRows(), board.getColumns()));

        board.forEachField(c -> add(new FieldButton(c)));
        board.registerObserver(e -> {
            SwingUtilities.invokeLater(() -> {
                if(e.isYouWin()){
                    JOptionPane.showMessageDialog(this, "You WIN :)");
                } else{
                    JOptionPane.showMessageDialog(this, "You LOSE :(");
                }
                board.restart();
            });


        });
    }
}
