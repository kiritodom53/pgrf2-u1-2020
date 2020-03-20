package com.mandinec.pgrf2.projekt1.objects;

import com.mandinec.pgrf2.projekt1.model.Element;
import com.mandinec.pgrf2.projekt1.model.ElementType;
import com.mandinec.pgrf2.projekt1.model.Vertex;
import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;

public class Xyz extends Solid {
    public Xyz(){
        ib = new ArrayList<>();
        vb = new ArrayList<>();
        elements = new ArrayList<>();

        transferable = false;

        elements.add(new Element(ElementType.LINE, 6, 0));

//        vb.add(new Vertex(new Point3D(-1, -2, 0.5), Color.GREEN));
//        vb.add(new Vertex(new Point3D(-0.5, -2, 0.5), Color.GREEN));
//
//        vb.add(new Vertex(new Point3D(-0.5, -2, 0.5), Color.RED));
//        vb.add(new Vertex(new Point3D(-0.5, -1.5, 0.5), Color.RED));
//
//        vb.add(new Vertex(new Point3D(-0.5, -2, 0.5), Color.BLUE));
//        vb.add(new Vertex(new Point3D(-0.5, -2, 0.9), Color.BLUE));

        ib.add(0);
        ib.add(1);
        ib.add(2);
        ib.add(3);
        ib.add(4);
        ib.add(5);

    }
}
