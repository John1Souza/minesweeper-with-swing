package main.java.com.john.minefield.model;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {
    private int rows;
    private int columns;
    private int mines;

    private final List<Field> fields = new ArrayList<>();

    public Board(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;

        generateFields();
        joinNeighbors();
        sortMines();
    }

    private void generateFields() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                fields.add(new Field(row, column));
            }
        }
    }

    public void open(int row, int column){
        try{
            fields.parallelStream()
                    .filter(f -> f.getRow() == row && f.getColumn() == column)
                    .findFirst()
                    .ifPresent(f -> f.open());
        } catch (Exception e){
            // FIXME adjust implementation of the method open
            fields.forEach(c -> c.setOpen(true));
            throw e;
        }
    }

    public void changeTag(int row, int column){
        fields.parallelStream()
            .filter(f -> f.getRow() == row && f.getColumn() == column)
            .findFirst()
            .ifPresent(f -> f.changeTag());
    }


    private void joinNeighbors() {
        for(Field f1: fields){
            for(Field f2: fields){
                f1.addNeighbors(f2);
            }
        }
    }

    private void sortMines() {
        long armedMines = 0;
        //Predicate<Field> undermined = f -> f.isMined();
        Predicate<Field> undermined = Field::isMined;

        do{
           armedMines = fields.stream().filter(undermined).count();
           int randomValue = (int)(Math.random() * fields.size());
           fields.get(randomValue).undermine();
        }while(armedMines < mines);
    }

    public boolean reachedGoal(){
        //return fields.stream().allMatch(f -> f.reachedGoal());
        return fields.stream().allMatch(Field::reachedGoal);
    }

    public void restart(){
        //fields.stream().forEach(f -> f.restart());
        fields.forEach(Field::restart);
        sortMines();
    }
//
//    public String toString(){
//        StringBuilder sb = new StringBuilder();
//
//        int i = 0;
//        for (int row = 0; row < rows; row++) {
//
//            for (int column = 0; column < columns; column++) {
//                sb.append(" ");
//                sb.append(fields.get(i));
//                sb.append(" ");
//                i++;
//            }
//            sb.append("\n");
//        }
//        return sb.toString();
//    }
}
