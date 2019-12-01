package interfejs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.rmi.AccessException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import figury.SpiralaLogarytmiczna;

/**
 * Tworzy okienko, obsługuje przyciski, skaluje okienko.
 * 
 * @author pafeu
 * @since 1.0
 */
public class Window extends JFrame implements ActionListener, ComponentListener {
	private static final long serialVersionUID = 1L;

	int sizeWindowX = 800;
	int sizeWindowY = 600;

	private Dimension poleSize = new Dimension(50, 30); // 100 , 30
	private Dimension buttonSize = new Dimension(100, 30);
	private Dimension minimumSize = new Dimension(640, 300);

	private static JPanel graph;
	private static JLabel napisNazwaProgramu, napisParametry, napisParametrA, napisParametrB, napisZakres,
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

	private BufferedImage graphImage;

	public Window() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(NAZWAPROGRAMUTEXT);
		setLocationRelativeTo(null);
		setLocation(0, 0);
		setSize(sizeWindowX, sizeWindowY);
		setLayout(null);
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
		przyciskCzysc.addActionListener(this);
		add(przyciskCzysc);

		przyciskRysuj = new JButton(RYSUJTEXT);
		przyciskRysuj.addActionListener(this);
		add(przyciskRysuj);

		przyciskPelnyEkran = new JButton(PELNYEKRANTEXT);
		przyciskPelnyEkran.addActionListener(this);
		add(przyciskPelnyEkran);

		poleKomentarz = new JLabel();
		add(poleKomentarz);
		poleKomentarz.setBorder(BorderFactory.createLineBorder(Color.black));//

		przeskalujOkienko();// w tym miejscu ustawia size wszystkich elementďż˝w
		addComponentListener(this); // musi byďż˝ za metodďż˝ przeskalujOkienko();

		setVisible(true);

		//////// Spanie jest po to ĹĽeby nie znikaĹ‚a szybko narysowana spirala ////////
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		/////////////////////////////////////////////////////////////////////////////////////////

		graphImage = new BufferedImage(graph.getWidth(), graph.getHeight(), BufferedImage.TYPE_INT_ARGB);

		// Rysowanie obrazka startowego

		long start = System.currentTimeMillis();
		draw(new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA("10").setParametrB("0.1")
				.setZakres("40").setGraph(graphImage).setSSAA(1).build().getImage());
		System.out.println("Wykonywanie Spirali trwało: " + (System.currentTimeMillis() - start) / 1000.0 + " sekund");

	}

	private void przeskalujOkienko() {

		sizeWindowX = this.getWidth();
		sizeWindowY = this.getHeight();

		////////////////////////////////////////
		napisNazwaProgramu.setHorizontalAlignment(SwingConstants.CENTER);
		napisNazwaProgramu.setSize(sizeWindowX, sizeWindowY / 20);
		napisNazwaProgramu.setLocation(0, 0);

		graph.setSize(sizeWindowX - sizeWindowX / 20, sizeWindowY * 4 / 6);
		graph.setLocation(sizeWindowX / 80, napisNazwaProgramu.getHeight());
		graph.setAlignmentX(100);

		int odstepY = sizeWindowY / 60;
		int locationY = graph.getY() + graph.getHeight() + odstepY;
		int odstep = (graph.getWidth() - 640) / 6; // (szerokosc graph - suma długości elementów)/ilosc elementów

		// 70+20+50+20+50+50+50+30+buttony
		// w kolejnosci od lewej do prawej
		napisParametry.setSize(70, poleSize.height);
		napisParametry.setLocation(graph.getX(), locationY);

		napisParametrA.setSize(20, poleSize.height);
		napisParametrA.setLocation(napisParametry.getLocation().x + napisParametry.getSize().width + odstep, locationY);

		poleParametrA.setSize(poleSize);
		poleParametrA.setLocation(napisParametrA.getLocation().x + napisParametrA.getSize().width, locationY);

		napisParametrB.setSize(20, poleSize.height);
		napisParametrB.setLocation(poleParametrA.getLocation().x + poleParametrA.getSize().width + odstep, locationY);

		poleParametrB.setSize(poleSize);
		poleParametrB.setLocation(napisParametrB.getX() + napisParametrB.getWidth(), locationY);

		napisZakres.setSize(50, poleSize.height);
		napisZakres.setLocation(poleParametrB.getLocation().x + poleParametrB.getSize().width + odstep, locationY);

		poleZakres.setSize(poleSize);
		poleZakres.setLocation(napisZakres.getX() + napisZakres.getWidth(), locationY);

		napisZakresJednostka.setSize(30, poleSize.height);
		napisZakresJednostka.setLocation(poleZakres.getLocation().x + poleZakres.getSize().width, locationY);

		// jbUTTON
		przyciskRysuj.setSize(buttonSize);
		przyciskRysuj.setLocation(napisZakresJednostka.getX() + napisZakresJednostka.getWidth() + odstep, locationY);

		przyciskCzysc.setSize(buttonSize);
		przyciskCzysc.setLocation(przyciskRysuj.getX() + przyciskRysuj.getWidth() + odstep, locationY);

		przyciskPelnyEkran.setSize(buttonSize);
		przyciskPelnyEkran.setLocation(przyciskCzysc.getX() + przyciskCzysc.getWidth() + odstep, locationY);

		poleKomentarz.setLocation(graph.getX(), locationY + odstepY + poleSize.height);
		poleKomentarz.setSize(graph.getWidth(),
				sizeWindowY - napisNazwaProgramu.getHeight() - graph.getHeight() - poleSize.height - 4 * odstepY - 30);

	}

	private void setFullScreen() {

	}

	private static void draw(BufferedImage BI) {
		Graphics2D g2d = (Graphics2D) graph.getGraphics();
		g2d.drawImage(BI, 0, 0, null);
	}

	public static void setParametrAText(String parametrAText) throws AccessException {
		if (napisParametrA != null) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			if (stackTraceElements[2].getClassName().equals("figury.Figury"))
				napisParametrA.setText(parametrAText);
			else
				throw new AccessException("Tylko klasa figury.Figury ma dostęp do tej metody");
		}
	}

	public static void setParametrBText(String parametrBText) throws AccessException {
		if (napisParametrB != null) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			if (stackTraceElements[2].getClassName().equals("figury.Figury"))
				napisParametrB.setText(parametrBText);
			else
				throw new AccessException("Tylko klasa figury.Figury ma dostęp do tej metody");
		}
	}

	public static void setZakresText(String zakresText) throws AccessException {
		if (napisZakres != null) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			if (stackTraceElements[2].getClassName().equals("figury.Figury"))
				napisZakres.setText(zakresText);
			else
				throw new AccessException("Tylko klasa figury.Figury ma dostęp do tej metody");
		}
	}

	public static void setJednostkaZakresuText(String jednostkaZakresuText) throws AccessException {
		if (napisZakresJednostka != null) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			if (stackTraceElements[2].getClassName().equals("figury.Figury"))
				napisZakresJednostka.setText(jednostkaZakresuText);
			else
				throw new AccessException("Tylko klasa figury.Figury ma dostęp do tej metody");
		}
	}

	public static void setKomentarz(String komentarz) throws AccessException {
		if (poleKomentarz != null) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			if (stackTraceElements[2].getClassName().equals("figury.Figury"))
				poleKomentarz.setText(komentarz);
			else
				throw new AccessException("Tylko klasa figury.Figury ma dostęp do tej metody");
		}
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

		JButton obj = (JButton) e.getSource();
		if (obj == przyciskRysuj) {
			graphImage = new BufferedImage(graph.getWidth(), graph.getHeight(), BufferedImage.TYPE_INT_ARGB);
			BufferedImage nic = new BufferedImage(graph.getWidth(), graph.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) graph.getGraphics();
			g2d.drawImage(nic, 0, 0, null);
			try {
				long start = System.currentTimeMillis();
				draw(new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA(poleParametrA.getText())
						.setParametrB(poleParametrB.getText()).setZakres(poleZakres.getText()).setGraph(graphImage)
						.setSSAA(2).build().getImage());
				System.out.println(
						"Wykonywanie Spirali trwało: " + (System.currentTimeMillis() - start) / 1000.0 + " sekund");
			} catch (Exception exc) {
				exc.printStackTrace();
				System.err.println(exc.getMessage());
			}
			System.gc();
		}

		if (obj == przyciskCzysc) {
			System.out.println("Czyscze");
		}

		if (obj == przyciskPelnyEkran) {
			System.out.println("rOBIE PELNY EKRAN");
			setFullScreen();
		}

	}

}
