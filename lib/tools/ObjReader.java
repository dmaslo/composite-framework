package lib.tools;

import lib.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <h1>Knihovní třída pro čtení .obj souborů</h1>
 *
 * .obj soubory slouží jako mezisoftwarový standardní formát pro
 * přenos 3D dat. Obsahují seznam vertexů, vertexových normál, texturových souřadnic
 * a také seznam faců.
 *
 * @author Daniel Máslo <daniel.maslo@gmail.com>
 * @version 2.0
 */
public class ObjReader {

	/**
	 * načtení sítě trojúhelníků (faců) z .obj souboru
	 *
	 * @param String jméno souboru
	 *
	 * @return ArrayList&lt;Triangle&gt; : seznam trojúhelníků (faců)
	 *
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static ArrayList<Triangle> gainFaces(String fileName_) throws FileNotFoundException, IOException {
		ArrayList<Triangle> res = new ArrayList<Triangle>();
		ArrayList<Vector> verts = new ArrayList<Vector>();
		verts.add(new Vector()); // fix indexes

		double x, y, z;
		int fA, fB, fC;

		String record;
        String[] tokens = new String[3];

        File f = new File(fileName_);
        FileInputStream fis = new FileInputStream(f);
        BufferedInputStream bis = new BufferedInputStream(fis);
        DataInputStream dis = new DataInputStream(bis);

        while ((record = dis.readLine()) != null) {

			// load vertexes
            if (record.startsWith("v ")) {
                tokens = record.split(" ");

                x = Double.parseDouble(tokens[1]);
                y = Double.parseDouble(tokens[2]);
				z = Double.parseDouble(tokens[3]);

				verts.add(new Vector(x, y, z));
            }

			// load faces
            if (record.startsWith("f ")) {
                tokens = record.split(" ");

                fA = Integer.parseInt(tokens[1].split("/")[0]);
                fB = Integer.parseInt(tokens[2].split("/")[0]);
				fC = Integer.parseInt(tokens[3].split("/")[0]);

				res.add(new Triangle(verts.get(fA), verts.get(fB), verts.get(fC)));
            }

        }

        dis.close();

		return(res);
	}

}
