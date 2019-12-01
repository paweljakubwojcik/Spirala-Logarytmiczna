package wykres;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * Zawiera metody wygładzania krawędzi działające jako Post Procesy
 * 
 * @author 7Adrian
 * @since 1.2
 */
public class AntiAliazing {

	/**
	 * Super Sampling Anti Aliasing - metoda ta zmiejsza obraz w większej
	 * rozdzielczości do obrazka w mniejszej rozdzielczości przy czym obrazek ten
	 * musi mieć jeden kolor - niebieski
	 * 
	 * @param image         - obrazek który ma zostać zmniejszony
	 * @param sizeToConvert - ile razy zmniejszony ma zostać obrazek
	 * @return - obrazk zmniejszony z wygładzonymi krawędziami
	 */
	public static BufferedImage SSAAMonoColor(BufferedImage image, int sizeToConvert) {
		Dimension size = new Dimension(image.getWidth() / sizeToConvert, image.getHeight() / sizeToConvert);
		BufferedImage aaImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		int alpha;
		int red;
		int green;
		int blue;
		int RGBA;
		int skala = (int) Math.pow(sizeToConvert, 2);
		for (int x = 0; x < size.width; x++) {
			for (int y = 0; y < size.height; y++) {
				alpha = 0;
				red = 0;
				green = 0;
				blue = 255;
				for (int i = 0; i < sizeToConvert; i++) {
					for (int j = 0; j < sizeToConvert; j++) {
						int xx = x * sizeToConvert + i;
						int yy = y * sizeToConvert + j;
						RGBA = image.getRGB(xx, yy);
						alpha += (RGBA >> 24) & 255;
					}
				}
				alpha /= skala;
				int minColor = 256 / sizeToConvert;
				if (alpha >= minColor) {
					alpha = minColor;
				}
				if (alpha > 0) {
					alpha *= sizeToConvert;
					alpha--;
				}
				aaImage.setRGB(x, y, new Color(red, green, blue, alpha).getRGB());
			}
		}
		return aaImage;
	}

}
