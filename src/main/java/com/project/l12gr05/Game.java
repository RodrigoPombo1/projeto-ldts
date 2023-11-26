package com.project.l12gr05;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
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

    private void processKey(KeyStroke key) {
        if (key.getKeyType() == KeyType.ArrowUp) arena.moveSnake(arena.moveUp());
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'w') arena.moveSnake(arena.moveUp());
        if (key.getKeyType() == KeyType.ArrowDown) arena.moveSnake(arena.moveDown());
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 's') arena.moveSnake(arena.moveDown());
        if (key.getKeyType() == KeyType.ArrowRight) arena.moveSnake(arena.moveRight());
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'd') arena.moveSnake(arena.moveRight());
        if (key.getKeyType() == KeyType.ArrowLeft) arena.moveSnake(arena.moveLeft());
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'a') arena.moveSnake(arena.moveLeft());
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

    public void run() {
        try {
            while(true) {
                draw();
                KeyStroke key = screen.readInput();
                processKey(key);
                if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'e') screen.close();
                if (key.getKeyType() == KeyType.EOF) break;
                if(arena.verifyCollisions()){
                    screen.close();
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}