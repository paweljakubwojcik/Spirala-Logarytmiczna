package figury;

import java.awt.Point;
import java.math.BigDecimal;

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
		Xs = 0.0;
		Ys = 0.0;

		double wym = (double) roz / 2.0 / Math.sqrt(Math.pow(Xm - Xs, 2) + Math.pow(Ym - Ys, 2));

		double probkowanie = 0.01;
		do {
			punkty.clear();
			probkowanie /= 10.0;
			if (probkowanie == 0) {
				break;
			}
			for (double fi = -10000.000; fi < z; fi += probkowanie) {
				double x = a * Math.pow(Math.E, b * fi) * Math.cos(fi);
				double y = a * Math.pow(Math.E, b * fi) * Math.sin(fi);

				Point pkt = new Point((int) ((x * wym + graph.getWidth() / 2)),
						(int) ((y * wym + graph.getHeight() / 2)));
				if (punkty.size() == 0)
					punkty.add(pkt);
				if (!pkt.equals(punkty.get(punkty.size() - 1)) && pkt.x >= 0 && pkt.y >= 0)
					punkty.add(pkt);
			}
		} while (isGood());

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
