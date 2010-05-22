package app.dim3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import lib.tools.StopWatch;
import lib.Vector;
import lib.dim3.Capsle;
import lib.dim3.Composite3D;
import lib.dim3.Object3D;

/**
 * Generování 3D kompozitní struktury s válcovými objekty
 * Aplikováno postupné nafukování
 */
public class SlantedTubesBurn {

    public static void main(String args[]) throws IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, FileNotFoundException, CloneNotSupportedException {

        //------------------------------------------------------------------------
        // COMPOSITE SETTINGS                                                START
        //------------------------------------------------------------------------
        Composite3D com = new Composite3D();

        com.setStartX(-50.0);
        com.setStartY(-10.0);
        com.setStartZ(-50.0);
        com.setEndX(600.0);
        com.setEndY(60.0);
        com.setEndZ(600.0);

        com.setStartWorkX(0.0);
        com.setStartWorkY(0.0);
        com.setStartWorkZ(0.0);
        com.setEndWorkX(600.0);
        com.setEndWorkY(50.0);
        com.setEndWorkZ(600.0);
        //------------------------------------------------------------------------
        // COMPOSITE SETTINGS                                                  END
        //------------------------------------------------------------------------


        //------------------------------------------------------------------------
        // COMPOSITE INIT                                                    START
        //------------------------------------------------------------------------
        Capsle temp = new Capsle();        
        double x1, y1, z1, x2, y2, z2;
        Vector start, end;

        start = new Vector();
        end   = new Vector();

        for (int i = 0; i < 100000; i++) {
            // slanted
            x1 = Math.random() * (com.getEndX() - com.getStartX()) + com.getStartX();
            y1 = com.getStartY();
            z1 = Math.random() * (com.getEndZ() - com.getStartZ()) + com.getStartZ();

            start.set(x1, y1, z1);

            x2 = x1 + 30.0 * (Math.random() -0.5) + 50.0;
            y2 = com.getEndY();
            z2 = z1 + 30.0 * (Math.random() -0.5);

            end.set(x2, y2, z2);

            temp = new Capsle(start.clone(), end.clone(), 3.5);

            com.add(temp.clone());
        }

        // export composite
        com.export("TUBES/600x50x600-init100k/r-5.0_slanted-rand5-shift50_INIT_num-" + com.getObjects().size() + ".pov", "workarea");
        com.save("TUBES/600x50x600-init100k/r-5.0_slanted-rand5-shift50_INIT_num-" + com.getObjects().size() + ".ser");

        System.out.println("INIT FINISH --- NUM = " + com.getObjects().size());
        //------------------------------------------------------------------------
        // COMPOSITE INIT                                                    START
        //------------------------------------------------------------------------



        //------------------------------------------------------------------------
        // BLOW                                                              START
        //------------------------------------------------------------------------
        Vector tempXAxe;
        Vector tempYAxe;
        Vector tempCenter;
        boolean all;
        double coefs[] = {1.20, 1.15, 1.10, 1.05, 1.025};
        double coef;
        int counter = 0;
        Capsle cTemp = new Capsle();
        boolean addIt;
        ArrayList<Integer> canBlow = new ArrayList<Integer>();

        for (int i = 0; i < 5; i++) {
            coef = coefs[i];

            for (int k = 0; k < com.getObjects().size(); k++) {
                canBlow.add(k);
            }

            all = true;

            while (all) {
                all = false;
                counter++;

                System.out.println(coef + " GROW");

                
                for (int eToGrow = 0; eToGrow < canBlow.size(); eToGrow++) {
                    System.out.println(eToGrow);

                    cTemp = (Capsle) com.getObjects().get(eToGrow).clone();

                    // grow process
                    cTemp.blow(coef, 1.0);

                    // control with all other ellipses in structure
                    addIt = true;

                    for (int q = 0; q < com.getObjects().size(); q++) {
                        if (q != eToGrow) {                            
                            if (!Object3D.isOverlap(cTemp, com.getObjects().get(q).clone())) {
                                addIt = addIt && true;
                            }
                            else {        
                                addIt = addIt && false;
                                canBlow.remove(eToGrow);
                                break;
                            }
                        }
                    }

                    // add to structure
                    if (addIt) {
                        com.getObjects().set(eToGrow, cTemp);
                        all = true;
                    }
                }
            }
        }

        // export composite
        com.export("TUBES/600x50x600-init100k/r-5.0_slanted-rand5-shift50_BLOW.pov", "workarea");
        com.save("TUBES/600x50x600-init100k/r-5.0_slanted-rand5-shift50_BLOW.ser");

        System.out.println("BLOW FINISH");
        //------------------------------------------------------------------------
        // BLOW                                                                END
        //------------------------------------------------------------------------



        //------------------------------------------------------------------------
        // BURN                                                              START
        //------------------------------------------------------------------------
        com.burn(1.025, 1.0);

        // export composite
        com.export("TUBES/600x50x600-init100k/r-5.0_slanted-rand5-shift50_BURN-7-1.025.pov", "workarea");
        com.save("TUBES/600x50x600-init100k/r-5.0_slanted-rand5-shift50_BURN-7-1.025.ser");

        System.out.println("BURN FINISH");
        //------------------------------------------------------------------------
        // BURN                                                                END
        //------------------------------------------------------------------------



        //------------------------------------------------------------------------
        // MEASURE                                                           START
        //------------------------------------------------------------------------
        // filling factor
        double ff;
        StopWatch sw = new StopWatch();

        sw.start();
        ff = com.gainFF(100000);
        sw.stop();

        System.out.println("FF (100000) = " + ff);
        System.out.println("time (100000) = " + sw.getElapsedTime() + " ms");
        //------------------------------------------------------------------------
        // MEASURE                                                             END
        //------------------------------------------------------------------------

    }

}
