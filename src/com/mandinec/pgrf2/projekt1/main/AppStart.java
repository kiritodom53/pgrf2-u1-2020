package com.mandinec.pgrf2.projekt1.main;

import com.mandinec.pgrf2.projekt1.PGRFWindow;
import com.mandinec.pgrf2.projekt1.controller.Controller3D;

import javax.swing.*;

public class AppStart {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PGRFWindow window = new PGRFWindow();
            new Controller3D(window.getRaster());
            window.setVisible(true);
        });
    }
}
