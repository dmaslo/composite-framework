package app.tools;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.imageio.ImageIO;

/**
 * Aplikační třída, která získává stupně pokrytí ze
 * série PNG souborů. Stupně pokrytí vypisuje do konzole, zvlášť pro
 * každou vrstvu.
 */
public class CrossSectionFFgainer {

    public static void main(String args[]) throws IOException {

		/**
		 * Společný názek všech souborů s řezy:
		 *
		 * example = example000.png, example001.png, example002.png, ...
		 */
        String initFileName = "3D/10-6-10-mix-rot-cut";

		/**
		 * Kolik řezových PNG je uloženo ve složce
		 */
		int numOfFiles = 100;

        String number = "";
        String fileName;
        int[][] pic;
        int top = 0;
        int bottom = 0;
        int left = 0;
        int right = 0;

        NumberFormat formatter = new DecimalFormat("000");


        for (int i = 1; i < numOfFiles; i++) {
            number = formatter.format(i);
            fileName = initFileName + number + ".png";

            // read png image
            BufferedImage img = ImageIO.read(new File(fileName));


            pic = new int[img.getHeight()][img.getWidth()];

            // create matrix
            for (int h = 0; h < img.getHeight(); h++) {                
                for (int w = 0; w < img.getWidth(); w++) {
                    pic[h][w] = img.getRGB(w, h);

                    // top and bottom crop
                    if (pic[h][w] != -1) {
                        bottom = h;
                        top = img.getHeight() - bottom;
                    }
                                        
                    // WHITE -1
                    // BLUE  -16719881
                }
            }

            for (int w = 0; w < img.getWidth(); w++) {
                for (int h = 0; h < img.getHeight(); h++) {
                    // left and right crop
                    if (img.getRGB(w, h) != -1) {
                        right = w;
                        left = img.getWidth() - right;
                    }

                    // WHITE -1
                    // BLUE  -16719881
                }
            }

            // compute FF
            int all = (bottom - top) * ( right - left);
            int obj = 0;
            for (int x = top; x < bottom; x++) {
                for (int y = left; y < right; y++) {
                    if (pic[x][y] == -1) {
                        obj++;
                    }
                }
            }

            double ff = (double) obj / (double) all;

            System.out.println(ff);

        }

    }

}
