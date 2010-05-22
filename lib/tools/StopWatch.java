package lib.tools;

/**
 * <h1>Knihovní třída pro měření času</h1>
 *
 * @author Daniel Máslo <daniel.maslo@gmail.com>
 * @since JDK 1.5
 * @version 2.0
 */
public class StopWatch {

    // proměnné
    /** UNIX milisekundy při startu měření */
    private long startTime = 0;

    /** UNIX milisekundy při konci měření */
    private long stopTime = 0;

    /** příznak běhu stopek */
    private boolean running = false;


    // veřejné metody
    /**
     * start měření času
     */
    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    /**
     * konec měření času
     */
    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }

    /**
     * příznak běhu měření času
     * 
     * @return boolean : příznak běhu stopek
     */
    public boolean isRunning() {
        return(this.running);
    }

    /**
     * naměřený čas [ms]
     *
     * @return long : naměřený čas [ms]
     */
    public long getElapsedTime() {
        long elapsed;
        if (running) {
             elapsed = (System.currentTimeMillis() - startTime);
        }
        else {
            elapsed = (stopTime - startTime);
        }
        return(elapsed);
    }

    /**
     * naměřený čas [s]
     *
     * @return long : naměřený čas [s]
     */
    public long getElapsedTimeSecs() {
        long elapsed;
        if (running) {
            elapsed = ((System.currentTimeMillis() - startTime) / 1000);
        }
        else {
            elapsed = ((stopTime - startTime) / 1000);
        }
        return(elapsed);
    }

}
