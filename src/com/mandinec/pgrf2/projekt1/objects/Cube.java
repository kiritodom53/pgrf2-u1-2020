package com.mandinec.pgrf2.projekt1.objects;

import com.mandinec.pgrf2.projekt1.model.Element;
import com.mandinec.pgrf2.projekt1.model.ElementType;
import com.mandinec.pgrf2.projekt1.model.Vertex;
import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;

public class Cube extends Solid{
    public Cube(){
        ib = new ArrayList<>();
        vb = new ArrayList<>();
        elements = new ArrayList<>();

        elements.add(new Element(ElementType.TRIANGLE, 36, 0));


        vb.add(new Vertex(new Point3D(0, 0, 0), Color.CYAN)); //0

        vb.add(new Vertex(new Point3D(1, 0, 0), Color.CYAN));
        vb.add(new Vertex(new Point3D(0, 1, 0), Color.CYAN));
        vb.add(new Vertex(new Point3D(1, 1, 0), Color.CYAN)); // 3

        vb.add(new Vertex(new Point3D(0, 0, 1), Color.CYAN));
        vb.add(new Vertex(new Point3D(0, 1, 1), Color.CYAN)); // 5

        vb.add(new Vertex(new Point3D(1, 0, 1), Color.CYAN));
        vb.add(new Vertex(new Point3D(0, 0, 1), Color.CYAN)); // 7

        vb.add(new Vertex(new Point3D(0, 1, 1), Color.CYAN));
        vb.add(new Vertex(new Point3D(1, 1, 1), Color.CYAN)); // 9

        vb.add(new Vertex(new Point3D(1, 1, 1), Color.CYAN));
        vb.add(new Vertex(new Point3D(1, 0, 1), Color.CYAN)); // 11

        vb.add(new Vertex(new Point3D(0, 1, 1), Color.CYAN));
        vb.add(new Vertex(new Point3D(0, 0, 1), Color.CYAN)); // 13

        ib.add(0);
        ib.add(1);
        ib.add(2);
        ib.add(1);
        ib.add(2);
        ib.add(3);
        ib.add(0);
        ib.add(2);
        ib.add(5);
        ib.add(0);
        ib.add(5);
        ib.add(4);
        ib.add(0);
        ib.add(1);
        ib.add(6);
        ib.add(0);
        ib.add(6);
        ib.add(7);
        ib.add(2);
        ib.add(3);
        ib.add(8);
        ib.add(3);
        ib.add(8);
        ib.add(9);
        ib.add(3);
        ib.add(10);
        ib.add(11);
        ib.add(3);
        ib.add(11);
        ib.add(1);
        ib.add(11);
        ib.add(10);
        ib.add(12);
        ib.add(11);
        ib.add(12);
        ib.add(13);
    }
}
