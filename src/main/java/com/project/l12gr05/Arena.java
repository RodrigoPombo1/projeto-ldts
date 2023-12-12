package com.project.l12gr05;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;
    private Snake snake;
    private List<SnakeBodyPart> SnakeBodyParts;
    private List<Wall> walls;
    private List<Fruit> fruits;
    private int length = 3; //length of the snake, will be implemented with growth

    Arena(int width, int height) {
        this.width = width;
        this.height = height;
        snake = new Snake(10, 10);
        SnakeBodyParts = createSnakeBodyParts();
        walls = createWalls();
        fruits = createFruits();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void draw(TextGraphics screen) {
        screen.setBackgroundColor(TextColor.Factory.fromString("#41980A"));
        screen.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        snake.draw(screen);

        for (Wall wall : walls)
            wall.draw(screen);
        for (SnakeBodyPart snakeBodyPart : SnakeBodyParts) {
            snakeBodyPart.draw(screen);
        }
        for(Fruit fruit : fruits)
            fruit.draw(screen);
    }

    public void moveSnake(Position position) {
        Position initial_snake_position = snake.getPosition();
        boolean was_position_changed = false;
        if (canSnakeMove(position)) {
            was_position_changed = true;
            // need to implement tp check for snake here
            snake.setPosition(position);
        }
        boolean wasFruitCollected = collectFruit();
        if (was_position_changed) {
            updateSnakeNewBodyParts(initial_snake_position, wasFruitCollected);
        }
//        if (wasFruitCollected) {
//            fruits.addNewFruit();
//        }
    }

    public void updateSnakeNewBodyParts(Position position_to_add, boolean wasFruitCollected) {
        List<SnakeBodyPart> aux = new ArrayList<>();
        aux.add(new SnakeBodyPart(position_to_add.getX(), position_to_add.getY()));
        if (!wasFruitCollected) {
            for (int i = 0; i < SnakeBodyParts.size() - 1; i++) {
                aux.add(SnakeBodyParts.get(i));
            }
            SnakeBodyParts = aux;
            return;
        }
        for (int i = 0; i < SnakeBodyParts.size(); i++) {
            aux.add(SnakeBodyParts.get(i));
        }
        SnakeBodyParts = aux;
        return;
    }

    public boolean verifySnakeBodyCollisions() {
        return SnakeBodyParts.contains(new SnakeBodyPart(snake.getPosition().getX(), snake.getPosition().getY()));
    }

    public boolean canSnakeMove(Position position) {
        return (position.getX() >= 0 && position.getX() <= width) && (position.getY() >= 0 && position.getY() <= height);
    }

    public List<SnakeBodyPart> createSnakeBodyParts() {
        List<SnakeBodyPart> res = new ArrayList<>();
        int number_of_starting_body_parts = 2;
        Position snake_position = snake.getPosition();
        for (int i = 0; i < number_of_starting_body_parts; i++) {
            res.add(new SnakeBodyPart(snake_position.getX() + 1 + i, snake_position.getY()));
        }
        return res;
    }

    public Position moveUp() {
        return new Position(snake.getPosition().getX(), snake.getPosition().getY() - 1);
    }
    public Position moveDown() {
        return new Position(snake.getPosition().getX(), snake.getPosition().getY() + 1);
    }
    public Position moveLeft() {
        return new Position(snake.getPosition().getX()  - 1, snake.getPosition().getY());
    }
    public Position moveRight() {
        return new Position(snake.getPosition().getX()  + 1, snake.getPosition().getY());
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }
    public boolean verifyWallCollisions(){
        return walls.contains(new Wall(snake.getPosition().getX(), snake.getPosition().getY()));
    }

    private List<Fruit> createFruits() {
        Random random = new Random();
        ArrayList<Fruit> fruits = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            fruits.add(new Fruit(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return fruits;
    }
    private boolean collectFruit(){
        for(Fruit fruit : fruits){
            if(snake.getPosition().equals(fruit.getPosition())) {
                fruits.remove(fruit);
                length++;
                return true;
            }
        }
        return false;
    }
}