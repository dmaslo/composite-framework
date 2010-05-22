package app.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Aplikační třída, která po předání PNG souboru (binarizovaní obraz - BW)
 * generuje (vypisuje do konzole) kovarianční funkci daného obrazu.
 */
public class Covariance {

    public static void main(String args[]) throws IOException {

		/**
		 * hlavní parametr - jméno PNG souboru
		 */
		String initFileName = "example.png";

        String fileName;
        boolean[][] pic;
        boolean[][] covPic;
        int all;
        int xor;
        double covariance;

        fileName = initFileName;

		// read png image
		BufferedImage img = ImageIO.read(new File(fileName));

		pic    = new boolean[img.getHeight()][img.getWidth()];
		covPic = new boolean[img.getHeight()][img.getWidth()];

		// create matrix
		for (int h = 0; h < img.getHeight(); h++) {
			for (int w = 0; w < img.getWidth(); w++) {
				//pic[h][w] = img.getRGB(w, h);
				//System.out.println(img.getRGB(w, h));

				if (img.getRGB(w, h) == -1) {
					pic[h][w] = false;
					covPic[h][w] = false;
				}
				else {
					pic[h][w] = true;
					covPic[h][w] = true;
				}
			}
		}

		// covariance
		for (int shift = 0; shift < img.getWidth()/2; shift++) {
			xor = 0;
			all = img.getHeight() * (img.getWidth() - 2*shift);

			for (int i = 0; i < img.getHeight(); i++) {
				for (int j = shift; j < img.getWidth()-shift; j++) {
					if (pic[i][j] && covPic[i][j]) {
						xor++;
					}
				}
			}

			// shift covPic
			for (int i = 0; i < img.getHeight(); i++) {
				for (int j = img.getWidth()-2; j >= 0; j--) {
					covPic[i][j+1] = covPic[i][j];
				}
			}

			covariance = (double) xor / (double) all;

			System.out.println(shift + ", " + covariance);
		}

    }

}
