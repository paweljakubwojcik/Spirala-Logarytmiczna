package interfejs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Window extends JFrame implements ActionListener, ComponentListener {
	private static final long serialVersionUID = 1L;

	int sizeWindowX = 800;
	int sizeWindowY = 600;

	private Dimension poleSize = new Dimension(); // 100 , 30
	private Dimension buttonSize = new Dimension();
	private Dimension minimumSize = new Dimension(640, 300);

	private static JPanel graph;
	private JLabel napisNazwaProgramu, napisParametry, napisParametrA, napisParametrB, napisZakres,
			napisZakresJednostka, poleKomentarz;

	private JTextField poleParametrA, poleParametrB, poleZakres;
	private JButton przyciskRysuj, przyciskCzysc, przyciskPelnyEkran;

	public static final String NAZWAPROGRAMUTEXT = "Spirala Logarytmiczna";
	public static final String PARAMETRYTEXT = "XXX";
	public static final String RYSUJTEXT = "RYSUJ";
	public static final String CZYSCTEXT = "CZYŚĆ";
	public static final String PELNYEKRANTEXT = "PEŁNY EKRAN";

	private static String zakresText, jednostkaZakresuText, parametrAText, parametrBText, komentarz;
	private static BigDecimal parametrA, parametrB, zakres;

	Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(NAZWAPROGRAMUTEXT);
		setLocationRelativeTo(null);
		setLocation(0, 0);
		setSize(sizeWindowX, sizeWindowY);
		setLayout(null);
		setVisible(true);
		setMinimumSize(minimumSize);

		graph = new JPanel();
		add(graph);
		graph.setBorder(BorderFactory.createLineBorder(Color.black));

		// JText
		poleParametrA = new JTextField();
		add(poleParametrA);

		poleParametrB = new JTextField();
		add(poleParametrB);

		poleZakres = new JTextField();
		add(poleZakres);

		// JLabel
		napisNazwaProgramu = new JLabel(NAZWAPROGRAMUTEXT);
		add(napisNazwaProgramu);
		// napisNazwaProgramu.setBorder(BorderFactory.createLineBorder(Color.black));//
		// tests

		napisParametry = new JLabel("Parametry:");
		add(napisParametry);
		// napisParametry.setBorder(BorderFactory.createLineBorder(Color.black));//
		// testing

		napisParametrA = new JLabel("a=");
		add(napisParametrA);
		// napisParametrA.setBorder(BorderFactory.createLineBorder(Color.black));//
		// testing

		napisParametrB = new JLabel("b=");
		add(napisParametrB);
		// napisParametrB.setBorder(BorderFactory.createLineBorder(Color.black));//
		// testing

		napisZakres = new JLabel("zakres=");
		add(napisZakres);
		// napisZakres.setBorder(BorderFactory.createLineBorder(Color.black));// testing

		napisZakresJednostka = new JLabel("rad");
		add(napisZakresJednostka);
		// napisZakresJednostka.setBorder(BorderFactory.createLineBorder(Color.black));//
		// testing

		// JButtons
		przyciskCzysc = new JButton(CZYSCTEXT);
		add(przyciskCzysc);

		przyciskRysuj = new JButton(RYSUJTEXT);
		add(przyciskRysuj);

		przyciskPelnyEkran = new JButton(PELNYEKRANTEXT);
		add(przyciskPelnyEkran);

		poleKomentarz = new JLabel();
		add(poleKomentarz);
		poleKomentarz.setBorder(BorderFactory.createLineBorder(Color.black));//

		przeskalujOkienko();// w tym miejscu ustawia size wszystkich element�w
		addComponentListener(this); // musi by� za metod� przeskalujOkienko();

	}

	private void przeskalujOkienko() {

		sizeWindowX = this.getWidth();
		sizeWindowY = this.getHeight();

		poleSize.width = 50;
		poleSize.height = 30;

		buttonSize.width = 100;
		buttonSize.height = 30;

		////////////////////////////////////////
		napisNazwaProgramu.setHorizontalAlignment(SwingConstants.CENTER);
		napisNazwaProgramu.setSize(sizeWindowX, sizeWindowY / 20);
		napisNazwaProgramu.setLocation(0, 0);

		graph.setSize(sizeWindowX - sizeWindowX / 20, sizeWindowY * 4 / 6);
		graph.setLocation(sizeWindowX / 80, napisNazwaProgramu.getHeight());

		int locationY = graph.getY() + graph.getHeight() + sizeWindowY / 60;
		int odstep = (graph.getWidth() - 640) / 6; // (szerokosc graph - suma d�ugo�ci element�w)/ilosc element�w
													// 70+20+50+20+50+50+50+30+buttony
		// w kolejnosci od lewej do prawej
		napisParametry.setSize(70, 30);
		napisParametry.setLocation(graph.getX(), locationY);

		napisParametrA.setSize(20, 30);
		napisParametrA.setLocation(napisParametry.getLocation().x + napisParametry.getSize().width + odstep, locationY);

		poleParametrA.setSize(poleSize);
		poleParametrA.setLocation(napisParametrA.getLocation().x + napisParametrA.getSize().width, locationY);

		napisParametrB.setSize(20, 30);
		napisParametrB.setLocation(poleParametrA.getLocation().x + poleParametrA.getSize().width + odstep, locationY);

		poleParametrB.setSize(poleSize);
		poleParametrB.setLocation(napisParametrB.getX() + napisParametrB.getWidth(), locationY);

		napisZakres.setSize(50, 30);
		napisZakres.setLocation(poleParametrB.getLocation().x + poleParametrB.getSize().width + odstep, locationY);

		poleZakres.setSize(poleSize);
		poleZakres.setLocation(napisZakres.getX() + napisZakres.getWidth(), locationY);

		napisZakresJednostka.setSize(30, 30);
		napisZakresJednostka.setLocation(poleZakres.getLocation().x + poleZakres.getSize().width, locationY);

		// jbUTTON
		przyciskRysuj.setSize(buttonSize);
		przyciskRysuj.setLocation(napisZakresJednostka.getX() + napisZakresJednostka.getWidth() + odstep, locationY);

		przyciskCzysc.setSize(buttonSize);
		przyciskCzysc.setLocation(przyciskRysuj.getX() + przyciskRysuj.getWidth() + odstep, locationY);

		przyciskPelnyEkran.setSize(buttonSize);
		przyciskPelnyEkran.setLocation(przyciskCzysc.getX() + przyciskCzysc.getWidth() + odstep, locationY);

		poleKomentarz.setLocation(graph.getX(), locationY + 40);
		poleKomentarz.setSize(graph.getWidth(), 50);

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
		if (e.getSource() == this) {
			// System.out.println("resize");
			przeskalujOkienko();
		}

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
