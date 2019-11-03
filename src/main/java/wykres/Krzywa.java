package wykres;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Krzywa {
	private static BufferedImage krzywa;
	private ArrayList<Point> punkty;

	static BufferedImage getKrzywa(Point graphSize, ArrayList<Point> punkty) {
		krzywa = new BufferedImage(graphSize.x, graphSize.y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) krzywa.getGraphics();
		g2d.setColor(Color.BLUE);
		while (punkty.size() > 0) {
			g2d.drawLine(punkty.get(0).x, punkty.get(0).y, punkty.get(0).x, punkty.get(0).y);
			punkty.remove(0);
		}
		return krzywa;
	}

	synchronized Point pobierzPunkt() {
		return null;
	}

}
