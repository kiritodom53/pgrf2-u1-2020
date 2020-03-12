package com.mandinec.pgrf2.projekt1.renderer;

import com.mandinec.pgrf2.projekt1.model.Element;
import com.mandinec.pgrf2.projekt1.model.ElementType;
import com.mandinec.pgrf2.projekt1.model.Vertex;
import com.mandinec.pgrf2.projekt1.view.Raster;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Mat4PerspRH;
import transforms.Vec3D;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class Renderer3D implements GPURenderer {

    private final Raster raster;
    private Mat4 model, view, projection;

    private ZBuffer<Double> zBuffer;

    public Renderer3D(Raster raster) {
        this.raster = raster;

        model = new Mat4Identity();
        view = new Mat4Identity();

        projection = new Mat4PerspRH(Math.PI / 4, Raster.HEIGHT / (float) Raster.WIDTH, 1, 200);

        zBuffer = new ZBuffer<>(new Double[Raster.WIDTH][Raster.HEIGHT]);
        zBuffer.clear(1d);

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
                    System.out.println("nevim");
                    prepareTriangle(v1, v2, v3);
                }
            } else if (element.getElementType() == ElementType.LINE) {

            } else {
                // Point
            }
        }
    }

    private void prepareTriangle(Vertex a, Vertex b, Vertex c) {
        // 1. transformace vrcholů
        System.out.println("debil");
        System.out.println(a.x);
        System.out.println(b.x);
        System.out.println(c.x);
        System.out.println();
        System.out.println(a.y);
        System.out.println(b.y);
        System.out.println(c.y);
        System.out.println();
        System.out.println(a.z);
        System.out.println(b.z);
        System.out.println(c.z);
        a = new Vertex(a.getPoint().mul(model).mul(view).mul(projection), a.getColor());
        // TODO b, c - HOTOVO
        b = new Vertex(b.getPoint().mul(model).mul(view).mul(projection), b.getColor());
        c = new Vertex(c.getPoint().mul(model).mul(view).mul(projection), c.getColor());
        System.out.println("Transformace vrcholů ok");
        // 2. ořezání
        // rychlé ořezání zobrazovacím objemem
        // vyhodí trojúhelníky, které jsou CELÉ mimo zobrazovací objem
        if (-a.w > a.x && -b.w > b.x && -c.w > c.x) return;
        if (a.x > a.w && b.x > b.w && c.x > c.w) return;
        System.out.println("1 podminka orezeani ok");
        // TODO y, z - HOTOVO
        if (-a.w > a.y && -b.w > b.y && -c.w > c.y) return;
        if (a.y > a.w && b.y > b.w && c.y > c.w) return;
        System.out.println("2 podminka orezeani ok");
        if (0 > a.z && 0 > b.z && 0 > c.z) return;
        if (a.z > a.w && b.z > b.w && c.z > c.w) return;
        System.out.println("3 podminka orezeani ok");
        // 3. seřazení vrcholů podle souřadnice Z
        System.out.println("kurva");
        System.out.println(a.x);
        System.out.println(b.x);
        System.out.println(c.x);
        System.out.println();
        System.out.println(a.y);
        System.out.println(b.y);
        System.out.println(c.y);
        System.out.println();
        System.out.println(a.z);
        System.out.println(b.z);
        System.out.println(c.z);
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
        System.out.println("seřazení ok");

        // 4. ořezání a interpolace podle hrany Z
        if (a.z < 0) {
            // a.z je největší a je záporné, takže celý trojúhelník není vidět
            System.out.println("Nejdu kreslit");
            return;
        } else if (b.z < 0) {
            double t = (0 - a.z) / (b.z - a.z);
            Vertex ab = new Vertex(a.getPoint().mul(1 - t).add(b.getPoint().mul(t)), b.getColor());
            // nekorektně barva, měla by se také interpolovat; volitelně

            double t2 = -a.z / (c.z - a.z);
            Vertex ac = new Vertex(a.getPoint().mul(1 - t2).add(c.getPoint().mul(t2)), c.getColor());
            // lze vytvořit funkci pro ořezání, aby se neopakoval kód
            System.out.println("Jdu kreslit 1");
            drawTriangle(a, ab, ac);
        } else if (c.z < 0) {
            // TODO ac, bc - HOTOVO
            double t2 = -a.z / (c.z - a.z);
            Vertex ac = new Vertex(a.getPoint().mul(1 - t2).add(c.getPoint().mul(t2)), c.getColor());

            double t3 = -b.z / (c.z - b.z);
            Vertex bc = new Vertex(b.getPoint().mul(1 - t3).add(c.getPoint().mul(t3)), c.getColor());
            System.out.println("Jdu kreslit 2-1");
            drawTriangle(a, b, ac);
            System.out.println("Jdu kreslit 2-2");
            drawTriangle(b, ac, bc);

        } else {
            System.out.println("Jdu kreslit 3");
            drawTriangle(a, b, c);
        }
    }

    private void drawTriangle(Vertex a, Vertex b, Vertex c) {
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
        System.out.println("PO");
        System.out.println(v1.getX());
        System.out.println(v2.getX());
        System.out.println(v3.getX());
        System.out.println();
        System.out.println(v1.getY());
        System.out.println(v2.getY());
        System.out.println(v3.getY());
        System.out.println();
        System.out.println(v1.getZ());
        System.out.println(v2.getZ());
        System.out.println(v3.getZ());

        // TODO upravit cyklus
        // TODO dodělat barvy
        for (int y = (int) Math.round(v1.getY()); y < v2.getY(); y++) {
            double t12 = (y - v1.getY()) / (v2.getY() - v1.getY());
//            double x12 = v1.getX() * (1 - t12) + v2.getX() * t12;
//            double z12 = v1.getZ() * (1 - t12) + v2.getZ() * t12;
            Vec3D v12 = v1.mul(1 - t12).add(v2.mul(t12));

            double t13 = (y - v1.getY()) / (v3.getY() - v1.getY());
            Vec3D v13 = v1.mul(1 - t13).add(v3.mul(t13));

            fillLine(y, v12, v13, c1, c3);
        }

        for (int y = (int) Math.round(v2.getY()); y < v3.getY(); y++) {
            double t13 = (y - v1.getY()) / (v3.getY() - v1.getY());
            Vec3D v13 = v1.mul(1 - t13).add(v3.mul(t13));

            double t23 = (y - v2.getY()) / (v3.getY() - v2.getY());
            Vec3D v23 = v2.mul(1 - t23).add(v3.mul(t23));

            fillLine(y, v13, v23, c2, c3);
        }
    }

    private void fillLine(int y, Vec3D a, Vec3D b, Color cA, Color cB) {
        System.out.println("start fillLine");
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
            System.out.println("t : " + (x - a.getX()) / (b.getX() - a.getX()));
            System.out.println("x :" + x);
            System.out.println("a.getX() :" + a.getX());
            System.out.println("a.getY() :" + a.getY());
            System.out.println("a.getZ() :" + a.getZ());
            System.out.println("b.getX() :" + b.getX());
            System.out.println("b.getY() :" + b.getY());
            System.out.println("b.getZ() :" + b.getZ());
            System.out.println("x :");
            System.out.println("x :");
            System.out.println("a.getZ() * (1 - t) + b.getZ() * t === " + a.getZ() * (1 - t) + b.getZ() * t);
            double z = a.getZ() * (1 - t) + b.getZ() * t;
            System.out.println("drawPixel");
            drawPixel(x, y, z, cA);
        }
    }

    private void drawPixel(int x, int y, double z, Color color) {
        System.out.println("Renderer3D - start drawPixel");
        System.out.println("z je : " + z);
        System.out.println("zBuffer je : " + zBuffer.get(x, y));
        if (z < zBuffer.get(x, y)) {
            System.out.println("Podmínka Renderer3d drawPixel");
            zBuffer.set(z, x, y);
            raster.drawPixel(x, y, color.getRGB());
        }
    }

    private Vec3D transformToWindow(Vec3D v) {
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

