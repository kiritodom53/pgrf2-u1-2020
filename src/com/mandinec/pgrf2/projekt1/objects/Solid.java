package com.mandinec.pgrf2.projekt1.objects;

import com.mandinec.pgrf2.projekt1.model.Element;
import com.mandinec.pgrf2.projekt1.model.Vertex;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.List;

public abstract class Solid {
    protected List<Vertex> vb;
    protected List<Integer> ib;
    protected List<Element> elements;
    protected List<Integer> colors;
    protected Mat4 transMat = new Mat4Identity();
    protected boolean transferable = true;

    public List<Vertex> getVb() {
        return vb;
    }

    public List<Element> getElemetns() {
        return elements;
    }

    public List<Integer> getIb() {
        return ib;
    }

    public void setTransMat(Mat4 transMat) {
        this.transMat = transMat;
    }

    public Mat4 getTransMat() {
        return transMat;
    }

    public boolean isTransferable() {
        return transferable;
    }
}

