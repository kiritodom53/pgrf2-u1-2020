package com.mandinec.pgrf2.projekt1.renderer;

import com.mandinec.pgrf2.projekt1.model.Element;
import com.mandinec.pgrf2.projekt1.model.Vertex;
import transforms.Mat4;

import java.awt.*;
import java.util.List;

public interface GPURenderer {

    void draw(List<Element> elements, List<Vertex> vb, List<Integer> ib , boolean fill);

    void clear();

    Mat4 getModel();

    void setModel(Mat4 model);

    Mat4 getView();

    void setView(Mat4 view);

    Mat4 getProjection();

    void setProjection(Mat4 projection);

    void setShader(Shader<Vertex, Color> shader);

}
