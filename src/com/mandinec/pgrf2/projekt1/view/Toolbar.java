package com.mandinec.pgrf2.projekt1.view;

import com.mandinec.pgrf2.projekt1.controller.Controller3D;

import javax.swing.*;

/**
 * Created by Dominik Mandinec
 */

public class Toolbar {
    private JToolBar toolBar;
    private JButton btnPer;
    private JButton btnOr;
    private JButton btnReset;

    private ButtonGroup bg;

    public ButtonGroup getBg() {
        return bg;
    }

    public Toolbar() {
        toolBar = new JToolBar();

        btnPer = new JButton();
        btnOr = new JButton();
        btnReset = new JButton();

        bg = new ButtonGroup();

        btnPer.setText("Perspectivní");
        btnOr.setText("Ortogonální");
        btnReset.setText("Reset");

        btnPer.setFocusable(false);
        btnOr.setFocusable(false);
        btnReset.setFocusable(false);
        toolBar.setFocusable(false);

        this.initComponents();
    }

    private void initComponents() {
        toolBar.add(btnReset);
        toolBar.add(btnPer);
        toolBar.add(btnOr);
    }


    public JToolBar getToolBar() {
        return toolBar;
    }

    public JButton getBtnPer() {
        return btnPer;
    }

    public JButton getBtnOr() {
        return btnOr;
    }

    public JButton getBtnReset() {
        return btnReset;
    }

}
