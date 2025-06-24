package javatry.privates.base.colorbox;

import java.util.ArrayList;
import java.util.List;

import javatry.privates.base.colorbox.color.BoxColor;
import javatry.privates.base.colorbox.size.BoxSize;
import javatry.privates.base.colorbox.space.BoxSpace;

public abstract class AbstractColorBox implements ColorBox {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final BoxColor color;
    private final List<BoxSpace> spaceList = new ArrayList<BoxSpace>();
    private BoxSize size = new BoxSize(0, 0, 0); // as default (auto-calculated when a space added)

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractColorBox(BoxColor color) {
        this.color = color;
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    public String toString() {
        return "{" + color + ", " + size + ", space=" + spaceList.size() + "}";
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public BoxColor getColor() {
        return color;
    }

    public BoxSize getSize() {
        return size;
    }

    public int getSpaceCount() {
        return spaceList.size();
    }

    public List<BoxSpace> getSpaceList() {
        return spaceList;
    }

    protected void addSpace(BoxSpace boxSpace) {
        spaceList.add(boxSpace);
        calculateWholeSize();
    }

    protected void calculateWholeSize() {
        int edge = BoxFixedSpec.EDGE_SIZE;
        int sumHeight = 0;
        int maxWidth = 0;
        int maxDepth = 0;
        for (BoxSpace space : spaceList) {
            BoxSize size = space.getSize();
            sumHeight = sumHeight + size.getHeight();
            int width = size.getWidth();
            if (width > maxWidth) {
                maxWidth = width;
            }
            int depth = size.getDepth();
            if (depth > maxDepth) {
                maxDepth = depth;
            }
        }
        // +---+
        // |   |
        // |---|
        // |   |
        // |---|
        // |   |
        // +---+
        int heightEdge = edge * (spaceList.size() + 1);
        int widthEdge = edge * 2;
        int depthEdge = edge;
        size = new BoxSize(sumHeight + heightEdge, maxWidth + widthEdge, maxDepth + depthEdge);
    }
}
