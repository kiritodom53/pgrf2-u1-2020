package com.mandinec.pgrf2.projekt1.raster;

import com.mandinec.pgrf2.projekt1.model.Vertex;
import com.mandinec.pgrf2.projekt1.renderer.Renderer3D;
import com.mandinec.pgrf2.projekt1.view.Raster;
import transforms.Vec3D;

import java.awt.*;
import java.util.Optional;

public class Line extends Renderer3D {
    public Line(Raster raster) {
        super(raster);
    }

    public void draw(Vertex a, Vertex b) {
        if (cut(a.getPoint()) && cut(b.getPoint())){
            Color c1 = a.getColor();
            Color c2 = b.getColor();

            Optional<Vec3D> d1 = a.getPoint().dehomog();
            Optional<Vec3D> d2 = b.getPoint().dehomog();

            // zahodit trojúhelník, pokud některý vrchol má w==0 (nelze provést dehomogenizaci)
            if (!d1.isPresent() || !d2.isPresent()) return;

            Vec3D v1 = d1.get();
            Vec3D v2 = d2.get();



            v1 = transformToWindow(v1);
            v2 = transformToWindow(v2);


            int x1 = (int) v1.getX();
            int y1 = (int) v1.getY();
            int x2 = (int) v2.getX();
            int y2 = (int) v2.getY();
            int d = 0;

            int dx = Math.abs(x2 - x1);
            int dy = Math.abs(y2 - y1);

            int dx2 = 2 * dx;
            int dy2 = 2 * dy;

            int ix = x1 < x2 ? 1 : -1;
            int iy = y1 < y2 ? 1 : -1;

            int x = x1;
            int y = y1;

            if (dx >= dy) {
                while (true) {
                    //plot(g, x, y);
                    double t = (x - v1.getX()) / (v2.getX() - v1.getX());
                    Vertex v = new Vertex(a.getPoint().mul(1 - t).add(b.getPoint().mul(t)), c1);
                    int z = (int)a.getPoint().mul(1 - t).add(b.getPoint().mul(t)).getZ();

                    double t1 = (x - v1.getX()) / (v2.getX() - v1.getX());
                    //Vertex vertexAB = a.mul(1 - t1).add(b.mul(t1));
                    Vertex vertexAB = new Vertex(a.getPoint().mul(1 - t1).add(b.getPoint().mul(t1)), c1);


                    drawPixel(x, y, z, c1);
                    if (x == x2)
                        break;
                    x += ix;
                    d += dy2;
                    if (d > dx) {
                        y += iy;
                        d -= dx2;
                    }
                }
            } else {
                while (true) {
                    double t = (y - v1.getY()) / (v2.getY() - v1.getY());
                    int z = (int)a.getPoint().mul(1 - t).add(b.getPoint().mul(t)).getZ();
                    drawPixel(x, y, z, c1);
                    if (y == y2)
                        break;
                    y += iy;
                    d += dx2;
                    if (d > dy) {
                        x += ix;
                        d -= dy2;
                    }
                }
            }
        }
    }

}
