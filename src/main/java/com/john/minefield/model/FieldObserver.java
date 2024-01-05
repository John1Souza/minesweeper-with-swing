package main.java.com.john.minefield.model;

public interface FieldObserver {

    public void eventOccurred(Field field, FieldEvent event);
}
