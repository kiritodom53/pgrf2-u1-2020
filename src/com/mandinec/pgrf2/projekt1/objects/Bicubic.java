package com.mandinec.pgrf2.projekt1.objects;

import com.mandinec.pgrf2.projekt1.model.Element;
import com.mandinec.pgrf2.projekt1.model.ElementType;
import com.mandinec.pgrf2.projekt1.model.Vertex;
import transforms.Mat4;
import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;

public class Bicubic extends Solid {
    private transforms.Bicubic bc;
    private Mat4 gridType;

    public Bicubic(Mat4 type) {
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

        for (float i = 0f; i < 1f; i += 0.1) { // zhuštění
            counter++;
            for (float j = 0f; j < 1f; j += 0.1) { // zhuštění
                vb.add(new Vertex(bc.compute(i, j), Color.BLUE));
            }
        }

        int count = 0;

        for (int i = 0; i < vb.size(); i++) {
            if ((i + 1) % counter != 0) {
                if ((i + counter) < vb.size()) {
                    ib.add(i);
                    ib.add(i + 1);
                    ib.add(i + counter);
                    count += 1;
                    if ((i - counter) < vb.size()) {
                        ib.add(i + 1 + counter);
                        ib.add(i + 1);
                        ib.add(i + counter);
                        count += 1;
                    }
                }

            }
        }

        elements.add(new Element(ElementType.TRIANGLE, count, 0));
    }
}
