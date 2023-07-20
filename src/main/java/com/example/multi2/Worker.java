package com.example.multi2;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.Random;

public class Worker {
    int x;
    double intervalSec;
    int iterations;
    SimpleIntegerProperty commonValue;

    public Worker(int x, double intervalSec, SimpleIntegerProperty commonValue) {
        this.x = x;
        this.intervalSec = intervalSec;
        this.commonValue = commonValue;
        Random r = new Random();
        iterations = r.nextInt(1, 2100);
    }

    public void work() throws InterruptedException {
        for (int i = 0; i < iterations; i++) {
            commonValue.set(commonValue.get()+x);
            Thread.sleep((long) (intervalSec*1000));
        }
    }

    @Override
    public String toString() {

        return this.getClass().getSimpleName()+"{" +
                "x=" + x +
                ", intervalSec=" + intervalSec +
                ", iterations=" + iterations +
                ", commonValue=" + commonValue +
                '}';
    }
}
