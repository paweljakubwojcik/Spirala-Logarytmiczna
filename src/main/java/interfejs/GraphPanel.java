package interfejs;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GraphPanel extends JPanel {

	public Image image;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, 0, 0, null);
		super.paintComponents(g);
	}

	@Override
	public void setSize(int x, int y) {
		super.setSize(x, y);
		if (image != null)
			image = image.getScaledInstance(x, y, BufferedImage.SCALE_REPLICATE);

	}

	/**
	 * Funkcja dzieki której resize komponentu powoduje automatyczny resize grafiki
	 * Po ustawieniu g nalezy wywołać ffunkkcje repaint()
	 * 
	 * @param g - image to paint on component
	 */
	public void setImage(BufferedImage g) {
		this.image = g;
	}
}
