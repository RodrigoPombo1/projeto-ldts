package com.project.l12gr05;

import java.util.Random;

public class RandomNumberGenerator implements GenericRandomNumberGenerator {
    private Random random;

    public RandomNumberGenerator() {
        this.random = new Random();
    }

    public Integer randomNextInt(int bound) {
        return random.nextInt(bound);
    }
}
