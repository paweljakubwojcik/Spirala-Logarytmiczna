package figury;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import interfejs.Window;
import wykres.Wykres;

/**
 * Klasa Figury zawiera informacje o metodach i polach które muszą zawierać
 * dziedziczące klasy. Dodatkowo są dostępne metody pomocne przy tworzenieniu
 * rysunku innych figur. Ponaddto tylko ta klasa ma możliwość zmiany opisów poł
 * w klasie interfejs.Window.
 * 
 * @author 7Adrian
 * @since 1.0
 */
public abstract class Figury {

	/**
	 * Zawiera komunikaty jakie mają się wyświetlać według kryterium zawartym w
	 * wymaganiach
	 */
	private static final String[] komentarz = { "Parametr a nie został ustawiony", "Parametr b nie został ustawiony",
			"Zakres nie został ustawiony", "Podano niepoprawne dane.\n", "a musi być większe od zera\n",
			"a musi należeć do liczb rzeczywistych\n", "b musi należeć do liczb rzeczywistych\n",
			"U+03C6 musi nale�e� do liczb rzeczywistych" };
	String[] opisy = { " ", " ", " ", " " };
	BigDecimal parametrA;
	BigDecimal parametrB;
	BigDecimal zakres;
	ArrayList<Point> punkty = new ArrayList<Point>();
	BufferedImage graph;

	/**
	 * Generuje nową krzywą oraz rysuje jej wykres
	 * 
	 * @param opisy     - Definiuje jaki tekst będzie się wyświetlał przy: 0)
	 *                  parametrzeA 1) parametrzeB 2) parametrZakres 3)
	 *                  jednkostceZakresu. String ten powinien zawierać dokładnie 4
	 *                  elementy
	 * @param parametrA - Pierwszy parametr funkcji
	 * @param parametrB - Drugi parametr funkcji
	 * @param zakres    - Trzeci parametr funkcji (najczęsciej zarkes)
	 * @param graph     - Obrazek w którym ma się narysować wykres funkcji
	 */
	Figury(String[] opisy, BigDecimal parametrA, BigDecimal parametrB, BigDecimal zakres, BufferedImage graph) {
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

	/**
	 * Generuje punkty spirali i zapisuje je w ArrayList<Punkty> punkty
	 */
	abstract void wyznaczPunkty();

	/**
	 * Funkcja przyjmuje parametry dla których ma się policzyć jej wartość w osi X,
	 * oraz zwrócić ją jako typ prosty liczby zmienno przecinkowej
	 * 
	 * @param a - Pierwszy parametr funkcji
	 * @param b - Drugi parametr funkcji
	 * @param z - Trzeci parametr funkcji, punkty z zakresu dla którego ma policzyć
	 *          się wartość
	 * @return - Wartość funkcji X(a, b, z)
	 */
	abstract double wartoscFunkcjiX(double a, double b, double z);

	/**
	 * Funkcja przyjmuje parametry dla których ma się policzyć jej wartość w osi Y,
	 * oraz zwrócić ją jako typ prosty liczby zmienno przecinkowej
	 * 
	 * @param a - Pierwszy parametr funkcji
	 * @param b - Drugi parametr funkcji
	 * @param z - Trzeci parametr funkcji, punkty z zakresu dla którego ma policzyć
	 *          się wartość
	 * @return - Wartość funkcji Y(a, b, z)
	 */
	abstract double wartoscFunkcjiY(double a, double b, double z);

	/**
	 * Funkcja wyświetla komentarze w polu w klasie interfejs.Window
	 * 
	 * @param numer - zbiór numerów odpowiadających odpowiednim tekstom w zmiennej
	 *              komentarz które mają się wyświetlić
	 */
	static void setKomentarz(int[] numer) {
		String wysylanyKomentarz = "";
		if (numer != null) {
			for (int i = 0; i < numer.length; i++) {
				if (numer[i] == 1)
					wysylanyKomentarz += komentarz[i];
			}
		}
		try {
			Window.setKomentarz(wysylanyKomentarz);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ustawia opisy parametrów w klasie interfejs.Window
	 */
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

	/**
	 * Funkcja sprawdza czy w podanej liście sąsiadujące pusnky są obok siebie
	 * 
	 * @param punkty - ArrayLista<Point> z punktami które będą sprawdzane
	 * @return - True -> jeśli między punktami nie będzie pixelowej przerwy; False
	 *         -> jeśli między punktami jest przerwa (brakuje punktów pomiędzy)
	 */
	boolean isGood(ArrayList<Point> punkty) {
		for (int i = 1; i < punkty.size(); i++) {
			if (Math.sqrt(Math.pow(punkty.get(i).getX() - punkty.get(i - 1).getX(), 2)
					+ Math.pow(punkty.get(i).getY() - punkty.get(i - 1).getY(), 2)) > Math.sqrt(2)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @return - Zwraca obrazek krzywej
	 */
	public BufferedImage getImage() {
		return graph;
	}

	/**
	 * Rysuje łuk
	 * 
	 * @param z      - zakres dla jakiego ma powstać łuk
	 * @param graphW - szerokość okienka w którym będzie rysowany łuk
	 * @param graphH - wysokość okienka w którym będzie rysowany łuk
	 * @param punkty - ArrayLista<Point> w której będą dodawane punkty odpowiadające
	 *               danemu łukowi
	 */
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
		double skala = getSkala(rozmiar, 0, 0, X2, Y2) * 0.995;
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

	/**
	 * Czyści punkty które się powtarzają
	 * 
	 * @param punkty - ArrayList<Point> w której zdublowane elementy będą usunięte
	 */
	public static void clearDoubledPoints(ArrayList<Point> punkty) {
		Set<Point> set = new LinkedHashSet<Point>();
		set.addAll(punkty);
		punkty.clear();
		punkty.addAll(set);
	}

	/**
	 * Sprawdza czy punkt należy do obszaru rysowania
	 * 
	 * @param X      - współrzędna X punktu
	 * @param Y      - współrzędna Y punktu
	 * @param graphW - szerokość obszaru rysowania
	 * @param graphH - wysokość obszaru rysowania
	 * @return - True -> punkt znajduje się w obszarze rysowania; False -> punkt nie
	 *         znajduje się w obszarze rysowania
	 */
	public static boolean isXYinImage(double X, double Y, int graphW, int graphH) {
		return (X >= 0 && X < graphW && Y >= 0 && Y < graphH);
	}

	/**
	 * Sprawdza czy punkt należy do obszaru rysowania
	 * 
	 * @param pkt    - Punkt którego obecność w obszarze będzie sprawdzana
	 * @param graphW - szerokość obszaru rysowania
	 * @param graphH - wysokość obszaru rysowania
	 * @return - True -> punkt znajduje się w obszarze rysowania; False -> punkt nie
	 *         znajduje się w obszarze rysowania
	 */
	public static boolean isXYinImage(Point pkt, int graphW, int graphH) {
		return (pkt.x >= 0 && pkt.x < graphW && pkt.y >= 0 && pkt.y < graphH);
	}

	/**
	 * Rysuje linnie między dwoma punktami
	 * 
	 * @param pkt1   - punkt 1
	 * @param pkt2   - punkt 2
	 * @param graphW - szerokość obszaru rysowania
	 * @param graphH - wysokość obszaru rysownaia
	 * @param punkty - ArrayList<Point> w której będą dodawane punkty
	 */
	public static void drawLine(Point pkt1, Point pkt2, int graphW, int graphH, ArrayList<Point> punkty) {
		drawLine(pkt1.x, pkt1.y, pkt2.x, pkt2.y, graphW, graphH, punkty);
	}

	/**
	 * Rysuje linnie między dwoma punktami
	 * 
	 * @param pkt1   - punkt 1
	 * @param x      - współrzędna x punktu 2
	 * @param y      - współrzędna y punktu 2
	 * @param graphW - szerokość obszaru rysowania
	 * @param graphH - wysokość obszaru rysownaia
	 * @param punkty - ArrayList<Point> w której będą dodawane punkty
	 */
	public static void drawLine(Point pkt1, double x, double y, int graphW, int graphH, ArrayList<Point> punkty) {
		drawLine(pkt1.x, pkt1.y, x, y, graphW, graphH, punkty);
	}

	/**
	 * Rysuje linnie między dwoma punktami
	 * 
	 * @param x1     - współrzędna x punktu 1
	 * @param y1     - współrzędna y punktu 1
	 * @param x2     - współrzędna x punktu 2
	 * @param y2     - współrzędna y punktu 2
	 * @param graphW - szerokość obszaru rysowania
	 * @param graphH - wysokość obszaru rysownaia
	 * @param punkty - ArrayList<Point> w której będą dodawane punkty
	 */
	public static void drawLine(double x1, double y1, double x2, double y2, int graphW, int graphH,
			ArrayList<Point> punkty) {
		double aa = (y2 - y1) / (x2 - x1);
		double bb = (x2 - x1) / (y2 - y1);
		Point pkt;
		if (Math.abs(aa) <= 1) {
			if (x2 > x1) {
				for (int DX = 1; DX < x2 - x1; DX++) {
					pkt = new Point((int) (x1 + DX), (int) (aa * DX + y1));
					if (isXYinImage(pkt, graphW, graphH))
						punkty.add(pkt);
				}
			} else {
				for (int DX = 1; DX < x1 - x2; DX++) {
					pkt = new Point((int) (x2 + DX), (int) (aa * DX + y2));
					if (isXYinImage(pkt, graphW, graphH))
						punkty.add(pkt);
				}
			}
		} else {
			if (y2 > y1) {
				for (int DY = 1; DY < y2 - y1; DY++) {
					pkt = new Point((int) (bb * DY + x1), (int) (y1 + DY));
					if (isXYinImage(pkt, graphW, graphH))
						punkty.add(pkt);
				}
			} else {
				for (int DY = 1; DY < y1 - y2; DY++) {
					pkt = new Point((int) (bb * DY + x2), (int) (y2 + DY));
					if (isXYinImage(pkt, graphW, graphH))
						punkty.add(pkt);
				}
			}
		}
	}

	/**
	 * Rysuje linie pod zadanym kątem od środka układu biegunowego do końca
	 * rysowanego zakresu
	 * 
	 * @param degree  - kąt pod jakim ma zostać narysowana linia
	 * @param graphW2 - połowa szerokości rysowanego obszaru
	 * @param graphH2 - połowa wysokości rysowanego obszaru
	 * @param punkty  - ArrayList<Point> w której będą dodawane punkty
	 */
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

	/**
	 * Odejmuje wielokrotność PI
	 * 
	 * @param rad - kąt od którego ma być odjęta wielokrotność PI
	 * @return - kąt bez wielokrotności liczby PI
	 */
	public static double getRadWithoutPIMultiply(double rad) {
		return rad - Math.PI * ((int) (rad / Math.PI));
	}

	/**
	 * Odejmuje wielokrotność 2 PI
	 * 
	 * @param rad - kąt od którego ma być odjęta wielokrotność 2 PI
	 * @return - kąt bez wielokrotności liczby 2 PI
	 */
	public static double getRadWithout2PIMultiply(double rad) {
		return rad - 2 * Math.PI * ((int) (rad / 2 / Math.PI));
	}

	/**
	 * Odjemuje wielokrtoność 2
	 * 
	 * @param rad - kąt od którego ma być odjęta wielokrotność 2
	 * @return - kąt bez wielokrotności liczby 2
	 */
	public static double getRadWithout2Multiply(double rad) {
		return rad - 2 * ((int) (rad / 2));
	}

	/**
	 * Zwraca skalę rysunku w jakiej należy rysować punkty tak aby ostatni punkt nie
	 * wyszedł poza rysowany zakres
	 * 
	 * @param rozmiar  - wartość mniejszej ze składowych wysokość/szerokość
	 * @param pktStart - punkt początkowy
	 * @param pktStop  - punkt końcowy
	 * @return - skala w jakiej nalezy wykonać rysunek aby ostatni punkt nie wyszedł
	 *         poza rysowany zakres
	 */
	public static double getSkala(double rozmiar, Point pktStart, Point pktStop) {
		return getSkala(rozmiar, pktStart.x, pktStart.y, pktStop.x, pktStop.y);
	}

	/**
	 * Zwraca skalę rysunku w jakiej należy rysować punkty tak aby ostatni punkt nie
	 * wyszedł poza rysowany zakres
	 * 
	 * @param rozmiar - wartość mniejszej ze składowych wysokość/szerokość
	 * @param pkt     - jeden ze skrajnych punktów wykresu
	 * @param x       - współrzędna x drugiego ze skrajnych punktów wykresu
	 * @param y       - współrzędna y drugiego ze skrajnych punktów wykresu
	 * @return - skala w jakiej nalezy wykonać rysunek aby ostatni punkt nie wyszedł
	 *         poza rysowany zakres
	 */
	public static double getSkala(double rozmiar, Point pkt, double x, double y) {
		return getSkala(rozmiar, pkt.x, pkt.y, x, y);
	}

	/**
	 * Zwraca skalę rysunku w jakiej należy rysować punkty tak aby ostatni punkt nie
	 * wyszedł poza rysowany zakres
	 * 
	 * @param rozmiar - wartość mniejszej ze składowych wysokość/szerokość
	 * @param X1      - współrzędna x jednego ze skrajnych punktów wykresu
	 * @param Y1      - współrzędna y jednego ze skrajnych punktów wykresu
	 * @param X2      - współrzędna x drugiego ze skrajnych punktów wykresu
	 * @param Y2      - współrzędna y drugiego ze skrajnych punktów wykresu
	 * @return - skala w jakiej nalezy wykonać rysunek aby ostatni punkt nie wyszedł
	 *         poza rysowany zakres
	 */
	public static double getSkala(double rozmiar, double X1, double Y1, double X2, double Y2) {
		return rozmiar / (2 * mathDistanceOfPoints(X1, Y1, X2, Y2));
	}

	/**
	 * Przypisuje do punktów start i koniec wartości dla których powstały kwadrat da
	 * nam obszar w którym krzywa będzie rysowana. Funkcja ta optymalizuje obszar
	 * rysowany tak, że dla wymiarów o stosunku różnym od 1:1 powstanie obszar o
	 * stosunku 1:1, dzięki czemu pixele dla których należy sprawdzić czy funkcja
	 * istnieje będą ograniczone przez co wyznaczenie ich będzie odbywało się
	 * znacznie szybciej
	 * 
	 * @param start  - punkt początkowy od którego należy zacząć rysować
	 * @param koniec - punkt końcowy do którego należy rysować
	 * @param graphW - szerokość obsszru rysowanego
	 * @param graphH - wysokość obszaru rysowanego
	 */
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

	/**
	 * Zamienia wartość funkcji na odpowiadający jemu pixel
	 * 
	 * @param x      - wartość x funkcji
	 * @param y      - wartość y funkcji
	 * @param skala  - skala obrazka
	 * @param graphW - szerokość obszaru rysowanego
	 * @param graphH - wysokość obszaru rysowanego
	 * @return - Gotowy punkt do narysowania wyznaczony na podstawie wartości
	 *         funkcji
	 */
	public static Point transformFunctionToPixels(double x, double y, double skala, int graphW, int graphH) {
		int graphW2 = graphW / 2;
		int graphH2 = graphH / 2;
		return new Point((int) ((x * skala + graphW2)), (int) ((-y * skala + graphH2)));
	}

	/**
	 * Rysuje pierścień o szerokości różnicy odległości pierwszego i ostatniego
	 * punktu
	 * 
	 * @param odl1PKT   - odległość od środka układu biegunowego do pierwszego
	 *                  punktu
	 * @param odlOstPKT - odległość od środka układu biegtunowego do ostatniego
	 *                  punktu
	 * @param graphW    - szerokość obszaru rysowanego
	 * @param graphH    - wysokość obszaru rysowanego
	 * @param punkty    - ArrayList<Point> w której wyznaczone punkty zostaną
	 *                  zapisane
	 */
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

	/**
	 * Rysuje koło wpisane w kwadrat zrobiony z wymiarów graphW i graphH
	 * 
	 * @param graphW - szerokość obszaru rysowanego
	 * @param graphH - wysokość obszaru rysowanego
	 * @param punkty - ArrayList<Point> w której wyznaczone punkty zostaną zapisane
	 */
	public static void drawCircle(int graphW, int graphH, ArrayList<Point> punkty) {
		drawRing(0.0, Math.min(graphW, graphH) / 2.0, graphW, graphH, punkty);
	}

	/**
	 * Liczy odległość między początkiem układu kartezjańskiego a punktem
	 * 
	 * @param x - współrzędna x punktu
	 * @param y - współrzędna y punktu
	 * @return - odległość między początkiem układu kartezjańskiego a punktu
	 */
	public static double mathDistanceOfPoint(double x, double y) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	/**
	 * Liczy odległość między początkiem układu kartezjańskiego a punktem
	 * 
	 * @param pkt - punkt do którego odległość ma zostać policzona
	 * @return - odległość między początkiem układu kartezjańskiego a punktu
	 */
	public static double mathDistanceOfPoint(Point pkt) {
		return Math.sqrt(Math.pow(pkt.x, 2) + Math.pow(pkt.y, 2));
	}

	/**
	 * Liczy odległość miedzy dwoma punktami
	 * 
	 * @param x1 - współrzędna x pierwszego punktu
	 * @param y1 - współrzędna y pierwszego punktu
	 * @param x2 - współrzędna x drugiego punktu
	 * @param y2 - współrzędna y drugiego punktu
	 * @return - odległość między punktami
	 */
	public static double mathDistanceOfPoints(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	/**
	 * Liczy odległość miedzy dwoma punktami
	 * 
	 * @param pkt1 - punkt pierwszy
	 * @param pkt2 - punkt drugi
	 * @return - odległość między punktami
	 */
	public static double mathDistanceOfPoints(Point pkt1, Point pkt2) {
		return Math.sqrt(Math.pow(pkt1.x - pkt2.x, 2) + Math.pow(pkt1.y - pkt2.y, 2));
	}
}
