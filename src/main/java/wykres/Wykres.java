package wykres;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;

import figury.SpiralaLogarytmiczna;

/**
 * Zarządza gotowym wyglądem wykresu. Scala obrazki, dodaje tło.
 * 
 * 
 * @author 7Adrian
 * @since 1.0
 */
public class Wykres {
	private BufferedImage graph;
	private SpiralaLogarytmiczna spirala;
	private BufferedImage wykres;
	private BufferedImage tlo;
	private BufferedImage krzywa;

	/**
	 * konstruktor edytuje podane parametry
	 * 
	 * @param graph  - BufferedImage na którym zostaje narysowany wykres.
	 * @param krzywa - Obraz wykresu utworzony z wyznaczonych punktów
	 * @param zakres - potrzebne do wykonania tła
	 */
	public Wykres(BufferedImage graph, BufferedImage krzywa, SpiralaLogarytmiczna spirala) {
		this.graph = graph;
		this.krzywa = krzywa;
		this.spirala = spirala;
		wykres = new BufferedImage(graph.getWidth(), graph.getWidth(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) wykres.getGraphics();

		Point graphSize = new Point(graph.getWidth(), graph.getWidth());

		g2d.drawImage(Tlo.getTlo(graphSize, spirala), null, 0, 0);

		g2d.setColor(Color.BLUE);
		g2d.drawImage(krzywa, 0, 0, null);

		// Generowanie obrazków do testów zostawić w spokoju te 5 linijek poniżej
//		try {

//			ImageIO.write(krzywa, "png", new File("src/test/java/figury/Wykres[10,200.75].png"));

//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		Graphics2D g = (Graphics2D) graph.getGraphics();
		g.drawImage(wykres, 0, 0, null);
	}

	private void scalObrazki() {

	}

	private void drawWykres() {

	}

}
