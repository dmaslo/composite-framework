package lib.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * <h1>Knihovní třída pro práci s histogramy</h1>
 *
 * @author Daniel Máslo <daniel.maslo@gmail.com>
 * @since JDK 1.5
 * @version 2.0
 */
public class Histogram {

    // proměnné
    /** minimální hodnota, přípustná pro zpracování histogramem */
    private double min;

    /** maximální hodnota, přípustná pro zpracování histogramem */
    private double max;

    /** minimem uzavřený interval */
    private boolean minClose = true;

    /** maximem uzavřený interval */
    private boolean maxClose = true;

    /** dělení histogramu */
    private int division = -1;

    /** počet hodnot, zpracovaných histogramem */
    private int numOfValues = 0;
    
    /** histogram */
    private ArrayList<Double> histogram;


    // gettery and settery
    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        if (this.max < min) {
            throw new IllegalArgumentException("maximum musí být větsí než minimum");
        }
        else {
            this.min = min;
        }
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        if (this.min > max) {
            throw new IllegalArgumentException("maximum musí být větsí než minimum");
        }
        else {
            this.max = max;
        }
    }

    public boolean isMinClose() {
        return minClose;
    }

    public void setMinClose(boolean minClose) {
        this.minClose = minClose;
    }

    public boolean isMaxClose() {
        return maxClose;
    }

    public void setMaxClose(boolean maxClose) {
        this.maxClose = maxClose;
    }

    public int getDivision() {
        return division;
    }

    public void setDivision(int division) {
        if (this.division == -1) {
            this.division = division;

            for (int i = 0; i < division; i++) {
                this.histogram.add(0.0);
            }
        }
        else {
            throw new IllegalAccessError("dělení lze nastavit jen jednou - v konstruktoru");
        }
    }


    // konstruktor
    /**
     * prázdný (výchozí) konstruktor
     */
    public Histogram() {
        this.min = Double.NEGATIVE_INFINITY;
        this.max = Double.POSITIVE_INFINITY;
        this.division = 1;
        this.histogram = new ArrayList<Double>();
    }

    /**
     * základní konstruktor
     *
     * @param double minimum
     * @param double maximum
     * @param int dělení histogramu
     */
    public Histogram(double min_, double max_, int division_) {
        if (min_ < max_) {
            this.min = min_;
            this.max = max_;
            this.division = division_;
            this.histogram = new ArrayList<Double>(division_);

            for (int i = 0; i < division_; i++) {
                this.histogram.add(0.0);
            }
        } else {
            throw new IllegalArgumentException("maximum musí být větsí než minimum");
        }
    }

    /**
     * rozšířený konstruktor
     *
     * @param boolean uzavřenost minima
     * @param double minimum
     * @param double maximum
     * @param boolean uzavřenost maxima
     * @param int dělení
     */
    public Histogram(boolean minClose_, double min_, double max_, boolean maxClose_, int division_) {
        if (min_ < max_) {
            this.min = min_;
            this.max = max_;
            this.minClose = minClose_;
            this.maxClose = maxClose_;
            this.division = division_;
            this.histogram = new ArrayList<Double>(division_);

            for (int i = 0; i < division_; i++) {
                this.histogram.add(0.0);
            }
        } else {
            throw new IllegalArgumentException("maximum musí být větsí než minimum");
        }
    }

    // překryté metody
    @Override
    public Histogram clone() throws CloneNotSupportedException {
        Histogram res = new Histogram(this.minClose, this.min, this.max, this.maxClose, this.division);

        res.histogram = (ArrayList<Double>) this.histogram.clone();

        return (res);
    }

    @Override
    public String toString() {
        String res = new String();
        String minCloseId = new String("(close)");
        String maxCloseId = new String("(close)");

        if (!this.minClose) {
            minCloseId = "(open)";
        }
        if (!this.maxClose) {
            maxCloseId = "(open)";
        }

        res = "Histogram[min: " + this.min + minCloseId + ", max: " + this.max + maxCloseId + ", division: " + this.division + "]";

        return res;
    }

    // veřejné metody
    /**
     * ověření, zda je hodnota v rozsahu histogramu
     *
     * @param double hodnota
     *
     * @return boolean : true pokud je hodnota v rozsahu histogramu, jinak false
     */
    public boolean isInRange(double x_) {
        boolean res = false;

        if ((x_ > min) && (x_ < max)) {
            res = true;
        }
        if (minClose) {
            if (x_ == min) {
                res = true;
            }
        }
        if (maxClose) {
            if (x_ == max) {
                res = true;
            }
        }
        if ((x_ < min) && (x_ > max)) {
            res = false;
        }

        return res;
    }

    /**
     * vynulování celého pole histogramu
     */
    public void resetHistogram() {
        for (int i = 0; i < this.histogram.size(); i++) {
            this.histogram.set(i, 0.0);
        }
    }

    /**
     * přidání jedné hodnoty do histogramu
     *
     * @param double hodnota
     */
    public void add(double x_) {
        this.addToHistogram(x_);
    }

    /**
     * přidání ArrayListu hodnot do histogramu
     *
     * @param ArrayList&lt;Double&gt; : x_
     */
    public void add(ArrayList<Double> x_) {
        for (int i = 0; i < x_.size(); i++) {
            this.addToHistogram(x_.get(i));
        }
    }

    /**
     * přidání libovolného počtu hodnot do histogramu
     *
     * @param double... hodnoty
     */
    public void add(double... x_) {
        for (int i = 0; i < x_.length; i++) {
            this.addToHistogram(x_[i]);
        }
    }

    /**
     * import dat do histogramu ze souboru. Textový soubor musí mít následující formát:
     * (každá hodnota je na samostatném řádku)
     * <code>
     * 0.123<br />
     * 1.26812356<br />
     * 458.215<br />
     * 12.0
     * </code>
     *
     * @param String jméno souboru
     *
     * @throws NumberFormatException
     * @throws IOException
     */
    public void add(String fileName_) throws NumberFormatException, IOException {
        FileReader fr = new FileReader(fileName_);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            this.addToHistogram(Double.parseDouble(line));
        }

        br.close();
    }

    /**
     * normování histogramu počtem hodnot (na jedničku)
     *
     * @return ArrayList&lt;Double&gt; : normovaný histogram
     */
    public ArrayList<Double> norm() {
        ArrayList<Double> res = (ArrayList<Double>) this.histogram.clone();

        for (int i = 0; i < this.histogram.size(); i++) {
            res.set(i, this.histogram.get(i) / (double) this.numOfValues);
        }

        this.histogram = res;

        return (res);
    }

    /**
     * normování histogramu danou hodnotou
     * <strong>histogram (data) jsou DĚLENA parametrem norm_</strong>
     *
     * @param double normovací parametr
     *
     * @return ArrayList&lt;Double&gt; : normovaný histogram
     */
    public ArrayList<Double> norm(double norm_) {
        ArrayList<Double> res = (ArrayList<Double>) this.histogram.clone();

        for (int i = 0; i < this.histogram.size(); i++) {
            res.set(i, this.histogram.get(i) / norm_);
        }

        this.histogram = res;

        return (res);
    }

	/**
     * normování histogramu danou hodnotou násobenou pozicí v histogramu (RDF)
     * <strong>histogram (data) jsou DĚLENA parametrem (norm_ * střed dílčího intervalu)</strong>
     *
     * @param double normovací parametr
     *
     * @return ArrayList&lt;Double&gt; : normovaný histogram
     */
    public ArrayList<Double> normIterate(double norm_) {
        ArrayList<Double> res = (ArrayList<Double>) this.histogram.clone();

		double d = (this.max - this.min) / this.division;

        for (int i = 0; i < this.histogram.size(); i++) {
            res.set(i, this.histogram.get(i) / (((i + 1) * d - (d / 2.0)) * norm_));
        }

        this.histogram = res;

        return (res);
    }

	/**
	 * zprůměrování histogramů
	 *
	 * @param Histogram... množina histogramů
	 *
	 * @return Histogram : histogram, vzniklý zprůměrováním dané množiny
	 */
	public static Histogram merge(Histogram... h) {
		// check inputs
		double min   = h[0].getMin();
		double max   = h[0].getMax();
		int division = h[0].getDivision();

		boolean merge = true;

		for (int i = 1; i < h.length; i++) {
			if ( (h[i].getMin() != min)	|| (h[i].getMax() != max) || (h[i].getDivision() != division) ) {
				merge = false;				
			}
		}

		// merge
		if (merge) {
			Histogram res = new Histogram(min, max, division);

			for (int i = 0; i < h.length; i++) {
				for (int j = 0; j < res.histogram.size(); j++) {
					res.histogram.set(j, h[i].histogram.get(j));
				}
			}

			res.norm(h.length);

			return(res);
		}
		else {
			throw new IllegalArgumentException("histogramy nemají shodné vlastnosti (min, max, dělení)");
		}
	}

	/**
	 * zprůměrování k histogramu
	 *
	 * @param Histogram histogram
	 *
	 * @return Histogram : histogram, vzniklý zprůměrováním this histogramu a daného histogramu
	 */
	public void merge(Histogram h) {
		Histogram temp = Histogram.merge(this, h);

		this.minClose = temp.minClose;
		this.min = temp.min;
		this.max = temp.max;
		this.maxClose = temp.maxClose;
		this.division = temp.division;

        this.histogram = (ArrayList<Double>) temp.histogram.clone();
	}

    /**
     * Uložení histogramu do souboru (gnuplot>> plot thisFile with boxes)
     *
     * @param String jméno souboru
     *
     * @throws java.io.IOException
     */
    public void save(String fileName_) throws IOException {
        FileWriter fw = new FileWriter(new File(fileName_));

        // intervals
        double length = this.max - this.min;
        double delta = length / this.histogram.size();

        // date info
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Date date = new java.util.Date();

        // comment
        fw.write("# THIS FILE WAS CREATED BY Histogram CLASS\n");
        fw.write("# ----------------------------------------\n");
        fw.write("# GENERATED: " + dateFormat.format(date) + "\n");
        fw.write("# \n");
        fw.write("# GENERAL HISTOGRAM INFO:\n");
        fw.write("#     start value:    " + this.min + "\n");
        fw.write("#     end value:      " + this.max + "\n");
        fw.write("#     division:       " + this.division + "\n");
        fw.write("#     delta:          " + delta + "\n");
        fw.write("#     inputed values: " + this.numOfValues + "\n");
        fw.write("#     minimal value:  " + Collections.min(this.histogram) + "\n");
        fw.write("#     maximal value:  " + Collections.max(this.histogram) + "\n");
        fw.write("# ----------------------------------------\n");
        fw.write("# HISTOGRAM DATA:\n");

        // classic output
        for (int i = 0; i < this.histogram.size(); i++) {
            fw.write((i * delta + this.min) + ", " + this.histogram.get(i) + "\n");
        }

        fw.close();
    }

    
    // soukromé metody
    /**
     * vytvoření <code>ArrayList&lt;Double&gt;</code> s histogramem.
     * Všechny hodnoty jsou Integer - ceké, jedná se tedy o klasický histogram
     *
     * @return ArrayList&lt;Integer&gt; : histogram
     */
    private void addToHistogram(double x_) {
        double delta = (this.max - this.min) / (double) this.division;

        // create histogram
        if (isInRange(x_)) {
            for (int j = 1; j <= this.division; j++) {
                if (x_ <= ((j * delta) + this.min)) {
                    this.histogram.set(j - 1, this.histogram.get(j - 1) + 1);
                    break;
                }
            }

            this.numOfValues++;
        }
    }
}
