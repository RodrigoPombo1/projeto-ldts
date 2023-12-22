package com.project.l12gr05;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TeleporterTest {

    Teleporter teleporter;

    @BeforeEach
    public void setup() {
        teleporter = new Teleporter(1, 2);
    }

    @Test
    public void TeleporterPosition() {
        assertEquals(1, teleporter.getPosition().getX());
        assertEquals(2, teleporter.getPosition().getY());
    }

    @Test
    public void TeleporterPositionChange() {
        teleporter.setPosition(new Position(3,4));
        assertEquals(3, teleporter.getPosition().getX());
        assertEquals(4, teleporter.getPosition().getY());
    }

    @Test
    public void TeleporterDraw() {
        TextGraphics screen = Mockito.mock(TextGraphics.class);
        teleporter.draw(screen);
        Mockito.verify(screen, Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#000000"));
        Mockito.verify(screen, Mockito.times(1)).enableModifiers(SGR.BOLD);
        Mockito.verify(screen, Mockito.times(1)).putString(new TerminalPosition(1, 2), "T");
    }

    @Test
    public void TeleporterEquals() {
        Teleporter teleporter2 = new Teleporter(1, 2);
        assertEquals(teleporter, teleporter2);
    }

    @Test
    public void TeleporterNotEquals() {
        Teleporter teleporter2 = new Teleporter(3, 4);
        assertNotEquals(teleporter, teleporter2);
    }

    @Test
    public void TeleporterEqualsItself() {
        assertEquals(teleporter, teleporter);
    }

    @Test
    public void TeleporterNotEqualsNonTeleporterOject() {
        assertNotEquals(teleporter, "not a teleporter");
    }

    @Test
    public void TeleporterNotEqualsNull() {
        assertNotEquals(teleporter, null);
    }
}
