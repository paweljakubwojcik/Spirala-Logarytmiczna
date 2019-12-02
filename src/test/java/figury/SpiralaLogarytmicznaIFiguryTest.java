package figury;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.JLabel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import interfejs.Window;

@DisplayName(value = "Test metod z Figur i Spirali Logarytmicznej")
class SpiralaLogarytmicznaIFiguryTest {
	BufferedImage graph;
	int width = 760;
	int height = 400;
	int imageType = BufferedImage.TYPE_INT_ARGB;
	static SpiralaLogarytmiczna spirala;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		BufferedImage graf = new BufferedImage(760, 400, BufferedImage.TYPE_INT_ARGB);
		try {
			spirala = new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("0.5").setParametrB("0.1")
					.setZakres("30").setGraph(graf).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeEach
	void setUp() throws Exception {
		spirala.graph = new BufferedImage(760, 400, BufferedImage.TYPE_INT_ARGB);
	}

	@DisplayName(value = "Obliczanie wartości X funkcji spirali logarytmicznej")
	@Test
	void testWartoscFunkcjiX() {
		// Given
		double a = 10;
		double b = 0.1;
		double z = 24;
		// When
		double wynik = spirala.wartoscFunkcjiX(a, b, z);
		// Then
		assertEquals(46.7580001484, wynik, 0.00001);
	}

	@DisplayName(value = "Obliczanie wartości Y funkcji spirali logarytmicznej")
	@Test
	void testWartoscFunkcjiY() {
		// Given
		double a = 10;
		double b = 0.1;
		double z = 24;
		// When
		double wynik = spirala.wartoscFunkcjiY(a, b, z);
		// Then
		assertEquals(-99.823500, wynik, 0.00001);
	}

	@Test
	void testSetKomentarz()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// GIVEN
		int[] a = { 0, 0, 0, 1, 1 };
		Window okno = new Window();
		okno.setVisible(false);
		Class<?> okno2 = okno.getClass();
		Field komentarz = okno2.getDeclaredField("poleKomentarz");
		komentarz.setAccessible(true);
		JLabel poleKomentarz = (JLabel) komentarz.get(okno);
		// WHEN
		SpiralaLogarytmiczna.setKomentarz(a);
		String komentarzText = poleKomentarz.getText();
		// THEN
		okno.dispose();
		assertEquals("Podano niepoprawne dane.\n a musi być większe od zera\n ", komentarzText);
	}

	@DisplayName(value = "setOpis() sprawdza czy wykonanie nowej spirali zmienia opis w interfejs.Window")

	@Test
	void testSetOpis()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// Given
		Window okno = new Window();
		okno.setVisible(false);
		Class<?> okno2 = okno.getClass();
		Field ParamA = okno2.getDeclaredField("napisParametrA");
		ParamA.setAccessible(true);
		JLabel PA = (JLabel) ParamA.get(okno);
		Field ParamB = okno2.getDeclaredField("napisParametrB");
		ParamB.setAccessible(true);
		JLabel PB = (JLabel) ParamB.get(okno);
		Field ParamZakresText = okno2.getDeclaredField("napisZakres");
		ParamZakresText.setAccessible(true);
		JLabel PZT = (JLabel) ParamZakresText.get(okno);
		Field ParamJednostkaZakresText = okno2.getDeclaredField("napisZakresJednostka");
		ParamJednostkaZakresText.setAccessible(true);
		JLabel PJZT = (JLabel) ParamJednostkaZakresText.get(okno);
		// When
		String A = PA.getText();
		String B = PB.getText();
		String ZT = PZT.getText();
		String JZT = PJZT.getText();
		// Then
		okno.dispose();
		assertAll("Sprawdza czy opisy w interfejs.Window zostały ustawione", () -> assertEquals("a=", A),
				() -> assertEquals("b=", B), () -> assertEquals("zakres=", ZT), () -> assertEquals("rad", JZT));
	}

	@DisplayName(value = "isGood() dla linni z punktami blisko siebie")
	@Test
	void testIsGoodTure() {
		// GIVEN
		ArrayList<Point> punkty = new ArrayList<Point>();
		double Y = 50;
		for (int X = 100; X < 150; X++) {
			Y += 0.75;
			punkty.add(new Point(X, (int) Y));
		}
		// WHEN
		boolean result = spirala.isGood(punkty);
		// THEN
		assertTrue(result);
	}

	@DisplayName(value = "isGood() dla linni z punktami daleko od siebie")
	@Test
	void testIsGoodFalse() {
		// GIVEN
		ArrayList<Point> punkty = new ArrayList<Point>();
		double Y = 50;
		for (int X = 100; X < 150; X++) {
			Y += 2.5;
			punkty.add(new Point(X, (int) Y));
		}
		// WHEN
		boolean result = spirala.isGood(punkty);
		// THEN
		assertFalse(result);
	}

	@DisplayName(value = "isGood() dla pustej listy")
	@Test
	void testIsGoodEmptyList() {
		// GIVEN
		ArrayList<Point> punkty = new ArrayList<Point>();
		// WHEN
		boolean result = spirala.isGood(punkty);
		// THEN
		assertTrue(result);
	}

	@DisplayName(value = "getImage() dla takich samych obrazków")
	@Test
	void testGetImageTrue() {
		// GIVEN
		BufferedImage graf = new BufferedImage(200, 300, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) graf.getGraphics();
		g2d.drawRect(10, 10, 100, 100);
		spirala.graph = graf;
		// WHEN
		boolean result = porownajObrazki(spirala.getImage(), graf);
		// THEN
		assertTrue(result);
	}

	@DisplayName(value = "getImage() dla róznych obrazków")
	@Test
	void testGetImageFalse() {
		// GIVEN
		BufferedImage graf = new BufferedImage(200, 300, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) graf.getGraphics();
		g2d.drawRect(10, 10, 100, 100);
		spirala.graph = graf;
		BufferedImage graf2 = new BufferedImage(200, 300, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d2 = (Graphics2D) graf2.getGraphics();
		g2d2.drawLine(0, 0, 10, 10);
		// WHEN
		boolean result = porownajObrazki(spirala.getImage(), graf2);
		// THEN
		assertFalse(result);
	}

	@DisplayName(value = "clearDoubledPoints() dla listy która ma powtórzone punkty")
	@Test
	void testClearDoubledPointsTrue() {
		// Given
		ArrayList<Point> punkty = new ArrayList<Point>();
		punkty.add(new Point(1, 1));
		punkty.add(new Point(1, 1));
		punkty.add(new Point(1, 1));
		punkty.add(new Point(2, 1));
		punkty.add(new Point(1, 2));
		punkty.add(new Point(5, 5));
		// When
		Figury.clearDoubledPoints(punkty);
		// Then
		boolean result = false;
		ArrayList<Point> pkt = new ArrayList<Point>();
		pkt.add(new Point(1, 1));
		pkt.add(new Point(2, 1));
		pkt.add(new Point(1, 2));
		pkt.add(new Point(5, 5));
		if (punkty.equals(pkt)) {
			result = true;
		}
		assertTrue(result, "Powtórzone punkty zostały źle usunięte");
	}

	@DisplayName(value = "clearDoubledPoints() dla listy która nie ma powtórzonych punktów")
	@Test
	void testClearDoubledPointsTrueWithoutDoubledPoints() {
		// Given
		ArrayList<Point> punkty = new ArrayList<Point>();
		punkty.add(new Point(1, 1));
		punkty.add(new Point(2, 1));
		punkty.add(new Point(1, 2));
		punkty.add(new Point(5, 5));
		// When
		Figury.clearDoubledPoints(punkty);
		// Then
		boolean result = false;
		ArrayList<Point> pkt = new ArrayList<Point>();
		pkt.add(new Point(1, 1));
		pkt.add(new Point(2, 1));
		pkt.add(new Point(1, 2));
		pkt.add(new Point(5, 5));
		if (punkty.equals(pkt)) {
			result = true;
		}
		assertTrue(result, "Punkty zostały usunięte lub zmienione a nie powinny");
	}

	@DisplayName(value = "clearDoubledPoints() dla pustej listy")
	@Test
	void testClearDoubledPointsTrueForBlankList() {
		// Given
		ArrayList<Point> punkty = new ArrayList<Point>();
		// When
		Figury.clearDoubledPoints(punkty);
		// Then
		boolean result = false;
		ArrayList<Point> pkt = new ArrayList<Point>();
		if (punkty.equals(pkt)) {
			result = true;
		}
		assertTrue(result, "Pojawiły się jakieś punkty. Skąd?");
	}

	@ParameterizedTest(name = "X= {0}; Y= {1}; graphW= {2}; graphH= {3}; wynik= {4}")
	@CsvSource({ "10, 20, 800, 600, True", "-10, 20, 800, 600, False", "-10, -20, 800, 600, False",
			"900, 20, 800, 600, False", "10, 800, 800, 600, False", "800, 20, 800, 600, False",
			"10, 600, 800, 600, False", "800, 600, 800, 600, False", "10, -20, 800, 600, False" })
	@DisplayName(value = "Testy funkcji isXYinImage(dla X, Y, graphW, graphH)")
	void testIsXYinImageDoubleDoubleIntInt(double X, double Y, int graphW, int graphH, boolean expectedResult) {
		// Given

		// When
		boolean result = Figury.isXYinImage(X, Y, graphW, graphH);
		// Then
		assertTrue(result == expectedResult);
	}

	@ParameterizedTest(name = "pkt.X= {0}; pkt.Y= {1}; graphW= {2}; graphH= {3}; wynik= {4}")
	@CsvSource({ "10, 20, 800, 600, True", "-10, 20, 800, 600, False", "-10, -20, 800, 600, False",
			"900, 20, 800, 600, False", "10, 800, 800, 600, False", "800, 20, 800, 600, False",
			"10, 600, 800, 600, False", "800, 600, 800, 600, False", "10, -20, 800, 600, False" })
	@DisplayName(value = "Testy funkcji isXYinImage(dla Point, graphW, graphH)")
	void testIsXYinImagePointIntInt(double X, double Y, int graphW, int graphH, boolean expectedResult) {
		// Given

		// When
		boolean result = Figury.isXYinImage(new Point((int) X, (int) Y), graphW, graphH);
		// Then
		assertTrue(result == expectedResult);
	}

	@DisplayName(value = "getRadWithoutPIMultiply dla 3.5PI, 0.234PI, -12.5PI")
	@Test
	void testGetRadWithoutPIMultiply() {
		// Given
		// When
		// Then
		assertAll("Powinno zwrócić liczbę bez wielokrotności PI",
				() -> assertEquals(0.5 * Math.PI, Figury.getRadWithoutPIMultiply(3.5 * Math.PI), 0.001),
				() -> assertEquals(0.234 * Math.PI, Figury.getRadWithoutPIMultiply(0.234 * Math.PI), 0.001),
				() -> assertEquals(-0.5 * Math.PI, Figury.getRadWithoutPIMultiply(-12.5 * Math.PI), 0.001));
	}

	@DisplayName(value = "getRadWithout2PIMultiply dla 3.5PI, 0.234PI, -12.5PI")
	@Test
	void testGetRadWithout2PIMultiply() {
		// Given
		// When
		// Then
		assertAll("Powinno zwrócić liczbę bez wielokrotności 2PI",
				() -> assertEquals(1.5 * Math.PI, Figury.getRadWithout2PIMultiply(3.5 * Math.PI), 0.001),
				() -> assertEquals(0.234 * Math.PI, Figury.getRadWithout2PIMultiply(0.234 * Math.PI), 0.001),
				() -> assertEquals(-0.5 * Math.PI, Figury.getRadWithout2PIMultiply(-12.5 * Math.PI), 0.001));
	}

	@DisplayName(value = "getRadWithout2Multiply dla 10.354, 0.234, -12.5")
	@Test
	void testGetRadWithout2Multiply() {
		// Given
		// When
		// Then
		assertAll("Powinno zwrócić liczbę bez wielokrotności 2",
				() -> assertEquals(0.354, Figury.getRadWithout2Multiply(10.354), 0.001),
				() -> assertEquals(0.234, Figury.getRadWithout2Multiply(0.234), 0.001),
				() -> assertEquals(-0.5, Figury.getRadWithout2Multiply(-12.5), 0.001));
	}

	@DisplayName(value = "getSkala(double Point Point) dla 400, Point(100, 100), Point(200, 300)")
	@Test
	void testGetSkalaDoublePointPoint() {
		// Given
		// When
		// Then
		assertEquals(0.8944271909999159, Figury.getSkala(400, new Point(100, 100), new Point(200, 300)), 0.000000001);
	}

	@DisplayName(value = "getSkala(double Point double double) dla 400, Point(100, 100), 200, 300")
	@Test
	void testGetSkalaDoublePointDoubleDouble() {
		// Given
		// When
		// Then
		assertEquals(0.8944271909999159, Figury.getSkala(400, new Point(100, 100), 200, 300), 0.000000001);
	}

	@DisplayName(value = "getSkala(double double double double double) dla 400, 100, 100, 200, 300")
	@Test
	void testGetSkalaDoubleDoubleDoubleDoubleDouble() {
		// Given
		// When
		// Then
		assertEquals(0.8944271909999159, Figury.getSkala(400, 100, 100, 200, 300), 0.000000001);
	}

	@DisplayName(value = "getStartStopPoint() dla szerkości wykresu większej od wysokości")
	@Test
	void testGetStartStopPoint0() {
		// Given
		Point start = new Point(100, 100);
		Point koniec = new Point(200, 300);
		int graphW = 800;
		int graphH = 600;
		// When
		Figury.getStartStopPoint(start, koniec, graphW, graphH);
		// Then
		assertAll("Powinno nadpisać wartości start stop nowymi", () -> assertEquals(new Point(98, 0), start),
				() -> assertEquals(new Point(702, 600), koniec));
	}

	@DisplayName(value = "getStartStopPoint() dla wysokości wykresu większej od szerokości")
	@Test
	void testGetStartStopPoint1() {
		// Given
		Point start = new Point(100, 100);
		Point koniec = new Point(200, 300);
		int graphW = 600;
		int graphH = 800;
		// When
		Figury.getStartStopPoint(start, koniec, graphW, graphH);
		// Then
		assertAll("Powinno nadpisać wartości start stop nowymi", () -> assertEquals(new Point(0, 98), start),
				() -> assertEquals(new Point(600, 702), koniec));
	}

	@DisplayName(value = "transformFunctionToPixels() dla 10, 20, 6, 800, 600")
	@Test
	void testTransformFunctionToPixels() {
		// Given
		double x = 10;
		double y = 20;
		double skala = 5;
		int graphW = 800;
		int graphH = 600;
		// When
		// Then
		assertEquals(new Point(450, 200), Figury.transformFunctionToPixels(x, y, skala, graphW, graphH));
	}

	@ParameterizedTest(name = "x= {0}; y= {1}; wynik= {2}")
	@CsvSource({ "5, 0, 5", "0, 5, 5", "3, 4, 5", "10, 12, 15.620499351813308", "-5, 0, 5", "0, -5, 5", "-3, -4, 5" })
	@DisplayName(value = "mathDistanceOfPoint() dla wartosci double double")
	void testMathDistanceOfPointDoubleDouble(double x, double y, double wynik) {
		// Given
		// When
		// Then
		assertEquals(wynik, Figury.mathDistanceOfPoint(x, y), 0.0001);
	}

	@ParameterizedTest(name = "x= {0}; y= {1}; wynik= {2}")
	@CsvSource({ "5, 0, 5", "0, 5, 5", "3, 4, 5", "10, 12, 15.620499351813308", "-5, 0, 5", "0, -5, 5", "-3, -4, 5" })
	@DisplayName(value = "mathDistanceOfPoint() dla wartosci Point(x, y)")
	void testMathDistanceOfPointPoint(double x, double y, double wynik) {
		// Given
		// When
		// Then
		assertEquals(wynik, Figury.mathDistanceOfPoint(new Point((int) x, (int) y)), 0.0001);
	}

	@ParameterizedTest(name = "x1= {0}; y1= {1}; x2= {2}; y2= {3}; wynik= {4}")
	@CsvSource({ "5, 0, 0, 0, 5", "0, 5, 0, 0, 5", "3, 4, 0, 0, 5", "10, 12, 0, 0, 15.620499351813308",
			"-5, 0, 0, 0, 5", "0, -5, 0, 0, 5", "-3, -4, 0, 0, 5", "10, 5, 5, 5, 5", "5, 10, 5, 5, 5", "8, 9, 5, 5, 5",
			"15, 17, 5, 5, 15.620499351813308", "-10, -5, -5, -5, 5", "-5, -10, -5, -5, 5", "-8, -9, -5, -5, 5",
			"-4, 4, 4, -4, 11.313708499" })
	@DisplayName(value = "mathDistanceOfPoint() dla wartosci x1, y1, x2, y2")
	void testMathDistanceOfPointsDoubleDoubleDoubleDouble(double x1, double y1, double x2, double y2, double wynik) {
		// Given
		// When
		// Then
		assertEquals(wynik, Figury.mathDistanceOfPoints(x1, y1, x2, y2), 0.0001);
	}

	@ParameterizedTest(name = "x1= {0}; y1= {1}; x2= {2}; y2= {3}; wynik= {4}")
	@CsvSource({ "5, 0, 0, 0, 5", "0, 5, 0, 0, 5", "3, 4, 0, 0, 5", "10, 12, 0, 0, 15.620499351813308",
			"-5, 0, 0, 0, 5", "0, -5, 0, 0, 5", "-3, -4, 0, 0, 5", "10, 5, 5, 5, 5", "5, 10, 5, 5, 5", "8, 9, 5, 5, 5",
			"15, 17, 5, 5, 15.620499351813308", "-10, -5, -5, -5, 5", "-5, -10, -5, -5, 5", "-8, -9, -5, -5, 5",
			"-4, 4, 4, -4, 11.313708499" })
	@DisplayName(value = "mathDistanceOfPoint() dla wartosci Point(x1, y1) Point(x2, y2)")
	void testMathDistanceOfPointsPointPoint(double x1, double y1, double x2, double y2, double wynik) {
		// Given
		// When
		// Then
		assertEquals(wynik, Figury.mathDistanceOfPoints(new Point((int) x1, (int) y1), new Point((int) x2, (int) y2)),
				0.0001);
	}

	boolean porownajObrazki(BufferedImage spirala, BufferedImage graph) {
		int spiralaColor;
		int graphColor;
		if (spirala.getWidth() == graph.getWidth() && spirala.getHeight() == graph.getHeight()) {
			for (int x = 0; x < spirala.getWidth(); x++) {
				for (int y = 0; y < spirala.getHeight(); y++) {
					spiralaColor = spirala.getRGB(x, y);
					graphColor = graph.getRGB(x, y);
					if (spiralaColor != graphColor) {
						return false;
					}
				}
			}
		} else {
			return false;
		}
		return true;
	}

}
