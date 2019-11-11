package figury;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import interfejs.Window;
import wykres.Wykres;
//TODO JavaDoc
public abstract class Figury {
	private static final String[] komentarz = { "Podano niepoprawne dane.\n", "a musi być większe od zera\n",
			"a musi należeć do liczb rzeczywistych\n", "b musi należeć do liczb rzeczywistych\n",
			"U+03C6 musi należeć do liczb rzeczywistych" };
	String[] opisy = { " ", " ", " ", " " };
	BigDecimal parametrA;
	BigDecimal parametrB;
	BigDecimal zakres;
	ArrayList<Point> punkty = new ArrayList<Point>();
	BufferedImage graph;

	public Figury(String[] opisy, BigDecimal parametrA, BigDecimal parametrB, BigDecimal zakres, BufferedImage graph) {
		this.opisy = opisy;
		this.parametrA = parametrA;
		this.parametrB = parametrB;
		this.zakres = zakres;
		this.graph = graph;
		setOpis();
		wyznaczPunkty();
//		System.out.println(punkty.size());
//		System.out.println(punkty);
//		System.out.println(graph.getWidth() + " " + graph.getHeight());
		new Wykres(graph, punkty, zakres);
	}

	abstract void wyznaczPunkty();

	abstract double wartoscFunkcjiX(double a, double b, double z);

	abstract double wartoscFunkcjiY(double a, double b, double z);

	static void setKomentarz(int[] numer) {
		String wysylanyKomentarz = "";
		if (numer != null) {
			for (int i = 0; i < numer.length; i++) {
				wysylanyKomentarz += komentarz[i];
			}
		}
		try {
			Window.setKomentarz(wysylanyKomentarz);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setOpis() {
		try {
			Window.setParametrAText(opisy[0]);
			Window.setParametrBText(opisy[1]);
			Window.setZakresText(opisy[2]);
			Window.setJednostkaZakresuText(opisy[3]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	boolean isGood() {
		for (int i = 1; i < punkty.size(); i++) {
			if (Math.sqrt(Math.pow(punkty.get(i).getX() - punkty.get(i - 1).getX(), 2)
					+ Math.pow(punkty.get(i).getY() - punkty.get(i - 1).getY(), 2)) > Math.sqrt(2)) {
				return true;
			}
		}
		return false;
	}

	public BufferedImage getImage() {
		return graph;
	}

	public static void drawArc(double z, int graphW, int graphH, ArrayList<Point> punkty) {
		double katStart;
		double katStop;
		if (z >= 0) {
			katStart = 0;
			katStop = z;
		} else {
			katStart = z;
			katStop = 0;
		}
		double rozmiar = Math.min(graphH, graphW);
		double r = rozmiar / 2;
		double X2 = r * Math.cos(katStop);
		double Y2 = r * Math.sin(katStop);
		double skala = getSkala(rozmiar, 0, 0, X2, Y2)*0.995;
		double probkowanie = rozmiar / 3600000;
		double x;
		double y;
		Point pkt;
		while (katStart < katStop) {
			x = r * Math.cos(katStart);
			y = r * Math.sin(katStart);
			pkt = transformFunctionToPixels(x, y, skala, graphW, graphH);
			if (isXYinImage(pkt, graphW, graphH)) {
				punkty.add(pkt);
			}
			katStart += probkowanie;
		}
	}

	public static void clearDoubledPoints(ArrayList<Point> punkty) {
		Set<Point> set = new LinkedHashSet<Point>();
		set.addAll(punkty);
		punkty.clear();
		punkty.addAll(set);
	}

	public static boolean isXYinImage(double X, double Y, int graphW, int graphH) {
		return (X >= 0 && X < graphW && Y >= 0 && Y < graphH);
	}

	public static boolean isXYinImage(Point pkt, int graphW, int graphH) {
		return (pkt.x >= 0 && pkt.x < graphW && pkt.y >= 0 && pkt.y < graphH);
	}

	public static void drawLine(Point pkt1, Point pkt2, int graphW, int graphH, ArrayList<Point> punkty) {
		drawLine(pkt1.x, pkt1.y, pkt2.x, pkt2.y, graphW, graphH, punkty);
	}

	public static void drawLine(Point pkt1, double x, double y, int graphW, int graphH, ArrayList<Point> punkty) {
		drawLine(pkt1.x, pkt1.y, x, y, graphW, graphH, punkty);
	}

	public static void drawLine(double x1, double y1, double x2, double y2, int graphW, int graphH,
			ArrayList<Point> punkty) {
		double aa = (y2 - y1) / (x2 - x1);
		double bb = (x2 - x1) / (y2 - y1);
		Point pkt;

		if (Math.abs(aa) <= 1) {
			if (x2 > x1) {
				for (int DX = 1; DX < x2 - x1; DX++) {
					pkt = new Point((int) (x1 + DX), (int) (aa * DX + y1));
					if (pkt.x >= 0 && pkt.x < graphW && pkt.y >= 0 && pkt.y < graphH)
						punkty.add(pkt);
				}
			} else {
				for (int DX = 1; DX < x1 - x2; DX++) {
					pkt = new Point((int) (x2 + DX), (int) (aa * DX + y2));
					if (pkt.x >= 0 && pkt.x < graphW && pkt.y >= 0 && pkt.y < graphH)
						punkty.add(pkt);
				}
			}
		} else {
			if (y2 > y1) {
				for (int DY = 1; DY < y2 - y1; DY++) {
					pkt = new Point((int) (bb * DY + x1), (int) (y1 + DY));
					if (pkt.x >= 0 && pkt.x < graphW && pkt.y >= 0 && pkt.y < graphH)
						punkty.add(pkt);
				}
			} else {
				for (int DY = 1; DY < y1 - y2; DY++) {
					pkt = new Point((int) (bb * DY + x2), (int) (y2 + DY));
					if (pkt.x >= 0 && pkt.x < graphW && pkt.y >= 0 && pkt.y < graphH)
						punkty.add(pkt);
				}
			}
		}
	}

	public static void drawLine(double degree, int graphW2, int graphH2, ArrayList<Point> punkty) {
		int roz = Math.min(graphW2, graphH2);
		double aa = Math.abs(Math.tan(degree));
		for (int x = 0; x < graphW2; x++) {
			int y = (int) (aa * x);
			if (mathDistanceOfPoint(x, y) < Math.sqrt(Math.pow(roz, 2))) {
				punkty.add(new Point(x + graphW2, -y + graphH2));
			}
		}
		for (int y = 0; y < graphH2; y++) {
			int x = (int) (y / aa);
			if (mathDistanceOfPoint(x, y) < Math.sqrt(Math.pow(roz, 2))) {
				punkty.add(new Point(x + graphW2, -y + graphH2));
			}
		}
	}

	public static double getRadWithoutPIMultiply(double rad) {
		return rad - Math.PI * ((int) (rad / Math.PI));
	}

	public static double getRadWithout2PIMultiply(double rad) {
		return rad - 2 * Math.PI * ((int) (rad / Math.PI));
	}

	public static double getRadWithout2Multiply(double rad) {
		return rad - 2 * ((int) (rad / 2));
	}

	public static double getSkala(double rozmiar, Point pkt1, Point pkt2) {
		return getSkala(rozmiar, pkt1.x, pkt1.y, pkt2.x, pkt2.y);
	}

	public static double getSkala(double rozmiar, Point pkt, double x, double y) {
		return getSkala(rozmiar, pkt.x, pkt.y, x, y);
	}

	public static double getSkala(double rozmiar, double X1, double Y1, double X2, double Y2) {
		return rozmiar / (2 * mathDistanceOfPoints(X1, Y1, X2, Y2));
	}

	public static void getStartStopPoint(Point start, Point koniec, int graphW, int graphH) {
		int graphW2 = graphW / 2;
		int graphH2 = graphH / 2;
		double roz = Math.min(graphW, graphH);
		double roz2 = roz / 2;
		if (graphW >= graphH) {
			start.x = (int) (graphW2 - roz2 - 2);
			start.y = 0;
			koniec.x = (int) (graphW2 + roz2 + 2);
			koniec.y = (int) roz;
		} else {
			start.x = 0;
			start.y = (int) (graphH2 - roz2 - 2);
			koniec.x = (int) roz;
			koniec.y = (int) (graphH2 + roz2 + 2);
		}
	}

	public static Point transformFunctionToPixels(double x, double y, double skala, int graphW, int graphH) {
		int graphW2 = graphW / 2;
		int graphH2 = graphH / 2;
		return new Point((int) ((x * skala + graphW2)), (int) ((-y * skala + graphH2)));
	}

	public static void drawRing(double odl1PKT, double odlOstPKT, int graphW, int graphH, ArrayList<Point> punkty) {
		int graphW2 = graphW / 2;
		int graphH2 = graphH / 2;
		Point start = new Point(), koniec = new Point();
		getStartStopPoint(start, koniec, graphW, graphH);
		double odl, delta = 0.58;
		for (int X = start.x; X < koniec.x; X++) {
			for (int Y = start.y; Y < koniec.y; Y++) {
				odl = mathDistanceOfPoints(X, Y, graphW2, graphH2);
				if (odl + delta >= odl1PKT && odl - delta <= odlOstPKT) {
					punkty.add(new Point(X, Y));
				}
			}
		}
	}

	public static double mathDistanceOfPoint(double x, double y) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public static double mathDistanceOfPoint(Point pkt) {
		return Math.sqrt(Math.pow(pkt.x, 2) + Math.pow(pkt.y, 2));
	}

	public static double mathDistanceOfPoints(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	public static double mathDistanceOfPoints(Point pkt1, Point pkt2) {
		return Math.sqrt(Math.pow(pkt1.x - pkt2.x, 2) + Math.pow(pkt1.y - pkt2.y, 2));
	}
}
