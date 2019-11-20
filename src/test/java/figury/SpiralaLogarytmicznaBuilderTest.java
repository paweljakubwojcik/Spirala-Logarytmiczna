package figury;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName(value = "Testy implementacji Buildera w Spirali Logarytmicznej")
class SpiralaLogarytmicznaBuilderTest {

	@DisplayName(value = "Sprawdzanie czy zwrócony obiekt jest z klasy SpiralaLogarytmiczna")
	@Test
	void testTworzenieSpirali() {
		// Given
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		SpiralaLogarytmiczna spirala = null;
		// When
		try {
			spirala = new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("10")
					.setParametrB("1500").setZakres("2").setGraph(graphImage).build();
		} catch (Exception e) {
		}
		// Then
		assertEquals(SpiralaLogarytmiczna.class, spirala.getClass());
	}

	@DisplayName(value = "Sprawdzanie błędu braku ustawienia parametru A")
	@Test
	void testTworzenieSpiraliANull() {
		// Given
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		ExceptionInInitializerError thrown = null;
		// When
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrB("1500")
					.setZakres("2").setGraph(graphImage).build();
		} catch (ExceptionInInitializerError e) {
			thrown = e;
		}
		// Then
		assertEquals("Parametr A nie został ustawiony", thrown.getMessage());
	}

	@DisplayName(value = "Sprawdzanie błędu braku ustawienia parametru B")
	@Test
	void testTworzenieSpiraliBNull() {
		// Given
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		ExceptionInInitializerError thrown = null;
		// When
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("10")
					.setZakres("2").setGraph(graphImage).build();
		} catch (ExceptionInInitializerError e) {
			thrown = e;
		}
		// Then
		assertEquals("Parametr B nie został ustawiony", thrown.getMessage());
	}

	@DisplayName(value = "Sprawdzanie błędu braku ustawienia zakresu")
	@Test
	void testTworzenieSpiraliZakresNull() {
		// Given
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		ExceptionInInitializerError thrown = null;
		// When
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("10")
					.setParametrB("1500").setGraph(graphImage).build();
		} catch (ExceptionInInitializerError e) {
			thrown = e;
		}
		// Then
		assertEquals("Parametr zakres nie został ustawiony", thrown.getMessage());
	}

	@DisplayName(value = "Sprawdzanie błędu braku ustawienia grafu")
	@Test
	void testTworzenieSpiraliGraphNull() {
		// Given
		ExceptionInInitializerError thrown = null;
		// When
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("10")
					.setParametrB("1500").setZakres("2").build();
		} catch (ExceptionInInitializerError e) {
			thrown = e;
		}
		// Then
		assertEquals("Parametr graph nie został ustawiony", thrown.getMessage());
	}

	@DisplayName(value = "Sprawdzanie błędu złych parametrów")
	@Test
	void testTworzenieSpiraliParametrNull() {
		// Given
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		ExceptionInInitializerError thrown = null;
		// When
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("-10")
					.setParametrB("1500").setZakres("2").setGraph(graphImage).build();
		} catch (ExceptionInInitializerError e) {
			thrown = e;
		}
		// Then
		assertEquals("Błąd wprowadzonych parametrów", thrown.getMessage());
	}

}
