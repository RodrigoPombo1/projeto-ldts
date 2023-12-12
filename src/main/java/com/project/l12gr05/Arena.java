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
    private List<Wall> walls;
    private List<Fruit> fruits;
    private int length = 3; //length of the snake, will be implemented with growth

    Arena(int width, int height) {
        this.width = width;
        this.height = height;
        snake = new Snake(10, 10);
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
        for(Fruit fruit : fruits)
            fruit.draw(screen);
    }

    public void moveSnake(Position position) {
        if (canSnakeMove(position)) snake.setPosition(position);
        collectFruit();
    }

    public boolean canSnakeMove(Position position) {
        return (position.getX() >= 0 && position.getX() <= width) && (position.getY() >= 0 && position.getY() <= height);
    }

    public Position moveUp() {return new Position(snake.getPosition().getX(), snake.getPosition().getY() - 1);}
    public Position moveDown() {
        return new Position(snake.getPosition().getX(), snake.getPosition().getY() + 1);
    }
    public Position moveLeft() {
        return new Position(snake.getPosition().getX()  - 1, snake.getPosition().getY());
    }
    public Position moveRight() {return new Position(snake.getPosition().getX()  + 1, snake.getPosition().getY());}

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
        if(walls.contains(new Wall(snake.getPosition().getX(), snake.getPosition().getY()))){
            System.out.println("GAME OVER");
            return true;
        }
        return false;
    }

    private List<Fruit> createFruits() {
        Random random = new Random();
        ArrayList<Fruit> fruits = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            fruits.add(new Fruit(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return fruits;
    }
    private void collectFruit(){
        for(Fruit fruit : fruits){
            if(snake.getPosition().equals(fruit.getPosition())) {
                fruits.remove(fruit);
                length++;
                break;
            }
        }
    }
}