package com.mandinec.pgrf2.projekt1.view;

import com.mandinec.pgrf2.projekt1.model.Element;
import com.mandinec.pgrf2.projekt1.model.ElementType;
import com.mandinec.pgrf2.projekt1.model.Vertex;
import com.mandinec.pgrf2.projekt1.renderer.Renderer3D;
import transforms.Mat4;
import transforms.Mat4RotXYZ;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PGRFWindow extends JFrame {

    private final Raster raster;
    private int click;
    private int mousePositionX;
    private int mousePositionY;
    private int newMousePosX;
    private int newMousePosY;
    private Toolbar toolbar;

    public PGRFWindow() {
        // bez tohoto nastavení se okno zavře, ale aplikace stále běží na pozadí
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("PGRF2 cvičení (úterý 13:15)"); // titulek okna

        raster = new Raster();
        raster.setFocusable(true);
        raster.grabFocus(); // důležité pro pozdější ovládání z klávesnice

        FlowLayout layout = new FlowLayout();
        layout.setVgap(0);
        layout.setHgap(0);

        setLayout(layout);
        add(raster); // vložit plátno do okna

        pack();

        /*toolbar = new Toolbar();
        toolbar.getRbBezier().setSelected(true);
        add(toolbar.getToolBar(), BorderLayout.PAGE_START);*/

        setLocationRelativeTo(null); // vycentrovat okno


//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent g) {
//                click = g.getButton();
//                addMouseMotionListener(new MouseAdapter() {
//                    @Override
//                    public void mouseDragged(MouseEvent e) {
//                        newMousePosX = mousePositionX;
//                        newMousePosY = mousePositionY;
//
//                        mousePositionX = e.getX();
//                        mousePositionY = e.getY();
//                        if (click == MouseEvent.BUTTON1) {
//                            camera = camera.addAzimuth(-(mousePositionX - newMousePosX) * Math.PI / 360);
//                            camera = camera.addZenith(-(mousePositionY - newMousePosY) * Math.PI / 360);
//                        } else if (click == MouseEvent.BUTTON3) {
//                            Mat4 rot = new Mat4RotXYZ(0, -(mousePositionY - newMousePosY) * 0.02, (mousePositionX - newMousePosX) * 0.02);
//                            model = model.mul(rot);
//                        }
//                        draw();
//                    }
//                });
//            }
//        });
    }

    public Raster getRaster() {
        return raster;
    }

}
