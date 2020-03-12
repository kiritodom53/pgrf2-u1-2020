package com.mandinec.pgrf2.projekt1.view;

import com.mandinec.pgrf2.projekt1.model.Element;
import com.mandinec.pgrf2.projekt1.model.ElementType;
import com.mandinec.pgrf2.projekt1.model.Vertex;
import com.mandinec.pgrf2.projekt1.renderer.Renderer3D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PGRFWindow extends JFrame {

    private final Raster raster;

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
        setLocationRelativeTo(null); // vycentrovat okno

        Raster raster = new Raster();
        Renderer3D render = new Renderer3D(raster);

        Element e = new Element(ElementType.TRIANGLE, 0, 0);
        ArrayList<Integer> ib = new ArrayList<>();
        ArrayList<Vertex> vb = new ArrayList<>();

        /*ib.add(0);
        ib.add(1);
        ib.add(1);
        ib.add(2);
        ib.add(2);
        ib.add(3);
        ib.add(3);
        ib.add(0);
        ib.add(0);
        ib.add(4);
        ib.add(4);
        ib.add(5);
        ib.add(5);
        ib.add(6);
        ib.add(6);
        ib.add(7);
        ib.add(7);
        ib.add(4);
        ib.add(1);
        ib.add(5);
        ib.add(2);
        ib.add(6);
        ib.add(3);
        ib.add(7);

        vb.add(new Point3D(-2, -2, -2));
        vb.add(new Point3D(-2, 2, -2));
        vb.add(new Point3D(2, 2, -2));
        vb.add(new Point3D(2, -2, -2));

        vb.add(new Point3D(-2, -2, 2));
        vb.add(new Point3D(-2, 2, 2));
        vb.add(new Point3D(2, 2, 2));
        vb.add(new Point3D(2, -2, 2));
        setColor(0x00ffff);


        render.draw();*/
    }

    public Raster getRaster() {
        return raster;
    }

}
