package figury;

import java.math.BigDecimal;

import javax.swing.JPanel;

interface Builder {
	BigDecimal parametrA = null;
	BigDecimal parametrB = null;
	BigDecimal zakres = null;
	JPanel graph = null;

	public Object setParametrA(BigDecimal parametrA);

	public Object setParametrB(BigDecimal parametrB);

	public Object setZakres(BigDecimal zakres);

	public Object setGraph(JPanel graph);

	public Object build() throws Exception;

	int[] sprawdzParametry();

}
