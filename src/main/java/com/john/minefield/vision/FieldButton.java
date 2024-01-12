package main.java.com.john.minefield.vision;

import main.java.com.john.minefield.model.Field;
import main.java.com.john.minefield.model.FieldEvent;
import main.java.com.john.minefield.model.FieldObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FieldButton extends JButton implements FieldObserver, MouseListener {

    private final Color BG_DEFAULT = new Color(184, 184, 184);
    private final Color BG_MARK = new Color(8, 179, 247);
    private final Color BG_EXPLOSION = new Color(189, 66, 68);
    private final Color TEXT_GREEN = new Color(0, 100, 0);
    private Field field;


    public FieldButton(Field field) {
        this.field = field;
        setBackground(BG_DEFAULT);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);
        field.registerObserver(this);
    }

    @Override
    public void eventOccurred(Field field, FieldEvent event) {
        switch (event){
            case OPEN:
                applyStyleOpen();
                break;
            case MARK:
                applyStyleMark();
                break;
            case EXPLOSION:
                applyStyleExplosion();
                break;
            default:
                applyStyleDefault();
        }
    }

    private void applyStyleDefault() {
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }

    private void applyStyleExplosion() {
        setBackground(BG_EXPLOSION);
        setForeground(Color.WHITE);
        setText("X");
    }

    private void applyStyleMark() {
        setBackground(BG_MARK);
        setForeground(Color.BLACK);
        setText("M");
    }

    private void applyStyleOpen() {

        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        if(field.isMined()){
            setBackground(BG_EXPLOSION);
            return;
        }


        setBackground(BG_DEFAULT);

        switch (field.minesOnNeighbor()){
            case 1:
                setForeground(TEXT_GREEN);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
                setForeground(Color.YELLOW);
                break;
            case 4:
            case 5:
            case 6:
                setForeground(Color.RED);
                break;
            default:
                setForeground(Color.PINK);
        }
        String value = !field.securityNeighbor() ? field.minesOnNeighbor() + "" : "";
        setText(value);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1){
            field.open();
        }else{
            field.changeTag();
        }
    }
    public void mouseClicked(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}


    // Mouse event interface

}
