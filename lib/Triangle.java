package lib;

import com.jme.intersection.Intersection;
import com.jme.math.Plane;
import com.jme.math.Vector3f;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * <h1>Knihovní třída pro práci s trojúhelníky ve 3D prostoru</h1>
 * <p>
 * Trojúhelník je dán třemi body, které neleží v přímce,
 * tato podmínka je testována v každém setteru a v konstuktorech.
 * V přípdě nesplnění této podmínky je volána výjimka.
 * </p>
 *
 * @author Daniel Máslo <daniel.maslo@gmail.com>
 * @since JDK 1.5
 * @version 2.0
 */

public class Triangle implements Serializable, Cloneable {

    // proměnné
    /**
     * unikátní id pro serializaci
     * zrcadlí verzi třídy/objektu
     */
    private static final long serialVersionUID = 2L;

    /** bod A */
    private Vector a;

    /** bod B */
    private Vector b;

    /** bod C */
    private Vector c;

    
    // konstanty
    /** numerická nula <= nepřesná reprezentace čísel */
    public static final double ZERO = 0.0000000000000001;


    // gettery a settery
    public Vector getA() {
        return a;
    }

    public void setA(Vector a) {
        if (isTriangle(a, this.b, this.c)) {
            this.a = a;
        }
        else {
            throw new IllegalArgumentException("Není splněna trojúhelníková nerovnost");
        }
    }

    public Vector getB() {
        return b;
    }

    public void setB(Vector b) {
        if (isTriangle(this.a, b, this.c)) {
            this.b = b;
        }
        else {
            throw new IllegalArgumentException("Není splněna trojúhelníková nerovnost");
        }
    }

    public Vector getC() {
        return c;
    }

    public void setC(Vector c) {
        if (isTriangle(this. a, this.b, c)) {
            this.c = c;
        }
        else {
            throw new IllegalArgumentException("Není splněna trojúhelníková nerovnost");
        }
    }


    // konstruktory
    /**
     * prázdný (výchozí) konstruktor
     */
    public Triangle() {

    }

    /**
     * bodový konstruktor
     *
     * @param Vector bod A
     * @param Vector bod B
     * @param Vector bod C
     */
    public Triangle(Vector a_, Vector b_, Vector c_) {
        if (isTriangle(a_, b_, c_)) {
            this.a = a_.clone();
            this.b = b_.clone();
            this.c = c_.clone();
        }
        else {            
            throw new IllegalArgumentException("Není splněna trojúhelníková nerovnost");
        }
    }


    // překryté metody
    @Override
    public Triangle clone() {
        Triangle res = new Triangle();

        res.a = this.a.clone();
        res.b = this.b.clone();
        res.c = this.c.clone();

        return(res);
    }

    @Override
    public String toString() {
        return("Triangle(" + this.a + ", " + this.b + ", " + this.c + ")");
    }


    // veřejné metody
    /**
     * nastavení souřadnic trojúhelníku
     *
     * @param Vector vrchol A - <strong>nebude změněn</strong>
     * @param Vector vrchol B - <strong>nebude změněn</strong>
     * @param Vector vrchol C - <strong>nebude změněn</strong>
     */
    public void set(Vector a_, Vector b_, Vector c_) {
        this.a = a_.clone();
        this.b = b_.clone();
        this.c = c_.clone();
    }

    /**
     * vzdálenost dvou trojúhelníků v prostoru
     *
     * @param Triangle 1. trojúhelník
     * @param Triangle 2. trojúhelník
     *
     * @return Vector : vektor, představující nejkratší vzdálenost mezi trojúhelníky
     */
    public static Vector distance(Triangle t1_, Triangle t2_) {
        if (Triangle.isOverlap(t1_, t2_)) {
            return(new Vector(0, 0, 0));
        }

        Vector res = new Vector();        
        Vector temp = new Vector();
        double resSize = Double.MAX_VALUE;

        // rovina trojúhelníku t1_
        Plane p1 = new Plane();
        p1.setPlanePoints(new Vector3f((float) t1_.getA().getX(), (float) t1_.getA().getY(), (float) t1_.getA().getZ()), new Vector3f((float) t1_.getB().getX(), (float) t1_.getB().getY(), (float) t1_.getB().getZ()), new Vector3f((float) t1_.getC().getX(), (float) t1_.getC().getY(), (float) t1_.getC().getZ()));

        // pseudovzdálenosti vrcholů trojúhelníku t2_ od roviny trojúhelníku t1_
        double p1A = p1.pseudoDistance(new Vector3f((float) t1_.getA().getX(), (float) t1_.getA().getY(), (float) t1_.getA().getZ()));
        double p1B = p1.pseudoDistance(new Vector3f((float) t1_.getB().getX(), (float) t1_.getB().getY(), (float) t1_.getB().getZ()));
        double p1C = p1.pseudoDistance(new Vector3f((float) t1_.getC().getX(), (float) t1_.getC().getY(), (float) t1_.getC().getZ()));

        // normálový vektor roviny trojúhelníku t1_
        Vector norm1 = Vector.crossProduct(new Vector(t1_.a, t1_.b), new Vector(t1_.a, t1_.c));

        // posun vrcholů trojúhelníku t2_ do roviny trojúhelníku t1_
        Vector imgA = Vector.adjustSize(p1A, norm1);
        Vector imgB = Vector.adjustSize(p1B, norm1);
        Vector imgC = Vector.adjustSize(p1C, norm1);

        // kontrola, zda jou obrazy vrcholů trojúhelníku t2_ uvnitř trojúhelníku t1_
        if (Triangle.isIn(t1_, imgA)) {
            temp.set(imgA, t2_.getA());
            if (temp.gainSize() < resSize) {
                resSize = temp.gainSize();
                res = temp.clone();
            }
        }
        if (Triangle.isIn(t1_, imgB)) {
            temp.set(imgB, t2_.getB());
            if (temp.gainSize() < resSize) {
                resSize = temp.gainSize();
                res = temp.clone();
            }
        }
        if (Triangle.isIn(t1_, imgC)) {
            temp.set(imgC, t2_.getC());
            if (temp.gainSize() < resSize) {
                resSize = temp.gainSize();
                res = temp.clone();
            }
        }

        // kontrola vzdáleností stran trojúhelníků
        LineSegment t1AB = new LineSegment(t1_.a, t1_.b);
        LineSegment t1AC = new LineSegment(t1_.a, t1_.c);
        LineSegment t1BC = new LineSegment(t1_.b, t1_.c);
        LineSegment t2AB = new LineSegment(t2_.a, t2_.b);
        LineSegment t2AC = new LineSegment(t2_.a, t2_.c);
        LineSegment t2BC = new LineSegment(t2_.b, t2_.c);

        if (LineSegment.distance(t1AB, t2AB).gainSize() < resSize) {
            resSize = LineSegment.distance(t1AB, t2AB).gainSize();
            res = LineSegment.distance(t1AB, t2AB);
        }

        if (LineSegment.distance(t1AB, t2AC).gainSize() < resSize) {
            resSize = LineSegment.distance(t1AB, t2AC).gainSize();
            res = LineSegment.distance(t1AB, t2AC);
        }

        if (LineSegment.distance(t1AB, t2BC).gainSize() < resSize) {
            resSize = LineSegment.distance(t1AB, t2BC).gainSize();
            res = LineSegment.distance(t1AB, t2BC);
        }

        if (LineSegment.distance(t1AC, t2AB).gainSize() < resSize) {
            resSize = LineSegment.distance(t1AC, t2AB).gainSize();
            res = LineSegment.distance(t1AC, t2AB);
        }

        if (LineSegment.distance(t1AC, t2AC).gainSize() < resSize) {
            resSize = LineSegment.distance(t1AC, t2AC).gainSize();
            res = LineSegment.distance(t1AC, t2AC);
        }

        if (LineSegment.distance(t1AC, t2BC).gainSize() < resSize) {
            resSize = LineSegment.distance(t1AC, t2BC).gainSize();
            res = LineSegment.distance(t1AC, t2BC);
        }

        if (LineSegment.distance(t1BC, t2AB).gainSize() < resSize) {
            resSize = LineSegment.distance(t1BC, t2AB).gainSize();
            res = LineSegment.distance(t1BC, t2AB);
        }

        if (LineSegment.distance(t1BC, t2AC).gainSize() < resSize) {
            resSize = LineSegment.distance(t1BC, t2AC).gainSize();
            res = LineSegment.distance(t1BC, t2AC);
        }

        if (LineSegment.distance(t1BC, t2BC).gainSize() < resSize) {
            resSize = LineSegment.distance(t1BC, t2BC).gainSize();
            res = LineSegment.distance(t1BC, t2BC);
        }

        return(res);
    }

    /**
     * obsah trojúhelníku
     *
     * @return double : obsah trojúhelníku
     */
    public double gainVolume() {
        Vector ab = new Vector(this.a, this.b);
        Vector ac = new Vector(this.a, this.c);

        return(0.5 * Vector.crossProduct(ab, ac).gainSize());
    }

    /**
     * těžiště trojúhelníku
     *
     * @return Vector : těžiště trojúhelníku
     */
    public Vector gainCenter() {
        double x = (1.0 / 3.0) * (this.a.getX() + this.b.getX() + this.c.getX());
        double y = (1.0 / 3.0) * (this.a.getY() + this.b.getY() + this.c.getY());
        double z = (1.0 / 3.0) * (this.a.getZ() + this.b.getZ() + this.c.getZ());

        return(new Vector(x, y, z));
    }

    /**
     * kontrola kolize dvou trojúhelníků
     *
     * @param Triangle trojúhelník - <strong>nebude změněn</strong>
     * @param Triangle trojúhelník - <strong>nebude změněn</strong>
     *
     * @return boolean : true, pokud je kolize, jinak false
     */
    public static boolean isOverlap(Triangle t1_, Triangle t2_) {
        Vector3f v0 = new Vector3f((float) t1_.a.x(), (float) t1_.a.y(), (float) t1_.a.z());
        Vector3f v1 = new Vector3f((float) t1_.b.x(), (float) t1_.b.y(), (float) t1_.b.z());
        Vector3f v2 = new Vector3f((float) t1_.c.x(), (float) t1_.c.y(), (float) t1_.c.z());
        Vector3f u0 = new Vector3f((float) t2_.a.x(), (float) t2_.a.y(), (float) t2_.a.z());
        Vector3f u1 = new Vector3f((float) t2_.b.x(), (float) t2_.b.y(), (float) t2_.b.z());
        Vector3f u2 = new Vector3f((float) t2_.c.x(), (float) t2_.c.y(), (float) t2_.c.z());

        return(Intersection.intersection(v0, v1, v2, u0, u1, u2));
    }

    /**
     * kontrola, zda se bod point_ nalézá v trojúhelníku t_
     *
     * @param Triangle trojúhelník - <strong>nebude změněn</strong>
     * @param Vector bod - <strong>nebude změněn</strong>
     *
     * @return boolean : true, pokud bod leží v trojúhelníku, jinak false
     */
    public static boolean isIn(Triangle t_, Vector point_) {
        LineSegment ab = new LineSegment(t_.a, t_.b);
        LineSegment ac = new LineSegment(t_.a, t_.c);
        LineSegment bc = new LineSegment(t_.b, t_.c);

        // bod Leží na straně trojúhelníku
        if ( (LineSegment.distance(ab, point_).gainSize() < Triangle.ZERO) || (LineSegment.distance(ac, point_).gainSize() < Triangle.ZERO) || (LineSegment.distance(bc, point_).gainSize() < Triangle.ZERO) ) {
            return(true);
        }

        // bod leží uvnitř trojúhelníku
        Triangle pab = new Triangle();
        Triangle pac = new Triangle();
        Triangle pbc = new Triangle();

        try {
            pab = new Triangle(t_.a, t_.b, point_);
            pac = new Triangle(t_.a, point_, t_.c);
            pbc = new Triangle(point_, t_.b, t_.c);
        } catch (Exception e) {
            return(false);
        }

        if ( Math.abs(pab.gainVolume() + pac.gainVolume() + pbc.gainVolume() - t_.gainVolume()) < Triangle.ZERO) {
            return(true);
        }
        else {
            return(false);
        }
    }

	/**
	 * posun trojúhelníku
	 *
	 * @param Vector vektor posunutí
	 */
	public void translate(Vector translate_) {
		this.a.add(translate_);
		this.b.add(translate_);
		this.c.add(translate_);
	}

	/**
	 * škálování trojúhelníku
	 *
	 * @param Vector vektor škálování
	 */
	public void scale(Vector scale_) {
		this.a.scale(scale_);
		this.b.scale(scale_);
		this.c.scale(scale_);
	}

	/**
	 * rotace trojúhelníku
	 *
	 * @param Vector vektor rotace
	 */
	public void rotate(Vector rotate_) {
		this.a.rotate(rotate_);
		this.b.rotate(rotate_);
		this.c.rotate(rotate_);
	}


    // soukromé metody
    /**
     * kontrola zda tři body this.a, this.b, this.c tvoří
     * trojúhleník a ne přímku
     *
     * @return boolean : true, pokud je objekt trojúhelníkem, false, pokud jsou body A, B, C na přímce
     */
    private boolean isTriangle(Vector a_, Vector b_, Vector c_) {
        Vector ab = new Vector(a_, b_);
        Vector ac = new Vector(a_, c_);
        Vector bc = new Vector(b_, c_);

        double abSize = ab.gainSize();
        double acSize = ac.gainSize();
        double bcSize = bc.gainSize();

        if ( ((abSize + acSize) > bcSize) && ((abSize + bcSize) > acSize) && ((acSize + bcSize) > abSize) ) {
            return(true);
        }
        else {
            return(false);
        }
    }

}
