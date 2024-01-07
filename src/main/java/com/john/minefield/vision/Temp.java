package main.java.com.john.minefield.vision;

import main.java.com.john.minefield.model.Board;

public class Temp {
    public static void main(String[] args) {
        Board board = new Board(3,3,9);
        board.registerObserver(e -> {
            if(e.isYouWin()){
                System.out.println("You Win! :)");
            } else {
                System.out.println("You Lose! :(");
            }
        });
        board.changeTag(0, 0);
        board.changeTag(0, 1);
        board.changeTag(0, 2);
        board.changeTag(1, 0);
        board.changeTag(1, 1);
        board.changeTag(1, 2);
        board.changeTag(2, 0);
        board.changeTag(2, 1);
        board.open(2,2);


        //board.changeTag(2, 2);
    }
}
