package lib.dim3;

import java.io.Serializable;
import lib.Vector;

/**
 * <h1>Knihovní třída představující koule</h1>
 * <p>
 * Koule jsou ve 3D prostoru dány vektorem středu a reálným poloměrem
 * </p>
 *
 * @author Daniel Máslo <daniel.maslo@gmail.com>
 * @since JDK 1.5
 * @version 2.0
 */
public class Sphere extends Object3D implements Serializable, Cloneable {

    // proměnné
    /**
     * unikátní id pro serializaci
     * zrcadlí verzi třídy/objektu
     */
    private static final long serialVersionUID = 2L;

    /** poloměr koule */
    private double radius;

    /* střed koule */
    private Vector center;


    // gettery a settery
    public Vector getCenter() {
        return center;
    }

    public void setCenter(Vector center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
    

    // překryté metody
    @Override
    public Object3D clone() {
        Sphere res = new Sphere();

        res.radius = this.radius;
        res.center = this.center.clone();

        return(res);
    }

    @Override
    public String export(String fileType_) {
        StringBuffer res = new StringBuffer();

        if (fileType_.toLowerCase().endsWith("pov")) {
            res.append("object {\n");
            res.append("\tsphere {\n");
            res.append("\t\t<0, 0, 0>\n");
            res.append("\t\t" + this.radius + "\n");
            res.append("\t}\n");

            res.append("texture {\n");

            res.append("\tpigment {\n");
            res.append("\t\tcolor rgb <"+((double) this.getColor().getRed()/255.0)+", "+((double) this.getColor().getGreen()/255.0)+", "+((double) this.getColor().getBlue()/255.0)+">\n");
            res.append("\t}\n");

            res.append("}\n");

            res .append("\tfinish {phong 1}\n");

            res.append("translate <" + (this.center.x()) + ", " + (this.center.y()) + ", " + (this.center.z()) + ">\n");
            res .append("}\n\n");
        }

        return(res.toString());
    }

    @Override
    public String toString() {
        return("Sphere(Center(" + this.center + "), r = " + this.radius + ")");
    }

    /**
     * koule se nafukuje jen přenásobením poloměru daným koeficientem,
     * ostatní koeficienty nejou použity
     */
    @Override
    public void blow(double... blowCoefs_) {
        this.radius *= blowCoefs_[0];
    }

	@Override
    public void rotate(Vector rotate_) {
	}

	@Override
	public void translate(Vector translate_) {
		this.center.add(translate_);
	}

    @Override
    public boolean isIn(Vector v_) {
        if (Vector.distanceSquared(this.center, v_) <= (this.radius * this.radius)) {
            return(true);
        }
        else {
            return(false);
        }
    }

    @Override
    public boolean isOut(Vector v_) {
        return(!this.isIn(v_));
    }

    @Override
    public double gainMinX() {
        return(this.center.getX() - this.radius);
    }

    @Override
    public double gainMinY() {
        return(this.center.getY() - this.radius);
    }

    @Override
    public double gainMinZ() {
        return(this.center.getZ() - this.radius);
    }

    @Override
    public double gainMaxX() {
        return(this.center.getX() + this.radius);
    }

    @Override
    public double gainMaxY() {
        return(this.center.getY() + this.radius);
    }

    @Override
    public double gainMaxZ() {
        return(this.center.getZ() + this.radius);
    }


    // konstruktory
    /**
     * prázdný (výchozí) konstruktor
     */
    public Sphere() {
        super();
    }

    /**
     * plný konstruktor
     *
     * @param Vector : střed koule - <strong>nebude změněn</strong>
     * @param double : poloměr koule
     */
    public Sphere(Vector center_, double radius_) {
        super();

        this.center = center_.clone();
        this.radius = radius_;
    }


    // veřejné metody
	/**
     * kontrola kolize dvou koulí
     *
     * @param Sphere koule - <strong>nebude změněna</strong>
     * @param Sphere koule - <strong>nebude změněna</strong>
     *
     * @return boolean : true, pokud koule kolidují, jinak false
     */
    public static boolean isOverlap(Sphere s1_, Sphere s2_) {
        if (Vector.distanceSquared(s1_.center, s2_.center) >= ((s1_.radius + s2_.radius) * (s1_.radius + s2_.radius)) ) {
            return(false);
        }
        else {            
            return(true);
        }
    }

	/**
     * kontrola kolize dvou objektů - externě volané matoda z předka
     *
     * @param Object3D objekt - <strong>nebude změněn</strong>
     * @param Object3D objekt - <strong>nebude změněn</strong>
     *
     * @return boolean : true, pokud objekty kolidují, jinak false
     */
    public static boolean isOverlap(Object3D o1_, Object3D o2_) {
        if ( (o1_.getClass().equals(Sphere.class)) && (o2_.getClass().equals(Sphere.class)) ) {
            Sphere s1_ = (Sphere) o1_;
            Sphere s2_ = (Sphere) o2_;

            return(Sphere.isOverlap(s1_, s2_));
        }
        else {
            throw new UnsupportedOperationException("Neplatné objekty.");
        }
    }

    /**
     * měření orientované vzdálenosti mezi koulemi
     *
     * @param Sphere koule - <strong>nebude změněna</strong>
     * @param Sphere koule - <strong>nebude změněna</strong>
     *
     * @return Vector : orientovaná vzdálenost mezi koulemi (vrací i vektor, představují zápornou vzdálenost)
     */
    public static Vector distance(Sphere s1_, Sphere s2_) {
        Vector res = new Vector(s1_.center, s2_.center);

        res.adjustSize(res.gainSize() - s1_.radius - s2_.radius);

        return(res);
    }

	/**
     * měření vzdálenosti mezi koulemi
     *
     * @param Sphere koule - <strong>nebude změněna</strong>
     * @param Sphere koule - <strong>nebude změněna</strong>
     *
     * @return double : vzdálenost mezi koulemi (i záporná)
     */
	public static double realDistance(Sphere s1_, Sphere s2_) {
        Vector res = new Vector(s1_.center, s2_.center);

        double dist = res.gainSize() - s1_.radius - s2_.radius;

        return(dist);
    }

	/**
     * měření orientované vzdálenosti mezi středy kouleí
     *
     * @param Sphere koule - <strong>nebude změněna</strong>
     * @param Sphere koule - <strong>nebude změněna</strong>
     *
     * @return Vector : orientovaná vzdálenost mezi středy koulí
     */
	public static Vector centerDistance(Sphere s1_, Sphere s2_) {
        Vector res = new Vector(s1_.center, s2_.center);

        return(res);
    }

}
