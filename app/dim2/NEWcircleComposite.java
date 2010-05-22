package app.dim2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import lib.Vector;
import lib.dim2.Composite2D;
import lib.dim2.Circle;

/**
 * Generování 2D kompozitní struktury s kruhy
 */
public class NEWcircleComposite {

    public static void main(String args[]) throws IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CloneNotSupportedException, FileNotFoundException, ClassNotFoundException {

        // composite set up
        Composite2D com = new Composite2D();

        com.setStartX(-30.0);
        com.setStartY(-30.0);
        com.setEndX(230.0);
        com.setEndY(230.0);

        com.setStartWorkX(0.0);
        com.setStartWorkY(0.0);
        com.setEndWorkX(200.0);
        com.setEndWorkY(200.0);


        // fill composite with spheres
        Circle temp = new Circle();
        double x, y, z;

        for (int i = 0; i < 200000; i++) {
            x = Math.random() * (com.getEndX() - com.getStartX()) + com.getStartX();
            y = Math.random() * (com.getEndY() - com.getStartY()) + com.getStartY();

            temp.setRadius(2.5);
            temp.setCenter(new Vector(x, y, 0.0));

            if (com.add(temp.clone())) {
                System.out.println(i);
            }
        }

		
		com.burn(1.025);

		System.out.println("FF = " + com.gainFF(10000));

		com.backbone();
		com.export("backboneCr25.svg", "workarea");


		int horici = 0;
		int kriticke = 0;
		int cykly = 0;
		int mrtve = 0;
		int zelene = 0;

		for (int i = 0; i < com.getObjects().size(); i++) {
			if (com.getObjects().get(i).getColor().getRed() == 255) {
				if (com.getObjects().get(i).getColor().getBlue() == 0) {
					kriticke++;
					horici++;
				}
				else {
					cykly++;
					horici++;
				}
			}
			else if (com.getObjects().get(i).getColor().getBlue() == 255) {
				mrtve++;
				horici++;
			}
			else if (com.getObjects().get(i).getColor().getGreen() == 255) {
				zelene++;
			}
		}

		System.out.println("vsechny = " + com.getObjects().size());
		System.out.println("kriticke = " + kriticke);
		System.out.println("zelene = " + zelene);
		System.out.println("cykly = " + cykly);
		System.out.println("mrtve = " + mrtve);
		System.out.println("horici = " + horici);

    }

}
