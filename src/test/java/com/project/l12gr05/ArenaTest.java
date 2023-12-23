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
        for (int i = 0; i < 2; i++) {
            walls.add(wall);
        }
        SnakeBodyPart snakeBodyPart = Mockito.mock(SnakeBodyPart.class);
        List<SnakeBodyPart> snakeBodyParts = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            snakeBodyParts.add(snakeBodyPart);
        }
        Monster monster = Mockito.mock(Monster.class);
        List<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            monsters.add(monster);
        }
        MovingFruit movingFruit = Mockito.mock(MovingFruit.class);
        List<MovingFruit> movingFruits = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            movingFruits.add(movingFruit);
        }
        Fruit fruit = Mockito.mock(Fruit.class);
        List<Fruit> fruits = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            fruits.add(fruit);
        }

        arena.draw(screen, snake, teleporters, walls, snakeBodyParts, monsters, movingFruits, fruits);

        Mockito.verify(screen, Mockito.times(1))
                .setBackgroundColor(TextColor.Factory.fromString("#41980A"));
        Mockito.verify(screen, Mockito.times(1))
                .fillRectangle(new TerminalPosition(0, 0), new TerminalSize(40,20), ' ');
        Mockito.verify(snake, Mockito.times(1)).draw(screen);
        Mockito.verify(teleporter, Mockito.times(2)).draw(screen);
        Mockito.verify(wall, Mockito.times(2)).draw(screen);
        Mockito.verify(snakeBodyPart, Mockito.times(2)).draw(screen);
        Mockito.verify(monster, Mockito.times(2)).draw(screen);
        Mockito.verify(movingFruit, Mockito.times(2)).draw(screen);
        Mockito.verify(fruit, Mockito.times(2)).draw(screen);
    }

    @Test
    public void moveSnakeTest() {
        Snake snake = Mockito.mock(Snake.class);
        Mockito.when(snake.getPosition()).thenReturn(new Position(21, 10));

        arena.moveSnake(new Position(21, 10), snake);

        assertEquals(21, snake.getPosition().getX());
        assertEquals(10, snake.getPosition().getY());
    }

    @Test
    public void moveSnakeCanNotBecauseOfXTest() {
        Snake snake = Mockito.mock(Snake.class);
        Mockito.when(snake.getPosition()).thenReturn(new Position(20, 10));

        arena.moveSnake(new Position(-1, 10), snake);

        assertEquals(20, snake.getPosition().getX());
        assertEquals(10, snake.getPosition().getY());
    }

    @Test
    public void moveSnakeCanNotBecauseOfYTest() {
        Snake snake = Mockito.mock(Snake.class);
        Mockito.when(snake.getPosition()).thenReturn(new Position(20, 10));

        arena.moveSnake(new Position(20, -1), snake);

        assertEquals(20, snake.getPosition().getX());
        assertEquals(10, snake.getPosition().getY());
    }

    @Test
    public void moveSnakeIntoFirstTeleporterTest() {
        arena.moveSnake(new Position(9, 5), arena.snake);
        arena.moveSnake(new Position(10, 5), arena.snake);

        assertEquals(31, arena.snake.getPosition().getX());
        assertEquals(15, arena.snake.getPosition().getY());

        arena.moveSnake(new Position(11, 5), arena.snake);
        arena.moveSnake(new Position(10, 5), arena.snake);

        assertEquals(29, arena.snake.getPosition().getX());
        assertEquals(15, arena.snake.getPosition().getY());

        arena.moveSnake(new Position(10, 4), arena.snake);
        arena.moveSnake(new Position(10, 5), arena.snake);

        assertEquals(30, arena.snake.getPosition().getX());
        assertEquals(16, arena.snake.getPosition().getY());

        arena.moveSnake(new Position(10, 6), arena.snake);
        arena.moveSnake(new Position(10, 5), arena.snake);

        assertEquals(30, arena.snake.getPosition().getX());
        assertEquals(14, arena.snake.getPosition().getY());
    }

    @Test
    public void moveSnakeIntoSecondTeleporterTest() {
        arena.moveSnake(new Position(29, 15), arena.snake);
        arena.moveSnake(new Position(30, 15), arena.snake);

        assertEquals(11, arena.snake.getPosition().getX());
        assertEquals(5, arena.snake.getPosition().getY());

        arena.moveSnake(new Position(31, 15), arena.snake);
        arena.moveSnake(new Position(30, 15), arena.snake);

        assertEquals(9, arena.snake.getPosition().getX());
        assertEquals(5, arena.snake.getPosition().getY());

        arena.moveSnake(new Position(30, 14), arena.snake);
        arena.moveSnake(new Position(30, 15), arena.snake);

        assertEquals(10, arena.snake.getPosition().getX());
        assertEquals(6, arena.snake.getPosition().getY());

        arena.moveSnake(new Position(30, 16), arena.snake);
        arena.moveSnake(new Position(30, 15), arena.snake);

        assertEquals(10, arena.snake.getPosition().getX());
        assertEquals(4, arena.snake.getPosition().getY());
    }
}
