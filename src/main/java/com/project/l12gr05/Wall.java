package com.project.l12gr05;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Wall extends Element{

    public Wall(int x, int y) {super(x,y);}

    public void draw(TextGraphics screen){
        screen.setForegroundColor(TextColor.Factory.fromString("#333333"));
        screen.enableModifiers(SGR.BOLD);
        screen.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "#");
    }

    @Override
    public boolean equals(Object o){
        if(o == null || this.getClass() != o.getClass()) return false;
        return(this == o || this.getPosition().equals(((Wall) o).getPosition()));
    }
}