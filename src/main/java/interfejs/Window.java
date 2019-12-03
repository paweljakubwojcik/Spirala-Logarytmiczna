package interfejs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.rmi.AccessException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

	private boolean pelnyekran = false;

	private int sizeWindowX = 800;
	private int sizeWindowY = 600;

	private Dimension poleSize = new Dimension(50, 30);
	private Dimension buttonSize = new Dimension(80, 30);
	private Dimension minimumSize = new Dimension(750, 550); // 640 ,550

	private static GraphPanel graph;
	private static JLabel napisNazwaProgramu, napisParametry, napisParametrA, napisParametrB, napisZakres,
			napisZakresJednostka, poleKomentarz;

	private JTextField poleParametrA, poleParametrB, poleZakres;
	private JButton przyciskRysuj, przyciskCzysc, przyciskPelnyEkran;

	public static final String NAZWAPROGRAMUTEXT = "Spirala Logarytmiczna     r=ae^(b\u03C6)";
	public static final String PARAMETRYTEXT = "XXX";
	public static final String RYSUJTEXT = "RYSUJ";
	public static final String CZYSCTEXT = "CZYŚĆ";
	public static final String PELNYEKRANTEXT = "PEŁNY EKRAN";
	public static final String DefaultA = "0.5";
	public static final String DefaultB = "0.1";
	public static final String DefaultZakres = "30";

	private BufferedImage graphImage;

	private Font defaultFont;

	SpiralaLogarytmiczna spirala;

	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(NAZWAPROGRAMUTEXT);
		setLocationRelativeTo(null);
		setLocation(0, 0);
		setSize(sizeWindowX, sizeWindowY);
		setLayout(null);
		setMinimumSize(minimumSize);

		graph = new GraphPanel();
		add(graph);

		// JText
		poleParametrA = new JTextField(DefaultA);
		add(poleParametrA);

		poleParametrB = new JTextField(DefaultB);
		add(poleParametrB);

		poleZakres = new JTextField(DefaultZakres);
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

		napisParametrA = new JLabel();
		napisParametrA.setText("a=");
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
		poleKomentarz.setBorder(BorderFactory.createLineBorder(Color.black));
		poleKomentarz.setVerticalTextPosition(SwingConstants.BOTTOM);

		przeskalujOkienko();// w tym miejscu ustawia size wszystkich elementďż˝w
		addComponentListener(this); // musi byďż˝ za metodďż˝ przeskalujOkienko();

		graphImage = new BufferedImage(graph.getWidth(), graph.getHeight(), BufferedImage.TYPE_INT_ARGB);

		// Rysowanie obrazka startowego
		stworzSpirale();
		przeskalujOkienko();
		setVisible(true);
	}

	/**
	 * skaluje wszystkie elementy okienka
	 */
	private void przeskalujOkienko() {

		sizeWindowX = this.getWidth() - 16;
		sizeWindowY = this.getHeight();

		defaultFont = new Font("Dialog", Font.BOLD, 12);
		napisNazwaProgramu.setFont(new Font("Dialog", Font.BOLD, 15));

		napisParametrA.setFont(defaultFont);
		napisParametrB.setFont(defaultFont);
		napisZakres.setFont(defaultFont);
		napisZakresJednostka.setFont(defaultFont);
		poleKomentarz.setFont(defaultFont);
		przyciskCzysc.setFont(defaultFont);
		przyciskPelnyEkran.setFont(defaultFont);
		przyciskRysuj.setFont(defaultFont);
		napisParametry.setFont(defaultFont);

		////////////////////////////////////////
		napisNazwaProgramu.setHorizontalAlignment(SwingConstants.CENTER);
		napisNazwaProgramu.setSize(sizeWindowX, getFontMetrics(napisNazwaProgramu.getFont()).getHeight());
		napisNazwaProgramu.setLocation(0, 0);

		int odstepY = sizeWindowY / 240;
//		int locationY = graph.getY() + graph.getHeight() + odstepY; // polozenie Y przyciskow i pol
		int locationY = sizeWindowY - odstepY - napisParametrA.getHeight() - poleSize.height - getInsets().top - 10;

		int margines = sizeWindowX / 60;
		int graphWidth = sizeWindowX - 2 * margines; //
//		int graphHeight = sizeWindowY * 3 / 4;
		int graphY = napisNazwaProgramu.getHeight();
		int graphHeight = -napisNazwaProgramu.getHeight() + locationY - 10;

		// aby rysunek nie wyszedl poza okienko
		if (graphHeight > graphWidth)
			graph.setSize(graphWidth, graphWidth);
		else
			graph.setSize(graphHeight, graphHeight);

		graph.setLocation(margines + (graphWidth - graph.getWidth()) / 2, graphY + 2);

		napisParametry.setSize(getFontMetrics(defaultFont).stringWidth(napisParametry.getText()), poleSize.height);
		napisParametrA.setSize(getFontMetrics(defaultFont).stringWidth(napisParametrA.getText()), poleSize.height);
		poleParametrA.setSize(poleSize);
		napisParametrB.setSize(getFontMetrics(defaultFont).stringWidth(napisParametrB.getText()), poleSize.height);
		poleParametrB.setSize(poleSize);
		napisZakres.setSize(getFontMetrics(defaultFont).stringWidth(napisZakres.getText()), poleSize.height);
		poleZakres.setSize(poleSize);
		napisZakresJednostka.setSize(getFontMetrics(defaultFont).stringWidth(napisZakresJednostka.getText()),
				poleSize.height);
		przyciskRysuj.setSize(buttonSize);
		przyciskCzysc.setSize(buttonSize);
		przyciskPelnyEkran.setSize(170, 30);

		int odstep = (graphWidth - (napisParametry.getWidth() + napisParametrA.getWidth() + poleParametrA.getWidth()
				+ napisParametrB.getWidth() + poleParametrB.getWidth() + napisZakres.getWidth() + poleZakres.getWidth()
				+ napisZakresJednostka.getWidth() + przyciskRysuj.getWidth() + przyciskCzysc.getWidth()
				+ przyciskPelnyEkran.getWidth())) / 6;
		// (szerokosc graph - suma długośc elementów) ilosc odstepow
		// 70+20+50+20+50+50+50+30+buttony
		// w kolejnosci od lewej do prawej

		napisParametry.setLocation(margines, locationY);
		napisParametrA.setLocation(napisParametry.getLocation().x + napisParametry.getSize().width + odstep, locationY);
		poleParametrA.setLocation(napisParametrA.getLocation().x + napisParametrA.getSize().width, locationY);
		napisParametrB.setLocation(poleParametrA.getLocation().x + poleParametrA.getSize().width + odstep, locationY);
		poleParametrB.setLocation(napisParametrB.getX() + napisParametrB.getWidth(), locationY);
		napisZakres.setLocation(poleParametrB.getLocation().x + poleParametrB.getSize().width + odstep, locationY);
		poleZakres.setLocation(napisZakres.getX() + napisZakres.getWidth(), locationY);
		napisZakresJednostka.setLocation(poleZakres.getLocation().x + poleZakres.getSize().width, locationY);

		przyciskRysuj.setLocation(napisZakresJednostka.getX() + napisZakresJednostka.getWidth() + odstep, locationY);
		przyciskCzysc.setLocation(przyciskRysuj.getX() + przyciskRysuj.getWidth() + odstep, locationY);
		przyciskPelnyEkran.setLocation(przyciskCzysc.getX() + przyciskCzysc.getWidth() + odstep, locationY);

		poleKomentarz.setLocation(margines, locationY + odstepY + poleSize.height);
		poleKomentarz.setSize(graphWidth,
//				sizeWindowY - napisNazwaProgramu.getHeight() - graph.getHeight() - poleSize.height - 4 * odstepY - 40);
				poleSize.height);
	}

	/**
	 * Uruchamia i zamyka tryb pełnoekranowy
	 */
	private void setFullScreen() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		GraphicsDevice[] devices = env.getScreenDevices();
		for (GraphicsDevice dev : devices) {
			Rectangle screenLocation = dev.getDefaultConfiguration().getBounds();
			Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
			if (mouseLocation.x >= screenLocation.x && mouseLocation.x <= screenLocation.x + screenLocation.width
					&& mouseLocation.y >= screenLocation.y
					&& mouseLocation.y <= screenLocation.y + screenLocation.height) {
				device = dev;
				break;
			}
		}

		if (!device.isFullScreenSupported() && pelnyekran == false) {
			JOptionPane.showMessageDialog(this, "Twój komputer nie wspiera pełnego ekranu ;/");
			requestFocus();
		} else if (device.isFullScreenSupported() && pelnyekran == false) {
			dispose();
			setUndecorated(true);
			setResizable(true);
			device.setFullScreenWindow(this);
			requestFocus();

			przyciskPelnyEkran.setText("Wyłącz pełny ekran");
			pelnyekran = true;

		} else {
			dispose();
			setUndecorated(false);
			setVisible(true);
			requestFocus();
			przyciskPelnyEkran.setText("PełnyEkran");
			pelnyekran = false;
		}
	}

	private void draw(BufferedImage BI) {
		graph.setImage(BI);
		graph.repaint();
	}

	private void stworzSpirale() {
		graphImage = new BufferedImage(graph.getWidth(), graph.getHeight(), BufferedImage.TYPE_INT_ARGB);
		try {
			long start = System.currentTimeMillis();

			spirala = new SpiralaLogarytmiczna.SpiralaLogarytmicznaBuilder().setParametrA(poleParametrA.getText())
					.setParametrB(poleParametrB.getText()).setZakres(poleZakres.getText()).setGraph(graphImage).build();
			draw(spirala.getImage());

			System.out.println(
					"Wykonywanie Spirali trwało: " + (System.currentTimeMillis() - start) / 1000.0 + " sekund");
		} catch (NumberFormatException e) {
			poleKomentarz.setText("Wprowadzone wartości są zbyt duże");
		} catch (Exception exc) {
			exc.printStackTrace();
			System.err.println(exc.getMessage());
		}
		System.gc();
	}

	/**
	 * 
	 * @param parametrAText
	 * @throws AccessException - tylko klasa Figury ma dostęp
	 */
	public static void setParametrAText(String parametrAText) throws AccessException {

		if (napisParametrA != null) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			if (stackTraceElements[2].getClassName().equals("figury.Figury"))
				napisParametrA.setText(parametrAText);
			else
				throw new AccessException("Tylko klasa figury.Figury ma dostęp do tej metody");
		}
	}

	/**
	 * 
	 * @param parametrBText
	 * @throws AccessException tylko klasa Figury ma dostęp
	 */
	public static void setParametrBText(String parametrBText) throws AccessException {
		if (napisParametrB != null) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			if (stackTraceElements[2].getClassName().equals("figury.Figury"))
				napisParametrB.setText(parametrBText);
			else
				throw new AccessException("Tylko klasa figury.Figury ma dostęp do tej metody");
		}
	}

	/**
	 * 
	 * @param zakresText
	 * @throws AccessException tylko klasa Figury ma dostęp
	 */
	public static void setZakresText(String zakresText) throws AccessException {
		if (napisZakres != null) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			if (stackTraceElements[2].getClassName().equals("figury.Figury"))
				napisZakres.setText(zakresText);
			else
				throw new AccessException("Tylko klasa figury.Figury ma dostęp do tej metody");
		}
	}

	/**
	 * 
	 * @param jednostkaZakresuText
	 * @throws AccessException tylko klasa Figury ma dostęp
	 */
	public static void setJednostkaZakresuText(String jednostkaZakresuText) throws AccessException {
		if (napisZakresJednostka != null) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			if (stackTraceElements[2].getClassName().equals("figury.Figury"))
				napisZakresJednostka.setText(jednostkaZakresuText);
			else
				throw new AccessException("Tylko klasa figury.Figury ma dostęp do tej metody");
		}
	}

	/**
	 * 
	 * @param komentarz - komentarz który ma być ustawiony w polu komentarzy
	 * @throws AccessException - tylko klasa Figury ma dostęp
	 */
	public static void setKomentarz(String komentarz) throws AccessException {
		if (poleKomentarz != null) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			if (stackTraceElements[2].getClassName().equals("figury.Figury")
					|| stackTraceElements[2].getClassName().equals("interfejs.Window"))
				poleKomentarz.setText(komentarz);
			else
				throw new AccessException("Tylko klasa figury.Figury ma dostęp do tej metody");
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentResized(ComponentEvent e) {
		if (e.getSource() == this) {
			przeskalujOkienko();
			graph.repaint();
		}
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton obj = (JButton) e.getSource();

		if (obj == przyciskRysuj) {
			stworzSpirale();
		}

		if (obj == przyciskCzysc) {
			BufferedImage filler = new BufferedImage(graph.getWidth(), graph.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) filler.getGraphics();
			g.setColor(Color.black);
			g.fillRect(0, 0, graph.getWidth(), graph.getHeight());
			graph.setImage(filler);
			graph.repaint();

			poleKomentarz.setText("");
			poleParametrA.setText(DefaultA);
			poleParametrB.setText(DefaultB);
			poleZakres.setText(DefaultZakres);
		}

		if (obj == przyciskPelnyEkran) {
			setFullScreen();
		}
	}

}
