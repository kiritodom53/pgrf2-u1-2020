package com.mandinec.pgrf2.projekt1;

import com.mandinec.pgrf2.projekt1.renderer.Renderer3D;
import com.mandinec.pgrf2.projekt1.view.Raster;

import javax.swing.*;
import java.awt.*;

public class PGRFWindow extends JFrame {

    private final Raster raster;

    public PGRFWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("PGRF2 - 1 úkol - Dominik Mandinec"); // titulek okna

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
    }

    public Raster getRaster() {
        return raster;
    }

}
