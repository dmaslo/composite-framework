package app.dim2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import lib.Vector;
import lib.dim2.Composite2D;
import lib.dim2.Polygon;

/**
 * Generování 2D kompozitní struktury s polygony
 */
public class NEWsquareComposite {

    public static void main(String args[]) throws IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CloneNotSupportedException, FileNotFoundException, ClassNotFoundException {

        // composite set up
        Composite2D com = new Composite2D();

        com.setStartX(-20.0);
        com.setStartY(-20.0);
        com.setEndX(220.0);
        com.setEndY(220.0);

        com.setStartWorkX(0.0);
        com.setStartWorkY(0.0);
        com.setEndWorkX(200.0);
        com.setEndWorkY(200.0);


        // fill composite with spheres
        Polygon temp = new Polygon();
        double x, y;
        Vector origin, tmp1, tmp2, tmp3, tmp4, tmp5, tmp6;

		double a = 5.0;
		double aHalf = a / 2.0;
		double aSqrt3Half = (Math.sqrt(3.0) / 2.0) * a;

        for (int i = 0; i < 300000; i++) {
            x = Math.random() * (com.getEndX() - com.getStartX()) + com.getStartX();
            y = Math.random() * (com.getEndY() - com.getStartY()) + com.getStartY();

			Vector rotate = new Vector(0, 0, Math.random() * Math.PI);

            origin = new Vector(x, y, 0);

			double shape = Math.random();

			// hexagon
			if (shape < 0.33333333333) {
				tmp1 = Vector.sum(origin, Vector.rotate(new Vector(a, 0, 0), rotate));
				tmp2 = Vector.sum(origin, Vector.rotate(new Vector(aHalf, -aSqrt3Half, 0), rotate));
				tmp3 = Vector.sum(origin, Vector.rotate(new Vector(-aHalf, -aSqrt3Half, 0), rotate));
				tmp4 = Vector.sum(origin, Vector.rotate(new Vector(-a, 0, 0), rotate));
				tmp5 = Vector.sum(origin, Vector.rotate(new Vector(-aHalf, aSqrt3Half, 0), rotate));
				tmp6 = Vector.sum(origin, Vector.rotate(new Vector(aHalf, aSqrt3Half, 0), rotate));
				
				temp = new Polygon(origin.clone(), tmp1, tmp2, tmp3, tmp4, tmp5, tmp6);
			}

			// triangle
			else if (shape < 0.6666666666666) {
				tmp1 = Vector.sum(origin, Vector.rotate(new Vector(0, 4*2, 0), rotate));
				tmp2 = Vector.sum(origin, Vector.rotate(new Vector(2.88675135*2, -1.0*2, 0), rotate));
				tmp3 = Vector.sum(origin, Vector.rotate(new Vector(-2.88675135*2, -1.0*2, 0), rotate));
				
				temp = new Polygon(origin.clone(), tmp1, tmp2, tmp3);
			}

			// square
			else {
				tmp1 = Vector.sum(origin, Vector.rotate(new Vector(-a, -a, 0), rotate));
				tmp2 = Vector.sum(origin, Vector.rotate(new Vector(-a, a, 0), rotate));
				tmp3 = Vector.sum(origin, Vector.rotate(new Vector(a, a, 0), rotate));
				tmp4 = Vector.sum(origin, Vector.rotate(new Vector(a, -a, 0), rotate));

				temp = new Polygon(origin.clone(), tmp1, tmp2, tmp3, tmp4);
			}

            //if (com.addPolygon((Polygon) temp.clone())) {
			if (com.add(temp.clone())) {
                System.out.println(i);
            }
        }

        com.burn(1.05);
		
        com.backbone();

		com.export("mixed.svg", "workarea");

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

