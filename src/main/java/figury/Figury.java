package figury;

import java.awt.Point;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JPanel;

import interfejs.Window;
import wykres.Wykres;

public abstract class Figury {
	private static final String[] komentarz = { "Podano niepoprawne dane.\n", "a musi być większe od zera\n",
			"a musi należeć do liczb rzeczywistych\n", "b musi należeć do liczb rzeczywistych\n",
			"U+03C6 musi należeć do liczb rzeczywistych" };
	String[] opisy = { " ", " ", " ", " " };
	BigDecimal parametrA;
	BigDecimal parametrB;
	BigDecimal zakres;
	ArrayList<Point> punkty = new ArrayList<Point>();
	JPanel graph;

	public Figury(String[] opisy, BigDecimal parametrA, BigDecimal parametrB, BigDecimal zakres, JPanel graph) {
		this.opisy = opisy;
		this.parametrA = parametrA;
		this.parametrB = parametrB;
		this.zakres = zakres;
		this.graph = graph;
		setOpis();
		wyznaczPunkty();
		System.out.println(punkty.size());
		// System.out.println(punkty);
		new Wykres(graph, punkty, zakres);
	}

	abstract void wyznaczPunkty();

	static void setKomentarz(int[] numer) {
		String wysylanyKomentarz = "";
		if (numer != null) {
			for (int i = 0; i < numer.length; i++) {
				wysylanyKomentarz += komentarz[i];
			}
		}
		try {
			Window.setKomentarz(wysylanyKomentarz);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setOpis() {
		try {
			Window.setParametrAText(opisy[0]);
			Window.setParametrBText(opisy[1]);
			Window.setZakresText(opisy[2]);
			Window.setJednostkaZakresuText(opisy[3]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	boolean isGood() {
		for (int i = 1; i < punkty.size(); i++) {
			if (Math.sqrt(Math.pow(punkty.get(i).getX() - punkty.get(i - 1).getX(), 2)
					+ Math.pow(punkty.get(i).getY() - punkty.get(i - 1).getY(), 2)) > Math.sqrt(2)) {
				return true;
			}
		}
		return false;
	}
}
