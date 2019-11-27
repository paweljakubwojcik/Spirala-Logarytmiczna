package wykres;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class KrzywaTest {

	@Test
	void testKrzywa() {
		// GIVEN
		// WHEN
		new Krzywa();
		// THEN
		assertTrue(true);
	}

	@Test
	void testGetKrzywa() {
		// GIVEN
		ArrayList<Point> punkty = new ArrayList<Point>();
		punkty.add(new Point(0, 0));
		punkty.add(new Point(5, 5));
		BufferedImage wykres = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		// WHEN
		BufferedImage krzywa = Krzywa.getKrzywa(new Point(10, 10), punkty);
		wykres.setRGB(0, 0, Color.BLUE.getRGB());
		wykres.setRGB(5, 5, Color.BLUE.getRGB());
		// THEN
		assertEquals(0.0, porownajObrazki(wykres, krzywa));
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
					if (spiralaColor != 0) {
						iloscPixeli++;
					}
					if (graphColor == -16777216)
						graphColor = 0;
					if (spiralaColor != graphColor) {
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
