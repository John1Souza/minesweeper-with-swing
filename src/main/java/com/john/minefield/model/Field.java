package main.java.com.john.minefield.model;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Field {
    private final int row;
    private final int column;

    private boolean isOpen = false;
    private boolean undermined;
    private boolean marked;

    private List<Field> neighbors = new ArrayList<>();
    private List<FieldObserver> observers = new ArrayList<>();
    //private List<BiConsumer<Field, FieldEvent>> observersList = new ArrayList<>();

    Field(int row, int column){
        this.row = row;
        this.column = column;
    }

    public void registerObserver(FieldObserver observer){ // Chamar sempre que for notificar que aconteceu um evento
        observers.add(observer);
    }

    private void notifyObservers(FieldEvent event){
        observers.stream()
                .forEach(o -> o.eventOccurred(this, event));
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

    public void changeTag(){
        if(!isOpen){
            marked = !marked;
            if(marked){
                notifyObservers(FieldEvent.MARK);
            } else{
                notifyObservers(FieldEvent.UNMARK);
            }
        }
    }

    public boolean open(){
        if(!isOpen && !marked){
            isOpen = true;

            if(undermined){
                // TODO to implement new version
                notifyObservers(FieldEvent.EXPLOSION);
                return true;
            }
            setOpen(true);
            if(securityNeighbor()){
                neighbors.forEach(Field::open);
            }
            return true;

        }else{
            return false;
        }
    }

    public boolean securityNeighbor(){
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
        if(open){
            notifyObservers(FieldEvent.OPEN);
        }
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


    public int minesOnNeighbor(){
        return (int)neighbors.stream().filter(v -> v.undermined).count();
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
