package wykres;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Wykres {
	private JPanel graph;
	private ArrayList<Point> punkty;
	private BigDecimal zakres;
	private BufferedImage wykres;
	private BufferedImage tlo;
	private BufferedImage krzywa;

	public Wykres(JPanel graph, ArrayList<Point> punkty, BigDecimal zakres) {
		this.graph = graph;
		this.punkty = punkty;
		this.zakres = zakres;
		krzywa = Krzywa.getKrzywa(new Point(graph.getWidth(), graph.getHeight()), punkty);
		wykres = new BufferedImage(graph.getWidth(), graph.getWidth(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) wykres.getGraphics();

		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, graph.getWidth(), graph.getHeight());

		g2d.setColor(Color.BLUE);
		g2d.drawImage(krzywa, 0, 0, null);
		while (true) {
			Graphics2D g = (Graphics2D) graph.getGraphics();
			g.drawImage(wykres, 0, 0, null);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void scalObrazki() {

	}

	private void drawWykres() {

	}

}
