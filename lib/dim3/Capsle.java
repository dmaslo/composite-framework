package lib.dim3;

import java.io.Serializable;
import lib.LineSegment;
import lib.Vector;


/**
 * <h1>Knihovní třída představující kapsle</h1>
 * <p>
 * Kapsle jsou ve 3D prostoru dány úsečkou - jádrem a reálným poloměrem
 * </p>
 *
 * @author Daniel Máslo <daniel.maslo@gmail.com>
 * @since JDK 1.5
 * @version 2.0
 */
public class Capsle extends Object3D implements Serializable {

    // proměnné
    /**
     * unikátní id pro serializaci
     * zrcadlí verzi třídy/objektu
     */
    private static final long serialVersionUID = 2L;

    /** úsečka - jádro kapsle */
    LineSegment core = new LineSegment();

    /** poloměr */
    double radius;


    // gettery a settery
    public LineSegment getCore() {
        return core;
    }

    public void setCore(LineSegment core) {
        this.core = core.clone();
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }


    // překryté metody
    /**
     * 1. parametr přenásobuje poloměr kapsle
     * 2. parametr přenásobuje délku jádra - střed zůstává na stejném místě
     */
    @Override
    public void blow(double... blowCoefs_) {
        this.radius *= blowCoefs_[0];

        Vector start = this.core.getStart().clone();
        Vector end = this.core.getEnd().clone();

        double len = this.core.gainSize();

        Vector center = new Vector(start, end);
        center.adjustSize(len/2.0);

        Vector ce = new Vector(center, end);
        ce.dotProduct(blowCoefs_[1]);

        Vector cs = new Vector(center, start);
        cs.dotProduct(blowCoefs_[1]);

        LineSegment newCore = new LineSegment(Vector.sum(center, cs), Vector.sum(center, ce));

        this.core = newCore.clone();
    }

	@Override
    public void rotate(Vector rotate_) {
		this.core.getStart().add(Vector.opposite(this.core.gainCenter()));
		this.core.getEnd().add(Vector.opposite(this.core.gainCenter()));

		this.core.getStart().rotate(rotate_);
		this.core.getEnd().rotate(rotate_);

		this.core.getStart().add(this.core.gainCenter());
		this.core.getEnd().add(this.core.gainCenter());
	}

	@Override
	public void translate(Vector translate_) {
		this.core.getStart().add(translate_);
		this.core.getEnd().add(translate_);
	}

    @Override
    public boolean isIn(Vector v_) {
        if (LineSegment.distance(this.core, v_).gainSize() <= this.radius) {
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
    public String export(String fileType_) {
        StringBuffer res = new StringBuffer("");

        if (fileType_.toLowerCase().endsWith("pov")) {
            res.append("union {\n");
                res.append("\tobject {\n");
                    res.append("\t\tsphere {\n");
                        res.append("\t\t\t<" + this.core.getStart().x() + ", " + this.core.getStart().y() + ", " + this.core.getStart().z() + ">\n");
                        res.append("\t\t\t" + this.radius + "\n");
                    res.append("\t\t}\n");
                res.append("\t}\n");

                res.append("\tobject {\n");
                    res.append("\t\tsphere {\n");
                        res.append("\t\t\t<" + this.core.getEnd().x() + ", " + this.core.getEnd().y() + ", " + this.core.getEnd().z() + ">\n");
                        res.append("\t\t\t" + this.radius + "\n");
                    res.append("\t\t}\n");
                res.append("\t}\n");

                res.append("\tobject {\n");
                    res.append("\t\tcylinder {\n");
                        res.append("\t\t\t<" + this.core.getStart().x() + ", " + this.core.getStart().y() + ", " + this.core.getStart().z() + ">, <" + this.core.getEnd().x() + ", " + this.core.getEnd().y() + ", " + this.core.getEnd().z() + ">\n");
                        res.append("\t\t\t" + this.radius + "\n");
                    res.append("\t\t}\n");
                res.append("\t}\n");

                res.append("texture {\n");
                res.append("\tpigment {\n");
                res.append("\t\tcolor rgb <"+((double) this.getColor().getRed()/255.0)+", "+((double) this.getColor().getGreen()/255.0)+", "+((double) this.getColor().getBlue()/255.0)+">\n");
                res.append("\t}\n");
                res.append("}\n");
                res.append("\tfinish {phong 1}\n");
            res.append("}\n\n");
        }

        return(res.toString());
    }

    @Override
    public Capsle clone() {
        Capsle res = new Capsle();

        res.core = this.core.clone();
        res.radius = this.radius;

        return(res);
    }

    @Override
    public double gainMinX() {
        return(Math.min(this.core.getStart().x() - radius, this.core.getEnd().x() - radius));
    }

    @Override
    public double gainMaxX() {
        return(Math.max(this.core.getStart().x() + radius, this.core.getEnd().x() + radius));
    }

    @Override
    public double gainMinY() {
        return(Math.min(this.core.getStart().y() - radius, this.core.getEnd().y() - radius));
    }

    @Override
    public double gainMaxY() {
        return(Math.max(this.core.getStart().y() + radius, this.core.getEnd().y() + radius));
    }

    @Override
    public double gainMinZ() {
        return(Math.min(this.core.getStart().z() - radius, this.core.getEnd().z() - radius));
    }

    @Override
    public double gainMaxZ() {
        return(Math.max(this.core.getStart().z() + radius, this.core.getEnd().z() + radius));
    }

    @Override
    public String toString() {
        return("Capsle(" + this.core + ")(radius = " + this.radius + ")");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(Capsle.class)) {
            Capsle c = (Capsle) obj;

            if (c.radius == this.radius) {
                if (c.core.equals(this.core)) {
                    return(true);
                }
            }
        }

        return(false);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.core != null ? this.core.hashCode() : 0);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.radius) ^ (Double.doubleToLongBits(this.radius) >>> 32));
        return hash;
    }



    // konstruktory
    /**
     * prázdný (výchozí) konstruktor
     */
    public Capsle() {
        super();
    }

    /**
     * plný konstruktor
     *
     * @param LineSegment jádro - úsečka - <strong>nebude změněna</strong>
     * @param double poloměr
     */
    public Capsle(LineSegment core_, double radius_) {
        super();

        this.core   = core_.clone();
        this.radius = radius_;
    }

    /**
     * bodový konstruktor
     *
     * @param Vector počáteční bod jádra - <strong>nebude změněn</strong>
     * @param Vector koncový bod jádra - <strong>nebude změněn</strong>
     * @param double poloměr
     */
    public Capsle(Vector v1_, Vector v2_, double radius_) {
        super();

        this.core   = new LineSegment(v1_, v2_);
        this.radius = radius_;
    }


    // methods
    /**
     * kontrola překryvu dvou kapslí
     *
     * @param Capsle 1. kapsle
     * @param Capsle 2. kapsle
     *
     * @return boolean : true, pokud je překryv, jinak else
     */
    public static boolean isOverlap(Capsle cap1_, Capsle cap2_) {
		if (LineSegment.distance(cap1_.core, cap2_.core).gainSize() < (cap1_.radius + cap2_.radius)) {
            return(true);
        }
        else {            
            return(false);
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
    public static boolean isOverlap(Object3D o1_, Object3D o2_) {
        if ( (o1_.getClass().equals(Capsle.class)) && (o2_.getClass().equals(Capsle.class)) ) {
            Capsle c1_ = (Capsle) o1_;
            Capsle c2_ = (Capsle) o2_;

            return(Capsle.isOverlap(c1_, c2_));
        }
        else {
            throw new UnsupportedOperationException("Neplatné objekty.");
        }
    }

}
