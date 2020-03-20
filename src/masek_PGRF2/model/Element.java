package masek_PGRF2.model;

public class Element {

    private final ElementType elementType;
    private final int count;
    private final int start;

    public Element(ElementType elementType, int count, int start) {
        this.elementType = elementType;
        this.count = count;
        this.start = start;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public int getCount() {
        return count;
    }

    public int getStart() {
        return start;
    }
}
