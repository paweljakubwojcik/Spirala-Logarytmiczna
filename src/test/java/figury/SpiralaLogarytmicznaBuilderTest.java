package figury;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;

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
		spirala = new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("10").setParametrB("1500")
				.setZakres("2").setGraph(graphImage).build();
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
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrB("1500").setZakres("2")
					.setGraph(graphImage).build();
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
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("10").setZakres("2")
					.setGraph(graphImage).build();
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
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("10").setParametrB("1500")
					.setGraph(graphImage).build();
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
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("10").setParametrB("1500")
					.setZakres("2").build();
		} catch (ExceptionInInitializerError e) {
			thrown = e;
		}
		// Then
		assertEquals("Parametr graph nie został ustawiony", thrown.getMessage());
	}

	@DisplayName(value = "Sprawdzanie błędu złych parametrów (A ujemne)")
	@Test
	void testTworzenieSpiraliParametrAUjemny() {
		// Given
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		ExceptionInInitializerError thrown = null;
		// When
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("-10").setParametrB("1500")
					.setZakres("2").setGraph(graphImage).build();

		} catch (ExceptionInInitializerError e) {
			thrown = e;
		}
		// Then
		assertEquals("Błąd wprowadzonych parametrów", thrown.getMessage());
	}

	@DisplayName(value = "Sprawdzanie błędu złych parametrów (A zawierające dwie '.')")
	@Test
	void testTworzenieSpiraliParametrAPodwojnaKropka() {
		// Given
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		Exception thrown = null;
		// When
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("..10").setParametrB("1500")
					.setZakres("2").setGraph(graphImage).build();

		} catch (Exception e) {
			thrown = e;
		}
		// Then
		assertEquals("Błąd wprowadzonych parametrów", thrown.getMessage());
	}

	@DisplayName(value = "Sprawdzanie błędu złych parametrów (B zawierające dwie '.')")
	@Test
	void testTworzenieSpiraliParametrBPodwojnaKropka() {
		// Given
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		Exception thrown = null;
		// When
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("10").setParametrB("..1").setZakres("2")
					.setGraph(graphImage).build();

		} catch (Exception e) {
			thrown = e;
		}
		// Then
		assertEquals("Błąd wprowadzonych parametrów", thrown.getMessage());
	}

	@DisplayName(value = "Sprawdzanie błędu złych parametrów (Zakres zawierający dwie '.')")
	@Test
	void testTworzenieSpiraliParametrZakresPodwojnaKropka() {
		// Given
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		Exception thrown = null;
		// When
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("10").setParametrB("4500")
					.setZakres("..2").setGraph(graphImage).build();

		} catch (Exception e) {
			thrown = e;
		}
		// Then
		assertEquals("Błąd wprowadzonych parametrów", thrown.getMessage());
	}

	@DisplayName(value = "Sprawdzanie błędu złych parametrów (A zaczynające się od ',')")
	@Test
	void testTworzenieSpiraliParametrAPrzecinek() {
		// Given
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		SpiralaLogarytmiczna spirala = null;
		// When
		spirala = new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA(",1").setParametrB("4500")
				.setZakres("2").setGraph(graphImage).build();
		// Then
		assertEquals(SpiralaLogarytmiczna.class, spirala.getClass());
	}

	@DisplayName(value = "Sprawdzanie błędu złych parametrów (B zaczynające się od ',')")
	@Test
	void testTworzenieSpiraliParametrBPrzecinek() {
		// Given
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		SpiralaLogarytmiczna spirala = null;
		// When
		spirala = new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("10").setParametrB(",4500")
				.setZakres("2").setGraph(graphImage).build();
		// Then
		assertEquals(SpiralaLogarytmiczna.class, spirala.getClass());
	}

	@DisplayName(value = "Sprawdzanie błędu złych parametrów (Zakres zaczynający się od ',')")
	@Test
	void testTworzenieSpiraliParametrZakresPrzecinek() {
		// Given
		BufferedImage graphImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		SpiralaLogarytmiczna spirala = null;
		// When
		spirala = new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("10").setParametrB("4500")
				.setZakres(",2").setGraph(graphImage).build();
		// Then
		assertEquals(SpiralaLogarytmiczna.class, spirala.getClass());
	}

}
