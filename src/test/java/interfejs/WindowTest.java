package interfejs;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.rmi.AccessException;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import figury.SpiralaLogarytmiczna;

@DisplayName(value = "Testy klasy interfejs.Window")
class WindowTest {
	static Window okno;
	ComponentEvent s = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		okno = new Window();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		okno.dispose();
	}

	/**
	 * Sprawdza parametry po utworzeniu spirali, jeśli się uda można zamienić na
	 * domyśly text który bedzie np. nazwą pola, tylko wówczas ta metoda powinna
	 * znaleźć się w nowej klasie np. WindowCreateTest.java
	 * 
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@DisplayName(value = "Sprawdzenie poprawności napisów utworzonego się okienka")
	@Test
	void testWindow() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// Given
		okno = new Window();
		Class<?> okno2 = okno.getClass();

		Field ParamA = okno2.getDeclaredField("napisParametrA");
		Field ParamB = okno2.getDeclaredField("napisParametrB");
		Field ParamZakresText = okno2.getDeclaredField("napisZakres");
		Field ParamJednostkaZakresText = okno2.getDeclaredField("napisZakresJednostka");
		Field ButtonRysuj = okno2.getDeclaredField("przyciskRysuj");
		Field ButtonCzysc = okno2.getDeclaredField("przyciskCzysc");
		Field ButtonPelnyEkran = okno2.getDeclaredField("przyciskPelnyEkran");
		Field Parametry = okno2.getDeclaredField("napisParametry");
		Field Komentarze = okno2.getDeclaredField("poleKomentarz");

		ParamA.setAccessible(true);
		ParamB.setAccessible(true);
		ParamZakresText.setAccessible(true);
		ParamJednostkaZakresText.setAccessible(true);
		ButtonRysuj.setAccessible(true);
		ButtonCzysc.setAccessible(true);
		ButtonPelnyEkran.setAccessible(true);
		Parametry.setAccessible(true);
		Komentarze.setAccessible(true);

		JLabel PA = (JLabel) ParamA.get(okno);
		JLabel PB = (JLabel) ParamB.get(okno);
		JLabel PZT = (JLabel) ParamZakresText.get(okno);
		JLabel PJZT = (JLabel) ParamJednostkaZakresText.get(okno);
		JButton RYS = (JButton) ButtonRysuj.get(okno);
		JButton CZYSC = (JButton) ButtonCzysc.get(okno);
		JButton PELNYEKRAN = (JButton) ButtonPelnyEkran.get(okno);
		JLabel PARAM = (JLabel) Parametry.get(okno);
		JLabel KOM = (JLabel) Komentarze.get(okno);

		// When
		String A = PA.getText();
		String B = PB.getText();
		String ZT = PZT.getText();
		String JZT = PJZT.getText();
		String R = RYS.getText();
		String C = CZYSC.getText();
		String PE = PELNYEKRAN.getText();
		String P = PARAM.getText();
		String K = KOM.getText();

		// THEN
		assertAll("Sprawdza opisy utworzonego okienka", () -> assertEquals("Spirala Logarytmiczna", okno.getTitle()),
				() -> assertEquals("Spirala Logarytmiczna", Window.NAZWAPROGRAMUTEXT),
				() -> assertTrue(okno.isVisible()), () -> assertEquals("a=", A), () -> assertEquals("b=", B),
				() -> assertEquals("zakres=", ZT), () -> assertEquals("rad", JZT), () -> assertEquals("RYSUJ", R),
				() -> assertEquals("CZYŚĆ", C), () -> assertEquals("PEŁNY EKRAN", PE),
				() -> assertEquals("Parametry:", P), () -> assertEquals("", K));
		okno.setVisible(false);
	}

	@DisplayName(value = "Ustawianie parametru A w JFrame")
	@Test
	void testSetParametrAText()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// GIVEN
		Class<?> okno2 = okno.getClass();
		Field paramA = okno2.getDeclaredField("napisParametrA");
		paramA.setAccessible(true);
		JLabel parametrA = (JLabel) paramA.get(okno);
		// WHEN
		String parametrAText = parametrA.getText();
		// THEN
		assertEquals("a=", parametrAText);
	}

	@DisplayName(value = "Ustawianie parametru B w JFrame")
	@Test
	void testSetParametrBText()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// GIVEN
		Class<?> okno2 = okno.getClass();
		Field paramB = okno2.getDeclaredField("napisParametrB");
		paramB.setAccessible(true);
		JLabel parametrB = (JLabel) paramB.get(okno);
		// WHEN
		String parametrBText = parametrB.getText();
		// THEN
		assertEquals("b=", parametrBText);
	}

	@DisplayName(value = "Ustawianie zakresu w JFrame")
	@Test
	void testSetZakresText()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// GIVEN
		Class<?> okno2 = okno.getClass();
		Field paramZakres = okno2.getDeclaredField("napisZakres");
		paramZakres.setAccessible(true);
		JLabel parametrZakres = (JLabel) paramZakres.get(okno);
		// WHEN
		String parametrZakresText = parametrZakres.getText();
		// THEN
		assertEquals("zakres=", parametrZakresText);
	}

	@DisplayName(value = "Ustawianie jednostki zakresu w JFrame")
	@Test
	void testSetJednostkaZakresuText()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// GIVEN
		Class<?> okno2 = okno.getClass();
		Field paramJednostkaZakresText = okno2.getDeclaredField("napisZakresJednostka");
		paramJednostkaZakresText.setAccessible(true);
		JLabel parametrJednostkaZakres = (JLabel) paramJednostkaZakresText.get(okno);
		// WHEN
		String parametrJednostkaZakresText = parametrJednostkaZakres.getText();
		// THEN
		assertEquals("rad", parametrJednostkaZakresText);
	}

	@DisplayName(value = "Ustawianie komentarzy w JFrame")
	@Test
	void testSetKomentarz()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// GIVEN
		Class<?> okno2 = okno.getClass();
		Field komentarz = okno2.getDeclaredField("poleKomentarz");
		komentarz.setAccessible(true);
		JLabel poleKomentarz = (JLabel) komentarz.get(okno);
		// WHEN
		String komentarzText = poleKomentarz.getText();
		// THEN
		assertEquals("", komentarzText);
	}

	@DisplayName(value = "Ustawianie komentarzy w JFrame kiedy A jest ujemne")
	@Test
	void testSetKomentarzKiedyAJestUjemne()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// GIVEN
		Class<?> okno2 = okno.getClass();
		Field komentarz = okno2.getDeclaredField("poleKomentarz");
		komentarz.setAccessible(true);
		JLabel poleKomentarz = (JLabel) komentarz.get(okno);
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		// WHEN
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("-10").setParametrB("1500")
					.setZakres("2").setGraph(graphImage).build();
		} catch (ExceptionInInitializerError e) {
		}
		String komentarzText = poleKomentarz.getText();
		// THEN
		assertEquals("Podano niepoprawne dane.\n a musi być większe od zera\n ", komentarzText);
	}

	@DisplayName(value = "Ustawianie komentarzy w JFrame kiedy A nie jest liczbą")
	@Test
	void testSetKomentarzKiedyANieJestLiczba()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// GIVEN
		Class<?> okno2 = okno.getClass();
		Field komentarz = okno2.getDeclaredField("poleKomentarz");
		komentarz.setAccessible(true);
		JLabel poleKomentarz = (JLabel) komentarz.get(okno);
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		// WHEN
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("sto").setParametrB("1500")
					.setZakres("2").setGraph(graphImage).build();
		} catch (ExceptionInInitializerError e) {
		}
		String komentarzText = poleKomentarz.getText();
		// THEN
		assertEquals("Podano niepoprawne dane.\n a musi należeć do liczb rzeczywistych\n ", komentarzText);
	}

	@DisplayName(value = "Ustawianie komentarzy w JFrame kiedy B nie jest liczbą")
	@Test
	void testSetKomentarzKiedyBNieJestLiczba()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// GIVEN
		Class<?> okno2 = okno.getClass();
		Field komentarz = okno2.getDeclaredField("poleKomentarz");
		komentarz.setAccessible(true);
		JLabel poleKomentarz = (JLabel) komentarz.get(okno);
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		// WHEN
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("10").setParametrB("sto").setZakres("2")
					.setGraph(graphImage).build();
		} catch (ExceptionInInitializerError e) {
		}
		String komentarzText = poleKomentarz.getText();
		// THEN
		assertEquals("Podano niepoprawne dane.\n b musi należeć do liczb rzeczywistych\n ", komentarzText);
	}

	@DisplayName(value = "Ustawianie komentarzy w JFrame kiedy Zakres nie jest liczbą")
	@Test
	void testSetKomentarzKiedyZakresNieJestLiczba()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// GIVEN
		Class<?> okno2 = okno.getClass();
		Field komentarz = okno2.getDeclaredField("poleKomentarz");
		komentarz.setAccessible(true);
		JLabel poleKomentarz = (JLabel) komentarz.get(okno);
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		// WHEN
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("10").setParametrB("1500")
					.setZakres("sto").setGraph(graphImage).build();
		} catch (ExceptionInInitializerError e) {
		}
		String komentarzText = poleKomentarz.getText();
		// THEN
		assertEquals("Podano niepoprawne dane.\n U+03C6 musi należeć do liczb rzeczywistych ", komentarzText);
	}

	@DisplayName(value = "testComponentHidden")
	@Test
	void testComponentHidden() {
		// Given
		try {
			okno.componentHidden(s);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wyjątek w nienadpisywanej metodzie interfejsu");
		}
		// When
		// Then
		assertTrue(true);
	}

	@DisplayName(value = "testComponentMoved")
	@Test
	void testComponentMoved() {
		// Given
		try {
			okno.componentMoved(s);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wyjątek w nienadpisywanej metodzie interfejsu");
		}
		// When
		// Then
		assertTrue(true);
	}

	@Disabled
	@Test
	void testComponentResized() {
		// GIVEN
		// WHEN
		// THEN
		fail("Not yet implemented"); // TODO test skalowania się okienka
	}

	@DisplayName(value = "testComponentShown")
	@Test
	void testComponentShown() {
		// Given
		try {
			okno.componentShown(s);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wyjątek w nienadpisywanej metodzie interfejsu");
		}
		// When
		// Then
		assertTrue(true);
	}

	@DisplayName(value = "Wciśnięcie przycisku Rysuj w JFrame")
	@Disabled
	@Test
	void testActionPerformedRysuj()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// GIVEN
		Class<?> okno2 = okno.getClass();
		Field rysuj = okno2.getDeclaredField("przyciskRysuj");

		rysuj.setAccessible(true);

		JButton przyciskRysuj = (JButton) rysuj.get(okno);
		// WHEN
		okno.actionPerformed(new ActionEvent(przyciskRysuj, ActionEvent.ACTION_PERFORMED, "Test"));
		// THEN
		fail("Not yet implemented"); // TODO test wciskania przycisku w okienku
	}

	@DisplayName(value = "setParametr*() sprawdzanie czy wyrzuci błąd")
	@Test
	void setParametr() {
		// GIVEN
//		AccessException thrownParametrA = null;
//		AccessException thrownParametrB = null;
//		AccessException thrownParametrZakres = null;
//		AccessException thrownParametrJednostkaZakres = null;
//		AccessException thrownParametrKomentarz = null;
//		String[] a = { "" };
//		String b = "";
		String[] thrownParametr = new String[5];
		// WHEN
		try {
			Window.setParametrAText("TEST A");
		} catch (AccessException e) {
			thrownParametr[0] = e.getMessage().toString();
		}
		try {
			Window.setParametrBText("TEST B");
		} catch (AccessException e) {
			thrownParametr[1] = e.getMessage().toString();
		}
		try {
			Window.setZakresText("TEST ZAKRES");
		} catch (AccessException e) {
			thrownParametr[2] = e.getMessage().toString();
		}
		try {
			Window.setJednostkaZakresuText("TEST JEDNOSTKAZAKRESUTEXT");
		} catch (AccessException e) {
			thrownParametr[3] = e.getMessage().toString();
		}
		try {
			Window.setKomentarz("TEST KOMENTARZ");
		} catch (AccessException e) {
			thrownParametr[4] = e.getMessage().toString();
		}
		// THEN
		assertAll("Sprawdzanie czy inne klasy mają dostęp do ustawiania opisów parametrów w interfejs.Window",
				() -> assertEquals("Tylko klasa figury.Figury ma dostęp do tej metody", thrownParametr[0],
						"Błąd przy parametrzeA"),
				() -> assertEquals("Tylko klasa figury.Figury ma dostęp do tej metody", thrownParametr[1],
						"Błąd przy parametrzeB"),
				() -> assertEquals("Tylko klasa figury.Figury ma dostęp do tej metody", thrownParametr[2],
						"Błąd przy parametrzeZakres"),
				() -> assertEquals("Tylko klasa figury.Figury ma dostęp do tej metody", thrownParametr[3],
						"Błąd przy parametrzeJednostkaZakres"),
				() -> assertEquals("Tylko klasa figury.Figury ma dostęp do tej metody", thrownParametr[4],
						"Błąd przy parametrzeKomentarz"));

	}

}
