//import com.sun.xml.internal.bind.v2.model.core.NonElement;

public class Nodo{
	private static int contador = -1;
	private int id;
	private Casilla estado;
	private Double valor;
	private int costo;
	private int heuristica;
	private String accion;
	private int profundidad;
	private Nodo padre;


    public Nodo(){
		contador = contador+1;
		this.id = contador;
	}

	public Casilla getEstado(){
		return estado;
	}

	public void setEstado(Casilla estado){
		this.estado = estado;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public int getHeuristica() {
		return heuristica;
	}

	public void setHeuristica(int heuristica) {
		this.heuristica = heuristica;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public void setProfundidad(int profundidad){
		this.profundidad = profundidad;
	}

	public int getProfundidad() {
		return profundidad;
	}

	public Nodo getPadre() {
		return padre;
	}

	public void setPadre(Nodo padre) {
		this.padre = padre;
	}

	public void setPadre(){
		this.padre = null;
	}

	public int getId() {
		return id;
	}

	public String toString(){
		String imprimir;
		if(profundidad == 0){
			imprimir = "["+id+"]["+costo+",("+estado.getFila()+","+estado.getColumna()+"),Null,"+accion+","+profundidad+","+heuristica+","+valor+"]";
		} else {
			imprimir = "["+id+"]["+costo+",("+estado.getFila()+","+estado.getColumna()+"),"+padre.getId()+","+accion+","+profundidad+","+heuristica+","+valor+"]";
		}
		return imprimir;
	}
}
