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

public class FruitTest {

    private Fruit fruit;

    @BeforeEach
    public void setUp() {
        fruit = new Fruit(1,2);
    }

    @Test
    public void FruitPosition() {
        assertEquals(1, fruit.getPosition().getX());
        assertEquals(2, fruit.getPosition().getY());
    }

    @Test
    public void FruitPositionChange() {
        fruit.setPosition(new Position(3,4));
        assertEquals(3, fruit.getPosition().getX());
        assertEquals(4, fruit.getPosition().getY());
    }

    @Test
    public void FruitDraw() {
        TextGraphics screen = Mockito.mock(TextGraphics.class);
        fruit.draw(screen);
        Mockito.verify(screen, Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        Mockito.verify(screen, Mockito.times(1)).enableModifiers(SGR.BOLD);
        Mockito.verify(screen, Mockito.times(1)).putString(new TerminalPosition(1, 2), "o");
    }

    @Test
    public void FruitEquals() {
        Fruit fruit2 = new Fruit(1,2);
        assertEquals(fruit, fruit2);
    }

    @Test
    public void FruitNotEquals() {
        Fruit fruit2 = new Fruit(3,4);
        assertNotEquals(fruit,fruit2);
    }

    @Test
    public void FruitEqualsItself() {
        assertEquals(fruit, fruit);
    }

    @Test
    public void FruitNotEqualsNonFruitObject() {
        assertNotEquals("not a fruit", fruit);
    }

    @Test
    public void FruitNotEqualsNull() {
        assertNotEquals(fruit, null);
    }
}
