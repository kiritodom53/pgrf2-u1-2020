package com.mandinec.pgrf2.projekt1.renderer;

import java.util.Arrays;

class ZBuffer<T extends Number> {

    private T[][] data;

    public ZBuffer(T[][] data) {
        this.data = data;
    }

    T get(int x, int y) {
        return data[x][y];
    }

    void set(T zValue, int x, int y) {
        data[x][y] = zValue;
    }

    void clear(T clearValue) {
        for (T[] d : data) {
            Arrays.fill(d, clearValue);
            /*
            for (int i = 0; i < d.length; i++) {
                d[i] = clearValue;
            }
            */
        }
    }

}
