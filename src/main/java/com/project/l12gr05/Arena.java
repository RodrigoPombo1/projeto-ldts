package com.project.l12gr05;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    public int width;
    public int height;
    public Snake snake;

    public List<Teleporter> teleporters;
    public List<SnakeBodyPart> snakeBodyParts;
    public List<Wall> walls;
    public List<Fruit> fruits = new ArrayList<>();

    public List<Monster> monsters;

    public List<MovingFruit> movingFruits = new ArrayList<>();

    public int snakeBodyPartAddBecauseOfMovingFruitGoingAgainstSnake = 0;

    Arena(int width, int height) {
        this.width = width;
        this.height = height;
        snake = new Snake(20, 10);
        snakeBodyParts = createSnakeBodyParts();
        walls = createWalls();
        teleporters = createTeleporters();
        monsters = createMonsters();
        createMovingFruits();
        createFruits();
    }

    public void draw(TextGraphics screen, Snake snake, List<Teleporter> teleporters, List<Wall> walls,
                     List<SnakeBodyPart> snakeBodyParts, List<Monster> monsters, List<MovingFruit> movingFruits,
                     List<Fruit> fruits) {
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
        for (MovingFruit movingFruit : movingFruits) {
            movingFruit.draw(screen);
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
            if (!checkPositionNotInTeleporters(position)) {
                position = getTeleporterDestination(getTeleporter(position), initial_snake_position);
            }
            snake.setPosition(position);
        }
        boolean wasFruitCollected = collectFruit();
        boolean wasMovingFruitCollected = collectMovingFruit();
        if (was_position_changed) {
            updateSnakeNewBodyParts(initial_snake_position, wasFruitCollected, wasMovingFruitCollected);
        }
        if (wasFruitCollected) {
            addNewFruit();
        }
        if (wasMovingFruitCollected) {
            addNewMovingFruit();
        }
    }

    public void createMovingFruits() {
        for (int i = 0; i < 3; i++) {
            addNewMovingFruit();
        }
    }

    public void addNewMovingFruit() {
        RandomNumberGenerator random = new RandomNumberGenerator();
        Position position = new Position(random.randomNextInt(width - 2) + 1,
                random.randomNextInt(height - 2) + 1);
        while (!canElementMoveOrSpawn(position)) {
            position = new Position(random.randomNextInt(width - 2) + 1,
                    random.randomNextInt(height - 2) + 1);
        }
        movingFruits.add(new MovingFruit(position.getX(), position.getY()));
    }

    public Teleporter getTeleporter(Position position) {
        for (Teleporter teleporter : teleporters) {
            if (teleporter.getPosition().equals(position)) {
                return teleporter;
            }
        }
        return null;
    }

    public void addNewFruit() {
        RandomNumberGenerator random = new RandomNumberGenerator();
        Position position = new Position(random.randomNextInt(width - 2) + 1,
                random.randomNextInt(height - 2) + 1);
        while (!canElementMoveOrSpawn(position)) {
            position = new Position(random.randomNextInt(width - 2) + 1, random.randomNextInt(height - 2) + 1);
        }
        fruits.add(new Fruit(position.getX(), position.getY()));
    }

    public List<Monster> createMonsters() {
        RandomNumberGenerator random = new RandomNumberGenerator();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Position position = new Position(random.randomNextInt(width - 2) + 1,
                    random.randomNextInt(height - 2) + 1);
            while (!canElementMoveOrSpawn(position)) {
                position = new Position(random.randomNextInt(width - 2) + 1,
                        random.randomNextInt(height - 2) + 1);
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

    public void updateSnakeNewBodyParts(Position position_to_add, boolean wasFruitCollected, boolean wasMovingFruitCollected) {
        List<SnakeBodyPart> aux = new ArrayList<>();
        aux.add(new SnakeBodyPart(position_to_add.getX(), position_to_add.getY()));
        if (!(wasFruitCollected || wasMovingFruitCollected)
                && snakeBodyPartAddBecauseOfMovingFruitGoingAgainstSnake == 0) {
            for (int i = 0; i < snakeBodyParts.size() - 1; i++) {
                aux.add(snakeBodyParts.get(i));
            }
            snakeBodyParts = aux;
            return;
        }
        if (snakeBodyPartAddBecauseOfMovingFruitGoingAgainstSnake > 0
                && !(wasFruitCollected || wasMovingFruitCollected)) {
            snakeBodyPartAddBecauseOfMovingFruitGoingAgainstSnake--;
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

    public boolean verifyMonsterCollisions() {
        return !checkPositionNotInMonsters(snake.getPosition());
    }

    public boolean canSnakeMove(Position position) {
        return (position.getX() >= 0 && position.getX() <= width)
                && (position.getY() >= 0 && position.getY() <= height);
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

    public List<Wall> createWalls() {
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

    public void createFruits() {
        for (int i = 0; i < 5; i++)
            addNewFruit();
        return;
    }
    public boolean collectFruit(){
        for(Fruit fruit : fruits){
            if(snake.getPosition().equals(fruit.getPosition())) {
                fruits.remove(fruit);
                return true;
            }
        }
        return false;
    }

    public boolean collectMovingFruit(){
        for(MovingFruit movingFruit : movingFruits){
            if(snake.getPosition().equals(movingFruit.getPosition())) {
                movingFruits.remove(movingFruit);
                return true;
            }
        }
        return false;
    }

    public void moveMultipleElementsRandomly(List<? extends Element> elements) {
        for (Element element : elements) {
            Position new_position = getPositionToMoveElementRandomly(element);
            while (!canElementMoveOrSpawn(new_position)) {
                new_position = getPositionToMoveElementRandomly(element);
            }
            if (!checkPositionNotInTeleporters(new_position)) {
                new_position = getTeleporterDestination(getTeleporter(new_position), element.getPosition());
            }
            element.setPosition(new_position);
        }
    }

    public Position getPositionToMoveElementRandomly(Element element) {
        Position element_position = element.getPosition();
        int direction = new RandomNumberGenerator().randomNextInt(4);
        Position new_position = switch (direction) {
            case 0 -> new Position(element_position.getX(), element_position.getY() - 1);
            case 1 -> new Position(element_position.getX(), element_position.getY() + 1);
            case 2 -> new Position(element_position.getX() - 1, element_position.getY());
            case 3 -> new Position(element_position.getX() + 1, element_position.getY());
            default -> null;
        };
        return new_position;
    }
    public boolean canElementMoveOrSpawn(Position position) {
        return (position.getX() >= 0 && position.getX() <= width)
                && (position.getY() >= 0 && position.getY() <= height)
                && checkPositionNotInSnakeBodyParts(position)
                && checkPositionNotInWalls(position)
                && checkPositionNotInFruits(position)
                && checkPositionNotInMovingFruits(position)
                && checkPositionNotInMonsters(position);
    }

    public boolean checkPositionNotInSnakeBodyParts(Position position) {
        for (SnakeBodyPart snakeBodyPart : snakeBodyParts) {
            if (snakeBodyPart.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkPositionNotInFruits(Position position) {
        if (fruits == null) {
            return true;
        }
        for (Fruit fruit : fruits) {
            if (fruit.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkPositionNotInMovingFruits(Position position) {
        if (movingFruits == null) {
            return true;
        }
        for (MovingFruit movingFruit : movingFruits) {
            if (movingFruit.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkPositionNotInWalls(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    public void verifyMovingFruitCollisions() {
        if (collectMovingFruit()) {
            addNewMovingFruit();
            snakeBodyPartAddBecauseOfMovingFruitGoingAgainstSnake++;
        }
        return;
    }

    public boolean checkPositionNotInTeleporters(Position position) {
        for (Teleporter teleporter : teleporters) {
            if (teleporter.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkPositionNotInMonsters(Position position) {
        if (monsters == null) {
            return true;
        }
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }
}