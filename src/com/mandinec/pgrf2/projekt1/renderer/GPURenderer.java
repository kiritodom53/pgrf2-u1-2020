package com.mandinec.pgrf2.projekt1.renderer;

import com.mandinec.pgrf2.projekt1.model.Element;
import com.mandinec.pgrf2.projekt1.model.Vertex;
import com.mandinec.pgrf2.projekt1.objects.Solid;
import transforms.Mat4;

import java.awt.*;
import java.util.List;

public interface GPURenderer {

    void draw(Solid solid);

    void clear();

    Mat4 getModel();

    boolean isWireframeModel();

    void setWireframeModel(boolean wireframeModel);

    void setModel(Mat4 model);

    Mat4 getView();

    void setView(Mat4 view);

    Mat4 getProjection();

    void setProjection(Mat4 projection);

    void setShader(Shader<Vertex, Color> shader);
}
