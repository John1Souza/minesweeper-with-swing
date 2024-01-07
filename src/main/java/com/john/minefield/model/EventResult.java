package main.java.com.john.minefield.model;

public class EventResult {
    private final boolean youWin;

    public EventResult(boolean youWin) {
        this.youWin = youWin;
    }

    public boolean isYouWin() {
        return youWin;
    }
}
