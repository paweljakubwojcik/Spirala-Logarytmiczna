package interfejs;

/**
 * Aplikacja rysująca Spiralę Loagarytmiczną. Posiada interfejs graficzny oparty
 * na Swing. Wykorzystuje pewne sztuczki optymalizacyjne podczas rysowania
 * wykresu dzięki czemu może rysować spirale o bardzo dużych parametrach które
 * popularna strona internetowa Wolfram Alpha nie jest w stanie narysować, albo
 * rysuje je błędnie.
 * 
 * @author 7Adrian
 * @author pafeu
 * @since 1.0
 */
public class Main {

	/**
	 * Funkcja startowa
	 * 
	 * @param args - Parametry startowe aplikacji (Obecnie brak)
	 */
	public static void main(String[] args) {
		new Window();
	}

}
