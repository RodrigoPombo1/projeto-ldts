package com.project.l12gr05;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element {
    public Monster(int x, int y) {
        super(x,y);
    }
    public void draw(TextGraphics screen){
        screen.setForegroundColor(TextColor.Factory.fromString("#000000"));
        screen.enableModifiers(SGR.BOLD);
        screen.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "M");
    }

    public Position move() {
        Position monster_position = this.getPosition();
        Random random = new Random();
        int direction = random.nextInt(4);
        Position new_position = null;
        switch (direction) {
            case 0:
                new_position = new Position(monster_position.getX(), monster_position.getY() - 1);
                break;
            case 1:
                new_position = new Position(monster_position.getX(), monster_position.getY() + 1);
                break;
            case 2:
                new_position = new Position(monster_position.getX() - 1, monster_position.getY());
                break;
            case 3:
                new_position = new Position(monster_position.getX() + 1, monster_position.getY());
                break;
        }
        return new_position;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || this.getClass() != o.getClass()) return false;
        return(this == o || this.getPosition().equals(((Monster) o).getPosition()));
    }
}
