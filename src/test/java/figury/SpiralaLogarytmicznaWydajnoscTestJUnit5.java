package figury;

import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.time.Duration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName(value = "Testy wydajnościowe spirali")
class SpiralaLogarytmicznaWydajnoscTestJUnit5 {
	BufferedImage graph;
	int width = 760;
	int height = 400;
	int imageType = BufferedImage.TYPE_INT_ARGB;

	@BeforeAll
	public static void setUpBeforeClass() {
		BufferedImage graf = new BufferedImage(760, 400, BufferedImage.TYPE_INT_ARGB);
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA(new BigDecimal(0.5))
					.setParametrB(new BigDecimal(0.1)).setZakres(new BigDecimal(30)).setGraph(graf).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeEach
	void setUp() throws Exception {
		graph = new BufferedImage(width, height, imageType);
	}

	@ParameterizedTest(name = "A= {0} + B= {1} + C= {2}")
	@CsvSource({ "0.5, 0.1, 30", "0.5, 0.1, 2", "0.5, 0.001, 200", "0.5, 0.001, 2000", "0.5, 0.0000001, 2000",
			"0.5, 0.3, 70", "0.5, 0, 200", "0.5, 1.1, 200.82", "0.5, 1.1, 201.49", "0.5, 1.1, 201.82",
			"0.5, 1.1, -6.82", "0.5, 1.5, 201.23", "0.5, 1.15, 201.70", "0.5, 1, 200", "0.5, 2.5, 201",
			"0.5, 5, 200.24", "0.5, 7.5, 200.50", "0.5, 10, 200.75", "0.5, -1.1, 6.82", "0.5, -1.1, -6.82",
			"0.5, 0.0001, 0.75", "0.5, 0.0001, -0.75" })
	@DisplayName(value = "Testy wydajności spirali")
	void testRysowania(String a, String b, String z) {
		assertTimeout(Duration.ofMillis(1000), () -> {
			try {
				new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA(new BigDecimal(a))
						.setParametrB(new BigDecimal(b)).setZakres(new BigDecimal(z)).setGraph(graph).build();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		});

	}
}