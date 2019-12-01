package figury;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName(value = "Testy parametryzowane spirali")
class SpiralaLogarytmicznaTestJUnit5 {
	BufferedImage graph;
	int width = 760;
	int height = 400;
	int imageType = BufferedImage.TYPE_INT_ARGB;

	@BeforeAll
	public static void setUpBeforeClass() {
		BufferedImage graf = new BufferedImage(760, 400, BufferedImage.TYPE_INT_ARGB);
		try {

			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("0.5").setParametrB("0.1")
					.setZakres("30").setGraph(graf).build();

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
			"0.5, 0.0001, 0.75", "0.5, 0.0001, -0.75", "10, 0.00001, 100000000", "10, 1, 100000000",
			"10E403, 10.123, -1000.5" })

	@DisplayName(value = "Testy powstawania spirali Jednowątkowe")
	void testRysowaniaJendowatkowe(String a, String b, String z) {
//		SpiralaLogarytmiczna krzywa = null;
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA(a).setParametrB(b).setZakres(z)
					.setGraph(graph).setThreads(1).build();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		BufferedImage spirala = new BufferedImage(width, height, imageType);
		URL url = getClass().getResource("/figury/images/Wykres[" + b + "," + z + "].png");

//		try {
//			ImageIO.write(krzywa.getImage(), "png", new File("src/test/java/figury/Wykres[" + b + "," + z + "].png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		try {
			spirala = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(0.0, porownajObrazki(spirala, graph) * 100, 0.5, "Spirala A=" + a + ", B=" + b + ", zakres=" + z);
	}

	@ParameterizedTest(name = "A= {0} + B= {1} + C= {2}")
	@CsvSource({ "0.5, 0.1, 30", "0.5, 0.1, 2", "0.5, 0.001, 200", "0.5, 0.001, 2000", "0.5, 0.0000001, 2000",
			"0.5, 0.3, 70", "0.5, 0, 200", "0.5, 1.1, 200.82", "0.5, 1.1, 201.49", "0.5, 1.1, 201.82",
			"0.5, 1.1, -6.82", "0.5, 1.5, 201.23", "0.5, 1.15, 201.70", "0.5, 1, 200", "0.5, 2.5, 201",
			"0.5, 5, 200.24", "0.5, 7.5, 200.50", "0.5, 10, 200.75", "0.5, -1.1, 6.82", "0.5, -1.1, -6.82",
			"0.5, 0.0001, 0.75", "0.5, 0.0001, -0.75", "10, 0.00001, 100000000", "10, 1, 100000000",
			"10E403, 10.123, -1000.5" })
	@DisplayName(value = "Testy powstawania spirali Wielowątkowe")
	void testRysowaniaWielowatkowe(String a, String b, String z) {
		int numberOfThreads = Runtime.getRuntime().availableProcessors();
		if (numberOfThreads == 1)
			numberOfThreads = 2;
		try {
			new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA(a).setParametrB(b).setZakres(z)
					.setGraph(graph).setThreads(numberOfThreads).build();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		BufferedImage spirala = new BufferedImage(width, height, imageType);
		URL url = getClass().getResource("/figury/images/Wykres[" + b + "," + z + "].png");

		try {
			spirala = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(0.0, porownajObrazki(spirala, graph) * 100, 0.5, "Spirala A=" + a + ", B=" + b + ", zakres=" + z);
	}

	double porownajObrazki(BufferedImage spirala, BufferedImage graph) {
		int spiralaColor;
		int graphColor;
		double iloscPixeli = 0;
		double iloscBlednychPixeli = 0;

		if (spirala.getWidth() == graph.getWidth() && spirala.getHeight() == graph.getHeight()) {
			for (int x = 0; x < spirala.getWidth(); x++) {
				for (int y = 0; y < spirala.getHeight(); y++) {
					spiralaColor = spirala.getRGB(x, y);
					graphColor = graph.getRGB(x, y);
					// -16777216 czarny; -9013642 osiowy; -16776961 wykresowy; -1 biały?
					if (graphColor != 0 && spiralaColor != -1 && spiralaColor != -16777216) {
						iloscPixeli++;
					}
					if (spiralaColor != graphColor && spiralaColor != -1 && spiralaColor != -16777216) {
						iloscBlednychPixeli++;
					}
				}
			}
		} else {
			return 1;
		}
		System.out.println("Stosunek błędnych pixeli= " + iloscBlednychPixeli * 100 / iloscPixeli + "%");
		if (iloscBlednychPixeli / iloscPixeli >= 0.01) {
			return iloscBlednychPixeli / iloscPixeli;
		}
		return 0;
	}

}
