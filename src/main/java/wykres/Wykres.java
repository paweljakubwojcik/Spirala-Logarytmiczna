package wykres;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Zarządza gotowym wyglądem wykresu. Scala obrazki, rysuje i dodaje tło.
 * 
 * @author 7Adrian
 * @since 1.0
 */
public class Wykres {
	private BufferedImage graph;
	private BufferedImage wykres;
	private BufferedImage tlo;
	private BufferedImage krzywa;

	/**
	 * konstruktor Rysuje w graph - tło, krzywą oraz osie i ich opisy
	 * 
	 * @param graph     - BufferedImage na którym zostaje narysowany wykres.
	 * @param krzywa    - Obraz wykresu utworzony z wyznaczonych punktów
	 * @param podzialka - okresla co ile px rysowana bedzie podzialka osi
	 * @param opisy     - opisy obrazka
	 */
	public Wykres(BufferedImage graph, BufferedImage krzywa, int podzialka, String[] opisy) {
		this.graph = graph;
		this.krzywa = krzywa;
		wykres = new BufferedImage(graph.getWidth(), graph.getWidth(), BufferedImage.TYPE_INT_ARGB);
		tlo = new BufferedImage(graph.getWidth(), graph.getWidth(), BufferedImage.TYPE_INT_ARGB);

		stworzTlo(podzialka, opisy);
		scalObrazki();
		drawWykres();
	}

	/**
	 * Scala wszystkie obrazki w jeden w kolejności: 1) tlo, 2) krzywa
	 */
	private void scalObrazki() {
		Graphics2D g = (Graphics2D) wykres.getGraphics();
		g.drawImage(tlo, 0, 0, null);
		g.drawImage(krzywa, 0, 0, null);
	}

	/**
	 * Umieszcza obrazek z wykresem (wykres) na obrazku na którym ma zostać finalnie
	 * narysowany (graph)
	 */
	private void drawWykres() {
		Graphics2D g = (Graphics2D) graph.getGraphics();
		g.drawImage(wykres, 0, 0, null);
	}

	/**
	 * Generuje tło do krzywej w kolejności: 1) tło, 2) osie, 3) opisy osi
	 * 
	 * @param podzialka - okresla co ile px rysowana bedzie podzialka osi
	 * @param opisy     - opisy obrazka
	 */
	private void stworzTlo(int podzialka, String[] opisy) {
		Graphics2D g = (Graphics2D) tlo.getGraphics();
		Point graphSize = new Point(graph.getWidth(), graph.getHeight());
		g.drawImage(Tlo.getTlo(graphSize), null, 0, 0);
		g.drawImage(Tlo.getOsie(graphSize, podzialka), null, 0, 0);
		g.drawImage(Tlo.getOpisyOsi(graphSize, opisy), null, 0, 0);
	}

}
