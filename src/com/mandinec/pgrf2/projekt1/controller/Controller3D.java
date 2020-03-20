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

    private int click;
    private int mousePositionX;
    private int mousePositionY;
    private int newMousePosX;
    private int newMousePosY;


    private List<Solid> solids = new ArrayList<>();

    public GPURenderer getRenderer3D() {
        return renderer3D;
    }

    public Controller3D(Raster raster) {
        initObjects(raster);
        initListeners(raster);
    }

    public void display() {
        renderer3D.clear();

        renderer3D.setModel(renderer3D.getModel());
        renderer3D.setView(camera.getViewMatrix());

        for (Solid solid : solids) {
            renderer3D.draw(solid);
        }
    }

    private void initObjects(Raster raster) {
        renderer3D = new Renderer3D(raster);
        resetCamera();

        solids.add(new Cube());
        solids.add(new Bicubic(Cubic.BEZIER));
        solids.add(new Triangle());
        solids.add(new Xyz());

        for (Solid solid : solids) {
            renderer3D.draw(solid);
        }

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
                mousePositionX = e.getX();
                mousePositionY = e.getY();
                click = e.getButton();
            }
        });


        raster.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                newMousePosX = mousePositionX;
                newMousePosY = mousePositionY;
                mousePositionX = e.getX();
                mousePositionY = e.getY();
                if (click == MouseEvent.BUTTON1) {
                    camera = camera.addAzimuth(-(mousePositionX - newMousePosX) * Math.PI / 360);
                    camera = camera.addZenith(-(mousePositionY - newMousePosY) * Math.PI / 360);
                    renderer3D.setView(camera.getViewMatrix());
                } else if (click == MouseEvent.BUTTON3) {
                    Mat4 rot = new Mat4RotXYZ(0, -(mousePositionY - newMousePosY) * 0.02, (mousePositionX - newMousePosX) * 0.02);
                    renderer3D.setModel(renderer3D.getModel().mul(rot));
                }
                renderer3D.clear();
                for (Solid solid : solids) {
                    renderer3D.draw(solid);
                }
            }
        });

        raster.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        System.out.println("key");
                        camera = camera.up(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        System.out.println("key");
                        camera = camera.down(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        System.out.println("key");
                        camera = camera.right(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        System.out.println("key");
                        camera = camera.left(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_T:
                        System.out.println("key");
                        camera = camera.forward(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_Z:
                        System.out.println("key");
                        camera = camera.backward(0.1);
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_X:
                        Mat4 scale;
                        scale = new Mat4Scale(1.1, 1.1, 1.1);
                        renderer3D.setModel(renderer3D.getModel().mul(scale));
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_Y:
                        Mat4 scale2;
                        scale2 = new Mat4Scale(0.9, 0.9, 0.9);
                        renderer3D.setModel(renderer3D.getModel().mul(scale2));
                        renderer3D.setView(camera.getViewMatrix());
                        display();
                        break;
                    case KeyEvent.VK_R:
                        System.out.println("r");
                        resetCamera();
                        renderer3D.setProjection(new Mat4PerspRH(Math.PI / 4, Raster.HEIGHT / (float) Raster.WIDTH, 1, 200));
                        renderer3D.setWireframeModel(false);
                        display();
                        break;
                    case KeyEvent.VK_Q:
                        if (renderer3D.isWireframeModel()) {
                            renderer3D.setWireframeModel(false);
                        } else {
                            renderer3D.setWireframeModel(true);
                        }
                        display();
                        break;
                    case KeyEvent.VK_O:
                        renderer3D.setProjection(new Mat4PerspRH(Math.PI / 4, Raster.HEIGHT / (float) Raster.WIDTH, 1, 200));
                        display();
                        break;
                    case KeyEvent.VK_P:
                        renderer3D.setProjection(new Mat4OrthoRH(20, 20, 0.1, 200));
                        display();
                        break;
                }
            }
        });
    }
}
