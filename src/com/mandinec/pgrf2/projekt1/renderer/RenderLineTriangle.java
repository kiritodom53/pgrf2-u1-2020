package com.mandinec.pgrf2.projekt1.renderer;

import com.mandinec.pgrf2.projekt1.model.Vertex;
import com.mandinec.pgrf2.projekt1.view.Raster;
import transforms.Col;
import transforms.Vec3D;

import java.awt.*;
import java.util.Optional;



public class RenderLineTriangle extends Renderer3D {


    public RenderLineTriangle(Raster raster) {
        super(raster);
    }

    public void prepareTriangleWithnoutFill(Vertex v1, Vertex v2, Vertex v3) {
        drawLine(v1,v2);
        drawLine(v2,v3);
        drawLine(v3,v1);
    }

    private void drawLine(Vertex a, Vertex b) {

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


        cyklus(v1,v2 ,c1);

      /*  int x1 = (int) Math.round(v1.getX());
        int y1 = (int) Math.round(v1.getY());

        int x2 = (int) Math.round(v2.getX());
        int y2 = (int) Math.round(v2.getY());

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
                double t1 = (x - v1.getX()) / (v2.getX() - v1.getX());
                Vec3D vertexAB = d1.get().mul(1 - t1).add(d2.get().mul(t1));

                drawPixel(x, y, vertexAB.getZ(), c1);
                if (x == x2)
                    break;
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= 2 * dx;
                }
            }
        } else {
            while (true) {
                double t1 = (y - v1.getY()) / (v2.getY() - v1.getY());
                Vec3D vertexAB = d1.get().mul(1 - t1).add(d2.get().mul(t1));

                drawPixel(x, y, vertexAB.getZ(), c1);
                if (y == y2)
                    break;
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= 2 * dy;
                }
            }
        }*/



    }

    private void cyklus(Vec3D v1, Vec3D v2, Color c1){

    int dx , dy;
    boolean ridiciX;
    float x, y,k ,G, H;
        int x1 = (int) Math.round(v1.getX());
        int y1 = (int) Math.round(v1.getY());

        int x2 = (int) Math.round(v2.getX());
        int y2 = (int) Math.round(v2.getY());
    dx =x2-x1;
    dy = y2-y1;
        k = dy / (float) dx;

        if (Math.abs(dx) > Math.abs(dy)) {
            ridiciX = true;
            G = 1;
            H = k;
            if (x1 >= x2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
        } else {
            ridiciX = false;
            G = 1 / k;
            H = 1;

            if (y1 > y2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
        }

        x = x1;
        y = y1;
        int max = Math.max(Math.abs(dx), Math.abs(dy));
        System.out.println("max - " +   max);


        for (int i = 0; i <= max; i++) {
            if (ridiciX){
                double t1 = (x - x1) / (x2 - x1);
                Vec3D vertexAB = v1.mul(1 - t1).add(v2.mul(t1));
                drawPixel(Math.round(x), Math.round(y), vertexAB.getZ(), c1);
            }else {
                double t1 = (y - v1.getY()) / (v2.getY() - v1.getY());
                Vec3D vertexAB = v1.mul(1 - t1).add(v2.mul(t1));
                drawPixel(Math.round(x), Math.round(y), vertexAB.getZ(), c1);
            }

            x = x + G;
            y = y + H;
        }
        double t1 = (x - v1.getX()) / (v2.getX() - v1.getX());
        Vec3D vertexAB = v1.mul(1 - t1).add(v2.mul(t1));
        drawPixel(Math.round(x), Math.round(y), vertexAB.getZ(), c1);
    }
       /* int x1 = (int) Math.round(v1.getX());
        int y1 = (int) Math.round(v1.getY());

        int x2 = (int) Math.round(v2.getX());
        int y2 = (int) Math.round(v2.getY());

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



            for (int xN = x; xN <= x2; xN = xN + ix){
                double t1 = (x - v1.getX()) / (v2.getX() - v1.getX());
                Vec3D vertexAB = v1.mul(1 - t1).add(v2.mul(t1));
                drawPixel(x, y, vertexAB.getZ(), c1);

                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= 2 * dx;
                }
            }
            double t1 = (x - v1.getX()) / (v2.getX() - v1.getX());
            Vec3D vertexAB = v1.mul(1 - t1).add(v2.mul(t1));
            drawPixel(x, y, vertexAB.getZ(), c1);
        } else {
            for (int yN = y1; yN <= y2; yN = yN + ix){
                double t1 = (y - v1.getY()) / (v2.getY() - v1.getY());
                Vec3D vertexAB = v1.mul(1 - t1).add(v2.mul(t1));

                drawPixel(x, y, vertexAB.getZ(), c1);

                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= 2 * dy;
                }
            }
            double t1 = (y - v1.getY()) / (v2.getY() - v1.getY());
            Vec3D vertexAB = v1.mul(1 - t1).add(v2.mul(t1));

            drawPixel(x, y, vertexAB.getZ(), c1);

        }
    }*/

}
