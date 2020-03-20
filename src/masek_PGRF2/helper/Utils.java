package masek_PGRF2.helper;

import transforms.Point3D;

public class Utils {


    static public boolean fastCut(Point3D a) {
        return !(-a.getW() >= a.getX() || -a.getW() >= a.getY() || a.getX() >= a.getW() || a.getY() >= a.getW()
                || 0 >= a.getZ() || a.getZ() >= a.getW());
    }
}
