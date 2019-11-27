package figury;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import wykres.Wykres;

/**
 * Wyznacza punkty spirali oraz opisy i komentarze w interfejs.Window
 * 
 * @author 7Adrian
 * @since 1.0
 */
public class SpiralaLogarytmiczna extends Figury {
	private static String[] opisy = { "a=", "b=", "zakres=", "rad" };

	/**
	 * 
	 * @param parametrA - parametr A spirali
	 * @param parametrB - parametr B spirali
	 * @param zakres    - zakres rysowania spirali
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
		double az = a;
		double skala = 0;
		double rozmiarMin = Math.min(graphW, graphH);

		// wyznaczenie początkowego i końcowego fi
		double katStart = 0;
		double katStop = z;
		if (z < 0) {
			katStart = z;
			katStop = 0;
		}

		// Próba znalezienia takiego a żeby mimo wszystko dało się narysować coś
		// nie nie
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
			skala = getSkala(rozmiarMin, Xm, Ym, Xs, Ys);
			if (skala == 0) {
				az *= 1.0E-1;
			}
		}

		// rysowanie prostej gdy podane parametry praktycznie prostują wykres
		if (Double.isNaN(skala) && Math.abs(b) > 1) {
			double kat = getRadWithoutPIMultiply(z);
			if (kat >= 0)
				drawLine(kat, graphW2, graphH2, punkty);
			else {
				drawLine(0, graphW2, graphH2, punkty);
				return;
			}
			double katy = getRadWithout2Multiply(zRad);
			double stopnie = katy * 180;
			mirrorTransformForDegree(stopnie, graphW2, graphH2);
			return;
		} else if (Double.isNaN(skala) && Math.abs(b) < 1) {
			drawCircle(graphW, graphH, punkty);
			return;
		} else if (Double.isNaN(skala) && Math.abs(b) == 1) {
			drawArc(2 * Math.PI, graphW, graphH, punkty);
			return;
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

		if (Math.abs(odlOstPKT - odl1PKT) < Math.abs(zRad) && Math.abs(odlOstPKT - odlPOstPKT) <= 4 && Math.abs(b) <= 1
				&& Math.abs(zRad) >= 2) {
			punkty.clear();
			if (odlOstPKT < odl1PKT) {
				double o = odlOstPKT;
				odlOstPKT = odl1PKT;
				odl1PKT = o;
			}
			drawRing(odl1PKT, odlOstPKT, graphW, graphH, punkty);
			return;
		} else if (Math.abs(odlOstPKT - odlPOstPKT) <= 2.1 && Math.abs(b) <= 1 && Math.abs(zRad) < 2) {
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
					katFI = getKatFI(X, Y, az, b, skala, graphW, graphH);
					if (katFI <= katStop && katFI >= katStart) {
						addPointOfFunction(az, b, katFI, skala, graphW, graphH, katy);
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

					if (odleglosc < Math.sqrt(2) * 3) {
						drawLine(katy.get(i).pkt, katy.get(i - 1).pkt, graphW2, graphH2, punkty);
					} else {
						licznikOdleglosciowy++;
					}
				}
			}
			clearDoubledPoints(punkty);
			if (licznikOdleglosciowy > punkty.size() / 2 && probki >= 1.0 / 16 || punkty.size() == 0) {
				new Wykres(graph, punkty, zakres);
				probki /= 2.0;
				OK = false;
			} else {
				OK = true;
			}
			katy.clear();
		}

		clearDoubledPoints(punkty);
		System.out.println("Czas szukania punktów metodą próbkowania: " + (System.currentTimeMillis() - czasStart));

	}

	/**
	 * Odbicie lustrzane rysunku w zależności od ćwiartki
	 * 
	 * @param stopnie - kąt do którego docelowo funkcja ma się transformować
	 * @param graphW2 - połowa szerokości rysowanego okienka
	 * @param graphH2 - połowa wysokości rysowanego okeinka
	 */
	private void mirrorTransformForDegree(double stopnie, int graphW2, int graphH2) {
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

	}

	/**
	 * Dodaje punkt dla zadanej wartości kąta FI
	 * 
	 * @param a      - parametr A funkcji
	 * @param b      - parametr B funkcji
	 * @param katFI  - kat dla którego liczymy wartość funkcji
	 * @param skala  - skala z jaką rysujemy spiralę
	 * @param graphW - szerokość rysowanego okienka
	 * @param graphH - wysokość rysowanego okienka
	 * @param katy   - lista do której punkt będzie dodany
	 */
	private void addPointOfFunction(double a, double b, double katFI, double skala, int graphW, int graphH,
			ArrayList<KatPunkt> katy) {
		double xf = wartoscFunkcjiX(a, b, katFI);
		double yf = wartoscFunkcjiY(a, b, katFI);
		Point pkt = transformFunctionToPixels(xf, yf, skala, graphW, graphH);
		if (isXYinImage(pkt, graphW, graphH)) {
			katy.add(new KatPunkt(katFI, pkt));
		}

	}

	/**
	 * Liczy kąt FI spirali dla zadanych wartości
	 * 
	 * @param X      - wartość X w pixelach
	 * @param Y      - wartość Y w pixelach
	 * @param a      - parametr A funkcji
	 * @param b      - parametr B funkcji
	 * @param skala  - skala z jaką rysujemy spiralę
	 * @param graphW - szerokość rysowanego okienka
	 * @param graphH - wysokość rysowanego okienka
	 * @return - kąt Fi funkcji
	 */
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

	/**
	 * Klasa przypominająca klasę Point z API Javy, natomiast rozszerząja ją o
	 * dodatkową zmienną zmiennoprzecinkową
	 * 
	 * @author 7Adrian
	 *
	 */
	private class KatPunkt {
		double kat;
		Point pkt;

		/**
		 * Tworzy nowy punkt
		 * 
		 * @param kat - wartość kąta
		 * @param pkt - punkt
		 */
		public KatPunkt(double kat, Point pkt) {
			this.kat = kat;
			this.pkt = pkt;
		}
	}

	/**
	 * Builder dla figury.SpiralaLogarytmiczna
	 * 
	 * @author 7Adrian
	 *
	 */
	public static class SpiralaLogarytmicznaBuilder implements Builder {
		String parametrAText = null;
		String parametrBText = null;
		String zakresText = null;

		BigDecimal parametrA = null;
		BigDecimal parametrB = null;
		BigDecimal zakres = null;
		BufferedImage graph = null;

		@Override
		public SpiralaLogarytmicznaBuilder setParametrA(String parametrA) {
			this.parametrAText = parametrA;
			return this;
		}

		@Override
		public SpiralaLogarytmicznaBuilder setParametrB(String parametrBText) {
			this.parametrBText = parametrBText;
			return this;
		}

		@Override
		public SpiralaLogarytmicznaBuilder setZakres(String zakresText) {
			this.zakresText = zakresText;

			return this;
		}

		@Override
		public SpiralaLogarytmicznaBuilder setGraph(BufferedImage graph) {
			this.graph = graph;
			return this;
		}

		@Override
		public SpiralaLogarytmiczna build() throws ExceptionInInitializerError {

			Figury.setKomentarz(sprawdzParametry());

			if (parametrAText != null && parametrBText != null && zakresText != null && graph != null
					&& sprawdzParametry() == null) {
				parametrA = new BigDecimal(parametrAText);
				parametrB = new BigDecimal(parametrBText);
				zakres = new BigDecimal(zakresText);
				this.zakres = zakres.multiply(new BigDecimal(Math.PI));
				return new SpiralaLogarytmiczna(parametrA, parametrB, zakres, graph);
			} else if (parametrAText == null)
				throw new ExceptionInInitializerError("Parametr A nie został ustawiony");
			else if (parametrBText == null)
				throw new ExceptionInInitializerError("Parametr B nie został ustawiony");
			else if (zakresText == null)
				throw new ExceptionInInitializerError("Parametr zakres nie został ustawiony");
			else if (graph == null)
				throw new ExceptionInInitializerError("Parametr graph nie został ustawiony");
			else
				throw new ExceptionInInitializerError("Błąd wprowadzonych parametrów");
		}

		@Override
		public int[] sprawdzParametry() {
//			String[] komentarz = { "Parametr a nie został ustawiony", "Parametr b nie został ustawiony",
//					"Zakres nie został ustawiony", "Podano niepoprawne dane.\n", "a musi być większe od zera\n",
//					"a musi należeć do liczb rzeczywistych\n", "b musi należeć do liczb rzeczywistych\n",
//					"U+03C6 musi nale�e� do liczb rzeczywistych" };
			int[] a = { 0, 0, 0, 0, 0 };

			if (parametrAText == null) // dodaj komentarz a nieustawione
				a[0] = 1;
			if (parametrBText == null) // dodaj komentarz b nieustawione
				a[1] = 1;
			if (zakresText == null) // dodaj komentarz z nieustawione
				a[2] = 1;
			if (isItANumber(parametrAText))
				if (Double.valueOf(parametrAText) < 0)
					a[4] = 1;

			if (!(isItANumber(parametrAText) && isItANumber(zakresText) && isItANumber(parametrBText)))
				a[3] = 1;

			for (int i = 0; i < a.length; i++)
				if (a[i] == 1)
					return a;
			return null;

		}

		/**
		 * 
		 * @param string
		 * @return true je�li string da si� przekonwetowa� na liczb�
		 * @author Pafeu
		 */
		private boolean isItANumber(String string) {

			if (string == null)
				return false;

			for (int i = 0; i < string.length(); i++) {
				if (string.charAt(0) == "-".charAt(0) && i == 0)
					i++;
				if (string.charAt(0) == ".".charAt(0))
					return false;
				if (!((int) string.charAt(i) <= 57 && (int) string.charAt(i) >= 48) && string.charAt(i) != ".".charAt(0)
						&& string.charAt(i) != "E".charAt(0))
					return false;
			}
			return true;
		}

	}
}
