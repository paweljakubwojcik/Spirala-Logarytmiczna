package wykres;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Krzywa {
	private static BufferedImage krzywa;

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
