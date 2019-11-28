package interfejs;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName(value = "Test startu aplikacji")
class MainTest {

	@DisplayName(value = "Sprawdza czy tworzenie okienka nie wywala błędu")
	@Test
	void testMain() {
		// Given
		// When
		try {
			new Main();
			Main.main(null);
		} catch (Exception e) {
			fail("Błąd podczas tworzenia okienka!: \n");
			e.printStackTrace();
		}
		// Then
		assertTrue(true, "Okienko utworzyło się bez błędu");
	}

}
