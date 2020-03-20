package com.mandinec.pgrf2.projekt1;

import com.mandinec.pgrf2.projekt1.controller.Controller3D;
import com.mandinec.pgrf2.projekt1.objects.Cube;
import com.mandinec.pgrf2.projekt1.objects.Triangle;
import com.mandinec.pgrf2.projekt1.renderer.Renderer3D;
import com.mandinec.pgrf2.projekt1.view.Raster;
import com.mandinec.pgrf2.projekt1.view.Toolbar;
import transforms.Mat4;
import transforms.Mat4OrthoRH;
import transforms.Mat4PerspRH;

import javax.swing.*;
import java.awt.*;

public class PGRFWindow extends JFrame {

    private final Raster raster;
    private int click;
    private int mousePositionX;
    private int mousePositionY;
    private int newMousePosX;
    private int newMousePosY;
    private Toolbar toolbar;

    private Boolean cube = false;


    private Renderer3D renderer;
    private static Controller3D cont;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PGRFWindow window = new PGRFWindow();
            cont = new Controller3D(window.getRaster());
            window.setVisible(true);
        });
    }

    // ehm private -> public
    public PGRFWindow() {
        // bez tohoto nastavení se okno zavře, ale aplikace stále běží na pozadí
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("PGRF2 cvičení (úterý 13:15)"); // titulek okna

        raster = new Raster();
        raster.setFocusable(true);
        raster.grabFocus(); // důležité pro pozdější ovládání z klávesnice

        //renderer = new Renderer3D(raster);


        //cont.getRenderer3D().setProjection(new Mat4PerspRH(Math.PI / 4, Raster.HEIGHT / (float) Raster.WIDTH, 1, 200));
        //cont.getRenderer3D().setProjection(new Mat4OrthoRH(20, 20, 0.1, 200));


        FlowLayout layout = new FlowLayout();
        layout.setVgap(0);
        layout.setHgap(0);

        BorderLayout layout1 = new BorderLayout();

        setLayout(layout1);
        add(raster, BorderLayout.CENTER); // vložit plátno do okna

        toolbar = new Toolbar();
        add(toolbar.getToolBar(), BorderLayout.PAGE_START);

        pack();

        setLocationRelativeTo(null); // vycentrovat okno
        actionButtons();
    }

    public void actionButtons(){
        //toolbar.getBtnPer().addActionListener(e -> cont.getRenderer3D().setProjection(new Mat4PerspRH(Math.PI / 4, Raster.HEIGHT / (float) Raster.WIDTH, 1, 200)));
        //toolbar.getBtnOr().addActionListener(e -> cont.getRenderer3D().setProjection(new Mat4OrthoRH(20, 20, 0.1, 200)));
        toolbar.getBtnPer().addActionListener(e -> setProjections(new Mat4PerspRH(Math.PI / 4, Raster.HEIGHT / (float) Raster.WIDTH, 1, 200)));
        toolbar.getBtnOr().addActionListener(e -> setProjections(new Mat4OrthoRH(20, 20, 0.1, 200)));
    }



    private void setProjections(Mat4 proj){
        cont.getRenderer3D().setProjection(proj);
        cont.display();
    }


    public Raster getRaster() {
        return raster;
    }

}
