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
		g.setColor(new Color(200, 200, 200, 150));
		g.drawLine(0, graphSize.y / 2, graphSize.x, graphSize.y / 2); // oś x
		g.drawLine(graphSize.x / 2, 0, graphSize.x / 2, graphSize.y); // oś Y

		double wartoscOstatniejPodzialki, wartoscPierwszejPodzialki;
		double z = spirala.getZakres().doubleValue(); // ten zakres jest pomnozeony przez pi XD
		double a = spirala.getParametrA().doubleValue();
		double b = spirala.getParametrB().doubleValue();
		double skala;
		int podzialka;
		double i = 0;

		double X = a * Math.cos(z) * Math.exp(z * b);
		double Y = a * Math.sin(z) * Math.exp(z * b);

		// proba radzenie sobie z nie wielokrotnosciami PI
//		z = Math.abs(z);
//		z = Math.round(z / Math.PI);
//		System.out.println(z);
//		z *= Math.PI;
		/////////////////

		wartoscOstatniejPodzialki = Math.hypot(X, Y);
		wartoscPierwszejPodzialki = a;

		skala = Math.abs((graphSize.x / 2) / wartoscOstatniejPodzialki);

		// jesli skala jest zbyt duza i podzialki sie nie mieszcza
		// 10 - odstep pomiedzy podzialkami musi byc wiekszy niz 10px
		while (skala <= 10) {
			wartoscOstatniejPodzialki = wartoscOstatniejPodzialki / 10;
			skala = Math.abs((graphSize.x / 2) / wartoscOstatniejPodzialki);
			i++;
		}

		// jesli skala jest zbyt mala i zadna podzialka nie bylaby widoczna
		// podzielone na 10 zeby zawze bylo przynajmniej 10 podzialek
		while (skala > (graphSize.x / 2)) {
			wartoscOstatniejPodzialki = wartoscOstatniejPodzialki * 10;
			skala = Math.abs((graphSize.x / 2) / wartoscOstatniejPodzialki);
			i--;
		}

		skala = Math.round(skala);
		podzialka = (int) skala;

		for (int x = graphSize.x / 2; x < graphSize.x; x += podzialka) {
			g.drawLine(x, (graphSize.y / 2) + 6, x, (graphSize.y / 2) - 6);
			// TODO MNIEJSZE LINIE POMIEDZY DUZYMI PODZIALKAMI
//			for (int x2 = x; x2 < (x + podzialka); x2 += podzialka / 10)
//				g.drawLine(x2, (graphSize.y / 2) + 3, x2, (graphSize.y / 2) - 3);
		}
		for (int x = graphSize.x / 2; x > 0; x -= podzialka) {
			g.drawLine(x, (graphSize.y / 2) + 6, x, (graphSize.y / 2) - 6);
		}
		for (int y = graphSize.y / 2; y < graphSize.y; y += podzialka) {
			g.drawLine(graphSize.x / 2 + 6, y, graphSize.x / 2 - 6, y);
		}
		for (int y = graphSize.y / 2; y > 0; y -= podzialka) {
			g.drawLine(graphSize.x / 2 + 6, y, graphSize.x / 2 - 6, y);
		}

		double podzialkaWyswietlana = Math.pow(10, i);
		wartoscOstatniejPodzialki = Math.hypot(X, Y);
		i = 0;
		g.drawString("r(zakres)= " + String.valueOf(wartoscOstatniejPodzialki), 10, 10);
		g.drawString("Podzialka: " + String.valueOf(podzialkaWyswietlana), 10, 30);
		// g.drawString(String.valueOf(podzialka), 10, 50);

	}

	private void drawOpisyOsi() {

	}

}
