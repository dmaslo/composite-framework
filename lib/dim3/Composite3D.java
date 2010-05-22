package lib.dim3;

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
 * <h1>Knihovní třída pro práci s celým kompozitem ve 3D prostoru</h1>
 *
 * @author Daniel Máslo <daniel.maslo@gmail.com>
 * @since JDK 1.5
 * @version 2.0
 */
public class Composite3D implements Serializable, Cloneable {

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

    /** začátek (osa z) oblasti pro generování */
    private double startZ;

    /** konec (osa z) oblasti pro generování */
    private double endZ;

    /** začátek (osa x) pracovní oblasti pro generování */
    private double startWorkX;

    /** konec (osa x) pracovní oblasti pro generování */
    private double endWorkX;

    /** začátek (osa y) pracovní oblasti pro generování */
    private double startWorkY;

    /** konec (osa y) pracovní oblasti pro generování */
    private double endWorkY;

    /** začátek (osa z) pracovní oblasti pro generování */
    private double startWorkZ;

    /** konec (osa z) pracovní oblasti pro generování */
    private double endWorkZ;

	/** kolekce všech objektů (inkluzí) v kompozitu */
    private ArrayList<Object3D> objects = new ArrayList<Object3D>();


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

    public double getEndWorkZ() {
        return endWorkZ;
    }

    public void setEndWorkZ(double endWorkZ) {
        this.endWorkZ = endWorkZ;
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

    public double getEndZ() {
        return endZ;
    }

    public void setEndZ(double endZ) {
        this.endZ = endZ;
    }

    public ArrayList<Object3D> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object3D> objects) {
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

    public double getStartWorkZ() {
        return startWorkZ;
    }

    public void setStartWorkZ(double startWorkZ) {
        this.startWorkZ = startWorkZ;
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

    public double getStartZ() {
        return startZ;
    }

    public void setStartZ(double startZ) {
        this.startZ = startZ;
    }


    // překryté metody
    @Override
    public Composite3D clone() throws CloneNotSupportedException {
        Composite3D res = new Composite3D();

        res.objects = (ArrayList<Object3D>) this.objects.clone();
        res.startX  = this.startX;
        res.startY  = this.startY;
        res.startZ  = this.startZ;
        res.startWorkX  = this.startWorkX;
        res.startWorkY  = this.startWorkY;
        res.startWorkZ  = this.startWorkZ;
        res.endX  = this.endX;
        res.endY  = this.endY;
        res.endZ  = this.endZ;
        res.endWorkX  = this.endWorkX;
        res.endWorkY  = this.endWorkY;
        res.endWorkZ  = this.endWorkZ;

        return(res);
    }


    // konstruktory
    /**
     * prázdný (výchozí) konstruktor
     */
    public Composite3D() {
        this.objects = new ArrayList<Object3D>();
        this.startX  = 0.0;
        this.startY  = 0.0;
        this.startZ  = 0.0;
        this.startWorkX  = 0.0;
        this.startWorkY  = 0.0;
        this.startWorkZ  = 0.0;
        this.endX  = 0.0;
        this.endY  = 0.0;
        this.endZ  = 0.0;
        this.endWorkX  = 0.0;
        this.endWorkY  = 0.0;
        this.endWorkZ  = 0.0;
    }

    /**
     * konstruktor z kolekce
     *
     * @deprecated
     *
     * @param ArrayList&lt;Object2D&gt; kolekce objektů
     */
    public Composite3D(ArrayList<Object3D> objects_) {
        if (objects_ != null) {
            this.objects  = objects_;
        }
        else {
            this.objects  = new ArrayList<Object3D>();
        }

        this.startX  = 0.0;
        this.startY  = 0.0;
        this.startZ  = 0.0;
        this.startWorkX  = 0.0;
        this.startWorkY  = 0.0;
        this.startWorkZ  = 0.0;
        this.endX  = 0.0;
        this.endY  = 0.0;
        this.endZ  = 0.0;
        this.endWorkX  = 0.0;
        this.endWorkY  = 0.0;
        this.endWorkZ  = 0.0;
    }

    /**
     * kopírovací konstruktor
     *
     * @param Composite2D original
     */
    public Composite3D(Composite3D c_) {
        this.objects = (ArrayList<Object3D>) c_.objects.clone();
        this.startX  = c_.startX;
        this.startY  = c_.startY;
        this.startZ  = c_.startZ;
        this.startWorkX  = c_.startWorkX;
        this.startWorkY  = c_.startWorkY;
        this.startWorkZ  = c_.startWorkZ;
        this.endX  = c_.endX;
        this.endY  = c_.endY;
        this.endZ  = c_.endZ;
        this.endWorkX  = c_.endWorkX;
        this.endWorkY  = c_.endWorkY;
        this.endWorkZ  = c_.endWorkZ;
    }


    // veřejné metody
	/**
	 * Přeplňovací metoda generování náhodné struktury
	 *
	 * @param ArrayList&lt;Object3D&gt; kolekce objektů
	 *
	 * @throws CloneNotSupportedException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
    public void initOF(ArrayList<Object3D> objects_) throws CloneNotSupportedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object3D o1;
        Object3D o2;

        for (int i = 0; i < objects_.size()-1; i++) {
            for (int j = i+1; j < objects_.size(); j++) {
                o1 = (Object3D) objects_.get(i);
                o2 = (Object3D) objects_.get(j);

                if (Object3D.isOverlap(o1, o2)) {
                    objects_.set(j, objects_.get(objects_.size()-1).clone());
                    objects_.remove(objects_.size()-1);
					j--;
                }
            }
        }

        this.objects.addAll(objects_);
    }

	/**
	 * Monte Carlo metoda generování náhodné struktury
	 *
	 * @param ArrayList&lt;Object3D&gt; kolekce objektů
	 *
	 * @throws CloneNotSupportedException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
    public void initMC(ArrayList<Object3D> objects_) throws CloneNotSupportedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object3D o1;
        Object3D o2;

        for (int i = 0; i < objects_.size()-1; i++) {            
            for (int j = i+1; j < objects_.size(); j++) {
                o1 = objects_.get(i);
                o2 = objects_.get(j);

                if (Object3D.isOverlap(o1, o2)) {
                    objects_.set(j,(Object3D) objects_.get(objects_.size()-1).clone());
                    objects_.remove(objects_.size()-1);
                    j--;
                }
            }
        }

        this.objects.addAll(objects_);
    }

	/**
	 * přidání objektu do kompozitu
	 *
	 * objekt se vkládá pouze pokud není v kolizi s jiným, již vloženým, objektem
	 *
	 * @param Object3D vkládaný objekt
	 *
	 * @return boolean : true, pokud byl objekt vložen, jinak false
	 *
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public boolean add(Object3D object_) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean addIt = true;

        Object3D o;

        for (int i = 0; i < this.objects.size(); i++) {
            o = this.objects.get(i);

            if (Object3D.isOverlap(object_, o)) {
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
        Composite3D res = new Composite3D();

        if (fileName_.endsWith(".ser")) {
            File fr = new File(fileName_);
            FileInputStream fis = new FileInputStream(fr);
            ObjectInputStream ois = new ObjectInputStream(fis);

            res = (Composite3D) ois.readObject();
        }
        if (fileName_.endsWith(".yml")) {
            res = (Composite3D) Yaml.load(new File(fileName_));
        }

        this.objects    = (ArrayList<Object3D>) res.objects.clone();
        this.endWorkX   = res.endWorkX;
        this.endWorkY   = res.endWorkY;
        this.endWorkZ   = res.endWorkZ;
        this.endX       = res.endX;
        this.endY       = res.endY;
        this.endZ       = res.endZ;
        this.startWorkX = res.startWorkX;
        this.startWorkY = res.startWorkY;
        this.startWorkZ = res.startWorkZ;
        this.startX     = res.startX;
        this.startY     = res.startY;
        this.startZ     = res.startZ;
    }

    /**
     * objem PRACOVNÍ OBLASTI
     *
     * @return double : obsah pracovn9 oblasti
     */
    public double gainVolume() {
        return( (this.endWorkX - this.startWorkX) * (this.endWorkY - this.startWorkY) * (this.endWorkZ - this.startWorkZ) );
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
        Object3D o;

        for (int i = 0; i < n_; i++) {
            temp.set(Math.random() * (this.endWorkX - this.startWorkX) + this.startWorkX, Math.random() * (this.endWorkY - this.startWorkY) + this.startWorkY, Math.random() * (this.endWorkZ - this.startWorkZ) + this.startWorkZ);

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
						dist.add(Object3D.centerDistance(this.objects.get(i).clone(), this.objects.get(j).clone()).gainSize());
					}
				}

				Collections.sort(dist);

				if (dist.get(0) < 50.0) {
					System.out.println(dist.get(0));
				}

				dist.clear();
			}
		}

		if (type_.equalsIgnoreCase("side")) {
			for (int i = 0; i < this.objects.size(); i++) {
				for (int j = 0; j < this.objects.size(); j++) {
					if (i != j) {
						dist.add(Object3D.distance(this.objects.get(i), this.objects.get(j)).gainSize());
					}
				}

				Collections.sort(dist);

				for (int k = 0; k < res.length; k++) {
					res[k].add(dist.get(k));
				}

				dist.clear();
			}
		}

		if (type_.equalsIgnoreCase("real")) {
			for (int i = 0; i < this.objects.size(); i++) {
				for (int j = 0; j < this.objects.size(); j++) {
					if (i != j) {
						dist.add(Sphere.realDistance((Sphere) this.objects.get(i).clone(), (Sphere) this.objects.get(j).clone()));
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
        // POV-Ray export
        if (fileName_.endsWith(".pov")) {
            double centerX = (this.endWorkX - this.startWorkX) / 2.0;
            double centerY = (this.endWorkY - this.startWorkY) / 2.0;
            double centerZ = (this.endWorkZ - this.startWorkZ) / 2.0;

            // components
            StringBuffer header     = new StringBuffer();
            StringBuffer camera     = new StringBuffer();
            StringBuffer light      = new StringBuffer();
            StringBuffer box        = new StringBuffer();
            StringBuffer particles  = new StringBuffer();
            StringBuffer diffStart  = new StringBuffer();
            StringBuffer diffEnd    = new StringBuffer();
            StringBuffer unionStart = new StringBuffer();
            StringBuffer unionEnd   = new StringBuffer();


            // header
            header.append("#include \"shapes.inc\"\n");
            header.append("#include \"colors.inc\"\n");
            header.append("#include \"textures.inc\"\n\n");
            header.append("background {\n");
            header.append("\tcolor rgb <1,1,1>\n");
            header.append("}\n\n");

            // camera
            camera.append("camera {\n");
            //camera += "\torthographic\n";
            
            //camera.append("\tlocation <" + centerX*2.5 + ", " + centerY*4 + ", " + centerZ*2.5 + ">\n");
			camera.append("\tlocation <" + centerX*2.5 + ", " + centerY + ", " + centerZ*2.5 + ">\n");
            
            camera.append("\tlook_at <0, " + centerY + ", 0>\n");
            camera.append("\trotate <0, 360*clock, 0>\n");
            camera.append("}\n\n");

            // light
            //light += "light_source {<" + centerX*3 + ", " + centerY*3 + ", " + centerZ*3 + "> color rgb <2,2,2>}\n";
            //light += "light_source {<" + centerX*3 + ", " + centerY*3 + ", " + -centerZ + "> color rgb <2,2,2>}\n\n";

            light.append("light_source {<-" + centerX*8 + ", -" + centerY*8 + ", -" + centerZ*8 + "> color rgb <2,2,2>}\n");
            light.append("light_source {<" + centerX*8 + ", -" + centerY*8 + ", " + centerZ*8 + "> color rgb <2,2,2>}\n");
            light.append("light_source {<-" + centerX*8 + ", -" + centerY*8 + ", " + centerZ*8 + "> color rgb <2,2,2>}\n");
            light.append("light_source {<" + centerX*4 + ", " + centerY*4 + ", " + centerZ*4 + "> color rgb <2,2,2>}\n\n");

            // union start
            unionStart.append("union {\n");

            // difference start
            diffStart.append("box {<" + this.startWorkX + ", " + this.startWorkY + ", " + this.startWorkZ + "> <" + this.endWorkX + ", " + this.endWorkY + ", " + this.endWorkZ + "> texture{ pigment { color rgb <0.0, 0.0, 0.0> }}}\n\n");
            diffStart.append("difference {\n\n");

            // box
            //box.append("box {<" + this.startWorkX + ", " + this.startWorkY + ", " + this.startWorkZ + "> <" + this.endWorkX + ", " + this.endWorkY + ", " + this.endWorkZ + "> texture{ pigment { color rgbf <0.0, 0.8, 1.0, 0.9> }}}\n\n");
			box.append("box {<" + this.startWorkX + ", " + this.startWorkY + ", " + this.startWorkZ + "> <" + this.endWorkX + ", " + (this.startWorkY + 0.01) + ", " + this.endWorkZ + "> texture{ pigment { color rgbf <0.0, 0.8, 1.0, 0.9> }}}\n\n");
			box.append("box {<" + this.startWorkX + ", " + this.endWorkY + ", " + this.startWorkZ + "> <" + this.endWorkX + ", " + (this.endWorkY + 0.01) + ", " + this.endWorkZ + "> texture{ pigment { color rgbf <0.0, 0.8, 1.0, 0.9> }}}\n\n");

            // objects
            if (modif_.contains("workarea")) {
                for (int i = 0; i < this.objects.size(); i++) {
                    if ( (this.objects.get(i).gainMaxX() > this.startWorkX) && (this.objects.get(i).gainMaxY() > this.startWorkY) && (this.objects.get(i).gainMaxZ() > this.startWorkZ) && (this.objects.get(i).gainMinX() < this.endWorkX) && (this.objects.get(i).gainMinY() < this.endWorkY) && (this.objects.get(i).gainMinZ() < this.endWorkZ) ) {
                        particles.append(this.objects.get(i).export("pov"));
                    }
                }
            }
			// horizontální řez - parametr je double
            else if (modif_.contains(".")) {
                double planeY = Double.parseDouble(modif_);

                for (int i = 0; i < this.objects.size(); i++) {
                    if ( (this.objects.get(i).gainMaxX() > this.startWorkX) && (this.objects.get(i).gainMaxY() > planeY) && (this.objects.get(i).gainMaxZ() > this.startWorkZ) && (this.objects.get(i).gainMinX() < this.endWorkX) && (this.objects.get(i).gainMinY() < planeY) && (this.objects.get(i).gainMinZ() < this.endWorkZ) ) {
                        particles.append(this.objects.get(i).export("pov"));
                    }
                }
            }
			// jen hořící objekty
			else if (modif_.contains("burn")) {
                for (int i = 0; i < this.objects.size(); i++) {
                    if ( (Integer) this.objects.get(i).getBurn().get("num") != 0) {
                        particles.append(this.objects.get(i).export("pov"));
                    }
                }
            }
			// bez "jednoelektordů"
			else if (modif_.contains("cluster")) {
                for (int i = 0; i < this.objects.size(); i++) {
					if ( (Integer) this.objects.get(i).getBurn().get("num") != 0) {
						if (this.objects.get(i).getColor().getGreen() != 255) {
							particles.append(this.objects.get(i).export("pov"));
						}
					}
                }
            }
			// bez mrtvých konců
			else if (modif_.contains("live")) {
				for (int i = 0; i < this.objects.size(); i++) {
					if ( (Integer) this.objects.get(i).getBurn().get("num") != 0) {
						if (this.objects.get(i).getColor().getGreen() != 255) {
							if (this.objects.get(i).getColor().getRed() == 255) {
								particles.append(this.objects.get(i).export("pov"));
							}
						}
					}
                }
			}
            else {				
                for (int i = 0; i < this.objects.size(); i++) {
                    particles.append(this.objects.get(i).export("pov"));					
                }
            }

            // difference end
            diffEnd.append("}\n");

            // union end
            unionEnd.append("translate <-"+((this.endWorkX-this.startWorkX)/2.0)+", 0, -"+((this.endWorkZ-this.startWorkZ)/2.0)+">\n");
            unionEnd.append("}\n");


            // write to file
            File fw = new File(fileName_);
            FileWriter fWriter = new FileWriter(fw);

            fWriter.write(header.toString());
            fWriter.write(camera.toString());
            fWriter.write(light.toString());
            fWriter.write(unionStart.toString());

            if (modif_.equals("difference")) {
                fWriter.write(diffStart.toString());
            }

            fWriter.write(box.toString());
            fWriter.write(particles.toString());

            if (modif_.equals("difference")) {
                fWriter.write(diffEnd.toString());
            }

            fWriter.write(unionEnd.toString());

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
    public void burn(double... blowCoefs_) throws CloneNotSupportedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, IllegalArgumentException, InvocationTargetException {
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

        // init burn
        for (int i = 0; i < this.objects.size(); i++) {
            // rozhoduji odkud kam se bude horet
				if ( (this.objects.get(i).gainMinY() < (this.startWorkY)) && (this.objects.get(i).gainMaxY() > (this.startWorkY)) ) {
                //if ( (this.objects.get(i).gainMinX() < (this.startWorkX)) && (this.objects.get(i).gainMaxX() > (this.startWorkX)) ) {
                    this.objects.get(i).getBurn().put("num", 1);
                    this.objects.get(i).getBurn().put("from", 4);
                    burning.add(i);
                    this.objects.get(i).setColor(new Color(255, 0, 255));
                }
                //if ( (this.objects.get(i).gainMinX() < (this.endWorkX)) && (this.objects.get(i).gainMaxX() > (this.endWorkX)) ) {
				if ( (this.objects.get(i).gainMinY() < (this.endWorkY)) && (this.objects.get(i).gainMaxY() > (this.endWorkY)) ) {
                    this.objects.get(i).getBurn().put("num", 1);
                    this.objects.get(i).getBurn().put("from", 2);
                    burning.add(i);
                    this.objects.get(i).setColor(new Color(255, 0, 255));
                }
            //}
        }


        // TODO, automaticke ukonceni v pripade neuspechu
        while (goOnMain) {
//            randIndex++;
//            randIndex = randIndex % this.objects.size();
            randIndex = (int) Math.floor(this.objects.size() * Math.random());

            if ((Integer) this.objects.get(randIndex).getBurn().get("blow") < 6) {
                if (scaleable.contains(randIndex)) {
                    //System.out.println(randIndex);
                    System.out.println(burning.size());

                    if ( (this.objects.get(randIndex).gainMaxY() > (this.startWorkY)) && (this.objects.get(randIndex).gainMinY() < (this.endWorkY)) && (this.objects.get(randIndex).gainMaxX() > (this.startWorkX)) && (this.objects.get(randIndex).gainMinX() < (this.endWorkX)) && (this.objects.get(randIndex).gainMaxZ() > (this.startWorkZ)) && (this.objects.get(randIndex).gainMinZ() < (this.endWorkZ)) ) {
                        this.objects.get(randIndex).blow(blowCoefs_);
                        this.objects.get(randIndex).getBurn().put("blow", (Integer) this.objects.get(randIndex).getBurn().get("blow") + 1);

                        for (int j = 0; j < burning.size(); j++) {
                            //if (this.objects.get(randIndex).isOverlap(this.objects.get(burning.get(j)))) {
                            if (Object3D.isOverlap(this.objects.get(randIndex), this.objects.get(burning.get(j)))) {
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
                                                        if (Object3D.isOverlap(this.objects.get(count), this.objects.get(burning.get(burnCount)))) {
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
        ArrayList<Object3D> burning = new ArrayList<Object3D>();

        for (int i = 0; i < objects.size(); i++) {
            if ( (Integer) objects.get(i).getBurn().get("num") != 0) {
                objects.get(i).getBurn().put("numTop", 0);
                objects.get(i).getBurn().put("numBottom", 0);
                burning.add(objects.get(i));
            }
        }

        //----------------------------------------------------------------------------------------------------------------------------------
        // burning TOP->BOTTOM
        //----------------------------------------------------------------------------------------------------------------------------------
        // init first line
        for (int i = 0; i < burning.size(); i++) {
            if ( (burning.get(i).gainMinY() < (this.startWorkY)) && (burning.get(i).gainMaxY() > (this.startWorkY)) ) {
                burning.get(i).getBurn().put("numTop", 1);
                burning.get(i).getBurn().put("from", 4);
            }
        }

        // burn
        int parent;
        boolean goOnTop = true;

        while (goOnTop) {
            goOnTop = false;

            for (int i = 0; i < burning.size(); i++) {
                if ( (Integer) burning.get(i).getBurn().get("numTop") == 0) {
                    parent = this.objects.size();

                    for (int j = 0; j < burning.size(); j++) {
                        if ( ( (Integer) burning.get(j).getBurn().get("numTop") > 0) && (i != j) ) {
                            //if (burning.get(i).isOverlap(burning.get(j))) {
                            if (Object3D.isOverlap(burning.get(i), burning.get(j))) {
                                parent = Math.min(parent, (Integer) burning.get(j).getBurn().get("numTop"));
                            }
                        }
                    }

                    if (parent != this.objects.size()) {
                        burning.get(i).getBurn().put("numTop", parent + 1);
                        goOnTop = true;
                    }
                }
            }
        }


        //----------------------------------------------------------------------------------------------------------------------------------
        // burning BOTTOM->TOP
        //----------------------------------------------------------------------------------------------------------------------------------
        // init first line
        for (int i = 0; i < burning.size(); i++) {
            if ( (burning.get(i).gainMinY() < (this.endWorkY)) && (burning.get(i).gainMaxY() > (this.endWorkY)) ) {
                burning.get(i).getBurn().put("numBottom", 1);
                burning.get(i).getBurn().put("from", 2);
            }
        }

        // burn
        boolean goOnBottom = true;

        while (goOnBottom) {
            goOnBottom = false;

            for (int i = 0; i < burning.size(); i++) {
                if ( (Integer) burning.get(i).getBurn().get("numBottom") == 0) {
                    parent = this.objects.size();

                    for (int j = 0; j < burning.size(); j++) {
                        if ( ( (Integer) burning.get(j).getBurn().get("numBottom") > 0) && (i != j) ) {
                            //if (burning.get(i).isOverlap(burning.get(j))) {
                            if (Object3D.isOverlap(burning.get(i), burning.get(j))) {
                                parent = Math.min(parent, (Integer) burning.get(j).getBurn().get("numBottom"));
                            }
                        }
                    }

                    if (parent != this.objects.size()) {
                        burning.get(i).getBurn().put("numBottom", parent + 1);
                        goOnBottom = true;
                    }
                }
            }
        }



        // get one side clusters
        for (int i = 0; i < burning.size(); i++) {
            if ( ( (Integer) burning.get(i).getBurn().get("numTop") == 0) || ( (Integer) burning.get(i).getBurn().get("numBottom") == 0) ) {
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
		// from - z jake strany prvek hori: from = 0 (nehori)
		// blow - kolikrat byl prvek nafouknut
		//
		// burning   - arraylist horicich elementu
		// numTop    - poradi zapaleni zhora (from = 4)
		// numBottom - poradi zapaleni zespoda (from = 2)


		ArrayList<Integer> deadEnds = new ArrayList<Integer>();
		ArrayList<Integer> criticals = new ArrayList<Integer>();
		//ArrayList<Object2D> cropedBurning = new ArrayList<Object2D>();

		for (int i = 0; i < burning.size(); i++) {
			// burn indicator
			boolean burn = false;

			// prepare
			for (int f = 0; f < burning.size(); f++) {
				if ((Integer) burning.get(f).getBurn().get("num") != -2) {
					burning.get(f).getBurn().put("from", 0);
				}
			}

			// get indexes
			//----------------------------------------------------------------------------------------------------------------------------------
			// burning BOTTOM->TOP
			//----------------------------------------------------------------------------------------------------------------------------------
			// init first line
			for (int x = 0; x < burning.size(); x++) {
				if ( (burning.get(x).gainMinY() < (this.startWorkY)) && (burning.get(x).gainMaxY() > (this.startWorkY)) ) {
					burning.get(x).getBurn().put("from", 4);
				}
			}

			// burn
			goOnTop = true;

			while (goOnTop) {
				goOnTop = false;

				for (int topCounter = 0; topCounter < burning.size(); topCounter++) {
					if ((Integer) burning.get(topCounter).getBurn().get("from") == 0) {
						for (int j = 0; j < burning.size(); j++) {
							if ( (Integer) burning.get(j).getBurn().get("from") != 0) {
								if ( (topCounter != j) && (i != j) && (topCounter != i) ) {
								//if ( (topCounter != j) && (i != j) ) {
									if (Object3D.isOverlap(burning.get(topCounter), burning.get(j))) {
										burning.get(topCounter).getBurn().put("from", 4);

										goOnTop = true;

										// burn thought
										if (burning.get(topCounter).gainMaxY() > (this.endWorkY)) {
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
			// burning TOP->BOTTOM
			//----------------------------------------------------------------------------------------------------------------------------------
			// init first line
			for (int y = 0; y < burning.size(); y++) {
				if ( (burning.get(y).gainMinY() < (this.endWorkY)) && (burning.get(y).gainMaxY() > (this.endWorkY)) ) {
					burning.get(y).getBurn().put("from", 2);
				}
			}

			// burn
			goOnBottom = true;

			while (goOnBottom) {
				goOnBottom = false;

				for (int bottomCounter = 0; bottomCounter < burning.size(); bottomCounter++) {
					if ((Integer) burning.get(bottomCounter).getBurn().get("from") == 0) {
						for (int j = 0; j < burning.size(); j++) {
							if ( (Integer) burning.get(j).getBurn().get("from") != 0) {
								//if ( (bottomCounter != j) && (i != j) && (bottomCounter != i)) {
								if ( (bottomCounter != j) && (i != j)) {
									if (Object3D.isOverlap(burning.get(bottomCounter), burning.get(j))) {
										burning.get(bottomCounter).getBurn().put("from", 2);

										goOnBottom = true;

										// burn thought
										if (burning.get(bottomCounter).gainMinY() < (this.startWorkY)) {
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

			// extract dead ends
			for (int q = 0; q < burning.size(); q++) {
				if ( (Integer) burning.get(q).getBurn().get("from") == 0) {
					if (!deadEnds.contains(q)) {
						deadEnds.add(q);
					}
				}
			}

			// extract critical
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

}
