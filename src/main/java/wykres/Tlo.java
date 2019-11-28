package wykres;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;

/**
 * Generuje opisy i osie do Spirali
 *
 */
public class Tlo {

	private static BufferedImage tlo ;
	private BufferedImage osie;
	private BufferedImage opisyOsi;
	private static Point graphSize;

	static BufferedImage getTlo(Point graphSize1, BigDecimal zakres) {
		
		graphSize = graphSize1;
		tlo = new BufferedImage(graphSize.x, graphSize.y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)tlo.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0, graphSize.x, graphSize.x);
		
		drawOsie(g);
		return tlo;
	}

	private void scalObrazki() {

	}

	private static void drawOsie(Graphics2D g) {
		g.setColor(Color.white);
		g.drawLine(0, graphSize.y/2, graphSize.x, graphSize.y/2);
		g.drawLine(graphSize.x/2, 0, graphSize.x/2, graphSize.y);
		int podzialka =10;
		for(int i=0;i<graphSize.x;i+=podzialka) {
			g.drawLine(i,(graphSize.y/2)+3, i, (graphSize.y/2)-3);
		}
		for(int i=0;i<graphSize.y;i+=podzialka) {
			g.drawLine((graphSize.x/2)+3, i, (graphSize.x/2)-3,i);
		}

	}

	private void drawOpisyOsi() {

	}

}
