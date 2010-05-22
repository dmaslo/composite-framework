package lib.dim3;

import java.awt.Color;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import lib.Vector;

/**
 * <h1>Knihovní abstraktní třída pro práci s objekty ve 3D prostoru</h1>
 * <p>
 * Všechny objekty musí implementovat toto rozhraní.
 * </p>
 *
 * @author Daniel Máslo <daniel.maslo@gmail.com>
 * @since JDK 1.5
 * @version 2.0
 */
public abstract class Object3D implements Serializable, Cloneable {

    // proměnné
    /**
     * unikátní id pro serializaci
     * zrcadlí verzi třídy/objektu
     */
    private static final long serialVersionUID = 2L;

    /** informace o stavu hoření objektu - perkolace */
    private HashMap<String, Object> burn = new HashMap<String, Object>();

    /** barva objektu */
    private Color color = new Color(178,153,0);


    // gettery a settery
    public HashMap<String, Object> getBurn() {
        return burn;
    }

    public void setBurn(HashMap<String, Object> burn) {
        this.burn = burn;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    // překryté metody
    @Override
    public abstract Object3D clone();


    // veřejné metody
    /**
     * kontrola kolize dvou objektů
     * 
     * @param Object3D objekt - <strong>nebude změněn</strong>
     * @param Object3D objekt - <strong>nebude změněn</strong>
     * 
     * @return boolean : true, pokud objekty kolidují, jinak false
     */
    public static boolean isOverlap(Object3D o1_, Object3D o2_) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class c1 = o1_.getClass();
        Class c2 = o2_.getClass();

        if (c1.equals(c2)){
            Method m;
            Class[] params = {c1, c2};
            Object[] objs = {o1_, o2_};

            m = c1.getMethod("isOverlap", params);

            return((Boolean) m.invoke(null, objs));
        }
        else {
            throw new UnsupportedOperationException("Nesedí třídy.");
        }
    }

    /**
     * měření orientované vzdálenosti mezi objekty
     *
     * @param Object3D objekt - <strong>nebude změněn</strong>
     * @param Object3D objekt - <strong>nebude změněn</strong>
     *
     * @return Vector : orientovaná vzdálenost mezi objekty
     */
    public static Vector distance(Object3D o1_, Object3D o2_) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class c1 = o1_.getClass();
        Class c2 = o2_.getClass();

        if (c1.equals(c2)){
            Method m;
            Class[] params = {c1, c2};
            Object[] objs = {o1_, o2_};

            m = c1.getMethod("distance", params);

            return((Vector) m.invoke(null, objs));
        }
        else {
            throw new UnsupportedOperationException("Nesedí třídy.");
        }
    }

	/**
     * měření orientované vzdálenosti mezi středy objektů
     *
     * @param Object3D objekt - <strong>nebude změněn</strong>
     * @param Object3D objekt - <strong>nebude změněn</strong>
     *
     * @return Vector : orientovaná vzdálenost mezi středy objektů
     */
    public static Vector centerDistance(Object3D o1_, Object3D o2_) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class c1 = o1_.getClass();
        Class c2 = o2_.getClass();

        if (c1.equals(c2)) {
            Method m;
            Class[] params = {c1, c2};
            Object[] objs = {o1_, o2_};

            m = c1.getMethod("centerDistance", params);

            return((Vector) m.invoke(null, objs));
        }
        else {
            throw new UnsupportedOperationException("Nesedí třídy.");
        }
    }


    // abstraktní metody
    /**
     * nafouknutí objektu, může být řízen více koeficienty
     * jednotlivé objekty mají tuto metodu implementovanou odlišně
     * konkrétní implementace je popsána  v příslušné třídě
     *
     * @param double... koeficienty, určující nafouknutí objektu
     */
    public abstract void blow(double... blowCoefs_);

	/**
	 * rotace objektu
	 *
	 * @param Vector vektor rotace
	 */
	public abstract void rotate(Vector rotate_);

	/**
	 * posunutí objektu
	 *
	 * @param Vector vektor posunutí
	 */
	public abstract void translate(Vector translate_);

    /**
     * reprezentace objektu v daném souborovém formátu
     *
     * @param String mime typ souboru
     *
     * @return String : kód, reprezentují objekt
     */
    public abstract String export(String fileType_);
   
    /**
     * kontrola, zda je bod (parametr) uvnitř nebo na plášti objktu
     *
     * @param Vector bod - <strong>nebude změněn</strong>
     *
     * @return boolean : true, pokud je bod uvnitř nebo na plášti objektu, jinak false
     */
    public abstract boolean isIn(Vector v_);

    /**
     * kontrola, zda je bod (parametr) mimo objekt
     *
     * @param Vector bod - <strong>nebude změněn</strong>
     *
     * @return boolean : true, pokud je bod mimo objekt, jinak false
     */
    public abstract boolean isOut(Vector v_);

    /**
     * nejmenší x-ová souřadnice, na kterou objekt dosahuje
     *
     * @return double : minimální x-ové souřadnice
     */
    public abstract double gainMinX();

    /**
     * nejmenší y-ová souřadnice, na kterou objekt dosahuje
     *
     * @return double : minimální y-ové souřadnice
     */
    public abstract double gainMinY();

    /**
     * nejmenší z-ová souřadnice, na kterou objekt dosahuje
     *
     * @return double : minimální z-ové souřadnice
     */
    public abstract double gainMinZ();

    /**
     * největší x-ová souřadnice, na kterou objekt dosahuje
     *
     * @return double : maximální x-ové souřadnice
     */
    public abstract double gainMaxX();

    /**
     * největší y-ová souřadnice, na kterou objekt dosahuje
     *
     * @return double : maximální y-ové souřadnice
     */
    public abstract double gainMaxY();

    /**
     * největší z-ová souřadnice, na kterou objekt dosahuje
     *
     * @return double : maximální z-ové souřadnice
     */
    public abstract double gainMaxZ();
    
}
