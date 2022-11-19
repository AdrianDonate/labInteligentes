import java.io.*;
import java.util.*;

import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

public class Principal {

	public static void main(String[]args) throws FileNotFoundException{
		menu();
	}

	public static void menu() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		int opcion = 0;
		Scanner teclado = new Scanner(System.in);
		System.out.println("BIENVENIDO, SELECCIONE UNA DE LAS OPCIONES: ");
		System.out.println("1) Generar Laberinto aleatorio, resolverlo y exportarlo en JSON y PNG.");
		System.out.println("2) Generar Laberinto a partir de JSON contenido en la carpeta raiz del proyecto.");
		opcion = teclado.nextInt();
		Algoritmo algoritmo;
		ArrayList<Nodo> solucion = null;
		String estrategia;

		switch(opcion) {
			//Generar laberinto aleatorio y resolverlo
			case 1:
				System.out.println("Indique numero de filas:");
				int filas = teclado.nextInt();
				System.out.println("Indique numero de columnas:");
				int columnas = teclado.nextInt();

				System.out.println("Indique estratregia de resolucion (BREADTH, DEPTH, UNIFORM, GREEDY, A):");
				estrategia = teclado.next();

				//Crear algoritmo
				algoritmo=new Algoritmo(filas, columnas);
				algoritmo.setEstrategia(estrategia);

				//Generar laberinto aleatorio
				algoritmo.ejecutarAlgoritmo();
				dibujar(solucion, algoritmo, algoritmo.getFrontera(), algoritmo.getArbolInterior());

				//Resolver laberinto
				solucion = algoritmo.ejecutarBusqueda(algoritmo.getEstrategia());
				dibujar(solucion, algoritmo, algoritmo.getFrontera(), algoritmo.getArbolInterior());

				//Exportaciones
				String nombre = exportarLaberinto(algoritmo.mapa);
				exportarProblema(algoritmo, nombre);
				exportarSolucion(algoritmo, solucion);

				System.out.println("\nEl JSON y la imagen han sido guardados en la carpeta del proyecto.");
				break;

			//Cargar el laberinto y el problema
			case 2:
				System.out.println("Escriba el nombre del fichero JSON del problema (con el .json incluido):");
				String jsonProblema = teclado.next();
				Problema problema = lecturaProblema(jsonProblema);
				
				//Leemos el problema
				Casilla[][] tablero = lecturaJsonMaze(problema.getMaze());
				problema.setEstadoInicial(tablero[problema.getEstadoInicial().getFila()][problema.getEstadoInicial().getColumna()]);
				problema.setObjetivo(tablero[problema.getObjetivo().getFila()][problema.getObjetivo().getColumna()]);

				//Creamos laberinto
				algoritmo=new Algoritmo(tablero.length, tablero[0].length);
				algoritmo.setProblema(problema);
				algoritmo.setMapa(tablero);
				dibujar(solucion, algoritmo, algoritmo.getFrontera(), algoritmo.getArbolInterior());

				//Definir estratenia
				System.out.println("Indique estratregia de resolucion (BREADTH, DEPTH, UNIFORM, GREEDY, A):");
				estrategia = teclado.next();

				//Resolvemos laberinto
				algoritmo.setEstrategia(estrategia);
				solucion = algoritmo.ejecutarBusqueda(estrategia);
				dibujar(solucion, algoritmo, algoritmo.getFrontera(), algoritmo.getArbolInterior());

				//Exportamos solucion
				exportarSolucion(algoritmo, solucion);
				
				System.out.println("\nSolucion generada en archivo de texto en la carpeta del proyecto.");
		}
		teclado.close();
	}


	public static void dibujar(ArrayList<Nodo> solucion, Algoritmo algoritmo, Frontera frontera, ArrayList<Casilla> arbolInterior){
		TableroFrame frame = new TableroFrame(algoritmo, solucion, 800, 800, frontera, arbolInterior);
		frame.setTitle("Laberinto");
	}

	
	//EXPORTAR SOLUCION
	public static void exportarSolucion(Algoritmo algoritmo, ArrayList<Nodo> solucion){
		try {
			String ruta = "solution_"+algoritmo.mapa.length+"x"+algoritmo.mapa[0].length+"_"+algoritmo.estrategia+".txt";
			File file = new File(ruta);
			// Si el archivo no existe es creado
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("[id][cost,state,father_id,action,depth,h,value]\n");

			for(int i = solucion.size()-1; i>=0 ;i--){
				bw.write(solucion.get(i).toString()+"\n");
			}

			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	//JSON
	public static Casilla[][] lecturaJsonMaze(String nombre) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		Casilla[][] matrizCeldas;
		ArrayList<Casilla> listaCeldas = new ArrayList<Casilla>();
		JsonParser parser = new JsonParser();
		JsonElement jsonElementMaze = parser.parse(new FileReader(nombre));
		JsonObject jsonObjectMaze = jsonElementMaze.getAsJsonObject();

		JsonPrimitive rows = jsonObjectMaze.getAsJsonPrimitive("rows");
		JsonPrimitive cols = jsonObjectMaze.getAsJsonPrimitive("cols");
		matrizCeldas = new Casilla[Integer.parseInt(rows.toString())][Integer.parseInt(cols.toString())];

		JsonObject celdas = jsonObjectMaze.getAsJsonObject("cells");
		String cells = celdas.toString();
		//System.out.println(rows.toString() + " " + cols.toString() + "\n" + cells);

		//Quitamos los simbolos que no necesitamos para la lectura de los valores
		cells = cells.replaceAll("[\\p{Ps}\\p{Pe}\\\":, ]", " ");
		StringTokenizer tokenCells = new StringTokenizer(cells, " ");

		while(tokenCells.hasMoreElements()) {
			int fila = Integer.parseInt(tokenCells.nextToken());
			int columna = Integer.parseInt(tokenCells.nextToken());

			tokenCells.nextToken();
			int valor = Integer.parseInt(tokenCells.nextToken());
			tokenCells.nextToken();
			boolean vecinoN = Boolean.parseBoolean(tokenCells.nextToken());
			boolean vecinoE = Boolean.parseBoolean(tokenCells.nextToken());
			boolean vecinoS = Boolean.parseBoolean(tokenCells.nextToken());
			boolean vecinoO = Boolean.parseBoolean(tokenCells.nextToken());
			Casilla celda = new Casilla(fila, columna, vecinoN, vecinoE, vecinoS, vecinoO, valor);
			listaCeldas.add(celda);

		}
		int index = 0;
		for (int i = 0; i < matrizCeldas.length; i++) {
			for (int j = 0; j < matrizCeldas[0].length; j++) {
				matrizCeldas[i][j] = listaCeldas.get(index);
				index++;
			}
		}
		return matrizCeldas;
	}
	
	
	public static Problema lecturaProblema(String jsonProblema) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		Problema problema;
		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(new FileReader(jsonProblema));
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		
		JsonPrimitive initial = jsonObject.getAsJsonPrimitive("INITIAL");
		JsonPrimitive objetive = jsonObject.getAsJsonPrimitive("OBJETIVE");
		JsonPrimitive maze = jsonObject.getAsJsonPrimitive("MAZE");
		
		String posiciones = initial.getAsString() + objetive.getAsString();

		posiciones = posiciones.replaceAll("[(),]", " ");

		StringTokenizer tokens = new StringTokenizer(posiciones, " ");
		
		Casilla initialCell = new Casilla(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()));
		Casilla objetiveCell = new Casilla(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()));
		
		problema = new Problema(initialCell, objetiveCell);
		problema.setMaze(maze.getAsString());
		
		return problema;
	}
	
	
	public static void exportarProblema(Algoritmo algoritmo, String nombre){
		JsonObject obj = new JsonObject();
		obj.addProperty("INITIAL", algoritmo.problema.stringIdEstado(algoritmo.problema.getEstadoInicial()));
		obj.addProperty("OBJETIVE", algoritmo.problema.stringIdEstado(algoritmo.problema.getObjetivo()));
		obj.addProperty("MAZE", nombre);

		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(obj.toString()).getAsJsonObject();

		String jsonString = json.toString();

		String json_problema = "problema_"+algoritmo.mapa.length+"x"+algoritmo.mapa[0].length+".json";
		try {
			FileWriter file = new FileWriter(json_problema);
			file.write(jsonString);
			file.close();

		} catch (IOException e) {
			System.out.println("Error al guardar el archivo JSON.");
		}

	}

	public static String exportarLaberinto(Casilla[][] mapa) {
		int filas = mapa.length;
		int columnas = mapa[0].length;

		JsonObject obj = new JsonObject();

		int[][] mov = {{-1,0},{0,1},{1,0},{0,-1}};
		JsonArray jsonArrayMov = new Gson().toJsonTree(mov).getAsJsonArray();

		String[] id_mov = {"N", "E", "S", "O"};
		JsonArray jsonArrayMovId = new Gson().toJsonTree(id_mov).getAsJsonArray();

		JsonObject JsonCells = new JsonObject();
		JsonObject[][] JsonOneCell = new JsonObject[mapa.length][mapa[0].length];

		obj.addProperty("rows", filas);
		obj.addProperty("cols", columnas);
		obj.addProperty("max_n", 4);
		obj.add("mov", jsonArrayMov);
		obj.add("id_mov", jsonArrayMovId);

		boolean[] vecinos;
		JsonArray jsonArrayVecinos;

		for(int i = 0; i < mapa.length; i++) {
			for(int j = 0; j < mapa[0].length; j++) {
				JsonOneCell[i][j] = new JsonObject();
				JsonOneCell[i][j].addProperty("value", mapa[i][j].getValue());
				vecinos = mapa[i][j].getNeighbors();
				jsonArrayVecinos = new Gson().toJsonTree(vecinos).getAsJsonArray();
				//System.out.println(jsonArrayVecinos.toString());
				JsonOneCell[i][j].add("neighbors", jsonArrayVecinos);
				JsonCells.add("(" + i + ", " + j + ")", JsonOneCell[i][j]);
			}
		}

		obj.add("cells", JsonCells);
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(obj.toString()).getAsJsonObject();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(json);
		// System.out.println(prettyJson);
		String nombre = "problema_" + mapa.length + "x" + mapa[0].length + "_maze.json";
		try {
			FileWriter file = new FileWriter(nombre);
			file.write(prettyJson);
			file.close();

		} catch (IOException e) {
			System.out.println("Error al guardar el archivo JSON.");
		}
		return nombre;
	}
}
