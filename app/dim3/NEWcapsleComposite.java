package app.dim3;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import lib.Vector;
import lib.dim3.Capsle;
import lib.dim3.Composite3D;

/**
 * Generování 3D kompozitní struktury s kapslovými objekty
 */
public class NEWcapsleComposite {

    public static void main(String args[]) throws IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Composite3D com = new Composite3D();

        com.setStartX(-50.0);
        com.setStartY(-10.0);
        com.setStartZ(-50.0);
        com.setEndX(150.0);
        com.setEndY(60.0);
        com.setEndZ(150.0);

        com.setStartWorkX(0.0);
        com.setStartWorkY(0.0);
        com.setStartWorkZ(0.0);
        com.setEndWorkX(150.0);
        com.setEndWorkY(50.0);
        com.setEndWorkZ(150.0);


        // fungujici cast kodu
        Capsle temp = new Capsle();
        double x, y, z;
        double x1, y1, z1, x2, y2, z2;
        Vector start, end;

        start = new Vector();
        end   = new Vector();

        for (int i = 0; i < 1000000; i++) {
            // lamelar
            x1 = Math.random() * (com.getEndX() - com.getStartX()) + com.getStartX();
            y1 = com.getStartY();
            z1 = Math.random() * (com.getEndZ() - com.getStartZ()) + com.getStartZ();

            start.set(x1, y1, z1);

            //x2 = x1 + 0.5 * (Math.random() -0.5);
            x2 = x1 + 5.0 * (Math.random() -0.5) + 50.0;
            y2 = com.getEndY();
            z2 = z1 + 5.0 * (Math.random() -0.5);

            end.set(x2, y2, z2);

            temp = new Capsle(start.clone(), end.clone(), 2.0);

            if (com.add(temp.clone())) {
                System.out.println(i);
            }
        }

        // export composite
        com.export("NEW/tubes-slanted.pov", "workarea");

        System.out.println("NUM = " + com.getObjects().size());

    }

}
