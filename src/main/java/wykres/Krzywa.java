package wykres;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Rysuje punkty na wykresie z podanej listy
 * 
 * @since 1.0
 */
public class Krzywa {
	private static BufferedImage krzywa;

	/**
	 * Tworzy BufferowanyObrazek rozmiarów podanych w graphSize oraz koloruje pixele
	 * według listy punkty
	 * 
	 * @param graphSize - Rozmiar obrazka w jakim mają być narysowane punkty
	 * @param punkty    - Lista pixeli które mają zostać pokolorowane
	 * @return - Obrazek z pokolorowanymi punktami oraz mający przezroczyste tło
	 */
	static BufferedImage getKrzywa(Point graphSize, ArrayList<Point> punkty) {
		krzywa = new BufferedImage(graphSize.x, graphSize.y, BufferedImage.TYPE_INT_ARGB);
		while (punkty.size() > 0) {
			int nr = punkty.size() - 1;
			krzywa.setRGB(punkty.get(nr).x, punkty.get(nr).y, Color.BLUE.getRGB());
			punkty.remove(nr);
		}
		return krzywa;
	}
}
