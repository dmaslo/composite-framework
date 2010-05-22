package lib.dim3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import lib.tools.ObjReader;
import lib.Triangle;
import lib.Vector;

public class Spheroid extends Object3D implements Serializable, Cloneable {

    // proměnné
    /**
     * unikátní id pro serializaci
     * zrcadlí verzi třídy/objektu
     */
    private static final long serialVersionUID = 2L;

    /** střed spheroidu */
    private Vector center;

    /** velikost poloosy ve směru osy x */
    private double xScale;

    /** velikost poloosy ve směru osy y */
    private double yScale;

    /** velikost poloosy ve směru osy z */
    private double zScale;

    /** úhel otočení kolem osy x */
    private double xAngle;

    /** úhel otočení kolem osy y */
    private double yAngle;

    /** úhel otočení kolem osy z */
    private double zAngle;

	/** síť trojúhelníků */
	private ArrayList<Triangle> faces = new ArrayList<Triangle>();


    // gettery a settery
    public Vector getCenter() {
        return center;
    }

//    public void setCenter(Vector center) {
//        this.center = center.clone();
//    }

    public double getXAngle() {
        return xAngle;
    }

//    public void setXAngle(double xAngle) {
//        this.xAngle = xAngle;
//    }

    public double getXScale() {
        return xScale;
    }

//    public void setXScale(double xScale) {
//        this.xScale = xScale;
//    }

    public double getYAngle() {
        return yAngle;
    }

//    public void setYAngle(double yAngle) {
//        this.yAngle = yAngle;
//    }

    public double getYScale() {
        return yScale;
    }

//    public void setYScale(double yScale) {
//        this.yScale = yScale;
//    }

    public double getZAngle() {
        return zAngle;
    }

//    public void setZAngle(double zAngle) {
//        this.zAngle = zAngle;
//    }

    public double getZScale() {
        return zScale;
    }

//    public void setZScale(double zScale) {
//        this.zScale = zScale;
//    }

	public ArrayList<Triangle> getFaces() {
		return faces;
	}

//	public void setFaces(ArrayList<Triangle> faces) {
//		this.faces = faces;
//	}


    // překryté metody
    @Override
    public Object3D clone() {
        Spheroid res = new Spheroid();

        res.center = this.center.clone();
        res.xScale = this.xScale;
        res.yScale = this.yScale;
        res.zScale = this.zScale;
        res.xAngle = this.xAngle;
        res.yAngle = this.yAngle;
        res.zAngle = this.zAngle;

		res.faces  = (ArrayList<Triangle>) this.faces.clone();

        return(res);
    }

    @Override
    public String export(String fileType_) {
        StringBuffer res = new StringBuffer();

        if (fileType_.toLowerCase().endsWith("pov")) {
            res.append("object {\n");
            res.append("\tsphere {\n");
            res.append("\t\t<0, 0, 0>\n");
            res.append("\t\t1.0\n");
            res.append("\t}\n");

            res.append("texture {\n");

            res.append("\tpigment {\n");
            res.append("\t\tcolor rgb <"+((double) this.getColor().getRed()/255.0)+", "+((double) this.getColor().getGreen()/255.0)+", "+((double) this.getColor().getBlue()/255.0)+">\n");
            res.append("\t}\n");

            res.append("}\n");

            res.append("\tfinish {phong 1}\n");

            res.append("scale <" + this.xScale + ", " + this.yScale + ", " + this.zScale + ">\n");
            res.append("rotate <" + (180.0 * (xAngle/Math.PI)) + ", " + (-180.0 * (yAngle/Math.PI)) + ", " + (180.0 * (zAngle/Math.PI)) + ">\n");
            res.append("translate <" + (this.center.x()) + ", " + (this.center.y()) + ", " + (this.center.z()) + ">\n");
            res.append("}\n\n");
        }

        return(res.toString());
    }

    @Override    
    public void blow(double... blowCoef_) {
		this.faces.clear();

        this.xScale *= blowCoef_[0];
        this.yScale *= blowCoef_[0];
        this.zScale *= blowCoef_[0];

		try {
			this.faces.addAll(ObjReader.gainFaces("OBJ/normal-sphere.obj"));
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		} catch (IOException ex) {
			System.out.println("IO exception");
		}

		this.scale(new Vector(this.xScale, this.yScale, this.zScale));
		this.rotate(new Vector(this.xAngle, this.yAngle, this.zAngle));
		this.translate(this.center);
    }

	@Override
	public void translate(Vector translate_) {
		for (int i = 0; i < this.faces.size(); i++) {
			this.faces.get(i).translate(translate_);
		}
	}

	@Override
	public void rotate(Vector rotate_) {
		for (int i = 0; i < this.faces.size(); i++) {
			this.faces.get(i).rotate(rotate_);
		}
	}

    @Override
    // TODO check this method - get from whEllipsoid
    public boolean isIn(Vector v_) {
        double x = v_.x() - this.center.x();
        double y = v_.y() - this.center.y();
        double z = v_.z() - this.center.z();

        // center point
        Vector v = new Vector(x, y, z);

        v.rotate(new Vector(-this.xAngle, -this.yAngle, -this.zAngle));
        v.scale(new Vector(1.0 / this.xScale, 1.0 / this.yScale, 1.0 / this.zScale));

        if (v.gainSizeSquared() <= 1.0) {
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
		double res = this.faces.get(0).getA().getX();

		for (int i = 0; i < this.faces.size(); i++) {
			if (this.faces.get(i).getA().getX() < res) {
				res = this.faces.get(i).getA().getX();
			}
			if (this.faces.get(i).getB().getX() < res) {
				res = this.faces.get(i).getB().getX();
			}
			if (this.faces.get(i).getC().getX() < res) {
				res = this.faces.get(i).getC().getX();
			}
		}

        return(res);
    }

    @Override
    public double gainMinY() {
        double res = this.faces.get(0).getA().getY();

		for (int i = 0; i < this.faces.size(); i++) {
			if (this.faces.get(i).getA().getY() < res) {
				res = this.faces.get(i).getA().getY();
			}
			if (this.faces.get(i).getB().getY() < res) {
				res = this.faces.get(i).getB().getY();
			}
			if (this.faces.get(i).getC().getY() < res) {
				res = this.faces.get(i).getC().getY();
			}
		}

        return(res);
    }

    @Override
	   public double gainMinZ() {
        double res = this.faces.get(0).getA().getZ();

		for (int i = 0; i < this.faces.size(); i++) {
			if (this.faces.get(i).getA().getZ() < res) {
				res = this.faces.get(i).getA().getZ();
			}
			if (this.faces.get(i).getB().getZ() < res) {
				res = this.faces.get(i).getB().getZ();
			}
			if (this.faces.get(i).getC().getZ() < res) {
				res = this.faces.get(i).getC().getZ();
			}
		}

        return(res);
    }

    @Override	
    public double gainMaxX() {
        double res = this.faces.get(0).getA().getX();

		for (int i = 0; i < this.faces.size(); i++) {
			if (this.faces.get(i).getA().getX() > res) {
				res = this.faces.get(i).getA().getX();
			}
			if (this.faces.get(i).getB().getX() > res) {
				res = this.faces.get(i).getB().getX();
			}
			if (this.faces.get(i).getC().getX() > res) {
				res = this.faces.get(i).getC().getX();
			}
		}

        return(res);
    }

    @Override
    public double gainMaxY() {
        double res = this.faces.get(0).getA().getY();

		for (int i = 0; i < this.faces.size(); i++) {
			if (this.faces.get(i).getA().getY() > res) {
				res = this.faces.get(i).getA().getY();
			}
			if (this.faces.get(i).getB().getY() > res) {
				res = this.faces.get(i).getB().getY();
			}
			if (this.faces.get(i).getC().getY() > res) {
				res = this.faces.get(i).getC().getY();
			}
		}

        return(res);
    }

    @Override
    public double gainMaxZ() {
        double res = this.faces.get(0).getA().getZ();

		for (int i = 0; i < this.faces.size(); i++) {
			if (this.faces.get(i).getA().getZ() > res) {
				res = this.faces.get(i).getA().getZ();
			}
			if (this.faces.get(i).getB().getZ() > res) {
				res = this.faces.get(i).getB().getZ();
			}
			if (this.faces.get(i).getC().getZ() > res) {
				res = this.faces.get(i).getC().getZ();
			}
		}

        return(res);
    }


    // konstruktory
    /**
     * prázdný (výchozí) konstruktor
     */
    public Spheroid() {
        super();
    }

    /**
     * plný konstruktor
	 *
	 * xScale_ musí být větší než yScale_ a zScale_
     *
     * @param Vector střed spheroidu - <strong>nebude změněn</strong>
     * @param double velikost poloosy ve směru x
     * @param double velikost poloosy ve směru y
     * @param double velikost poloosy ve směru z
     * @param double úhel otočení kolem osy x
     * @param double úhel otočení kolem osy y
     * @param double úhel otočení kolem osy z     
     */
    public Spheroid(Vector center_, double xScale_, double yScale_, double zScale_, double xAngle_, double yAngle_, double zAngle_) throws FileNotFoundException, IOException {
        super();

        this.center = center_.clone();
        this.xScale = xScale_;
        this.yScale = yScale_;
        this.zScale = zScale_;
        this.xAngle = xAngle_;
        this.yAngle = yAngle_;
        this.zAngle = zAngle_;

		this.faces.addAll(ObjReader.gainFaces("OBJ/normal-sphere.obj"));

		this.scale(new Vector(xScale_, yScale_, zScale_));
		this.rotate(new Vector(xAngle_, yAngle_, zAngle_));
		this.translate(center_);
    }


    // veřejné metody
	/**
     * přeškálování rozměrů sféroidu
     *
     * @param Vector přeškálovací koeficienty
     */
	public void scale(Vector scale_) {
		for (int i = 0; i < this.faces.size(); i++) {
			this.faces.get(i).scale(scale_);
		}
	}

	/**
     * kontrola kolize dvou sféroidů
     *
     * @param Spheroid sféroid - <strong>nebude změněn</strong>
     * @param Spheroid sféroid - <strong>nebude změněn</strong>
     *
     * @return boolean : true, pokud sféroidy kolidují, jinak false
     */
	public static boolean isOverlap(Spheroid s1_, Spheroid s2_) {
        //if (Vector.distanceSquared(s1_.center, s2_.center) >= ((Lib.max(s1_.xScale, s1_.yScale, s1_.zScale) + Lib.max(s2_.xScale, s2_.yScale, s2_.zScale)) * (Lib.max(s1_.xScale, s1_.yScale, s1_.zScale) + Lib.max(s2_.xScale, s2_.yScale, s2_.zScale))) ) {
		if (Vector.distanceSquared(s1_.center, s2_.center) >= ((s1_.xScale + s2_.xScale) * (s1_.xScale + s2_.xScale)) ) {
            return(false);
        }
        else {
            for (int i = 0; i < s1_.faces.size(); i++) {
				for (int j = 0; j < s2_.faces.size(); j++) {
					if (Triangle.isOverlap(s1_.faces.get(i), s2_.faces.get(j))) {
						return(true);
					}
				}
			}

			return(false);
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
        if ( (o1_.getClass().equals(Spheroid.class)) && (o2_.getClass().equals(Spheroid.class)) ) {
            Spheroid s1_ = (Spheroid) o1_;
            Spheroid s2_ = (Spheroid) o2_;

            return(Spheroid.isOverlap(s1_, s2_));
        }
        else {
            throw new UnsupportedOperationException("Neplatné objekty.");
        }
    }

	/**
     * měření orientované vzdálenosti mezi sféroidy
     *
     * @param Spheroid sféroid - <strong>nebude změněn</strong>
     * @param Spheroid sféroid - <strong>nebude změněn</strong>
     *
     * @return Vector : orientovaná vzdálenost mezi sféroidy
     */
    public static Vector distance(Spheroid s1_, Spheroid s2_) {
		Vector res = Triangle.distance(s1_.faces.get(0), s2_.faces.get(0));

        for (int i = 0; i < s1_.faces.size(); i++) {
			for (int j = 0; j < s2_.faces.size(); j++) {
				if (Triangle.distance(s1_.faces.get(i), s2_.faces.get(j)).gainSize() < res.gainSize() ) {
					res = Triangle.distance(s1_.faces.get(i), s2_.faces.get(j)).clone();
				}
			}
		}

		return(res);
    }

}
