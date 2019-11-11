package figury;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

import wykres.Wykres;

public class SpiralaLogarytmiczna extends Figury {
	private static String[] opisy = { "a=", "b=", "zakres=", "rad" };

	/**
	 * 
	 * @param parametrA - parametr A spirali
	 * @param parametrB - parametr B spirali
	 * @param zakres    - parametr fi
	 * @param graph     - BufferedImage na którym ma się spirala narysować
	 */
	private SpiralaLogarytmiczna(BigDecimal parametrA, BigDecimal parametrB, BigDecimal zakres, BufferedImage graph) {
		super(opisy, parametrA, parametrB, zakres, graph);
	}

	@Override
	void wyznaczPunkty() { // TODO REFAKTORYZACJA
		double a = parametrA.doubleValue();
		double b = parametrB.doubleValue();
		double z = zakres.doubleValue();
		System.err.println("b= " + b);
		System.err.println("z= " + z / Math.PI);
		double az = a;
		double wym = 0;
		double roz = 0, Xs, Ys, Xm, Ym;

		while (wym == 0) {
			roz = Math.min(graph.getWidth(), graph.getHeight());
			Xs = az * Math.pow(Math.E, b * 0) * Math.cos(0);
			Ys = az * Math.pow(Math.E, b * 0) * Math.sin(0);
			Xm = az * Math.pow(Math.E, b * z) * Math.cos(z);
			Ym = az * Math.pow(Math.E, b * z) * Math.sin(z);
			if (z >= 0 && b > 0 || b < 0 && z < 0) {
				Xs = 0.0;
				Ys = 0.0;
			} else {
				Xm = 0.0;
				Ym = 0.0;
			}
//			System.out.println("roz= " + roz);
			wym = (double) roz / 2.0 / Math.sqrt(Math.pow(Xm - Xs, 2) + Math.pow(Ym - Ys, 2));
//			System.out.println("wym= " + wym);
			if (wym == 0) {
				az *= 1.0E-1;
			}
		}

		System.out.println("wym= " + wym);
		// rysowanie prostej gdy podane parametry praktycznie prostują wykres
		if (Double.isNaN(wym) && Math.abs(b) > 1) {
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
			System.err.println("stopnie= " + stopnie);
			if (stopnie >= 0) {
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
			} else {
				stopnie = -stopnie;
				if (stopnie > 180 && stopnie < 270) {
					for (Point o : punkty) {
						o.x = -(o.x - graph.getWidth() / 2) + graph.getWidth() / 2;
					}
				}
				if (stopnie >= 90 && stopnie < 180) {
					for (Point o : punkty) {
						o.x = -(o.x - graph.getWidth() / 2) + graph.getWidth() / 2;
						o.y = -(o.y - graph.getHeight() / 2) + graph.getHeight() / 2;
					}
				}
				if (stopnie >= 0 && stopnie < 90) {
					for (Point o : punkty) {
						o.y = -(o.y - graph.getHeight() / 2) + graph.getHeight() / 2;
					}
				}
			}
			return;
		} else if (Double.isNaN(wym) && Math.abs(b) < 1) {
			// TODO tu trzeba narysować koło i sprawdzić X i Y czy są te największe
			double mojeR = roz / 2;
			System.out.println("Koło");

			int XStart, XKoniec, YStart, YKoniec;
			if (graph.getWidth() >= graph.getHeight()) {
				XStart = (int) (graph.getWidth() / 2 - roz / 2 - 2);
				XKoniec = (int) (graph.getWidth() / 2 + roz / 2 + 2);
				YStart = 0;
				YKoniec = (int) roz;
			} else {
				XStart = 0;
				XKoniec = (int) roz;
				YStart = (int) (graph.getHeight() / 2 - roz / 2 - 2);
				YKoniec = (int) (graph.getHeight() / 2 + roz / 2 + 2);
			}

			double odl;
			for (int X = XStart; X < XKoniec; X++) {
				for (int Y = YStart; Y < YKoniec; Y++) {
					odl = Math.sqrt(Math.pow((X - graph.getWidth() / 2), 2) + Math.pow((Y - graph.getHeight() / 2), 2));
					if (odl <= mojeR) {
						punkty.add(new Point(X, Y));
					}
				}
			}
			return;
			// albo może i nie :D
		}

		// double probkowanie = 0.0001;

		double start = 0;
		double stop = z;
		// probkowanie = 0.01;

		if (z < 0) {
			start = z;
			stop = 0;
		}

		{
			Point pkt1 = null;
			Point pkt2 = null;
			Point pkt3 = null;

			if (z < 0) {
				double x1 = az * Math.pow(Math.E, b * start) * Math.cos(start);
				double y1 = az * Math.pow(Math.E, b * start) * Math.sin(start);
				pkt1 = new Point((int) ((x1 * wym + graph.getWidth() / 2)),
						(int) ((-y1 * wym + graph.getHeight() / 2)));

				double x2 = az * Math.pow(Math.E, b * stop) * Math.cos(stop);
				double y2 = az * Math.pow(Math.E, b * stop) * Math.sin(stop);
				pkt2 = new Point((int) ((x2 * wym + graph.getWidth() / 2)),
						(int) ((-y2 * wym + graph.getHeight() / 2)));

				double x3 = az * Math.pow(Math.E, b * (stop - 2 * Math.PI)) * Math.cos((stop - 2 * Math.PI));
				double y3 = az * Math.pow(Math.E, b * (stop - 2 * Math.PI)) * Math.sin((stop - 2 * Math.PI));
				pkt3 = new Point((int) ((x3 * wym + graph.getWidth() / 2)),
						(int) ((-y3 * wym + graph.getHeight() / 2)));

			} else if (z >= 0) {
				double x1 = az * Math.pow(Math.E, b * start) * Math.cos(start);
				double y1 = az * Math.pow(Math.E, b * start) * Math.sin(start);
				pkt1 = new Point((int) ((x1 * wym + graph.getWidth() / 2)),
						(int) ((-y1 * wym + graph.getHeight() / 2)));

				double x2 = az * Math.pow(Math.E, b * stop) * Math.cos(stop);
				double y2 = az * Math.pow(Math.E, b * stop) * Math.sin(stop);
				pkt2 = new Point((int) ((x2 * wym + graph.getWidth() / 2)),
						(int) ((-y2 * wym + graph.getHeight() / 2)));

				double x3 = az * Math.pow(Math.E, b * (stop - 2 * Math.PI)) * Math.cos((stop - 2 * Math.PI));
				double y3 = az * Math.pow(Math.E, b * (stop - 2 * Math.PI)) * Math.sin((stop - 2 * Math.PI));
				pkt3 = new Point((int) ((x3 * wym + graph.getWidth() / 2)),
						(int) ((-y3 * wym + graph.getHeight() / 2)));
			}
			double odleglosc3 = Math
					.sqrt(Math.pow((pkt3.x - graph.getWidth() / 2), 2) + Math.pow((pkt3.y - graph.getHeight() / 2), 2));
			double odleglosc2 = Math
					.sqrt(Math.pow((pkt2.x - graph.getWidth() / 2), 2) + Math.pow((pkt2.y - graph.getHeight() / 2), 2));
			double odleglosc1 = Math
					.sqrt(Math.pow((pkt1.x - graph.getWidth() / 2), 2) + Math.pow((pkt1.y - graph.getHeight() / 2), 2));
			System.out.println("Delta odległości= " + Math.abs(odleglosc2 - odleglosc3));
			System.out.println(
					"Odległosci= " + Math.abs(odleglosc2 - odleglosc1) + "   Szerokość= " + Math.abs(z / Math.PI));
			if (Math.abs(odleglosc2 - odleglosc1) < Math.abs(z / Math.PI) && Math.abs(odleglosc2 - odleglosc3) <= 3.1
					&& Math.abs(b) <= 1) { // TODO bardzo małe B i małe fi
				// System.out.println("HAHAHAHHAHAHAHAHAH");
				punkty.clear();
				int XStart, XKoniec, YStart, YKoniec;
				if (graph.getWidth() >= graph.getHeight()) {
					XStart = (int) (graph.getWidth() / 2 - roz / 2 - 2);
					XKoniec = (int) (graph.getWidth() / 2 + roz / 2 + 2);
					YStart = 0;
					YKoniec = (int) roz;
				} else {
					XStart = 0;
					XKoniec = (int) roz;
					YStart = (int) (graph.getHeight() / 2 - roz / 2 - 2);
					YKoniec = (int) (graph.getHeight() / 2 + roz / 2 + 2);
				}
				double odl;
				if (odleglosc2 < odleglosc1) {
					double o = odleglosc2;
					odleglosc2 = odleglosc1;
					odleglosc1 = o;
				}

				for (int X = XStart; X < XKoniec; X++) {
					for (int Y = YStart; Y < YKoniec; Y++) {
						odl = Math.sqrt(
								Math.pow((X - graph.getWidth() / 2), 2) + Math.pow((Y - graph.getHeight() / 2), 2));
						if (odl + 0.58 >= odleglosc1 && odl - 0.58 <= odleglosc2) {
							punkty.add(new Point(X, Y));
						}
					}
				}
				return;
			}

		}

		long czasStart = System.currentTimeMillis();

		// wym = (double) roz / 2.0 / (a * Math.pow(Math.E, b * stop));
//		if (wym == 0)
//			wym = 1.0E-200;
		// System.out.println("wym= " + wym);

		double katFI;
		double t = graph.getHeight() / 2.0;
		double s = graph.getWidth() / 2.0;

		ArrayList<KatPunkt> katy = new ArrayList<KatPunkt>();
		boolean OK = false;
		double probki = 1;
		while (!OK) {
			katy.clear();
			punkty.clear();
			double XStart, XKoniec, YStart, YKoniec;
			if (graph.getWidth() >= graph.getHeight()) {
				XStart = graph.getWidth() / 2 - roz / 2 - 2;
				XKoniec = graph.getWidth() / 2 + roz / 2 + 2;
				YStart = 0;
				YKoniec = roz;
			} else {
				XStart = 0;
				XKoniec = roz;
				YStart = graph.getHeight() / 2 - roz / 2 - 2;
				YKoniec = graph.getHeight() / 2 + roz / 2 + 2;
			}
			double x1 = 0, y1 = 0;

			for (double X = XStart; X < XKoniec; X += probki) {
				for (double Y = YStart; Y < YKoniec; Y += probki) {
					if (wym != 0 && b != 0 && az != 0) {
						katFI = (1.0 / b
								* Math.log((Math.sqrt(Math.pow(X - s, 2) + Math.pow(-Y + t, 2))) / (az * wym)));
						// katFI /= Math.PI;
						if (katFI <= stop && katFI >= start) {
							x1 = az * Math.pow(Math.E, b * katFI) * Math.cos(katFI);
							y1 = az * Math.pow(Math.E, b * katFI) * Math.sin(katFI);
							Point pkt1 = new Point((int) ((x1 * wym + graph.getWidth() / 2)),
									(int) ((-y1 * wym + graph.getHeight() / 2)));
							if (pkt1.x >= 0 && pkt1.y >= 0 && pkt1.x < graph.getWidth() && pkt1.y < graph.getHeight()) {
								katy.add(new KatPunkt(katFI, pkt1));
							}
							// if (Math.abs(X - (a * Math.pow(Math.E, b * katFI) * Math.cos(katFI) * wym +
							// s)) <= Math.sqrt(z)
							// * b * z * b
							// && Math.abs(Y - (-a * Math.pow(Math.E, b * katFI) * Math.sin(katFI) * wym +
							// t)) <= Math
							// .sqrt(z) * b * z * b) {
//						if (Math.abs(X - (a * Math.pow(Math.E, b * katFI) * Math.cos(katFI) * wym + s)) <= Math.sqrt(z)
//								* b * z * b
//								&& Math.abs(Y - (-a * Math.pow(Math.E, b * katFI) * Math.sin(katFI) * wym + t)) <= Math
//										.sqrt(z) * b * z * b) {
							// System.out.println(" " + (katFI - ((int) katFI)));
//						if (Math.abs(Math.atan(Y / X) - (katFI - ((int) katFI))) <= 0.1) {
//							punkty.add(new Point((int) X, (int) Y));
//						}
//						System.out.println(
//								"^X= " + Math.abs(X - (a * Math.pow(Math.E, b * katFI) * Math.cos(katFI) * wym + s)));
//						System.out.print("   ^Y= "
//								+ Math.abs(Y - (-a * Math.pow(Math.E, b * katFI) * Math.sin(katFI) * wym + t)));

//						if (Y != 0 && katFI != 0 && Math.abs(X / Y - 1 / (Math.tan(katFI))) <= 0.1
//								|| X != 0 && Math.abs(Y / X - (Math.tan(katFI))) <= 0.1) {
//
//						} else {
//							punkty.add(new Point((int) X, (int) Y));
//						}
							// punkty.add(new Point((int) X, (int) Y));
							// System.out.print(katFI + " ");

//						if (punkty.size() == 0)
//							punkty.add(pkt1);
//						if (!pkt1.equals(punkty.get(punkty.size() - 1)) && pkt1.x >= 0 && pkt1.y >= 0
//								&& pkt1.x < graph.getWidth() && pkt1.y < graph.getHeight())
//							punkty.add(pkt1);

//						if (X - pkt1.x <= 1 && X - pkt1.x >= -1 && Y - pkt1.y <= 1 && Y - pkt1.y >= -1) {
//
							// punkty.add(new Point((int) X, (int) Y));
//
//						}
							// punkty.add(new Point((int) X, (int) Y));
						}
					} else if (wym != 0 && b == 0) {
						if (Math.abs((Math
								.sqrt(Math.pow(X - graph.getWidth() / 2, 2) + Math.pow(Y - graph.getHeight() / 2, 2)))
								- roz / 2) <= Math.sqrt(2) && X >= 0 && X < graph.getWidth() && Y >= 0
								&& Y < graph.getHeight())
							punkty.add(new Point((int) X, (int) Y));
					}
				}
				// System.out.println();
			}
			// long ssssssss = System.currentTimeMillis();-
			{
//				Set<KatPunkt> set = new LinkedHashSet<KatPunkt>();
//				set.addAll(katy);
//				katy.clear();
//				katy.addAll(set);
			}
			Collections.sort(katy, new Comparator<KatPunkt>() {
				@Override
				public int compare(KatPunkt p1, KatPunkt p2) {
					return Double.compare(p1.kat, p2.kat);
				}
			});
			int licznikOdleglosciowy = 0;
			// System.out.println(System.currentTimeMillis() - ssssssss);
			for (int i = 1; i < katy.size(); i++) {
				double odleglosc = Math.sqrt(Math.pow(katy.get(i).pkt.getX() - katy.get(i - 1).pkt.getX(), 2)
						+ Math.pow(katy.get(i).pkt.getY() - katy.get(i - 1).pkt.getY(), 2));
				if (odleglosc <= Math.sqrt(2)) {
					punkty.add(katy.get(i).pkt);
				} else {

					if (odleglosc < Math.sqrt(2) * 60) {
						double X1 = katy.get(i).pkt.getX(), X2 = katy.get(i - 1).pkt.getX(),
								Y1 = katy.get(i).pkt.getY(), Y2 = katy.get(i - 1).pkt.getY();
						double aa = (Y2 - Y1) / (X2 - X1);
						double bb = (X2 - X1) / (Y2 - Y1);
						Point pkt;

						if (Math.abs(aa) <= 1) {
							if (X2 > X1) {
								for (int DX = 1; DX < X2 - X1; DX++) {
									pkt = new Point((int) (X1 + DX), (int) (aa * DX + Y1));
									if (pkt.x >= 0 && pkt.x < graph.getWidth() && pkt.y >= 0
											&& pkt.y < graph.getHeight())
										punkty.add(pkt);
								}
							} else {
								for (int DX = 1; DX < X1 - X2; DX++) {
									pkt = new Point((int) (X2 + DX), (int) (aa * DX + Y2));
									if (pkt.x >= 0 && pkt.x < graph.getWidth() && pkt.y >= 0
											&& pkt.y < graph.getHeight())
										punkty.add(pkt);
								}
							}
						} else {
							if (Y2 > Y1) {
								for (int DY = 1; DY < Y2 - Y1; DY++) {
									pkt = new Point((int) (bb * DY + X1), (int) (Y1 + DY));
									if (pkt.x >= 0 && pkt.x < graph.getWidth() && pkt.y >= 0
											&& pkt.y < graph.getHeight())
										punkty.add(pkt);
								}
							} else {
								for (int DY = 1; DY < Y1 - Y2; DY++) {
									pkt = new Point((int) (bb * DY + X2), (int) (Y2 + DY));
									if (pkt.x >= 0 && pkt.x < graph.getWidth() && pkt.y >= 0
											&& pkt.y < graph.getHeight())
										punkty.add(pkt);
								}
							}
						}
					} else {
						licznikOdleglosciowy++;
					}
				}
				if (punkty.size() > 5 * roz * roz) {
					Set<Point> set = new LinkedHashSet<Point>();
					set.addAll(punkty);
					punkty.clear();
					punkty.addAll(set);
				}
			}
			Set<Point> set = new LinkedHashSet<Point>();
			set.addAll(punkty);
			punkty.clear();
			punkty.addAll(set);
			System.out.println("licznik= " + licznikOdleglosciowy + " punktysize= " + punkty.size() / 2);
			if (licznikOdleglosciowy > punkty.size() / 2 && wym != 0 && probki >= 1.0 / 16 || punkty.size() == 0) {
				new Wykres(graph, punkty, zakres); // TODO bardzo małe B i małe fi
				probki /= 2.0;
				OK = false;
			} else {
				OK = true;
			}

			katy.clear();
		}

//		double f = start;
//		probkowanie = Math.abs(z / (roz * 1024));
//		while (f < stop) {
//			double x1 = a * Math.pow(Math.E, b * f) * Math.cos(f);
//			double y1 = a * Math.pow(Math.E, b * f) * Math.sin(f);
//			double x2 = a * Math.pow(Math.E, b * (f + probkowanie)) * Math.cos(f + probkowanie);
//			double y2 = a * Math.pow(Math.E, b * (f + probkowanie)) * Math.sin(f + probkowanie);
//			double x3 = a * Math.pow(Math.E, b * (f + 2 * probkowanie)) * Math.cos(f + 2 * probkowanie);
//			double y3 = a * Math.pow(Math.E, b * (f + 2 * probkowanie)) * Math.sin(f + 2 * probkowanie);
//
//			Point pkt1 = new Point((int) ((x1 * wym + graph.getWidth() / 2)),
//					(int) ((-y1 * wym + graph.getHeight() / 2)));
//			Point pkt2 = new Point((int) ((x2 * wym + graph.getWidth() / 2)),
//					(int) ((-y2 * wym + graph.getHeight() / 2)));
//			Point pkt3 = new Point((int) ((x3 * wym + graph.getWidth() / 2)),
//					(int) ((-y3 * wym + graph.getHeight() / 2)));
//
//			if (Math.sqrt(Math.pow(pkt1.getX() - pkt2.getX(), 2) + Math.pow(pkt1.getY() - pkt2.getY(), 2)) > Math
//					.sqrt(2)
//					|| Math.sqrt(Math.pow(pkt2.getX() - pkt3.getX(), 2) + Math.pow(pkt1.getY() - pkt2.getY(), 2)) > Math
//							.sqrt(2)) {
//				probkowanie /= 1.3;
//			}
//
//			if (punkty.size() == 0)
//				punkty.add(pkt1);
//			if (!pkt1.equals(punkty.get(punkty.size() - 1)) && pkt1.x >= 0 && pkt1.y >= 0)
//				punkty.add(pkt1);
//			f += probkowanie;
//		}

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

		Set<Point> set = new LinkedHashSet<Point>();
		set.addAll(punkty);
		punkty.clear();
		punkty.addAll(set);
		System.err.println(System.currentTimeMillis() - czasStart);

	}

	private class KatPunkt {
		double kat;
		Point pkt;

		public KatPunkt(double kat, Point pkt) {
			this.kat = kat;
			this.pkt = pkt;
		}
	}

	public static class SpiralaLogarytmicznaBuilder implements Builder {
		BigDecimal parametrA = null;
		BigDecimal parametrB = null;
		BigDecimal zakres = null;
		BufferedImage graph = null;

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
		public SpiralaLogarytmicznaBuilder setGraph(BufferedImage graph) {
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
