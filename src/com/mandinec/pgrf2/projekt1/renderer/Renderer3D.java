package com.mandinec.pgrf2.projekt1.renderer;

import com.mandinec.pgrf2.projekt1.model.Element;
import com.mandinec.pgrf2.projekt1.model.ElementType;
import com.mandinec.pgrf2.projekt1.model.Vertex;
import com.mandinec.pgrf2.projekt1.objects.Solid;
import com.mandinec.pgrf2.projekt1.raster.LineRas;
import com.mandinec.pgrf2.projekt1.raster.TriangleRas;
import com.mandinec.pgrf2.projekt1.view.Raster;
import transforms.*;

import java.awt.*;
import java.util.List;

public class Renderer3D implements GPURenderer {

    private final Raster raster;
    private Mat4 model, view, projection;
    private LineRas lineRas;
    private TriangleRas triangleRas;

    @Override
    public boolean isWireframeModel() {
        return wireframeModel;
    }

    @Override
    public void setWireframeModel(boolean wireframeModel) {
        this.wireframeModel = wireframeModel;
    }

    private boolean wireframeModel = true;

    private ZBuffer<Double> zBuffer;

    public Renderer3D(Raster raster) {
        this.raster = raster;

        model = new Mat4Identity();
        view = new Mat4Identity();

        projection = new Mat4PerspRH(Math.PI / 4, Raster.HEIGHT / (float) Raster.WIDTH, 1, 200);
        zBuffer = new ZBuffer<>(new Double[Raster.WIDTH][Raster.HEIGHT]);
        zBuffer.clear(10d); //

    }

    @Override
    public void draw(Solid solid) {
        for (Element element : solid.getElemetns()) {
            final int start = element.getStart();
            final int count = element.getCount();

            if (element.getElementType() == ElementType.TRIANGLE) {
                for (int i = start; i < count + start; i += 3) {
                    final Integer i1 = solid.getIb().get(i);
                    final Integer i2 = solid.getIb().get(i + 1);
                    final Integer i3 = solid.getIb().get(i + 2);
                    final Vertex v1 = solid.getVb().get(i1);
                    final Vertex v2 = solid.getVb().get(i2);
                    final Vertex v3 = solid.getVb().get(i3);
                    lineRas = new LineRas(raster);
                    triangleRas = new TriangleRas(raster);
                    prepareTriangle(v1, v2, v3);
                }
            } else if (element.getElementType() == ElementType.LINE) {
                for (int i = start; i < count + start; i += 2) {
                    final Integer i1 = solid.getIb().get(i);
                    final Integer i2 = solid.getIb().get(i + 1);
                    final Vertex v1 = solid.getVb().get(i1);
                    final Vertex v2 = solid.getVb().get(i2);
                    lineRas = new LineRas(raster);
                    triangleRas = new TriangleRas(raster);
                    prepareLine(v1, v2);
                }
            } else {
                // Point
            }
        }
    }

    private void prepareLine(Vertex a, Vertex b) {
        a = new Vertex(a.getPoint().mul(model).mul(view).mul(projection), a.getColor());
        b = new Vertex(b.getPoint().mul(model).mul(view).mul(projection), b.getColor());


        if (-a.w > a.x && -b.w > b.x) return;
        if (a.x > a.w && b.x > b.w) return;
        if (-a.w > a.y && -b.w > b.y) return;
        if (a.y > a.w && b.y > b.w) return;

        if (a.z < b.z) {
            Vertex temp = a;
            a = b;
            b = temp;
        }

        if (a.z < 0) {
            return;
        } else if (b.z < 0) {
            double t = a.getPoint().getZ() / (a.getPoint().getZ() - b.getPoint().getZ());
            Vertex ab = new Vertex(a.getPoint().mul(1 - t).add(b.getPoint().mul(t)), b.getColor());
            lineRas.draw(a, ab);
        } else {
            lineRas.draw(a, b);
        }
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
        if (-a.w > a.y && -b.w > b.y && -c.w > c.y) return;
        if (a.y > a.w && b.y > b.w && c.y > c.w) return;
        if (0 > a.z && 0 > b.z && 0 > c.z) return;
        if (a.z > a.w && b.z > b.w && c.z > c.w) return;

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

        // 4. ořezání a interpolace podle hrany Z
        if (a.z < 0) {
            return;
        } else if (b.z < 0) {

            double t = a.getPoint().getZ() / (a.getPoint().getZ() - b.getPoint().getZ());
            Vertex ab = new Vertex(a.getPoint().mul(1 - t).add(b.getPoint().mul(t)), b.getColor());

            double t2 = a.getPoint().getZ() / (a.getPoint().getZ() - c.getPoint().getZ());
            Vertex ac = new Vertex(a.getPoint().mul(1 - t2).add(c.getPoint().mul(t2)), c.getColor());

            if (!wireframeModel) {
                triangleRas.drawTriangle(a, ab, ac);
            } else {
                lineRas.draw(a, ab);
                lineRas.draw(ab, ac);
                lineRas.draw(ac, a);
            }

        } else if (c.z < 0) {
            double t = b.getPoint().getZ() / (b.getPoint().getZ() - c.getPoint().getZ());
            Vertex bc = new Vertex(b.getPoint().mul(1 - t).add(c.getPoint().mul(t)), c.getColor());

            double t2 = a.getPoint().getZ() / (a.getPoint().getZ() - c.getPoint().getZ());
            Vertex ac = new Vertex(a.getPoint().mul(1 - t2).add(c.getPoint().mul(t2)), c.getColor());

            if (!wireframeModel) {
                triangleRas.drawTriangle(a, b, ac);
                triangleRas.drawTriangle(b, ac, bc);
            } else {
                lineRas.draw(a, b);
                lineRas.draw(b, ac);
                lineRas.draw(a, ac);

                lineRas.draw(b, ac);
                lineRas.draw(ac, bc);
                lineRas.draw(bc, b);
            }
        } else {
            if (!wireframeModel) {
                triangleRas.drawTriangle(a, b, c);
            } else {
                lineRas.draw(a, b);
                lineRas.draw(a, c);
                lineRas.draw(b, c);
            }
        }
    }

    protected Boolean cut(Point3D point) {
        return -point.getW() <= point.getY() &&
                -point.getW() <= point.getX() &&
                point.getY() <= point.getW() &&
                point.getX() <= point.getW() &&
                point.getZ() >= 0 &&
                point.getZ() <= point.getW();
    }

    protected void fillLine(int y, Vec3D a, Vec3D b, Color cA, Color cB) {
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
            drawPixel(x - 1, y - 1, z, cA);
        }
    }

    public void drawPixel(int x, int y, double z, Color color) {
        if (z < zBuffer.get(x, y)) {
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
