package com.mandinec.pgrf2.projekt1.raster;

import com.mandinec.pgrf2.projekt1.model.Vertex;
import com.mandinec.pgrf2.projekt1.renderer.Renderer3D;
import com.mandinec.pgrf2.projekt1.view.Raster;
import transforms.Vec3D;

import java.awt.*;
import java.util.Optional;

public class TriangleRas extends Renderer3D {
    public TriangleRas(Raster raster) {
        super(raster);
    }

    public void drawTriangle(Vertex a, Vertex b, Vertex c) {

        if (cut(a.getPoint()) && cut(b.getPoint()) && cut(c.getPoint())) {
            Color c1 = a.getColor();
            Color c2 = b.getColor();
            Color c3 = c.getColor();

            Optional<Vec3D> d1 = a.getPoint().dehomog();
            Optional<Vec3D> d2 = b.getPoint().dehomog();
            Optional<Vec3D> d3 = c.getPoint().dehomog();

            // zahodit trojúhelník, pokud některý vrchol má w==0 (nelze provést dehomogenizaci)
            if (!d1.isPresent() || !d2.isPresent() || !d3.isPresent()) return;

            Vec3D v1 = d1.get();
            Vec3D v2 = d2.get();
            Vec3D v3 = d3.get();


            v1 = transformToWindow(v1);
            v2 = transformToWindow(v2);
            v3 = transformToWindow(v3);
            if (v1.getY() > v2.getY()) {
                Vec3D temp = v1;
                v1 = v2;
                v2 = temp;

                Color tempC = c1;
                c1 = c2;
                c2 = tempC;
            }
            if (v2.getY() > v3.getY()) {
                Vec3D temp = v2;
                v2 = v3;
                v3 = temp;

                Color tempC = c2;
                c2 = c3;
                c3 = tempC;
            }
            if (v1.getY() > v2.getY()) {
                Vec3D temp = v1;
                v1 = v2;
                v2 = temp;

                Color tempC = c1;
                c1 = c2;
                c2 = tempC;
            }

            for (int y = (int) (v1.getY() + 1); y < v2.getY(); y++) {
                double t1 = (y - v1.getY()) / (v2.getY() - v1.getY());
                double t2 = (y - v1.getY()) / (v3.getY() - v1.getY());

                Vec3D vAB = v1.mul(1 - t1).add(v2.mul(t1));
                Vec3D vAC = v1.mul(1 - t2).add(v3.mul(t2));

                if (vAB.getX() > vAC.getX()) {
                    Vec3D temp = vAB;
                    vAB = vAC;
                    vAC = temp;
                }
                this.fillLine(y, vAB, vAC, c1, c3);
            }

            for (int y = (int) (v2.getY() + 1); y < v3.getY(); y++) {
                double t1 = (y - v2.getY()) / (v3.getY() - v2.getY());
                double t2 = (y - v1.getY()) / (v3.getY() - v1.getY());

                Vec3D vBC = v2.mul(1 - t1).add(v3.mul(t1));
                Vec3D vAC = v1.mul(1 - t2).add(v3.mul(t2));

                if (vBC.getX() > vAC.getX()) {
                    Vec3D temp = vBC;
                    vBC = vAC;
                    vAC = temp;
                }
                this.fillLine(y, vBC, vAC, c2, c3);
            }
        }
    }
}
