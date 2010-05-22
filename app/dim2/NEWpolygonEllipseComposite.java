package app.dim2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import lib.Vector;
import lib.dim2.Composite2D;
import lib.dim2.Polygon;

/**
 * Generování 2D kompozitní struktury s elipsami
 */
public class NEWpolygonEllipseComposite {

    public static void main(String args[]) throws IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CloneNotSupportedException, FileNotFoundException, ClassNotFoundException {

        // composite set up
        Composite2D com = new Composite2D();

        com.setStartX(-10.0);
        com.setStartY(-10.0);
        com.setEndX(160.0);
        com.setEndY(160.0);

        com.setStartWorkX(0.0);
        com.setStartWorkY(0.0);
        com.setEndWorkX(150.0);
        com.setEndWorkY(150.0);


        // fill composite with spheres
        Polygon temp = new Polygon();
        double x, y;
        Vector origin, tmp1, tmp2, tmp3, tmp4, tmp5, tmp6, tmp7, tmp8, tmp9, tmp10, tmp11, tmp12, tmp13, tmp14, tmp15, tmp16, tmp17, tmp18, tmp19, tmp20, tmp21, tmp22, tmp23, tmp24;

		double a = 1.0;

		double main = 5.0;

		// compute FF
		System.out.println("Form Factor = " + ((4.0 * main) / Math.pow(1.5 * (1 + main) - Math.sqrt(main), 2.0)));

		Vector scale = new Vector(main, 1, 0);
		
        for (int i = 0; i < 100000; i++) {
            x = Math.random() * (com.getEndX() - com.getStartX()) + com.getStartX();
            y = Math.random() * (com.getEndY() - com.getStartY()) + com.getStartY();

			Vector rotate = new Vector(0, 0, Math.random() * Math.PI);

            origin = new Vector(x, y, 0);

			Vector A = new Vector(0, a, 0);																// 0, 1
			Vector B = new Vector(a * Math.sin(Math.PI / 12.0), a * Math.cos(Math.PI / 12.0), 0);		// 0.25, 0.96
			Vector C = new Vector(a * 0.5, a * Math.cos(Math.PI / 6.0), 0);								// 0.5, sqrt(3)/2
			Vector D = new Vector(a * Math.sin(Math.PI / 4.0), a * Math.sin(Math.PI / 4.0), 0);			// sqrt(2)/2, sqrt(2)/2
			Vector E = new Vector(a * Math.cos(Math.PI / 6.0), a * 0.5, 0);								// sqrt(3)/2, 0.5
			Vector F = new Vector(a * Math.cos(Math.PI / 12.0), a * Math.sin(Math.PI / 12.0), 0);		// 0.96, 0.25
			Vector G = new Vector(a, 0, 0);																// 1, 0

			Vector oF = new Vector(a * Math.cos(Math.PI / 12.0), -a * Math.sin(Math.PI / 12.0), 0);		// 0.96, -0.25
			Vector oE = new Vector(a * Math.cos(Math.PI / 6.0), -a * 0.5, 0);							// sqrt(3)/2, -0.5
			Vector oD = new Vector(a * Math.sin(Math.PI / 4.0), -a * Math.sin(Math.PI / 4.0), 0);		// sqrt(2)/2, -sqrt(2)/2
			Vector oC = new Vector(a * 0.5, -a * Math.cos(Math.PI / 6.0), 0);							// 0.5, -sqrt(3)/2
			Vector oB = new Vector(a * Math.sin(Math.PI / 12.0), -a * Math.cos(Math.PI / 12.0), 0);		// 0.25, -0.96
			Vector oA = new Vector(0, -a, 0);															// 0, -1


            tmp1  = Vector.sum(origin, Vector.rotate(Vector.scale(A, scale), rotate));
            tmp2  = Vector.sum(origin, Vector.rotate(Vector.scale(B, scale), rotate));
			tmp3  = Vector.sum(origin, Vector.rotate(Vector.scale(C, scale), rotate));
			tmp4  = Vector.sum(origin, Vector.rotate(Vector.scale(D, scale), rotate));
			tmp5  = Vector.sum(origin, Vector.rotate(Vector.scale(E, scale), rotate));
			tmp6  = Vector.sum(origin, Vector.rotate(Vector.scale(F, scale), rotate));
			tmp7  = Vector.sum(origin, Vector.rotate(Vector.scale(G, scale), rotate));

			tmp8  = Vector.sum(origin, Vector.rotate(Vector.scale(oF, scale), rotate));
			tmp9  = Vector.sum(origin, Vector.rotate(Vector.scale(oE, scale), rotate));
			tmp10 = Vector.sum(origin, Vector.rotate(Vector.scale(oD, scale), rotate));
			tmp11 = Vector.sum(origin, Vector.rotate(Vector.scale(oC, scale), rotate));
			tmp12 = Vector.sum(origin, Vector.rotate(Vector.scale(oB, scale), rotate));
			tmp13 = Vector.sum(origin, Vector.rotate(Vector.scale(oA, scale), rotate));

			tmp14 = Vector.sum(origin, Vector.rotate(Vector.opposite(Vector.scale(B, scale)), rotate));
			tmp15 = Vector.sum(origin, Vector.rotate(Vector.opposite(Vector.scale(C, scale)), rotate));
			tmp16 = Vector.sum(origin, Vector.rotate(Vector.opposite(Vector.scale(D, scale)), rotate));
			tmp17 = Vector.sum(origin, Vector.rotate(Vector.opposite(Vector.scale(E, scale)), rotate));
			tmp18 = Vector.sum(origin, Vector.rotate(Vector.opposite(Vector.scale(F, scale)), rotate));
			tmp19 = Vector.sum(origin, Vector.rotate(Vector.opposite(Vector.scale(G, scale)), rotate));
			
            tmp20 = Vector.sum(origin, Vector.rotate(Vector.opposite(Vector.scale(oF, scale)), rotate));
			tmp21 = Vector.sum(origin, Vector.rotate(Vector.opposite(Vector.scale(oE, scale)), rotate));
			tmp22 = Vector.sum(origin, Vector.rotate(Vector.opposite(Vector.scale(oD, scale)), rotate));
			tmp23 = Vector.sum(origin, Vector.rotate(Vector.opposite(Vector.scale(oC, scale)), rotate));
			tmp24 = Vector.sum(origin, Vector.rotate(Vector.opposite(Vector.scale(oB, scale)), rotate));
			
            temp = new Polygon(origin, tmp1, tmp2, tmp3, tmp4, tmp5, tmp6, tmp7, tmp8, tmp9, tmp10, tmp11, tmp12, tmp13, tmp14, tmp15, tmp16, tmp17, tmp18, tmp19, tmp20, tmp21, tmp22, tmp23, tmp24);

            if (com.add(temp.clone())) {
                System.out.println(i);
            }
        }

		com.burn(1.05);

		com.export("struct.svg", "workarea");

		com.backbone();

		com.export("ellBACBONE.svg", "workarea");

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


		double FF = com.gainFF(10000);
		System.out.println("FF = " + FF);

    }

}


