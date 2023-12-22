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

public class MovingFruitTest {
    MovingFruit movingFruit;

    @BeforeEach
    public void setUp() {
        movingFruit = new MovingFruit(1,2);
    }

    @Test
    public void MovingFruitPosition() {
        assertEquals(1, movingFruit.getPosition().getX());
        assertEquals(2, movingFruit.getPosition().getY());
    }

    @Test
    public void MovingFruitPositionChange() {
        movingFruit.setPosition(new Position(3,4));
        assertEquals(3, movingFruit.getPosition().getX());
        assertEquals(4, movingFruit.getPosition().getY());
    }

    @Test
    public void MovingFruitDraw() {
        TextGraphics screen = Mockito.mock(TextGraphics.class);
        movingFruit.draw(screen);
        Mockito.verify(screen, Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        Mockito.verify(screen, Mockito.times(1)).enableModifiers(SGR.BOLD);
        Mockito.verify(screen, Mockito.times(1)).putString(new TerminalPosition(1, 2), "F");
    }

    @Test
    public void MovingFruitEquals() {
        MovingFruit movingFruit2 = new MovingFruit(1,2);
        assertEquals(movingFruit, movingFruit2);
    }

    @Test
    public void MovingFruitNotEquals() {
        MovingFruit movingFruit2 = new MovingFruit(3,4);
        assertNotEquals(movingFruit,movingFruit2);
    }

    @Test
    public void MovingFruitEqualsItself() {
        assertEquals(movingFruit, movingFruit);
    }

    @Test
    public void MovingFruitNotEqualsNonMovingFruitObject() {
        assertNotEquals(movingFruit, "not a moving fruit");
    }

    @Test
    public void MovingFruitNotEqualsNull() {
        assertNotEquals(movingFruit, null);
    }
}
