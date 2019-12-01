package figury;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import wykres.Wykres;

/**
 * Wyznacza punkty spirali oraz opisy i komentarze w interfejs.Window
 * 
 * @author 7Adrian
 * @since 1.0
 */
public class SpiralaLogarytmiczna extends Figury {
	private static String[] opisy = { "a=", "b=", "zakres=", "rad" };
	private static Vector<MultiDraw> watki = new Vector<SpiralaLogarytmiczna.MultiDraw>();
	private int numberOfThread;
	private int podzialka;
	private String[] opisyOsi = new String[2];

	/**
	 * Konstuktor Spirali Logarytmicznej, przy pierwszym wykonaniu tworzy wątki do
	 * pracy wielowątkowej.
	 * 
	 * @param parametrA      - parametr A spirali
	 * @param parametrB      - parametr B spirali
	 * @param zakres         - zakres rysowania spirali
	 * @param graph          - BufferedImage na którym ma się spirala narysować
	 * @param numberOfThread - liczba wątków utworzona do rysowania
	 */
	private SpiralaLogarytmiczna(BigDecimal parametrA, BigDecimal parametrB, BigDecimal zakres, BufferedImage graph,
			int numberOfThread) {
		super(opisy, parametrA, parametrB, zakres, graph);
		this.numberOfThread = numberOfThread;
		wyznaczOsie();
		wyznaczPunkty();
		new Wykres(graph, krzywa, podzialka, opisyOsi);
	}

	/**
	 * wyznacza podzialke dla osi oraz opisy
	 */
	private void wyznaczOsie() {
		double wartoscOstatniejPodzialki;
		double z = Math.abs(zakres.doubleValue()); // ten zakres jest pomnozeony przez pi XD
		double a = parametrA.doubleValue();
		double b = parametrB.doubleValue();
		double skala;

		// okresla ile razy zmniejszono/zwiekszono podzialke
		double i = 0;

		// tutaj jesli z jest ujemne to X i Y = NaN
		double X = wartoscFunkcjiX(a, b, z);
		double Y = wartoscFunkcjiY(a, b, z);

		wartoscOstatniejPodzialki = Math.hypot(X, Y);

		// jesli wartosc ostatniego punktu na wykresie jest nieskonczona
		if (Double.isInfinite(wartoscOstatniejPodzialki))
			wartoscOstatniejPodzialki = Double.MAX_VALUE;
// jesli wartosc ostatniego pkt wychodzi0
		if (wartoscOstatniejPodzialki == 0)
			wartoscOstatniejPodzialki = Double.MIN_VALUE;

		skala = Math.abs((graph.getWidth() / 2) / wartoscOstatniejPodzialki);

		// jesli skala jest zbyt duza i podzialki sie nie mieszcza
		// 10 - odstep pomiedzy podzialkami musi byc wiekszy niz 10px
		while (skala <= 10) {
			wartoscOstatniejPodzialki = wartoscOstatniejPodzialki / 10;
			skala = Math.abs((graph.getWidth() / 2) / wartoscOstatniejPodzialki);
			i++;
		}

		// jesli skala jest zbyt mala i zadna podzialka nie bylaby widoczna
		// podzielone na 10 zeby zawze bylo przynajmniej 10 podzialek
		while (skala > (graph.getWidth() / 2)) {
			wartoscOstatniejPodzialki = wartoscOstatniejPodzialki * 10;
			skala = Math.abs((graph.getWidth() / 2) / wartoscOstatniejPodzialki);
			i--;
		}

		// zapobiega bledom zwiazanym z konwersja z double na int
		skala = Math.round(skala);
		podzialka = (int) skala;

		// do wyswietlenia wartosci podzialki
		double podzialkaWyswietlana = Math.pow(10, i);
		wartoscOstatniejPodzialki = Math.hypot(X, Y);

		// tworzy opisy do osi
		opisyOsi[0] = "r(zakres)= " + String.valueOf(wartoscOstatniejPodzialki);
		opisyOsi[1] = "Podzialka: " + String.valueOf(podzialkaWyswietlana);
	}

	@Override
	void wyznaczPunkty() {
		double a = parametrA.doubleValue();
		if (Double.isInfinite(a))
			a = Double.MAX_VALUE;
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
		// niebędącego oskryptowaną figurą, oraz wyznaczenie skali rysunku

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
				drawLine(kat, graphW2, graphH2, krzywa);
			else {
				drawLine(0, graphW2, graphH2, krzywa);
				return;
			}
			double katy = getRadWithout2Multiply(zRad);
			double stopnie = katy * 180;
			mirrorTransformForDegree(stopnie, graphW2, graphH2);
			return;
		} else if (Double.isNaN(skala) && Math.abs(b) < 1) {
			drawCircle(graphW, graphH, krzywa);
			return;
		} else if (Double.isNaN(skala) && Math.abs(b) == 1) {
			drawArc(2 * Math.PI, graphW, graphH, krzywa);
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
			if (odlOstPKT < odl1PKT) {
				double o = odlOstPKT;
				odlOstPKT = odl1PKT;
				odl1PKT = o;
			}
			drawRing(odl1PKT, odlOstPKT, graphW, graphH, krzywa);
			return;
		} else if (Math.abs(odlOstPKT - odlPOstPKT) <= 2.1 && Math.abs(b) <= 1 && Math.abs(zRad) < 2) {
			drawArc(z, graphW, graphH, krzywa);
			return;
		}

		long czasStart = System.currentTimeMillis();
		ArrayList<KatPunkt> katy = new ArrayList<KatPunkt>();
		boolean OK = false;
		double probki = 0.5;
		while (!OK) {
			int iloscPKT = 0;
			katy.clear();
			krzywa = new BufferedImage(graphW, graphH, BufferedImage.TYPE_INT_ARGB);

			Point start = new Point(), koniec = new Point();
			getStartStopPoint(start, koniec, graphW, graphH);

//			long czasPoczatkowy = System.currentTimeMillis();
			// Wykonywanie rysowania wielowątkowo
			if (numberOfThread > 1) {
				int dX = koniec.x - start.x;
				int perCore = dX / numberOfThread;
				int rest = dX % numberOfThread;
				int offset = 0;
				int counter = 0;

				for (int i = 0; i < numberOfThread; i++) {
					watki.add(new MultiDraw());
				}

				ArrayList<ArrayList<KatPunkt>> katPunktWatek;
				katPunktWatek = new ArrayList<ArrayList<KatPunkt>>();
				for (int i = 0; i < numberOfThread; i++) {
					katPunktWatek.add(new ArrayList<KatPunkt>());
				}

				// Ustawienie każdego wątku według pracy którą musi wykonać
				for (MultiDraw watek : watki) {
					watek.setArguments(probki, new Point(start.x + perCore * counter + offset, start.y),
							new Point(start.x + perCore * (counter + 1) + offset, koniec.y), az, b, skala, graphW,
							graphH, katStop, katStart, katPunktWatek.get(counter));
					if (rest-- > 0)
						offset++;
					counter++;
					watek.start();
				}

				// Dodawanie punktów jak tylko wątek skończy je wyznaczać
				while (!watki.isEmpty()) {
					for (int i = 0; i < watki.size(); i++) {
						if (!watki.get(i).isAlive()) {
							katy.addAll(watki.get(i).katy);
							watki.remove(i);
						}
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

//				System.out.println("Czasy wykonywania wieluwątków= " + (System.currentTimeMillis() - czasPoczatkowy));
			} else { // Wykonywanie rysowania jednowątkowo
				double katFI;
				for (double X = start.x; X < koniec.x; X += probki) {
					for (double Y = start.y; Y < koniec.y; Y += probki) {

						katFI = getKatFI(X, Y, az, b, skala, graphW, graphH);
						if (katFI <= katStop && katFI >= katStart) {
							addPointOfFunction(az, b, katFI, skala, graphW, graphH, katy);
						}

					}
				}

//				long czasPoczatkowy2 = System.currentTimeMillis();
				Collections.sort(katy, new Comparator<KatPunkt>() {
					@Override
					public int compare(KatPunkt p1, KatPunkt p2) {
						return Double.compare(p1.kat, p2.kat);
					}
				});
//				System.out.println("Czasy wykonywania sortowania= " + (System.currentTimeMillis() - czasPoczatkowy2));
			}

//			long czasPoczatkowy3 = System.currentTimeMillis();
			// Aproksymacja funkcji
			int licznikOdleglosciowy = 0;
			for (int i = 1; i < katy.size(); i++) {
				double odleglosc = mathDistanceOfPoints(katy.get(i).pkt, katy.get(i - 1).pkt);
				if (odleglosc <= Math.sqrt(2)) {
					iloscPKT++;
					krzywa.setRGB(katy.get(i).pkt.x, katy.get(i).pkt.y, Color.BLUE.getRGB());
				} else {
					if (odleglosc < Math.sqrt(2) * 3) {
						drawLine(katy.get(i).pkt, katy.get(i - 1).pkt, graphW2, graphH2, krzywa);
					} else {
						licznikOdleglosciowy++;
					}
				}
			}

//			System.out.println("Czasy wykonywania aproksymowania= " + (System.currentTimeMillis() - czasPoczatkowy3));

			// Ocena czy wykres po próbkowaniu kwalifikuje się do dokładniejszego
			// próbkowania
			if (licznikOdleglosciowy > iloscPKT / 32 && probki >= 1.0 / 16 || iloscPKT == 0) {
				new Wykres(graph, krzywa, podzialka, opisyOsi);
				probki /= 2.0;
				OK = false;
			} else {
				OK = true;
			}
			katy.clear();
		}

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
			AffineTransform at = new AffineTransform();
			at.concatenate(AffineTransform.getScaleInstance(-1, 1));
			at.concatenate(AffineTransform.getTranslateInstance(-krzywa.getWidth(), 0));
			BufferedImage tranImg = new BufferedImage(krzywa.getWidth(), krzywa.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = (Graphics2D) tranImg.getGraphics();
			g2d.transform(at);
			g2d.drawImage(krzywa, 0, 0, null);
			g2d.dispose();
			krzywa = tranImg;
		}
		if (stopnie >= 180 && stopnie < 270) {
			AffineTransform at = new AffineTransform();
			at.concatenate(AffineTransform.getScaleInstance(-1, -1));
			at.concatenate(AffineTransform.getTranslateInstance(-krzywa.getWidth(), -krzywa.getHeight()));
			BufferedImage tranImg = new BufferedImage(krzywa.getWidth(), krzywa.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = (Graphics2D) tranImg.getGraphics();
			g2d.transform(at);
			g2d.drawImage(krzywa, 0, 0, null);
			g2d.dispose();
			krzywa = tranImg;
		}
		if (stopnie >= 270 && stopnie < 360) {
			AffineTransform at = new AffineTransform();
			at.concatenate(AffineTransform.getScaleInstance(1, -1));
			at.concatenate(AffineTransform.getTranslateInstance(0, -krzywa.getHeight()));
			BufferedImage tranImg = new BufferedImage(krzywa.getWidth(), krzywa.getHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = (Graphics2D) tranImg.getGraphics();
			g2d.transform(at);
			g2d.drawImage(krzywa, 0, 0, null);
			g2d.dispose();
			krzywa = tranImg;
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
	 * Klasa dodająca wielowątkowo punkty spirali
	 * 
	 * @author 7Adrian
	 * @since 1.1
	 */
	private class MultiDraw extends Thread implements Runnable {
		double probki;
		Point start;
		Point koniec;
		double az;
		double b;
		double skala;
		int graphW;
		int graphH;
		double katStop;
		double katStart;
		ArrayList<KatPunkt> katy;

		/**
		 * Konstruktor nowego wątku
		 */
		MultiDraw() {
			super();
		}

		/**
		 * Wyznacza punkty i układa je w kolejności rosnącej według kąta
		 */
		@Override
		public void run() {
//			long czasPoczatkowy = System.currentTimeMillis();
			for (double X = start.x; X < koniec.x; X += probki) {
				for (double Y = start.y; Y < koniec.y; Y += probki) {
					addPoint(X, Y);
				}
			}
			Collections.sort(katy, new Comparator<KatPunkt>() {
				@Override
				public int compare(KatPunkt p1, KatPunkt p2) {
					return Double.compare(p1.kat, p2.kat);
				}
			});
//			System.out.println("Czasy wykonywania wyatku" + currentThread().getName() + "= "
//					+ (System.currentTimeMillis() - czasPoczatkowy));
		}

		/**
		 * Dodaje punkt o współrzędnych X i Y
		 * 
		 * @param X - Współrzędna X
		 * @param Y - Współrzędna Y
		 */
		private void addPoint(double X, double Y) {
			double katFI;
			katFI = getKatFI(X, Y, az, b, skala, graphW, graphH);
			if (katFI <= katStop && katFI >= katStart) {
				addPointOfFunction(az, b, katFI, skala, graphW, graphH, katy);
			}
		}

		/**
		 * Ustawia niezbędne dane dla wykonania obliczeń przez wątek
		 * 
		 * @param probki   - dokładność próbkowania po X i Y
		 * @param start    - Punkt początkowy od którego zaczyna być wykonywana pętla
		 * @param koniec   - Punkt końcowy do którego będzie wykonywana pętla
		 * @param az       - parametr A spirali
		 * @param b        - parametr B spirali
		 * @param skala    - skala wykresu
		 * @param graphW   - szerokość okienka
		 * @param graphH   - wysokość okienka
		 * @param katStop  - kąt końcowy spirali
		 * @param katStart - kąt początkowy spirali
		 * @param katy     - {@link ArrayList<KatPunkt>} katy do której będą dodane nowe
		 *                 punkty
		 */
		void setArguments(double probki, Point start, Point koniec, double az, double b, double skala, int graphW,
				int graphH, double katStop, double katStart, ArrayList<KatPunkt> katy) {
			this.probki = probki;
			this.start = start;
			this.koniec = koniec;
			this.az = az;
			this.b = b;
			this.skala = skala;
			this.graphW = graphW;
			this.graphH = graphH;
			this.katStart = katStart;
			this.katStop = katStop;
			this.katy = katy;
		}

	}

	/**
	 * Klasa przypominająca klasę Point z API Javy, natomiast rozszerząja ją o
	 * dodatkową zmienną zmiennoprzecinkową
	 * 
	 * @author 7Adrian
	 * @since 1.0
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
	 * @since 1.0
	 */
	public static class SpiralaLogarytmicznaBuilder implements Builder {
		String parametrAText = null;
		String parametrBText = null;
		String zakresText = null;

		BigDecimal parametrA = null;
		BigDecimal parametrB = null;
		BigDecimal zakres = null;
		BufferedImage graph = null;

		int numberOfThread = 0;

		@Override
		public SpiralaLogarytmicznaBuilder setParametrA(String parametrAText) {
			this.parametrAText = parametrAText;
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

		/**
		 * Nadaję liczbę wątków jaką będzie używać aplikacja podczas rysowania Spirali
		 * 
		 * @param numberOfThread - liczba wątków która będzie zaangażowana w rysowanie
		 * @return - Wewnętrzna klasa Budowniczego
		 */
		public SpiralaLogarytmicznaBuilder setThreads(int numberOfThread) {
			this.numberOfThread = numberOfThread;
			return this;
		}

		@Override
		public SpiralaLogarytmiczna build() throws ExceptionInInitializerError {
			if (numberOfThread == 0)
				numberOfThread = Runtime.getRuntime().availableProcessors();

			Figury.setKomentarz(sprawdzParametry());

			if (parametrAText != null && parametrBText != null && zakresText != null && graph != null
					&& sprawdzParametry() == null) {
				parametrA = new BigDecimal(parametrAText);
				parametrB = new BigDecimal(parametrBText);
				zakres = new BigDecimal(zakresText);
				this.zakres = zakres.multiply(new BigDecimal(Math.PI));
				return new SpiralaLogarytmiczna(parametrA, parametrB, zakres, graph, numberOfThread);
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
//					"U+03C6 musi należeć do liczb rzeczywistych" };
			int[] a = { 0, 0, 0, 0, 0, 0, 0, 0 };

			if (parametrAText == null || parametrBText == null || zakresText == null)
				return null;

			parametrAText = parametrAText.replace(',', '.');
			if (parametrAText.charAt(0) == '.') {
				parametrAText = parametrAText.replace(".", "0.");
				System.out.println(parametrAText);
			}

			parametrBText = parametrBText.replace(',', '.');
			if (parametrBText.charAt(0) == '.')
				parametrBText = parametrBText.replace(".", "0.");

			zakresText = zakresText.replace(',', '.');
			if (zakresText.charAt(0) == '.')
				zakresText = zakresText.replace(".", "0.");

			if (parametrAText.isEmpty()) // dodaj komentarz a nieustawione
				a[0] = 1;
			if (parametrBText.isEmpty()) // dodaj komentarz b nieustawione
				a[1] = 1;
			if (zakresText.isEmpty()) // dodaj komentarz z nieustawione
				a[2] = 1;
			if (isItANumber(parametrAText))
				if (Double.valueOf(parametrAText) < 0) {
					a[3] = 1;
					a[4] = 1;
				}
			if (!(isItANumber(parametrAText))) {
				a[3] = 1;
				a[5] = 1;
			}
			if (!(isItANumber(parametrBText))) {
				a[3] = 1;
				a[6] = 1;
			}
			if (!(isItANumber(zakresText))) {
				a[3] = 1;
				a[7] = 1;
			}

			for (int i = 0; i < a.length; i++)
				if (a[i] == 1)
					return a;
			return null;

		}

		/**
		 * Sprawdza czy da się przekonwertować obiekt typu String na obiekt liczbowy
		 * 
		 * @param string
		 * @return true jeśli string da się przekonwetować na liczbę
		 * @author Pafeu
		 */
		private boolean isItANumber(String string) {

			int j = 0;
			if (string == null || string.isEmpty())
				return false;
			for (int i = 0; i < string.length(); i++) {
				if (string.charAt(0) == "-".charAt(0) && i == 0)
					i++;
				if (string.charAt(0) == ".".charAt(0))
					return false;
				if (!((int) string.charAt(i) <= 57 && (int) string.charAt(i) >= 48) && string.charAt(i) != ".".charAt(0)
						&& string.charAt(i) != "E".charAt(0))
					return false;
				if (string.charAt(i) == ".".charAt(0))
					j++;
			}
			if (j > 1)
				return false;
			return true;
		}

	}
}
