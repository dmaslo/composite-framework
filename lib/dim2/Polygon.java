package lib.dim2;

import java.io.Serializable;
import java.util.ArrayList;
import lib.LineSegment;
import lib.Triangle;
import lib.Vector;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * <h1>Knihovní třída představující mnohoúhelníky</h1>
 * <p>
 * Mnohoúhelníky jsou ve 2D prostoru dány počátečním (center) vektorem
 * a sadou vektorů z něj vybíhajících k ostatním bodů mnohoúhelníku tak,
 * že center leží uvnitř hranic mnohoúhelníku a není tak vrcholem. *
 * </p>
 *
 * @author Daniel Máslo <daniel.maslo@gmail.com>
 * @since JDK 1.5
 * @version 2.0
 */
public class Polygon extends Object2D implements Serializable, Cloneable {

    // proměnné
    /**
     * unikátní id pro serializaci
     * zrcadlí verzi třídy/objektu
     */
    private static final long serialVersionUID = 2L;

    /** počáteční (center) vektor */
    private Vector center;

    /** sada vektorů, určující ostatní body */
    private ArrayList<Vector> points = new ArrayList<Vector>();

	/** 
	 * vzdálenost počátečního (orgin) vektoru
	 * a nejvzdálenějšího bodu polygonu.
	 * Kružnice se středem v počátečním (center)
	 * vektoru s poloměrem radius je obálkou
	 * celého polygonu.
	 */
	private double radius;


    // gettery a settery
    public Vector getOrigin() {
        return center;
    }

    public void setOrigin(Vector origin) {
        this.center = origin;
    }

    public ArrayList<Vector> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Vector> points) {
        this.points = points;
    }

	public double getRadius() {
		return radius;
	}
    

    // překryté metody
    @Override
    public Object2D clone() {
        Polygon res = new Polygon();

        res.center = this.center.clone();
        res.points = (ArrayList<Vector>) this.points.clone();
		res.radius = this.radius;

        return(res);
    }

    @Override
    public String export(String fileType_) {
        StringBuffer res = new StringBuffer();

        if (fileType_.toLowerCase().endsWith("svg")) {
            StringBuffer sb = new StringBuffer();
            Vector point = new Vector();

            for (int i = 0; i < this.points.size(); i++) {
                point = this.points.get(i);
                sb.append(" " + point.getX() + ", " + point.getY());
            }

            //res.append("\t<circle cx=\"" + this.center.getX() + "\" cy=\"" + this.center.getY() + "\" r =\"" + this.radius + "\" fill=\"rgb(" + this.getColor().getRed() + ", " + this.getColor().getGreen() + ", " + this.getColor().getBlue() + ")\" stroke=\"none\" stroke-width=\"0\" />\n");
            res.append("\t<polygon points=\"" + sb.toString() + "\" fill=\"rgb(" + this.getColor().getRed() + ", " + this.getColor().getGreen() + ", " + this.getColor().getBlue() + ")\" stroke=\"none\" stroke-width=\"0\" />\n");
        }

        if (fileType_.toLowerCase().endsWith("pov")) {
            throw new NotImplementedException();
            
//            res.append("object {\n");
//            res.append("\tsphere {\n");
//            res.append("\t\t<0, 0, 0>\n");
//            res.append("\t\t" + this.radius + "\n");
//            res.append("\t}\n");
//
//            res.append("texture {\n");
//
//            res.append("\tpigment {\n");
//            res.append("\t\tcolor rgb <"+((double) this.getColor().getRed()/255.0)+", "+((double) this.getColor().getGreen()/255.0)+", "+((double) this.getColor().getBlue()/255.0)+">\n");
//            res.append("\t}\n");
//
//            res.append("}\n");
//
//            res .append("\tfinish {phong 1}\n");
//
//            res.append("translate <" + (this.center.x()) + ", " + (this.center.y()) + ", " + (this.center.z()) + ">\n");
//            res .append("}\n\n");
        }

        return(res.toString());
    }

    @Override
    public String toString() {
        return("Polygon(Origin(" + this.center + "), points = " + this.points.toString() + ")");
    }

    /**
     * mnohoúhelník se nafukuje přenásobením points seznamu vektorů,
     * vektor center se nemění
     * ostatní koeficienty nejou použity
     */
    @Override
    public void blow(double... blowCoefs_) {
		Vector temp = new Vector();

        for (int i = 0; i < this.points.size(); i++) {
			temp.set(this.center, this.points.get(i));
			temp.dotProduct(blowCoefs_[0]);

            this.points.set(i, Vector.sum(this.center, temp));
        }

		this.radius *= blowCoefs_[0];
    }

	/**
	 * mnohoúhelník rotuje kolem počátku - Vector center
	 */
	@Override
	public void rotate(double angle_) {
		for (int i = 0; i < this.points.size(); i++) {
			this.points.get(i).add(Vector.opposite(this.center));
			this.points.get(i).rotate(new Vector(0, 0, angle_));
		}

		for (int i = 0; i < this.points.size(); i++) {
			this.points.get(i).add(this.center);
		}
	}

	@Override
	public void translate(Vector translate_) {
		this.center.add(translate_);

		for (int i = 0; i < this.points.size(); i++) {
			this.points.get(i).add(translate_);
		}
	}

    @Override
    public boolean isIn(Vector v_) {
		// obálka
		if (Vector.distanceSquared(v_, this.center) > (this.radius * this.radius)) {
			return(false);
		}

        Triangle t = new Triangle();

        // step the mesh
        for (int i = 0; i < this.points.size() - 1; i++) {
            t.set(this.center, this.points.get(i), this.points.get(i + 1));

            if (Triangle.isIn(t, v_)) {
                return(true);
            }
        }

		// last mesh
		t.set(this.center, this.points.get(0), this.points.get(this.points.size() - 1));

		if (Triangle.isIn(t, v_)) {
			return(true);
		}

        return(false);        
    }

    @Override
    public boolean isOut(Vector v_) {
        return(!this.isIn(v_));
    }

    @Override
    public double gainMinX() {
        double res = this.center.getX();

        for (int i = 0; i < this.points.size(); i++) {
            if (this.points.get(i).getX() < res) {
                res = this.points.get(i).getX();
            }
        }

        return(res);
    }

    @Override
    public double gainMinY() {
        double res = this.center.getY();

        for (int i = 0; i < this.points.size(); i++) {
            if (this.points.get(i).getY() < res) {
                res = this.points.get(i).getY();
            }
        }

        return(res);
    }

    @Override
    public double gainMaxX() {
        double res = this.center.getX();

        for (int i = 0; i < this.points.size(); i++) {
            if (this.points.get(i).getX() > res) {
                res = this.points.get(i).getX();
            }
        }

        return(res);
    }

    @Override
    public double gainMaxY() {
        double res = this.center.getY();

        for (int i = 0; i < this.points.size(); i++) {
            if (this.points.get(i).getY() > res) {
                res = this.points.get(i).getY();
            }
        }

        return(res);
    }


    // konstruktory
    /**
     * prázdný (výchozí) konstruktor
     */
    public Polygon() {
        super();
    }

    /**
     * plný konstruktor
     *
     * @param Vector : počátek (center) - <strong>nebude změněn</strong>
     * @param Vector... : sada bodů - <strong>nebudou změněny</strong>
     */
    public Polygon(Vector center_, Vector... points_) {
        super();

        this.center = center_.clone();
		this.radius = 0.0;

        for (int i = 0; i < points_.length; i++) {
            this.points.add(points_[i].clone());

			if (Vector.distanceSquared(center_, points_[i]) > (this.radius * this.radius)) {
				this.radius = Vector.distance(center_, points_[i]);
			}
        }
    }


    // veřejné metody
	/**
     * měření orientované vzdálenosti mezi polygony
     *
     * @param Polygon polygon - <strong>nebude změněn</strong>
     * @param Polygon polygon - <strong>nebude změněn</strong>
     *
     * @return Vector : orientovaná vzdálenost mezi objekty
     */
    public static boolean isOverlap(Polygon p1_, Polygon p2_) {
		// obálka
		if (Vector.distanceSquared(p1_.center, p2_.center) > ( (p1_.radius + p2_.radius) * (p1_.radius + p2_.radius) )) {
			return(false);
		}
		if (Vector.distance(p1_.center, p2_.center) > (p1_.radius + p2_.radius) ) {
			return(false);
		}

		LineSegment temp1;
		LineSegment temp2;

		for (int i = 0; i < p1_.points.size(); i++) {
			for (int j = 0; j < p2_.points.size(); j++) {
				if ( (i == 0) && (j != 0) ) {
					temp1 = new LineSegment(p1_.points.get(0), p1_.points.get(p1_.points.size() - 1));
					temp2 = new LineSegment(p2_.points.get(j), p2_.points.get(j - 1));
				}
				else if ( (j == 0) && (i != 0) ) {
					temp1 = new LineSegment(p1_.points.get(i), p1_.points.get(i - 1));
					temp2 = new LineSegment(p2_.points.get(0), p2_.points.get(p2_.points.size() - 1));
				}
				else if ( (j == 0) && (i == 0) ) {
					temp1 = new LineSegment(p1_.points.get(0), p1_.points.get(p1_.points.size() - 1));
					temp2 = new LineSegment(p2_.points.get(0), p2_.points.get(p2_.points.size() - 1));
				}
				else {
					temp1 = new LineSegment(p1_.points.get(i), p1_.points.get(i - 1));
					temp2 = new LineSegment(p2_.points.get(j), p2_.points.get(j - 1));
				}

//				if (LineSegment.distance(temp1, temp2).gainSize() < 0.1) {
//					return(true);
//				}

				if (LineSegment.isOverlap2D(temp1, temp2)) {
					return(true);
				}

			}
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
    public static boolean isOverlap(Object2D o1_, Object2D o2_) {
        if ( (o1_.getClass().equals(Polygon.class)) && (o2_.getClass().equals(Polygon.class)) ) {
            Polygon p1_ = (Polygon) o1_;
            Polygon p2_ = (Polygon) o2_;

            return(Polygon.isOverlap(p1_, p2_));
        }
        else {
            throw new UnsupportedOperationException("Neplatné objekty.");
        }
    }

	/**
     * měření orientované vzdálenosti mezi polygony
     *
     * @param Polygon polygon - <strong>nebude změněn</strong>
     * @param Polygon polygon - <strong>nebude změněn</strong>
     *
     * @return Vector : orientovaná vzdálenost mezi objekty
     */
    public static Vector distance(Polygon p1_, Polygon p2_) {
        Vector res = new Vector();
        Triangle temp = new Triangle();

        ArrayList<Triangle> t1 = new ArrayList<Triangle>();
        ArrayList<Triangle> t2 = new ArrayList<Triangle>();

        // t1 mesh
        for (int i = 0; i < p1_.points.size() - 1; i++) {
            temp.set(p1_.center, p1_.points.get(i), p1_.points.get(i + 1));
            t1.add(temp.clone());
        }
		temp.set(p1_.center, p1_.points.get(0), p1_.points.get(p1_.points.size()-1));
        t1.add(temp.clone());

        // t2 mesh
        for (int i = 0; i < p2_.points.size() - 1; i++) {
            temp.set(p2_.center, p2_.points.get(i), p2_.points.get(i + 1));
            t2.add(temp.clone());
        }
		temp.set(p2_.center, p2_.points.get(0), p2_.points.get(p2_.points.size()-1));
        t2.add(temp.clone());


        // meshes distance
        double dist = Double.MAX_VALUE;

        for (int i = 0; i < t1.size(); i++) {
            for (int j = 0; j < t2.size(); j++) {
                if (Triangle.distance(t1.get(i), t2.get(j)).gainSize() < dist) {
                    dist = Triangle.distance(t1.get(i), t2.get(j)).gainSize();
                    res = Triangle.distance(t1.get(i), t2.get(j));
                }
            }
        }

        return(res);
    }

	/**
     * měření orientované vzdálenosti mezi středy polygonů
     *
     * @param Polygon polygon - <strong>nebude změněn</strong>
     * @param Polygon polygon - <strong>nebude změněn</strong>
     *
     * @return Vector : orientovaná vzdálenost mezi středy polygonů
     */
	public static Vector centerDistance(Polygon p1_, Polygon p2_) {
		Vector res = new Vector(p1_.center, p2_.center);

        return(res);
	}

}

