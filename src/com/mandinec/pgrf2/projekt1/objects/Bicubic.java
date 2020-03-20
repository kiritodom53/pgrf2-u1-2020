package com.mandinec.pgrf2.projekt1.objects;

import com.mandinec.pgrf2.projekt1.model.Element;
import com.mandinec.pgrf2.projekt1.model.ElementType;
import com.mandinec.pgrf2.projekt1.model.Vertex;
import transforms.Mat4;
import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;

public class Bicubic extends Solid{
    private transforms.Bicubic bc;
    private Mat4 gridType;

    public Bicubic(Mat4 type){
        gridType = type;
        ib = new ArrayList<>();
        vb = new ArrayList<>();
        elements = new ArrayList<>();

        Point3D[] points;
        points = new Point3D[]{
                new Point3D(1, 1, 0),
                new Point3D(1, 2, 0),
                new Point3D(1, 3, -2),
                new Point3D(1, 4, 0),
                new Point3D(2, 1, 0),
                new Point3D(2, 2, -3),
                new Point3D(2, 3, 0),
                new Point3D(2, 4, 0),
                new Point3D(3, 1, -4),
                new Point3D(3, 2, 0),
                new Point3D(3, 3, 0),
                new Point3D(3, 4, 0),
                new Point3D(4, 1, -5),
                new Point3D(4, 2, 0),
                new Point3D(4, 3, -7),
                new Point3D(4, 4, 0),
        };


        bc = new transforms.Bicubic(gridType, points);
        int counter = 0;

        for (float i = 0; i < 1.0; i += .1) {
            counter++;
            //System.out.println("i : " + i);
            for (float j = 0; j < 1.0; j += .1) { // zhuštění
                //vb.add(new Vertex(bc.compute(i,j), Color.BLUE));
            }
        }

        // IndexBuffer
        System.out.println("ct : " + counter);
        System.out.println("vb : " + vb.size());
        //counter = vb.size();

        int count = 0;

        for (int i = 0; i < vb.size(); i++) {
            for (int j = 0; j < counter; j++) {
                if (j < counter - 1){
                    ib.add((i * counter) + j);
                    ib.add((i * counter) + j + 1);
                    ib.add((i * counter) + j + (int) Math.sqrt(vb.size()));
                    count++;
                }
                if (i < counter - 1){
                    ib.add((i * counter) + j);
                    ib.add(((i + 1) * counter) + j);
                    ib.add(((i + 1 + (int) Math.sqrt(vb.size())) * counter) + j);
                    count++;
                }
            }
        }


        System.out.println("count : " + count);

        elements.add(new Element(ElementType.TRIANGLE, count, 0));



    }
}
