package com.example.cluedo_seii;

import java.io.Serializable;

public class MyPoint implements Serializable {
    public int x;
    public int y;

    private MyPoint() {

    }

    public MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
