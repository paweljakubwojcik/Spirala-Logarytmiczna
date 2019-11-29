package wykres;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;

import figury.SpiralaLogarytmiczna;

/**
 * Generuje opisy i osie do Spirali
 *
 */
public class Tlo {

	private static BufferedImage tlo;
	private BufferedImage osie;
	private BufferedImage opisyOsi;
	private static Point graphSize;

	static BufferedImage getTlo(Point graphSize1, SpiralaLogarytmiczna spirala) {

		graphSize = graphSize1;
		tlo = new BufferedImage(graphSize.x, graphSize.y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tlo.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, graphSize.x, graphSize.x);

		drawOsie(g, spirala);
		return tlo;
	}

	private void scalObrazki() {

	}

	private static void drawOsie(Graphics2D g, SpiralaLogarytmiczna spirala) {
		g.setColor(new Color(200, 200, 200, 200));
		g.drawLine(0, graphSize.y / 2, graphSize.x, graphSize.y / 2); // oś x
		g.drawLine(graphSize.x / 2, 0, graphSize.x / 2, graphSize.y); // oś Y

		double wartoscOstatniejPodzialki, wartoscPierwszejPodzialki;
		double z = spirala.getZakres().doubleValue();
		double a = spirala.getParametrA().doubleValue();
		double b = spirala.getParametrB().doubleValue();
		double skala;
		int podzialka;
		double i = 0;
		wartoscOstatniejPodzialki = a * Math.cos(z) * Math.exp(z * b);
		wartoscPierwszejPodzialki = a;

		skala = Math.abs((graphSize.x / 2) / wartoscOstatniejPodzialki);

		while (skala <= 1) {
			wartoscOstatniejPodzialki = wartoscOstatniejPodzialki / 10;
			skala = Math.abs((graphSize.x / 2) / wartoscOstatniejPodzialki);
			i++;
		}
		podzialka = (int) skala;

		for (int x = graphSize.x / 2; x < graphSize.x; x += podzialka) {
			g.drawLine(x, (graphSize.y / 2) + 3, x, (graphSize.y / 2) - 3);
		}
		for (int x = graphSize.x / 2; x > 0; x -= podzialka) {
			g.drawLine(x, (graphSize.y / 2) + 3, x, (graphSize.y / 2) - 3);
		}
		for (int y = graphSize.y / 2; y < graphSize.y; y += podzialka) {
			g.drawLine(graphSize.x / 2 + 3, y, graphSize.x / 2 - 3, y);
		}
		for (int y = graphSize.y / 2; y > 0; y -= podzialka) {
			g.drawLine(graphSize.x / 2 + 3, y, graphSize.x / 2 - 3, y);
		}

		wartoscPierwszejPodzialki *= Math.pow(10, i);
		i = 1;
		g.drawString(String.valueOf(wartoscOstatniejPodzialki), 10, 10);
		g.drawString(String.valueOf(wartoscPierwszejPodzialki), 10, 30);
		g.drawString(String.valueOf(podzialka), 10, 50);

	}

	private void drawOpisyOsi() {

	}

}
