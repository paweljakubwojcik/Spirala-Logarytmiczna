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
//TODO JavaDoc
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
	void wyznaczPunkty() {
		double a = parametrA.doubleValue();
		double b = parametrB.doubleValue();
		double z = zakres.doubleValue();
		double zRad = z / Math.PI;
		int graphW = graph.getWidth();
		int graphH = graph.getHeight();
		int graphW2 = graphW / 2;
		int graphH2 = graphH / 2;
		System.err.println("b= " + b);
		System.err.println("z= " + z / Math.PI);
		double az = a;
		double skala = 0;
		double rozmiarMin = Math.min(graphW, graphH);
		double rozmiarMin2 = rozmiarMin / 2.0;

		// wyznaczenie początkowego i końcowego fi
		double katStart = 0;
		double katStop = z;
		if (z < 0) {
			katStart = z;
			katStop = 0;
		}

		// Próba znalezienia takiego a żeby mimo wszystko dało się narysować coś nie nie
		// będącego oskryptowaną figurą, oraz wyznaczenie skali rysunku
		while (skala == 0) {
			double Xs = wartoscFunkcjiX(az, b, 0);
			double Ys = wartoscFunkcjiY(az, b, 0);
			double Xm = wartoscFunkcjiX(az, b, z);
			double Ym = wartoscFunkcjiY(az, b, z);
			if (z >= 0 && b > 0 || b < 0 && z < 0) {
				Xs = 0.0;
				Ys = 0.0;
			} else {
				Xm = 0.0;
				Ym = 0.0;
			}
//			System.out.println("roz= " + roz);
			skala = getSkala(rozmiarMin, Xm, Ym, Xs, Ys);
//			System.out.println("wym= " + wym);
			if (skala == 0) {
				az *= 1.0E-1;
			}
		}
		System.out.println("wym= " + skala);

		// rysowanie prostej gdy podane parametry praktycznie prostują wykres
		if (Double.isNaN(skala) && Math.abs(b) > 1) {
			double kat = getRadWithoutPIMultiply(z);
			drawLine(kat, graphW2, graphH2, punkty);
			double katy = getRadWithout2Multiply(zRad);
			double stopnie = katy * 180;
			mirrorTransformForDegree(stopnie, graphW2, graphH2);
			return;
		} else if (Double.isNaN(skala) && Math.abs(b) < 1) {
			// TODO tu trzeba narysować koło i sprawdzić X i Y czy są te największe i czy
			// wgl ta część kodu jest jeszcze potrzebna
			double mojeR = rozmiarMin2;
			System.out.println("Koło");

			int XStart, XKoniec, YStart, YKoniec;
			if (graphW >= graphH) {
				XStart = (int) (graphW2 - rozmiarMin2 - 2);
				YStart = 0;
				XKoniec = (int) (graphW2 + rozmiarMin2 + 2);
				YKoniec = (int) rozmiarMin;
			} else {
				XStart = 0;
				YStart = (int) (graphH2 - rozmiarMin2 - 2);
				XKoniec = (int) rozmiarMin;
				YKoniec = (int) (graphH2 + rozmiarMin2 + 2);
			}

			for (int X = XStart; X < XKoniec; X++) {
				for (int Y = YStart; Y < YKoniec; Y++) {
					if (mathDistanceOfPoints(X, Y, graphW2, graphH2) <= mojeR) {
						punkty.add(new Point(X, Y));
					}
				}
			}
			return;
			// albo może i nie :D
		}

		// Rysowanie okregu, pierścienia, koła, jeśli parametry wejściowe są długo
		// próbkowalne, największa optymalizacja czasu wykonywania
		double x1 = wartoscFunkcjiX(az, b, katStart);
		double y1 = wartoscFunkcjiY(az, b, katStart);
		double x2 = wartoscFunkcjiX(az, b, katStop);
		double y2 = wartoscFunkcjiY(az, b, katStop);
		double x3 = wartoscFunkcjiX(az, b, (katStop - 2 * Math.PI));
		double y3 = wartoscFunkcjiY(az, b, (katStop - 2 * Math.PI));
		Point pkt1 = transformFunctionToPixels(x1, y1, skala, graphW, graphH);
		Point pkt2 = transformFunctionToPixels(x2, y2, skala, graphW, graphH);
		Point pkt3 = transformFunctionToPixels(x3, y3, skala, graphW, graphH);
		// Odległości 1 pkt od środka, ostatniego i przed 2PI ostatniego
		double odl1PKT = mathDistanceOfPoints(pkt1.x, pkt1.y, graphW2, graphH2);
		double odlOstPKT = mathDistanceOfPoints(pkt2.x, pkt2.y, graphW2, graphH2);
		double odlPOstPKT = mathDistanceOfPoints(pkt3.x, pkt3.y, graphW2, graphH2);

		System.out.println("Delta odległości= " + Math.abs(odlOstPKT - odlPOstPKT));
		System.out.println("Odległosci= " + Math.abs(odlOstPKT - odl1PKT) + "   Szerokość= " + Math.abs(zRad));
		if (Math.abs(odlOstPKT - odl1PKT) < Math.abs(zRad) && Math.abs(odlOstPKT - odlPOstPKT) <= 3.1
				&& Math.abs(b) <= 1 && Math.abs(zRad) >= 2) {
			punkty.clear();
			if (odlOstPKT < odl1PKT) {
				double o = odlOstPKT;
				odlOstPKT = odl1PKT;
				odl1PKT = o;
			}
			drawRing(odl1PKT, odlOstPKT, graphW, graphH, punkty);
			return;
		} else if (Math.abs(odlOstPKT - odlPOstPKT) <= 2.1 && Math.abs(b) <= 1 && Math.abs(zRad) < 4) {
			drawArc(z, graphW, graphH, punkty);
			return;
		}

		long czasStart = System.currentTimeMillis();
		ArrayList<KatPunkt> katy = new ArrayList<KatPunkt>();
		boolean OK = false;
		double probki = 0.5;
		double katFI;
		while (!OK) {
			katy.clear();
			punkty.clear();

			Point start = new Point(), koniec = new Point();
			getStartStopPoint(start, koniec, graphW, graphH);
			for (double X = start.x; X < koniec.x; X += probki) {
				for (double Y = start.y; Y < koniec.y; Y += probki) {
					if (skala != 0 && b != 0 && az != 0) {
						katFI = getKatFI(X, Y, az, b, skala, graphW, graphH);
						if (katFI <= katStop && katFI >= katStart) {
							addPointOfFunction(az, b, katFI, skala, graphW, graphH, katy);
						}
					} else if (skala != 0 && b == 0) {
						if (Math.abs((mathDistanceOfPoints(X, Y, graphW2, graphH2)) - rozmiarMin2) <= Math.sqrt(2)
								&& isXYinImage(X, Y, graphW, graphH))
							punkty.add(new Point((int) X, (int) Y));
					}
				}
			}

			Collections.sort(katy, new Comparator<KatPunkt>() {
				@Override
				public int compare(KatPunkt p1, KatPunkt p2) {
					return Double.compare(p1.kat, p2.kat);
				}
			});
			int licznikOdleglosciowy = 0;

			for (int i = 1; i < katy.size(); i++) {
				double odleglosc = mathDistanceOfPoints(katy.get(i).pkt, katy.get(i - 1).pkt);
				if (odleglosc <= Math.sqrt(2)) {
					punkty.add(katy.get(i).pkt);
				} else {

					if (odleglosc < Math.sqrt(2) * 60) {
						drawLine(katy.get(i).pkt, katy.get(i - 1).pkt, graphW2, graphH2, punkty);
					} else {
						licznikOdleglosciowy++;
					}
				}
				if (punkty.size() > 5 * rozmiarMin * rozmiarMin) {
					clearDoubledPoints(punkty);
				}
			}
			clearDoubledPoints(punkty);
//			System.out.println("licznik= " + licznikOdleglosciowy + " punktysize= " + punkty.size() / 2);
			if (licznikOdleglosciowy > punkty.size() / 2 && skala != 0 && probki >= 1.0 / 16 || punkty.size() == 0) {
				new Wykres(graph, punkty, zakres);
				probki /= 2.0;
				OK = false;
			} else {
				OK = true;
			}
			katy.clear();
		}

		clearDoubledPoints(punkty);
		System.err.println("Czas szukania punktów metodą próbkowania: " + (System.currentTimeMillis() - czasStart));

	}

	private void mirrorTransformForDegree(double stopnie, int graphW2, int graphH2) {
		if (stopnie >= 0) {
			if (stopnie > 90 && stopnie < 180) {
				for (Point o : punkty) {
					o.x = -(o.x - graphW2) + graphW2;
				}
			}
			if (stopnie >= 180 && stopnie < 270) {
				for (Point o : punkty) {
					o.x = -(o.x - graphW2) + graphW2;
					o.y = -(o.y - graphH2) + graphH2;
				}
			}
			if (stopnie >= 270 && stopnie < 360) {
				for (Point o : punkty) {
					o.y = -(o.y - graphH2) + graphH2;
				}
			}
		} else {
			stopnie = -stopnie;
			if (stopnie > 180 && stopnie < 270) {
				for (Point o : punkty) {
					o.x = -(o.x - graphW2) + graphW2;
				}
			}
			if (stopnie >= 90 && stopnie < 180) {
				for (Point o : punkty) {
					o.x = -(o.x - graphW2) + graphW2;
					o.y = -(o.y - graphH2) + graphH2;
				}
			}
			if (stopnie >= 0 && stopnie < 90) {
				for (Point o : punkty) {
					o.y = -(o.y - graphH2) + graphH2;
				}
			}
		}
	}

	private void addPointOfFunction(double a, double b, double katFI, double skala, int graphW, int graphH,
			ArrayList<KatPunkt> katy) {
		double xf = wartoscFunkcjiX(a, b, katFI);
		double yf = wartoscFunkcjiY(a, b, katFI);
		Point pkt = transformFunctionToPixels(xf, yf, skala, graphW, graphH);
		if (pkt.x >= 0 && pkt.y >= 0 && pkt.x < graphW && pkt.y < graphH) {
			katy.add(new KatPunkt(katFI, pkt));
		}

	}

	private double getKatFI(double X, double Y, double a, double b, double skala, int graphW, int graphH) {
		int graphW2 = graphW / 2;
		int graphH2 = graphH / 2;
		return (1.0 / b * Math.log(mathDistanceOfPoints(X, Y, graphW2, graphH2) / (a * skala)));
	}

	@Override
	double wartoscFunkcjiX(double a, double b, double z) {
		return a * Math.pow(Math.E, b * z) * Math.cos(z);
	}

	@Override
	double wartoscFunkcjiY(double a, double b, double z) {
		return a * Math.pow(Math.E, b * z) * Math.sin(z);
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
