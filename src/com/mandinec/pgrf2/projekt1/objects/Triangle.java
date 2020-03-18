package com.mandinec.pgrf2.projekt1.objects;

import com.mandinec.pgrf2.projekt1.model.Element;
import com.mandinec.pgrf2.projekt1.model.ElementType;
import com.mandinec.pgrf2.projekt1.model.Vertex;
import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;

public class Triangle extends Solid {
    public Triangle(){
        ib = new ArrayList<>();
        vb = new ArrayList<>();
        elements = new ArrayList<>();

        elements.add(new Element(ElementType.TRIANGLE, 12, 0));

        vb.add(new Vertex(new Point3D(-1, -1, 0), Color.CYAN));
        vb.add(new Vertex(new Point3D(-1, 0, 0), Color.GREEN));
        vb.add(new Vertex(new Point3D(0, -1, 0), Color.RED));
        vb.add(new Vertex(new Point3D(-1, -1, 1), Color.ORANGE));

        ib.add(0);
        ib.add(1);
        ib.add(2);

        ib.add(2);
        ib.add(1);
        ib.add(3);

        ib.add(3);
        ib.add(0);
        ib.add(1);

        ib.add(0);
        ib.add(2);
        ib.add(3);

//        elements.add(new Element(ElementType.TRIANGLE, 3, 0));
//        elements.add(new Element(ElementType.TRIANGLE, 3, 3));
//        elements.add(new Element(ElementType.TRIANGLE, 3, 6));
//        //elements.add(new Element(ElementType.TRIANGLE, 3, 9));
//
//        vb.add(new Vertex(new Point3D(0, -2, 0.9), Color.CYAN));
//        vb.add(new Vertex(new Point3D(1, -2, 0.9), Color.CYAN));
//        vb.add(new Vertex(new Point3D(0, -3, 0.9), Color.CYAN));
//
//        vb.add(new Vertex(new Point3D(0, -2, 0.9), Color.RED));
//        vb.add(new Vertex(new Point3D(1, -2, 0.9), Color.RED));
//        vb.add(new Vertex(new Point3D(1, -3, 0.2), Color.RED));
//
//        vb.add(new Vertex(new Point3D(0, -3, 0.5), Color.GREEN));
//        vb.add(new Vertex(new Point3D(1, -3, 0.2), Color.GREEN));
//        vb.add(new Vertex(new Point3D(1, -2, 0.5), Color.GREEN));
//
////        vb.add(new Vertex(new Point3D(0, -2, 0.9), Color.ORANGE));
////        vb.add(new Vertex(new Point3D(1, -2, 0.9), Color.ORANGE));
////        vb.add(new Vertex(new Point3D(0, -3, 0.9), Color.ORANGE));
//
//        ib.add(0);
//        ib.add(1);
//        ib.add(2);
//        ib.add(3);
//        ib.add(4);
//        ib.add(5);
//        ib.add(6);
//        ib.add(7);
//        ib.add(8);
//        ib.add(9);
//        ib.add(10);
//        ib.add(11);
    }
}
