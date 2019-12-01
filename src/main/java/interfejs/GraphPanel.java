package interfejs;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GraphPanel extends JPanel {

	public Image image;
	public Image imageScaled;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(imageScaled, 0, 0, null);
		super.paintComponents(g);
	}

	@Override
	public void setSize(int x, int y) {
		super.setSize(x, y);
		if (image != null) {
			imageScaled = image.getScaledInstance(x, y, BufferedImage.SCALE_SMOOTH);
//			Graphics2D g2d = (Graphics2D) this.getGraphics();
//			g2d.drawImage(imageScaled, 0, 0, null);
		}

	}

	/**
	 * Funkcja dzieki której resize komponentu powoduje automatyczny resize grafiki
	 * Po ustawieniu g nalezy wywołać ffunkkcje repaint()
	 * 
	 * @param g - image to paint on component
	 */
	public void setImage(BufferedImage g) {
		this.image = g;
		this.imageScaled = g;
	}
}
