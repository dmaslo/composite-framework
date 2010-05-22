package lib.dim2;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import org.ho.yaml.Yaml;
import lib.Vector;
import lib.tools.Histogram;

/**
 * <h1>Knihovní třída pro práci s celým kompozitem ve 2D prostoru</h1>
 *
 * @author Daniel Máslo <daniel.maslo@gmail.com>
 * @since JDK 1.5
 * @version 2.0
 */
public class Composite2D implements Serializable, Cloneable {

    // proměnné
    /**
     * unikátní id pro serializaci
     * zrcadlí verzi třídy/objektu
     */
    private static final long serialVersionUID = 2L;

    /** začátek (osa x) oblasti pro generování */
    private double startX;

    /** konec (osa x) oblasti pro generování */
    private double endX;

    /** začátek (osa y) oblasti pro generování */
    private double startY;

    /** konec (osa y) oblasti pro generování */
    private double endY;

    /** začátek (osa x) pracovní oblasti pro generování */
    private double startWorkX;

    /** konec (osa x) pracovní oblasti pro generování */
    private double endWorkX;

    /** začátek (osa y) pracovní oblasti pro generování */
    private double startWorkY;

    /** konec (osa y) pracovní oblasti pro generování */
    private double endWorkY;

    /** kolekce všech objektů (inkluzí) v kompozitu */
    private ArrayList<Object2D> objects = new ArrayList<Object2D>();


    // gettery a settery
    public double getEndWorkX() {
        return endWorkX;
    }

    public void setEndWorkX(double endWorkX) {
        this.endWorkX = endWorkX;
    }

    public double getEndWorkY() {
        return endWorkY;
    }

    public void setEndWorkY(double endWorkY) {
        this.endWorkY = endWorkY;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public ArrayList<Object2D> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object2D> objects) {
        this.objects = objects;
    }

    public double getStartWorkX() {
        return startWorkX;
    }

    public void setStartWorkX(double startWorkX) {
        this.startWorkX = startWorkX;
    }

    public double getStartWorkY() {
        return startWorkY;
    }

    public void setStartWorkY(double startWorkY) {
        this.startWorkY = startWorkY;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }


    // překryté metody
    @Override
    public Composite2D clone() throws CloneNotSupportedException {
        Composite2D res = new Composite2D();

        res.objects = (ArrayList<Object2D>) this.objects.clone();
        res.startX  = this.startX;
        res.startY  = this.startY;
        res.startWorkX  = this.startWorkX;
        res.startWorkY  = this.startWorkY;
        res.endX  = this.endX;
        res.endY  = this.endY;
        res.endWorkX  = this.endWorkX;
        res.endWorkY  = this.endWorkY;
        
        return(res);
    }


    // konstruktory
    /**
     * prázdný (výchozí) konstruktor
     */
    public Composite2D() {
        this.objects = new ArrayList<Object2D>();
        this.startX  = 0.0;
        this.startY  = 0.0;        
        this.startWorkX  = 0.0;
        this.startWorkY  = 0.0;        
        this.endX  = 0.0;
        this.endY  = 0.0;        
        this.endWorkX  = 0.0;
        this.endWorkY  = 0.0;        
    }

    /**
     * konstruktor z kolekce
     *
     * @deprecated
     *
     * @param ArrayList&lt;Object2D&gt; kolekce objektů
     */
    public Composite2D(ArrayList<Object2D> objects_) {
        if (objects_ != null) {
            this.objects  = objects_;
        }
        else {
            this.objects  = new ArrayList<Object2D>();
        }

        this.startX  = 0.0;
        this.startY  = 0.0;        
        this.startWorkX  = 0.0;
        this.startWorkY  = 0.0;        
        this.endX  = 0.0;
        this.endY  = 0.0;        
        this.endWorkX  = 0.0;
        this.endWorkY  = 0.0;
    }

    /**
     * kopírovací konstruktor
     *
     * @param Composite2D original
     */
    public Composite2D(Composite2D c_) {
        this.objects = (ArrayList<Object2D>) c_.objects.clone();
        this.startX  = c_.startX;
        this.startY  = c_.startY;        
        this.startWorkX  = c_.startWorkX;
        this.startWorkY  = c_.startWorkY;        
        this.endX  = c_.endX;
        this.endY  = c_.endY;        
        this.endWorkX  = c_.endWorkX;
        this.endWorkY  = c_.endWorkY;        
    }


    // veřejné metody
	/**
	 * Přplňovací metoda generování náhodné struktury
	 *
	 * @param ArrayList&lt;Object2D&gt; kolekce objektů
	 *
	 * @throws CloneNotSupportedException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
    public void initOF(ArrayList<Object2D> objects_) throws CloneNotSupportedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object2D o1;
        Object2D o2;

		int count = objects_.size();

		for (int i = 0; i < count-1; i++) {
            for (int j = i+1; j < count; j++) {
                o1 = (Object2D) objects_.get(i);
                o2 = (Object2D) objects_.get(j);

                if (Object2D.isOverlap(o1, o2)) {
                    objects_.set(j, objects_.get(count-1).clone());
                    count--;
                    j--;
                }
            }
        }

		this.objects.addAll(objects_.subList(0, count));
    }

	/**
	 * přidání objektu do kompozitu
	 *
	 * objekt se vkládá pouze pokud není v kolizi s jiným, již vloženým, objektem
	 *
	 * @param Object2D vkládaný objekt
	 *
	 * @return boolean : true, pokud byl objekt vložen, jinak false
	 *
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
    public boolean add(Object2D object_) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean addIt = true;

        Object2D o;

        for (int i = 0; i < this.objects.size(); i++) {
            o = this.objects.get(i);

            if (Object2D.isOverlap(object_, o)) {
                addIt = false;
                break;
            }
        }

        if (addIt) {
            this.objects.add(object_.clone());
        }

        return(addIt);
    }

	/**
	 * přidání objektu s difúzní zónou do kompozitu
	 *
	 * objekt se vkládá pouze pokud není v kolizi s jiným, již vloženým, objektem - VČETNĚ DIFÚZNÍCH ZÓN
	 *
	 * @param Object2D vkládaný objekt
	 * @param double difúzní zóna vkládaného objektu
	 *
	 * @return boolean : true, pokud byl objekt vložen, jinak false
	 *
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public boolean addWithDiffZone(Object2D object_, double diff_) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		boolean addIt = true;

        Object2D o;

        for (int i = 0; i < this.objects.size(); i++) {
            o = this.objects.get(i);

			if (Object2D.isOverlap(object_, o)) {
				addIt = false;
				break;
			}
			else if (Object2D.distance(object_, o).gainSize() < diff_) {
				addIt = false;
				break;
			}
        }

        if (addIt) {
            this.objects.add(object_.clone());
        }

        return(addIt);
    }

    /**
     * uložení kompletního kompozitu do serializačního souboru
     *
     * @param String jméno souboru (koncovka .ser nebo .yml)
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws java.lang.CloneNotSupportedException
     */
    public void save(String fileName_) throws FileNotFoundException, IOException, CloneNotSupportedException {
        if (fileName_.endsWith(".ser")) {
            File fw = new File(fileName_);
            FileOutputStream fos = new FileOutputStream(fw);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this);

            oos.close();
        }
        if (fileName_.endsWith(".yml")) {
            Yaml.dump(this.clone(), new File(fileName_));
        }
    }

    /**
     * načtení kompletního kompozitu ze serializačního souboru
     *
     * @param String jméno souboru
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public void load(String fileName_) throws FileNotFoundException, IOException, ClassNotFoundException {
        Composite2D res = new Composite2D();

        if (fileName_.endsWith(".ser")) {
            File fr = new File(fileName_);
            FileInputStream fis = new FileInputStream(fr);
            ObjectInputStream ois = new ObjectInputStream(fis);

            res = (Composite2D) ois.readObject();
        }
        if (fileName_.endsWith(".yml")) {
            res = (Composite2D) Yaml.load(new File(fileName_));
        }

        this.objects    = (ArrayList<Object2D>) res.objects.clone();
        this.endWorkX   = res.endWorkX;
        this.endWorkY   = res.endWorkY;
        this.endX       = res.endX;
        this.endY       = res.endY;
        this.startWorkX = res.startWorkX;
        this.startWorkY = res.startWorkY;
        this.startX     = res.startX;
        this.startY     = res.startY;
    }

    /**
     * obsah PRACOVNÍ OBLASTI
     *
     * @return double : obsah pracovn9 oblasti
     */
    public double gainVolume() {
        return( (this.endWorkX - this.startWorkX) * (this.endWorkY - this.startWorkY) );
    }

    /**
     * faktor plnění pracovní oblasti (metoda MC)
     *
     * @param int počet MC kroků (nástřelů do oblasti)
     *
     * @return double : faktor plnění
     */
    public double gainFF(int n_) throws CloneNotSupportedException {
        int in = 0;
        Vector temp = new Vector();
        Object2D o;

        for (int i = 0; i < n_; i++) {
            temp.set(Math.random() * (this.endWorkX - this.startWorkX) + this.startWorkX, Math.random() * (this.endWorkY - this.startWorkY) + this.startWorkY, 0.0);

            for (int j = 0; j < this.objects.size(); j++) {
                o = this.objects.get(j);

                if (o.isIn(temp)) {
                    in++;
                }
            }
        }

        return((double) in / (double) n_);
    }

	/**
	 * distribuce nejbližších sousedů
	 *
	 * @param String typ distribuce (střed-střed nebo hrana-hrana)
	 * @param double minimum histogramů
	 * @param double maximum histogramů
	 * @param int dělení histogramů
	 * @param int počet nejbližších sousedů
	 *
	 * @return Histogram[] : histogramy nejbližších sousedů
	 *
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Histogram[] gainDNN(String type_, double min_, double max_, int division_, int numOfNeighbours_) throws NoSuchMethodException, InvocationTargetException, IllegalArgumentException, IllegalAccessException {
		Histogram[] res        = new Histogram[numOfNeighbours_];
		ArrayList<Double> dist = new ArrayList<Double>();

		// init Histograms
		for (int k = 0; k < res.length; k++) {			
			res[k] = new Histogram(min_, max_, division_);
		}

		if (type_.equalsIgnoreCase("center")) {
			for (int i = 0; i < this.objects.size(); i++) {
				for (int j = 0; j < this.objects.size(); j++) {
					if (i != j) {
						dist.add(Object2D.centerDistance(this.objects.get(i), this.objects.get(j)).gainSize());
					}
				}
		
				Collections.sort(dist);

				for (int k = 0; k < res.length; k++) {
					res[k].add(dist.get(k));
				}

				dist.clear();
			}
		}

		if (type_.equalsIgnoreCase("side")) {
			for (int i = 0; i < this.objects.size(); i++) {
				for (int j = 0; j < this.objects.size(); j++) {
					if (i != j) {
						dist.add(Object2D.distance(this.objects.get(i), this.objects.get(j)).gainSize());
					}
				}

				Collections.sort(dist);

				for (int k = 0; k < res.length; k++) {
					res[k].add(dist.get(k));
				}

				dist.clear();
			}
		}		

		// norm histograms
		for (int k = 0; k < res.length; k++) {
			res[k].norm(this.objects.size());
		}

		return(res);
	}

	/**
     * vizualizační export
     *
     * @param String jméno souboru (koncovka .svg nebo .pov)
     * @param String modifikátory zpracovaných dat
     *
     * @throws java.io.IOException
     */
    public void export(String fileName_, String modif_) throws IOException {
        // SVG export
        if (fileName_.endsWith(".svg")) {
            StringBuffer res  = new StringBuffer();

            res.append("<?xml version=\"1.0\" standalone=\"no\" ?>\n");
            res.append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n");
            res.append("<svg width=\"" + (this.getEndWorkX() - this.getStartWorkX()) + "\" height=\"" + (this.getEndWorkY() - this.getStartWorkY()) + "\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n");
            res.append("<title>SVG export</title>\n");
            res.append("<desc></desc>\n\n");

            // objects
            for (int i = 0; i < this.objects.size(); i++) {
                res.append(this.objects.get(i).export("svg"));
            }

            res.append("</svg>");

            // write to file
            File fw = new File(fileName_);
            FileWriter fWriter = new FileWriter(fw);

            fWriter.write(res.toString());

            fWriter.close();
        }

        // POV-Ray export
        if (fileName_.endsWith(".pov")) {
            double centerX = (this.endWorkX - this.startWorkX) / 2.0;
            double centerY = (this.endWorkY - this.startWorkY) / 2.0;

            // components
            StringBuffer header    = new StringBuffer();
            StringBuffer camera    = new StringBuffer();
            StringBuffer light     = new StringBuffer();
            StringBuffer box       = new StringBuffer();
            StringBuffer objects   = new StringBuffer();
            StringBuffer diffStart = new StringBuffer();
            StringBuffer diffEnd   = new StringBuffer();

            // header
            header.append("#include \"shapes.inc\"\n");
            header.append("#include \"colors.inc\"\n");
            header.append("#include \"textures.inc\"\n\n");
            header.append("background {\n");
            header.append("\tcolor rgb <1,1,1>\n");
            header.append("}\n\n");

            // camera
            camera.append("camera {\n");
            camera.append("\torthographic\n");
            camera.append("\tlocation <" + centerX + ", " + centerY + ", " + (0.6*(endX - startX + endY - startY)) + ">\n");
            camera.append("\tlook_at <" + centerX + ", " + centerY + ", 0>\n");
            camera.append("}\n\n");

            // light
            light.append("light_source {<" + centerX + ", " + centerY + ", " + (1.2*centerX + 1.2*centerY) + "> color rgb <2,2,2>}\n\n");

            // difference start
            diffStart.append("box {<" + this.startWorkX + ", " + this.startWorkY + ", 0.0> <" + this.endWorkX + ", " + this.endWorkY + ", 0> texture{ pigment { color rgb <0.0, 0.0, 0.0> }}}\n\n");
            diffStart.append("difference {\n\n");

            // box
            box.append("box {<" + this.startWorkX + ", " + this.startWorkY + ", 0.0> <" + this.endWorkX + ", " + this.endWorkY + ", 0> texture{ pigment { color rgb <0.6, 0.6, 1.0> }}}\n\n");

            // objects
            for (int i = 0; i < this.objects.size(); i++) {
                objects.append(this.objects.get(i).export("pov"));
            }

            // difference end
            diffEnd.append("}\n");

            // write to file
            File fw = new File(fileName_);
            FileWriter fWriter = new FileWriter(fw);

            fWriter.write(header.toString());
            fWriter.write(camera.toString());
            fWriter.write(light.toString());

            if (modif_.equals("difference")) {
                fWriter.write(diffStart.toString());
            }

            fWriter.write(box.toString());
            fWriter.write(objects.toString());

            if (modif_.equals("difference")) {
                fWriter.write(diffEnd.toString());
            }

            fWriter.close();
        }
    }

	/**
	 * hoření struktury
	 *
	 * metoda volaná nad podperkolační strukturou zajišťuje vytvoření
	 * nekonečného clusteru
	 *
	 * @param double nafukovací koeficienty
	 *
	 * @throws CloneNotSupportedException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
    public void burn(double... blowCoefs_) throws CloneNotSupportedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ArrayList<Integer> burning = new ArrayList<Integer>();
        ArrayList<Integer> scaleable = new ArrayList<Integer>();
        boolean goOnMain = true;
        int randIndex = -1;

        // no burn
        for (int i = 0; i < this.objects.size(); i++) {
            this.objects.get(i).getBurn().put("num", 0);
            this.objects.get(i).getBurn().put("from", 0);
            this.objects.get(i).getBurn().put("blow", 0);
            scaleable.add(i);
        }

        for (int i = 0; i < this.objects.size(); i++) {
            if ( (this.objects.get(i).gainMinY() > (this.startWorkY)) && (this.objects.get(i).gainMaxY() < (this.endWorkY)) ) {
                if ( (this.objects.get(i).gainMinX() < (this.startWorkX)) && (this.objects.get(i).gainMaxX() > (this.startWorkX)) ) {
                    this.objects.get(i).getBurn().put("num", 1);
                    this.objects.get(i).getBurn().put("from", 4);
                    burning.add(i);
                    this.objects.get(i).setColor(new Color(255, 0, 255));
                }
                if ( (this.objects.get(i).gainMinX() < (this.endWorkX)) && (this.objects.get(i).gainMaxX() > (this.endWorkX)) ) {
                    this.objects.get(i).getBurn().put("num", 1);
                    this.objects.get(i).getBurn().put("from", 2);
                    burning.add(i);
                    this.objects.get(i).setColor(new Color(255, 0, 255));
                }
            }
        }


        while (goOnMain) {
//            randIndex++;
//            randIndex = randIndex % this.objects.size();
            randIndex = (int) Math.floor(this.objects.size() * Math.random());

            if ((Integer) this.objects.get(randIndex).getBurn().get("blow") < 4) {
            if (scaleable.contains(randIndex)) {
                //System.out.println(randIndex);
                System.out.println(burning.size());

                if ( (this.objects.get(randIndex).gainMaxY() > (this.startWorkY)) && (this.objects.get(randIndex).gainMinY() < (this.endWorkY)) && (this.objects.get(randIndex).gainMaxX() > (this.startWorkX)) && (this.objects.get(randIndex).gainMinX() < (this.endWorkX)) ) {
                    this.objects.get(randIndex).blow(blowCoefs_);
                    this.objects.get(randIndex).getBurn().put("blow", (Integer) this.objects.get(randIndex).getBurn().get("blow") + 1);

                    for (int j = 0; j < burning.size(); j++) {
                        //if (this.objects.get(randIndex).isOverlap(this.objects.get(burning.get(j)))) {
                        if (Object2D.isOverlap(this.objects.get(randIndex), this.objects.get(burning.get(j)))) {
                            if (!burning.contains(randIndex)) {
                                this.objects.get(randIndex).getBurn().put("num", (Integer) this.objects.get(burning.get(j)).getBurn().get("num") + 1);
                                this.objects.get(randIndex).getBurn().put("from", this.objects.get(burning.get(j)).getBurn().get("from"));
                                burning.add(randIndex);
                                scaleable.remove(burning.get(j));

                                /////////////////////////////////////////////////////////////////

                                boolean goOn = true;

                                while (goOn) {
                                    goOn = false;

                                    for (int count = 0; count < this.objects.size(); count++) {
                                        for (int burnCount = 0; burnCount < burning.size(); burnCount++) {
                                            if ( (this.objects.get(count).gainMaxY() > (this.startWorkY)) && (this.objects.get(count).gainMinY() < (this.endWorkY)) && (this.objects.get(count).gainMaxX() > (this.startWorkX)) && (this.objects.get(count).gainMinX() < (this.endWorkX)) ) {
                                                if ((Integer) this.objects.get(count).getBurn().get("num") == 0) {
                                                    //if (this.objects.get(count).isOverlap(this.objects.get(burning.get(burnCount)))) {
                                                    if (Object2D.isOverlap(this.objects.get(count), this.objects.get(burning.get(burnCount)))) {
                                                        if (!burning.contains(count)) {
                                                            this.objects.get(count).getBurn().put("num", (Integer) this.objects.get(burning.get(burnCount)).getBurn().get("num") + 1);
                                                            this.objects.get(count).getBurn().put("from", this.objects.get(burning.get(burnCount)).getBurn().get("from"));
                                                            burning.add(count);
                                                            //scaleable.remove(burning.get(j));
                                                            goOn = true;
                                                        }
                                                        else {
                                                            if (this.objects.get(count).getBurn().get("from") != this.objects.get(burning.get(burnCount)).getBurn().get("from")) {
                                                                goOn = true;
                                                                goOnMain = false;
                                                            }
                                                        }
                                                        this.objects.get(count).setColor(new Color(255, 0, 255));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                /////////////////////////////////////////////////////////////////
                            }
                            else {
                                if (this.objects.get(randIndex).getBurn().get("from") != this.objects.get(burning.get(j)).getBurn().get("from")) {
                                    goOnMain = false;
                                }
                            }
                            this.objects.get(randIndex).setColor(new Color(255, 0, 255));
                            //break;
                        }
                    }

                }
            }
        }
        }
    }

	/**
	 * označení a morfologie páteře nekonečného clusteru
	 *
	 * metoda volaná nad perkolující strukturou označuje jednotlivé části
	 * nekonečného clusteru a zpracovává morfologii páteře
	 *
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
    public void backbone() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		//get all one electrod elements
		ArrayList<Integer> oneSide = new ArrayList<Integer>();

        // group all burning objets
        ArrayList<Object2D> burning = new ArrayList<Object2D>();

        for (int i = 0; i < objects.size(); i++) {
            if ( (Integer) objects.get(i).getBurn().get("num") != 0) {
                objects.get(i).getBurn().put("numLeft", 0);
                objects.get(i).getBurn().put("numRight", 0);
                burning.add(objects.get(i));
            }
        }

        //----------------------------------------------------------------------------------------------------------------------------------
        // burning LEFT->RIGHT
        //----------------------------------------------------------------------------------------------------------------------------------
        // init first line
        for (int i = 0; i < burning.size(); i++) {
            if ( (burning.get(i).gainMinX() < (this.startWorkX)) && (burning.get(i).gainMaxX() > (this.startWorkX)) ) {
                burning.get(i).getBurn().put("numLeft", 1);
                burning.get(i).getBurn().put("from", 4);
            }
        }

        // burn
        int parent;
        boolean goOnLeft = true;

        while (goOnLeft) {
            goOnLeft = false;

            for (int i = 0; i < burning.size(); i++) {
                if ( (Integer) burning.get(i).getBurn().get("numLeft") == 0) {
                    parent = this.objects.size();

                    for (int j = 0; j < burning.size(); j++) {
                        if ( ( (Integer) burning.get(j).getBurn().get("numLeft") > 0) && (i != j) ) {
                            //if (burning.get(i).isOverlap(burning.get(j))) {
                            if (Object2D.isOverlap(burning.get(i), burning.get(j))) {
                                parent = Math.min(parent, (Integer) burning.get(j).getBurn().get("numLeft"));
                            }
                        }
                    }

                    if (parent != this.objects.size()) {
                        burning.get(i).getBurn().put("numLeft", parent + 1);
                        goOnLeft = true;
                    }
                }
            }
        }


        //----------------------------------------------------------------------------------------------------------------------------------
        // burning RIGHT->LEFT
        //----------------------------------------------------------------------------------------------------------------------------------
        // init first line
        for (int i = 0; i < burning.size(); i++) {
            if ( (burning.get(i).gainMinX() < (this.endWorkX)) && (burning.get(i).gainMaxX() > (this.endWorkX)) ) {
                burning.get(i).getBurn().put("numRight", 1);
                burning.get(i).getBurn().put("from", 2);
            }
        }

        // burn
        boolean goOnRight = true;

        while (goOnRight) {
            goOnRight = false;

            for (int i = 0; i < burning.size(); i++) {
                if ( (Integer) burning.get(i).getBurn().get("numRight") == 0) {
                    parent = this.objects.size();

                    for (int j = 0; j < burning.size(); j++) {
                        if ( ( (Integer) burning.get(j).getBurn().get("numRight") > 0) && (i != j) ) {
                            //if (burning.get(i).isOverlap(burning.get(j))) {
                            if (Object2D.isOverlap(burning.get(i), burning.get(j))) {
                                parent = Math.min(parent, (Integer) burning.get(j).getBurn().get("numRight"));
                            }
                        }
                    }

                    if (parent != this.objects.size()) {
                        burning.get(i).getBurn().put("numRight", parent + 1);
                        goOnRight = true;
                    }
                }
            }
        }



        // get one side clusters
        for (int i = 0; i < burning.size(); i++) {
            if ( ( (Integer) burning.get(i).getBurn().get("numLeft") == 0) || ( (Integer) burning.get(i).getBurn().get("numRight") == 0) ) {
                burning.get(i).getBurn().put("num", -2);
                burning.get(i).setColor(new Color(0, 255, 0)); /////////////////////
            }
        }


        // get dead ends

		// burn codes:
		//
		// * cislovani stran jako v CSS
		//
		// num  - poradi zapaleni
		// from - z jake strany prvek hori
		// blow - kolikrat byl prvek nafouknut
		//
		// burning  - arraylist horicich elementu
		// numLeft  - poradi zapaleni zleva (from = 4)
		// numRight - poradi zapaleni zprava (from = 2)


		ArrayList<Integer> deadEnds = new ArrayList<Integer>();
		ArrayList<Integer> criticals = new ArrayList<Integer>();
		//ArrayList<Object2D> cropedBurning = new ArrayList<Object2D>();

		for (int i = 0; i < burning.size(); i++) {
			// burn indicator
			boolean burn = false;

			// prepare
			for (int f = 0; f < burning.size(); f++) {			
				burning.get(f).getBurn().put("from", 0);
			}

			// get indexes
			//----------------------------------------------------------------------------------------------------------------------------------
			// burning LEFT->RIGHT
			//----------------------------------------------------------------------------------------------------------------------------------
			// init first line
			for (int x = 0; x < burning.size(); x++) {
				if ( (burning.get(x).gainMinX() < (this.startWorkX)) && (burning.get(x).gainMaxX() > (this.startWorkX)) ) {					
					burning.get(x).getBurn().put("from", 4);
				}
			}

			// burn
			goOnLeft = true;

			while (goOnLeft) {
				goOnLeft = false;

				for (int leftCounter = 0; leftCounter < burning.size(); leftCounter++) {
					if ( (Integer) burning.get(leftCounter).getBurn().get("from") == 0) {
						for (int j = 0; j < burning.size(); j++) {
							if ( (Integer) burning.get(j).getBurn().get("from") != 0) {
								if ( (leftCounter != j) && (i != j) && (leftCounter != i) ) {
									if (Object2D.isOverlap(burning.get(leftCounter), burning.get(j))) {
										burning.get(leftCounter).getBurn().put("from", 4);

										goOnLeft = true;

										// burn thought
										if (burning.get(leftCounter).gainMaxX() > (this.endWorkX)) {
											burn = true;
										}
									}									
								}
							}
						}
					}
				}
			}

			//----------------------------------------------------------------------------------------------------------------------------------
			// burning RIGHT->LEFT
			//----------------------------------------------------------------------------------------------------------------------------------
			// init first line
			for (int y = 0; y < burning.size(); y++) {
				if ( (burning.get(y).gainMinX() < (this.endWorkX)) && (burning.get(y).gainMaxX() > (this.endWorkX)) ) {				
					burning.get(y).getBurn().put("from", 2);
				}
			}

			// burn
			goOnRight = true;

			while (goOnRight) {
				goOnRight = false;

				for (int rightCounter = 0; rightCounter < burning.size(); rightCounter++) {
					if ( (Integer) burning.get(rightCounter).getBurn().get("from") == 0) {
						for (int j = 0; j < burning.size(); j++) {
							if ( (Integer) burning.get(j).getBurn().get("from") != 0) {
								if ( (rightCounter != j) && (i != j) ) {
								//if ( (rightCounter != j) && (i != j) && (rightCounter != i)) {
									if (Object2D.isOverlap(burning.get(rightCounter), burning.get(j))) {
										burning.get(rightCounter).getBurn().put("from", 2);

										goOnRight = true;

										// burn thought
										if (burning.get(rightCounter).gainMinX() < (this.startWorkX)) {
											burn = true;
										}
									}									
								}
							}
						}
					}
				}
			}
			// end fo get indexes

			//this.export("BACKBONE/s_" + i + ".svg", "workarea");

			// extract dead ends			
			for (int q = 0; q < burning.size(); q++) {
				if ( (Integer) burning.get(q).getBurn().get("from") == 0) {					
					if (!deadEnds.contains(q)) {
						deadEnds.add(q);
					}					
				}				
			}

			//System.out.println(i + ": " + burning.get(i).getBurn().get("numLeft") + "\t" + burning.get(i).getBurn().get("numRight") + "\t" + burning.get(i).getBurn().get("from"));
			if (!burn) {
				if (!criticals.contains(i)) {
					criticals.add(i);
				}
			}
		}


		// color dead ends
		for (int i = 0; i < deadEnds.size(); i++) {
			//burning.remove(i-(comp++));
			if ( (Integer) burning.get(deadEnds.get(i)).getBurn().get("num") != 1) {
				burning.get(deadEnds.get(i)).setColor(new Color(0, 0, 255));
			}
		}

		//recolor one side elements
		for (int i = 0; i < burning.size(); i++) {
			if ( (Integer) burning.get(i).getBurn().get("num") == -2) {
				burning.get(i).setColor(new Color(0, 255, 0));
			}
		}

		// color criticals
		//System.out.println("critical = " + criticals.size());

		for (int i = 0; i < criticals.size(); i++) {
			burning.get(criticals.get(i)).setColor(new Color(255, 0, 0));
		}

    }


	/**
	 * míchání s ohledem na mezičásticový potenciál
	 *
	 * @param double délka posunutí (střední volná dráha)
	 *
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void energyMove(double r_) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int k = (int) (Math.random() * this.objects.size());
		Object2D temp = this.objects.get(k);
		double u0 = 0.0;
		double u1 = 0.0;
		Vector dr = Vector.random2D(r_);

		for (int i = 0; i < this.objects.size(); i++) {
			if (i != k) {
				u0 += Composite2D.energy(Object2D.distance(this.objects.get(i).clone(), this.objects.get(k).clone()).gainSize());
			}
		}

		temp.translate(dr);

		for (int i = 0; i < this.objects.size(); i++) {
			if (i != k) {
				u1 += Composite2D.energy(Object2D.distance(this.objects.get(i).clone(), this.objects.get(k).clone()).gainSize());
			}
		}

//		System.out.println("u = " + u0 + "\tu = " + u1);

		if (u1 >= u0) {
			temp.translate(Vector.opposite(dr));
//			System.out.println("****");
		}
	}

	/**
	 * definice mezičásticového potenciálu
	 *
	 * @param double vzdálenost objektů
	 *
	 * @return double : meziobjektový potenciál
	 */
	public static double energy(double r_) {
		if (r_ > 1.0) {			
			return(Math.log(r_));
		}
		else {
			return(Double.POSITIVE_INFINITY);
		}
	}

}
