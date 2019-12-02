package wykres;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Generuje opisy i osie do Spirali
 *
 * @since 1.2
 * @author Pafeu
 */
public class Tlo {

	private static BufferedImage tlo;
	private static BufferedImage osie;
	private static BufferedImage opisyOsi;

	/**
	 * Funkcja zwraca obrazek tła (czarny prostkąt)
	 * 
	 * @param graphSize - rozmiar obrazka na ktorym ma byc tlo
	 * @return obraz tla
	 */
	static BufferedImage getTlo(Point graphSize) {
		tlo = new BufferedImage(graphSize.x, graphSize.y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tlo.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, graphSize.x, graphSize.x);

		return tlo;
	}

	/**
	 * Fucnkja zwraca obrazek osi (X Y oraz podziałki)
	 * 
	 * @param graphSize
	 * @param podzialka - co ile pixeli ma byc narysowana podzialka
	 * @return obraz osi
	 */
	static BufferedImage getOsie(Point graphSize, int podzialka) {
		osie = new BufferedImage(graphSize.x, graphSize.y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) osie.getGraphics();
		g.setColor(new Color(200, 200, 200, 150));
		g.drawLine(0, graphSize.y / 2, graphSize.x, graphSize.y / 2); // oś x
		g.drawLine(graphSize.x / 2, 0, graphSize.x / 2, graphSize.y); // oś Y

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

		return osie;
	}

	/**
	 * 
	 * @param graphSize - rozmmiar obrazka
	 * @param opisy     - opisy które maja zostac wyswietlone
	 * @return BufferedImage z npisami w odpowiednim miejscu
	 */
	public static BufferedImage getOpisyOsi(Point graphSize, String[] opisy) {
		opisyOsi = new BufferedImage(graphSize.x, graphSize.y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) opisyOsi.getGraphics();

		g.setFont(new Font("Dialog", Font.PLAIN, graphSize.x * 25 / 1000));
		for (int i = 0; i < opisy.length; i++)
			g.drawString(opisy[i], 5, (i + 1) * g.getFontMetrics().getHeight());

		return opisyOsi;
	}

}
