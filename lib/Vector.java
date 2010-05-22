package lib;

import java.io.Serializable;

/**
 * <h1>Knihovní třída pro práci s vektory ve 3D prostoru</h1>
 * <p>
 * Instance této třídy reprezentují vektory nebo body ve 3D prostoru.
 * Z hlediska implementace není rozdíl mezi vektorem a bodem, proto
 * není implementována třída Point.
 * </p>
 * <p>
 * Pokud je nějaké metodě předáván parametr Vector jako bod,
 * je zpravidla pojmenován <pre>point_</pre>
 * </p>
 *
 * @author Daniel Máslo <daniel.maslo@gmail.com>
 * @since JDK 1.5
 * @version 2.0
 */
public class Vector implements Serializable, Cloneable {

    // proměnné
    /**
     * unikátní id pro serializaci
     * zrcadlí verzi třídy/objektu
     */
    private static final long serialVersionUID = 2L;

    /** x-ová souřadnice vektoru */
    private double x;

    /** y-ová souřadnice vektoru */
    private double y;

    /** z-ová souřadnice vektoru */
    private double z;


    // konstanty
    /**
     * bázový vektor <strong>i</strong> &lt;1.0, 0.0, 0.0&gt;
     */
    public static final Vector I = new Vector(1.0, 0.0, 0.0);

    /**
     * bázový vektor <strong>j</strong> &lt;0.0, 1.0, 0.0&gt;
     */
    public static final Vector J = new Vector(0.0, 1.0, 0.0);

    /**
     * bázový vektor <strong>k</strong> &lt;0.0, 0.0, 1.0&gt;
     */
    public static final Vector K = new Vector(0.0, 0.0, 1.0);


    // gettery a settery
    public double getX() {
        return x;
    }
    public double x() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    public double y() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }
    public double z() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    
    // překryté metody
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vector other = (Vector) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        if (z != other.z) {
            return false;
        }
        return true;
    }

    @Override
    public Vector clone() {
        Vector res = new Vector();

        res.x = this.x;
        res.y = this.y;
        res.z = this.z;

        return res;
    }

    @Override
    public String toString() {
        return ("Vector[" + this.x + ", " + this.y + ", " + this.z + "]");
    }

    // konstruktory
    /**
     * prázdný (výchozí) konstruktor
     */
    public Vector() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    /**
     * souřadnicový konstruktor
     *
     * @param double x-ová souřadnice vektoru
     * @param double y-ová souřadnice vektoru
     * @param double z-ová souřadnice vektoru
     */
    public Vector(double x_, double y_, double z_) {
        this.x = x_;
        this.y = y_;
        this.z = z_;
    }

    /**
     * kopírovací konstuktor
     *
     * @param Vector originální vektor - <strong>nebudou změněny</strong>
     */
    public Vector(Vector v_) {
        this.x = v_.getX();
        this.y = v_.getY();
        this.z = v_.getZ();
    }

    /**
     * konstruktor vektoru ze dvou bodů
     *
     * @param Vector počáteční bod vektoru - <strong>nebudou změněny</strong>
     * @param Vector koncový bod vektoru - <strong>nebudou změněny</strong>
     */
    public Vector(Vector startPoint_, Vector endPoint_) {
        this.x = endPoint_.x - startPoint_.x;
        this.y = endPoint_.y - startPoint_.y;
        this.z = endPoint_.z - startPoint_.z;
    }

    // veřejné metody
    /**
     * nastavení souřadnic vektoru
     *
     * @param double x-ová souřadnice vektoru
     * @param double y-ová souřadnice vektoru
     * @param double z-ová souřadnice vektoru
     */
    public void set(double x_, double y_, double z_) {
        this.x = x_;
        this.y = y_;
        this.z = z_;
    }

    /**
     * nastavení vektoru mezi dvěma body
     *
     * @param Vector počáteční bod vektoru - <strong>nebudou změněny</strong>
     * @param Vector koncový bod vektoru - <strong>nebudou změněny</strong>
     */
    public void set(Vector startPoint_, Vector endPoint_) {
        this.x = endPoint_.x - startPoint_.x;
        this.y = endPoint_.y - startPoint_.y;
        this.z = endPoint_.z - startPoint_.z;
    }

    /**
     * opačný vektor
     *
     * @param Vector originální vektor - <strong>nebude změněn</strong>
     */
    public static Vector opposite(Vector vector_) {
        Vector res = new Vector();

        res.x = -1.0 * vector_.x;
        res.y = -1.0 * vector_.y;
        res.z = -1.0 * vector_.z;

        return(res);
    }

    /**
     * opačný vektor
     */
    public void opposite() {
        this.x = -1.0 * this.x;
        this.y = -1.0 * this.y;
        this.z = -1.0 * this.z;
    }

    /**
     * součet <em>n</em> vektorů
     *
     * @param Vector... vektory k součtu - <strong>nebudou změněny</strong>
     *
     * @return : Vector součet vektorů
     */
    public static Vector sum(Vector... vectors_) {
        Vector res = new Vector(0.0, 0.0, 0.0);

        for (Vector v_ : vectors_) {
            res.x += v_.x;
            res.y += v_.y;
            res.z += v_.z;
        }

        return(res);
    }

    /**
     * přičtení <em>n</em> vektorů k aktuálnímu vektoru
     *
     * @param Vector... vektory k součtu - <strong>nebudou změněny</strong>
     */
    public void add(Vector... vectors_) {
        for (Vector v_ : vectors_) {
            this.x += v_.x;
            this.y += v_.y;
            this.z += v_.z;
        }
    }

    /**
     * skalární součin vektoru a skaláru
     *
     * @param double skalár
     * @param Vector vektor - <strong>nebude změněn</strong>
     *
     * @return Vector : skalární součin vektoru a skaláru
     */
    public static Vector dotProduct(double d_, Vector vector_) {
        Vector res = new Vector();

        res.setX(d_ * vector_.getX());
        res.setY(d_ * vector_.getY());
        res.setZ(d_ * vector_.getZ());

        return(res);
    }

    /**
     * skalární násobení aktuálního vektoru skalárem
     *
     * @param double skalár
     */
    public void dotProduct(double d_) {
        this.setX(d_ * this.x);
        this.setY(d_ * this.y);
        this.setZ(d_ * this.z);
    }

    /**
     * skalární násobení dvou vektorů
     *
     * @param Vector vektor 1 - <strong>nebude změněn</strong>
     * @param Vector vektor 2 - <strong>nebude změněn</strong>
     *
     * @return double : skalár
     */
    public static double dotProduct(Vector vector1_, Vector vector2_) {
        return(vector1_.x * vector2_.x + vector1_.y * vector2_.y + vector1_.z * vector2_.z);
    }

    /**
     * vektorový součin
     *
     * @param Vector vektor (první - vlevo) - <strong>nebude změněn</strong>
     * @param Vector vektor (druhý - vpravo) - <strong>nebude změněn</strong>
     *
     * @return Vector : vektorový součin
     */
    public static Vector crossProduct(Vector vector1_, Vector vector2_) {
        Vector res = new Vector();

        res.x = vector1_.getY() * vector2_.getZ() - vector1_.getZ() * vector2_.getY();
        res.y = vector1_.getZ() * vector2_.getX() - vector1_.getX() * vector2_.getZ();
        res.z = vector1_.getX() * vector2_.getY() - vector1_.getY() * vector2_.getX();

        return(res);
    }

    /**
     * vektorový součin zleva - aktuální vektor stojí v součinu vpravo
     *
     * @param Vector vektor - <strong>nebude změněn</strong>
     */
    public void leftCrossProduct(Vector vector_) {
        Vector res = new Vector();

        res = crossProduct(vector_, this);

        this.x = res.x;
        this.y = res.y;
        this.z = res.z;
    }

    /**
     * vektorový součin zprava - aktuální vektor stojí v součinu vlevo
     *
     * @param Vector vektor - <strong>nebude změněn</strong>
     */
    public void rightCrossProduct(Vector vector_) {
        Vector res = new Vector();

        res = crossProduct(this, vector_);

        this.x = res.x;
        this.y = res.y;
        this.z = res.z;
    }

    /**
     * velikost vektoru
     *
     * @param Vector vektor - <strong>nebude změněn</strong>
     *
     * @return double : velikost vektoru
     */
    public static double gainSize(Vector vector_) {
        return(Math.sqrt(vector_.x * vector_.x + vector_.y * vector_.y + vector_.z * vector_.z));
    }

    /**
     * velikost vektoru
     *
     * @return double : velikost vektoru
     */
    public double gainSize() {
        return(gainSize(this));
    }

    /**
     * čtverec velikost vektoru - velikost vektoru "na druhou"
     *
     * @param Vector vektor- <strong>nebude změněn</strong>
     *
     * @return double : čtverec velikost vektoru - velikost vektoru "na druhou"
     */
    public static double gainSizeSquared(Vector vector_) {
        return(vector_.x * vector_.x + vector_.y * vector_.y + vector_.z * vector_.z);
    }

    /**
     * čtverec velikost vektoru - velikost vektoru "na druhou"
     *
     * @return double : čtverec velikost vektoru - velikost vektoru "na druhou"
     */
    public double gainSizeSquared() {
        return(gainSizeSquared(this));
    }

    /**
     * úhel mezi vektory
     *
     * @param Vector vektor - <strong>nebude změněn</strong>
     * @param Vector vektor - <strong>nebude změněn</strong>
     *
     * @return double : úhel mezi vektory [rad]
     */
    public static double angle(Vector vector1_, Vector vector2_) {
        return(Math.acos((Vector.dotProduct(vector1_, vector2_)) / (vector1_.gainSize() * vector2_.gainSize())));
    }

    /**
     * projekce aktuálního vektoru do parametru (vektoru)
     *
     * @param Vector vektor - <strong>nebude změněn</strong>
     *
     * @return Vector : projekce
     */
    public Vector projection(Vector vector_) {
        Vector vectorUnit = Vector.adjustSize(1.0, vector_.clone());

        return(Vector.dotProduct(Vector.dotProduct(this, vectorUnit), vectorUnit));
    }

    /**
     * škálovaní vektoru
     *
     * @param double velikost výsledného vektoru
     * @param Vector vektor - <strong>nebude změněn</strong>
     *
     * @return Vector : škálovaný vektor
     */    
    public static Vector adjustSize(double d_, Vector vector_) {
        Vector res = vector_.clone();

        res.dotProduct(d_ / res.gainSize());

        return(res);
    }

    /**
     * škálování aktuálního vektoru
     *
     * @param double nová velikost vektoru
     */
    public void adjustSize(double d_) {
        this.dotProduct(d_ / this.gainSize());
    }

    /**
     * vzdálenost dvou bodů
     *
     * @param Vector bod - <strong>nebude změněn</strong>
     * @param Vector bod - <strong>nebude změněn</strong>
     *
     * @return double : vzdálenost
     */
    public static double distance(Vector point1_, Vector point2_) {
        return (Math.sqrt(distanceSquared(point1_, point2_)));
    }

    /**
     * čtverec vzdálenosti dvou bodů - vzdálenost dvou vektorů "na druhou"
     *
     * @param Vector bod - <strong>nebude změněn</strong>
     * @param Vector bod - <strong>nebude změněn</strong>
     *
     * @return double : čtverec vzdálenosti dvou bodů - vzdálenost dvou vektorů "na druhou"
     */
    public static double distanceSquared(Vector point1_, Vector point2_) {
        return((point1_.x - point2_.x) * (point1_.x - point2_.x) + (point1_.y - point2_.y) * (point1_.y - point2_.y) + (point1_.z - point2_.z) * (point1_.z - point2_.z));
    }

    /**
     * determinant 3x3
     *
     * @param Vector první řádek - <strong>nebude změněn</strong>
     * @param Vector druhý řádek - <strong>nebude změněn</strong>
     * @param Vector třetí řádek - <strong>nebude změněn</strong>
     *
     * @return double : determinant
     */
    public static double determinate(Vector a_, Vector b_, Vector c_) {
        double det = a_.x*b_.y*c_.z + b_.x*c_.y*a_.z + c_.x*a_.y*b_.z - a_.z*b_.y*c_.x - b_.z*c_.y*a_.x	- c_.z*a_.y*b_.x;

        return det;
    }

    /**
     * rotace aktuálního bodu kolem jednotlivách os
     *
     * rotate_[1] ... úhel rotace kolem osy X [rad]
     * rotate_[2] ... úhel rotace kolem osy Y [rad]
     * rotate_[3] ... úhel rotace kolem osy Z [rad]
     *
     * @param Vector vektor - <strong>nebude změněn</strong>
     * @param Vector rotate_ vektor - <strong>nebude změněn</strong>
     *
     * @return Vector : rotovaný vektor
     */
    public static Vector rotate(Vector vector_, Vector rotate_) {
        Vector res = vector_.clone();

        double newX;
        double newY;
        double newZ;

        // x-axe rotate
        if (rotate_.getX() != 0.0) {
            newX = 0.0;
            newY = Math.cos(rotate_.getX()) * res.y - Math.sin(rotate_.getX()) * res.z;
            newZ = Math.sin(rotate_.getX()) * res.y + Math.cos(rotate_.getX()) * res.z;

            res.y = newY;
            res.z = newZ;
        }

        // y-axe rotate
        if (rotate_.getY() != 0.0) {
            newX = Math.cos(rotate_.getY()) * res.x - Math.sin(rotate_.getY()) * res.z;
            newY = 0.0;
            newZ = Math.sin(rotate_.getY()) * res.x + Math.cos(rotate_.getY()) * res.z;

            res.x = newX;
            res.z = newZ;
        }

        // z-axe rotate
        if (rotate_.getZ() != 0.0) {
            newX = Math.cos(rotate_.getZ()) * res.x - Math.sin(rotate_.getZ()) * res.y;
            newY = Math.sin(rotate_.getZ()) * res.x + Math.cos(rotate_.getZ()) * res.y;
            newZ = 0.0;

            res.x = newX;
            res.y = newY;
        }

        return(res);
    }

    /**
     * rotace aktuálního bodu kolem jednotlivách os
     *
     * rotate_[1] ... úhel rotace kolem osy X [rad]
     * rotate_[2] ... úhel rotace kolem osy Y [rad]
     * rotate_[3] ... úhel rotace kolem osy Z [rad]
     *
     * @param Vector rotate_ vektor - <strong>nebude změněn</strong>
     */
    public void rotate(Vector rotate_) {
        double newX;
        double newY;
        double newZ;

        // x-axe rotate
        if (rotate_.getX() != 0.0) {
            newX = 0.0;
            newY = Math.cos(rotate_.getX()) * this.y - Math.sin(rotate_.getX()) * this.z;
            newZ = Math.sin(rotate_.getX()) * this.y + Math.cos(rotate_.getX()) * this.z;

            this.y = newY;
            this.z = newZ;
        }

        // y-axe rotate
        if (rotate_.getY() != 0.0) {
            newX = Math.cos(rotate_.getY()) * this.x - Math.sin(rotate_.getY()) * this.z;
            newY = 0.0;
            newZ = Math.sin(rotate_.getY()) * this.x + Math.cos(rotate_.getY()) * this.z;

            this.x = newX;
            this.z = newZ;
        }

        // z-axe rotate
        if (rotate_.getZ() != 0.0) {
            newX = Math.cos(rotate_.getZ()) * this.x - Math.sin(rotate_.getZ()) * this.y;
            newY = Math.sin(rotate_.getZ()) * this.x + Math.cos(rotate_.getZ()) * this.y;
            newZ = 0.0;

            this.x = newX;
            this.y = newY;
        }
    }

    /**
     * škálování souřadnic vektoru
     *
     * scale_[1] ... násobek x-ové souřadnice
     * scale_[2] ... násobek y-ové souřadnice
     * scale_[3] ... násobek z-ové souřadnice
     *
     * @param Vector vektor - <strong>nebude změněn</strong>
     * @param Vector scale_ vektor - <strong>nebude změněn</strong>
     *
     * @return Vector : škálovaný vektor
     */
    public static Vector scale(Vector vector_, Vector scale_) {
        Vector res = vector_.clone();

        res.x *= scale_.getX();
        res.y *= scale_.getY();
        res.z *= scale_.getZ();

        return(res);
    }

    /**
     * škálování souřadnic vektoru
     *
     * scale_[1] ... násobek x-ové souřadnice
     * scale_[2] ... násobek y-ové souřadnice
     * scale_[3] ... násobek z-ové souřadnice
     *
     * @param Vector scale_ vektor - <strong>nebude změněn</strong>
     */
    public void scale(Vector scale_) {
        this.x *= scale_.getX();
        this.y *= scale_.getY();
        this.z *= scale_.getZ();
    }

    /**
     * Náhodný vektor ve 3D o dané velikosti
     *
     * @param double požadovaná velikost vektoru
     *
     * @return Vector : 3D náhodný vektor
     */
    public static Vector random3D(double size_) {
        double z = 2.0 * Math.random() - 1.0;
        double t = 2.0 * Math.PI * Math.random();
        double w = Math.sqrt(1.0 - z * z);

        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        Vector res = new Vector(x, y, z);

        res.adjustSize(size_);

        return(res);
    }

    /**
     * Náhodný vektor ve 2D (z-ová souřadnice = 0) o dané velikosti
     *
     * @param double požadovaná velikost vektoru
     *
     * @return Vector : 2D náhodný vektor
     */
    public static Vector random2D(double size_) {
        double angle = 2.0 * Math.PI * Math.random();

        Vector res = new Vector(size_ * Math.cos(angle), size_ * Math.sin(angle), 0.0);

        return(res);
    }

    /**
     * Náhodný vektor v 2D (z-ová souřadnice = 0) v kruhu o daném poloměru
     *
     * @param double poloměr kruhu, do kterého se vektor generuje
     *
     * @return Vector : 2D náhodný vektor v kruhu
     */
    public static Vector randomInCircle(double radius_) {
        double angle = 2.0 * Math.PI * Math.random();
        double r = radius_ * Math.sqrt(Math.random());

        Vector res = new Vector(r * Math.cos(angle), r * Math.sin(angle), 0.0);

        return(res);
    }

    /**
     * reprezentace objektu v daném souborovém formátu
     *
     * @param String mime typ souboru
     *
     * @return String : kód, reprezentují objekt
     */
    public String export(String fileType_) {
        StringBuffer res = new StringBuffer();

        if (fileType_.toLowerCase().endsWith("pov")) {
            res.append("<" + this.x + ", " + this.y + ", " + this.z + ">");
        }

        return(res.toString());
    }

}
