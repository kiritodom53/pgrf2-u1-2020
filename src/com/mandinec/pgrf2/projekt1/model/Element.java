package com.mandinec.pgrf2.projekt1.model;

import com.mandinec.pgrf2.projekt1.renderer.Shader;

import java.awt.*;

public class Element {

    private final ElementType elementType;
    private final int count;
    private final int start;

    public Element(ElementType elementType, int count, int start) {
        this.elementType = elementType;
        this.count = count;
        this.start = start;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public int getCount() {
        return count;
    }

    public int getStart() {
        return start;
    }
}
