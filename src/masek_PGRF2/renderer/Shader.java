package masek_PGRF2.renderer;

@FunctionalInterface
public interface Shader<V, C> {

    C shade(V... v);

}