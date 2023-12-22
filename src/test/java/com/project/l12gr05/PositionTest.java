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
    public void PositionEqualsPosition() {
        Position position_other = new Position(1, 2);
        assertEquals(position, position_other);
        assertEquals(position_other, position);
    }

    @Test
    public void PositionNotEqualsPosition() {
        Position position_other = new Position(3, 4);
        assertNotEquals(position, position_other);
        assertNotEquals(position_other, position);
    }

    @Test
    public void PositionEqualsItself() {
        Position position_other = position;
        assertEquals(position, position_other);
        assertEquals(position_other, position);
    }

    @Test
    public void PositionNotEqualsNonPositionObject() {
        assertNotEquals(position, "not a position object");
    }

    @Test
    public void PositionNotEqualsNull() {
        assertNotEquals(position, null);
    }
}
