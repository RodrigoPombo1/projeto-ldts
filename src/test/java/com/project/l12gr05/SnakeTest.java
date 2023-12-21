package com.project.l12gr05;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnakeTest {
    private Snake snake;

    @BeforeEach
    public void setUp() {
        snake = new Snake(1,2);
    }

    @Test
    public void SnakePosition() {
        assertEquals(1, snake.getPosition().getX());
        assertEquals(2, snake.getPosition().getY());
    }

    @Test
    public void SnakePositionChange() {
        snake.setPosition(new Position(3,4));
        assertEquals(3, snake.getPosition().getX());
        assertEquals(4, snake.getPosition().getY());
    }

    @Test
    public void SnakeDraw() {
        TextGraphics screen = Mockito.mock(TextGraphics.class);
        snake.draw(screen);
        Mockito.verify(screen, Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#000000"));
        Mockito.verify(screen, Mockito.times(1)).enableModifiers(SGR.BOLD);
        Mockito.verify(screen, Mockito.times(1)).putString(new TerminalPosition(1, 2), "X");
    }
}
