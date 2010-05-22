package lib;

import flanagan.complex.Complex;
import java.io.Serializable;

/**
 * <h1>Knihovní třída pro práci s úsečkami ve 3D prostoru</h1>
 * <p>
 * Instance této třídy reprezentují úsečky, dané dvěma body v
 * 3D prostoru.
 * </p>
 *
 * @author Daniel Máslo <daniel.maslo@gmail.com>
 * @since JDK 1.5
 * @version 2.0
 */
public class LineSegment implements Serializable, Cloneable {

    // proměnné
    /**
     * unikátní id pro serializaci
     * zrcadlí verzi třídy/objektu
     */
    private static final long serialVersionUID = 2L;

    /** počáteční bod úsečky */
    private Vector start = new Vector();

    /** koncový bod úsečky */
    private Vector end = new Vector();

    /** směrový vektor úsečky */
    private Vector t = new Vector();


    // konstanty
    /** numerická nula <= nepřesná reprezentace čísel */
    public static final double ZERO = 0.0000000000000001;

    
    // gettery a settery
    public Vector getEnd() {
        return end;
    }

    public void setEnd(Vector end) {
        this.end = end.clone();
    }

    public Vector getStart() {
        return start;
    }

    public void setStart(Vector start) {
        this.start = start.clone();
    }


    // překryté metody
    @Override
    public LineSegment clone() {
        LineSegment res = new LineSegment();

        res.start = this.start.clone();
        res.end   = this.end.clone();
        res.t     = this.t.clone();

        return(res);
    }

    @Override
    public String toString() {
        return("LineSegment(" + this.start + ")(" + this.end + ")");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(LineSegment.class)) {
            LineSegment ls = (LineSegment) obj;

            if ( (ls.start.equals(this.start)) && (ls.end.equals(this.end)) ) {
                return(true);
            }
            if ( (ls.start.equals(this.end)) && (ls.end.equals(this.start)) ) {
                return(true);
            }
        }

        return(false);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.start != null ? this.start.hashCode() : 0);
        hash = 97 * hash + (this.end != null ? this.end.hashCode() : 0);
        return hash;
    }


    // konstruktory
    /**
     * prázdný (výchozí) konstruktor
     */
    public LineSegment() {

    }

    /**
     * bodový konstruktor
     *
     * @param Vector počáteční bod - <strong>nebude změněn</strong>
     * @param Vector koncový bod - <strong>nebude změněn</strong>
     */
    public LineSegment(Vector start_, Vector end_) {
        this.start = start_.clone();
        this.end   = end_.clone();

        this.t = new Vector(end_.x() - start_.x(), end_.y() - start_.y(), end_.z() - start_.z());
    }


    // veřejné metody
    /**
     * velikost úsečky
     *
     * @return double : velikost úsečky
     */
    public double gainSize() {
        return(t.gainSize());
    }

    /**
     * čtverec velikost úsečky - velikost úsečky "na druhou"
     *
     * @return double : čtverec velikost úsečky - velikost úsečky "na druhou"
     */
    public double gainSizeSquared() {
        return(t.gainSizeSquared());
    }

    /**
     * střed úsečky
     *
     * @return Vector : střed úsečky
     */
    public Vector gainCenter() {
        Vector res = new Vector();

		res.setX((this.end.getX() + this.start.getX()) / 2.0);
		res.setY((this.end.getY() + this.start.getY()) / 2.0);
		res.setZ((this.end.getZ() + this.start.getZ()) / 2.0);        

        return(res);
    }

	/**
	 * překryv úseček v rovině
	 *
	 * @param LineSegment úsečka - <string>nebude změněna</strong>
	 * @param LineSegment úsečka - <string>nebude změněna</strong>
	 *
	 * @return boolean : true pokud se úsečky dotýkají (protínají), jinak false
	 */
	public static boolean isOverlap2D(LineSegment ls1_, LineSegment ls2_) {
		Complex zi, zf, wi, wf, b, bb;
		double t, u, Det;

		zf = new Complex(ls1_.getStart().getX(), ls1_.getStart().getY());
		zi = new Complex(ls1_.getEnd().getX(), ls1_.getEnd().getY());
		wf = new Complex(ls2_.getStart().getX(), ls2_.getStart().getY());
		wi = new Complex(ls2_.getEnd().getX(), ls2_.getEnd().getY());

		b  = Complex.minus(zf, zi);    // b = zf - zi;
		bb = Complex.minus(wf, wi);    // bb = wf - wi

		Det = Complex.times(Complex.conjugate(bb), b).getImag();  // Det = Im( conjg(bb) * b )

		if (Math.abs(Det) < 1.0e-10) {           // if abs( Det ) < 1.0e-10 \ check for parallelism			
			return(false);
		}
		else {
			t = Complex.times(Complex.conjugate(bb), Complex.minus(wi, zi)).getImag() / Det;	// t = Im( conjg(bb) * (wi - zi) ) / Det;
			u = Complex.times(Complex.conjugate(b), Complex.minus(wi, zi)).getImag() / Det;	// u = Im( conjg(b) * (wi - zi) ) / Det;

			if ( (0 <= t) && (t <= 1) && (0 <= u) && (u <= 1) ) {
				return(true);
			}
			else {				
				return(false);
			}
		}
	}

    /**
     * vzdálenost aktuální úsečky od bodu
     *
     * @param Vector bod - <string>nebude změněn</strong>
     *
     * @return Vector : vektor, představující nejmenší vzdálenost
     */
    // TODO remove that LineSegment HACK
    public static Vector distance(LineSegment lineSegment_, Vector point_) {
        LineSegment temp = new LineSegment(point_.clone(), Vector.sum(point_, new Vector(lineSegment_.start, point_)));

		return(LineSegment.distance(lineSegment_, temp));
    }

    /**
     * vzdálenost dvou úseček
     *
     * @param LineSegment úsečka - <string>nebude změněna</strong>
     * @param LineSegment úsečka - <string>nebude změněna</strong>
     *
     * @return Vector : vektor, představující nejmenší vzdálenost
     */
    public static Vector distance(LineSegment ls1_, LineSegment ls2_) {
        Vector u = ls1_.t.clone();
        Vector v = ls2_.t.clone();
        //Vector w = Vector.sum(ls1_.start, ls2_.start.clone().opposite());
        Vector w = Vector.sum(ls1_.start.clone(), Vector.opposite(ls2_.start.clone()));

        double a = Vector.dotProduct(u,u);        // always >= 0
        double b = Vector.dotProduct(u,v);
        double c = Vector.dotProduct(v,v);        // always >= 0
        double d = Vector.dotProduct(u,w);
        double e = Vector.dotProduct(v,w);

        double D = a*c - b*b;       // always >= 0
        double sc, sN, sD = D;      // sc = sN / sD, default sD = D >= 0
        double tc, tN, tD = D;      // tc = tN / tD, default tD = D >= 0

        // compute the line parameters of the two closest points
        if (D < LineSegment.ZERO) { // the lines are almost parallel
            sN = 0.0;        // force using point P0 on segment S1
            sD = 1.0;        // to prevent possible division by 0.0 later
            tN = e;
            tD = c;
        }
        else {                // get the closest points on the infinite lines
            sN = (b*e - c*d);
            tN = (a*e - b*d);
            if (sN < 0.0) {       // sc < 0 => the s=0 edge is visible
                sN = 0.0;
                tN = e;
                tD = c;
            }
            else if (sN > sD) {  // sc > 1 => the s=1 edge is visible
                sN = sD;
                tN = e + b;
                tD = c;
            }
        }

        if (tN < 0.0) {           // tc < 0 => the t=0 edge is visible
            tN = 0.0;
            // recompute sc for this edge
            if (-d < 0.0)
                sN = 0.0;
            else if (-d > a)
                sN = sD;
            else {
                sN = -d;
                sD = a;
            }
        }
        else if (tN > tD) {      // tc > 1 => the t=1 edge is visible
            tN = tD;
            // recompute sc for this edge
            if ((-d + b) < 0.0)
                sN = 0;
            else if ((-d + b) > a)
                sN = sD;
            else {
                sN = (-d + b);
                sD = a;
            }
        }
        
        // finally do the division to get sc and tc
        sc = (Math.abs(sN) < LineSegment.ZERO ? 0.0 : sN / sD);
        tc = (Math.abs(tN) < LineSegment.ZERO ? 0.0 : tN / tD);

        // get the difference of the two closest points
        //Vector dP = w + (sc * u) - (tc * v);  // = S1(sc) - S2(tc)
        Vector dP = Vector.sum(w, Vector.dotProduct(sc, u), Vector.dotProduct(-tc, v));

        return(dP);   // return the closest distance
    }
    
}
