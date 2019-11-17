package figury;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;

/**
 * Interface wzorca projektowego Budowniczy. Należy go zaimplementować kiedy
 * chcemy utworzyć nową figurę. 
 * W implementowanej funkcji należy zmienić Object na nazwę klasy wewnętrznej
 * którą tworzy do stworzenia budowniczego, natomiast przy metodzie build należy
 * zmienić Object na nazwę klasy obiekt której zwracamy. 
 * 
 * Uwaga: Należy ustawić każdy parametr aby funkcja mogła się policzyć!
 * 
 * 
 * @author 7Adrian
 *
 */
interface Builder {
	BigDecimal parametrA = null;
	BigDecimal parametrB = null;
	BigDecimal zakres = null;
	BufferedImage graph = null;

	/**
	 * Ustawia parametr A funkcji
	 * 
	 * @param parametrA - parametr A funkcji
	 * @return - Wewnętrzna klasa Budowniczego
	 */
	public Object setParametrA(BigDecimal parametrA);

	/**
	 * Ustawia parametr B funkcji
	 * 
	 * @param parametrB - parametr B funkcji
	 * @return - Wewnętrzna klasa Budowniczego
	 */
	public Object setParametrB(BigDecimal parametrB);

	/**
	 * Ustawia zakres funkcji
	 * 
	 * @param zakres - zakres rysowania funkcji
	 * @return - Wewnętrzna klasa Budowniczego
	 */
	public Object setZakres(BigDecimal zakres);

	/**
	 * Ustawia obrazek w którym ma zostać narysowana figura
	 * 
	 * @param graph - Obrazek w którym ma zostać rysowana figura
	 * @return - Wewnętrzna klasa Budowniczego
	 */
	public Object setGraph(BufferedImage graph);

	/**
	 * Tworzy obiekt klasy którą chcemy utworzy na podstawie parametrów które
	 * wcześniej wpisaliśmy
	 * 
	 * @return - Obiekt tworzonej klasy
	 * @throws Exception informacja z błędami które zostały napotkane podczas
	 *                   tworzenia
	 */
	public Object build() throws Exception;

	/**
	 * Sprawdza wpisane dane czy można utworzyć z nich funkcję i generuje komentarz.
	 * 
	 * @return - Numery indeksów komentarzy które się mają wyświetlić.
	 */
	int[] sprawdzParametry();

}
