package com.project.l12gr05;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WallTest {
    Wall wall;

    @BeforeEach
    public void setUp() {
        wall = new Wall(1,2);
    }

    @Test
    public void WallPosition() {
        assertEquals(1, wall.getPosition().getX());
        assertEquals(2, wall.getPosition().getY());
    }

    @Test
    public void WallPositionChange() {
        wall.setPosition(new Position(3,4));
        assertEquals(3, wall.getPosition().getX());
        assertEquals(4, wall.getPosition().getY());
    }

    @Test
    public void WallDraw() {
        TextGraphics screen = Mockito.mock(TextGraphics.class);
        wall.draw(screen);
        Mockito.verify(screen, Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#333333"));
        Mockito.verify(screen, Mockito.times(1)).enableModifiers(SGR.BOLD);
        Mockito.verify(screen, Mockito.times(1)).putString(new TerminalPosition(1, 2), "#");
    }

    @Test
    public void WallEquals() {
        Wall wall2 = new Wall(1,2);
        assertEquals(wall, wall2);
    }

    @Test
    public void WallNotEquals() {
        Wall wall2 = new Wall(3,4);
        assertNotEquals(wall,wall2);
    }

    @Test
    public void WallEqualsItself() {
        assertEquals(wall, wall);
    }

    @Test
    public void WallNotEqualsNonWallObject() {
        assertNotEquals(wall, "not a wall");
    }

    @Test
    public void WallNotEqualsNull() {
        assertNotEquals(wall, null);
    }
}
