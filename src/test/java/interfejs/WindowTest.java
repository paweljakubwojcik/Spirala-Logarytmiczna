package interfejs;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.event.ComponentEvent;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

	@DisplayName(value = "Sprawdzenie poprawności napisów utworzonego się okienka")
	@Test
	final void testWindow() {
		// TODO Posprawdzać pozostałe pola w sposób dostępu do prywatnych pól
		// Given
		// When
		// THEN
		assertAll("Sprawdza opisy utworzonego okienka", () -> assertEquals("Spirala Logarytmiczna", okno.getTitle()),
				() -> assertEquals("Spirala Logarytmiczna", Window.NAZWAPROGRAMUTEXT),
				() -> assertTrue(okno.isVisible()));
		okno.setVisible(false);
	}

	@DisplayName(value = "Ustawianie parametru A w JFrame")
	@Disabled
	@Test
	final void testSetParametrAText() {
		fail("Not yet implemented"); // TODO test setParametrA
	}

	@DisplayName(value = "Ustawianie parametru B w JFrame")
	@Disabled
	@Test
	final void testSetParametrBText() {
		fail("Not yet implemented"); // TODO test setParametrB
	}

	@DisplayName(value = "Ustawianie zakresu w JFrame")
	@Disabled
	@Test
	final void testSetZakresText() {
		fail("Not yet implemented"); // TODO test setZakres
	}

	@DisplayName(value = "Ustawianie jednostki zakresu w JFrame")
	@Disabled
	@Test
	final void testSetJednostkaZakresuText() {
		fail("Not yet implemented"); // TODO test setJednostkaZakresu
	}

	@DisplayName(value = "Ustawianie komentarzy w JFrame")
	@Disabled
	@Test
	final void testSetKomentarz() {
		fail("Not yet implemented"); // TODO test setKomentarz
	}

	@DisplayName(value = "testComponentHidden")
	@Test
	final void testComponentHidden() {
		// Given
		okno.componentHidden(s);
		// When
		// Then
		assertTrue(true);
	}

	@DisplayName(value = "testComponentMoved")
	@Test
	final void testComponentMoved() {
		// Given
		okno.componentMoved(s);
		// When
		// Then
		assertTrue(true);
	}

	@Disabled
	@Test
	final void testComponentResized() {
		fail("Not yet implemented"); // TODO test skalowania się okienka
	}

	@DisplayName(value = "testComponentShown")
	@Test
	final void testComponentShown() {
		// Given
		okno.componentShown(s);
		// When
		// Then
		assertTrue(true);
	}

	@DisplayName(value = "Wciśnięcie przycisku w JFrame")
	@Disabled
	@Test
	final void testActionPerformed() {
		fail("Not yet implemented"); // TODO test wciskania przycisku w okienku
	}

}
