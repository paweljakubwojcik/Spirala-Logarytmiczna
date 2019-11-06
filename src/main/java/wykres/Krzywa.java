package wykres;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Krzywa {
	private static BufferedImage krzywa;
	private ArrayList<Point> punkty;

	static BufferedImage getKrzywa(Point graphSize, ArrayList<Point> punkty) {
		krzywa = new BufferedImage(graphSize.x, graphSize.y, BufferedImage.TYPE_INT_ARGB);
		// long start = System.nanoTime();
		long start = System.currentTimeMillis();
		while (punkty.size() > 0) {
			int nr = punkty.size() - 1;
			krzywa.setRGB(punkty.get(nr).x, punkty.get(nr).y, Color.BLUE.getRGB());
			punkty.remove(nr);
		}
		// System.err.println(System.nanoTime() - start);
		System.err.println(System.currentTimeMillis() - start);
		// 1 641 212 455 <Graphics2D==setRGB> 433 966 485 na 15 044 161 punktów
		return krzywa;
	}

	synchronized Point pobierzPunkt() {
		return null;
	}

}
