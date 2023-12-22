package com.project.l12gr05;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MonsterTest {
    Monster monster;

    @BeforeEach
    public void setUp() {
        monster = new Monster(1,2);
    }

    @Test
    public void MonsterPosition() {
        assertEquals(1, monster.getPosition().getX());
        assertEquals(2, monster.getPosition().getY());
    }

    @Test
    public void MonsterPositionChange() {
        monster.setPosition(new Position(3,4));
        assertEquals(3, monster.getPosition().getX());
        assertEquals(4, monster.getPosition().getY());
    }

    @Test
    public void MonsterDraw() {
        TextGraphics screen = Mockito.mock(TextGraphics.class);
        monster.draw(screen);
        Mockito.verify(screen, Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#000000"));
        Mockito.verify(screen, Mockito.times(1)).enableModifiers(SGR.BOLD);
        Mockito.verify(screen, Mockito.times(1)).putString(new TerminalPosition(1, 2), "M");
    }

    @Test
    public void MonsterEquals() {
        Monster monster2 = new Monster(1,2);
        assertEquals(monster, monster2);
    }

    @Test
    public void MonsterNotEquals() {
        Monster monster2 = new Monster(3,4);
        assertNotEquals(monster,monster2);
    }

    @Test
    public void MonsterEqualsItself() {
        assertEquals(monster, monster);
    }

    @Test
    public void MonsterNotEqualsNonMonsterObject() {
        assertNotEquals(monster, "not a monster");
    }

    @Test
    public void MonsterNotEqualsNull() {
        assertNotEquals(monster, null);
    }
}
