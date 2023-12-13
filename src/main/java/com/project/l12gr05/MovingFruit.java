package com.project.l12gr05;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class MovingFruit extends Element {
    public MovingFruit(int x, int y) {
        super(x, y);
    }
    public void draw(TextGraphics screen) {
        screen.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        screen.enableModifiers(SGR.BOLD);
        screen.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "F");
    }

    public Position move() {
        Position movingFruitPosition = this.getPosition();
        Random random = new Random();
        int direction = random.nextInt(4);
        Position new_position = switch (direction) {
            case 0 -> new Position(movingFruitPosition.getX(), movingFruitPosition.getY() - 1);
            case 1 -> new Position(movingFruitPosition.getX(), movingFruitPosition.getY() + 1);
            case 2 -> new Position(movingFruitPosition.getX() - 1, movingFruitPosition.getY());
            case 3 -> new Position(movingFruitPosition.getX() + 1, movingFruitPosition.getY());
            default -> null;
        };
        return new_position;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || this.getClass() != o.getClass()) return false;
        return(this == o || this.getPosition().equals(((MovingFruit) o).getPosition()));
    }
}
