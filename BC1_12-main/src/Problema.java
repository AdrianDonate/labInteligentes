public class Problema {
    private Casilla estadoInicial;
    private Casilla objetivo;
    private String maze;
    
    public Problema(Casilla estadoInicial, Casilla objetivo){
        this.estadoInicial = estadoInicial;
        this.objetivo = objetivo;
    }
    
    //GETTER
    public Casilla getEstadoInicial() {
        return estadoInicial;
    }
    
    public Casilla getObjetivo() {
        return objetivo;
    }

    public String getMaze(){
    	return maze;
    }

    //SETTER
    public void setEstadoInicial(Casilla estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public void setObjetivo(Casilla objetivo) {
        this.objetivo = objetivo;
    }
    
    public void setMaze(String maze) {
    	this.maze = maze;
    }
    
    public String stringIdEstado(Casilla casilla){
        String idEstado = "("+casilla.getFila()+", "+casilla.getColumna()+")";
        return idEstado;
    }
}
