package lib;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * <h1>Knihovní třída pro práci se seznami vertexů - trojúhelníků</h1>
 * <p>
 * Tato třída reprezentuje meshe, jak jsou známy z počítačové grafiky.
 * Mesh tedy představuje trojúhelníkovou síť.
 * </p>
 * <p>
 * Třída nabízí 3 možnosti uspořádání vertexů do meshe. Třída proto nenabízí
 * jiné mechanizmy pro práci s topologií meshe.
 * </p>
 *
 * @author Daniel Máslo <daniel.maslo@gmail.com>
 * @since JDK 1.5
 * @version 2.0
 */
public class Mesh implements Serializable, Cloneable {

    /** možnosti typu meshe */
    public enum type {
        /** trojúhelník reprezentují vždy tři vektory po sobě */
        TRIANGLES,

        /** trojúhelník reprezentuje vždy vektor a jeho dva předchůdci */
        STRIPE,

        /** trojúhelník reprezentuje vždy vektor, jeho předchůdce a první vektor v seznamu */
        FAN
    };


    // proměnné
    /** seznam trojúhelníků - vertexů */
    private ArrayList<Vector> verts = new ArrayList<Vector>();

    /** typ meshe */
    private Mesh.type type;

    /** barva meshe */
    private Color color = new Color(178,153,0);


    // gettery a settery
    public Mesh.type getType() {
        return type;
    }

    public void setType(Mesh.type type) {
        this.type = type;
    }

    public ArrayList<Vector> getVerts() {
        return verts;
    }

    public void setVerts(ArrayList<Vector> verts) {
        this.verts = (ArrayList<Vector>) verts.clone();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    // konstruktory
    /**
     * prázdný (výchozí) konstruktor
     */
    public Mesh() {
        
    }

    /**
     * plný konstruktor - výchozí barva
     *
     * @param Mesh.type typ meshe
     * @param ArrayList&ltVector&gt; vertexy
     */
    public Mesh(Mesh.type type_, ArrayList<Vector> verts_) {
        this.type  = type_;
        this.verts = (ArrayList<Vector>) verts_.clone();
    }

    /**
     * plný konstruktor
     *
     * @param Mesh.type typ meshe
     * @param ArrayList&ltVector&gt; vertexy
     * @param Color barva meshe
     */
    public Mesh(Mesh.type type_, ArrayList<Vector> verts_, Color color_) {
        this.type  = type_;
        this.verts = (ArrayList<Vector>) verts_.clone();
        this.color = color_;
    }


    // veřejné metody
    public String export(String fileType_) {
        ArrayList<Triangle> faces = this.gainTrianglesList();
        StringBuffer res = new StringBuffer();

        if (fileType_.toLowerCase().endsWith("pov")) {
            res.append("mesh {\n");

            for (int i = 0; i < faces.size(); i++) {
                res.append("\ttriangle {\n");
                res.append("\t\t" + faces.get(i).getA().export("pov") + ", " + faces.get(i).getB().export("pov") + ", " + faces.get(i).getC().export("pov") + "\n");
                res.append("\t}\n");
            }

            res.append("\ttexture {\n");
            res.append("\t\tpigment {\n");
            res.append("\t\t\tcolor rgb <"+((double) this.getColor().getRed()/255.0)+", "+((double) this.getColor().getGreen()/255.0)+", "+((double) this.getColor().getBlue()/255.0)+">\n");
            res.append("\t\t}\n");
            res.append("\t}\n");
            res.append("\t\tfinish {phong 1}\n");

            res.append("} \n\n");
        }

        return(res.toString());
    }

    /**
     * počet vertexů v meshi
     *
     * @return int : počet vertexů v meshi
     */
    public int gainVertsCount() {
        return(this.verts.size());
    }

    /**
     * počet trojúhelníku v meshi
     *
     * @return int : počet trojúhelníku v meshi
     */
    public int gainTrianglesCount() {
        if (this.type.equals(type.TRIANGLES)) {
            return(this.gainVertsCount() / 3);
        }
        else if (this.type.equals(type.FAN)) {
            return(this.gainVertsCount() - 2);
        }
        else if (this.type.equals(type.STRIPE)) {
            return(this.gainVertsCount() - 2);
        }
        else {
            throw new RuntimeException("Neznámý typ meshe");
        }
    }

    /**
     * pole trojúhelníků z meshe - spojení topologie s geometrií
     *
     * @return Triangle[] : pole trojúhelníků
     */
    public Triangle[] gainTrianglesArray() {
        Triangle[] res = new Triangle[this.gainTrianglesCount()];

        if (this.type.equals(type.TRIANGLES)) {
            for (int i = 0; i < this.verts.size()-2; i += 3) {
                res[i] = new Triangle(this.verts.get(i), this.verts.get(i + 1), this.verts.get(i + 2));
            }
        }
        else if (this.type.equals(type.FAN)) {
            for (int i = 1; i < this.verts.size()-1; i++) {
                res[i] = new Triangle(this.verts.get(0), this.verts.get(i), this.verts.get(i + 1));
            }
        }
        else if (this.type.equals(type.STRIPE)) {
            for (int i = 0; i < this.verts.size()-2; i++) {
                res[i] = new Triangle(this.verts.get(i), this.verts.get(i + 1), this.verts.get(i + 2));
            }
        }
        else {
            throw new RuntimeException("Neznámý typ meshe");
        }

        return(res);
    }

    /**
     * list trojúhelníků z meshe - spojení topologie s geometrií
     *
     * @return ArrayList&lt;Triangle&gt; : list trojúhelníků
     */
    public ArrayList<Triangle> gainTrianglesList() {
        ArrayList<Triangle> res = new ArrayList<Triangle>(this.gainTrianglesCount());

        if (this.type.equals(type.TRIANGLES)) {
            for (int i = 0; i < this.verts.size()-2; i += 3) {
                res.add(new Triangle(this.verts.get(i), this.verts.get(i + 1), this.verts.get(i + 2)));
            }
        }
        else if (this.type.equals(type.FAN)) {
            for (int i = 1; i < this.verts.size()-1; i++) {
                res.add(new Triangle(this.verts.get(0), this.verts.get(i), this.verts.get(i + 1)));
            }
        }
        else if (this.type.equals(type.STRIPE)) {
            for (int i = 0; i < this.verts.size()-2; i++) {
                res.add(new Triangle(this.verts.get(i), this.verts.get(i + 1), this.verts.get(i + 2)));
            }
        }
        else {
            throw new RuntimeException("Neznámý typ meshe");
        }

        return(res);
    }

    /**
     * vzdálenost dvou meshů v prostoru
     *
     * @param Mesh 1. mesh
     * @param Mesh 2. mesh
     *
     * @return Vector : vektor, představující nejkratší vzdálenost mezi trojúhelníky
     */
    public static Vector distance(Mesh m1_, Mesh m2_) {
        Vector res  = new Vector(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
        Vector temp = new Vector();

        ArrayList<Triangle> m1t = m1_.gainTrianglesList();
        ArrayList<Triangle> m2t = m2_.gainTrianglesList();

        int m1vertsCount = m1t.size();
        int m2vertsCount = m2t.size();

        for (int i = 0; i < m1vertsCount; i++) {
            for (int j = 0; j < m2vertsCount; j++) {
                temp = Triangle.distance(m1t.get(i), m2t.get(j));

                if (temp.gainSizeSquared() < res.gainSizeSquared()) {
                    res = temp.clone();
                }
            }
        }

        return(res);
    }

    /**
     * kontrola kolize dvou meshů
     *
     * @param Mesh 1. mesh - <strong>nebude změněn</strong>
     * @param Mesh 2. mesh - <strong>nebude změněn</strong>
     *
     * @return boolean : true, pokud je kolize, jinak false
     */
    public static boolean isOverlap(Mesh m1_, Mesh m2_) {
        ArrayList<Triangle> m1t = m1_.gainTrianglesList();
        ArrayList<Triangle> m2t = m2_.gainTrianglesList();

        int m1vertsCount = m1t.size();
        int m2vertsCount = m2t.size();

        for (int i = 0; i < m1vertsCount; i++) {
            for (int j = 0; j < m2vertsCount; j++) {
                if (Triangle.isOverlap(m1t.get(i), m2t.get(j))) {
                    return(true);
                }
            }
        }

        return(false);
    }

}
