package lib.dim3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import lib.Mesh;
import lib.Vector;

/**
 * <h1>Knihovní třída představující rovnoběžnostěny</h1>
 * <p>
 * Rovnoběžnostěn je ve 3D dán počátkem a třemi vektory podél hran z něj vybíhajících
 * </p>
 *
 * @author Daniel Máslo <daniel.maslo@gmail.com>
 * @since JDK 1.5
 * @version 2.0
 */
public class Prism extends Object3D implements Serializable, Cloneable {

    // proměnné
    /**
     * unikátní id pro serializaci
     * zrcadlí verzi třídy/objektu
     */
    private static final long serialVersionUID = 2L;

    /** počátek rovnoběžnostěnu */
    private Vector origin;

    /** 1. hrana */
    private Vector a;

    /** 2. hrana */
    private Vector b;

    /** 3. hrana */
    private Vector c;


    // gettery a settery
    public Vector getA() {
        return a;
    }

    public void setA(Vector a) {
        this.a = a.clone();
    }

    public Vector getB() {
        return b;
    }

    public void setB(Vector b) {
        this.b = b.clone();
    }

    public Vector getC() {
        return c;
    }

    public void setC(Vector c) {
        this.c = c.clone();
    }

    public Vector getOrigin() {
        return origin;
    }

    public void setOrigin(Vector origin) {
        this.origin = origin.clone();
    }


    // překryté metody
    @Override
    public Object3D clone() {
        Prism res = new Prism();

        res.origin = this.origin.clone();
        res.a      = this.a.clone();
        res.b      = this.b.clone();
        res.c      = this.c.clone();

        return(res);
    }

    @Override
    public String export(String fileType_) {
        StringBuffer res = new StringBuffer();

        if (fileType_.toLowerCase().endsWith("pov")) {
            ArrayList<Vector> verts = new ArrayList<Vector>();


            verts.add(this.origin);
            verts.add(Vector.sum(this.origin, this.a));
            verts.add(Vector.sum(this.origin, this.b));
            verts.add(Vector.sum(this.origin, this.c));
            verts.add(Vector.sum(this.origin, this.a));

            Mesh m1a = new Mesh(Mesh.type.FAN, verts);
            verts.clear();

            verts.add(Vector.sum(this.origin, this.b, this.c));
            verts.add(Vector.sum(this.origin, this.b));
            verts.add(Vector.sum(this.origin, this.c));
            verts.add(Vector.sum(this.origin, this.a, this.b, this.c));
            verts.add(Vector.sum(this.origin, this.b));

            Mesh m1b = new Mesh(Mesh.type.FAN, verts);
            verts.clear();

            verts.add(Vector.sum(this.origin, this.a, this.b));
            verts.add(Vector.sum(this.origin, this.a));
            verts.add(Vector.sum(this.origin, this.b));
            verts.add(Vector.sum(this.origin, this.a, this.b, this.c));
            verts.add(Vector.sum(this.origin, this.a));

            Mesh m1c = new Mesh(Mesh.type.FAN, verts);
            verts.clear();

            verts.add(Vector.sum(this.origin, this.a, this.c));
            verts.add(Vector.sum(this.origin, this.a));
            verts.add(Vector.sum(this.origin, this.c));
            verts.add(Vector.sum(this.origin, this.a, this.b, this.c));
            verts.add(Vector.sum(this.origin, this.a));

            Mesh m1d = new Mesh(Mesh.type.FAN, verts);
            verts.clear();

            res.append(m1a.export("pov"));
            res.append(m1b.export("pov"));
            res.append(m1c.export("pov"));
            res.append(m1d.export("pov"));
        }

        return(res.toString());
    }

    /**
     * přenásobují se všechny vektory, určující rovnoběžnostěn
     * vektor origin zůstáva na místě
     */
    // TODO - zachovávat střed a ne origin
    @Override
    public void blow(double... blowCoefs_) {
        this.a.dotProduct(blowCoefs_[0]);
        this.b.dotProduct(blowCoefs_[0]);
        this.c.dotProduct(blowCoefs_[0]);
    }

	@Override
    public void rotate(Vector rotate_) {
		this.a.add(Vector.opposite(this.origin));
		this.b.add(Vector.opposite(this.origin));
		this.c.add(Vector.opposite(this.origin));

		this.a.rotate(rotate_);
		this.b.rotate(rotate_);
		this.c.rotate(rotate_);

		this.a.add(this.origin);
		this.b.add(this.origin);
		this.c.add(this.origin);
	}

	@Override
	public void translate(Vector translate_) {
		this.origin.add(translate_);
		this.a.add(translate_);
		this.b.add(translate_);
		this.c.add(translate_);
	}

    @Override
    public boolean isIn(Vector v_) {
        // Cramerovo pravidlo
        double aDeter = Vector.determinate(v_, this.b, this.c);
        double bDeter = Vector.determinate(this.a, v_, this.c);
        double cDeter = Vector.determinate(this.a, this.b, v_);
        double dDeter = Vector.determinate(this.a, this.b, this.c);

        if (dDeter == 0) {
            if ( (aDeter == 0) && (bDeter == 0) && (cDeter == 0) ) {
                // závislé vektory
                return(false);
            }
            else {
                // řešení neexistuje
                return(false);
            }
        }
        // řešení
        else {
            double x = aDeter / dDeter;
            double y = bDeter / dDeter;
            double z = cDeter / dDeter;

            if ( (x <= 1.0) && (x >= 0.0) ) {
                if ( (y <= 1.0) && (y >= 0.0) ) {
                    if ( (z <= 1.0) && (z >= 0.0) ) {
                        return(true);
                    }
                    else {
                        return(false);
                    }
                }
                else {
                    return(false);
                }
            }
            else {
                return(false);
            }
        }
    }

    @Override
    public boolean isOut(Vector v_) {
        return(!this.isIn(v_));
    }

    @Override
    public double gainMinX() {
        ArrayList<Double> xVerts = new ArrayList<Double>();

        for (int i = 0; i < this.gainVerts().size(); i++) {
            xVerts.add(this.gainVerts().get(i).getX());
        }

        return(Collections.min(xVerts));
    }

    @Override
    public double gainMinY() {
        ArrayList<Double> yVerts = new ArrayList<Double>();

        for (int i = 0; i < this.gainVerts().size(); i++) {
            yVerts.add(this.gainVerts().get(i).getY());
        }

        return(Collections.min(yVerts));
    }

    @Override
    public double gainMinZ() {
        ArrayList<Double> zVerts = new ArrayList<Double>();

        for (int i = 0; i < this.gainVerts().size(); i++) {
            zVerts.add(this.gainVerts().get(i).getZ());
        }

        return(Collections.min(zVerts));
    }

    @Override
    public double gainMaxX() {
        ArrayList<Double> xVerts = new ArrayList<Double>();

        for (int i = 0; i < this.gainVerts().size(); i++) {
            xVerts.add(this.gainVerts().get(i).getX());
        }

        return(Collections.max(xVerts));
    }

    @Override
    public double gainMaxY() {
        ArrayList<Double> yVerts = new ArrayList<Double>();

        for (int i = 0; i < this.gainVerts().size(); i++) {
            yVerts.add(this.gainVerts().get(i).getY());
        }

        return(Collections.max(yVerts));
    }

    @Override
    public double gainMaxZ() {
        ArrayList<Double> zVerts = new ArrayList<Double>();

        for (int i = 0; i < this.gainVerts().size(); i++) {
            zVerts.add(this.gainVerts().get(i).getZ());
        }

        return(Collections.max(zVerts));
    }


    // konstruktory
    /**
     * prázdný (výchozí) konstruktor
     */
    public Prism() {
        super();
    }

    /**
     * plný konstruktor
     *
     * @param Vector : střed koule - <strong>nebude změněn</strong>
     * @param double : poloměr koule
     */
    public Prism(Vector origin_, Vector a_, Vector b_, Vector c_) {
        super();

        this.origin = origin_.clone();
        this.a = a_.clone();
        this.b = b_.clone();
        this.c = c_.clone();
    }


    // veřejné metody
    /**
     * vrcholy rovnoběžnostěnu
     *
     * @return ArrayList&lt;Vector&gt; : vrcholy rovnoběžnostěnu
     */
    public ArrayList<Vector> gainVerts() {
        ArrayList<Vector> res = new ArrayList<Vector>();

        res.add(this.origin.clone());
        res.add(Vector.sum(this.origin, this.a));
        res.add(Vector.sum(this.origin, this.b));
        res.add(Vector.sum(this.origin, this.c));
        res.add(Vector.sum(this.origin, this.a, this.b));
        res.add(Vector.sum(this.origin, this.a, this.c));
        res.add(Vector.sum(this.origin, this.b, this.c));
        res.add(Vector.sum(this.origin, this.a, this.b, this.c));
        
        return(res);
    }

    /**
     * střed rovnoběžnostěnu
     *
     * @return Vector : střed rovnoběžnostěnu
     */
    public Vector gainCenter() {
        return(Vector.sum(this.origin, Vector.dotProduct(0.5, this.a), Vector.dotProduct(0.5, this.b), Vector.dotProduct(0.5, this.c)));
    }

	/**
     * kontrola kolize dvou rovnoběžnostěnů
     *
     * @param Prism objekt - <strong>nebude změněn</strong>
     * @param Prism objekt - <strong>nebude změněn</strong>
     *
     * @return boolean : true, pokud objekty kolidují, jinak false
     */
    public static boolean isOverlap(Prism p1_, Prism p2_) {
        // bounding sphere
        Sphere bs1 = new Sphere(p1_.gainCenter(), Vector.sum(Vector.dotProduct(0.5, p1_.a), Vector.dotProduct(0.5, p1_.b), Vector.dotProduct(0.5, p1_.c)).gainSize());
        Sphere bs2 = new Sphere(p2_.gainCenter(), Vector.sum(Vector.dotProduct(0.5, p2_.a), Vector.dotProduct(0.5, p2_.b), Vector.dotProduct(0.5, p2_.c)).gainSize());

        if (!Sphere.isOverlap(bs1, bs2)) {            
            return(false);
        }

        ArrayList<Vector> verts = new ArrayList<Vector>();


        verts.add(p1_.origin);
        verts.add(Vector.sum(p1_.origin, p1_.a));
        verts.add(Vector.sum(p1_.origin, p1_.b));
        verts.add(Vector.sum(p1_.origin, p1_.c));
        verts.add(Vector.sum(p1_.origin, p1_.a));

        Mesh m1a = new Mesh(Mesh.type.FAN, verts);
        verts.clear();

        verts.add(Vector.sum(p1_.origin, p1_.b, p1_.c));
        verts.add(Vector.sum(p1_.origin, p1_.b));
        verts.add(Vector.sum(p1_.origin, p1_.c));
        verts.add(Vector.sum(p1_.origin, p1_.a, p1_.b, p1_.c));
        verts.add(Vector.sum(p1_.origin, p1_.b));

        Mesh m1b = new Mesh(Mesh.type.FAN, verts);
        verts.clear();

        verts.add(Vector.sum(p1_.origin, p1_.a, p1_.b));
        verts.add(Vector.sum(p1_.origin, p1_.a));
        verts.add(Vector.sum(p1_.origin, p1_.b));
        verts.add(Vector.sum(p1_.origin, p1_.a, p1_.b, p1_.c));
        verts.add(Vector.sum(p1_.origin, p1_.a));

        Mesh m1c = new Mesh(Mesh.type.FAN, verts);
        verts.clear();

        verts.add(Vector.sum(p1_.origin, p1_.a, p1_.c));
        verts.add(Vector.sum(p1_.origin, p1_.a));
        verts.add(Vector.sum(p1_.origin, p1_.c));
        verts.add(Vector.sum(p1_.origin, p1_.a, p1_.b, p1_.c));
        verts.add(Vector.sum(p1_.origin, p1_.a));

        Mesh m1d = new Mesh(Mesh.type.FAN, verts);
        verts.clear();


        verts.add(p2_.origin);
        verts.add(Vector.sum(p2_.origin, p2_.a));
        verts.add(Vector.sum(p2_.origin, p2_.b));
        verts.add(Vector.sum(p2_.origin, p2_.c));
        verts.add(Vector.sum(p2_.origin, p2_.a));

        Mesh m2a = new Mesh(Mesh.type.FAN, verts);
        verts.clear();

        verts.add(Vector.sum(p2_.origin, p2_.b, p2_.c));
        verts.add(Vector.sum(p2_.origin, p2_.b));
        verts.add(Vector.sum(p2_.origin, p2_.c));
        verts.add(Vector.sum(p2_.origin, p2_.a, p2_.b, p2_.c));
        verts.add(Vector.sum(p2_.origin, p2_.b));

        Mesh m2b = new Mesh(Mesh.type.FAN, verts);
        verts.clear();

        verts.add(Vector.sum(p2_.origin, p2_.a, p2_.b));
        verts.add(Vector.sum(p2_.origin, p2_.a));
        verts.add(Vector.sum(p2_.origin, p2_.b));
        verts.add(Vector.sum(p2_.origin, p2_.a, p2_.b, p2_.c));
        verts.add(Vector.sum(p2_.origin, p2_.a));

        Mesh m2c = new Mesh(Mesh.type.FAN, verts);
        verts.clear();

        verts.add(Vector.sum(p2_.origin, p2_.a, p2_.c));
        verts.add(Vector.sum(p2_.origin, p2_.a));
        verts.add(Vector.sum(p2_.origin, p2_.c));
        verts.add(Vector.sum(p2_.origin, p2_.a, p2_.b, p2_.c));
        verts.add(Vector.sum(p2_.origin, p2_.a));

        Mesh m2d = new Mesh(Mesh.type.FAN, verts);
        verts.clear();


        // overlap test
        if ( (Mesh.isOverlap(m1a, m2a)) || (Mesh.isOverlap(m1a, m2b)) || (Mesh.isOverlap(m1a, m2c)) || (Mesh.isOverlap(m1a, m2d)) ) {
            return(true);
        }
        if ( (Mesh.isOverlap(m1b, m2a)) || (Mesh.isOverlap(m1b, m2b)) || (Mesh.isOverlap(m1b, m2c)) || (Mesh.isOverlap(m1b, m2d)) ) {
            return(true);
        }
        if ( (Mesh.isOverlap(m1c, m2a)) || (Mesh.isOverlap(m1c, m2b)) || (Mesh.isOverlap(m1c, m2c)) || (Mesh.isOverlap(m1c, m2d)) ) {
            return(true);
        }
        if ( (Mesh.isOverlap(m1d, m2a)) || (Mesh.isOverlap(m1d, m2b)) || (Mesh.isOverlap(m1d, m2c)) || (Mesh.isOverlap(m1d, m2d)) ) {
            return(true);
        }

        return(false);
    }

	/**
     * kontrola kolize dvou objektů - externě volané matoda z předka
     *
     * @param Object2D objekt - <strong>nebude změněn</strong>
     * @param Object2D objekt - <strong>nebude změněn</strong>
     *
     * @return boolean : true, pokud objekty kolidují, jinak false
     */
    public static boolean isOverlap(Object3D o1_, Object3D o2_) {
        if ( (o1_.getClass().equals(Prism.class)) && (o2_.getClass().equals(Prism.class)) ) {
            Prism p1_ = (Prism) o1_;
            Prism p2_ = (Prism) o2_;

            return(Prism.isOverlap(p1_, p2_));
        }
        else {
            throw new UnsupportedOperationException("Neplatné objekty.");
        }
    }

}

