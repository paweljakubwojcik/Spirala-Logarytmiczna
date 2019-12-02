package wykres;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AntiAliazingTest {

	@DisplayName(value = "Test utworzenia obiektu AntiAliazing")
	@Test
	void test() {
		// GIVEN
		// WHEN
		new AntiAliazing();
		// THEN
		assertTrue(true);
	}

}
