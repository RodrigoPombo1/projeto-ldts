package com.project.l12gr05;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    private Screen screen;
    private Arena arena;

    enum lastMove {UP, DOWN, LEFT, RIGHT}
    lastMove lastMoveInput = lastMove.LEFT;

    private void processKey(KeyStroke key) {
        KeyType key_keytype = key.getKeyType();
        if (key_keytype == KeyType.Character) {
            switch(key.getCharacter()) {
                case 'w' -> arena.moveSnake(arena.moveUp());
                case 's' -> arena.moveSnake(arena.moveDown());
                case 'd' -> arena.moveSnake(arena.moveRight());
                case 'a' -> arena.moveSnake(arena.moveLeft());
            }
        }
        else {
            switch (key_keytype) {
                case ArrowUp -> arena.moveSnake(arena.moveUp());
                case ArrowDown -> arena.moveSnake(arena.moveDown());
                case ArrowRight -> arena.moveSnake(arena.moveRight());
                case ArrowLeft -> arena.moveSnake(arena.moveLeft());
            }
        }
    }


    public Game(){
        try {
            TerminalSize terminalSize = new TerminalSize(40, 20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();
        } catch (IOException e) {
            e.printStackTrace();
        }

        arena = new Arena(40, 20);
    }

    private void draw() throws IOException{
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }

    private boolean checkInput(KeyStroke key) {
        boolean validMove = false;
        switch (key.getKeyType()) {
            case Character -> {
                switch(key.getCharacter()) {
                    case 'w' -> {
                        if (lastMoveInput != lastMove.DOWN) {
                            lastMoveInput = lastMove.UP;
                            validMove = true;
                        }
                    }
                    case 's' -> {
                        if (lastMoveInput != lastMove.UP) {
                            lastMoveInput = lastMove.DOWN;
                            validMove = true;
                        }
                    }
                    case 'a' -> {
                        if (lastMoveInput != lastMove.RIGHT) {
                            lastMoveInput = lastMove.LEFT;
                            validMove = true;
                        }
                    }
                    case 'd' -> {
                        if (lastMoveInput != lastMove.LEFT) {
                            lastMoveInput = lastMove.RIGHT;
                            validMove = true;
                        }
                    }
                    case 'e' -> {
                        validMove = true;
                    }
                    default -> {
                        validMove = false;
                    }

                }
            }
            case ArrowUp -> {
                if (lastMoveInput != lastMove.DOWN) {
                    lastMoveInput = lastMove.UP;
                    validMove = true;
                }
            }
            case ArrowDown -> {
                if (lastMoveInput != lastMove.UP) {
                    lastMoveInput = lastMove.DOWN;
                    validMove = true;
                }
            }
            case ArrowLeft -> {
                if (lastMoveInput != lastMove.RIGHT) {
                    lastMoveInput = lastMove.LEFT;
                    validMove = true;
                }
            }
            case ArrowRight -> {
                if (lastMoveInput != lastMove.LEFT) {
                    lastMoveInput = lastMove.RIGHT;
                    validMove = true;
                }
            }
            case EOF -> {
                validMove = true;
            }
            default -> {
                validMove = false;
            }
        }
        return validMove;
    }

    public void run() {
        try {
            while(true) {
                draw();
                boolean validMove = false;
                KeyStroke key = null;
                while (!validMove) {
                    key = screen.readInput();
                    validMove = checkInput(key);
                }
                processKey(key);
                // verify collisions after movement
                if(arena.verifyWallCollisions()) {
                    screen.close();
                    break;
                }
                if(arena.verifyMonsterCollisions()) {
                    screen.close();
                    break;
                }
                if(arena.verifySnakeBodyCollisions()) {
                    screen.close();
                    break;
                }

                if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'e') screen.close();
                if (key.getKeyType() == KeyType.EOF) break;

                arena.moveMonsters();
                if(arena.verifyMonsterCollisions()){
                    screen.close();
                    break;
                }

                arena.moveMovingFruits();
                arena.verifyMovingFruitCollisions();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}