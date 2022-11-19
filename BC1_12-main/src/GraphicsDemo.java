import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

public class GraphicsDemo extends JPanel{
	private Casilla[][] mapa;
	private ArrayList<Nodo> solucion;
	private Frontera frontera;
	private ArrayList<Casilla> arbolInterior;
	private int xsize;
	private int ysize;
	
	public GraphicsDemo(Casilla[][] mapa, ArrayList<Nodo> solucion, int xsize, int ysize, Frontera frontera, ArrayList<Casilla> arbolInterior) {
		this.mapa = mapa;
		this.solucion = solucion;
		this.xsize = xsize;
		this.ysize = ysize;
		this.frontera = frontera;
		this.arbolInterior = arbolInterior;
	}
	

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		int pinicial = 20;
		int porcentual = Math.max(mapa.length, mapa[0].length);
		int longitudLinea = (int)(xsize/porcentual)-(100/mapa.length);

		//PINTAMOS LAS CASILLAS DE COLOR DEPENDIENDO DEL VALOR(TERRENO)
		for(int i = 0; i < mapa.length; i++) {
			for(int j = 0; j < mapa[0].length; j++) {
				switch(mapa[i][j].getValue()) {
					case 1:
						g2D.setColor(new Color(245, 222, 179));
						g2D.fillRect((pinicial+j*longitudLinea), (pinicial+i*longitudLinea), longitudLinea, longitudLinea);
						break;
					case 2:
						g2D.setColor(new Color(152, 251, 152));
						g2D.fillRect((pinicial+j*longitudLinea), (pinicial+i*longitudLinea), longitudLinea, longitudLinea);
						break;
					case 3:
						g2D.setColor(new Color(135, 206, 250));
						g2D.fillRect((pinicial+j*longitudLinea), (pinicial+i*longitudLinea), longitudLinea, longitudLinea);
						break;
				}
			}
		}


		//PINTAMOS EL ARBOL INTERIOR
		if(arbolInterior != null){
			for(int i = 0; i < arbolInterior.size(); i++) {
				g2D.setColor(Color.GREEN);
				g2D.fillRect((pinicial+(arbolInterior.get(i).getColumna()*longitudLinea)),
						(pinicial+(arbolInterior.get(i).getFila()*longitudLinea)), longitudLinea, longitudLinea);
			}
		}

		//PINTAMOS LA FRONTERA
		if(frontera != null){
			for(int i = 0; i < frontera.frontera.size(); i++) {
				g2D.setColor(Color.BLUE);
				g2D.fillRect((pinicial+(frontera.frontera.get(i).getEstado().getColumna()*longitudLinea)),
						(pinicial+(frontera.frontera.get(i).getEstado().getFila()*longitudLinea)), longitudLinea, longitudLinea);
			}
		}

		//PINTAMOS LA SOLUCION
		if(solucion != null){
			for(int i = 0; i < solucion.size(); i++) {
				g2D.setColor(Color.RED);
				g2D.fillRect((pinicial+(solucion.get(i).getEstado().getColumna()*longitudLinea)),
						(pinicial+(solucion.get(i).getEstado().getFila()*longitudLinea)), longitudLinea, longitudLinea);
			}
		}
		
		//PINTAMOS MUROS
		for(int i = 0; i < mapa.length; i++) {
			for(int j = 0; j < mapa[0].length; j++) {

				if(!mapa[i][j].getNeighbor(0)) {
					g2D.setColor(Color.BLACK);
					g2D.setStroke(new BasicStroke(2));
					g2D.drawLine(pinicial+j*longitudLinea, pinicial+i*longitudLinea, pinicial+(j+1)*longitudLinea, pinicial+i*longitudLinea);
				}
				if(!mapa[i][j].getNeighbor(1)) {
					g2D.setColor(Color.BLACK);
					g2D.setStroke(new BasicStroke(2));
					g2D.drawLine(pinicial+(j+1)*longitudLinea, pinicial+i*longitudLinea, pinicial+(j+1)*longitudLinea, pinicial+(i+1)*longitudLinea);
				}
				if(!mapa[i][j].getNeighbor(2)) {
					g2D.setColor(Color.BLACK);
					g2D.setStroke(new BasicStroke(2));
					g2D.drawLine(pinicial+j*longitudLinea, pinicial+(i+1)*longitudLinea, pinicial+(j+1)*longitudLinea, pinicial+(i+1)*longitudLinea);
				}
				if(!mapa[i][j].getNeighbor(3)) {
					g2D.setColor(Color.BLACK);
					g2D.setStroke(new BasicStroke(2));
					g2D.drawLine(pinicial+j*longitudLinea, pinicial+i*longitudLinea, pinicial+j*longitudLinea, pinicial+(i+1)*longitudLinea);
				}
				
				
			}	 
		}



	}
	
	
}
