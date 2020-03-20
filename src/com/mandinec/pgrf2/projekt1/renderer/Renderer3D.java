package com.mandinec.pgrf2.projekt1.renderer;

import com.mandinec.pgrf2.projekt1.model.Element;
import com.mandinec.pgrf2.projekt1.model.ElementType;
import com.mandinec.pgrf2.projekt1.model.Vertex;
import com.mandinec.pgrf2.projekt1.objects.Solid;
import com.mandinec.pgrf2.projekt1.raster.Line;
import com.mandinec.pgrf2.projekt1.view.Raster;
import transforms.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class Renderer3D implements GPURenderer {

    private final Raster raster;
    private Mat4 model, view, projection;
    private Line line; // ehm

    private ZBuffer<Double> zBuffer;

    public Renderer3D(Raster raster) {
        this.raster = raster;

        model = new Mat4Identity();
        view = new Mat4Identity();

        projection = new Mat4PerspRH(Math.PI / 4, Raster.HEIGHT / (float) Raster.WIDTH, 1, 200);
        //projection = new Mat4PerspRH(0.7853981633974483D, Raster.HEIGHT / (float) Raster.WIDTH, 0.01D, 100.0D);
        //projection = new Mat4PerspRH(Math.PI / 4, 1, 0.01, 30);
        //projection = new Mat4OrthoRH(20, 20, 0.1, 200);

        zBuffer = new ZBuffer<>(new Double[Raster.WIDTH][Raster.HEIGHT]);
        zBuffer.clear(20d); // ehm
    }

    @Override
    public void draw(List<Element> elements, List<Vertex> vb, List<Integer> ib) {
        for (Element element : elements) {
            final int start = element.getStart();
            final int count = element.getCount();
            if (element.getElementType() == ElementType.TRIANGLE) {
                for (int i = start; i < count + start; i += 3) {
//                    final Vertex v1 = vb.get(ib.get(i));
                    final Integer i1 = ib.get(i);
                    final Integer i2 = ib.get(i + 1);
                    final Integer i3 = ib.get(i + 2);
                    final Vertex v1 = vb.get(i1);
                    final Vertex v2 = vb.get(i2);
                    final Vertex v3 = vb.get(i3);
//                    System.out.println("nevim");
                    prepareTriangle(v1, v2, v3);
                }
            } else if (element.getElementType() == ElementType.LINE) {
                for (int i = start; i < count + start; i += 2) {
//                    final Vertex v1 = vb.get(ib.get(i));
                    final Integer i1 = ib.get(i);
                    final Integer i2 = ib.get(i + 1);
                    final Vertex v1 = vb.get(i1);
                    final Vertex v2 = vb.get(i2);
//                    System.out.println("nevim");
                    prepareLine(v1, v2);
                }
            } else {
                // Point
            }
        }
    }

    @Override
    public void draw(Solid solid) {
        for (Element element : solid.getElemetns()) {
            final int start = element.getStart();
            final int count = element.getCount();
            if (element.getElementType() == ElementType.TRIANGLE) {
                for (int i = start; i < count + start; i += 3) {
//                    final Vertex v1 = vb.get(ib.get(i));
                    final Integer i1 = solid.getIb().get(i);
                    final Integer i2 = solid.getIb().get(i + 1);
                    final Integer i3 = solid.getIb().get(i + 2);
                    final Vertex v1 = solid.getVb().get(i1);
                    final Vertex v2 = solid.getVb().get(i2);
                    final Vertex v3 = solid.getVb().get(i3);
//                    System.out.println("nevim");
//                    lineRaster = new LineRaster(this); // ehm
//                    rlt = new RendererLineTriangle(raster);
                    line = new Line(raster);
                    prepareTriangle(v1, v2, v3);
                }
            } else if (element.getElementType() == ElementType.LINE) {
                for (int i = start; i < count + start; i += 2) {
//                    final Vertex v1 = vb.get(ib.get(i));
                    final Integer i1 = solid.getIb().get(i);
                    final Integer i2 = solid.getIb().get(i + 1);
                    final Vertex v1 = solid.getVb().get(i1);
                    final Vertex v2 = solid.getVb().get(i2);
//                    System.out.println("nevim");
                    prepareLine(v1, v2);
                }
            } else {
                // Point
            }
        }
    }

    private void prepareLine(Vertex a, Vertex b){
//        a = new Vertex(a.getPoint().mul(model).mul(view).mul(projection), a.getColor());
//        b = new Vertex(b.getPoint().mul(model).mul(view).mul(projection), b.getColor());
        if (-a.w > a.x && -b.w > b.x) return;
        if (a.x > a.w && b.x > b.w) return;
//        System.out.println("1 podminka orezeani ok");
        // TODO y, z - HOTOVO
        if (-a.w > a.y && -b.w > b.y) return;
        if (a.y > a.w && b.y > b.w) return;
        //this.drawLine(a, b);
        System.out.println("Prepare line");
//        a = new Vertex(a.getPoint().mul(model).mul(view).mul(projection), a.getColor());
//        b = new Vertex(b.getPoint().mul(model).mul(view).mul(projection), b.getColor());
//
//        if (-a.w > a.x && -b.w > b.x) return;
//        if (a.x > a.w && b.x > b.w) return;
//        System.out.println("1 podminka orezeani ok");
//        // TODO y, z - HOTOVO
//        if (-a.w > a.y && -b.w > b.y) return;
//        if (a.y > a.w && b.y > b.w) return;
//        System.out.println("2 podminka orezeani ok");
//        if (0 > a.z && 0 > b.z) return;
//        if (a.z > a.w && b.z > b.w) return;
//
//        if (a.z < b.z) {
//            Vertex temp = a;
//            a = b;
//            b = temp;
//        }
//        System.out.println("Prohození ok");
//
//        if (a.z < 0) {
//            System.out.println("1 podminka return");
//            // a.z je největší a je záporné, takže celý trojúhelník není vidět
////            System.out.println("Nejdu kreslit");
//            return;
//        } else if (b.z < 0) {
//            System.out.println("2 podminka drawline");
//            double t = (0 - a.z) / (b.z - a.z);
//            Vertex ab = new Vertex(a.getPoint().mul(1 - t).add(b.getPoint().mul(t)), b.getColor());
//            // nekorektně barva, měla by se také interpolovat; volitelně
//            // lze vytvořit funkci pro ořezání, aby se neopakoval kód
////            System.out.println("Jdu kreslit 1");
//            this.drawLine(a, ab);
//        } else {
//            System.out.println("3 podminka vse v poho a drawline");
////            System.out.println("Jdu kreslit 3");
//            this.drawLine(a, b);
//        }
    }

    // pozor není naše
    private Vertex calculateTriangleCut(Vertex v1, Vertex v2) {
        double t = v1.getPoint().getZ() / (v1.getPoint().getZ() - v2.getPoint().getZ());
        return new Vertex(new Point3D(v1.getPoint().mul(1 - t).add(v2.getPoint().mul(t))), Color.CYAN);
    }

    private void prepareTriangle(Vertex a, Vertex b, Vertex c) {
        // 1. transformace vrcholů
        a = new Vertex(a.getPoint().mul(model).mul(view).mul(projection), a.getColor());
        // TODO b, c - HOTOVO
        b = new Vertex(b.getPoint().mul(model).mul(view).mul(projection), b.getColor());
        c = new Vertex(c.getPoint().mul(model).mul(view).mul(projection), c.getColor());
//        System.out.println("Transformace vrcholů ok");
        // 2. ořezání
        // rychlé ořezání zobrazovacím objemem
        // vyhodí trojúhelníky, které jsou CELÉ mimo zobrazovací objem

        // Celý mimo zobrazovací objem

        if (-a.w > a.x && -b.w > b.x && -c.w > c.x) return;
        if (a.x > a.w && b.x > b.w & c.x > c.w) return;
//        System.out.println("1 podminka orezeani ok");
        // TODO y, z - HOTOVO
        if (-a.w > a.y && -b.w > b.y && -c.w > c.y) return;
        if (a.y > a.w && b.y > b.w && c.y > c.w) return;
//        System.out.println("2 podminka orezeani ok");
        if (0 > a.z && 0 > b.z && 0 > c.z) return;
        if (a.z > a.w && b.z > b.w && c.z > c.w) return;

        System.out.println("3 podminka orezeani ok");
        // 3. seřazení vrcholů podle souřadnice Z

        if (a.z < b.z) {
            Vertex temp = a;
            a = b;
            b = temp;
        }
        if (b.z < c.z) {
            Vertex temp = b;
            b = c;
            c = temp;
        }
        if (a.z < b.z) {
            Vertex temp = a;
            a = b;
            b = temp;
        }

//        System.out.println("seřazení ok");


        // 4. ořezání a interpolace podle hrany Z
        if (a.z < 0) {
            // a.z je největší a je záporné, takže celý trojúhelník není vidět
//            System.out.println("Nejdu kreslit");
            return;
        } else if (b.z < 0) {

            double t = a.getPoint().getZ() / (a.getPoint().getZ() - b.getPoint().getZ());
            Vertex ab = new Vertex(a.getPoint().mul(1 - t).add(b.getPoint().mul(t)), b.getColor());
            // nekorektně barva, měla by se také interpolovat; volitelně

            double t2 = a.getPoint().getZ() / (a.getPoint().getZ() - c.getPoint().getZ());
            Vertex ac = new Vertex(a.getPoint().mul(1 - t2).add(c.getPoint().mul(t2)), c.getColor());

//            Vertex ab = calculateTriangleCut(a, b);
//            Vertex ac = calculateTriangleCut(a, c);

            // lze vytvořit funkci pro ořezání, aby se neopakoval kód
            System.out.println("Jdu kreslit 1");
            //drawTriangle(a, ab, ac);
            line.draw(a, ab);
            line.draw(ab, ac);
            line.draw(ac, a);
        } else if (c.z < 0) {
            // TODO ac, bc - HOTOVO
            double t = b.getPoint().getZ() / (b.getPoint().getZ() - c.getPoint().getZ());
            Vertex bc = new Vertex(b.getPoint().mul(1 - t).add(c.getPoint().mul(t)), c.getColor());

            double t2 = a.getPoint().getZ() / (a.getPoint().getZ() - c.getPoint().getZ());
            Vertex ac = new Vertex(a.getPoint().mul(1 - t2).add(c.getPoint().mul(t2)), c.getColor());

//            Vertex ac = calculateTriangleCut(a, c);
//            Vertex bc = calculateTriangleCut(b, c);


            System.out.println("Jdu kreslit 2");
//            drawTriangle(a, b, ac);
//            drawTriangle(b, ac, bc);

            line.draw(a, b);
            line.draw(b, ac);
            line.draw(a, ac);

            line.draw(b, ac);
            line.draw(ac, bc);
            line.draw(bc, b);

        } else {
            System.out.println("Jdu kreslit 3");
            System.out.println("a.z : " + a.z);
            System.out.println("b.z : " + b.z);
            System.out.println("c.z : " + c.z);
//            drawTriangle(a, b, c);
            line.draw(a, b);
            line.draw(a, c);
            line.draw(b, c);
        }
    }

//    public void drawLineTriangle(Vertex a, Vertex b){
////        a = a.dehomog();
////        b = b.dehomog();
////
////        Vec3D va = RasterizerUtil.viewportTransformation(a.getPosition().ignoreW(), visibilitityBuffer.getWidth(), visibilitityBuffer.getHeight());
////        Vec3D vb = RasterizerUtil.viewportTransformation(b.getPosition().ignoreW(), visibilitityBuffer.getWidth(), visibilitityBuffer.getHeight());
//
//        Color c1 = a.getColor();
//        Color c2 = b.getColor();
//
//        Optional<Vec3D> d1 = a.getPoint().dehomog();
//        Optional<Vec3D> d2 = b.getPoint().dehomog();
//
//        // zahodit trojúhelník, pokud některý vrchol má w==0 (nelze provést dehomogenizaci)
//        if (!d1.isPresent() || !d2.isPresent()) return;
//
//        Vec3D v1 = d1.get();
//        Vec3D v2 = d2.get();
//
//        v1 = transformToWindow(v1);
//        v2 = transformToWindow(v2);
//
//
//        int x1 = (int) v1.getX();
//        int y1 = (int) v1.getY();
//
//        int x2 = (int) v2.getX();
//        int y2 = (int) v2.getY();
//
//        int d = 0;
//
//        int dx = Math.abs(x2 - x1);
//        int dy = Math.abs(y2 - y1);
//
//        int dx2 = 2 * dx;
//        int dy2 = 2 * dy;
//
//        int ix = x1 < x2 ? 1 : -1;
//        int iy = y1 < y2 ? 1 : -1;
//
//        int x = x1;
//        int y = y1;
//
//        if (dx >= dy) {
//            while (true) {
//                double t1 = (x - v1.getX()) / (v2.getX() - v1.getX());
//                Vertex vertexAB = new Vertex(new Point3D(a.getPoint().mul(1 - t1).add(b.getPoint().mul(t1))), c1);
//                drawPixel(x, y, vertexAB.getPoint().getZ(), vertexAB.getColor());
//                if (x == x2)
//                    break;
//                x += ix;
//                d += dy2;
//                if (d > dx) {
//                    y += iy;
//                    d -= dx2;
//                }
//            }
//        } else {
//            while (true) {
//                double t1 = (y - v1.getY()) / (v2.getY() - v1.getY());
//                Vertex vertexAB = new Vertex(new Point3D(a.getPoint().mul(1 - t1).add(b.getPoint().mul(t1))), c1);
//                drawPixel(x, y, vertexAB.getPoint().getZ(), vertexAB.getColor());
//                if (y == y2)
//                    break;
//                y += iy;
//                d += dx2;
//                if (d > dy) {
//                    x += ix;
//                    d -= dy2;
//                }
//            }
//        }
//    }

    private Boolean orez(Point3D point){
        return -point.getW() <= point.getY() &&
                -point.getW() <= point.getX() &&
                point.getY() <= point.getW() &&
                point.getX() <= point.getW() &&
                point.getZ() >= 0 &&
                point.getZ() <= point.getW();
    }


    //public void drawLineDDA(int x1, int y1, int x2, int y2, int c) {

//    public void drawLine(Vertex a, Vertex b) {
//        Color c1 = a.getColor();
//
////        a = a.dehomog();
////        b = b.dehomog();
//
//        Optional<Vec3D> d1 = a.getPoint().dehomog();
//        Optional<Vec3D> d2 = b.getPoint().dehomog();
//
//        Vec3D v1 = d1.get();
//        Vec3D v2 = d2.get();
//
//
//        v1 = transformToWindow(v1);
//        v2 = transformToWindow(v2);
//
////        Vec3D va = RasterizerUtil.viewportTransformation(a.getPosition().ignoreW(), visibilitityBuffer.getWidth(), visibilitityBuffer.getHeight());
////        Vec3D vb = RasterizerUtil.viewportTransformation(b.getPosition().ignoreW(), visibilitityBuffer.getWidth(), visibilitityBuffer.getHeight());
//
////        v1.getX();
////        v2.getX();
////        v1.getY();
////        v2.getY();
//
//        int x, x1 = (int)v1.getX(), x2 = (int)v2.getX(), y1 = (int)v1.getY(), y2 = (int)v2.getY();
//        float k;
//        float y;
//        if (Math.abs(v2.getY() - v1.getY()) < Math.abs(v2.getX() - v1.getX())) {
//            if (v2.getX() < v1.getX()) {
//                x = x1;
//                x1 = x2;
//                x2 = x;
//                x = y1;
//                y1 = y2;
//                y2 = x;
//            }
//
//            k = (float) (y2 - y1) / (float) (x2 - x1);
//            y = (float) y1;
//
//            for (x = x1; x <= x2; ++x) {
//                double t1 = (x - v1.getX()) / (v2.getX() - v1.getX());
//                Vertex vertexAB = new Vertex(a.getPoint().mul(1 - t1).add(b.getPoint().mul(t1)), c1);
//                //raster.drawPixel(x, (int) ((double) y + 0.5D));
//                double z = a.z * (1 - t1) + b.z * t1;
//                System.out.println("z: " + z);
//                drawPixel(x, (int) ((double) y + 0.5D), z, c1);
//                y += k;
//            }
//        } else {
//            if (v2.getY() < v1.getY()) {
//                x = x1;
//                x1 = x2;
//                x2 = x;
//                x = y1;
//                y1 = y2;
//                y2 = x;
//            }
//
//            k = (float) (x2 - x1) / (float) (y2 - y1);
//            y = (float) x1;
//
//            for (x = y1; x <= y2; ++x) {
//                double t1 = (y - v1.getY()) / (v2.getY() - v1.getY());
//                Vertex vertexAB = new Vertex(a.getPoint().mul(1 - t1).add(b.getPoint().mul(t1)), c1);
//                double z = a.z * (1 - t1) + b.z * t1;
//
//                //raster.drawPixel((int) ((double) y + 0.5D), x);
//                drawPixel((int) ((double) y + 0.5D), x, z, c1);
//                y += k;
//            }
//        }
//
//
//
//
//
//
//
//
//
////        int x1 = (int) v1.getX();
////        int y1 = (int) v1.getY();
////
////        int x2 = (int) v2.getX();
////        int y2 = (int) v2.getY();
////
////        int d = 0;
////
////        int dx = Math.abs(x2 - x1);
////        int dy = Math.abs(y2 - y1);
////
////        int dx2 = 2 * dx;
////        int dy2 = 2 * dy;
////
////        int ix = x1 < x2 ? 1 : -1;
////        int iy = y1 < y2 ? 1 : -1;
////
////        int x = x1;
////        int y = y1;
////
////        if (dx >= dy) {
////            while (true) {
////                double t1 = (x - v1.getX()) / (v2.getX() - v1.getX());
////                Vertex vertexAB = new Vertex(a.getPoint().mul(1 - t1).add(b.getPoint().mul(t1)), c1);
////                drawPixel(x, y, vertexAB.getPoint().getZ(), vertexAB.getColor());
////                if (x == x2)
////                    break;
////                x += ix;
////                d += dy2;
////                if (d > dx) {
////                    y += iy;
////                    d -= dx2;
////                }
////            }
////        } else {
////            while (true) {
////                double t1 = (y - v1.getY()) / (v2.getY() - v1.getY());
////                Vertex vertexAB = new Vertex(a.getPoint().mul(1 - t1).add(b.getPoint().mul(t1)), c1);
////                drawPixel(x, y, vertexAB.getPoint().getZ(), vertexAB.getColor());
////                if (y == y2)
////                    break;
////                y += iy;
////                d += dx2;
////                if (d > dy) {
////                    x += ix;
////                    d -= dy2;
////                }
////            }
////        }
//    }

//    private void drawLine(Vertex p1, Vertex p2) {
//        if (orez(p1.getPoint()) && orez(p2.getPoint())) {
//            lineRaster.drawLine(p1, p2);
//        }
//    }



    private void Line(int y, Vec3D a, Vec3D b, Color cA, Color cB){
        if (a.getX() > b.getX()) {
            Vec3D temp = a;
            a = b;
            b = temp;

            Color tempC = cA;
            cA = cB;
            cB = tempC;
        }

        double t = ((int)a.getX() - a.getX()) / (b.getX() - a.getX());
        double z = a.getZ() * (1 - t) + b.getZ() * t;
        drawPixel((int)a.getX(), y, a.getZ(), cA);
        drawPixel((int)b.getX(), y, b.getZ(), cA);
    }

    private void fillLine(int y, Vec3D a, Vec3D b, Color cA, Color cB) {
//        System.out.println("start fillLine");
        if (a.getX() > b.getX()) {
            Vec3D temp = a;
            a = b;
            b = temp;

            Color tempC = cA;
            cA = cB;
            cB = tempC;
        }

        for (int x = (int) Math.round(a.getX()); x < b.getX(); x++) {
            double t = (x - a.getX()) / (b.getX() - a.getX());
            double z = a.getZ() * (1 - t) + b.getZ() * t;
//            System.out.println("drawPixel");
            drawPixel(x-1, y-1, z, cA);
        }
    }

    // ehm private -> public
    public void drawPixel(int x, int y, double z, Color color) {
//        System.out.println("Renderer3D - start drawPixel");
//        System.out.println("z je : " + z);
//        System.out.println("zBuffer je : " + zBuffer.get(x, y));
        if (z < zBuffer.get(x, y)) {
//            System.out.println("Podmínka Renderer3d drawPixel");
            zBuffer.set(z, x, y);
            raster.drawPixel(x, y, color.getRGB());
        }
    }

    protected Vec3D transformToWindow(Vec3D v) {
        return v.mul(new Vec3D(1, -1, 1)) // Y jde nahoru, chceme dolu
                .add(new Vec3D(1, 1, 0)) // (0,0) je uprostřed, chceme v rohu
                // máme <0, 2> -> vynásobíme polovinou velikosti plátna
                .mul(new Vec3D(Raster.WIDTH / 2f, Raster.HEIGHT / 2f, 1));
    }

    @Override
    public void clear() {
        raster.clear();
        zBuffer.clear(1d);
    }

    @Override
    public Mat4 getModel() {
        return model;
    }

    @Override
    public void setModel(Mat4 model) {
        this.model = model;
    }

    @Override
    public Mat4 getView() {
        return view;
    }

    @Override
    public void setView(Mat4 view) {
        this.view = view;
    }

    @Override
    public Mat4 getProjection() {
        return projection;
    }

    @Override
    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }

    @Override
    public void setShader(Shader<Vertex, Color> shader) {

    }

}

