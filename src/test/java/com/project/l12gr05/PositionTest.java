package com.project.l12gr05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PositionTest {
    private Position position;

    @BeforeEach
    public void setUp() { position = new Position(1, 2);}

    @Test
    public void getX() {
        assertEquals(1, position.getX());
    }

    @Test
    public void getY() {
        assertEquals(2, position.getY());
    }

    @Test
    public void equals() {
        Position position_other = new Position(1, 2);
        assertEquals(position, position_other);
        assertEquals(position_other, position);
    }

    @Test
    public void notEquals() {
        Position position_other = new Position(3, 4);
        assertNotEquals(position, position_other);
        assertNotEquals(position_other, position);
    }

    @Test
    public void samePosition() {
        Position position_other = position;
        assertEquals(position, position_other);
        assertEquals(position_other, position);
    }

    @Test
    public void notPositionObject() {
        assertNotEquals(position, "not a position object");
        assertNotEquals("not a position object", position);
    }

    @Test
    public void nullObject() {
        assertNotEquals(position, null);
        assertNotEquals(null, position);
    }
}
