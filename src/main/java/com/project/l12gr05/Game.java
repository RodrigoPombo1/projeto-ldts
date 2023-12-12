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

    public void run() {
        try {
            while(true) {
                draw();
                KeyStroke key = screen.readInput();
                processKey(key);
                if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'e') screen.close();
                if (key.getKeyType() == KeyType.EOF) break;
                if(arena.verifyWallCollisions()){
                    screen.close();
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}