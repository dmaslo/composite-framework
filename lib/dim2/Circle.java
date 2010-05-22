package lib.dim2;

import java.io.Serializable;
import lib.Vector;

/**
 * <h1>Knihovní třída představující kruhy</h1>
 * <p>
 * Kruhy jsou ve 2D prostoru dány vektorem středu a reálným poloměrem
 * </p>
 *
 * @author Daniel Máslo <daniel.maslo@gmail.com>
 * @since JDK 1.5
 * @version 2.0
 */
public class Circle extends Object2D implements Serializable, Cloneable {

    // proměnné
    /**
     * unikátní id pro serializaci
     * zrcadlí verzi třídy/objektu
     */
    private static final long serialVersionUID = 2L;

    /** poloměr kruhu */
    private double radius;

    /* střed kruhu */
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
    public Object2D clone() {
        Circle res = new Circle();

        res.radius = this.radius;
        res.center = this.center.clone();

        return(res);
    }

    @Override
    public String export(String fileType_) {
		double xShift;
        StringBuffer res = new StringBuffer();

        if (fileType_.toLowerCase().endsWith("svg")) {            
            res.append("\t<circle cx=\"" + this.center.getX() + "\" cy=\"" + this.center.getY() + "\" r =\"" + this.radius + "\" fill=\"rgb(" + this.getColor().getRed() + ", " + this.getColor().getGreen() + ", " + this.getColor().getBlue() + ")\" stroke=\"none\" stroke-width=\"0\" />\n");

			// text
//			if ( (Integer) this.getBurn().get("num") > 9) {
//				xShift = (this.center.getX() - this.radius/2);
//			}
//			else if ((Integer) this.getBurn().get("num") < 0) {
//				xShift = (this.center.getX() - this.radius/3);
//			}
//			else {
//				xShift = (this.center.getX() - this.radius/4);
//			}
//
//			res.append("\t<text font-size=\"" + this.radius + "\" fill=\"white\" x=\"" + xShift + "\" y=\"" + (this.center.getY() + this.radius/4) + "\">" + this.getBurn().get("num") + "</text>\n\n");
        }

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
        return("Circle(Center(" + this.center + "), r = " + this.radius + ")");
    }

    /**
     * kruh se nafukuje jen přenásobením poloměru daným koeficientem,
     * ostatní koeficienty nejou použity
     */
    @Override
    public void blow(double... blowCoefs_) {
        this.radius *= blowCoefs_[0];
    }

	@Override
	public void rotate(double angle_) {
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
    public double gainMaxX() {
        return(this.center.getX() + this.radius);
    }

    @Override
    public double gainMaxY() {
        return(this.center.getY() + this.radius);
    }


    // konstruktory
    /**
     * prázdný (výchozí) konstruktor
     */
    public Circle() {
        super();
    }

    /**
     * plný konstruktor
     *
     * @param Vector : střed kruhu - <strong>nebude změněn</strong>
     * @param double : poloměr kruhu
     */
    public Circle(Vector center_, double radius_) {
        super();

        this.center = center_.clone();
        this.radius = radius_;
    }


    // veřejné metody
	/**
     * kontrola kolize dvou kruhů
     *
     * @param Circle kruh - <strong>nebude změněn</strong>
     * @param Circle kruh - <strong>nebude změněn</strong>
     *
     * @return boolean : true, pokud objekty kolidují, jinak false
     */
    public static boolean isOverlap(Circle c1_, Circle c2_) {
        if (Vector.distanceSquared(c1_.center, c2_.center) >= ((c1_.radius + c2_.radius) * (c1_.radius + c2_.radius)) ) {
            return(false);
        }
        else {
            return(true);
        }
    }

	/**
     * kontrola kolize dvou objektů - externě volané matoda z předka
     *
     * @param Object2D objekt - <strong>nebude změněn</strong>
     * @param Object2D objekt - <strong>nebude změněn</strong>
     *
     * @return boolean : true, pokud objekty kolidují, jinak false
     */
    public static boolean isOverlap(Object2D o1_, Object2D o2_) {
        if ( (o1_.getClass().equals(Circle.class)) && (o2_.getClass().equals(Circle.class)) ) {
            Circle c1_ = (Circle) o1_;
            Circle c2_ = (Circle) o2_;

            return(Circle.isOverlap(c1_, c2_));
        }
        else {
            throw new UnsupportedOperationException("Neplatné objekty.");
        }
    }

	/**
     * měření orientované vzdálenosti mezi kruhy
     *
     * @param Circle kruh - <strong>nebude změněn</strong>
     * @param Circle kruh - <strong>nebude změněn</strong>
     *
     * @return Vector : orientovaná vzdálenost mezi objekty
     */
    public static Vector distance(Circle c1_, Circle c2_) {
        Vector res = new Vector(c1_.center, c2_.center);

        res.adjustSize(res.gainSize() - c1_.radius - c2_.radius);

        return(res);
    }

	/**
     * měření orientované vzdálenosti mezi středy kruhů
     *
     * @param Circle kruh - <strong>nebude změněn</strong>
     * @param Circle kruh - <strong>nebude změněn</strong>
     *
     * @return Vector : orientovaná vzdálenost mezi středy kruhů
     */
	public static Vector centerDistance(Circle c1_, Circle c2_) {
		Vector res = new Vector(c1_.center, c2_.center);

        return(res);
	}

}
