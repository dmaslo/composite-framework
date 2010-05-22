package app.dim3;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import lib.Vector;
import lib.dim3.Composite3D;
import lib.dim3.Prism;

/**
 * Generování 3D kompozitní struktury s rovnoběžnosteny
 */
public class NEWprismComposite {

    public static void main(String args[]) throws IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        // composite set up
        Composite3D com = new Composite3D();

        com.setStartX(-10.0);
        com.setStartY(-10.0);
        com.setStartZ(-10.0);
        com.setEndX(110.0);
        com.setEndY(60.0);
        com.setEndZ(110.0);

        com.setStartWorkX(0.0);
        com.setStartWorkY(0.0);
        com.setStartWorkZ(0.0);
        com.setEndWorkX(100.0);
        com.setEndWorkY(50.0);
        com.setEndWorkZ(100.0);


        // fungujici cast kodu
        Prism temp = new Prism();
        double x, y, z;
        Vector start, a, b, c, k;

        for (int i = 0; i < 100000; i++) {
            x = Math.random() * (com.getEndX() - com.getStartX()) + com.getStartX();
            y = Math.random() * (com.getEndY() - com.getStartY()) + com.getStartY();
            z = Math.random() * (com.getEndZ() - com.getStartZ()) + com.getStartZ();

            start = new Vector(x, y, z);

            a = Vector.random3D(1.0);
            k = Vector.random3D(3.0);
            b = Vector.crossProduct(a, k);
            c = Vector.crossProduct(a, b);

            b.adjustSize(10.0);
            c.adjustSize(10.0);

            temp = new Prism(start.clone(), a, b, c);

            if (com.add(temp.clone())) {
                System.out.println(i);
            }
        }

        // export composite
        com.export("NEW/planes-1-10-10.pov", "workarea");

        System.out.println("NUM = " + com.getObjects().size());

    }

}
