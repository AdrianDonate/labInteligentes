import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Frontera {
   ArrayList<Nodo> frontera;

    public Frontera(){
        frontera = new ArrayList<Nodo>();
    }

    public void push(Nodo n){
        frontera.add(n);
    }

    public Nodo pop(){
        Nodo n = frontera.get(frontera.size()-1);
        frontera.remove(frontera.size()-1);
        return n;
    }

    public void imprimir(){
        System.out.println("Frontera:");
        for(int i = 0; i<frontera.size();i++){
            System.out.println(frontera.get(i).toString());
        }
    }

    public void ordenar() {
    	Comparator<Nodo> comparator = new Comparator<Nodo>() {
			@Override
			public int compare(Nodo n1, Nodo n2) {
			        // Comparamos primero los valores
			    	int resultadoValor = Double.compare(n2.getValor(), n1.getValor());
			        if (resultadoValor != 0)
			        {
			            return resultadoValor;
			        }

			        // Despues las filas
			        int resultadoFila = Integer.compare(n2.getEstado().getFila(), n1.getEstado().getFila());
			        if (resultadoFila != 0)
			        {
			            return resultadoFila;
			        }
			        
			        // A continuacion las columnas
			        int resultadoColumna = Integer.compare(n2.getEstado().getColumna(), n1.getEstado().getColumna());
			        if (resultadoColumna != 0)
			        {
			            return resultadoColumna;
			        }

			        // Finalmente el ID
			        return Integer.compare(n2.getId(), n1.getId());
			    }
    	};
    	Collections.sort(frontera, comparator); // use the comparator as much as u want
    	
    }
   
}
