package app.dim3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import lib.Vector;
import lib.dim3.Composite3D;
import lib.dim3.Spheroid;

/**
 * Generování 3D kompozitní struktury se sféroidy
 */
public class NEWspheroidComposite {

    public static void main(String args[]) throws IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CloneNotSupportedException, FileNotFoundException, ClassNotFoundException {

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


        // fill composite with spheres
        Spheroid temp = new Spheroid();
        double x, y, z;

        for (int i = 0; i < 20000; i++) {
            x = Math.random() * (com.getEndX() - com.getStartX()) + com.getStartX();
            y = Math.random() * (com.getEndY() - com.getStartY()) + com.getStartY();
            z = Math.random() * (com.getEndZ() - com.getStartZ()) + com.getStartZ();

			temp = new Spheroid(new Vector(x, y, z), 5, 5, 2, Math.PI * Math.random(), Math.PI * Math.random(), Math.PI * Math.random());

            if (com.add(temp.clone())) {
                System.out.println(i);
            }
        }

		com.burn(1.05);
		System.out.println("BURNED");

		com.backbone();

		com.export("ELLall.pov", "workarea");
		com.export("ELLcontacts.pov", "burn");
		com.export("ELLcluster.pov", "cluster");
		com.export("ELLbackbone.pov", "live");

		System.out.println("FF = " + com.gainFF(10000));

    }

}
