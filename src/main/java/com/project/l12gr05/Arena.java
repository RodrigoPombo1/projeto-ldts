package com.project.l12gr05;

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

    private List<Teleporter> teleporters;
    private List<SnakeBodyPart> snakeBodyParts;
    private List<Wall> walls;
    private List<Fruit> fruits = new ArrayList<>();

    private List<Monster> monsters;
    private int score = 0;

    Arena(int width, int height) {
        this.width = width;
        this.height = height;
        snake = new Snake(20, 10);
        snakeBodyParts = createSnakeBodyParts();
        walls = createWalls();
        teleporters = createTeleporters();
        createFruits();
        monsters = createMonsters();
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
        for (Teleporter teleporter : teleporters)
            teleporter.draw(screen);
        for (Wall wall : walls)
            wall.draw(screen);
        for (SnakeBodyPart snakeBodyPart : snakeBodyParts) {
            snakeBodyPart.draw(screen);
        }
        for (Monster monster : monsters) {
            monster.draw(screen);
        }
        for(Fruit fruit : fruits) {
            fruit.draw(screen);
        }
    }

    public void moveSnake(Position position) {
        Position initial_snake_position = snake.getPosition();
        boolean was_position_changed = false;
        if (canSnakeMove(position)) {
            was_position_changed = true;
            // teleporter check for snake here
            if (verifyTeleporterCollisions(position)) {
                for (Teleporter teleporter : teleporters) {
                    if (teleporter.getPosition().equals(position)) {
                        snake.setPosition(getTeleporterDestination(teleporter, initial_snake_position));
                        break;
                    }
                }
            } else {
                snake.setPosition(position);
            }
        }
        boolean wasFruitCollected = collectFruit();
        if (was_position_changed) {
            updateSnakeNewBodyParts(initial_snake_position, wasFruitCollected);
        }
        if (wasFruitCollected) {
            addNewFruit();
        }
    }

    public void addNewFruit() {
        Random random = new Random();
        boolean validPosition = false;
        Position position = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
        while (!checkPositionNotInSnakeBodyParts(position) || !checkPositionNotInWalls(position) || !checkPositionNotInTeleporters(position)) {
            position = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
        }
        fruits.add(new Fruit(position.getX(), position.getY()));
    }

    public List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Position position = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            while (!checkPositionNotInTeleporters(position)
                    || !checkPositionNotInSnakeBodyParts(position)
                    || !checkPositionNotInWalls(position)) {
                position = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            }
            monsters.add(new Monster(position.getX(), position.getY()));
        }
        return monsters;
    }

    public List<Teleporter> createTeleporters() {
        teleporters = new ArrayList<>();
        teleporters.add(new Teleporter(10,5));
        teleporters.add(new Teleporter(30, 15));
        return teleporters;
    }

    public boolean verifyTeleporterCollisions(Position Element_position) {
        for (Teleporter teleporter : teleporters) {
            if (teleporter.getPosition().equals(Element_position)) {
                return true;
            }
        }
        return false;
    }

    public Teleporter getOtherTeleporter(Teleporter teleporter) {
        for (Teleporter other_teleporter : teleporters) {
            if (!other_teleporter.equals(teleporter)) {
                return other_teleporter;
            }
        }
        return null;
    }

    public Position getTeleporterDestination(Teleporter teleporter, Position initial_element_position) {
        Teleporter destinationTeleporter = getOtherTeleporter(teleporter);
        Position destinationTeleporterPosition = destinationTeleporter.getPosition();
        Position initialTeleporterPosition = teleporter.getPosition();
        int x_difference = initialTeleporterPosition.getX() - initial_element_position.getX();
        int y_difference = initialTeleporterPosition.getY() - initial_element_position.getY();
        return new Position(destinationTeleporterPosition.getX() + x_difference,
                destinationTeleporterPosition.getY() + y_difference);
    }

    public void updateSnakeNewBodyParts(Position position_to_add, boolean wasFruitCollected) {
        List<SnakeBodyPart> aux = new ArrayList<>();
        aux.add(new SnakeBodyPart(position_to_add.getX(), position_to_add.getY()));
        if (!wasFruitCollected) {
            for (int i = 0; i < snakeBodyParts.size() - 1; i++) {
                aux.add(snakeBodyParts.get(i));
            }
            snakeBodyParts = aux;
            return;
        }
        for (int i = 0; i < snakeBodyParts.size(); i++) {
            aux.add(snakeBodyParts.get(i));
        }
        snakeBodyParts = aux;
        return;
    }

    public boolean verifySnakeBodyCollisions() {
        return !checkPositionNotInSnakeBodyParts(snake.getPosition());
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
    public boolean verifyWallCollisions() {
        return !checkPositionNotInWalls(snake.getPosition());
    }

    private void createFruits() {
        for (int i = 0; i < 5; i++)
            addNewFruit();
        return;
    }
    private boolean collectFruit(){
        for(Fruit fruit : fruits){
            if(snake.getPosition().equals(fruit.getPosition())) {
                fruits.remove(fruit);
                score++;
                return true;
            }
        }
        return false;
    }

    private boolean checkPositionNotInSnakeBodyParts(Position position) {
        for (SnakeBodyPart snakeBodyPart : snakeBodyParts) {
            if (snakeBodyPart.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkPositionNotInFruits(Position position) {
        for (Fruit fruit : fruits) {
            if (fruit.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkPositionNotInWalls(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkPositionNotInTeleporters(Position position) {
        for (Teleporter teleporter : teleporters) {
            if (teleporter.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }
}