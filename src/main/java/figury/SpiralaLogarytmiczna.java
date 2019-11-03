package figury;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JPanel;

public class SpiralaLogarytmiczna extends Figury {
	private static String[] opisy = { "a=", "b=", "zakres=", "rad" };

	private SpiralaLogarytmiczna(BigDecimal parametrA, BigDecimal parametrB, BigDecimal zakres, JPanel graph) {
		super(opisy, parametrA, parametrB, zakres, graph);
	}

	@Override
	void wyznaczPunkty() {
		double a = parametrA.doubleValue();
		double b = parametrB.doubleValue();
		double z = zakres.doubleValue();

		int roz = Math.min(graph.getWidth(), graph.getHeight());
		double Xs = a * Math.pow(Math.E, b * 0) * Math.cos(0);
		double Ys = a * Math.pow(Math.E, b * 0) * Math.sin(0);
		double Xm = a * Math.pow(Math.E, b * z) * Math.cos(z);
		double Ym = a * Math.pow(Math.E, b * z) * Math.sin(z);
		if (z >= 0 && b > 0 || b < 0 && z < 0) {
			Xs = 0.0;
			Ys = 0.0;
		} else {
			Xm = 0.0;
			Ym = 0.0;
		}
		double wym = (double) roz / 2.0 / Math.sqrt(Math.pow(Xm - Xs, 2) + Math.pow(Ym - Ys, 2));
		System.out.println("wym= " + wym);

		// rysowanie prostej gdy podane parametry praktycznie prostują wykres
		if (wym == 0.0 && b > 1) {
			double kat = (z - Math.PI * ((int) (z / (Math.PI))));
			double aa = Math.abs(Math.tan(kat));
			for (int x = 0; x < graph.getWidth() / 2; x++) {
				int y = (int) (aa * x);
				if (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) < Math.sqrt(Math.pow(roz / 2, 2))) {
					punkty.add(new Point(x + graph.getWidth() / 2, -y + graph.getHeight() / 2));
				}
			}
			for (int y = 0; y < graph.getHeight() / 2; y++) {
				int x = (int) (y / aa);
				if (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) < Math.sqrt(Math.pow(roz / 2, 2))) {
					punkty.add(new Point(x + graph.getWidth() / 2, -y + graph.getHeight() / 2));
				}
			}

			double rad = z / Math.PI;
			double katy = (rad - 2 * (int) (rad / 2));
			double stopnie = katy * 180;
			if (stopnie > 90 && stopnie < 180) {
				for (Point o : punkty) {
					o.x = -(o.x - graph.getWidth() / 2) + graph.getWidth() / 2;
				}
			}
			if (stopnie >= 180 && stopnie < 270) {
				for (Point o : punkty) {
					o.x = -(o.x - graph.getWidth() / 2) + graph.getWidth() / 2;
					o.y = -(o.y - graph.getHeight() / 2) + graph.getHeight() / 2;
				}
			}
			if (stopnie >= 270 && stopnie < 360) {
				for (Point o : punkty) {
					o.y = -(o.y - graph.getHeight() / 2) + graph.getHeight() / 2;
				}
			}
			return;
		} else if (wym == 0.0 && b < 1) {
			// tu trzeba narysować koło
		}

		double probkowanie = 0.0001;

		double start = 0;
		double stop = z;
		probkowanie = 0.01;

		if (z < 0) {
			start = z;
			stop = 0;
		}

		long czasStart = System.currentTimeMillis();

		punkty.clear();
		double f = start;
		probkowanie = Math.abs(z / (roz * 1024));
		while (f < stop) {
			double x1 = a * Math.pow(Math.E, b * f) * Math.cos(f);
			double y1 = a * Math.pow(Math.E, b * f) * Math.sin(f);
			double x2 = a * Math.pow(Math.E, b * (f + probkowanie)) * Math.cos(f + probkowanie);
			double y2 = a * Math.pow(Math.E, b * (f + probkowanie)) * Math.sin(f + probkowanie);
			double x3 = a * Math.pow(Math.E, b * (f + 2 * probkowanie)) * Math.cos(f + 2 * probkowanie);
			double y3 = a * Math.pow(Math.E, b * (f + 2 * probkowanie)) * Math.sin(f + 2 * probkowanie);

			Point pkt1 = new Point((int) ((x1 * wym + graph.getWidth() / 2)),
					(int) ((-y1 * wym + graph.getHeight() / 2)));
			Point pkt2 = new Point((int) ((x2 * wym + graph.getWidth() / 2)),
					(int) ((-y2 * wym + graph.getHeight() / 2)));
			Point pkt3 = new Point((int) ((x3 * wym + graph.getWidth() / 2)),
					(int) ((-y3 * wym + graph.getHeight() / 2)));

			if (Math.sqrt(Math.pow(pkt1.getX() - pkt2.getX(), 2) + Math.pow(pkt1.getY() - pkt2.getY(), 2)) > Math
					.sqrt(2)
					|| Math.sqrt(Math.pow(pkt2.getX() - pkt3.getX(), 2) + Math.pow(pkt1.getY() - pkt2.getY(), 2)) > Math
							.sqrt(2)) {
				probkowanie /= 1.3;
			}

			if (punkty.size() == 0)
				punkty.add(pkt1);
			if (!pkt1.equals(punkty.get(punkty.size() - 1)) && pkt1.x >= 0 && pkt1.y >= 0)
				punkty.add(pkt1);
			f += probkowanie;
		}

		// start kod może na przyszłość do sprawka
//		do {
//			punkty.clear();
//			probkowanie /= 10.0;
//			if (probkowanie == 0) {
//				break;
//			}
//			for (double fi = start; fi < stop; fi += probkowanie) {
//				double x = a * Math.pow(Math.E, b * fi) * Math.cos(fi);
//				double y = a * Math.pow(Math.E, b * fi) * Math.sin(fi);
//
//				Point pkt = new Point((int) ((x * wym + graph.getWidth() / 2)),
//						(int) ((-y * wym + graph.getHeight() / 2)));
//				if (punkty.size() == 0)
//					punkty.add(pkt);
//				if (!pkt.equals(punkty.get(punkty.size() - 1)) && pkt.x >= 0 && pkt.y >= 0)
//					punkty.add(pkt);
//			}
//			// return;
//		} while (isGood());

		System.err.println(System.currentTimeMillis() - czasStart);
		Set<Point> set = new LinkedHashSet<Point>();
		set.addAll(punkty);
		punkty.clear();
		punkty.addAll(set);

	}

	public static class SpiralaLogarytmicznaBuilder implements Builder {
		BigDecimal parametrA = null;
		BigDecimal parametrB = null;
		BigDecimal zakres = null;
		JPanel graph = null;

		@Override
		public SpiralaLogarytmicznaBuilder setParametrA(BigDecimal parametrA) {
			this.parametrA = parametrA;
			return this;
		}

		@Override
		public SpiralaLogarytmicznaBuilder setParametrB(BigDecimal parametrB) {
			this.parametrB = parametrB;
			return this;
		}

		@Override
		public SpiralaLogarytmicznaBuilder setZakres(BigDecimal zakres) {
			this.zakres = zakres.multiply(new BigDecimal(Math.PI));
			return this;
		}

		@Override
		public SpiralaLogarytmicznaBuilder setGraph(JPanel graph) {
			this.graph = graph;
			return this;
		}

		@Override
		public SpiralaLogarytmiczna build() throws Exception {
			SpiralaLogarytmiczna.setKomentarz(sprawdzParametry());
			if (parametrA != null && parametrB != null && zakres != null && graph != null && sprawdzParametry() == null)
				return new SpiralaLogarytmiczna(parametrA, parametrB, zakres, graph);
			else if (parametrA == null)
				throw new Exception("Parametr A nie został ustawiony");
			else if (parametrB == null)
				throw new Exception("Parametr B nie został ustawiony");
			else if (zakres == null)
				throw new Exception("Parametr zakres nie został ustawiony");
			else if (graph == null)
				throw new Exception("Parametr graph nie został ustawiony");
			else if (sprawdzParametry() != null)
				throw new Exception("Błąd wprowadzonych parametrów");
			return null;
		}

		@Override
		public int[] sprawdzParametry() {
			int[] a = { 0, 1 };
			if (parametrA.compareTo(new BigDecimal(0)) == 1) {
				return null;
			}
			return a;
		}
	}
}
