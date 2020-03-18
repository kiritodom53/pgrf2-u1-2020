package com.mandinec.pgrf2.projekt1.renderer;

import com.mandinec.pgrf2.projekt1.model.Element;
import com.mandinec.pgrf2.projekt1.model.ElementType;
import com.mandinec.pgrf2.projekt1.model.Vertex;
import com.mandinec.pgrf2.projekt1.view.Raster;
import transforms.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class Renderer3D implements GPURenderer {

    private final Raster raster;
    private Mat4 model, view, projection;
    private boolean fill = true;

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
    public void draw(List<Element> elements, List<Vertex> vb, List<Integer> ib, boolean fill) {
        this.fill = fill;
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

    private void prepareLine(Vertex a, Vertex b){
//        a = new Vertex(a.getPoint().mul(model).mul(view).mul(projection), a.getColor());
//        b = new Vertex(b.getPoint().mul(model).mul(view).mul(projection), b.getColor());
        if (-a.w > a.x && -b.w > b.x) return;
        if (a.x > a.w && b.x > b.w) return;
//        System.out.println("1 podminka orezeani ok");
        // TODO y, z - HOTOVO
        if (-a.w > a.y && -b.w > b.y) return;
        if (a.y > a.w && b.y > b.w) return;
        this.drawLine(a, b);
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

      //  System.out.println("3 podminka orezeani ok");
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
//            double t = v1.getPosition().getZ() / (v1.getPosition().getZ() - v2.getPosition().getZ());
//            return v1.mul(1 - t).add(v2.mul(t));


//            double t = (0 - a.z) / (b.z - a.z);
//            Vertex ab = new Vertex(a.getPoint().mul(1 - t).add(b.getPoint().mul(t)), b.getColor());

            double t = a.getPoint().getZ() / (a.getPoint().getZ() - b.getPoint().getZ());
            Vertex ab = new Vertex(a.getPoint().mul(1 - t).add(b.getPoint().mul(t)), b.getColor());
            // nekorektně barva, měla by se také interpolovat; volitelně

//            double t2 = -a.z / (c.z - a.z);
//            Vertex ac = new Vertex(a.getPoint().mul(1 - t2).add(c.getPoint().mul(t2)), c.getColor());

            double t2 = a.getPoint().getZ() / (a.getPoint().getZ() - c.getPoint().getZ());
            Vertex ac = new Vertex(a.getPoint().mul(1 - t2).add(c.getPoint().mul(t2)), c.getColor());
            // lze vytvořit funkci pro ořezání, aby se neopakoval kód
            System.out.println("Jdu kreslit 1");
            drawTriangle(a, ab, ac);
        } else if (c.z < 0) {
            // TODO ac, bc - HOTOVO
//            double t2 = -a.z / (c.z - a.z);
//            Vertex ac = new Vertex(a.getPoint().mul(1 - t2).add(c.getPoint().mul(t2)), c.getColor());
//
//            double t3 = -b.z / (c.z - b.z);
//            Vertex bc = new Vertex(b.getPoint().mul(1 - t3).add(c.getPoint().mul(t3)), c.getColor());

            double t = b.getPoint().getZ() / (b.getPoint().getZ() - c.getPoint().getZ());
            Vertex bc = new Vertex(b.getPoint().mul(1 - t).add(c.getPoint().mul(t)), c.getColor());

            double t2 = a.getPoint().getZ() / (a.getPoint().getZ() - c.getPoint().getZ());
            Vertex ac = new Vertex(a.getPoint().mul(1 - t2).add(c.getPoint().mul(t2)), c.getColor());
            System.out.println("Jdu kreslit 2");
            drawTriangle(a, b, ac);
            drawTriangle(b, ac, bc);

        } else {
            System.out.println("Jdu kreslit 3");
            System.out.println("a.z : " + a.z);
            System.out.println("b.z : " + b.z);
            System.out.println("c.z : " + c.z);
            drawTriangle(a, b, c);
        }
    }


    public void drawLine(Vertex a, Vertex b) {

        Color c1 = a.getColor();
        Color c2 = b.getColor();

        Optional<Vec3D> d1 = a.getPoint().dehomog();
        Optional<Vec3D> d2 = b.getPoint().dehomog();

//        Vec3D va = RasterizerUtil.viewportTransformation(a.getPosition().ignoreW(), visibilitityBuffer.getWidth(), visibilitityBuffer.getHeight());
//        Vec3D vb = RasterizerUtil.viewportTransformation(b.getPosition().ignoreW(), visibilitityBuffer.getWidth(), visibilitityBuffer.getHeight());

        if (!d1.isPresent() || !d2.isPresent()) return;

        System.out.println("is present ");


        Vec3D v1 = d1.get();
        Vec3D v2 = d2.get();

        v1 = transformToWindow(v1);
        v2 = transformToWindow(v2);


        if (v1.getY() > v2.getY()) {
            Vec3D temp = v1;
            v1 = v2;
            v2 = temp;

            Color tempC = c1;
            c1 = c2;
            c2 = tempC;
        }

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
                double t1 = (x - v1.getX()) / (v2.getX() - v1.getX());
//                Vertex vertexAB = a.mul(1 - t1).add(b.mul(t1));
                Vertex vertexAB = new Vertex(a.getPoint().mul(1 - t1).add(b.getPoint().mul(t1)), c1);
                System.out.println(vertexAB.x);
                System.out.println(vertexAB.y);
                System.out.println(vertexAB.z);
                System.out.println(vertexAB.w);
                System.out.println("vykresleni 1");
                this.drawPixel(x, y, vertexAB.z, vertexAB.getColor());
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
                double t1 = (y - v1.getY()) / (v2.getY() - v1.getY());
//                Vertex vertexAB = a.mul(1 - t1).add(b.mul(t1));
                Vertex vertexAB = new Vertex(a.getPoint().mul(1 - t1).add(b.getPoint().mul(t1)), c1);
                System.out.println("vykresleni 2");
                this.drawPixel(x, y, vertexAB.z, vertexAB.getColor());
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

    private void zpracuj(Vertex a, Vertex b, Vertex c){
        Graphics2D g = (Graphics2D) raster.getGraphics();
        g.setColor(new Color(1.0f, 1.0f, 1.0f));
        g.drawLine((int)a.x,(int)a.y,(int)b.x,(int)b.y);
        g.drawLine((int)c.x,(int)c.y,(int)b.x,(int)b.y);
        g.drawLine((int)a.x,(int)a.y,(int)c.x,(int)c.y);


        if(a.y > b.y){
            Vertex pom = a;
            a = b;
            b = pom;
        }
        if(b.y > c.y){
            Vertex pom = b;
            b = c;
            c = pom;
        }
        if(c.y > a.y){
            Vertex pom = c;
            c = a;
            a = pom;
        }


        for(int y = Math.max((int) (a.y+1), 0)  ; y <= Math.min(b.y, raster.getHeight()) ; y++){
            double t1 = (y-a.y)/(b.y-a.y);
            double t2 = (y-a.y)/(c.y-a.y);

            Point3D v1 = b.getPoint().mul(t1).add(a.getPoint().mul(t1));
            Point3D v2 = b.getPoint().mul(t2).add(a.getPoint().mul(t2));
            if (v1.getX() > v2.getX()){
                Point3D pom = v1;
                v1 = v2;
                v2 = pom;

            }
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
//        System.out.println("PO");
//        System.out.println(v1.getX());
//        System.out.println(v2.getX());
//        System.out.println(v3.getX());
//        System.out.println();
//        System.out.println(v1.getY());
//        System.out.println(v2.getY());
//        System.out.println(v3.getY());
//        System.out.println();
//        System.out.println(v1.getZ());
//        System.out.println(v2.getZ());
//        System.out.println(v3.getZ());

        // TODO upravit cyklus
        // TODO dodělat barvy

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
            if (fill)
            this.fillLine(y, vAB, vAC, c1, c3);
            else
            this.drawLine(y,vAB,vAC,c2,c3);

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
            if (fill)
                this.fillLine(y, vBC, vAC, c2, c3);
            else
                this.drawLine(y,vBC,vAC,c2,c3);

        }

//        for (int y = (int) (v1.getY() + 1); y < v2.getY(); y++) {
//            double t12 = (y - v1.getY()) / (v2.getY() - v1.getY());
////            double x12 = v1.getX() * (1 - t12) + v2.getX() * t12;
////            double z12 = v1.getZ() * (1 - t12) + v2.getZ() * t12;
//            Vec3D v12 = v1.mul(1 - t12).add(v2.mul(t12));
//
//            double t13 = (y - v1.getY()) / (v3.getY() - v1.getY());
//            Vec3D v13 = v1.mul(1 - t13).add(v3.mul(t13));
//
//            fillLine(y, v12, v13, c1, c3);
//        }
//
//        for (int y = (int) (v2.getY() + 1); y < v3.getY(); y++) {
//            double t13 = (y - v1.getY()) / (v3.getY() - v1.getY());
//            Vec3D v13 = v1.mul(1 - t13).add(v3.mul(t13));
//
//            double t23 = (y - v2.getY()) / (v3.getY() - v2.getY());
//            Vec3D v23 = v2.mul(1 - t23).add(v3.mul(t23));
//
//            fillLine(y, v13, v23, c2, c3);
//        }
    }

    private void drawLine(int y, Vec3D a, Vec3D b, Color cA, Color cB) {
        if (a.getX() > b.getX()) {
            Vec3D temp = a;
            a = b;
            b = temp;

            Color tempC = cA;
            cA = cB;
            cB = tempC;
        }


          //  double t = (x - a.getX()) / (b.getX() - a.getX());
           // double z = a.getZ() * (1 - t) + b.getZ() * t;
//            System.out.println("drawPixel");
            drawPixel((int) Math.round(a.getX()-1), y-1, a.getZ(), cA);
            drawPixel((int) Math.round(b.getX()-1), y-1, b.getZ(), cA);

    }

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

        int aX = 0;
        int bX = 800;
        if (a.getX() > 0){
            aX = (int) Math.round(a.getX());
        }
        if (b.getX() < 800){
            bX =(int) b.getX();
        }

        for (int x = aX; x < bX; x++) {
            double t = (x - aX) / (bX - a.getX());
            double z = a.getZ() * (1 - t) + b.getZ() * t;
//            System.out.println("drawPixel");
            drawPixel(x-1, y-1, z, cA);
        }
    }

    private void drawPixel(int x, int y, double z, Color color) {
//        System.out.println("Renderer3D - start drawPixel");
//        System.out.println("z je : " + z);
//        System.out.println("zBuffer je : " + zBuffer.get(x, y));
        if (zBuffer.get(x,y) == null){

        }else {

        if (z < zBuffer.get(x, y)) {
//            System.out.println("Podmínka Renderer3d drawPixel");
            zBuffer.set(z, x, y);
            raster.drawPixel(x, y, color.getRGB());
        }
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

