package com.mandinec.pgrf2.projekt1.renderer;

@FunctionalInterface
public interface Shader<V, C> {

    C shade(V v);

}