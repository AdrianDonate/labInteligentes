import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TableroFrame extends JFrame {
	Algoritmo algoritmo;
	ArrayList<Casilla> arbolInterior;
	ArrayList<Nodo> camino;
	Frontera frontera;
	int xsize;
	int ysize;
	
	public TableroFrame(Algoritmo algoritmo, ArrayList<Nodo> camino, int xsize, int ysize, Frontera frontera, ArrayList<Casilla> arbolInterior) {
		GraphicsDemo tablero = new GraphicsDemo(algoritmo.mapa, camino, xsize, ysize, frontera, arbolInterior);
		this.algoritmo = algoritmo;
		this.camino = camino;
		this.xsize = xsize;
		this.ysize = ysize;
		this.setSize(xsize, ysize);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(tablero);
		this.frontera = frontera;
		this.arbolInterior = arbolInterior;
		this.setVisible(true);
		
        try
        {
            BufferedImage image = new BufferedImage(getWidth()-40, getHeight()-40, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = image.createGraphics();
            tablero.paint(graphics2D);
            String path;
            if(camino == null) {
				path = "puzzle_loop" + algoritmo.mapa.length + "x" + algoritmo.mapa[0].length + ".png";
			} else {
				path = "solution_" + algoritmo.mapa.length + "x" + algoritmo.mapa[0].length + "_"+algoritmo.estrategia+"_20.png";
			}
            ImageIO.write(image,"PNG", new File(path));
        }
        catch(Exception exception)
        {
        	System.out.println("Error al exportar la imagen");

        }
	}
	
}
