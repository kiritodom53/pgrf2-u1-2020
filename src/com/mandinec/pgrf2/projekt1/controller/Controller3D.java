package com.mandinec.pgrf2.projekt1.controller;

import com.mandinec.pgrf2.projekt1.model.Element;
import com.mandinec.pgrf2.projekt1.model.ElementType;
import com.mandinec.pgrf2.projekt1.model.Vertex;
import com.mandinec.pgrf2.projekt1.objects.*;
import com.mandinec.pgrf2.projekt1.objects.Bicubic;
import com.mandinec.pgrf2.projekt1.renderer.GPURenderer;
import com.mandinec.pgrf2.projekt1.renderer.Renderer3D;
import com.mandinec.pgrf2.projekt1.renderer.Shader;
import com.mandinec.pgrf2.projekt1.view.Raster;
import transforms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Controller3D {

    private GPURenderer renderer3D;
    private Camera camera;
    private Shader<Vertex, Color> testShader;

    private int click;
    private int mousePositionX;
    private int mousePositionY;
    private int newMousePosX;
    private int newMousePosY;

    //private Cube cube = new Cube();

    private List<Solid> solids = new ArrayList<>();

    private int mx, my;

    private List<Element> elements = new ArrayList<>();
    private List<Vertex> vb = new ArrayList<>();
    private List<Integer> ib = new ArrayList<>();

    // ehm
    public GPURenderer getRenderer3D() {
        return renderer3D;
    }

    public Controller3D(Raster raster) {
        initObjects(raster);
        initListeners(raster);
    }

    public void display() {
        renderer3D.clear();

        //renderer3D.setModel(new Mat4Identity());
        renderer3D.setModel(renderer3D.getModel());
        renderer3D.setView(camera.getViewMatrix());
        renderer3D.setShader(testShader);
//        renderer3D.setProjection();
        renderer3D.setShader(vertex -> new Color(Math.round(vertex.x % 255), 0, 0));


        //renderer3D.draw(elements, vb, ib);
        for (Solid solid : solids) {

            renderer3D.draw(solid);
        }


        //renderer3D.draw(new Cube());

        //renderer3D.setModel(new Mat4Transl(0, 0, 0));

//        renderer3D.draw();
    }

    private void initObjects(Raster raster) {
        renderer3D = new Renderer3D(raster);
        resetCamera();

        testShader = Vertex::getColor;

        testShader = vertex -> {
            long zbytek = Math.round(vertex.x) % 2;
//                if (zbytek == 0) return Color.GREEN;
//                else return Color.BLUE;
            return (zbytek == 0) ? Color.GREEN : Color.BLUE;
        };

        // TODO předpoklad naplněného vertex bufferu

//        ib = new ArrayList<>();
//        vb = new ArrayList<>();
//        elements = new ArrayList<>();
//
//
//
//        elements.add(new Element(ElementType.TRIANGLE, 3, 0));
//        elements.add(new Element(ElementType.TRIANGLE, 3, 3));
//        elements.add(new Element(ElementType.TRIANGLE, 3, 6));
//        elements.add(new Element(ElementType.TRIANGLE, 3, 9));
//
//        vb.add(new Vertex(new Point3D(0, 0, 0.5), Color.CYAN));
//        vb.add(new Vertex(new Point3D(1, 0, 0.5), Color.CYAN));
//        vb.add(new Vertex(new Point3D(0, 1, 0.5), Color.CYAN));
//
//        vb.add(new Vertex(new Point3D(0, 0, 0.5), Color.RED));
//        vb.add(new Vertex(new Point3D(1, 0, 0.5), Color.CYAN));
//        vb.add(new Vertex(new Point3D(1, 1, 0.7), Color.GREEN));
//
//        vb.add(new Vertex(new Point3D(0, 1, 0.5), Color.GREEN));
//        vb.add(new Vertex(new Point3D(1, 1, 0.7), Color.GREEN));
//        vb.add(new Vertex(new Point3D(1, 0, 0.5), Color.GREEN));
//
//        vb.add(new Vertex(new Point3D(0, 1, 0.5), Color.ORANGE));
//        vb.add(new Vertex(new Point3D(1, 1, 0.7), Color.ORANGE));
//        vb.add(new Vertex(new Point3D(0, 0, 0.5), Color.ORANGE));
//
//        ib.add(0);
//        ib.add(1);
//        ib.add(2);
//        ib.add(3);
//        ib.add(4);
//        ib.add(5);
//        ib.add(6);
//        ib.add(7);
//        ib.add(8);
//        ib.add(9);
//        ib.add(10);
//        ib.add(11);
//
//        elements.add(new Element(ElementType.LINE, 2, 12));
//
//        vb.add(new Vertex(new Point3D(.3, -.5, .3), Color.BLACK));
//        vb.add(new Vertex(new Point3D(.7, .4, .3), Color.BLACK));
//        ib.add(12);
//        ib.add(13);


//        elements.add(new Element(ElementType.TRIANGLE, 36, 0));
//
//
//        vb.add(new Vertex(new Point3D(0, 0, 0), Color.CYAN)); //0
//
//        vb.add(new Vertex(new Point3D(1, 0, 0), Color.CYAN));
//        vb.add(new Vertex(new Point3D(0, 1, 0), Color.CYAN));
//        vb.add(new Vertex(new Point3D(1, 1, 0), Color.CYAN)); // 3
//
//        vb.add(new Vertex(new Point3D(0, 0, 1), Color.CYAN));
//        vb.add(new Vertex(new Point3D(0, 1, 1), Color.CYAN)); // 5
//
//        vb.add(new Vertex(new Point3D(1, 0, 1), Color.CYAN));
//        vb.add(new Vertex(new Point3D(0, 0, 1), Color.CYAN)); // 7
//
//        vb.add(new Vertex(new Point3D(0, 1, 1), Color.CYAN));
//        vb.add(new Vertex(new Point3D(1, 1, 1), Color.CYAN)); // 9
//
//        vb.add(new Vertex(new Point3D(1, 1, 1), Color.CYAN));
//        vb.add(new Vertex(new Point3D(1, 0, 1), Color.CYAN)); // 11
//
//        vb.add(new Vertex(new Point3D(0, 1, 1), Color.CYAN));
//        vb.add(new Vertex(new Point3D(0, 0, 1), Color.CYAN)); // 13
//
//        ib.add(0);
//        ib.add(1);
//        ib.add(2);
//        ib.add(1);
//        ib.add(2);
//        ib.add(3);
//        ib.add(0);
//        ib.add(2);
//        ib.add(5);
//        ib.add(0);
//        ib.add(5);
//        ib.add(4);
//        ib.add(0);
//        ib.add(1);
//        ib.add(6);
//        ib.add(0);
//        ib.add(6);
//        ib.add(7);
//        ib.add(2);
//        ib.add(3);
//        ib.add(8);
//        ib.add(3);
//        ib.add(8);
//        ib.add(9);
//        ib.add(3);
//        ib.add(10);
//        ib.add(11);
//        ib.add(3);
//        ib.add(11);
//        ib.add(1);
//        ib.add(11);
//        ib.add(10);
//        ib.add(12);
//        ib.add(11);
//        ib.add(12);
//        ib.add(13);





        // haha troj nice
//        vb.add(new Vertex(new Point3D(-3, 0, 0.1), Color.CYAN));
//        vb.add(new Vertex(new Point3D(3, 0, 0.5), Color.CYAN));
//        vb.add(new Vertex(new Point3D(0, -3, 0.9), Color.CYAN));


//        vb.add(new Vertex(new Point3D(1, 1, 0.3), Color.BLUE));
//        vb.add(new Vertex(new Point3D(0, 0, 0.3), Color.BLUE));



//        Triangle triangle = new Triangle();
//        ib.addAll(triangle.getIb());
//        vb.addAll(triangle.getVb());
//        elements.addAll(triangle.getElemetns());
//
//        Cube cube = new Cube();
//        ib.addAll(cube.getIb());
//        vb.addAll(cube.getVb());
//        elements.addAll(cube.getElemetns());

        //solids.add(new Cube());

        solids.add(new Triangle());
        solids.add(new Xyz());





        //solids.add(new Bicubic(Cubic.FERGUSON));


//        System.out.println("ib");
//        for (Integer integer : ib) {
//            System.out.println(integer);
//        }
//        System.out.println("vb");
//        for (Vertex vertex : vb) {
////            System.out.println(vertex.x);
////            System.out.println(vertex.y);
////            System.out.println(vertex.w);
////            System.out.println(vertex.z);
////            System.out.println(vertex.getPoint());
////            System.out.println(vertex.getColor());
//        }
//        System.out.println("element");
//        for (Element element : elements) {
////            System.out.println(element.getElementType());
////            System.out.println(element.getStart());
////            System.out.println(element.getCount());
//        }

        //renderer3D.draw(elements, vb, ib);

//        renderer3D.draw(cube);

        for (Solid solid : solids) {
            renderer3D.draw(solid);
        }


        //elements.add(new Element(ElementType.LINE, 2, 6));
    }

    private void resetCamera() {
        camera = new Camera(
                new Vec3D(0, -5, 3),
                Math.toRadians(90),
                Math.toRadians(-40),
                1, true
        );
        renderer3D.setView(camera.getViewMatrix());
    }

    private void initListeners(Raster raster) {
        raster.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mx = e.getX();
                my = e.getY();
            }
        });

        raster.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePositionX = e.getX();
                mousePositionY = e.getY();
            }
        });

        raster.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent g) {
                click = g.getButton();
//                System.out.println("click1");

                }
            });

        raster.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                newMousePosX = mousePositionX;
                newMousePosY = mousePositionY;

//                        System.out.println("dragged");
                mousePositionX = e.getX();
                mousePositionY = e.getY();
                if (click == MouseEvent.BUTTON1) {
//                            System.out.println("click2");
                    //System.out.println(-(mousePositionX - newMousePosX));
                    //System.out.println(-(mousePositionX - newMousePosX) * Math.PI / 360);
                    //camera = camera.addAzimuth(-0.1);
                    //camera = camera.withAzimuth(-(mousePositionX - newMousePosX) * Math.PI / 360);
                    //camera = camera.addZenith(-0.1);
                    //camera = camera.withZenith(-(mousePositionY - newMousePosY) * Math.PI / 360);
                    Mat4 rot = new Mat4RotXYZ(0, -(mousePositionY - newMousePosY) * 0.02, (mousePositionX - newMousePosX) * 0.02);
//                            if (solid.isTransferable()) {
//                                this.model = solid.getTransMat().mul(model);
//                            } else {
//                                this.model = new Mat4Identity();
//                            }
                    //renderer3D.setModel(renderer3D.getModel().mul(rot));
                    renderer3D.setModel(renderer3D.getModel().mul(rot));
                } /*else if (click == MouseEvent.BUTTON3) {
                            Mat4 rot = new Mat4RotXYZ(0, -(mousePositionY - newMousePosY) * 0.02, (mousePositionX - newMousePosX) * 0.02);
                            model = model.mul(rot);
                        }*/
                //renderer3D.setView(camera.getViewMatrix());
                renderer3D.clear();
                //renderer3D.draw(elements, vb, ib);
                for (Solid solid : solids) {
                    renderer3D.draw(solid);
                }
            }
        });


        /*raster.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println("Dragged");
                if (SwingUtilities.isLeftMouseButton(e)) {
                    double diff = (mx - e.getX()) / 100.0;
                    double azimuth = camera.getAzimuth() + diff;
                    System.out.println(diff);
                    System.out.println(azimuth);
                    camera = camera.withAzimuth(azimuth);
                    renderer3D.setView(camera.getViewMatrix());
                    renderer3D.clear();
                    renderer3D.draw(elements, vb, ib);
                    //TODO: dodělat zenit, ořezat <-PI/2,PI/2>
                }

                //TODO: dodělat transformace
                mx = e.getX();
                my = e.getY();
            }
        });*/

        raster.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        System.out.println("key");
                        camera = camera.down(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        System.out.println("key");
                        camera = camera.up(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        System.out.println("key");
                        camera = camera.left(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        System.out.println("key");
                        camera = camera.right(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_J:
                        System.out.println("key");
                        camera = camera.forward(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_K:
                        System.out.println("key");
                        camera = camera.backward(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_NUMPAD1:
                        camera = camera.addAzimuth(-0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_NUMPAD2:
                        camera = camera.addZenith(-0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_NUMPAD4:
                        camera = camera.addAzimuth(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_NUMPAD5:
                        camera = camera.addZenith(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_O:
                        Mat4 scale;
                        scale = new Mat4Scale(1.1, 1.1, 1.1);
                        renderer3D.setModel(renderer3D.getModel().mul(scale));
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_P:
                        Mat4 scale2;
                        scale2 = new Mat4Scale(0.9, 0.9, 0.9);
                        renderer3D.setModel(renderer3D.getModel().mul(scale2));
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    // dodělat ovládání
                }
            }
        });
    }

}
