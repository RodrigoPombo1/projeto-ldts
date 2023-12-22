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

public class SnakeBodyPartTest {
    SnakeBodyPart snakeBodyPart;

    @BeforeEach
    public void setUp() {
        snakeBodyPart = new SnakeBodyPart(1,2);
    }

    @Test
    public void SnakeBodyPartPosition() {
        assertEquals(1, snakeBodyPart.getPosition().getX());
        assertEquals(2, snakeBodyPart.getPosition().getY());
    }

    @Test
    public void SnakeBodyPartPositionChange() {
        snakeBodyPart.setPosition(new Position(3,4));
        assertEquals(3, snakeBodyPart.getPosition().getX());
        assertEquals(4, snakeBodyPart.getPosition().getY());
    }

    @Test
    public void BodyPartDraw() {
        TextGraphics screen = Mockito.mock(TextGraphics.class);
        snakeBodyPart.draw(screen);
        Mockito.verify(screen, Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#333333"));
        Mockito.verify(screen, Mockito.times(1)).enableModifiers(SGR.BOLD);
        Mockito.verify(screen, Mockito.times(1)).putString(new TerminalPosition(1, 2), "s");
    }

    @Test
    public void SnakeBodyPartEquals() {
        SnakeBodyPart snakeBodyPart2 = new SnakeBodyPart(1,2);
        assertEquals(snakeBodyPart, snakeBodyPart2);
    }

    @Test
    public void SnakeBodyPartNotEquals() {
        SnakeBodyPart snakeBodyPart2 = new SnakeBodyPart(3,4);
        assertNotEquals(snakeBodyPart,snakeBodyPart2);
    }

    @Test
    public void SnakeBodyPartEqualsItself() {
        assertEquals(snakeBodyPart, snakeBodyPart);
    }

    @Test
    public void SnakeBodyPartNotEqualsNonSnakeBodyPartObject() {
        assertNotEquals(snakeBodyPart, "not a snake body part");
    }

    @Test
    public void SnakeBodyPartNotEqualsNull() {
        assertNotEquals(snakeBodyPart, null);
    }
}
