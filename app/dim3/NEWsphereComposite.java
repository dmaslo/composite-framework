package app.dim3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import lib.Vector;
import lib.dim3.Composite3D;
import lib.dim3.Sphere;

/**
 * Generování 3D kompozitní struktury s koulemi
 */
public class NEWsphereComposite {

    public static void main(String args[]) throws IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CloneNotSupportedException, FileNotFoundException, ClassNotFoundException {

        // composite set up
        Composite3D com = new Composite3D();

        com.setStartX(-30.0);
        com.setStartY(-30.0);
        com.setStartZ(-30.0);
        com.setEndX(630.0);
        com.setEndY(330.0);
        com.setEndZ(630.0);

        com.setStartWorkX(0.0);
        com.setStartWorkY(0.0);
        com.setStartWorkZ(0.0);
        com.setEndWorkX(600.0);
        com.setEndWorkY(300.0);
        com.setEndWorkZ(600.0);


        // fill composite with spheres
        Sphere temp = new Sphere();
        double x, y, z;

        for (int i = 0; i < 500000; i++) {
            x = Math.random() * (com.getEndX() - com.getStartX()) + com.getStartX();
            y = Math.random() * (com.getEndY() - com.getStartY()) + com.getStartY();
            z = Math.random() * (com.getEndZ() - com.getStartZ()) + com.getStartZ();

            temp.setRadius(10.0);
            temp.setCenter(new Vector(x, y, z));

            if (com.add(temp.clone())) {
                System.out.println(i);
            }
        }

		com.save("koule.ser");

		com.burn(1.05);

		System.out.println("num = " + com.getObjects().size());
		System.out.println("FF = " + com.gainFF(10000));

		com.export("koule-test.pov", "workarea");


//		com.load("koule.ser");
//		System.out.println("num = " + com.getObjects().size());


//		com.backbone();
//
//		int horici = 0;
//		int kriticke = 0;
//		int cykly = 0;
//		int mrtve = 0;
//		int zelene = 0;
//
//		for (int i = 0; i < com.getObjects().size(); i++) {
//			if (com.getObjects().get(i).getColor().getRed() == 255) {
//				if (com.getObjects().get(i).getColor().getBlue() == 0) {
//					kriticke++;
//					horici++;
//				}
//				else {
//					cykly++;
//					horici++;
//				}
//			}
//			else if (com.getObjects().get(i).getColor().getBlue() == 255) {
//				mrtve++;
//				horici++;
//			}
//			else if (com.getObjects().get(i).getColor().getGreen() == 255) {
//				zelene++;
//			}
//		}
//
//		System.out.println("vsechny = " + com.getObjects().size());
//		System.out.println("kriticke = " + kriticke);
//		System.out.println("zelene = " + zelene);
//		System.out.println("cykly = " + cykly);
//		System.out.println("mrtve = " + mrtve);
//		System.out.println("horici = " + horici);

    }

}
