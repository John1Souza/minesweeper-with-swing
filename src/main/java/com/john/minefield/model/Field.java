package main.java.com.john.minefield.model;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Field {
    private final int row;
    private final int column;

    private boolean isOpen = false;
    private boolean undermined;
    private boolean marked;

    private List<Field> neighbors = new ArrayList<>();
    Field(int row, int column){
        this.row = row;
        this.column = column;
    }

    boolean addNeighbors(Field neighbor){
        boolean differentRow = row != neighbor.row;
        boolean differentColumn = column != neighbor.column;
        boolean diagonal = differentRow && differentColumn;

        int deltaRow = Math.abs(row - neighbor.row);
        int deltaColumn = Math.abs(column - neighbor.column);
        int generalDelta = deltaColumn + deltaRow;

        if(generalDelta == 1 && !diagonal){
            neighbors.add(neighbor);
            return true;
        }else if (generalDelta == 2 && diagonal){
            neighbors.add(neighbor);
            return true;
        }else{
            return false;
        }
    }

    void changeTag(){
        if(!isOpen){
            marked = !marked;
        }
    }

    boolean open(){
        if(!isOpen && !marked){
            isOpen = true;

            if(undermined){
                // TODO to implement new version
            }
            if(securityNeighbor()){
                neighbors.forEach(Field::open);
            }
            return true;

        }else{
            return false;
        }
    }

    boolean securityNeighbor(){
        return neighbors.stream().noneMatch(v -> v.undermined);
    }

    void undermine(){
        undermined = true;
    }
    public boolean isMined(){
        return undermined;
    }
    public boolean isMarked(){
        return marked;
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
    }

    public boolean isOpen(){
        return isOpen;
    }
    public boolean isClosed(){
        return !isOpen;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    boolean reachedGoal() {
        boolean unraveled = !undermined && isOpen;
        boolean protectedOne = undermined && marked;
        return unraveled || protectedOne;
    }


    long minesOnNeighbor(){
        return neighbors.stream().filter(v -> v.undermined).count();
    }

    void restart(){
        isOpen = false;
        undermined = false;
        marked = false;
    }

//    public String toString(){
//        if(marked){
//            return ColoredText.GREEN + "x" + ColoredText.RESET;
//        } else if(isOpen && undermined){
//            return ColoredText.RED + "*" + ColoredText.RESET;
//        }else if(isOpen && minesOnNeighbor() > 0){
//            //Unecessery Long.toString(minesOnNeighbor())
//            return ColoredText.ORANGE + minesOnNeighbor() + ColoredText.RESET;
//        }else if(isOpen){
//            return " ";
//        }else{
//            return ColoredText.BLUE + "?" + ColoredText.RESET;
//        }
//    }

}
