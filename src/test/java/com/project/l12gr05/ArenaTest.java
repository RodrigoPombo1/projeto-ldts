package com.project.l12gr05;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArenaTest {
    Arena arena;

    @BeforeEach
    public void setUp() {
        arena = new Arena(40, 20);
    }

    @Test
    public void arenaConstructorTest() {
        assertEquals(arena.width, 40);
        assertEquals(arena.height, 20);
        assertEquals(arena.snake.getPosition(), new Snake(20, 10).getPosition());
//        technically would still need to test the other lines in the arena constructor
    }

    @Test
    public void arenaDrawTest() {
        TextGraphics screen = Mockito.mock(TextGraphics.class);
        Snake snake = Mockito.mock(Snake.class);
        Teleporter teleporter = Mockito.mock(Teleporter.class);
        List<Teleporter> teleporters = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            teleporters.add(teleporter);
        }
        Wall wall = Mockito.mock(Wall.class);
        List<Wall> walls = new ArrayList<>();
        for (int i = 0; i < 118; i++) {
            walls.add(wall);
        }
        SnakeBodyPart snakeBodyPart = Mockito.mock(SnakeBodyPart.class);
        List<SnakeBodyPart> snakeBodyParts = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            snakeBodyParts.add(snakeBodyPart);
        }
        Monster monster = Mockito.mock(Monster.class);
        List<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            monsters.add(monster);
        }
        MovingFruit movingFruit = Mockito.mock(MovingFruit.class);
        List<MovingFruit> movingFruits = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            movingFruits.add(movingFruit);
        }
        Fruit fruit = Mockito.mock(Fruit.class);
        List<Fruit> fruits = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fruits.add(fruit);
        }

        arena.draw(screen, snake, teleporters, walls, snakeBodyParts, monsters, movingFruits, fruits);

        Mockito.verify(screen, Mockito.times(1))
                .setBackgroundColor(TextColor.Factory.fromString("#41980A"));
        Mockito.verify(screen, Mockito.times(1))
                .fillRectangle(new TerminalPosition(0, 0), new TerminalSize(40,20), ' ');
        Mockito.verify(snake, Mockito.times(1)).draw(screen);
        Mockito.verify(teleporter, Mockito.times(2)).draw(screen);
        Mockito.verify(wall, Mockito.times(118)).draw(screen);
        Mockito.verify(snakeBodyPart, Mockito.times(2)).draw(screen);
        Mockito.verify(monster, Mockito.times(5)).draw(screen);
        Mockito.verify(movingFruit, Mockito.times(3)).draw(screen);
        Mockito.verify(fruit, Mockito.times(5)).draw(screen);
    }
}
