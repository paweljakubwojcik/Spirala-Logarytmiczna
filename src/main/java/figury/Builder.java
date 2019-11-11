package figury;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
//TODO JavaDoc
interface Builder {
	BigDecimal parametrA = null;
	BigDecimal parametrB = null;
	BigDecimal zakres = null;
	BufferedImage graph = null;

	public Object setParametrA(BigDecimal parametrA);

	public Object setParametrB(BigDecimal parametrB);

	public Object setZakres(BigDecimal zakres);

	public Object setGraph(BufferedImage graph);

	public Object build() throws Exception;

	int[] sprawdzParametry();

}
