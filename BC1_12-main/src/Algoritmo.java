import java.util.ArrayList;

public class Algoritmo {
	private ArrayList<Casilla> camino = new ArrayList<Casilla>();
	private ArrayList<Casilla> porVisitar = new ArrayList<Casilla>();
	private int filas;
	private int columnas;
	public String estrategia;
	public Casilla[][] mapa;
	public Problema problema;
	public ArrayList<Casilla> arbolInterior = new ArrayList<Casilla>();
	public Frontera frontera = new Frontera();

	public Algoritmo(int filas, int columnas) {
		this.filas = filas;
		this.columnas = columnas;
		mapa = new Casilla[filas][columnas];
	}

	public ArrayList<Casilla> getArbolInterior() {
		return arbolInterior;
	}

	public void setArbolInterior(ArrayList<Casilla> arbolInterior) {
		this.arbolInterior = arbolInterior;
	}

	public Frontera getFrontera() {
		return frontera;
	}

	public void setFrontera(Frontera frontera) {
		this.frontera = frontera;
	}
	
	public void setMapa(Casilla[][] mapa) {
		this.mapa = mapa;
	}

	public void setProblema(Problema problema){
		this.problema = problema;
	}

	public Problema getProblema(){
		return this.problema;
	}

	public void setEstrategia(String estrategia){
		this.estrategia = estrategia;
	}
	
	public String getEstrategia() {
		return estrategia;
	}

	public Problema generarProblema(){
		int[] posInicial = {0,0};
		int[] posFinal = {0,0};
		while (posInicial[0] == posFinal[0] && posInicial[1] == posFinal[1]) {
				posInicial = posicionAleatoria(filas, columnas);
				posFinal = posicionAleatoria(filas, columnas);
		}
		Problema problema = new Problema(mapa[posInicial[0]][posInicial[1]], mapa[posFinal[0]][posFinal[1]]);
		return problema;
	}
	
	public void ejecutarAlgoritmo() {
		inicializarMapa(mapa); // asigna posición a las casillas y numero de casillas
		//Generar un problema aleatorio
		setProblema(generarProblema());
		Casilla obj = problema.getObjetivo();
		Casilla ini = problema.getEstadoInicial();

		//Preparar el mapa
		mapa[obj.getFila()][obj.getColumna()].setVisitado(true);
		porVisitar.remove(mapa[obj.getFila()][obj.getColumna()]);

		//Algoritmo de generacion del laberinto
		algoritmo(ini.getFila(), ini.getColumna(), mapa);
	}
	
	public ArrayList<Nodo> ejecutarBusqueda(String estrategia) {
		ArrayList<Nodo> solucion = new ArrayList<Nodo>();
		solucion = busqueda(1000000, estrategia);
		//System.out.println(solucion);
		return solucion;
	}
	
	//ALGORITMO DE GENERACI�N DE LABERINTOS
	public void algoritmo(int fila, int columna, Casilla[][] mapa) {
		while (!porVisitar.isEmpty()) {

			if (mapa[fila][columna].getVisitado()) { // Camino encontrado o estoy en bucle
				if (comprobarBucle(mapa[fila][columna])) { // Existe bucle, hay que eliminarlo
					eliminarBucle(mapa[fila][columna], mapa);
					if (camino.size() > 1) {
						paredes(mapa[fila][columna], mapa);
					}
					
					mapa[fila][columna].setVisitado(false);

				} else { 
					// Camino encontrado, hay que vaciar el camino para empezar otro nuevo

					camino.add(mapa[fila][columna]);
					//System.out.println((camino.size()));
					porVisitar.remove(mapa[fila][columna]);

					paredes(mapa[fila][columna], mapa);

					if (porVisitar.size() != 0) {
						vaciarCamino();
						int n = nuevaPosicion();
						fila = porVisitar.get(n).getFila();
						columna = porVisitar.get(n).getColumna();
						//System.out.println("Un camino encontrado");

					} else {
						//System.out.println("Tengo el ultimo camino");
						vaciarCamino();
						//System.out.println("LABERINTO TERMINADO");

					}

				}
			} else { // no he encontrado camino
				mapa[fila][columna].setVisitado(true);

				//Evitar añadir dos veces el mismo elemento más de una vez
				if (!camino.contains(mapa[fila][columna])) {
					camino.add(mapa[fila][columna]);
				}

				int r = (int) Math.floor(Math.random() * 4);
				switch (r) {
					case 0: // Ir Norte
						if (comprobarDireccion(fila - 1, columna, mapa) == true) {
							porVisitar.remove(mapa[fila][columna]);
							asignacionTerreno(mapa[fila][columna]);
							
							if (camino.size() > 1) {
								paredes(mapa[fila][columna], mapa);
							} else {
								mapa[fila][columna].setNeighbors(0, true);
							}
							fila--;
						} else {
							mapa[fila][columna].setVisitado(false);
						}
						break;

					case 1: // Ir Este
						if (comprobarDireccion(fila, columna + 1, mapa) == true) {
							porVisitar.remove(mapa[fila][columna]);
							asignacionTerreno(mapa[fila][columna]);
							
							if (camino.size() > 1) {
								paredes(mapa[fila][columna], mapa);
							} else {
								mapa[fila][columna].setNeighbors(1, true);
							}

							columna++;
						} else {
							mapa[fila][columna].setVisitado(false);
						}
						break;

					case 2: // Ir Sur
						if (comprobarDireccion(fila + 1, columna, mapa) == true) {
							porVisitar.remove(mapa[fila][columna]);
							asignacionTerreno(mapa[fila][columna]);
							
							if (camino.size() > 1) {
								paredes(mapa[fila][columna], mapa);
							} else {
								mapa[fila][columna].setNeighbors(2, true);
							}
							fila++;
						} else {
							mapa[fila][columna].setVisitado(false);
						}
						break;

					case 3: // Ir Oeste
						if (comprobarDireccion(fila, columna - 1, mapa) == true) {
							porVisitar.remove(mapa[fila][columna]);
							asignacionTerreno(mapa[fila][columna]);
							
							if (camino.size() > 1) {
								paredes(mapa[fila][columna], mapa);
							} else {
								mapa[fila][columna].setNeighbors(3, true);
							}
							columna--;
						} else {
							mapa[fila][columna].setVisitado(false);
						}
						break;
				}
			}
		}
		// Última iteración
		camino.add(mapa[fila][columna]);
		//System.out.println((camino.size()));
		paredes(mapa[fila][columna], mapa);
	}

	public void asignacionTerreno(Casilla c) {
		int r = (int) Math.floor(Math.random() * 4);
		c.setValue(r);
	}
	
	
	public void inicializarMapa(Casilla[][] mapa) {
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa[0].length; j++) {
				mapa[i][j] = new Casilla(i, j);
				porVisitar.add(mapa[i][j]);
			}
		}
	}

	public static int[] posicionAleatoria(int N, int M) {
		int[] v = new int[2];
		N = (int) Math.floor(Math.random() * N);
		M = (int) Math.floor(Math.random() * M);
		v[0] = N;
		v[1] = M;
		return v;
	}

	public boolean comprobarBucle(Casilla c) {

		boolean formaBucle = false;
		int[] posActual = new int[2]; // Posicion 0 -> Fila | Posicion 1 -> Columna
		int[] posAux = new int[2]; // Posicion 0 -> Fila | Posicion 1 -> Columna

		posActual[0] = c.getFila();
		posActual[1] = c.getColumna();

		for (int i = 0; i < camino.size() - 1; i++) {

			posAux[0] = camino.get(i).getFila();
			posAux[1] = camino.get(i).getColumna();

			if (posActual[0] == posAux[0] && posActual[1] == posAux[1]) {
				formaBucle = true;
				break; // Bucle descubierto
			}

		}
		return formaBucle;
	}

	public void eliminarBucle(Casilla c, Casilla[][] mapa) {
		boolean salir = false;
		while (!salir) {
			Casilla aux = camino.get(camino.size() - 1); // Obtener la última casilla almacenada
			if (c.getFila() == aux.getFila() && c.getColumna() == aux.getColumna()) {

				// Reiniciar vecinos
				mapa[aux.getFila()][aux.getColumna()].reiniciarNeighbors();

				salir = true;

			} else {
				mapa[aux.getFila()][aux.getColumna()].setVisitado(false);

				// Reiniciar vecinos
				mapa[aux.getFila()][aux.getColumna()].reiniciarNeighbors();

				porVisitar.add(mapa[aux.getFila()][aux.getColumna()]);
				camino.remove(camino.size() - 1);
			}
		}
	}

	public boolean comprobarDireccion(int f, int c, Casilla[][] tab) {
		if (f >= 0 && f <= tab.length - 1 && c >= 0 && c <= tab[0].length - 1) {
			return true;
		} else {
			return false;
		}
	}

	public void print(Casilla mapa[][]) {
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa[0].length; j++) {
				System.out.print(mapa[i][j].getVisitado() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void vaciarCamino() {
		camino.removeAll(camino);
	}

	public void paredes(Casilla c, Casilla[][] mapa) {
		Casilla aux = camino.get(camino.size() - 2); // Obtengo la penultima casilla, de la que viene la actual

		// Hay que saber de donde viene la casilla actual para adaptar las paredes

		// comprobar que viene del Norte
		if (comprobarDireccion(c.getFila() - 1, c.getColumna(), mapa)) {
			if (aux.getFila() == c.getFila() - 1 && aux.getColumna() == c.getColumna()) {
				mapa[aux.getFila()][aux.getColumna()].setNeighbors(2, true);
				c.setNeighbors(0, true);
			}
		}
		// Comprobar que viene del Este
		if (comprobarDireccion(c.getFila(), c.getColumna() + 1, mapa)) {
			if (aux.getFila() == c.getFila() && aux.getColumna() == c.getColumna() + 1) {
				mapa[aux.getFila()][aux.getColumna()].setNeighbors(3, true);
				c.setNeighbors(1, true);
			}
		}
		// Comprobar que viene del Sur
		if (comprobarDireccion(c.getFila() + 1, c.getColumna(), mapa)) {
			if (aux.getFila() == c.getFila() + 1 && aux.getColumna() == c.getColumna()) {
				mapa[aux.getFila()][aux.getColumna()].setNeighbors(0, true);
				c.setNeighbors(2, true);
			}
		}
		// Comprobar que viene del Oeste
		if (comprobarDireccion(c.getFila(), c.getColumna() - 1, mapa)) {
			if (aux.getFila() == c.getFila() && aux.getColumna() == c.getColumna() -1) {
				mapa[aux.getFila()][aux.getColumna()].setNeighbors(1, true);
				c.setNeighbors(3, true);
			}
		}

	}

	public int nuevaPosicion() {
		int r = (int) Math.floor(Math.random() * porVisitar.size());
		return r;
	}
	

	//----------------------- ALGORITMO DE BUSQUEDA -----------------------

	public ArrayList<Nodo> busqueda(int profundidad, String estrategia){
		ArrayList<Casilla> visitado = new ArrayList<Casilla>();
		boolean solucion = false;

		//nodo inicial
		Nodo nodo = new Nodo();
		nodo.setPadre(); //Padre = null
		nodo.setEstado(problema.getEstadoInicial());
		nodo.setCosto(0);
		nodo.setProfundidad(0);
		nodo.setAccion(null);
		nodo.setHeuristica(heuristica(problema, nodo.getEstado()));
		nodo.setValor(calcularValor(estrategia, nodo));

		//Insertamos el nodo en la frontera
		frontera.push(nodo);

		//Varibales auxiliares
		int filaObj = problema.getObjetivo().getFila();
		int colObj =  problema.getObjetivo().getColumna();

		while(!frontera.frontera.isEmpty() && !solucion){
			nodo = frontera.pop(); //Sacamos el primer nodo de la frontera

			if(filaObj == nodo.getEstado().getFila() && colObj == nodo.getEstado().getColumna()){ //Hemos llegado a la solucion
				solucion = true;

			} else if (!visitado.contains(nodo.getEstado()) && nodo.getProfundidad() < profundidad){
				visitado.add(nodo.getEstado()); //Casilla visitada

				ArrayList<Nodo> nodosHijos = expandirNodo(problema, nodo, estrategia); //Hijos del nodo actual
				//System.out.println(nodosHijos);
				for (int i = 0; i < nodosHijos.size(); i++){
					frontera.push(nodosHijos.get(i)); //A�adir nuevo hijo a la frontera
					arbolInterior.add(nodosHijos.get(i).getEstado()); //A�adimos las casillas que componen el arbol interior
					frontera.ordenar(); //Ordenamos la frontera
				}
			}
		}

		if(solucion == true){
			return camino(nodo);
		} else {
			return null;
		}
	}



	public ArrayList<Nodo> camino(Nodo nodo){
		ArrayList<Nodo> camino = new ArrayList<Nodo>();
		while(nodo.getProfundidad()!= 0){
			camino.add(nodo);
			nodo = nodo.getPadre();
		}
		camino.add(nodo);
		return camino;
	}

	public ArrayList<Nodo> expandirNodo(Problema problema, Nodo nodo, String estrategia){
		ArrayList<Nodo> listaNodos = new ArrayList<Nodo>();
		ArrayList<Sucesor> listaSucesores;
		listaSucesores = sucesores(nodo.getEstado());
		for(int i = 0; i<listaSucesores.size();i++){
			Nodo hijo = new Nodo();
			hijo.setEstado(listaSucesores.get(i).getEstado());
			hijo.setPadre(nodo);
			hijo.setAccion(listaSucesores.get(i).getAccion());
			hijo.setProfundidad(nodo.getProfundidad()+1);
			hijo.setCosto(nodo.getCosto() + listaSucesores.get(i).getEstado().getValue() + 1);
			hijo.setHeuristica(heuristica(problema, listaSucesores.get(i).getEstado()));
			hijo.setValor(calcularValor(estrategia, hijo));
			listaNodos.add(hijo);
		}
		return listaNodos;
	}

	public int heuristica (Problema problema, Casilla c){
		return (Math.abs(c.getFila()-problema.getObjetivo().getFila()) + Math.abs(c.getColumna() - problema.getObjetivo().getColumna()));
	}

	public double calcularValor(String estrategia, Nodo nodo){
		double valor = 0;
		switch (estrategia){
			case "BREADTH":
				valor = nodo.getProfundidad();
				break;
				
			case "DEPTH":
				valor = 1/((double)nodo.getProfundidad() + 1);
				break;
				
			case "UNIFORM":
				valor = nodo.getCosto();
				break;
				
			case "GREEDY":
				valor = (double)nodo.getHeuristica();
				break;
				
			case "A":
				valor = (double)nodo.getCosto() + (double)nodo.getHeuristica();
				break;
		}
		
		return valor;
	}

	public ArrayList<Sucesor> sucesores(Casilla c){
		ArrayList<Sucesor> sucesores = new ArrayList<Sucesor>();

		if(c.getNeighbor(0) == true){
			Sucesor norte = new Sucesor("N", mapa[c.getFila()-1][c.getColumna()], 1);
			sucesores.add(norte);
		}
		if(c.getNeighbor(1) == true){
			Sucesor este = new Sucesor("E", mapa[c.getFila()][c.getColumna()+1], 1);
			sucesores.add(este);
		}
		if(c.getNeighbor(2) == true){
			Sucesor sur = new Sucesor("S", mapa[c.getFila()+1][c.getColumna()], 1);
			sucesores.add(sur);
		}
		if(c.getNeighbor(3) == true){
			Sucesor oeste = new Sucesor("O", mapa[c.getFila()][c.getColumna()-1], 1);
			sucesores.add(oeste);
		}
		//System.out.println(sucesores);
		return sucesores;
	}

	// ------------------- FIN ALGORITMO DE BUSQUEDA

}

