package main.java.com.john.minefield.model;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board implements FieldObserver {
    private int rows;
    private int columns;
    private int mines;

    private final List<Field> fields = new ArrayList<>();
    private final List<Consumer<EventResult>> observers =
            new ArrayList<>();

    public Board(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;

        generateFields();
        joinNeighbors();
        sortMines();
    }

    public void registerObserver(Consumer<EventResult> observer){
        observers.add(observer);
    }

    private void notifyObservers(boolean result){
        observers.stream()
                .forEach(o -> o.accept(new EventResult(result)));
    }

    private void generateFields() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Field field = new Field(row, column);
                field.registerObserver(this);
                fields.add(field);
            }
        }
    }

    public void open(int row, int column){

        fields.parallelStream()
                .filter(f -> f.getRow() == row && f.getColumn() == column)
                .findFirst()
                .ifPresent(f -> f.open());
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

    @Override
    public void eventOccurred(Field field, FieldEvent event) {
        if(event == FieldEvent.EXPLOSION){
            showMines();
            notifyObservers(false);
        }else if(reachedGoal()){
            notifyObservers(true);
        }
    }

    private void showMines(){
        fields.stream()
                .filter(c -> c.isMined())
                .forEach(c -> c.setOpen(true));
    }

}
