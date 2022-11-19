public class Casilla {
	private boolean visitado;
	private int fila, columna, value;
	private boolean []neighbors = new boolean[4];

	public Casilla(int fila, int columna) {
		//Atributos
		this.fila = fila;
		this.columna = columna;
		value = 0;

		neighbors[0] = false; //N
		neighbors[1] = false; //E
		neighbors[2] = false; //S
		neighbors[3] = false; //O
		//Controlar si ha sido visitado
		visitado = false;
	}

	//Constructor que se usa para leer casillas del JSON
	public Casilla(int fila, int columna, boolean vecinoSup, boolean vecinoDcha, boolean vecinoInf, boolean vecinoIzq, int value) {
		//Atributos
		this.fila = fila;
		this.columna = columna;
		this.value = value;
		//Paredes
		neighbors[0] = vecinoSup; //N
		neighbors[1] = vecinoDcha; //E
		neighbors[2] = vecinoInf; //S
		neighbors[3] = vecinoIzq; //O

	}


	// GETTER

	public int getFila() {
		return fila;
	}

	public int getColumna() {
		return columna;
	}

	public boolean getNeighbor(int id_mov){
		return neighbors[id_mov];
	}
	
	public boolean[] getNeighbors(){
		return neighbors;
	}
	

	public boolean getVisitado() {
		return visitado;
	}
	
	public int getValue() {
		return value;
	}

	// SETTER

	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}

	public void setNeighbors(int id_mov, boolean neighbor){
		this.neighbors[id_mov] = neighbor;
	}

	public void reiniciarNeighbors(){
		this.neighbors[0] = false;
		this.neighbors[1] = false;
		this.neighbors[2] = false;
		this.neighbors[3] = false;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
