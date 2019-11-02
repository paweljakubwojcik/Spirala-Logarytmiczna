package interfejs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window extends JFrame implements ActionListener, ComponentListener {
	private static final long serialVersionUID = 1L;

	int sizeWindowX;
	int sizeWindowY;

	private static JPanel graph;
	private JLabel napisNazwaProgramu, napisParametry, napisParametrA, napisParametrB, napisZakres,
			napisZakresJednostka, poleKomentarz;

	private JTextField poleParametrA, poleParametrB, poleZakres;
	private JButton przyciskRysuj, przyciskCzysc, przyciskPelnyEkran;

	public static final String NAZWAPROGRAMUTEXT = "XXX";
	public static final String PARAMETRYTEXT = "XXX";
	public static final String RYSUJTEXT = "XXX";
	public static final String CZYSCTEXT = "XXX";
	public static final String PELNYEKRANTEXT = "XXX";

	private static String zakresText, jednostkaZakresuText, parametrAText, parametrBText, komentarz;
	private static BigDecimal parametrA, parametrB, zakres;

	Window() {

	}

	private void przeskalujOkienko() {

	}

	private void setFullScreen() {

	}

	private static void setParametrAText(String parametrAText) {

	}

	private static void setParametrBText(String parametrBText) {

	}

	private static void setZakresext(String zakresText) {

	}

	private static void setJednostkaZakresuText(String jednostkaZakresuText) {

	}

	private static void setKomentarz(String komentarz) {

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
